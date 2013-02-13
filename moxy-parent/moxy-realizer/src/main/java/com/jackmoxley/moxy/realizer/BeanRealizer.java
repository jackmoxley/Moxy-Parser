package com.jackmoxley.moxy.realizer;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jackmoxley.moxy.realizer.transformer.Transformer;
import com.jackmoxley.moxy.realizer.xml.Bean;
import com.jackmoxley.moxy.realizer.xml.BeanArgument;
import com.jackmoxley.moxy.realizer.xml.BeanConstructor;
import com.jackmoxley.moxy.realizer.xml.BeanField;
import com.jackmoxley.moxy.realizer.xml.BeanMethod;
import com.jackmoxley.moxy.realizer.xml.HasArguments;
import com.jackmoxley.moxy.reflection.ReflectionUtils;
import com.jackmoxley.moxy.token.SymbolToken;
import com.jackmoxley.moxy.token.Token;

public class BeanRealizer<T> implements Realizer<T> {

	public static final Logger logger = LoggerFactory
			.getLogger(BeanRealizer.class);
	private Map<String, Bean> beans;
	private Map<Bean, Object> singletons;
	private Map<String, Transformer> transformers = new HashMap<String, Transformer>();
	private int level = 0;

	public BeanRealizer() {
		super();
	}

	public BeanRealizer(List<Bean> beanList) {
		super();
		createBeanMap(beanList);
	}

	public BeanRealizer(Map<String, Bean> beans) {
		super();
		this.beans = beans;
	}

	protected void createBeanMap(List<Bean> beanList) {
		this.beans = new HashMap<String, Bean>();
		for (Bean bean : beanList) {
			beans.put(bean.getName(), bean);
		}
	}

	public List<T> realize(List<Token> tokens) {
		level = 0;
		singletons = new HashMap<Bean,Object>();
		Set<T> list = new LinkedHashSet<T>();
		for (Token token : tokens) {
			if (token instanceof SymbolToken) {
				SymbolToken symbol = (SymbolToken) token;
				@SuppressWarnings("unchecked")
				T instance = (T) getInstance(beans.get(symbol.getSymbol()),
						symbol);
				if (instance != null) {
					list.add(instance);
				}
			}
		}
		return new ArrayList<T>(list);
	}

	protected Object getInstance(Bean bean, SymbolToken token) {
		logger.info("getInstance:{} {}, {}" ,level,bean, token);
		Object instance = null;
		level++;
		if(bean.isSingleton()){
			instance = singletons.get(bean);

			if(instance == null) {
				instance = createInstance(bean.getClazz(), bean, token);
				singletons.put(bean, instance);
			}
		} else {
			instance = createInstance(bean.getClazz(), bean, token);
		}
		logger.info("getInstance:{} {} ",level, instance);

		fillInstance(bean, token, instance);
		level--;
		return instance;
	}

	protected Object createInstance(String clazz, Bean bean, SymbolToken token) {
		BeanConstructor constructor = bean.getConstructor();
		Object[] arguments;

		// Create the constructor
		if (constructor != null) {
			List<Object[]> internalArgs = getArguments(constructor, token,
					false);
			if (internalArgs.isEmpty()) {
				arguments = new Object[0];
			} else {
				arguments = internalArgs.get(0);
			}
		} else {
			arguments = new Object[0];
		}
		Object instance;
		try {
			instance = ReflectionUtils.newInstance(clazz, arguments);
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| IOException | ClassNotFoundException e) {
			if (bean.getExtends() != null) {
				return createInstance(clazz, beans.get(bean.getExtends()),
						token);
			}
			e.printStackTrace();
			return null;
		}

		return instance;
	}

	protected void fillInstance(Bean bean, SymbolToken token, Object instance) {
		fillInstanceFromToken(bean, token, instance);
		String includesTerm = bean.getIncludes();
		for (SymbolToken includesToken : token.get(includesTerm)) {
			fillInstance(bean, includesToken, instance);
		}
	}
	


	protected void fillInstanceFromToken(Bean bean, SymbolToken token,
			Object instance) {
		if (bean.getExtends() != null) {
			fillInstance(beans.get(bean.getExtends()), token, instance);
		}
		fillField(bean, token, instance);

		executeMethods(bean, token, instance);

	}

	protected void executeMethods(Bean bean, SymbolToken token, Object instance) {
		TreeSet<MethodToExecute> methodsToExecute = new TreeSet<MethodToExecute>();
		// Execute any methods
		for (BeanMethod method : bean.getMethod()) {
			// I want a list of arguments and their associated objects
			if (method.getArgument().size() == 0) {
				methodsToExecute.add(new MethodToExecute(instance, method
						.getName(), new Object[0], 0));
			} else {

				List<Object[]> arguments = getArguments(method, token, true);
				logger.info("executeMethods:{} Considering {}, {}, {}",level,method, token, arguments.size());
				int count = 0;
				for (Object[] args : arguments) {
					if (args != null) {
						logger.info(
								"executeMethods:{} Found {}, {}, {}. {}",level,
								method, arguments, token, args.length);
						for (Object arg : args) {
							logger.info(
									"executeMethods:{} {}, {}, {}. {}",level,
									method, arguments, token, arg);
						}
						methodsToExecute.add(new MethodToExecute(instance,
								method.getName(), args, count));
					} 
					count++;
				}
			}
		}

		for (MethodToExecute method : methodsToExecute) {
			method.execute();
		}
	}

	protected void fillField(Bean bean, SymbolToken token, Object instance) {
		// Fill in any fields
		for (BeanField field : bean.getField()) {
			Object value = getArgument(field, token);
			try {
				ReflectionUtils.setField(instance, field.getName(), value);
			} catch (NoSuchFieldException | IllegalArgumentException
					| IllegalAccessException | IOException e) {
				e.printStackTrace();
			}
		}
	}

