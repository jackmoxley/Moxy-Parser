package com.jackmoxley.moxy.rule;

import java.util.ArrayList;
import java.util.List;

import com.jackmoxley.moxy.token.Token;

public class RuleDecision {

	private DecisionState state = DecisionState.Unconsidered;
	private List<Token> tokens = null;
	private int nextIndex;
	private final int startIndex;
	private String failure = "";
	private static final Object[] NO_ARGS = new Object[0];
	private Object[] arguments = NO_ARGS;

	public RuleDecision(int startIndex) {
		super();
		this.startIndex = startIndex;
		this.nextIndex = startIndex;
	}
	
	public void add(RuleDecision subDecision){
		nextIndex = subDecision.getNextIndex();

		getTokens().addAll(subDecision.getTokens());
	}
	
	public void add(Token token, int nextIndex){
		this.nextIndex = nextIndex;
		getTokens().add(token);
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

	public DecisionState getState() {
		return state;
	}

	public void setState(DecisionState state) {
		this.state = state;
	}

	public List<Token> getTokens() {
		if(tokens == null){
			tokens = new ArrayList<Token>();
		}
		return tokens;
	}

	public void setTokens(List<Token> tokens) {
		this.tokens = tokens;
	}

	public boolean hasPassed() {
		return DecisionState.Passed.equals(state);
	}

	public boolean hasFailed() {
		return DecisionState.Failed.equals(state);
	}

	public boolean isUnconsidered() {
		return DecisionState.Unconsidered.equals(state);
	}

	public boolean isConsidering() {
		return DecisionState.Considering.equals(state);
	}
	public void passed() {
		state = DecisionState.Passed;
		failure = "";
		arguments = NO_ARGS;
//		logger.debug("Passed");
	}

	public void failed(String failure, Object... arguments) {
		state = DecisionState.Failed;
//		logger.debug(failure,arguments);
		this.failure = failure;
		this.arguments = arguments;
	}

	public void unconsidered() {
		state = DecisionState.Unconsidered;
	}

	public void considering() {
		state = DecisionState.Considering;
	}



	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RuleDecision [state=").append(state)
				.append(", tokens=").append(tokens).append(", nextIndex=")
				.append(nextIndex).append(", startIndex=").append(startIndex)
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