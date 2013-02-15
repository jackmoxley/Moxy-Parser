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
package com.moxley.moxy;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import com.jackmoxley.moxy.grammer.RealizedRuledGrammer;
import com.jackmoxley.moxy.grammer.RuledGrammer;
import com.jackmoxley.moxy.grammer.bnf.HandCodedBNFGrammer;
import com.jackmoxley.moxy.realizer.XMLRealizer;
import com.jackmoxley.moxy.rule.optimizer.CharacterRangeRuleOptimizer;
import com.jackmoxley.moxy.rule.optimizer.OptimizerEngine;
import com.jackmoxley.moxy.rule.optimizer.OptionRuleOptimizer;
import com.jackmoxley.moxy.rule.optimizer.SequenceRuleOptimizer;
import com.jackmoxley.moxy.rule.optimizer.StringRuleOptimizer;
import com.jackmoxley.moxy.rule.optimizer.SymbolRuleOptimizer;
import com.jackmoxley.moxy.token.SymbolToken;
import com.jackmoxley.moxy.token.Token;
import com.jackmoxley.moxy.token.stream.CharacterTokenStream;

public class RuleVisitorTest {

	public static void main(String... args) throws Exception {
		if (true) {
			basicTest();
		} else {
			performanceTest();
		}

	}

	public static RuledGrammer /* List<RealizedHolder<? extends Rule>> */basicTest()
			throws Exception {
		Reader file = new InputStreamReader(RuleVisitorTest.class
				.getClassLoader().getResourceAsStream("BNFGrammer.mg"));
		CharacterTokenStream stream = new CharacterTokenStream(file);

		OptimizerEngine engine = new OptimizerEngine(new OptionRuleOptimizer(),
				new SequenceRuleOptimizer(), new SymbolRuleOptimizer(),
				new StringRuleOptimizer(), new CharacterRangeRuleOptimizer());

		HandCodedBNFGrammer grammer = new HandCodedBNFGrammer();
		int optimized = engine.optimize(grammer);
		System.out.println(grammer);
		List<Token> tokens = grammer.parse(stream);
		int heirachy = 0;
		printSymbols(heirachy, tokens);
		System.out.println("Rules Optimized=" + optimized);
		XMLRealizer<RealizedRuledGrammer> realizer = new XMLRealizer<RealizedRuledGrammer>(
				HandCodedBNFGrammer.class
						.getResourceAsStream("BNFRuleRealizer.xml"));
		List<RealizedRuledGrammer> result = realizer.realize(tokens);
		System.out.println("Realized " + result);
		RealizedRuledGrammer newGrammer = result.get(0);
		engine.optimize(newGrammer);
		return newGrammer;
		// return grammer;
	}

	public static void performanceTest() throws IOException,
			InterruptedException {
		HandCodedBNFGrammer grammer = new HandCodedBNFGrammer();
		Reader file = new InputStreamReader(RuleVisitorTest.class
				.getClassLoader().getResourceAsStream("BNFGrammer.mg"));
		CharacterTokenStream stream = new CharacterTokenStream(file);
		List<Token> tokens = grammer.parse(stream);
		int heirachy = 0;
		printSymbols(heirachy, tokens);

		System.out.println(grammer);
		OptimizerEngine engine = new OptimizerEngine(new OptionRuleOptimizer(),
				new SequenceRuleOptimizer(), new SymbolRuleOptimizer(),
				new StringRuleOptimizer(), new CharacterRangeRuleOptimizer());

		HandCodedBNFGrammer grammer2 = new HandCodedBNFGrammer();
		int optimized = engine.optimize(grammer2);
		System.out.println(grammer2);
		tokens = grammer2.parse(stream);
		heirachy = 0;
		printSymbols(heirachy, tokens);
		System.out.println("Rules Optimized=" + optimized);

		int ramp = 20;
		long time = 0;
		Runtime runtime = Runtime.getRuntime();

		System.gc();
		Thread.sleep(2000l);
		long startMemory = runtime.totalMemory() - runtime.freeMemory();
		long currentMemory = startMemory;
		System.out.println((currentMemory / 1024) + "kb");
		for (int r = ramp; r <= 100; r += ramp) {

			time = System.nanoTime();
			for (int i = 0; i < r; i++) {

				tokens = grammer.parse(stream);
			}
			time = System.nanoTime() - time;
			currentMemory = (runtime.totalMemory() - runtime.freeMemory())
					- startMemory;
			System.gc();
			Thread.sleep(1000l);
			System.out.println(r + " Unoptimized " + formatNanos(time) + "s "
					+ (currentMemory / 1024) + "kb");

			time = System.nanoTime();
			for (int i = 0; i < (r); i++) {

				tokens = grammer2.parse(stream);
			}
			time = System.nanoTime() - time;
			currentMemory = (runtime.totalMemory() - runtime.freeMemory())
					- startMemory;
			System.gc();
			Thread.sleep(1000l);
			System.out.println(r + " Optimized " + formatNanos(time) + "s "
					+ (currentMemory / 1024) + "kb");

		}
		heirachy = 0;
		printSymbols(heirachy, tokens);

	}

	private static String formatNanos(long nanos) {
		String toReturn = "" + nanos;
		while (toReturn.length() < 10) {
			toReturn = "0" + toReturn;
		}
		toReturn = toReturn.substring(0, toReturn.length() - 9) + "."
				+ toReturn.substring(toReturn.length() - 9);
		while (toReturn.charAt(toReturn.length() - 1) == '0') {
			toReturn = toReturn.substring(0, toReturn.length() - 1);
		}
		return toReturn;
	}

	private static void printSymbols(int heirachy, List<Token> tokens) {
		for (Token token : tokens) {
			if (token instanceof SymbolToken) {
				SymbolToken symbol = ((SymbolToken) token);
				System.out.println(heirachy + " " + symbol.getSymbol() + " "
						+ symbol.getValue());
				printSymbols(heirachy + 1, symbol);
			}
		}
	}

}
