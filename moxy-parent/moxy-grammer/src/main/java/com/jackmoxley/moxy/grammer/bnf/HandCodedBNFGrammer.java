package com.jackmoxley.moxy.grammer.bnf;

import java.util.LinkedHashMap;

import com.jackmoxley.moxy.grammer.Grammer;
import com.jackmoxley.moxy.grammer.RuledGrammer;
import com.jackmoxley.moxy.rule.Rule;
import com.jackmoxley.moxy.rule.functional.DelegateRule;
import com.jackmoxley.moxy.rule.functional.OptionRule;
import com.jackmoxley.moxy.rule.functional.PointerRule;
import com.jackmoxley.moxy.rule.functional.SequenceRule;
import com.jackmoxley.moxy.rule.terminating.CharacterRule;
import com.jackmoxley.moxy.rule.terminating.EOFRule;
import com.jackmoxley.moxy.rule.terminating.StringRule;
import com.jackmoxley.moxy.rule.terminating.TrueRule;

/**
 * <syntax> ::= <rule> | <rule> <syntax>
 * <rule>   ::= <opt-whitespace> "<" <rule-name> ">" <opt-whitespace> "::=" <opt-whitespace> <expression> <line-end>
 * <opt-whitespace> ::= " " <opt-whitespace> | ""  ; "" is empty string, i.e. no whitespace
 * <expression>     ::= <list> | <list> <opt-whitespace> "|" <opt-whitespace> <expression>
 * <line-end>       ::= <opt-whitespace> <EOL> | <line-end> <line-end>
 * <list>    ::= <term> | <term> <opt-whitespace> <list>
 * <term>    ::= <literal> | "<" <rule-name> ">"
 * <literal> ::= '"' <text> '"' | "'" <text> "'" ; actually, the original BNF did not use quotes
 * 
 * <EOL> ::= ';' <opt-whitespace> 
 * <rule-name> ::= <text>
 * <text> ::= <character> | <character> <text>
 * <character> ::= <upper-case> | <lower-case> | <number> | '-' | '_' | <opt-whitespace>
 * <number> ::= '0' | '1' | '2' | '3' | .. | '9'
 * <upper-case> ::= 'A' | 'B' | 'C' | 'D' | .. | 'Z'
 * <lower-case> ::= 'a' | 'b' | 'c' | 'd' | .. | 'z'
 * @author jack
 *
 */
public class HandCodedBNFGrammer extends RuledGrammer implements Grammer {

	private static final long serialVersionUID = 1L;

	public HandCodedBNFGrammer() {
		super();
		this.setRuleMap(new LinkedHashMap<String,Rule>());
		syntaxDefinition();
		ruleDefinition();
		optWhitespaceDefinition();
		expressionDefinition();
		lineEndDefinition();
		listDefinition();
		termDefinition();
		literalDefinition();
		eolDefinition();
		ruleNameDefinition();
		textDefinition();
		characterDefinition();
		lowerCaseDefinition();
		upperCaseDefinition();
		numberDefinition();
		symbolDefinition();
	}

	
	@Override
	public Rule getStart() {
//		SequenceRule fileEnded = new SequenceRule();
//		fileEnded.add(ruleMap.get("syntax"));
//		fileEnded.add(EOFRule.get());
		return ruleMap.get("syntax");
	}
	
	/**
	 * <syntax> ::= <rule> | <rule> <syntax>
	 */
	protected void syntaxDefinition() {
		PointerRule rule = new PointerRule(true,"rule");
		
		OptionRule syntax = new OptionRule();
		SequenceRule ended = new SequenceRule();
		ended.add(rule);
		ended.add(EOFRule.get());
		syntax.add(ended);
		
		SequenceRule more = new SequenceRule();
		more.add(rule);
		more.add(syntax);
		
		syntax.add(more);

		this.ruleMap.put("syntax", syntax);
	}
	

	/**
	 * <rule>   ::= <opt-whitespace> "<" <rule-name> ">" <opt-whitespace> "::=" <opt-whitespace> <expression> <line-end>
	 */
	protected void ruleDefinition() {
		SequenceRule rule = new SequenceRule();
		rule.add(new PointerRule(false,"opt-whitespace"));
		rule.add(new CharacterRule('<'));
		rule.add(new PointerRule(true,"rule-name"));
		rule.add(new CharacterRule('>'));
		rule.add(new PointerRule(false,"opt-whitespace"));
		rule.add(new StringRule("::="));
		rule.add(new PointerRule(false,"opt-whitespace"));
		rule.add(new PointerRule(true,"expression"));
		rule.add(new PointerRule(false,"line-end"));
		this.ruleMap.put("rule", rule);
	}
	
