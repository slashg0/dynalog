package xyz.slashg.dynalog;

import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.util.Log;

import org.json.JSONObject;

/**
 * @author SlashG
 * Created 14/02/19
 * @since 0.5.0
 */
public class ColorScheme {
	public static final String TAG = ColorScheme.class.getSimpleName();
	public static final String JSON_KEY = "text_color_scheme";
	private static final String JSON_ACCENT_COLOR = "accent_color";
	private static final String JSON_PRIMARY_COLOR = "primary_color";
	private static final String JSON_SECONDARY_COLOR = "secondary_color";
	private static final String JSON_INVERT_COLOR = "invert_color";

	@ColorInt
	int accentColor;
	@ColorInt
	int primaryTextColor;
	@ColorInt
	int secondaryTextColor;
	@ColorInt
	int invertTextColor;

	public ColorScheme(int accentColor, int primaryTextColor, int secondaryTextColor, int invertTextColor) {

		this.accentColor = accentColor;
		this.primaryTextColor = primaryTextColor;
		this.secondaryTextColor = secondaryTextColor;
		this.invertTextColor = invertTextColor;
	}

	/**
	 * JSON initer for {@link ColorScheme}
	 *
	 * @param object {@link JSONObject} to init {@link ColorScheme} from.
	 * @return Instance of {@link ColorScheme} if successful, 'null' if failed.
	 */
	@Nullable
	public static ColorScheme fromJSON(@Nullable JSONObject object) {
		ColorScheme result;
		String accentTextColorString = null;
		String primaryTextColorString = null;
		String secondaryTextColorString = null;
		String invertTextColorString = null;

		try {
			accentTextColorString = object.optString(JSON_ACCENT_COLOR);
			primaryTextColorString = object.optString(JSON_PRIMARY_COLOR);
			secondaryTextColorString = object.optString(JSON_SECONDARY_COLOR);
			invertTextColorString = object.optString(JSON_INVERT_COLOR);
		} catch (Exception e) {
			Log.d(TAG, "fromJSON: failed to init ColorScheme", e);
		}

		result = ColorScheme.fromStrings(accentTextColorString, primaryTextColorString, secondaryTextColorString, invertTextColorString);
		return result;
	}

	public static ColorScheme fromStrings(String accentTextColorString, String primaryTextColorString, String secondaryTextColorString, String invertTextColorString) {
		try {
			@ColorInt
			int accentTextColor = Color.parseColor(accentTextColorString);
			@ColorInt
			int primaryTextColor = Color.parseColor(primaryTextColorString);
			@ColorInt
			int secondaryTextColor = Color.parseColor(secondaryTextColorString);
			@ColorInt
			int invertTextColor = Color.parseColor(invertTextColorString);

			return new ColorScheme(accentTextColor, primaryTextColor, secondaryTextColor, invertTextColor);
		} catch (Exception e) {
			Log.e(TAG, "fromStrings: Couldn't create from Strings", e);
			return getDefault();
		}
	}

	public static ColorScheme getDefault() {
		@ColorInt
		int accentTextColor = Color.parseColor("#007FFF");
		@ColorInt
		int primaryTextColor = Color.parseColor("#2D2D2D");
		@ColorInt
		int secondaryTextColor = Color.parseColor("#6C6C6C");
		@ColorInt
		int invertTextColor = Color.parseColor("#fdfdfd");

		return new ColorScheme(accentTextColor, primaryTextColor, secondaryTextColor, invertTextColor);
	}

	public int getAccentColor() {
		return accentColor;
	}

	@ColorInt
	public int getPrimaryTextColor() {
		return primaryTextColor;
	}

	@ColorInt
	public int getSecondaryTextColor() {
		return secondaryTextColor;
	}

	@ColorInt
	public int getInvertTextColor() {
		return invertTextColor;
	}
}
