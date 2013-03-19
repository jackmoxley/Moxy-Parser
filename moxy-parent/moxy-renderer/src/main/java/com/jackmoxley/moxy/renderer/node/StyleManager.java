package com.jackmoxley.moxy.renderer.node;

import com.jackmoxley.moxy.renderer.common.style.Style;
import com.jackmoxley.moxy.rule.Rule;

public abstract class StyleManager {

	private static StyleManager instance;

	public static StyleManager getInstance() {
		if(instance == null){
			setInstance(new SimpleStyleManager());
		}
		return instance;
	}

	public static void setInstance(StyleManager instance) {
		StyleManager.instance = instance;
	}
	
	public abstract Style getStyle(Node<?> node, Rule rule);
	
	public static Style currentStyle(){
		return getInstance().getCurrentStyle();
	}
	
	protected abstract Style getCurrentStyle();
}
