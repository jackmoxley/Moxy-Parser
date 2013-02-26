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

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Scrollable;

import com.jackmoxley.moxy.grammer.RuleGraph;
import com.jackmoxley.moxy.rule.Rule;

public class ScrolledPanel extends JPanel implements Scrollable {

	private static final long serialVersionUID = -5327721190346341105L;
	
	private JScrollPane scrollPane;
	Map<Rule, Dimension> dimensions = new LinkedHashMap<Rule,Dimension>();
	Map<String, Rule> rules;
	
	public ScrolledPanel(Collection<RuleGraph> ruleGraphs) {
		super();
		this.rules = new HashMap<String, Rule>();
		for(RuleGraph tree: ruleGraphs){
			rules.put(tree.getName(),tree.getRule());
		}
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); 
		this.setDoubleBuffered(true);
		this.updateUI();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		updateScrollPane();
	}
	
	public void scrollToComponent(Component comp){
		System.out.println("scrollToComponent "+comp.getBounds());
		this.scrollRectToVisible(comp.getBounds());
	}
	

	public void scrollToRule(String ruleName){
		Rule rule = rules.get(ruleName);
		scrollToRule(rule);
	}
	
	public void scrollToRule(Rule rule){
		for(Component component : this.getComponents()){
			if(component instanceof RulePanel){
				if(rule == ((RulePanel)component).rule) {
					System.out.println("Scroll to "+rule);
					scrollToComponent(component);
					updateScrollPane();
					return ;
				}
			}
		}
	}

	// calls repaint on the scrollPane instance
	private void updateScrollPane() {
		if (scrollPane != null) {
			scrollPane.repaint();
		}
	}

	void setScrollPane(JScrollPane scrollPane) {
		if (scrollPane != null) {
			scrollPane.setViewportView(this);
		}
		this.scrollPane = scrollPane;
	}

	public void updatePreferedSize(RulePanel rulePanel, Dimension preferredSize) {
		dimensions.put(rulePanel.rule, preferredSize);
		int width = 0;
		int height = 0;
		for(Dimension key : dimensions.values()){
			width = Math.max(width, key.width);
			height += key.height;
		}
		this.setPreferredSize(new Dimension(width, height));
		updateScrollPane();
	}

	@Override
	public Dimension getPreferredScrollableViewportSize() {
		// TODO Auto-generated method stub
		return this.getComponent(0).getPreferredSize();
	}

	@Override
	public int getScrollableUnitIncrement(Rectangle visibleRect,
			int orientation, int direction) {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public int getScrollableBlockIncrement(Rectangle visibleRect,
			int orientation, int direction) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean getScrollableTracksViewportWidth() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getScrollableTracksViewportHeight() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
}