	/**
	 * <opt-whitespace> ::= " " <opt-whitespace> | ""  ; "" is empty string, i.e. no whitespace
	 */
	protected void optWhitespaceDefinition() {
		OptionRule singlewhitespace = new OptionRule();
		for(char i = 0 ;i <= ' ';i++){
			singlewhitespace.add(new CharacterRule(i));
		}
		OptionRule optionalWhitespace = new OptionRule();
		SequenceRule whitespace = new SequenceRule();
		whitespace.add(singlewhitespace);
		whitespace.add(optionalWhitespace);
		optionalWhitespace.add(whitespace);
		optionalWhitespace.add(TrueRule.get()); // will be cheaper to use a trueRule then empty string
		
		this.ruleMap.put("opt-whitespace", optionalWhitespace);
	}
	
	/**
	 * <expression>     ::= <list> | <list> <opt-whitespace> "|" <opt-whitespace> <expression>
	 */
	protected void expressionDefinition() {
		PointerRule list = new PointerRule(true,"list");
		PointerRule optWhitespace = new PointerRule(false,"opt-whitespace");
		
		OptionRule expression = new OptionRule();
		expression.add(list);
		
		SequenceRule or = new SequenceRule();
		or.add(list);
		or.add(optWhitespace);
		or.add(new CharacterRule('|'));
		or.add(new StringRule(""));
		or.add(optWhitespace);
		or.add(expression);
		
		expression.add(or);
		
		this.ruleMap.put("expression", expression);
	}
	
	/**
	 * <line-end>       ::= <EOL> | <line-end> <line-end>
	 */
	protected void lineEndDefinition(){

		
//
//		SequenceRule repeating = new SequenceRule();
//		OptionRule orlineEnd = new OptionRule();
//		
//		orlineEnd.add(new PointerRule(false,"EOL"));
//		orlineEnd.add(repeating);
//		
//		repeating.add(orlineEnd);
//		repeating.add(repeating);
		
		this.ruleMap.put("line-end", new PointerRule(false,"EOL"));
	}
	
	/**
	 * 
	 * <list>    ::= <term> | <term> <opt-whitespace> <list>
	 */
	protected void listDefinition() {

		PointerRule term = new PointerRule(false,"term");
		OptionRule list = new OptionRule();
		list.add(term);
		SequenceRule nextItem = new SequenceRule();
		nextItem.add(term);
		nextItem.add(new PointerRule(false,"opt-whitespace"));
		nextItem.add(list);
		

		list.add(nextItem);

		this.ruleMap.put("list", list);
	}

	/**
	 * <term>    ::= <literal> | "<" <rule-name> ">"
	 */
	protected void termDefinition() {
		OptionRule term = new OptionRule();
		SequenceRule ruleTerm = new SequenceRule();
		ruleTerm.add(new CharacterRule("<"));
		ruleTerm.add(new PointerRule(true,"rule-name"));
		ruleTerm.add(new CharacterRule(">"));
		term.add(ruleTerm);
		term.add(new PointerRule(false,"literal"));

		this.ruleMap.put("term", term);
	}
	

	/**
	 * <literal> ::= '"' <text> '"' | "'" <text> "'" | '"' <character> '"' | "'" <character> "'" 
	 */
	protected void literalDefinition() {
		OptionRule textOrSymbolSingle = new OptionRule();
		textOrSymbolSingle.add(new PointerRule(true,"text"));
		textOrSymbolSingle.add(new DelegateRule(true,"text",new CharacterRule('\'')));
		textOrSymbolSingle.add(new DelegateRule(true,"text",new CharacterRule('<')));
		textOrSymbolSingle.add(new DelegateRule(true,"text",new CharacterRule('>')));
		OptionRule textOrSymbolDouble = new OptionRule();
		textOrSymbolDouble.add(new PointerRule(true,"text"));
		textOrSymbolDouble.add(new DelegateRule(true,"text", new CharacterRule('"')));
		textOrSymbolDouble.add(new DelegateRule(true,"text",new CharacterRule('<')));
		textOrSymbolDouble.add(new DelegateRule(true,"text",new CharacterRule('>')));
		SequenceRule doubleQuote = new SequenceRule();
		doubleQuote.add(new CharacterRule("\""));
		doubleQuote.add(textOrSymbolSingle);
		doubleQuote.add(new CharacterRule("\""));
		SequenceRule singleQuote = new SequenceRule();
		singleQuote.add(new CharacterRule("'"));
		singleQuote.add(textOrSymbolDouble);
		singleQuote.add(new CharacterRule("'"));


		OptionRule literal = new OptionRule();
		literal.add(doubleQuote);
		literal.add(singleQuote);
		this.ruleMap.put("literal", literal);
	}
	
