package xyz.slashg.dynalog;

import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;

import xyz.slashg.dynalog.builders.Builder;

/**
 * @author SlashG
 * @created 19/02/19
 * @since <nextVersion/>
 */
public class DynaCounter extends HashMap<Integer, Integer> {
	public static final String TAG = DynaCounter.class.getSimpleName();

	/**
	 * JSON initer for {@link DynaCounter}
	 *
	 * @param object {@link JSONObject} to init {@link DynaCounter} from.
	 * @return Instance of {@link DynaCounter} if successful, 'null' if failed.
	 */
	@Nullable
	public static DynaCounter fromJSON(JSONObject object) {
		DynaCounter result = null;

		{

			try {
				Gson gson = GsonHelper.getGsonInstance();
				result = gson.fromJson(object.toString(), DynaCounter.class);
			} catch (Exception e) {
				Log.d(TAG, "fromJSON: failed to init DynaCounter", e);
			}
		}

		return result;
	}

	public void recordDialogShow(int dynalogId) {
		Log.d(TAG, "recordDialogShow: initial value = " + getShowCount(dynalogId));
		put(dynalogId, getShowCount(dynalogId) + 1);
		Log.d(TAG, "recordDialogShow:   final value = " + getShowCount(dynalogId));
	}

	public String toJSONString() {
		return GsonHelper.getGsonInstance().toJson(this);
	}

	public boolean shouldShouldDialog(Builder dynaBuilder) {
		if (dynaBuilder.getMaxShowCount() >= 0) {
			if (getShowCount(dynaBuilder.getId()) >= dynaBuilder.getMaxShowCount()) {
				Log.d(TAG, "shouldShouldDialog: max number of shows for the dialog reached");
				return false;
			}
			else {
				Log.d(TAG, "shouldShouldDialog: can show dialog");
			}
		}
		else {
			Log.d(TAG, "shouldShouldDialog: the dialog can be shown any number of times, show away!");
		}
		return true;
	}

	/**
	 * Method to safely get the number of times a dialog has been shown.
	 * It checks if the key passed as parameter is present. If so, it returns
	 * the value bound to it. Otherwise, it returns 0.
	 *
	 * @param dynalogId The ID of the dialog to get show count for
	 * @return Show count of the dialog in query
	 * @added 19/02/19
	 * @author kreatryx
	 * @since <nextVersion/>
	 */
	public int getShowCount(int dynalogId) {
		if (containsKey(dynalogId)) {
			return get(dynalogId);

		}
		else { return 0; }
	}

}
