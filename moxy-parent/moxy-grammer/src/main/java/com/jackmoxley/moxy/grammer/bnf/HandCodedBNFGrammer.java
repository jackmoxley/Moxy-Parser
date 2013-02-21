/**
 * Copyright (C) 2013  John Orlando Keleshian Moxley
 * 
 * Unless otherwise stated by the license provided by the copyright holder.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.jackmoxley.moxy.grammer.bnf;

import com.jackmoxley.meta.Beta;
import com.jackmoxley.moxy.grammer.Grammar;
import com.jackmoxley.moxy.grammer.RuledGrammar;
import com.jackmoxley.moxy.rule.functional.list.ChoiceRule;
import com.jackmoxley.moxy.rule.functional.list.SequenceRule;
import com.jackmoxley.moxy.rule.functional.symbol.DelegateRule;
import com.jackmoxley.moxy.rule.functional.symbol.PointerRule;
import com.jackmoxley.moxy.rule.terminating.TrueRule;
import com.jackmoxley.moxy.rule.terminating.text.CharacterRule;
import com.jackmoxley.moxy.rule.terminating.text.EOFRule;
import com.jackmoxley.moxy.rule.terminating.text.TextRule;

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
@Beta
public class HandCodedBNFGrammer extends RuledGrammar implements Grammar {

	private static final long serialVersionUID = 1L;

	public HandCodedBNFGrammer() {
		super();
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

	
	/**
	 * <syntax> ::= <rule> | <rule> <syntax>
	 */
	protected void syntaxDefinition() {
		PointerRule rule = new PointerRule(true,"rule");
		
		ChoiceRule syntax = new ChoiceRule();
		SequenceRule ended = new SequenceRule();
		ended.add(rule);
		ended.add(EOFRule.get());
		syntax.add(ended);
		
		SequenceRule more = new SequenceRule();
		more.add(rule);
		more.add(syntax);
		
		syntax.add(more);

		put("syntax", syntax);
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
		rule.add(new TextRule("::="));
		rule.add(new PointerRule(false,"opt-whitespace"));
		rule.add(new PointerRule(true,"expression"));
		rule.add(new PointerRule(false,"line-end"));
		put("rule", rule);
	}
	
	/**
	 * <opt-whitespace> ::= " " <opt-whitespace> | ""  ; "" is empty string, i.e. no whitespace
	 */
	protected void optWhitespaceDefinition() {
		ChoiceRule singlewhitespace = new ChoiceRule();
		for(char i = 0 ;i <= ' ';i++){
			singlewhitespace.add(new CharacterRule(i));
		}
		ChoiceRule optionalWhitespace = new ChoiceRule();
		SequenceRule whitespace = new SequenceRule();
		whitespace.add(singlewhitespace);
		whitespace.add(optionalWhitespace);
		optionalWhitespace.add(whitespace);
		optionalWhitespace.add(TrueRule.get()); // will be cheaper to use a trueRule then empty string
		
		put("opt-whitespace", optionalWhitespace);
	}
	
	/**
	 * <expression>     ::= <list> | <list> <opt-whitespace> "|" <opt-whitespace> <expression>
	 */
	protected void expressionDefinition() {
		PointerRule list = new PointerRule(true,"list");
		PointerRule optWhitespace = new PointerRule(false,"opt-whitespace");
		
		ChoiceRule expression = new ChoiceRule();
		expression.add(list);
		
		SequenceRule or = new SequenceRule();
		or.add(list);
		or.add(optWhitespace);
		or.add(new CharacterRule('|'));
		or.add(new TextRule(""));
		or.add(optWhitespace);
		or.add(expression);
		
		expression.add(or);
		
		put("expression", expression);
	}
	
	/**
	 * <line-end>       ::= <EOL> | <line-end> <line-end>
	 */
	protected void lineEndDefinition(){

		
//
//		SequenceRule repeating = new SequenceRule();
//		ChoiceRule orlineEnd = new ChoiceRule();
//		
//		orlineEnd.add(new PointerRule(false,"EOL"));
//		orlineEnd.add(repeating);
//		
//		repeating.add(orlineEnd);
//		repeating.add(repeating);
		
		put("line-end", new PointerRule(false,"EOL"));
	}
	
	/**
	 * 
	 * <list>    ::= <term> | <term> <opt-whitespace> <list>
	 */
	protected void listDefinition() {

		PointerRule term = new PointerRule(false,"term");
		ChoiceRule list = new ChoiceRule();
		list.add(term);
		SequenceRule nextItem = new SequenceRule();
		nextItem.add(term);
		nextItem.add(new PointerRule(false,"opt-whitespace"));
		nextItem.add(list);
		

		list.add(nextItem);

		put("list", list);
	}

	/**
	 * <term>    ::= <literal> | "<" <rule-name> ">"
	 */
	protected void termDefinition() {
		ChoiceRule term = new ChoiceRule();
		SequenceRule ruleTerm = new SequenceRule();
		ruleTerm.add(new CharacterRule("<"));
		ruleTerm.add(new PointerRule(true,"rule-name"));
		ruleTerm.add(new CharacterRule(">"));
		term.add(ruleTerm);
		term.add(new PointerRule(false,"literal"));

		put("term", term);
	}
	

	/**
	 * <literal> ::= '"' <text> '"' | "'" <text> "'" | '"' <character> '"' | "'" <character> "'" 
	 */
	protected void literalDefinition() {
		ChoiceRule textOrSymbolSingle = new ChoiceRule();
		textOrSymbolSingle.add(new PointerRule(true,"text"));
		textOrSymbolSingle.add(new DelegateRule(true,"text",new CharacterRule('\'')));
		textOrSymbolSingle.add(new DelegateRule(true,"text",new CharacterRule('<')));
		textOrSymbolSingle.add(new DelegateRule(true,"text",new CharacterRule('>')));
		ChoiceRule textOrSymbolDouble = new ChoiceRule();
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


		ChoiceRule literal = new ChoiceRule();
		literal.add(doubleQuote);
		literal.add(singleQuote);
		put("literal", literal);
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
		
		put("EOL", eol);
	}
	
	/**
	 * <rule-name> ::= <text>
	 */
	protected void ruleNameDefinition() {
		PointerRule ruleName = new PointerRule(false,"text");
		
		put("rule-name", ruleName);
	}
	/**
	 * <text> ::= <character> | <character> <text>
	 */
	protected void textDefinition(){
		ChoiceRule text = new ChoiceRule();
		PointerRule character = new PointerRule(false,"character");
		text.add(character);
		SequenceRule more = new SequenceRule();
		more.add(character);
		more.add(text);
		text.add(more);
		put("text", text);
	}
	
	/**
	 * <character> ::= <upper-case> | <lower-case> | <number> | '-' | '_' | <opt-whitespace>
	 */
	protected void characterDefinition() {
		ChoiceRule character = new ChoiceRule();
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
		put("character",character);
	}


	/**
	 * <lower-case> ::= 'a' | 'b' | 'c' | 'd' | .. | 'z'
	 */
	protected void lowerCaseDefinition() {
		ChoiceRule lowercase = new ChoiceRule();
		for(char i = 'a' ;i <= 'z';i++){
			lowercase.add(new CharacterRule(i));
		}
		lowercase.setGreedy(false);
		put("lower-case", lowercase);
	}

	/**
	 * <upper-case> ::= 'A' | 'B' | 'C' | 'D' | .. | 'Z'
	 */
	protected void upperCaseDefinition() {
		ChoiceRule uppercase = new ChoiceRule();
		for(char i = 'A' ;i <= 'Z';i++){
			uppercase.add(new CharacterRule(i));
		}
		uppercase.setGreedy(false);
		put("upper-case", uppercase);
	}

	/** 
	 * <number> ::= '0' | '1' | '2' | '3' | .. | '9'
	 */
	protected void numberDefinition() {
		ChoiceRule number = new ChoiceRule();
		for(char i = '0' ;i <= '9';i++){
			number.add(new CharacterRule(i));
		}
		number.setGreedy(false);
		put("number", number);
	}
	
	/**
	 * <symbol> ::= '-' | '_' | '"' | "'" | ';' | '<' | '>' | '|' | ':' | '=' | '\';
	 */
	protected void symbolDefinition() {
		ChoiceRule symbol = new ChoiceRule();
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
		put("symbol", symbol);
	}
	

	

}
