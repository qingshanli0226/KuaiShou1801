package com.lqs.kuaishou.kuaishou1801.login.view;

import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.lqs.kuaishou.kuaishou1801.R;
import com.lqs.kuaishou.kuaishou1801.base.BaseMVPFragment;
import com.lqs.kuaishou.kuaishou1801.login.contract.RegisterContract;
import com.lqs.kuaishou.kuaishou1801.login.mode.RegisterBean;
import com.lqs.kuaishou.kuaishou1801.login.presenter.RegisterPresenterImpl;

public class RegisterFragment extends BaseMVPFragment<RegisterPresenterImpl, RegisterContract.IRegisterView>
        implements RegisterContract.IRegisterView, View.OnClickListener,LoginRegisterActivity.INameInterface {
    private EditText nameEditText;
    private EditText passwordEditText;

    @Override
    protected void initHttpData() {
    }

    @Override
    protected void initPresenter() {
        ihttpPresenter = new RegisterPresenterImpl();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_register;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        nameEditText = findViewById(R.id.name);
        passwordEditText = findViewById(R.id.password);

        findViewById(R.id.registerButton).setOnClickListener(this);
        findViewById(R.id.loginTv).setOnClickListener(this);
    }

    @Override
    public void onRegister(RegisterBean registerBean) {
        showMessage("注册成功");
        switchLoginFragment();
    }

    @Override
    public void showError(String code, String message) {

    }

    @Override
    public void showLoaing() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.registerButton:
                register();
                break;
            case R.id.loginTv:
                switchLoginFragment();
                break;
                default:
                    break;
        }

    }

    private void switchLoginFragment() {
        LoginRegisterActivity loginRegisterActivity = (LoginRegisterActivity)getActivity();
        loginRegisterActivity.switchFragment(0, nameEditText.getText().toString().trim());//
    }

    private void register() {
        ihttpPresenter.register(nameEditText.getText().toString().trim(), passwordEditText.getText().toString().trim());
    }

    @Override
    public void setName(String name) {
        nameEditText.setText(name);
        passwordEditText.setText("");
    }
}
