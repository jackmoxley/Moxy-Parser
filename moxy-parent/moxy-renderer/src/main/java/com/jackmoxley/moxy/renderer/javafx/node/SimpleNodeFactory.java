package com.jackmoxley.moxy.renderer.javafx.node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.Scene;

import com.jackmoxley.moxy.renderer.javafx.node.functional.LogicalListNode;
import com.jackmoxley.moxy.renderer.javafx.node.functional.OptionalNode;
import com.jackmoxley.moxy.renderer.javafx.node.functional.SequenceNode;
import com.jackmoxley.moxy.rule.Rule;
import com.jackmoxley.moxy.rule.functional.list.AndRule;
import com.jackmoxley.moxy.rule.functional.list.ListRule;
import com.jackmoxley.moxy.rule.functional.list.LogicalListRule;
import com.jackmoxley.moxy.rule.functional.list.OrRule;
import com.jackmoxley.moxy.rule.functional.list.SequenceAnyOrderRule;
import com.jackmoxley.moxy.rule.functional.list.SequenceRule;
import com.jackmoxley.moxy.rule.functional.list.XOrRule;
import com.jackmoxley.moxy.rule.functional.single.OptionalRule;
import com.jackmoxley.moxy.rule.functional.symbol.SymbolRule;
import com.jackmoxley.moxy.rule.terminating.TerminatingRule;

public class SimpleNodeFactory extends NodeFactory {

	Map<Scene, Map<Rule, List<RuleNode<?>>>> sceneMap = new HashMap<>();

	@Override
	public RuleNode<?> getNodeFor(Scene scene, ParentNode parent, Rule rule) {

		List<RuleNode<?>> list = getNodes(scene, rule);
		if (rule instanceof TerminatingRule) {
			TerminatingNode node = new TerminatingNode((TerminatingRule) rule,parent);
			list.add(node);
			return node;
		}
		
		if (list.size() > 0) {
			LinkToNode node = new LinkToNode(scene, parent, list.get(0));
			list.add(node);
			return node;
		}

		if (rule instanceof SequenceRule || rule instanceof SequenceAnyOrderRule) {
			SequenceNode node = new SequenceNode((ListRule) rule,parent);
			list.add(node);
			node.constructNodes(scene);
			if(rule instanceof SequenceAnyOrderRule){
				node.infoTextProperty().set("Any Order");
			}
			return node;
		} else if (rule instanceof LogicalListRule) {
			LogicalListNode node = new LogicalListNode((LogicalListRule) rule,parent);
			list.add(node);
			node.constructNodes(scene);

			if(rule instanceof OrRule){
				node.infoTextProperty().set("Or "+((LogicalListRule) rule).getType());
			} else if(rule instanceof XOrRule){
				XOrRule xor = ((XOrRule) rule);
				node.infoTextProperty().set("X"+(xor.getExclusivity() == 1 ? "" : xor.getExclusivity())+"Or "+xor.getType());
			} else if(rule instanceof AndRule){
				node.infoTextProperty().set("And "+((LogicalListRule) rule).getType());
			}
			return node;
		} else if (rule instanceof OptionalRule) {
			OptionalNode node = new OptionalNode((OptionalRule) rule,parent);
			list.add(node);
			node.constructNode(scene);
			return node;
		} else if (rule instanceof SymbolRule) {
			SymbolNode node = new SymbolNode((SymbolRule) rule,parent);
			list.add(node);
			return node;
		}  else {
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
