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
package com.jackmoxley.moxy.parser;

import java.util.ArrayList;
import java.util.List;

import com.jackmoxley.meta.Beta;
import com.jackmoxley.moxy.token.Token;

@Beta
public class RuleDecision {

	public enum State {
		Passed, Failed, Considering, Unconsidered
	}

	private static final Object[] NO_ARGS = new Object[0];
	private static RuleDecision cyclicFail = new RuleDecision(0);
	static {
		cyclicFail.failed("cyclic", NO_ARGS);
	}

	private State state = State.Unconsidered;
	private List<Token> tokens = null;
	private int nextIndex;
	private final int startIndex;
	private String failure = "";
	private Object[] arguments = NO_ARGS;

	public RuleDecision(int startIndex) {
		super();
		this.startIndex = startIndex;
		this.nextIndex = startIndex;
	}

	public RuleDecision(RuleDecision subDecision) {
		super();
		this.startIndex = subDecision.startIndex;
		this.nextIndex = subDecision.nextIndex;

		getTokens().addAll(subDecision.getTokens());
	}

	public static RuleDecision cyclic() {
		return cyclicFail;
	}

	public void add(RuleDecision subDecision) {
		nextIndex = subDecision.getNextIndex();

		getTokens().addAll(subDecision.getTokens());
	}

	public void add(Token token, int nextIndex) {
		this.nextIndex = nextIndex;
		getTokens().add(token);
	}

	public void addAll(List<? extends Token> tokens, int nextIndex) {
		this.nextIndex = nextIndex;
		getTokens().addAll(tokens);
	}

	public int getStartIndex() {
		return startIndex;
	}

	public int getNextIndex() {
		return nextIndex;
	}

	public void setNextIndex(int nextIndex) {
		this.nextIndex = nextIndex;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public List<Token> getTokens() {
		if (tokens == null) {
			tokens = new ArrayList<Token>();
		}
		return tokens;
	}

	public void setTokens(List<Token> tokens) {
		this.tokens = tokens;
	}

	public boolean hasPassed() {
		return State.Passed.equals(state);
	}

	public boolean hasFailed() {
		return State.Failed.equals(state);
	}

	public boolean isUnconsidered() {
		return State.Unconsidered.equals(state);
	}

	public boolean isConsidering() {
		return State.Considering.equals(state);
	}

	public void passed() {
		state = State.Passed;
		failure = "";
		arguments = NO_ARGS;
	}

	public void failed(String failure, Object... arguments) {
		state = State.Failed;
		this.failure = failure;
		this.arguments = arguments;
	}

	public void unconsidered() {
		state = State.Unconsidered;
	}

	public void considering() {
		state = State.Considering;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RuleDecision [state=").append(state)
				.append(", tokens=").append(tokens).append(", startIndex=")
				.append(startIndex).append(", nextIndex=").append(nextIndex)
				.append("]");
		return builder.toString();
	}

	public String getFailure() {
		return failure;
	}

	public Object[] getArguments() {
		return arguments;
	}

}