	class MethodToExecute implements Comparable<MethodToExecute> {

		final Integer location;
		final Object instance;
		final String methodName;
		final Object[] arguments;

		public MethodToExecute(Object instance, String methodName,
				Object[] arguments, int location) {
			super();
			this.instance = instance;
			this.methodName = methodName;
			this.arguments = arguments;
			this.location = location;
		}

		@Override
		public int compareTo(MethodToExecute o) {
			int compare = location.compareTo(o.location);
			if(compare == 0){
				return new Integer(this.hashCode()).compareTo(new Integer(o.hashCode()));
			}
			return compare;
		}

		public void execute() {
			try {
				logger.info("execute:{} {}, {}, {}",level,instance, methodName, arguments);
				ReflectionUtils.executeMethod(instance, methodName, arguments);
			} catch (NoSuchMethodException | IllegalAccessException
					| IllegalArgumentException | InvocationTargetException
					| IOException e) {
				logger.error("An Error occured", e);
			}
		}

	}

	protected List<Object[]> getArguments(HasArguments beanArguments,
			SymbolToken token, boolean includeNulls) {
		if (includeNulls) {
			List<BeanArgument> arguments = beanArguments.getArgument();
			int size = arguments.size();
			List<Object[]> instancesList = new ArrayList<Object[]>();
			for (int a = 0; a < size; a++) {
				List<Object> objects = getArgument(arguments.get(a), token);
				for (int i = 0; i < objects.size(); i++) {
					Object obj = objects.get(i);
					logger.info("getArguments:{} considering {}, {}, {} ",level, a, i, obj);
					Object[] instances;
					if (instancesList.size() == i) {
						if (obj == null) {
							instances = null;
						} else {
							logger.info("getArguments:{} creating new {}, {}",level, a, i);
							instances = new Object[size];
						}
						instancesList.add(instances);
					} else {

						instances = instancesList.get(i);

						if (obj == null && instances != null) {
							logger.info("getArguments:{} nullfying {}, {}, {}",level, a, i, instances);
							instancesList.set(i, null);
							instances = null;
						}
					}
					if (instances != null) {
						logger.info("getArguments:{} setting {}, {}, {}, {}",level, a, i, obj, instances);
						instances[a] = obj;
					}
				}
			}
			logger.info("getArguments:{} {}, {}, {}",level, arguments, token, instancesList);
			return instancesList;
		} else {
			List<BeanArgument> arguments = beanArguments.getArgument();
			int size = arguments.size();
			List<Object[]> instancesList = new ArrayList<Object[]>();
			for (int a = 0; a < size; a++) {
				List<Object> objects = getArgument(arguments.get(a), token);
				for (int i = 0; i < objects.size(); i++) {
					Object obj = objects.get(i);
					if (obj == null) {
						objects.remove(i--);
					} else {
						Object[] instances;
						if (instancesList.size() == i) {
							instances = new Object[size];
							instancesList.add(instances);
						} else {
							instances = instancesList.get(i);
						}
						instances[a] = obj;
					}
				}
			}
			logger.info("getArguments:{} {}, {}, {}",level, arguments, token, instancesList);
			return instancesList;
		}
	}

	protected List<Object> getArgument(BeanArgument argument, SymbolToken token) {
		logger.info("getArgument:{} {}, {}",level, argument, token);
		List<Object> toReturn = new ArrayList<Object>();
		if (argument.getValue() != null) {
			toReturn.add(transform(argument, argument.getValue()));
			return toReturn;
		}
		List<SymbolToken> tokens = token.get(argument.getTerm());
//		List<SymbolToken> tokens = token.getOrNull(argument.getTerm());
		List<Object> subArguments = new ArrayList<Object>();
		if (argument.getBean() != null) {
			logger.info("getArgument:{} isBean {}",level, argument);
			Bean bean = beans.get(argument.getBean());
			for (SymbolToken subToken : tokens) {
				if (subToken != null) {
					logger.info("getArgument:{} BEFORE {}, {}",level, argument, subToken);
					Object subArgument = getInstance(bean, subToken);
					logger.info("getArgument:{} AFTER {}, {}, {}",level, argument,
							subToken, subArgument);
					subArguments.add(subArgument);
				} else {
					subArguments.add(null);
				}
			}
		} else {
			logger.info("getArgument:{} isValue {}",level, argument);
			if (tokens.size() == 0) {
				logger.info("getArgument:{} isValue {} no subTokens",level, argument);
				subArguments.add(token.getValue());
			} else {
				logger.info("getArgument:{} isValue {} with subTokens {}",level, argument, tokens);
				for (SymbolToken subToken : tokens) {
					if (subToken != null) {
						logger.info("getArgument:{} ADD {}, {}, {}",level, argument,
								subToken, subToken.getValue());
						subArguments.add(subToken.getValue());
					} else {
						subArguments.add(null);
					}
				}
			}
		}
		for (Object obj : subArguments) {
			if (obj != null) {
				toReturn.add(transform(argument, obj));
			} else {
				toReturn.add(null);
			}
		}
		return toReturn;

	}

	protected Object transform(BeanArgument argument, Object value) {

		if (argument.getTransformer() == null) {
			return value;
		} else {
			return getTransformer(argument.getTransformer()).transform(value);
		}
	}

	private Transformer getTransformer(String transformerName) {
		Transformer transformer = transformers.get(transformerName);
		if (transformer == null) {
			try {
				transformer = ReflectionUtils.newInstance(transformerName);
				transformers.put(transformerName, transformer);
			} catch (Exception e) {
				throw new RuntimeException("No Transformer Found", e);
			}
		}
		return transformer;
	}

}
