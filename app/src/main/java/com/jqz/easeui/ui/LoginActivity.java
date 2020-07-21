package com.jqz.easeui.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.jqz.easeui.MainActivity;
import com.jqz.easeui.R;
import com.jqz.easeui.SpUtil;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_name;
    private EditText et_psd;
    private Button bt_login;
    private Button bt_zc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boolean loggedInBefore = EMClient.getInstance().isLoggedInBefore();
//
        if(loggedInBefore){
            //以下两个方法是为了保证进入主页面后本地会话和群组都 load 完毕。
            EMClient.getInstance().groupManager().loadAllGroups();
            EMClient.getInstance().chatManager().loadAllConversations();
            startActivity(new Intent(this, MainActivity.class));
            finish();//关闭本页面
            return;
        }

        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        et_name = (EditText) findViewById(R.id.et_name);
        et_psd = (EditText) findViewById(R.id.et_psd);
        bt_login = (Button) findViewById(R.id.bt_login);
        bt_zc = (Button) findViewById(R.id.bt_zc);

        bt_login.setOnClickListener(this);
        bt_zc.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_login:
                login();
                break;
            case R.id.bt_zc:

                //跳转到注册页面
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                break;
        }
    }

    private void login() {
        final String name = et_name.getText().toString();
        final String psd = et_psd.getText().toString();

        if(name.isEmpty()||psd.isEmpty()){
            Toast.makeText(this, "账号或密码为空！", Toast.LENGTH_SHORT).show();
        }else{
            //调用环信的登录功能，
            EMClient.getInstance().login(name,psd,new EMCallBack() {//回调
                @Override
                public void onSuccess() {//登录成功会回调此方法
                    //以下两个方法是为了保证进入主页面后本地会话和群组都 load 完毕。
                    EMClient.getInstance().groupManager().loadAllGroups();
                    EMClient.getInstance().chatManager().loadAllConversations();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LoginActivity.this, "登录聊天服务器成功！", Toast.LENGTH_SHORT).show();
                            //登录成功跳转页面
                            //记录登录的账号，在后面的页面中显示
                            SpUtil.setParam("name",name);
                            SpUtil.setParam("psd",psd);
                            startActivity(new Intent(LoginActivity.this,MainActivity.class));
                            //跳转页面后关闭登录页面
                            finish();
                        }
                    });
//                    Log.d("main", "登录聊天服务器成功！");
                }

                @Override
                public void onProgress(int progress, String status) {
                    //进度
                }

                @Override
                public void onError(int code, final String message) {
                    //登录失败会回调此方法
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LoginActivity.this, "登录聊天服务器失败！"+message, Toast.LENGTH_SHORT).show();

                        }
                    });
//                    Log.d("main", "登录聊天服务器失败！");
                }
            });
        }



    }




}
