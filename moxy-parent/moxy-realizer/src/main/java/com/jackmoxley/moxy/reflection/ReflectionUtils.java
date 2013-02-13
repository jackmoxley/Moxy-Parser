package com.jackmoxley.moxy.reflection;

import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReflectionUtils {
	
	public static final Logger logger = LoggerFactory.getLogger(ReflectionUtils.class);

	public static <T> T newInstance(String className, Object... arguments)
			throws InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException,
			ClassNotFoundException, IOException {
		return newInstance(Class.forName(className), arguments);
	}

	@SuppressWarnings("unchecked")
	public static <T> T newInstance(Class<?> clazz, Object... arguments)
			throws IOException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		logger.info("newInstance " + clazz + " " + arguments);
		for (Object argument : arguments) {
			logger.info("* newInstance {}, {}, {}" , clazz,argument == null ? "null" : argument.getClass(),argument);
		}
		Object[] realArguments = new Object[arguments.length];
		Constructor<?> constructor = getConstructor(clazz, realArguments,
				arguments);

		for (Object argument : realArguments) {
			logger.info("** newInstance " + clazz + " " + argument);
		}
		try (Accessor<?> access = open(constructor)) {
			return (T) constructor.newInstance(realArguments);
		}
	}

	public static Object executeMethod(Object instance, String methodName,
			Object... params) throws NoSuchMethodException, IOException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		Object[] realArguments = new Object[params.length];
		Method method = getMethod(instance, methodName, realArguments, params);

		try (Accessor<?> access = open(method)) {
			return method.invoke(instance, realArguments);
		}
	}

	public static void setField(Object instance, String fieldName, Object value)
			throws NoSuchFieldException, IOException, IllegalArgumentException,
			IllegalAccessException {
		Field field = getField(instance, fieldName);
		Object[] argument = new Object[1];
		int fits = fitsArgument(field.getType(), argument, value, 0);
		if (fits < 0) {
			return;
		}
		try (Accessor<Field> access = open(field)) {
			field.set(instance, argument[0]);
		}
	}

	public static Field getField(Object instance, String fieldName)
			throws NoSuchFieldException {
		if (instance == null) {
			throw new NullPointerException("Instance is null for fieldName "
					+ fieldName);
		}
		Class<?> clazz = instance.getClass();
		do {
			try {
				Field field = clazz.getDeclaredField(fieldName);
				if (field != null) {
					return field;
				}
			} catch (Exception e) {
			}
			clazz = clazz.getSuperclass();
		} while (clazz != null);
		throw new NoSuchFieldException(fieldName + " field does not exist on "
				+ instance.getClass().getName());
	}

	public static Constructor<?> getConstructor(Class<?> clazz,
			Object[] argsOut, Object... argsIn) {
		Constructor<?>[] constructors = clazz.getDeclaredConstructors();
		int length = argsIn.length;
		TreeMap<Integer, Constructor<?>> bestFitConstructors = new TreeMap<Integer, Constructor<?>>();
		for (Constructor<?> constructor : constructors) {
			if (constructor.getParameterTypes().length == length) {
				int distance = fitsArguments(constructor.getParameterTypes(),
						argsOut, argsIn);
				bestFitConstructors.put(distance, constructor);
				return constructor;
			}
		}

		if (!bestFitConstructors.isEmpty()) {
			Constructor<?> constructor = bestFitConstructors.firstEntry()
					.getValue();
			// make sure we fill it again in this circumstance;
			fitsArguments(constructor.getParameterTypes(), argsOut, argsIn);
			return constructor;
		}
		return null;
	}

	public static Method getMethod(Object instance, String methodName,
			Object[] argsOut, Object... argsIn) throws NoSuchMethodException {
		logger.debug("getMethod " + methodName + " " + argsIn + " "
				+ argsOut.length);
		for (Object object : argsIn) {
			logger.debug("* getMethod {}, {}", methodName , object);
		}
		if (instance == null) {
			throw new NullPointerException("Instance is null for methodName "
					+ methodName);
		}
		Class<?> clazz = instance.getClass();
		TreeMap<Integer, Method> bestFitMethods = new TreeMap<Integer, Method>();
		do {
			logger.debug("** getMethod " + methodName + " "
					+ clazz.getSimpleName() + " " + argsOut.length + " "
					+ instance);
			try {

				Method[] methods = clazz.getDeclaredMethods();
				for (Method method : methods) {
					logger.debug("** getMethod got " + method + " "
							+ methodName + " " + clazz.getSimpleName() + " "+ argsIn.length+" "
							+ argsOut.length + " " + instance);
					if (method.getName().equals(methodName)) {
						logger.debug("Considering " + method);
						int distance = fitsArguments(
								method.getParameterTypes(), argsOut, argsIn);
						if (distance != -1) {
							logger.debug("Accepted " + method
									+ " distance:" + distance);
							bestFitMethods.put(distance, method);
						} else {
							logger.debug("Unconsidered " + method);
						}
					}
				}
				for (Method method : bestFitMethods.values()) {
					logger.debug("Considering Best Fit Methods" + method);
					// make sure we fill it again in this circumstance;
					fitsArguments(method.getParameterTypes(), argsOut, argsIn);
					return method;
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			clazz = clazz.getSuperclass();
		} while (clazz != null);
		throw new NoSuchMethodException(methodName
				+ " method does not exist on " + instance.getClass().getName());
	}
	
	/**
	 * Returns a distance factor from the arguments provided. -1 means it does
	 * not fit.
	 * 
	 * @param possibility
	 * @param argsOut
	 * @param argsIn
	 * @return
	 */
	public static int fitsArguments(Class<?>[] parameterTypes,
			Object[] argsOut, Object... argsIn) {
		int length = parameterTypes.length;
		if (length != argsIn.length) {
			for (int i = 0; i < argsIn.length; i++) {
				logger.info("* fitsArguments " + argsIn[i]);
			}
			logger.info("fitsArguments length " + length
					+ " does not match " + argsIn.length);
			return -1;
		}

		int distance = 0;
		int subDistance = 0;
		for (int i = 0; i < length; i++) {
			subDistance = fitsArgument(parameterTypes[i], argsOut, argsIn[i], i);
			if (subDistance == -1) {
				logger.info("* fitsArguments subDistance is wrong " + parameterTypes[i] + " " +argsIn[i]+" "+i);
				return -1;
			} else {
				distance += subDistance;
			}
		}

		return distance;
	}

	@SuppressWarnings("unchecked")
	public static int fitsArgument(Class<?> type, Object[] argsOut,
			Object argument, int index) {
		if (argument == null) {
			argsOut[index] = null;
			return 1;
		}
		int distance = 0;
		if (type.isArray()) {
			Object array = createObjectArray(type.getComponentType(), argument);
			if (array == null) {
				logger.info("Failed not array ");
				return -1;
			}
			argsOut[index] = array;
		} else if (Collection.class.isAssignableFrom(type)) {
			if (type.isAssignableFrom(ArrayList.class)) {

				logger.info("Type: " + type
						+ " Is assignable with ArrayList");
				Object array = createObjectArray(Object.class, argument);
				if (array == null) {
					logger.trace("Failed not ArrayList ");
					return -1;
				}
				int length = Array.getLength(array);
				@SuppressWarnings("rawtypes")
				List list = new ArrayList(length);
				for (int i = 0; i < length; i++) {
					list.add(Array.get(array, i));
				}
				argsOut[index] = list;

			} else if (type.isAssignableFrom(LinkedHashSet.class)) {

				Object array = createObjectArray(Object.class, argument);
				if (array == null) {
					logger.info("Failed not LinkedHashSet ");
					return -1;
				}
				int length = Array.getLength(array);
				@SuppressWarnings("rawtypes")
				Set set = new LinkedHashSet(length);
				for (int i = 0; i < length; i++) {
					set.add(Array.get(array, i));
				}
				argsOut[index] = set;

			} 
		} else {
			if((!type.isInstance(argument)) && (argument instanceof Iterable<?>)){
				Iterator<?> it =((Iterable<?>)argument).iterator();
				if(it.hasNext()){
					argument = it.next();
				}
			}
			if (type.isInstance(argument)) {
				logger.info("Type: {} Is assignable with {} "
						,type,argument.getClass());
				Class<?> argumentClass = argument.getClass();
				argsOut[index] = argument;
				do {
					if (argumentClass.isAssignableFrom(type)) {
						return distance;
					}
					distance++;
					argumentClass = argumentClass.getSuperclass();
				} while (argumentClass != null);
			}
		}
		logger.info("Failed not Assignable " + type + " "
				+ argument);
		return -1;
	}
	
	

	public static Object createObjectArray(Class<?> type, Object argument) {
		Object objects = null;
		if (argument instanceof Collection) {
			Collection<?> collection = ((Collection<?>) argument);
			if (collection.size() == 0) {
				objects = Array.newInstance(type, 0);
			}
			objects = collection.toArray();
			for (Object object : collection) {
				logger.trace("createObjectArray " + type + " " + object);
				if (!type.isInstance(object)) {
					return null;
				}
			}
		} else if (argument.getClass().isArray()) {
			int length = Array.getLength(argument);
			objects = Array.newInstance(type, length);
			for (int i = 0; i < length; i++) {
				Object object = Array.get(argument, i);
				if (!type.isInstance(object)) {
					return null;
				}
				Array.set(objects, i, object);
			}
		} else if (type.isInstance(argument)) {
			objects = Array.newInstance(type, 1);
			Array.set(objects, 0, argument);
		}
		return objects;

	}

	public static <T extends AccessibleObject & Member> Accessor<T> open(
			T accessibleObject) throws IOException {
		return new ReflectionUtils.Accessor<T>(accessibleObject);
	}

	public static class Accessor<T extends AccessibleObject & Member>
			implements Closeable {
		private final boolean isAccessible;
		private final boolean isFinal;
		private final int modifiers;
		private final T accessibleObject;

		private Accessor(T accessibleObject) throws IOException {
			super();
			if (accessibleObject == null) {
				throw new IOException(
						"Error preparing field for accesibility: Field is null");
			}
			try {
				this.accessibleObject = accessibleObject;
				this.modifiers = accessibleObject.getModifiers();
				this.isAccessible = accessibleObject.isAccessible();
				this.isFinal = Modifier.isFinal(modifiers);
				if (!this.isAccessible) {
					accessibleObject.setAccessible(true);
				}
				if (this.isFinal) {
					getModifiersField(accessibleObject).setInt(
							accessibleObject, modifiers & ~Modifier.FINAL);
				}
			} catch (Exception e) {
				throw new IOException("Error preparing field for accesibility",
						e);
			}

		}

		@Override
		public void close() throws IOException {

			if (!this.isAccessible) {
				accessibleObject.setAccessible(false);
			}
			if (this.isFinal) {
				try {
					getModifiersField(accessibleObject).setInt(
							accessibleObject, modifiers);
				} catch (Exception e) {
					throw new IOException("Error setting field to final", e);
				}
			}
		}

		public T getAccessibleObject() {
			return accessibleObject;
		}

		private static Field getModifiersField(AccessibleObject toFetch) {
			Field field;
			try {
				field = toFetch.getClass().getField("modifiers");
				field.setAccessible(true);
				return field;
			} catch (Exception e) {
				throw new RuntimeException(
						"Error occured getting modifiers field", e);
			}
		}
	}

}
