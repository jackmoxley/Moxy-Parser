package com.jackmoxley.moxy.renderer.node;

import com.jackmoxley.moxy.renderer.common.style.Style;
import com.jackmoxley.moxy.rule.Rule;
import com.jackmoxley.moxy.rule.terminating.text.CharacterRule;
import com.jackmoxley.moxy.rule.terminating.text.TextRule;

public class SimpleStyleManager extends StyleManager {

	private static float char_hue = 0.25f;

	public static final Style CHARACTER_STYLE = Style.BASE_STYLE.withHue(
			char_hue).withCornerRadius(Style.DOUBLE_MARGIN);

	public static final Style CHARACTER_HIGHLIGHT = Style.BASE_HIGHLIGHT
			.withHue(char_hue).withCornerRadius(Style.DOUBLE_MARGIN);

	
	@Override
	public Style getStyle(Node<?> node, Rule rule) {
		if (rule instanceof CharacterRule) {
			return CHARACTER_STYLE;
		} else if (rule instanceof TextRule) {
			return CHARACTER_STYLE;
		} 
		return Style.BASE_STYLE;
	}


	@Override
	public Style getCurrentStyle() {
		return Style.BASE_STYLE;
	}
	
	
}
