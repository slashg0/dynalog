package xyz.slashg.dynalog.builders;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;

import xyz.slashg.dynalog.ColorScheme;
import xyz.slashg.dynalog.Dynalog;
import xyz.slashg.dynalog.GsonHelper;

/**
 * @author SlashG
 * @created 26/03/19
 * @since <nextVersion/>
 */
public class Builder {
	private int id;
	private String title;
	private String message;
	private String headerImageUrl;
	private String iconImageUrl;
	transient private ColorScheme colorScheme;
	private ArrayList<ButtonBuilder> buttons;
	private boolean isDismissible;
	private int maxShowCount;

	/**
	 * JSON initer for {@link Dynalog}
	 *
	 * @param object {@link JSONObject} to init {@link Dynalog} from.
	 * @return Instance of {@link Dynalog} if successful, 'null' if failed.
	 */
	@Nullable
	public static Builder fromJSON(JSONObject object) {
		Builder result = null;
		Log.d(Dynalog.TAG, "fromJSON: " + object);
		{
			try {
				Gson gson = GsonHelper.getGsonInstance();
				result = gson.fromJson(object.toString(), Builder.class);
				result.colorScheme = ColorScheme.fromJSON(object.optJSONObject(ColorScheme.JSON_KEY));
			} catch (Exception e) {
				Log.d(Dynalog.TAG, "fromJSON: failed to init Dynalog", e);
			}
		}

		return result;
	}

	public int getMaxShowCount() {
		return maxShowCount;
	}

	public int getId() {
		return id;
	}

	public boolean isDismissible() {
		return isDismissible;
	}

	public String getTitle() {
		return title;
	}

	public String getMessage() {
		return message;
	}

	public String getHeaderImageUrl() {
		return headerImageUrl;
	}

	public String getIconImageUrl() {
		return iconImageUrl;
	}

	public ColorScheme getColorScheme() {
		return colorScheme;
	}

	@NonNull
	public ArrayList<ButtonBuilder> getButtons() {
		if (buttons == null) { buttons = new ArrayList<>(); }
		return buttons;
	}

	public Dynalog build(@NonNull Context context) {
		Dynalog dynalog = new Dynalog(context);
		dynalog.setData(this);
		return dynalog;
	}

}
