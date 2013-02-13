package com.jackmoxley.moxy.renderer.swing.node;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.font.LineMetrics;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import com.jackmoxley.moxy.renderer.swing.Dimensions;
import com.jackmoxley.moxy.renderer.swing.Node;
import com.jackmoxley.moxy.rule.Rule;
import com.jackmoxley.moxy.rule.functional.SymbolRule;
import com.jackmoxley.moxy.rule.terminating.CharacterRangeRule;
import com.jackmoxley.moxy.rule.terminating.CharacterRule;
import com.jackmoxley.moxy.rule.terminating.EOFRule;
import com.jackmoxley.moxy.rule.terminating.FalseRule;
import com.jackmoxley.moxy.rule.terminating.StringRule;
import com.jackmoxley.moxy.rule.terminating.TrueRule;

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
		} else if (rule instanceof StringRule) {
			sb.append('"');
			sb.append(((StringRule) rule).getValue());
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
