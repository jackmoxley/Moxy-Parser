package com.jackmoxley.moxy.renderer.swing;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Scrollable;

import com.jackmoxley.moxy.rule.Rule;

public class ScrolledPanel extends JPanel implements Scrollable {

	private static final long serialVersionUID = -5327721190346341105L;
	
	private JScrollPane scrollPane;
	Map<Rule, Dimension> dimensions = new LinkedHashMap<Rule,Dimension>();

	public ScrolledPanel() {
		super();
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
		scrollPane.scrollRectToVisible(comp.getBounds());
	}
	

	
	public void scrollToRule(Rule rule){
		for(Component component : this.getComponents()){
			if(component instanceof RulePanel){
				if(rule == ((RulePanel)component).rule) {
					scrollToComponent(component);
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