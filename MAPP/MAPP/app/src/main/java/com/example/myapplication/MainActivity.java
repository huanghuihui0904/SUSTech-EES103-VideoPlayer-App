package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //创建需要用到的控件的变量
    private TextView tv1,tv2,tv3;
    private FragmentManager fm;
    private FragmentTransaction ft;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //绑定控件
        tv1=(TextView)findViewById(R.id.menu1);
        tv2=(TextView)findViewById(R.id.menu2);
        tv3=(TextView)findViewById(R.id.menu3);
        //设置监听器，固定写法
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);
        //若是继承FragmentActivity，fm=getFragmentManger();
        fm=getSupportFragmentManager();
        //fm可以理解为Fragment显示的管理者，ft就是它的改变者
        ft=fm.beginTransaction();
        //默认情况下就显示frag1
        ft.replace(R.id.content,new frag1());
        //提交改变的内容
        ft.commit();
    }
    @Override
    //控件的点击事件
    public void onClick(View v){
        ft=fm.beginTransaction();
        //切换选项卡
        switch (v.getId()){
            case R.id.menu1:
                ft.replace(R.id.content,new frag1());
                break;
            case R.id.menu2:
                ft.replace(R.id.content,new frag2());
                break;
            case R.id.menu3:
                ft.replace(R.id.content,new frag3());
                break;
            default:
                break;
        }
        ft.commit();
    }
}