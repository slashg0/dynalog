package xyz.slashg.dynalog;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import xyz.slashg.dynalog.builders.ButtonBuilder;


/**
 * @author SlashG
 * Created 15/02/19
 * @since 0.5.0
 */
public class CustomButton extends AppCompatButton {
	public CustomButton(Context context) {
		super(context);
	}

	public CustomButton(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CustomButton(Context context, ButtonBuilder buttonBuilder, ColorScheme colorScheme) {
		super(context);
		setText(buttonBuilder.getText());
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
		layoutParams.weight = 1;
		setPadding(getPadding(), getPadding(), getPadding(), getPadding());
		setLayoutParams(layoutParams);
		setEnabled(buttonBuilder.isEnabled());
		setAllCaps(false);

		TypedValue outValue = new TypedValue();
		getContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackground, outValue, true);

		if (buttonBuilder.getType().equalsIgnoreCase(ButtonBuilder.TYPE_COLOURED)) {
			setBackgroundResource(R.drawable.button_coloured);
			setSupportBackgroundTintList(ColorStateList.valueOf(colorScheme.getAccentColor()));
			setTextColor(colorScheme.getInvertTextColor());
		}
		else {
			setBackgroundResource(R.drawable.button_default);
			setTextColor(colorScheme.getAccentColor());
		}


	}

	int getPadding() {
		return getResources().getDimensionPixelSize(R.dimen.button_padding);
	}

}
