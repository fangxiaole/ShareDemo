package com.lele.sharedemo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lele.sharedemo.R;
import com.libhttp.entity.LoginResult;
import com.libhttp.subscribers.SubscriberListener;
import com.p2p.core.P2PSpecial.HttpErrorCode;
import com.p2p.core.P2PSpecial.HttpSend;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Context context;
    private EditText et_account, et_pwd;
    private Button bt_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = this;
        initUI();
    }

    private void initUI() {
        et_account = (EditText) findViewById(R.id.et_account);
        et_pwd = (EditText) findViewById(R.id.et_pwd);
        bt_login = (Button) findViewById(R.id.bt_login);
        bt_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bt_login) {
            login();
        }
    }

    private void login() {
        String account = et_account.getText().toString().trim();
        String password = et_pwd.getText().toString().trim();
        if (TextUtils.isEmpty(account)) {
            Toast.makeText(context, R.string.input_account, Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(context, R.string.input_pwd, Toast.LENGTH_SHORT).show();
            return;
        }
        HttpSend.getInstance().login(account, password, new SubscriberListener<LoginResult>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onNext(LoginResult loginResult) {
                switch (loginResult.getError_code()){
                    case HttpErrorCode.ERROR_0:
                        //登录成功
                        Intent login=new Intent(context,MainActivity.class);
                        startActivity(login);
                        finish();
                        break;
                    default:
                        break;
                }


            }

            @Override
            public void onError(String error_code, Throwable throwable) {

            }
        });
    }
}
