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

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import ru.noties.markwon.Markwon;
import xyz.slashg.dynalog.builders.ButtonBuilder;

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

	/**
	 * Method checks if the input is null or empty.
	 * This method trims the input before checking its length.
	 * Written for the love of clean
	 * code.
	 *
	 * @param input The input to check.
	 * @return 'true' if the the input is null or of zero length; 'false' otherwise.
	 * @author SlashG
	 * @added 23-09-2016
	 * @since Version 3.2.1
	 */
	public static boolean isStringNullOrEmptyTrim(@Nullable String input) {

		if (input != null) {
			if (input.trim().length() > 0) {
				return false;
			}

		}
		return true;


	}

	/**
	 * Method that checks a provided {@link String} and if it isn't
	 * null/blank, sets it inside the provided {@link TextView} and
	 * makes the {@link TextView} {@link View#VISIBLE}. If the
	 * {@link String} is null/blank, the {@link TextView} is hidden ({@link View#GONE}).
	 *
	 * @param text     The {@link String} to check and set in the {@link TextView}
	 * @param textView The {@link TextView} to set the value in (or hide).
	 * @added 22/09/18
	 * @author kreatryx
	 * @since <nextVersion/>
	 */
	public static void setTextOrHide(@Nullable String text, @NonNull TextView textView) {
		textView.setText(text);
		if (isStringNullOrEmptyTrim(text)) {
			textView.setVisibility(View.GONE);
		}
		else {
			textView.setVisibility(View.VISIBLE);
		}
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

	public void setData(xyz.slashg.dynalog.builders.Builder data) {

		layout.setBackgroundColor(data.getColorScheme().getBackgroundColor());
		setTextOrHide(data.getTitle(), titleTextView);
		titleTextView.setTextColor(data.getColorScheme().getPrimaryTextColor());
		if (isStringNullOrEmptyTrim(data.getMessage())) {
			Markwon.setText(messageTextView, Markwon.markdown(getContext(), data.getMessage()));
			messageTextView.setVisibility(View.VISIBLE);
		}
		else {
			messageTextView.setVisibility(View.GONE);
		}
		messageTextView.setTextColor(data.getColorScheme().getSecondaryTextColor());
		ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(getContext()));        // Init UIL
		safelyLoadImage(headerImageView, data.getHeaderImageUrl(), view -> view.setVisibility(View.GONE), view -> view.setVisibility(View.VISIBLE), object -> Log.e(TAG, "setData: Couldn't load"));
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

}
