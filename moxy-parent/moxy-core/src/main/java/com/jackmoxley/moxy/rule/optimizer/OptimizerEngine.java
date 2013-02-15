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
package com.jackmoxley.moxy.rule.optimizer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jackmoxley.moxy.grammer.Grammer;
import com.jackmoxley.moxy.rule.Rule;
import com.jackmoxley.moxy.rule.RuleDecision;
import com.jackmoxley.moxy.rule.RuleEvaluator;
import com.jackmoxley.moxy.rule.functional.FunctionalRule;
import com.jackmoxley.moxy.rule.functional.RuleList;

public class OptimizerEngine {

	public enum OptimizerState {
		Unconsidered, Considered
	}

	public class OptimizerDecision {
		OptimizerState state = OptimizerState.Unconsidered;

		public OptimizerState getState() {
			return state;
		}

		public void setState(OptimizerState state) {
			this.state = state;
		}

	}

	private List<Optimizer> optimizers = new ArrayList<Optimizer>();
	private Map<Rule, OptimizerDecision> optimizerStates = new HashMap<Rule, OptimizerDecision>();

	public OptimizerEngine() {
		super();
	}

	public OptimizerEngine(Optimizer... optimizers) {
		super();
		add(optimizers);
	}

	protected OptimizerDecision getOptimizerDecision(Rule rule) {
		OptimizerDecision decision = optimizerStates.get(rule);
		if (decision == null) {
			decision = new OptimizerDecision();
			optimizerStates.put(rule, decision);
		}
		return decision;
	}

	public int optimize(final Grammer grammer) {
		int totalRulesOptimized = 0;

		RuleList root = new RuleList() {
			private static final long serialVersionUID = 1L;

			@Override
			public void consider(RuleEvaluator visitor, RuleDecision decision) {
			}

		};
		root.addAll(grammer.getRuleMap().values());
		int rulesOptimized = 0;
		do {
			rulesOptimized = optimize(grammer, root);
			totalRulesOptimized += rulesOptimized;
			optimizerStates.clear();
		} while (rulesOptimized != 0);
		return totalRulesOptimized;
	}

	public int optimize(final Grammer grammer, final FunctionalRule parent) {
		OptimizerDecision decision = getOptimizerDecision(parent);
		if (decision.getState() != OptimizerState.Unconsidered) {
			return 0;
		}
		int rulesOptimized = 0;
		for (Optimizer optimizer : optimizers) {
			rulesOptimized += optimizer.visitRule(grammer, parent);
		}
		decision.setState(OptimizerState.Considered);
		for (Rule innerRule : parent) {
			if (innerRule instanceof FunctionalRule) {
				rulesOptimized += optimize(grammer, (FunctionalRule) innerRule);
			}
		}
		return rulesOptimized;
	}

	public void add(Optimizer... optimizers) {
		for (Optimizer optimizer : optimizers) {
			this.optimizers.add(optimizer);
		}
	}
}
