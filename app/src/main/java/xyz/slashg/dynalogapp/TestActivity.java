package xyz.slashg.dynalogapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.json.JSONObject;

import xyz.slashg.dynalog.CustomButton;
import xyz.slashg.dynalog.DynaCounter;
import xyz.slashg.dynalog.Dynalog;
import xyz.slashg.dynalog.builders.Builder;
import xyz.slashg.dynalog.builders.ButtonBuilder;

public class TestActivity extends AppCompatActivity {

	public static final String PREF_KEY_DYNACOUNTER = "dynacounter";
	public static final String TAG = TestActivity.class.getSimpleName();
	public static final String PREF_KEY = "pref";
	DynaCounter dynaCounter;
	SharedPreferences sharedPreferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(xyz.slashg.dynalog.R.layout.activity_test);

		try {
			JSONObject json = new JSONObject("{\n" +
					"   \"title\":\"Title\",\n" +
					"   \"message\":\"This is a dynamically created dialog and you are required to respect the genius behind it :*\",\n" +
					"   \"header_image\":\"<image_url>\",\n" +
					"   \"is_dismissable\":true,\n" +
					"   \"max_show_count\":6,\n" +
					"   \"buttons\":[\n" +
					"      {\n" +
					"         \"text\":\"Leftmost button\",\n" +
					"         \"type\":\"coloured|default|borderless\",\n" +
					"         \"action\":\"dismiss|redirect\",\n" +
					"         \"is_enabled\":true\n" +
					"      },\n" +
					"      {\n" +
					"         \"text\":\"Rightmost button\",\n" +
					"         \"type\":\"coloured|default|borderless\",\n" +
					"         \"action\":\"dismiss\",\n" +
					"         \"is_enabled\":true\n" +
					"      }\n" +
					"   ]\n" +
					"}");
			Builder builder = Builder.fromJSON(json);

			Log.d(TAG, "onCreate: chutiya = " + builder.getMaxShowCount());
			if (getDynaCounter().shouldShouldDialog(builder)) {
				Dynalog dynalog = builder.build(this);
				dynalog.show();
				dynalog.setButtonClickListener(
						new Dynalog.ButtonClickListener() {
							@Override
							public void onButtonClicked(int buttonIndex, ButtonBuilder builder, CustomButton button, Dynalog dynalog) {

							}
						}
				);
				updateDynaCounter(builder.getId());
			}
			else {
				Log.d(TAG, "showDialogIfPresent: Dialog show count exhausted");

			}

		} catch (Exception e) {
			Log.e(TAG, "onCreate: ", e);
		}

	}

	public void updateDynaCounter(int id) {
		getDynaCounter().recordDialogShow(id);
		saveDynaCounter();
	}

	@NonNull
	public DynaCounter getDynaCounter() {
		Log.d(TAG, "getDynaCounter: ");
		if (dynaCounter == null) {
			String jsonString = getSharedPreferences().getString(PREF_KEY_DYNACOUNTER, "{}");
			Log.d(TAG, "getDynaCounter: " + jsonString);
			try {
				dynaCounter = DynaCounter.fromJSON(new JSONObject(jsonString));
			} catch (Exception e) {Log.e(TAG, "getDynaCounter: ", e);}
			if (dynaCounter == null) { dynaCounter = new DynaCounter(); }
		}

		return dynaCounter;
	}

	private void initPrefsIfNeeded() {
		if (sharedPreferences == null) {
			sharedPreferences = getSharedPreferences(PREF_KEY, MODE_PRIVATE);
		}
	}

	@NonNull
	public void saveDynaCounter() {
		String string = getDynaCounter().toJSONString();
		Log.d(TAG, "saveDynaCounter: jsonString = " + getDynaCounter().toJSONString());
		getSharedPreferences().edit().putString(PREF_KEY_DYNACOUNTER, string).apply();

	}

	public SharedPreferences getSharedPreferences() {
		initPrefsIfNeeded();
		return sharedPreferences;
	}
}
