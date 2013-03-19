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

import com.jackmoxley.moxy.renderer.common.style.Style;
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

	private static float char_hue = 0.25f;
	private static float pointer_hue = 0.10f;

	public static final Style CHARACTER_STYLE = BASE_STYLE.withHue(char_hue).withCornerRadius(DOUBLE_MARGIN);

	public static final Style CHARACTER_HIGHLIGHT = BASE_HIGHLIGHT.withHue(char_hue).withCornerRadius(DOUBLE_MARGIN);

	public static final Style SYMBOL_STYLE = BASE_STYLE.withHue(pointer_hue);

	public static final Style SYMBOL_HIGHLIGHT = BASE_HIGHLIGHT.withHue(pointer_hue);

	private String value;

	public TerminalNode(Rule rule, Node<?> parent) {
		super(rule, parent);
		generateStyleAndValue();
	}

	@Override
	public Dimensions calculateDimensions(Graphics2D g, Point2D stubLeft) {
		Rectangle2D valueBounds = g.getFontMetrics().getStringBounds(value, g);
		double w = valueBounds.getWidth() + (getStyle().margin * 2);
		double h = valueBounds.getHeight() + (getStyle().margin * 2);
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
		if (mouseClick && rule instanceof SymbolRule) {
			SymbolRule sRule = (SymbolRule) rule;
			System.out.println("GOTO " + sRule.getPointer());
			ScrolledPanel scroll = panel.parent;
			scroll.scrollToRule(sRule.getPointer());
		}
	}

	@Override
	public void render(Graphics2D g) {
		LineMetrics metrics = g.getFontMetrics().getLineMetrics(value, g);
		float ascent = metrics.getAscent();

		Rectangle2D shadow = new Rectangle2D.Double(dim.bounds.getX()+5, dim.bounds.getY()+5, dim.bounds.getWidth(), dim.bounds.getHeight());

		g.setColor(getStyle().shadowStyle.color);
		g.fill(round(shadow, getStyle().polygonStyle.cornerRadius.x,
				getStyle().polygonStyle.cornerRadius.y));
		
		g.setColor(getStyle().polygonStyle.color);
		g.fill(round(dim.bounds, getStyle().polygonStyle.cornerRadius.x,
				getStyle().polygonStyle.cornerRadius.y));
		g.setColor(getStyle().polygonStyle.lineStyle.color);
		g.draw(round(dim.bounds, getStyle().polygonStyle.lineStyle.cornerRadius.x,
				getStyle().polygonStyle.lineStyle.cornerRadius.y));
		g.setColor(getStyle().textStyle.color);
		g.setFont(getStyle().textStyle.font);
		g.drawString(value, (float) (dim.stubLeft.getX() + getStyle().margin),
				(float) (dim.stubLeft.getY() + (ascent / 2)));
		g.setColor(Color.BLACK);

	}

	protected void generateStyleAndValue() {
		StringBuilder sb = new StringBuilder();
		if (rule == null) {
			value = "null";
		} else if (rule instanceof CharacterRule) {
			style = CHARACTER_STYLE;
			highlight = CHARACTER_HIGHLIGHT;
			sb.append(((CharacterRule) rule).getCharacter());
		} else if (rule instanceof TextRule) {
			style = CHARACTER_STYLE;
			highlight = CHARACTER_HIGHLIGHT;
			sb.append(((TextRule) rule).getValue());
		} else if (rule instanceof CharacterRangeRule) {
			sb.append(((CharacterRangeRule) rule).getStart());
			sb.append(" ... ");
			sb.append(((CharacterRangeRule) rule).getEnd());
		} else if (rule instanceof TrueRule) {
			sb.append("True");
		} else if (rule instanceof FalseRule) {
			sb.append("False");
		} else if (rule instanceof EOFRule) {
			sb.append("EOF");
		} else if (rule instanceof SymbolRule) {
			style = SYMBOL_STYLE;
			highlight = SYMBOL_HIGHLIGHT;
			sb.append(((SymbolRule) rule).getPointer());
		} else {
			sb.append(rule.toString());
		}
		value = sb.toString();
	}

}
