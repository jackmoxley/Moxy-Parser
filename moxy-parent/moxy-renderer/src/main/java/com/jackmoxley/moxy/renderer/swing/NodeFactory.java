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
import com.jackmoxley.moxy.rule.functional.list.ChoiceRule;
import com.jackmoxley.moxy.rule.functional.list.SequenceRule;
import com.jackmoxley.moxy.rule.terminating.TerminatingRule;

public class NodeFactory {

	Map<Rule, List<Node<?>>> nodeMap = new HashMap<Rule, List<Node<?>>>();
	RulePanel panel;

	public RulePanel getPanel() {
		return panel;
	}

	public void setPanel(RulePanel panel) {
		this.panel = panel;
	}

	public Node<?> createNode(Rule rule, Node<?> parent) {
		if (rule instanceof TerminatingRule) {
			TerminalNode node = new TerminalNode(rule, parent);
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
		} else if (rule instanceof ChoiceRule) {
			OptionNode node = new OptionNode((ChoiceRule) rule, parent);
			addNode(rule, node);
			node.constructNodes(this);
			return node;
		} else {
			TerminalNode node = new TerminalNode(rule, parent);
			addNode(rule, node);
			return node;
		}

	}

	private void addNode(Rule rule, Node<?> node) {

		node.panel = panel;
		List<Node<?>> nodes = nodeMap.get(rule);
		if (nodes == null) {
			nodes = new ArrayList<Node<?>>();
			nodeMap.put(rule, nodes);
		}
		nodes.add(node);
	}
}
