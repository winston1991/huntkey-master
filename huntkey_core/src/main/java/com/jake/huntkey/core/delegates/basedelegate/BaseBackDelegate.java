package com.jake.huntkey.core.delegates.basedelegate;

import android.view.View;

import androidx.appcompat.widget.Toolbar;

import com.jake.huntkey.core.R;

public abstract class BaseBackDelegate extends CheckPermissionDelegate {
    protected Toolbar mToolbar;

    protected void initView(View view) {
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        if (mToolbar != null) {
            initToolbarNav(mToolbar);
        }
    }

    protected void initToolbarNav(Toolbar toolbar) {

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _mActivity.onBackPressed();
            }
        });
    }

}
