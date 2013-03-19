package com.jackmoxley.moxy.renderer.common.renderable;

import static com.jackmoxley.moxy.renderer.common.dimensions.Vertex.v;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.RenderingHints.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.jackmoxley.moxy.renderer.common.dimensions.Vertex;
import com.jackmoxley.moxy.renderer.node.Node;
import com.jackmoxley.moxy.renderer.node.NodeFactory;
import com.jackmoxley.moxy.rule.Rule;
import com.jackmoxley.moxy.rule.functional.list.OrRule;
import com.jackmoxley.moxy.rule.functional.list.SequenceRule;
import com.jackmoxley.moxy.rule.terminating.text.TextRule;

public class RenderableTest extends JPanel {

	private static final long serialVersionUID = 1L;

	Map<Key, Object> hints;
	List<Component> components = new ArrayList<>();
	List<Node<?>> nodes = new ArrayList<>();

	/**
	 * 
	 */
	public RenderableTest() {
		super();

		this.setDoubleBuffered(true);

		hints = new HashMap<Key, Object>();
		hints.put(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		this.setSize(new Dimension(1000, 500));
		setup();
	}

	private void setup() {
		components.add(new Text(null, "Some Text"));
		components.add(new Box(null));
		components.add(new TextBox(null, "Hello World!"));
		Line singleLine = new Line(null, v(0, 0), v(30, 30));
		components.add(singleLine);

		Line multipleLine = new Line(null, v(0, 25), v(0, 50), v(50, 50), v(50,
				0), v(0, 0), v(0, 25));
		components.add(multipleLine);

		Line multipleLineOtherDirection = new Line(null, v(0, 25), v(0, 0), v(
				50, 0), v(50, 50), v(0, 50), v(0, 25));
		components.add(multipleLineOtherDirection);

		add(new TextRule("Hello!"));

		SequenceRule sequenceRule = new SequenceRule();
		sequenceRule.add(new TextRule("Rule A"));
		sequenceRule.add(new TextRule("Rule B \n a bit bigger"));
		sequenceRule.add(new TextRule("Rule C"));
		sequenceRule.add(sequenceRule);
		add(sequenceRule);

		OrRule orRule = new OrRule();
		orRule.add(new TextRule("Rule A"));
		orRule.add(new TextRule("Rule B - a bit bigger"));
		orRule.add(new TextRule("Rule C"));
		add(orRule);
		// nodes.add(llNode);
		// OptionalRule optionalRule = new OptionalRule();
		// optionalRule.add(new TextRule("Rule A"));
		// OptionalNode optional = new OptionalNode(optionalRule);
		// nodes.add(optional);
	}

	private void paintGrid(Graphics2D g, int gridSize) {
		g.setColor(Color.orange);
		for (int i = 0; i < gridSize * 10; i += gridSize) {
			g.drawOval(0, i - 5, 10, 10);
			g.drawLine(10, i, gridSize * 10, i);
			g.drawOval(i - 5, 0, 10, 10);
			g.drawLine(i, 10, i, gridSize * 10);

		}
	}

	public void add(Rule rule) {
		nodes.add(NodeFactory.getInstance().getNodeFor(null, rule));
	}

	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);

		Graphics2D g = (Graphics2D) graphics;
		g.addRenderingHints(hints);
		Vertex translation = new Vertex(0, 50);
		int count = 1;
		int gridSize = 100;
		paintGrid(g, gridSize);
		for (Component r : components) {
			translation = new Vertex(count * gridSize, gridSize);
			r.prepare(g);
			r.localTranslation(translation);
			count++;
		}
		for (Component r : components) {
			r.render(g);
		}

		count = 2;
		for (Node<?> n : nodes) {
			n.prepare(g);
			translation = new Vertex(gridSize, count * gridSize);
			n.localTranslation(translation);
			count++;

		}
		for (Node<?> n : nodes) {
			n.render(g);
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		RenderableTest test = new RenderableTest();
		frame.getContentPane().add(test);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(new Dimension(1000, 500));
		frame.setVisible(true);
	}

}
