package com.jackmoxley.moxy.renderer.swing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jackmoxley.moxy.renderer.swing.node.LinkToNode;
import com.jackmoxley.moxy.renderer.swing.node.OptionNode;
import com.jackmoxley.moxy.renderer.swing.node.SequenceNode;
import com.jackmoxley.moxy.renderer.swing.node.TerminalNode;
import com.jackmoxley.moxy.rule.Rule;
import com.jackmoxley.moxy.rule.functional.OptionRule;
import com.jackmoxley.moxy.rule.functional.SequenceRule;
import com.jackmoxley.moxy.rule.terminating.TerminatingRule;

public class NodeFactory {

	Map<Rule, List<Node<?>>> nodeMap = new HashMap<Rule, List<Node<?>>>();

	public Node<?> createNode(Rule rule, Node<?> parent) {
		if (rule instanceof TerminatingRule) {
			TerminalNode node =  new TerminalNode(rule, parent);
			addNode(rule, node);
			return node;
		}
		List<Node<?>> list = nodeMap.get(rule);
		if (list != null && list.size() > 0) {
			Node<?> node = new LinkToNode(nodeMap.get(rule).get(0), parent);
			list.add(node);
			return node;
		} 

		if (rule instanceof SequenceRule) {
			SequenceNode node = new SequenceNode((SequenceRule) rule, parent);
			addNode(rule, node);
			node.constructNodes(this);
			return node;
		} else if (rule instanceof OptionRule) {
			OptionNode node = new OptionNode((OptionRule) rule, parent);
			addNode(rule, node);
			node.constructNodes(this);
			return node;
		} else {
			TerminalNode node = new TerminalNode(rule, parent);
			addNode(rule, node);
			return node;
		}

	}
	
	
	private void addNode(Rule rule, Node<?> node){
		List<Node<?>> nodes = nodeMap.get(rule);
		if(nodes ==  null){
			nodes = new ArrayList<Node<?>>();
			nodeMap.put(rule, nodes);
		}
		nodes.add(node);
	}
}
