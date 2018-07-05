package com.example.utility;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.R;

import java.util.ArrayList;
import java.util.List;

// original AlertDialog implementation, layout and style:
// https://github.com/android/platform_frameworks_base/blob/master/core/java/android/app/AlertDialog.java
// https://github.com/android/platform_frameworks_base/blob/master/core/java/com/android/internal/app/AlertController.java
// https://github.com/android/platform_frameworks_base/blob/master/core/res/res/layout/alert_dialog_holo.xml
// https://github.com/android/platform_frameworks_base/blob/master/core/res/res/values/themes.xml
public final class DialogStyle {
	private DialogStyle() {}

	public static void overrideStyle(Context context, AlertDialog dialog, boolean light) {
		// resources
		int resourceDialogTitleText = light ? R.color.dialog_title_text_light : R.color.dialog_title_text_dark;
		int resourceDialogTitleDivider = light ? R.color.dialog_title_divider_light : R.color.dialog_title_divider_dark;
		int resourceDialogMessageText = light ? R.color.dialog_message_text_light : R.color.dialog_message_text_dark;
		int resourceDialogButtonText = light ? R.color.dialog_button_text_light : R.color.dialog_button_text_dark;
		int resourceSelectorDialogButtonBg = light ? R.drawable.selector_dialog_button_bg_light : R.drawable.selector_dialog_button_bg_dark;
		int resourceShapeDialogDividerHorizontal = light ? R.drawable.shape_dialog_divider_horizontal_light : R.drawable.shape_dialog_divider_horizontal_dark;
		int resourceShapeDialogDividerVertical = light ? R.drawable.shape_dialog_divider_vertical_light : R.drawable.shape_dialog_divider_vertical_dark;

		// ids
		//final int parentPanel = context.getResources().getIdentifier("parentPanel", "id", "android");
		final int topPanel = context.getResources().getIdentifier("topPanel", "id", "android");
		final int contentPanel = context.getResources().getIdentifier("contentPanel", "id", "android");
		final int customPanel = context.getResources().getIdentifier("customPanel", "id", "android");
		final int buttonPanel = context.getResources().getIdentifier("buttonPanel", "id", "android");

		final int alertTitle = context.getResources().getIdentifier("alertTitle", "id", "android");
		final int titleDivider = context.getResources().getIdentifier("titleDivider", "id", "android");
		final int message = context.getResources().getIdentifier("message", "id", "android");
		final int selectDialogListview = context.getResources().getIdentifier("select_dialog_listview", "id", "android");
		final int button1 = context.getResources().getIdentifier("button1", "id", "android");
		final int button2 = context.getResources().getIdentifier("button2", "id", "android");
		final int button3 = context.getResources().getIdentifier("button3", "id", "android");

		// references
		//LinearLayout parentPanelView = dialog.findViewById(parentPanel);
		LinearLayout topPanelView = dialog.findViewById(topPanel);
		LinearLayout contentPanelView = dialog.findViewById(contentPanel);
		FrameLayout customPanelView = dialog.findViewById(customPanel);
		LinearLayout buttonPanelView = dialog.findViewById(buttonPanel);
		LinearLayout buttonPanelChildView = buttonPanelView.getChildAt(0);

		TextView alertTitleView = dialog.findViewById(alertTitle);
		View titleDividerView = dialog.findViewById(titleDivider);
		TextView messageView = dialog.findViewById(message);
		ListView selectDialogListviewView = dialog.findViewById(selectDialogListview);
		Button button1View = dialog.findViewById(button1);
		Button button2View = dialog.findViewById(button2);
		Button button3View = dialog.findViewById(button3);

		// dialog background
		if (topPanelView != null && contentPanelView != null && customPanelView != null && buttonPanelView != null) {
			setBackground(topPanelView, contentPanelView, customPanelView, buttonPanelView, light);
		}

		// dialog style
		if (alertTitleView != null)
			alertTitleView.setTextColor(context.getResources().getColor(resourceDialogTitleText));
		if (titleDividerView != null) {
			titleDividerView.setBackgroundColor(context.getResources().getColor(resourceDialogTitleDivider));
			ViewGroup.LayoutParams params = titleDividerView.getLayoutParams();
			params.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, context.getResources().getDisplayMetrics());
		}
		if (messageView != null) messageView.setTextColor(context.getResources().getColor(resourceDialogMessageText));
		if (selectDialogListviewView != null) {
			selectDialogListviewView.setSelector(resourceSelectorDialogButtonBg);
			selectDialogListviewView.setDivider(context.getResources().getDrawable(resourceShapeDialogDividerHorizontal));
		}
		if (button1View != null) {
			button1View.setTextColor(context.getResources().getColor(resourceDialogButtonText));
			button1View.setBackgroundResource(resourceSelectorDialogButtonBg);
			button1View.setTypeface(Typeface.DEFAULT);

			LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) button1View.getLayoutParams();
			params.setMargins(0, 0, 0, 0);
			button1View.setLayoutParams(params);
		}
		if (button2View != null) {
			button2View.setTextColor(context.getResources().getColor(resourceDialogButtonText));
			button2View.setBackgroundResource(resourceSelectorDialogButtonBg);
			button2View.setTypeface(Typeface.DEFAULT);

			LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) button2View.getLayoutParams();
			params.setMargins(0, 0, 0, 0);
			button2View.setLayoutParams(params);
		}
		if (button3View != null) {
			button3View.setTextColor(context.getResources().getColor(resourceDialogButtonText));
			button3View.setBackgroundResource(resourceSelectorDialogButtonBg);
			button3View.setTypeface(Typeface.DEFAULT);

			LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) button3View.getLayoutParams();
			params.setMargins(0, 0, 0, 0);
			button3View.setLayoutParams(params);
		}
		if (buttonPanelView != null) {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
				buttonPanelView.setShowDividers(LinearLayout.SHOW_DIVIDER_BEGINNING);
				buttonPanelView.setDividerDrawable(context.getResources().getDrawable(resourceShapeDialogDividerHorizontal));
			}
		}
		if (buttonPanelChildView != null) {
			buttonPanelChildView.setPadding(0, 0, 0, 0);
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
				buttonPanelChildView.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
				buttonPanelChildView.setDividerDrawable(context.getResources().getDrawable(resourceShapeDialogDividerVertical));
			}
		}
	}

	private static void setBackground(LinearLayout topPanelView, LinearLayout contentPanelView, FrameLayout customPanelView, LinearLayout buttonPanelView, boolean light) {
		int resourceDialogFullHolo = light ? R.drawable.dialog_full_holo_light : R.drawable.dialog_full_holo_dark;
		int resourceDialogTopHolo = light ? R.drawable.dialog_top_holo_light : R.drawable.dialog_top_holo_dark;
		int resourceDialogMiddleHolo = light ? R.drawable.dialog_middle_holo_light : R.drawable.dialog_middle_holo_dark;
		int resourceDialogBottomHolo = light ? R.drawable.dialog_bottom_holo_light : R.drawable.dialog_bottom_holo_dark;

		boolean topPanelVisible = topPanelView.getVisibility() == View.VISIBLE;
		boolean contentPanelVisible = contentPanelView.getVisibility() == View.VISIBLE;
		boolean customPanelVisible = customPanelView.getVisibility() == View.VISIBLE;
		boolean buttonPanelVisible = buttonPanelView.getVisibility() == View.VISIBLE;

		List<View> views = new ArrayList<>();
		if (topPanelVisible) views.add(topPanelView);
		if (contentPanelVisible) views.add(contentPanelView);
		if (customPanelVisible) views.add(customPanelView);
		if (buttonPanelVisible) views.add(buttonPanelView);

		if (views.size() == 1) {
			View v = views.get(0);
			v.setBackgroundResource(resourceDialogFullHolo);
		} else {
			for (int i = 0; i < views.size(); i++) {
				View v = views.get(i);

				// top
				if (i == 0) v.setBackgroundResource(resourceDialogTopHolo);

					// bottom
				else if (i == views.size() - 1) v.setBackgroundResource(resourceDialogBottomHolo);

					// middle
				else v.setBackgroundResource(resourceDialogMiddleHolo);
			}
		}
	}
}
