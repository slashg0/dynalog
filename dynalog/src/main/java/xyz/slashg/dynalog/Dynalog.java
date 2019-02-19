package xyz.slashg.dynalog;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * @author SlashG
 * Created 14/02/19
 * @since 0.5.0
 */
public class Dynalog extends AlertDialog {

	public static final String TAG = Dynalog.class.getSimpleName();
	LinearLayout buttonLayout;
	TextView titleTextView;
	ImageView headerImageView;
	TextView messageTextView;
	View layout;
	boolean isInit = false;
	ButtonClickListener buttonClickListener;

	public Dynalog(@NonNull Context context) {
		super(context, R.style.AppTheme_Dialog);
		init();
	}

	/**
	 * Method that uses <code>ImageLoader</code> to retrieve
	 * Image at <code>url</code> and set it as src in
	 * <code>imageView</code>. This method performs all null-checks
	 * and is robust.
	 *
	 * @author SlashG
	 * @added 06-04-2016
	 * @since Version 2.6.2
	 */
	public static void safelyLoadImage(final ImageView imageView, String url, @Nullable final Callback<ImageView> whileLoadingAction, @Nullable final Callback<ImageView> onLoadedAction, @Nullable final Callback<ImageView> onLoadFailedAction) {
		Log.d(TAG, "safelyLoadImage: url = " + url);
		if (imageView == null) {
			Log.d(TAG, "CommonUtilities::safelyLoadImage() imageView is null. Aborting");
			return;
		}
		if (url == null) {
			Log.d(TAG, "CommonUtilities::safelyLoadImage() url is null. Aborting");
			return;
		}

		DisplayImageOptions options = new DisplayImageOptions.Builder().cacheOnDisk(false).cacheInMemory(false).considerExifParams(true).resetViewBeforeLoading(true).bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.EXACTLY_STRETCHED).build();

		ImageLoader.getInstance().displayImage(url, imageView, options, new ImageLoadingListener() {

			@Override
			public void onLoadingStarted(String arg0, View arg1) {
				Log.d(TAG, "onLoadingStarted: ");
				if (whileLoadingAction != null) {
					whileLoadingAction.run(imageView);
				}
			}

			@Override
			public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
				Log.d(TAG, "onLoadingFailed: ");
				if (onLoadFailedAction != null) {
					onLoadFailedAction.run(imageView);
				}
			}

			@Override
			public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
				Log.d(TAG, "onLoadingComplete: ");
				if (onLoadedAction != null) {
					Log.d(TAG, "onLoadingComplete: calling callback");

					onLoadedAction.run(imageView);
				}
			}

			@Override
			public void onLoadingCancelled(String arg0, View arg1) {
				Log.d(TAG, "onLoadingCancelled: ");
			}
		});

	}

	public void applyTextColorScheme(ColorScheme colorScheme) {

	}

	private void init() {
		layout = LayoutInflater.from(getContext()).inflate(R.layout.layout_dynalog, null);
		setView(layout);

		titleTextView = layout.findViewById(R.id.dyna_tv_title);
		messageTextView = layout.findViewById(R.id.dyna_tv_message);
		buttonLayout = layout.findViewById(R.id.dyna_ll_buttons);
		headerImageView = layout.findViewById(R.id.dyna_iv_header);
		isInit = true;
	}

	public void setButtonClickListener(ButtonClickListener buttonClickListener) {
		this.buttonClickListener = buttonClickListener;
	}

	protected void onButtonClicked(int buttonIndex, ButtonBuilder builder, CustomButton button) {
		if (buttonClickListener != null) {

			buttonClickListener.onButtonClicked(buttonIndex, builder, button, this);
		}
		else {
			Log.d(TAG, "onButtonClicked: Callback not init, ignoring");
		}
	}

	public void setData(Builder data) {

		layout.setBackgroundColor(data.getColorScheme().getBackgroundColor());
		titleTextView.setText(data.getTitle());
		titleTextView.setTextColor(data.getColorScheme().getPrimaryTextColor());
		messageTextView.setText(data.getMessage());
		messageTextView.setTextColor(data.getColorScheme().getSecondaryTextColor());
		ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(getContext()));        // Init UIL
		safelyLoadImage(headerImageView, data.getHeaderImageUrl(), view -> view.setVisibility(View.GONE), view -> view.setVisibility(View.VISIBLE), null);
		applyTextColorScheme(data.getColorScheme());

		// add buttons
		buttonLayout.removeAllViews();
		for (int i = 0; i < data.getButtons().size(); i++) {
			final int index = i;
			ButtonBuilder buttonBuilder = data.getButtons().get(i);
			CustomButton customButton = new CustomButton(getContext(), buttonBuilder, data.getColorScheme());
			customButton.setOnClickListener(v -> onButtonClicked(index, buttonBuilder, (CustomButton) v));
			buttonLayout.addView(customButton);
		}
		setCancelable(data.isDismissible());
	}

	public interface ButtonClickListener {
		void onButtonClicked(int buttonIndex, ButtonBuilder builder, CustomButton button, Dynalog dynalog);
	}

	// util method

	public static class Builder {
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
			Log.d(TAG, "fromJSON: " + object);
			{
				try {
					Gson gson = GsonHelper.getGsonInstance();
					result = gson.fromJson(object.toString(), Builder.class);
					result.colorScheme = ColorScheme.fromJSON(object.optJSONObject(ColorScheme.JSON_KEY));
				} catch (Exception e) {
					Log.d(TAG, "fromJSON: failed to init Dynalog", e);
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
}
