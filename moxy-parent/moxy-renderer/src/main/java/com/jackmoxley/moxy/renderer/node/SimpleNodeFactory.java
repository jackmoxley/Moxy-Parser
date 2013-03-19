package com.jackmoxley.moxy.renderer.node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jackmoxley.moxy.renderer.swing.node.TerminalNode;
import com.jackmoxley.moxy.rule.Rule;
import com.jackmoxley.moxy.rule.functional.list.LogicalListRule;
import com.jackmoxley.moxy.rule.functional.list.SequenceRule;
import com.jackmoxley.moxy.rule.functional.single.OptionalRule;
import com.jackmoxley.moxy.rule.terminating.TerminatingRule;

public class SimpleNodeFactory extends NodeFactory {

	Map<Rule, List<Node<?>>> nodeMap = new HashMap<Rule, List<Node<?>>>();

	@Override
	public Node<?> getNodeFor(FunctionalNode<?> parent, Rule rule) {
		if (rule instanceof TerminatingRule) {
			TerminatingNode node = new TerminatingNode(parent,
					(TerminatingRule) rule);
			addNode(rule, node);
			return node;
		}
		List<Node<?>> list = nodeMap.get(rule);
		if (list != null && list.size() > 0) {
			Node<?> node = new LinkToNode(parent, nodeMap.get(rule).get(0));
			list.add(node);
			return node;
		}

		if (rule instanceof SequenceRule) {
			SequenceNode node = new SequenceNode(parent, (SequenceRule) rule);
			addNode(rule, node);
			node.constructNodes();
			return node;
		} else if (rule instanceof LogicalListRule) {
			LogicalListNode node = new LogicalListNode(parent,
					(LogicalListRule) rule);
			addNode(rule, node);
			node.constructNodes();
			return node;
		} else if (rule instanceof OptionalRule) {
			OptionalNode node = new OptionalNode(parent, (OptionalRule) rule);
			addNode(rule, node);
			node.constructNodes();
			return node;
		} else {
			return null;
		}
	}

	private void addNode(Rule rule, Node<?> node) {

		List<Node<?>> nodes = nodeMap.get(rule);
		if (nodes == null) {
			nodes = new ArrayList<Node<?>>();
			nodeMap.put(rule, nodes);
		}
		nodes.add(node);
	}
}
