package com.jake.huntkey.core.delegates;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
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
        Log.d("DebugPagerFragment", "Title:"+ mTitle+"      onCreate    class: "+ this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("DebugPagerFragment", "Title:"+ mTitle+"      onCreateView    class: "+ this);
        return super.onCreateView(inflater, container, savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("DebugPagerFragment", "Title:"+ mTitle+"      onResume    class: "+ this);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d("DebugPagerFragment", "Title:"+ mTitle+"      onAttach    class: "+ this);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("DebugPagerFragment", "Title:"+ mTitle+"      onDestroyView    class: "+ this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("DebugPagerFragment", "Title:"+ mTitle+"      onDestroy    class: "+ this);

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
//        super.initView(view);
//        mToolbar.setTitle(mTitle);
        TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
        tvTitle.setText(mTitle);
    }
}
