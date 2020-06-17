package com.lqs.kuaishou.kuaishou1801.login.view;

import android.view.View;
import android.widget.EditText;

import com.lqs.kuaishou.kuaishou1801.R;
import com.lqs.kuaishou.kuaishou1801.base.BaseMVPFragment;
import com.lqs.kuaishou.kuaishou1801.common.KSConstant;
import com.lqs.kuaishou.kuaishou1801.home.MainActivity;
import com.lqs.kuaishou.kuaishou1801.login.contract.LoginContract;
import com.lqs.kuaishou.kuaishou1801.login.mode.LoginBean;
import com.lqs.kuaishou.kuaishou1801.login.presenter.LoginPresenterImpl;
import com.lqs.kuaishou.kuaishou1801.manager.KsUserManager;

public class LoginFragment extends BaseMVPFragment<LoginPresenterImpl, LoginContract.ILoginView>
        implements LoginContract.ILoginView, View.OnClickListener,LoginRegisterActivity.INameInterface {

    private EditText nameEditText;
    private EditText passwordEditText;

    @Override
    protected void initHttpData() {

    }

    @Override
    protected void initPresenter() {
        ihttpPresenter = new LoginPresenterImpl();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initView() {
        nameEditText = findViewById(R.id.name);
        passwordEditText = findViewById(R.id.password);
        findViewById(R.id.loginButton).setOnClickListener(this);
        findViewById(R.id.registerTv).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.loginButton:
                login();
                break;
            case R.id.registerTv:
                switchRegisterFragment();
                break;
                default:
                    break;
        }

    }

    private void switchRegisterFragment() {
        LoginRegisterActivity loginRegisterActivity = (LoginRegisterActivity)getActivity();
        loginRegisterActivity.switchFragment(1, nameEditText.getText().toString().trim());//
    }

    private void login() {
        ihttpPresenter.login(nameEditText.getText().toString().trim(), passwordEditText.getText().toString().trim());
    }

    @Override
    public void onLogin(LoginBean loginBean) {
        showMessage("登录成功");
        //实现跳转到MainActivity，显示HomeFragment,Activity的启动模式问题.
        getActivity().finish();//是不是一定能回到MainActivity，这个不一定，因为，MainActivity有可能被系统回收.
        KsUserManager.getInstance().setLoginBean(loginBean);//登录成功后，将登录返回的数据存储到manger中
        launchActivity(MainActivity.class, null);
    }

    @Override
    public void showError(String code, String message) {
        if (code.equals(KSConstant.USER_NOT_REGISTER_ERROR)) {
            switchRegisterFragment();
        }
        showMessage(code + ":" + message);

    }

    @Override
    public void showLoaing() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void setName(String name) {
        nameEditText.setText(name);
        passwordEditText.setText("");
    }
}
