// Generated by view binder compiler. Do not edit!
package com.qualcomm.snapdragon.spaces.spacescontroller.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.qualcomm.snapdragon.spaces.spacescontroller.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentHapticSettingsBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final SwitchCompat hapticFeedback;

  @NonNull
  public final LinearLayout hapticStrength;

  @NonNull
  public final AppCompatSeekBar hapticStrengthSeekbar;

  private FragmentHapticSettingsBinding(@NonNull ConstraintLayout rootView,
      @NonNull SwitchCompat hapticFeedback, @NonNull LinearLayout hapticStrength,
      @NonNull AppCompatSeekBar hapticStrengthSeekbar) {
    this.rootView = rootView;
    this.hapticFeedback = hapticFeedback;
    this.hapticStrength = hapticStrength;
    this.hapticStrengthSeekbar = hapticStrengthSeekbar;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentHapticSettingsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentHapticSettingsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_haptic_settings, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentHapticSettingsBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.haptic_feedback;
      SwitchCompat hapticFeedback = ViewBindings.findChildViewById(rootView, id);
      if (hapticFeedback == null) {
        break missingId;
      }

      id = R.id.haptic_strength;
      LinearLayout hapticStrength = ViewBindings.findChildViewById(rootView, id);
      if (hapticStrength == null) {
        break missingId;
      }

      id = R.id.haptic_strength_seekbar;
      AppCompatSeekBar hapticStrengthSeekbar = ViewBindings.findChildViewById(rootView, id);
      if (hapticStrengthSeekbar == null) {
        break missingId;
      }

      return new FragmentHapticSettingsBinding((ConstraintLayout) rootView, hapticFeedback,
          hapticStrength, hapticStrengthSeekbar);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}