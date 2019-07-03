// Generated code from Butter Knife. Do not modify!
package com.codepath.apps.restclienttemplate;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ComposeActivity_ViewBinding<T extends ComposeActivity> implements Unbinder {
  protected T target;

  @UiThread
  public ComposeActivity_ViewBinding(T target, View source) {
    this.target = target;

    target.message = Utils.findRequiredViewAsType(source, R.id.messageTweet, "field 'message'", EditText.class);
    target.counter = Utils.findRequiredViewAsType(source, R.id.tvCounter, "field 'counter'", TextView.class);
    target.postTweetBtn = Utils.findRequiredViewAsType(source, R.id.postTweet, "field 'postTweetBtn'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.message = null;
    target.counter = null;
    target.postTweetBtn = null;

    this.target = null;
  }
}
