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
package com.jackmoxley.moxy.renderer.node;

import static com.jackmoxley.moxy.renderer.common.dimensions.Vertex.v;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import com.jackmoxley.moxy.renderer.common.dimensions.Vertex;
import com.jackmoxley.moxy.renderer.common.renderable.Line;
import com.jackmoxley.moxy.renderer.common.renderable.Spatial;
import com.jackmoxley.moxy.renderer.common.style.Style;
import com.jackmoxley.moxy.rule.functional.list.AndRule;
import com.jackmoxley.moxy.rule.functional.list.LogicalListRule;
import com.jackmoxley.moxy.rule.functional.list.LogicalListRule.Type;
import com.jackmoxley.moxy.rule.functional.list.OrRule;
import com.jackmoxley.moxy.rule.functional.list.XOrRule;

public class LogicalListNode extends FunctionalNode<LogicalListRule> {

	List<Line> linesIn = new ArrayList<Line>();
	List<Line> linesOut = new ArrayList<Line>();

	public LogicalListNode(Spatial parent, LogicalListRule rule) {
		super(parent, rule);
		for (int i = 0; i < rule.size(); i++) {
			Line in = new Line(this,v(0, 0),v(0, 0),v(0, 0),v(0, 0));
			Line out = new Line(this,v(0, 0),v(0, 0),v(0, 0),v(0, 0));
			linesIn.add(in);
			linesOut.add(out);
			this.add(in);
			this.add(out);
		}
		prepareTextInputs();
	}
	
	protected void prepareTextInputs(){
		Type type = rule.getType();
		StringBuilder sb = new StringBuilder();
		if(rule instanceof OrRule){
			sb.append("OR [");
		} else if(rule instanceof XOrRule){
			sb.append("XOR [max=").append(((XOrRule)rule).getExclusivity()).append(", ");
		}else if(rule instanceof AndRule){
			sb.append("AND [");
		}
		sb.append("type=").append(type).append("]");
		setInText(sb.toString());
	}


	@Override
	public void prepareNode(Graphics2D g) {
		Style style = StyleManager.currentStyle();
		double cornerRadiusX = style.lineStyle.cornerRadius.x;
		double margin = style.margin;
		final double doubleRadius = cornerRadiusX * 2;

		double totalHeight = margin;
		double maxWidth = 0;
		for (Node<?> node : nodes) {
			node.prepare(g);
			totalHeight += node.size().y + margin;
			maxWidth = Math.max(maxWidth, node.size().x);
		}

		double halfOffset = totalHeight/2;
		final Vertex left = new Vertex(0,halfOffset);
		final Vertex leftMiddle = left.addX(cornerRadiusX);
		final Vertex rightMiddle = leftMiddle.addX((cornerRadiusX*2) + maxWidth);
		final Vertex right = rightMiddle.addX(cornerRadiusX);
		double startY = margin ;
		int count = 0;
		for (Node<?> node : nodes) {
			if(count == 0){
				startY += node.yOffset();
			}
			Vertex nodeSize = node.size();
			double startX = doubleRadius
					+ ((maxWidth - nodeSize.x) / 2);
			node.localTranslation(new Vertex(startX,startY));

			Line in = linesIn.get(count);
			in.set(0, left);
			in.set(1, leftMiddle);
			in.set(2, leftMiddle.setY(startY));
			in.set(3, node.localTranslation());

			Line out = linesOut.get(count);
			out.set(0, right);
			out.set(1, rightMiddle);
			out.set(2, rightMiddle.setY(startY));
			out.set(3, node.localTranslation().addX(nodeSize.x));
			count++;
			startY += nodeSize.y+margin;
			in.prepare(g);
			out.prepare(g);
		}
		this.size(new Vertex(maxWidth+(cornerRadiusX*4),totalHeight));
	}

}
