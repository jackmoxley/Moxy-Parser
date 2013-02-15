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

import java.awt.Dimension;
import java.awt.HeadlessException;
import java.util.Collections;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import com.jackmoxley.moxy.grammer.Grammer;
import com.jackmoxley.moxy.rule.Rule;

public class RuleViewerFrame extends JFrame {

	ScrolledPanel parent;
	boolean odd = true;

	/**
	 * 
	 */
	private static final long serialVersionUID = 8734984858965275917L;

	public RuleViewerFrame(Map<String, Rule> rules) throws HeadlessException {
		this(rules, Collections.<String, String>emptyMap());

	}
	
	public RuleViewerFrame(Map<String, Rule> rules, Map<String, String> syntaxes) throws HeadlessException {
		super("RuleViewFrame: " + rules.size());
		this.setSize(new Dimension(640, 480));

		parent = new ScrolledPanel(rules);
        
		for (Map.Entry<String, Rule> rule : rules.entrySet()) {
			addRule(rule.getValue(),rule.getKey(),syntaxes.get(rule.getKey()));
		}

		JScrollPane scrollPane = new JScrollPane();
		parent.setScrollPane(scrollPane);
		this.getContentPane().add(scrollPane);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
	
	
	public RuleViewerFrame(Grammer rules) {
		this(rules.getRuleMap(),rules.getSyntaxMap());
	}
	
//	private static Map<String, Rule> getBestMap(Grammer rules){
//		if(rules instanceof RealizedRuledGrammer){
//			RealizedRuledGrammer rrules = (RealizedRuledGrammer)rules;
//			Map<String,Rule> map = new LinkedHashMap<String,Rule>();
//			for(String ruleName: rrules.getRuleMap().keySet()) {
//				map.put(rrules.getTextualSyntax(ruleName), rrules.getRule(ruleName));
//			}
//			return map;
//		} else {
//			return rules.getRuleMap();
//		}
//		
//	}
	

	public void addRule(Rule rule, String name, String syntax) {

		RulePanel panel = new RulePanel(rule, parent, name, syntax);
		panel.setPreferredSize(new Dimension(640, 480));
		panel.setOdd(odd);
		parent.add(panel);
		odd = !odd;
	}
}
