package com.jake.huntkey.core.delegates;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jake.huntkey.core.R;
import com.jake.huntkey.core.R2;
import com.jake.huntkey.core.activitys.ChangePasswdActivity;
import com.jake.huntkey.core.activitys.LoginActivity;
import com.jake.huntkey.core.app.Consts;
import com.jake.huntkey.core.delegates.basedelegate.BaseBackDelegate;

import butterknife.BindView;
import butterknife.OnClick;


public class MineFragment extends BaseBackDelegate {
    private static final String ARG_TYPE = "arg_type";
    private static final int REQUEST_CODE = 1;
    @BindView(R2.id.id_tv_job_number)
    TextView idTvJobNumber;
    @BindView(R2.id.id_tv_dept)
    TextView idTvDept;
    @BindView(R2.id.id_tv_change_passwd)
    TextView idTvChangePasswd;
    @BindView(R2.id.id_tv_name)
    TextView idTvName;
    @BindView(R2.id.id_tv_phone_number)
    TextView idTvPhoneNumber;

    private String mTitle;

    public static MineFragment newInstance(String title) {

        Bundle args = new Bundle();
        args.putString(ARG_TYPE, title);
        MineFragment fragment = new MineFragment();
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
        return R.layout.mine_delegate_layout;
    }


    @Override
    protected void onBindView(Bundle savedInstanceState, View rootView) {
        initView(rootView);
    }

    protected void initView(View view) {
        idTvJobNumber.setText("工号:" + SPUtils.getInstance(Consts.SP_INSTANT_NAME).getString(Consts.SP_ITEM_USER_JOB_NUMBER));
        idTvDept.setText("部门:" + SPUtils.getInstance(Consts.SP_INSTANT_NAME).getString(Consts.SP_ITEM_DEPT_NAME));
        idTvName.setText(SPUtils.getInstance(Consts.SP_INSTANT_NAME).getString(Consts.SP_ITEM_USER_NAME));
        idTvPhoneNumber.setText("手机号:" + SPUtils.getInstance(Consts.SP_INSTANT_NAME).getString(Consts.SP_ITEM_PHONE_NUMBER));
    }

    @OnClick(R2.id.id_tv_change_passwd)
    public void onViewClicked() {
        Intent intent = new Intent(_mActivity, ChangePasswdActivity.class);
        getActivity().startActivityFromFragment(this, intent, REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == ChangePasswdActivity.RESULT_CODE) {
            _mActivity.finish();
            ActivityUtils.startActivity(LoginActivity.class);//密码修改成功，重新登录
        }
    }

}
