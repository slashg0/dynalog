package xyz.slashg.dynalog;

import android.util.Log;

import java.util.HashMap;

import xyz.slashg.dynalog.builders.Builder;

/**
 * @author SlashG
 * @created 19/02/19
 * @since <nextVersion/>
 */
public class DynaCounter extends HashMap<Integer, Integer> {
	public static final String TAG = DynaCounter.class.getSimpleName();

	public void recordDialogShow(int dynalogId) {
		Log.d(TAG, "recordDialogShow: initial value = " + getShowCount(dynalogId));
		put(dynalogId, getShowCount(dynalogId) + 1);
		Log.d(TAG, "recordDialogShow:   final value = " + getShowCount(dynalogId));
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
