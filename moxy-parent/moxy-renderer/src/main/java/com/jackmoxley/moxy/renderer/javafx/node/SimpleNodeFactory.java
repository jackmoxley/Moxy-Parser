package com.jackmoxley.moxy.renderer.javafx.node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jackmoxley.moxy.grammer.RuleGraph;
import com.jackmoxley.moxy.renderer.javafx.node.functional.LogicalListNode;
import com.jackmoxley.moxy.renderer.javafx.node.functional.MinMaxNode;
import com.jackmoxley.moxy.renderer.javafx.node.functional.NotNode;
import com.jackmoxley.moxy.renderer.javafx.node.functional.OptionalNode;
import com.jackmoxley.moxy.renderer.javafx.node.functional.SequenceNode;
import com.jackmoxley.moxy.renderer.javafx.node.functional.UntilNode;
import com.jackmoxley.moxy.rule.Rule;
import com.jackmoxley.moxy.rule.functional.list.AndRule;
import com.jackmoxley.moxy.rule.functional.list.ListRule;
import com.jackmoxley.moxy.rule.functional.list.LogicalListRule;
import com.jackmoxley.moxy.rule.functional.list.OrRule;
import com.jackmoxley.moxy.rule.functional.list.SequenceAnyOrderRule;
import com.jackmoxley.moxy.rule.functional.list.SequenceRule;
import com.jackmoxley.moxy.rule.functional.list.XOrRule;
import com.jackmoxley.moxy.rule.functional.single.MinMaxRule;
import com.jackmoxley.moxy.rule.functional.single.NotRule;
import com.jackmoxley.moxy.rule.functional.single.OptionalRule;
import com.jackmoxley.moxy.rule.functional.single.UntilRule;
import com.jackmoxley.moxy.rule.functional.symbol.SymbolRule;
import com.jackmoxley.moxy.rule.terminating.TerminatingRule;

public class SimpleNodeFactory extends NodeFactory {

	Map<RuleGraphNode, Map<Rule, List<RuleNode<?>>>> sceneMap = new HashMap<>();

	public RuleGraphNode getGraphFor(RuleGraph graph) {
		RuleGraphNode graphNode = new RuleGraphNode(graph);
		// graphNode.setup();
		return graphNode;
	}

	@Override
	public RuleNode<?> getNodeFor(RuleGraphNode graph, ParentNode parent,
			Rule rule) {

		List<RuleNode<?>> list = getNodes(graph, rule);
		if (rule instanceof TerminatingRule) {
			TerminatingNode node = new TerminatingNode((TerminatingRule) rule,
					parent, graph);
			list.add(node);
			return node;
		}

		if (list.size() > 0) {
			LinkToNode node = new LinkToNode(list.get(0), parent, graph);
			list.add(node);
			return node;
		}

		if (rule instanceof SequenceRule
				|| rule instanceof SequenceAnyOrderRule) {
			SequenceNode node = new SequenceNode((ListRule) rule, parent, graph);
			list.add(node);
			node.constructNodes();
			if (rule instanceof SequenceAnyOrderRule) {
				node.infoTextProperty().set("Any Order");
			}
			return node;
		} else if (rule instanceof LogicalListRule) {
			LogicalListNode node = new LogicalListNode((LogicalListRule) rule,
					parent, graph);
			list.add(node);
			node.constructNodes();

			if (rule instanceof OrRule) {
				node.infoTextProperty().set(
						"Or " + ((LogicalListRule) rule).getType());
			} else if (rule instanceof XOrRule) {
				XOrRule xor = ((XOrRule) rule);
				node.infoTextProperty().set(
						"X"
								+ (xor.getExclusivity() == 1 ? "" : xor
										.getExclusivity()) + "Or "
								+ xor.getType());
			} else if (rule instanceof AndRule) {
				node.infoTextProperty().set(
						"And " + ((LogicalListRule) rule).getType());
			}
			return node;
		} else if (rule instanceof OptionalRule) {
			OptionalNode node = new OptionalNode((OptionalRule) rule, parent,
					graph);
			list.add(node);
			node.constructNode();
			return node;
		} else if (rule instanceof MinMaxRule) {
			if (rule instanceof UntilRule) {
				UntilNode node = new UntilNode((UntilRule) rule, parent,
						graph);
				list.add(node);
				node.constructNode();
				return node;
			} else {
				MinMaxNode<MinMaxRule> node = new MinMaxNode<>((MinMaxRule) rule, parent,
						graph);
				list.add(node);
				node.constructNode();
				return node;
			}
		} else if (rule instanceof SymbolRule) {
			SymbolNode node = new SymbolNode((SymbolRule) rule, parent, graph);
			list.add(node);
			return node;
		} else if (rule instanceof NotRule) {
			NotNode node = new NotNode((NotRule) rule, parent, graph);
			list.add(node);
			node.constructNode();
			return node;
		} else {

			return new BoxNode<Rule>(rule, parent, graph) {

				@Override
				protected String generateText() {
					return "Unimplemented: " + rule.getClass().getSimpleName();
				}
			};
		}
	}

	private Map<Rule, List<RuleNode<?>>> getNodeMap(RuleGraphNode graph) {
		Map<Rule, List<RuleNode<?>>> nodeMap = sceneMap.get(graph);
		if (nodeMap == null) {
			nodeMap = new HashMap<Rule, List<RuleNode<?>>>();
			sceneMap.put(graph, nodeMap);
		}
		return nodeMap;
	}

	private List<RuleNode<?>> getNodes(RuleGraphNode graph, Rule rule) {
		Map<Rule, List<RuleNode<?>>> nodeMap = getNodeMap(graph);
		List<RuleNode<?>> nodes = nodeMap.get(rule);
		if (nodes == null) {
			nodes = new ArrayList<RuleNode<?>>();
			nodeMap.put(rule, nodes);
		}
		return nodes;
	}
}
