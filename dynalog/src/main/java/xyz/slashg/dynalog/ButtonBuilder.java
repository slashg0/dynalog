package xyz.slashg.dynalog;

import android.support.annotation.StyleRes;

/**
 * Builder for action button to be used in {@link Dynalog}
 * This defines all params to map the appearance and action
 * associated with a button in {@link Dynalog}.
 *
 * @author SlashG
 * @created 15/02/19
 * @since <nextVersion/>
 */
public class ButtonBuilder {
	public static final String TYPE_COLOURED = "coloured";
	public static final String TYPE_BORDERLESS = "borderless";
	public static final String TYPE_DEFAULT = "default";
	public static final String ACTION_REDIRECT = "redirect";
	public static final String ACTION_DISMISS = "dismiss";
	String text;
	String type;
	String action;
	String actionParam;
	boolean isEnabled = true;

	public boolean isEnabled() {
		return isEnabled;
	}

	public @StyleRes
	int getThemeFromType() {
		switch (type) {
			case TYPE_BORDERLESS:
				return R.style.Widget_AppCompat_Button_Borderless;
			case TYPE_COLOURED:
				return R.style.Widget_AppCompat_Button_Colored;
			case TYPE_DEFAULT:
			default:
				return R.style.Widget_AppCompat_Button;
		}
	}

	public String getText() {
		return text;
	}

	public String getType() {
		return type;
	}

	public String getAction() {
		return action;
	}

	public String getActionParam() {
		return actionParam;
	}
}
