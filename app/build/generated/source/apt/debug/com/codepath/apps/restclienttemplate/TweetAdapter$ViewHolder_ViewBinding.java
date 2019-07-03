// Generated code from Butter Knife. Do not modify!
package com.codepath.apps.restclienttemplate;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class TweetAdapter$ViewHolder_ViewBinding<T extends TweetAdapter.ViewHolder> implements Unbinder {
  protected T target;

  @UiThread
  public TweetAdapter$ViewHolder_ViewBinding(T target, View source) {
    this.target = target;

    target.tvUsername = Utils.findRequiredViewAsType(source, R.id.tvUsername, "field 'tvUsername'", TextView.class);
    target.tvBody = Utils.findRequiredViewAsType(source, R.id.tvBody, "field 'tvBody'", TextView.class);
    target.tvRelativeTime = Utils.findRequiredViewAsType(source, R.id.tvRelativeTime, "field 'tvRelativeTime'", TextView.class);
    target.ivProfileImage = Utils.findRequiredViewAsType(source, R.id.ivProfileImage, "field 'ivProfileImage'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.tvUsername = null;
    target.tvBody = null;
    target.tvRelativeTime = null;
    target.ivProfileImage = null;

    this.target = null;
  }
}
