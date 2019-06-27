package com.jake.huntkey.core.delegates;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.jake.huntkey.core.R;
import com.jake.huntkey.core.delegates.basedelegate.BaseBackDelegate;

import me.yokeyword.fragmentation.SupportFragment;


public class DebugPagerFragment extends BaseBackDelegate {
    private static final String ARG_TYPE = "arg_type";

    private String mTitle;

    public static DebugPagerFragment newInstance(String title) {

        Bundle args = new Bundle();
        args.putString(ARG_TYPE, title);
        DebugPagerFragment fragment = new DebugPagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTitle = getArguments().getString(ARG_TYPE);

    }

    @Override
    public Object setLayout() {
        return R.layout.debug_test_layout;
    }


    @Override
    protected void onBindView(Bundle savedInstanceState, View rootView) {
        initView(rootView);
    }

    protected void initView(View view) {
        super.initView(view);
        mToolbar.setTitle(mTitle);
        TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
        tvTitle.setText(mTitle);
    }
}
