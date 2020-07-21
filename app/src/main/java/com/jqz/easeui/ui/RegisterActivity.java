package com.jqz.easeui.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.jqz.easeui.R;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_name_register;
    private EditText et_psd_register;
    private Button bt_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    private void initView() {
        et_name_register = (EditText) findViewById(R.id.et_name_register);
        et_psd_register = (EditText) findViewById(R.id.et_psd_register);
        bt_register = (Button) findViewById(R.id.bt_register);

        bt_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_register:
                register();
                break;
        }
    }

    private void register() {
        //环信注册账号
        final String name = et_name_register.getText().toString();
        final String psd = et_psd_register.getText().toString();
        if(name.isEmpty()||psd.isEmpty()){
            Toast.makeText(this, "账号密码不能为空！", Toast.LENGTH_SHORT).show();
        }else{
            new Thread(){
                @Override
                public void run() {
                    //注册失败会抛出HyphenateException
                    try {
                        EMClient.getInstance().createAccount(name, psd);//同步方法
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(RegisterActivity.this, "注册成功！", Toast.LENGTH_SHORT).show();
                                finish();//关闭注册页面

                            }
                        });
                    } catch (HyphenateException e) {
                        e.printStackTrace();
                        Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();

                    }
                }
            }.start();
        }
    }

}
