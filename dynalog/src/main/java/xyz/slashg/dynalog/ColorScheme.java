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
	public static final String JSON_KEY = "color_scheme";
	public static final String ACCENT_DEFAULT_STRING = "#007FFF";
	public static final String PRIMARY_DEFAULT_STRING = "#2D2D2D";
	public static final String SECONDARY_DEFAULT_STRING = "#6C6C6C";
	public static final String INVERT_DEFAULT_STRING = "#fdfdfd";
	public static final String BACKGROUND_DEFAULT_STRING = "#ffffff";
	private static final String JSON_BACKGROUND_COLOR = "background_color";
	private static final String JSON_ACCENT_COLOR = "accent_color";
	private static final String JSON_PRIMARY_COLOR = "primary_color";
	private static final String JSON_SECONDARY_COLOR = "secondary_color";
	private static final String JSON_INVERT_COLOR = "invert_color";
	@ColorInt
	int backgroundColor;
	@ColorInt
	int accentColor;
	@ColorInt
	int primaryTextColor;
	@ColorInt
	int secondaryTextColor;
	@ColorInt
	int invertTextColor;

	public ColorScheme(int backgroundColor, int accentColor, int primaryTextColor, int secondaryTextColor, int invertTextColor) {
		this.backgroundColor = backgroundColor;
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
		Log.d(TAG, "fromJSON: " + object);
		ColorScheme result;
		String backgroundColorString = null;
		String accentTextColorString = null;
		String primaryTextColorString = null;
		String secondaryTextColorString = null;
		String invertTextColorString = null;

		try {
			backgroundColorString = object.optString(JSON_BACKGROUND_COLOR);
			accentTextColorString = object.optString(JSON_ACCENT_COLOR);
			primaryTextColorString = object.optString(JSON_PRIMARY_COLOR);
			secondaryTextColorString = object.optString(JSON_SECONDARY_COLOR);
			invertTextColorString = object.optString(JSON_INVERT_COLOR);
		} catch (Exception e) {
			Log.e(TAG, "fromJSON: failed to get color overrides, passing null, fromStrings() will handle it ");
		}

		result = ColorScheme.fromStrings(backgroundColorString, accentTextColorString, primaryTextColorString, secondaryTextColorString, invertTextColorString);
		return result;
	}

	static @ColorInt
	int safeParseColor(String colorCode, String fallbackCode) {
		try {
			return Color.parseColor(colorCode);
		} catch (Exception e) {
			Log.e(TAG, "safeParseColor: Couldn't parse color " + colorCode + ", using fallback");
			return Color.parseColor(fallbackCode);
		}
	}

	public static ColorScheme fromStrings(String backgroundColorString, String accentTextColorString, String primaryTextColorString, String secondaryTextColorString, String invertTextColorString) {
		try {
			@ColorInt
			int backgroundColor = safeParseColor(backgroundColorString, BACKGROUND_DEFAULT_STRING);
			@ColorInt
			int accentTextColor = safeParseColor(accentTextColorString, ACCENT_DEFAULT_STRING);
			@ColorInt
			int primaryTextColor = safeParseColor(primaryTextColorString, PRIMARY_DEFAULT_STRING);
			@ColorInt
			int secondaryTextColor = safeParseColor(secondaryTextColorString, SECONDARY_DEFAULT_STRING);
			@ColorInt
			int invertTextColor = safeParseColor(invertTextColorString, INVERT_DEFAULT_STRING);

			return new ColorScheme(backgroundColor, accentTextColor, primaryTextColor, secondaryTextColor, invertTextColor);
		} catch (Exception e) {
			Log.d(TAG, "fromStrings", e);
			Log.e(TAG, "fromStrings: Couldn't create from Strings, returning default");
			return getDefault();
		}
	}

	public static ColorScheme getDefault() {
		@ColorInt
		int backgroundColor = Color.parseColor(BACKGROUND_DEFAULT_STRING);
		@ColorInt
		int accentTextColor = Color.parseColor(ACCENT_DEFAULT_STRING);
		@ColorInt
		int primaryTextColor = Color.parseColor(PRIMARY_DEFAULT_STRING);
		@ColorInt
		int secondaryTextColor = Color.parseColor(SECONDARY_DEFAULT_STRING);
		@ColorInt
		int invertTextColor = Color.parseColor(INVERT_DEFAULT_STRING);

		return new ColorScheme(backgroundColor, accentTextColor, primaryTextColor, secondaryTextColor, invertTextColor);
	}

	public int getBackgroundColor() {
		return backgroundColor;
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
