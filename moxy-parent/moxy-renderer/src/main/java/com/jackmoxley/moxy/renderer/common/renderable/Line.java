package com.jackmoxley.moxy.renderer.common.renderable;

import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.QuadCurve2D;
import java.util.Arrays;
import java.util.List;

import com.jackmoxley.moxy.renderer.common.dimensions.Vertex;
import com.jackmoxley.moxy.renderer.common.style.Style;
import com.jackmoxley.moxy.renderer.node.StyleManager;

public class Line extends Component {

	private List<Vertex> vertices;

	public Line(Spatial spatial, Vertex... vertices) {
		this(spatial, Arrays.asList(vertices));
	}

	public Line(Spatial spatial, List<Vertex> vertices) {
		super(spatial);
		this.vertices = vertices;
		sizeFromVertices(vertices);
	}

	@Override
	public void prepare(Graphics2D g) {
		sizeFromVertices(vertices);
	}

	@Override
	protected void draw(Graphics2D g) {
		// System.out.println("*"+translation+" "+boundsStart()+" "+worldTranslation);
		Style style = StyleManager.currentStyle();
		Vertex start = vertices.get(0);
		Vertex end;
		Vertex lineEnd = null;
		Vertex lineStart;
		int last = vertices.size() - 1;
		Vertex actualCornerRadius = style.lineStyle.cornerRadius;
		Vertex cornerRadius = actualCornerRadius;
		double radiusDistance = actualCornerRadius.distance();
		g.setColor(style.lineStyle.color);
		for (int i = 1; i <= last; i++) {
			end = vertices.get(i);

			Vertex d = start.sub(end);
			double distance = d.distance();
			if (distance < radiusDistance) {
				cornerRadius = actualCornerRadius.div(radiusDistance).mul(
						distance);
			} else {
				cornerRadius = actualCornerRadius;
			}
			if (i != 1) {
				if (d.x < 0) {
					lineStart = start.addX(Math.min(cornerRadius.x, -d.x));
				} else {
					lineStart = start.subX(Math.min(cornerRadius.x, d.x));
				}
				if (d.y < 0) {
					lineStart = lineStart.addY(Math.min(cornerRadius.y, -d.y));
				} else {
					lineStart = lineStart.subY(Math.min(cornerRadius.y, d.y));
				}

				g.setColor(style.lineStyle.color);
				QuadCurve2D curve = new QuadCurve2D.Double(lineStart.x,
						lineStart.y, start.x, start.y, lineEnd.x, lineEnd.y);
				g.draw(curve);

			} else {
				lineStart = start;
			}
			if (i != last) {
				if (d.x < 0) {
					lineEnd = end.subX(Math.min(cornerRadius.x, -d.x));
				} else {
					lineEnd = end.addX(Math.min(cornerRadius.x, d.x));
				}
				if (d.y < 0) {
					lineEnd = lineEnd.subY(Math.min(cornerRadius.y, -d.y));
				} else {
					lineEnd = lineEnd.addY(Math.min(cornerRadius.y, d.y));
				}

			} else {
				lineEnd = end;
			}

			g.setColor(style.lineStyle.color);
			Line2D line = new Line2D.Double(lineStart.x, lineStart.y,
					lineEnd.x, lineEnd.y);
			g.draw(line);

			start = end;
		}
	}

	public List<Vertex> getVertices() {
		return vertices;
	}

	public void setVertices(List<Vertex> vertices) {
		this.vertices = vertices;
	}

	public void set(int index, Vertex v) {
		vertices.set(index, v);
	}

	public Vertex get(int index) {
		return vertices.get(index);
	}

	public int vertexSize() {
		return vertices.size();
	}

	public boolean add(Vertex e) {
		return vertices.add(e);
	}

	public void add(int index, Vertex element) {
		vertices.add(index, element);
	}

	// @Override
	// public String toString() {
	// StringBuilder builder = new StringBuilder();
	// builder.append("Line [vertices=").append(vertices).append(", size=")
	// .append(size()).append(", localTranslation=")
	// .append(localTranslation()).append(", worldTranslation()=")
	// .append(worldTranslation()).append("]");
	// return builder.toString();
	// }

}
