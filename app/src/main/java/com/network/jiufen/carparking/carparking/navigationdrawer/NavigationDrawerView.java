package com.network.jiufen.carparking.carparking.navigationdrawer;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

import com.network.jiufen.carparking.carparking.R;
import com.network.jiufen.carparking.carparking.model.NavigationDrawerItem;
import com.network.jiufen.carparking.carparking.ui.misc.BetterViewAnimator;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Michal Bialas on 19/07/14.
 */
public class NavigationDrawerView extends BetterViewAnimator {

  @BindView(R.id.leftDrawerListView)
  ListView leftDrawerListView;

  private final NavigationDrawerAdapter adapter;

  public NavigationDrawerView(Context context, AttributeSet attrs) {
    super(context, attrs);
    adapter = new NavigationDrawerAdapter(context);
  }

  public void replaceWith(List<NavigationDrawerItem> items) {
    adapter.replaceWith(items);
    setDisplayedChildId(R.id.leftDrawerListView);
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();
    ButterKnife.bind(this);
    leftDrawerListView.setAdapter(adapter);
  }

  public NavigationDrawerAdapter getAdapter() {
    return adapter;
  }
}
