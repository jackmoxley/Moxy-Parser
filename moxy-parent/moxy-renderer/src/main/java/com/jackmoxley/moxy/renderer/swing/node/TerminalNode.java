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
package com.jackmoxley.moxy.renderer.swing.node;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.font.LineMetrics;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import com.jackmoxley.moxy.renderer.swing.Dimensions;
import com.jackmoxley.moxy.renderer.swing.Node;
import com.jackmoxley.moxy.renderer.swing.ScrolledPanel;
import com.jackmoxley.moxy.rule.Rule;
import com.jackmoxley.moxy.rule.functional.symbol.SymbolRule;
import com.jackmoxley.moxy.rule.terminating.EOFRule;
import com.jackmoxley.moxy.rule.terminating.FalseRule;
import com.jackmoxley.moxy.rule.terminating.TrueRule;
import com.jackmoxley.moxy.rule.terminating.text.CharacterRangeRule;
import com.jackmoxley.moxy.rule.terminating.text.CharacterRule;
import com.jackmoxley.moxy.rule.terminating.text.TextRule;

public class TerminalNode extends Node<Rule> {

	public TerminalNode(Rule rule, Node<?> parent) {
		super(rule, parent);
	}

	@Override
	public Dimensions calculateDimensions(Graphics2D g, Point2D stubLeft) {

		Rectangle2D valueBounds = g.getFontMetrics().getStringBounds(
				getValue(), g);
		double w = valueBounds.getWidth() + DOUBLE_MARGIN;
		double h = valueBounds.getHeight() + DOUBLE_MARGIN;
		Point2D size = new Point2D.Double(w, h);
		double localStubY = h / 2;
		Point2D stubRight = new Point2D.Double(stubLeft.getX() + w,
				stubLeft.getY());
		Rectangle2D bounds = new Rectangle2D.Double(stubLeft.getX(),
				stubLeft.getY() - localStubY, w, h);

		return new Dimensions(size, bounds, stubLeft, stubRight, localStubY);
	}

	@Override
	public void mouseClick() {
		super.mouseClick();
		if(mouseClick && rule instanceof SymbolRule){
			SymbolRule sRule = (SymbolRule)rule;
			System.out.println("GOTO "+sRule.getPointer());		
			ScrolledPanel scroll = panel.parent;
			scroll.scrollToRule(sRule.getPointer());
		}
	}

	@Override
	public void render(Graphics2D g) {
		LineMetrics metrics = g.getFontMetrics().getLineMetrics(getValue(), g);
		float ascent = metrics.getAscent();

		if (mouseOver) {
			g.setColor(fg);
		} else {
			g.setColor(bgTerm);
		}
		g.fill(round(dim.bounds, MARGIN, MARGIN));

		if (mouseOver) {
			g.setColor(bgTerm);
		} else {
			g.setColor(fg);
		}
		g.draw(round(dim.bounds, MARGIN, MARGIN));
		g.drawString(getValue(), (float) (dim.stubLeft.getX() + MARGIN),
				(float) (dim.stubLeft.getY() + (ascent / 2)));
		g.setColor(Color.BLACK);

	}

	protected String getValue() {
		StringBuilder sb = new StringBuilder();
		if(rule == null){
			return "null";
		}
		if (rule instanceof CharacterRule) {
			sb.append("'");
			sb.append(((CharacterRule) rule).getCharacter());
			sb.append("'");
		} else if (rule instanceof TextRule) {
			sb.append('"');
			sb.append(((TextRule) rule).getValue());
			sb.append('"');
		} else if (rule instanceof CharacterRangeRule) {
			sb.append("'");
			sb.append(((CharacterRangeRule) rule).getStart());
			sb.append("' ... '");
			sb.append(((CharacterRangeRule) rule).getEnd());
			sb.append("'");
		} else if (rule instanceof TrueRule) {
			sb.append("True");
		} else if (rule instanceof FalseRule) {
			sb.append("False");
		} else if (rule instanceof EOFRule) {
			sb.append("EOF");
		} else if (rule instanceof SymbolRule) {
			sb.append("* ");
			sb.append(((SymbolRule)rule).getPointer());
			sb.append(" *");
		} else {
			sb.append(rule.toString());
		}
		return sb.toString();
	}

}
