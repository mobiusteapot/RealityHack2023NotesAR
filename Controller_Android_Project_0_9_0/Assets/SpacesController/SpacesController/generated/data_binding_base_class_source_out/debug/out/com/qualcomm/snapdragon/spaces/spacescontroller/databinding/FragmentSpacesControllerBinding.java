// Generated by view binder compiler. Do not edit!
package com.qualcomm.snapdragon.spaces.spacescontroller.databinding;

import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.qualcomm.snapdragon.spaces.spacescontroller.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentSpacesControllerBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final ImageButton cameraButton;

  @NonNull
  public final SurfaceView cameraPreview;

  @NonNull
  public final ImageView qrCode;

  @NonNull
  public final ConstraintLayout spacesContainer;

  @NonNull
  public final Guideline spacesProfileBottom;

  @NonNull
  public final Guideline spacesProfileBottomMargin;

  @NonNull
  public final Guideline spacesProfileTop;

  @NonNull
  public final Guideline spacesProfileTopMargin;

  private FragmentSpacesControllerBinding(@NonNull ConstraintLayout rootView,
      @NonNull ImageButton cameraButton, @NonNull SurfaceView cameraPreview,
      @NonNull ImageView qrCode, @NonNull ConstraintLayout spacesContainer,
      @NonNull Guideline spacesProfileBottom, @NonNull Guideline spacesProfileBottomMargin,
      @NonNull Guideline spacesProfileTop, @NonNull Guideline spacesProfileTopMargin) {
    this.rootView = rootView;
    this.cameraButton = cameraButton;
    this.cameraPreview = cameraPreview;
    this.qrCode = qrCode;
    this.spacesContainer = spacesContainer;
    this.spacesProfileBottom = spacesProfileBottom;
    this.spacesProfileBottomMargin = spacesProfileBottomMargin;
    this.spacesProfileTop = spacesProfileTop;
    this.spacesProfileTopMargin = spacesProfileTopMargin;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentSpacesControllerBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentSpacesControllerBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_spaces_controller, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentSpacesControllerBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.camera_button;
      ImageButton cameraButton = ViewBindings.findChildViewById(rootView, id);
      if (cameraButton == null) {
        break missingId;
      }

      id = R.id.camera_preview;
      SurfaceView cameraPreview = ViewBindings.findChildViewById(rootView, id);
      if (cameraPreview == null) {
        break missingId;
      }

      id = R.id.qr_code;
      ImageView qrCode = ViewBindings.findChildViewById(rootView, id);
      if (qrCode == null) {
        break missingId;
      }

      id = R.id.spaces_container;
      ConstraintLayout spacesContainer = ViewBindings.findChildViewById(rootView, id);
      if (spacesContainer == null) {
        break missingId;
      }

      id = R.id.spaces_profile_bottom;
      Guideline spacesProfileBottom = ViewBindings.findChildViewById(rootView, id);
      if (spacesProfileBottom == null) {
        break missingId;
      }

      id = R.id.spaces_profile_bottom_margin;
      Guideline spacesProfileBottomMargin = ViewBindings.findChildViewById(rootView, id);
      if (spacesProfileBottomMargin == null) {
        break missingId;
      }

      id = R.id.spaces_profile_top;
      Guideline spacesProfileTop = ViewBindings.findChildViewById(rootView, id);
      if (spacesProfileTop == null) {
        break missingId;
      }

      id = R.id.spaces_profile_top_margin;
      Guideline spacesProfileTopMargin = ViewBindings.findChildViewById(rootView, id);
      if (spacesProfileTopMargin == null) {
        break missingId;
      }

      return new FragmentSpacesControllerBinding((ConstraintLayout) rootView, cameraButton,
          cameraPreview, qrCode, spacesContainer, spacesProfileBottom, spacesProfileBottomMargin,
          spacesProfileTop, spacesProfileTopMargin);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}