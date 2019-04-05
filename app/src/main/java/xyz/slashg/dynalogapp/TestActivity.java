package xyz.slashg.dynalogapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import xyz.slashg.dynalog.CustomButton;
import xyz.slashg.dynalog.DynaCounter;
import xyz.slashg.dynalog.Dynalog;
import xyz.slashg.dynalog.builders.Builder;
import xyz.slashg.dynalog.builders.ButtonBuilder;
import xyz.slashg.spine.Spine;

public class TestActivity extends AppCompatActivity {

	public static final String PREF_KEY_DYNACOUNTER = "dynacounter";
	public static final String TAG = TestActivity.class.getSimpleName();
	public static final String PREF_KEY = "pref";
	DynaCounter dynaCounter;
	SharedPreferences sharedPreferences;

	public void onDialogButtonClicked(int buttonIndex, ButtonBuilder builder, CustomButton button, Dynalog dynalog) {
		Log.d(TAG, "onButtonClicked:  " + buttonIndex);
		switch (builder.getAction()) {
			case ButtonBuilder.ACTION_REDIRECT:
				try {
					Uri uri = Uri.parse(builder.getActionParam());
					startActivity(new Intent(Intent.ACTION_VIEW, uri));
				} catch (Exception e) {
					Log.e(TAG, "onDialogButtonClicked: Couldn't redirect", e);
				}
				break;
			case ButtonBuilder.ACTION_DISMISS:
			default:
				dynalog.dismiss();
				break;
		}
	}

	public void restart(View view) {
		finish();
		startActivity(new Intent(this, this.getClass()));

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

//		JsonObjectRequest jsonObjectRequest = new
		try {Spine.initialize(this);} catch (Exception e) {
			Log.e(TAG, "onCreate: ", e);
		}
		Spine.queueRequest(new JsonObjectRequest(Request.Method.GET, "http://demo7150983.mockable.io/dynalog", null, response -> {

			try {

				Builder builder = Builder.fromJSON(response);

				Log.d(TAG, "onCreate: chutiya = " + builder.getMaxShowCount());
				if (getDynaCounter().shouldShouldDialog(builder)) {
					Dynalog dynalog = builder.build(this);
					dynalog.show();
					dynalog.setButtonClickListener(this::onDialogButtonClicked);
					updateDynaCounter(builder.getId());
				}
				else {
					Log.d(TAG, "showDialogIfPresent: Dialog show count exhausted");

				}

			} catch (Exception e) {
				Log.e(TAG, "onCreate: ", e);
			}

		}, error -> Log.e(TAG, "onCreate: Couldn't create dynalog", error)));


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
