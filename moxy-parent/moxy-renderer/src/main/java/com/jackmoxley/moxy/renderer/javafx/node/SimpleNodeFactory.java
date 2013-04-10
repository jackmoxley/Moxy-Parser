package com.jackmoxley.moxy.renderer.javafx.node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.Scene;

import com.jackmoxley.moxy.rule.Rule;
import com.jackmoxley.moxy.rule.functional.list.LogicalListRule;
import com.jackmoxley.moxy.rule.functional.list.SequenceRule;
import com.jackmoxley.moxy.rule.functional.single.OptionalRule;
import com.jackmoxley.moxy.rule.terminating.TerminatingRule;

public class SimpleNodeFactory extends NodeFactory {

	Map<Scene, Map<Rule, List<RuleNode<?>>>> sceneMap = new HashMap<>();

	@Override
	public RuleNode<?> getNodeFor(Scene scene, Rule rule) {

		List<RuleNode<?>> list = getNodes(scene, rule);
		if (rule instanceof TerminatingRule) {
			TerminatingNode node = new TerminatingNode((TerminatingRule) rule);
			list.add(node);
			return node;
		}
		
		if (list.size() > 0) {
			LinkToNode node = new LinkToNode(scene, list.get(0));
			list.add(node);
			return node;
		}

		if (rule instanceof SequenceRule) {
			SequenceNode node = new SequenceNode((SequenceRule) rule);
			list.add(node);
			node.constructNodes(scene);
			return node;
		} else if (rule instanceof LogicalListRule) {
			LogicalListNode node = new LogicalListNode((LogicalListRule) rule);
			list.add(node);
			node.constructNodes(scene);
			return node;
		} else if (rule instanceof OptionalRule) {
			OptionalNode node = new OptionalNode((OptionalRule) rule);
			list.add(node);
			node.constructNode(scene);
			return node;
		} else {
			return null;
		}
	}
	private Map<Rule, List<RuleNode<?>>> getNodeMap(Scene scene){
		Map<Rule, List<RuleNode<?>>> nodeMap = sceneMap.get(scene);
		if(nodeMap == null){
			nodeMap = new HashMap<Rule, List<RuleNode<?>>>();
			sceneMap.put(scene, nodeMap);
		}
		return nodeMap;
	}

	private List<RuleNode<?>> getNodes(Scene scene,Rule rule){
		Map<Rule, List<RuleNode<?>>> nodeMap = getNodeMap(scene);
		List<RuleNode<?>> nodes = nodeMap.get(rule);
		if (nodes == null) {
			nodes = new ArrayList<RuleNode<?>>();
			nodeMap.put(rule, nodes);
		}
		return nodes;
	}
}