	/**
	 * <EOL> ::= ';' <opt-whitespace> 
	 */
	protected void eolDefinition() {
		PointerRule optWhitespace = new PointerRule(false,"opt-whitespace");
		SequenceRule eol = new SequenceRule();
		eol.add(optWhitespace);
		eol.add(new CharacterRule(";"));
		eol.add(optWhitespace);
		
		this.ruleMap.put("EOL", eol);
	}
	
	/**
	 * <rule-name> ::= <text>
	 */
	protected void ruleNameDefinition() {
		PointerRule ruleName = new PointerRule(false,"text");
		
		this.ruleMap.put("rule-name", ruleName);
	}
	/**
	 * <text> ::= <character> | <character> <text>
	 */
	protected void textDefinition(){
		OptionRule text = new OptionRule();
		PointerRule character = new PointerRule(false,"character");
		text.add(character);
		SequenceRule more = new SequenceRule();
		more.add(character);
		more.add(text);
		text.add(more);
		this.ruleMap.put("text", text);
	}
	
	/**
	 * <character> ::= <upper-case> | <lower-case> | <number> | '-' | '_' | <opt-whitespace>
	 */
	protected void characterDefinition() {
		OptionRule character = new OptionRule();
		character.add(new PointerRule(false,"upper-case"));
		character.add(new PointerRule(false,"lower-case"));
		character.add(new PointerRule(false,"number"));
		character.add(new PointerRule(false,"symbol"));
		character.add(new PointerRule(false,"opt-whitespace"));
//		character.add(new CharacterRule('-'));
//		character.add(new CharacterRule('"'));
//		character.add(new CharacterRule('<'));
//		character.add(new CharacterRule('>'));
		character.setGreedy(false);
		this.ruleMap.put("character",character);
	}


	/**
	 * <lower-case> ::= 'a' | 'b' | 'c' | 'd' | .. | 'z'
	 */
	protected void lowerCaseDefinition() {
		OptionRule lowercase = new OptionRule();
		for(char i = 'a' ;i <= 'z';i++){
			lowercase.add(new CharacterRule(i));
		}
		lowercase.setGreedy(false);
		this.ruleMap.put("lower-case", lowercase);
	}

	/**
	 * <upper-case> ::= 'A' | 'B' | 'C' | 'D' | .. | 'Z'
	 */
	protected void upperCaseDefinition() {
		OptionRule uppercase = new OptionRule();
		for(char i = 'A' ;i <= 'Z';i++){
			uppercase.add(new CharacterRule(i));
		}
		uppercase.setGreedy(false);
		this.ruleMap.put("upper-case", uppercase);
	}

	/** 
	 * <number> ::= '0' | '1' | '2' | '3' | .. | '9'
	 */
	protected void numberDefinition() {
		OptionRule number = new OptionRule();
		for(char i = '0' ;i <= '9';i++){
			number.add(new CharacterRule(i));
		}
		number.setGreedy(false);
		this.ruleMap.put("number", number);
	}
	
	/**
	 * <symbol> ::= '-' | '_' | '"' | "'" | ';' | '<' | '>' | '|' | ':' | '=' | '\';
	 */
	protected void symbolDefinition() {
		OptionRule symbol = new OptionRule();
		symbol.add(new CharacterRule('_'));
		symbol.add(new CharacterRule('-'));
//		symbol.add(new CharacterRule('"'));
//		symbol.add(new CharacterRule('<'));
//		symbol.add(new CharacterRule('>'));
//		symbol.add(new CharacterRule('\''));
		symbol.add(new CharacterRule(';'));
		symbol.add(new CharacterRule('|'));
		symbol.add(new CharacterRule(':'));
		symbol.add(new CharacterRule('='));
		symbol.add(new CharacterRule('\\'));
		symbol.setGreedy(false);
		this.ruleMap.put("symbol", symbol);
	}
	

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("HandCodedBNFGrammer [ruleMap=").append(ruleMap)
				.append("]");
		return builder.toString();
	}
	

}
