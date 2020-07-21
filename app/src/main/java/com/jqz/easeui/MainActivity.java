package com.jqz.easeui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.EaseConstant;
import com.jqz.easeui.adapter.MyRecyclerAdapter;
import com.jqz.easeui.ui.LoginActivity;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private TextView tv_show;
    private RecyclerView rlv;
    private ArrayList<Frends> frends;
    private MyRecyclerAdapter myRecyclerAdapter;
    private boolean b=false;
    private boolean isPermissionRequested=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        getSupportActionBar().hide();//隐藏标题栏
        initView();
        checkPresmission();
        String name = (String) SpUtil.getParam("name", "");
        getSupportActionBar().setTitle("当前用户：" + name);
    }

    private void checkPresmission() {
        //需要申请权限
        if (Build.VERSION.SDK_INT >= 23 && !isPermissionRequested) {

            isPermissionRequested = true;

            ArrayList<String> permissionsList = new ArrayList<>();

            String[] permissions = {
                    android.Manifest.permission.RECORD_AUDIO,
                    android.Manifest.permission.CAMERA,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.ACCESS_FINE_LOCATION
            };
            for (String perm : permissions) {
                if (PackageManager.PERMISSION_GRANTED != checkSelfPermission(perm)) {
                    permissionsList.add(perm);
                    // 进入到这里代表没有权限.
                }
            }

            if (permissionsList.isEmpty()) {
                return;
            } else {
                requestPermissions(permissionsList.toArray(new String[permissionsList.size()]), 0);
            }
        }
    }

    private void initView() {
        tv_show = (TextView) findViewById(R.id.tv_show);
        rlv = (RecyclerView) findViewById(R.id.rlv);

        String name = (String) SpUtil.getParam("name", "");
        String psd = (String) SpUtil.getParam("psd", "");

        tv_show.setText("用户名：" + name);

        initRlv();

    }

    private void initRlv() {
        rlv.setLayoutManager(new LinearLayoutManager(this));
        rlv.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));

        frends = new ArrayList<>();

        frends.add(new Frends("aaa","男",""));
        frends.add(new Frends("bbb","女",""));
        frends.add(new Frends("ccc","男",""));
        frends.add(new Frends("ddd","女",""));

        myRecyclerAdapter = new MyRecyclerAdapter(frends, this);
        rlv.setAdapter(myRecyclerAdapter);
        myRecyclerAdapter.notifyDataSetChanged();


        myRecyclerAdapter.setOnClickListenner(new MyRecyclerAdapter.OnClickListenner() {
            @Override
            public void onClick(int position) {
                //跳转到聊天页面
                Frends frends1 = frends.get(position);
                SpUtil.setParam("user",frends1.getName());

                String name = frends1.getName();
                if(name.equals(SpUtil.getParam("name",""))){
                    Toast.makeText(MainActivity.this, "不能与自己聊天", Toast.LENGTH_SHORT).show();
                }else{
//                    startActivity(new Intent(MainActivity.this,ChatActivity.class));
                    Intent intent = new Intent(MainActivity.this, ChatActivity.class);
//username为对方的环信id
                    String user = (String) SpUtil.getParam("user", "");
                    intent.putExtra(EaseConstant.EXTRA_USER_ID, user);
                    startActivity(intent);
                }


            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(1, 1, 1, "退出登录");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == 1) {
            //退出登录
            EMClient.getInstance().logout(true);

            //退出登录后跳转到登录页面从新登录
            startActivity(new Intent(MainActivity.this, LoginActivity.class));

            Toast.makeText(this, "退出登录成功！", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
