// Generated by view binder compiler. Do not edit!
package com.qualcomm.snapdragon.spaces.spacescontroller.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.qualcomm.snapdragon.spaces.spacescontroller.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentDisplaySettingsBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final RadioButton radioAutoMode;

  @NonNull
  public final RadioButton radioDarkMode;

  @NonNull
  public final RadioGroup radioGroupMode;

  @NonNull
  public final RadioButton radioLightMode;

  private FragmentDisplaySettingsBinding(@NonNull ConstraintLayout rootView,
      @NonNull RadioButton radioAutoMode, @NonNull RadioButton radioDarkMode,
      @NonNull RadioGroup radioGroupMode, @NonNull RadioButton radioLightMode) {
    this.rootView = rootView;
    this.radioAutoMode = radioAutoMode;
    this.radioDarkMode = radioDarkMode;
    this.radioGroupMode = radioGroupMode;
    this.radioLightMode = radioLightMode;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentDisplaySettingsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentDisplaySettingsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_display_settings, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentDisplaySettingsBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.radio_auto_mode;
      RadioButton radioAutoMode = ViewBindings.findChildViewById(rootView, id);
      if (radioAutoMode == null) {
        break missingId;
      }

      id = R.id.radio_dark_mode;
      RadioButton radioDarkMode = ViewBindings.findChildViewById(rootView, id);
      if (radioDarkMode == null) {
        break missingId;
      }

      id = R.id.radio_group_mode;
      RadioGroup radioGroupMode = ViewBindings.findChildViewById(rootView, id);
      if (radioGroupMode == null) {
        break missingId;
      }

      id = R.id.radio_light_mode;
      RadioButton radioLightMode = ViewBindings.findChildViewById(rootView, id);
      if (radioLightMode == null) {
        break missingId;
      }

      return new FragmentDisplaySettingsBinding((ConstraintLayout) rootView, radioAutoMode,
          radioDarkMode, radioGroupMode, radioLightMode);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}