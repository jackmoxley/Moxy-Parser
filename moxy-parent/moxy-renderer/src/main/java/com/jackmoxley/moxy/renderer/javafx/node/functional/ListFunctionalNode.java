package com.jackmoxley.moxy.renderer.javafx.node.functional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.shape.Path;

import com.jackmoxley.moxy.renderer.javafx.node.NodeFactory;
import com.jackmoxley.moxy.renderer.javafx.node.ParentNode;
import com.jackmoxley.moxy.renderer.javafx.node.RuleGraphNode;
import com.jackmoxley.moxy.renderer.javafx.node.RuleNode;
import com.jackmoxley.moxy.rule.Rule;
import com.jackmoxley.moxy.rule.functional.list.ListRule;

public abstract class ListFunctionalNode<FR extends ListRule> extends
		FunctionalNode<FR> {
	private List<RuleNode<?>> nodes;
	private Map<RuleNode<?>, List<Path>> paths;

	public ListFunctionalNode(FR fRule,ParentNode parent, RuleGraphNode graph) {
		super(fRule,parent,graph);
		this.getStyleClass().add("multiple");
	}

	protected void setup() {
		nodes = new ArrayList<RuleNode<?>>();
		paths = new HashMap<RuleNode<?>, List<Path>>();
		super.setup();
	}

	public void constructNodes() {
		nodes.clear();
		paths.clear();
		for (Rule subRule : rule) {
			RuleNode<?> node = NodeFactory.getInstance().getNodeFor(graph,this,
					subRule);
			addChild(node);
			node.getStyleClass().add("child");
		}
	}

	protected void removeChild(RuleNode<?> node) {
		int index = nodes.indexOf(node);
		if (index < 0) {
			return;
		}
		removeChild(index, node);
	}

	protected void removeChild(int index) {
		RuleNode<?> node = nodes.get(index);
		if (node == null) {
			return;
		}
		removeChild(index, node);
	}

	private void removeChild(int index, RuleNode<?> node) {
		RuleNode<?> before = before(index);
		RuleNode<?> after = after(index);
		removeChild(before, nodes.remove(index), after);
	}

	protected void setChild(int index, RuleNode<?> node) {
		if (index >= nodes.size()) {
			return;
		}
		RuleNode<?> before = before(index);
		RuleNode<?> after = after(index);
		RuleNode<?> old = nodes.set(index, node);
		removeChild(before, old, after);
		addChild(before, node, after);
	}

	protected void addChild(RuleNode<?> node) {
		addChild(nodes.size(), node);
	}

	protected void addChild(int index, RuleNode<?> node) {
		RuleNode<?> before = before(index);
		RuleNode<?> after = at(index);
		nodes.add(index, node);
		addChild(before, node, after);
	}

	protected RuleNode<?> before(int index) {
		if (index > 0) {
			return nodes.get(index - 1);
		}
		return null;
	}

	protected RuleNode<?> after(int index) {
		if (index < nodes.size() - 1) {
			return nodes.get(index + 1);
		}
		return null;
	}

	protected RuleNode<?> at(int index) {
		if (index < nodes.size()) {
			return nodes.get(index);
		}
		return null;
	}

	public List<RuleNode<?>> getNodes() {
		return nodes;
	}

	protected void removeChild(RuleNode<?> before, RuleNode<?> node,
			RuleNode<?> after) {

		if (before != null) {
			unbindChildren(before, node);
		}
		if (after != null) {
			unbindChildren(node, after);
			if (before != null) {
				bindChildren(before, after);
			}
		}
		unbindChild(node);
		this.getRuleNode().getChildren().remove(node);
		paths.remove(node);

		node.boundsInLocalProperty().removeListener(this);
	}

	protected void addChild(RuleNode<?> before, RuleNode<?> node,
			RuleNode<?> after) {

		node.boundsInLocalProperty().addListener(this);
		if (before != null && after != null) {
			unbindChildren(before, after);
		}
		bindChildren(before, node);
		bindChildren(node, after);
		bindChild(node);
		this.getRuleNode().getChildren().add(node);
	}

	protected Path getPath(RuleNode<?> node, int index) {
		List<Path> pathList = paths.get(node);
		if (pathList == null) {
			return null;
		}
		if (index >= pathList.size()) {
			return null;
		}
		return pathList.get(index);
	}

	protected void removePath(Path path, RuleNode<?> node) {

		List<Path> pathList = paths.get(node);
		path.getElements().clear();
		if (pathList == null) {
			return;
		}

		if (pathList.remove(path)) {
			this.getRuleNode().getChildren().remove(path);
		}
	}

	protected void addPath(Path path, RuleNode<?> node) {
		List<Path> pathList = paths.get(node);
		if (pathList == null) {
			pathList = new ArrayList<>();
			paths.put(node, pathList);
		}
		pathList.add(path);
		path.getStyleClass().add("path");
		this.getRuleNode().getChildren().add(path);
	}

	protected abstract void bindChildren(RuleNode<?> first, RuleNode<?> second);

	protected abstract void unbindChildren(RuleNode<?> first, RuleNode<?> second);

}
