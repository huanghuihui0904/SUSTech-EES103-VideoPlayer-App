package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import static java.lang.Integer.parseInt;

public class VideoActivity extends AppCompatActivity implements View.OnClickListener {
    //进度条
    //private static SeekBar sb;
    //private static TextView tv_progress, tv_total;

    MediaController mMediaController;
    private String name;
    private Intent intent1, intent2;
    //记录服务是否被解绑，默认没有
    //private boolean isUnbind = false;
private VideoView mVideoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);   //全屏

        //获取从frag1传来的信息
        intent1 = getIntent();
        init();
    }

    private void init() {

        mVideoView=new VideoView(this);
        mVideoView=(VideoView) findViewById(R.id.video);
        mMediaController=new MediaController(this);
        mVideoView.setMediaController(mMediaController);
        String position = intent1.getStringExtra("position");
        int i = parseInt(position);
        String uri = frag1.icons[i];
         mVideoView.setVideoURI(Uri.parse(uri));
         mVideoView.start();
        //进度条上小绿点的位置，也就是当前已播放时间
       // tv_progress = (TextView) findViewById(R.id.tv_progress);
        //进度条的总长度，就是总时间
        //tv_total = (TextView) findViewById(R.id.tv_total);
        //进度条的控件
       // sb = (SeekBar) findViewById(R.id.sb);
        //绑定控件的同时设置点击事件监听器
        findViewById(R.id.btn_play).setOnClickListener(this);
        findViewById(R.id.btn_pause).setOnClickListener(this);
        findViewById(R.id.btn_continue_play).setOnClickListener(this);
        name = intent1.getStringExtra("name");
        //创建一个意图对象，是从当前的Activity跳转到Service
       // intent2 = new Intent(this, VideoService.class);
//        conn = new MyServiceConn();//创建服务连接对象
//        bindService(intent2, conn, BIND_AUTO_CREATE);//绑定服务
//        //为滑动条添加事件监听，每个控件不同果然点击事件方法名都不同
        //  sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            //这一行注解是保证API在KITKAT以上的模拟器才能顺利运行，也就是19以上
          //  @RequiresApi(api = Build.VERSION_CODES.KITKAT)
           // @Override
          //  public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //进当滑动条到末端时，结束动画
          //      if (progress == seekBar.getMax()) {
          //          mVideoView.pause();//停止播放动画
          //      }
            }

            //@Override
            //滑动条开始滑动时调用
          //  public void onStartTrackingTouch(SeekBar seekBar) {
          //  }

         //   @Override
            //滑动条停止滑动时调用
         //   public void onStopTrackingTouch(SeekBar seekBar) {
                //根据拖动的进度改变音乐播放进度
         //       int progress = seekBar.getProgress();//获取seekBar的进度
         //       mVideoView.seekTo(progress);//改变播放进度
         //   }
       // });
        //声明并绑定音乐播放器的iv_music控件
     //   ImageView iv_music = (ImageView) findViewById(R.id.iv_music);
      //  String position = intent1.getStringExtra("position");
        //praseInt()就是将字符串变成整数类型
    //    int i = parseInt(position);
     //   iv_music.setImageResource(frag1.icons[i]);
//        //rotation和0f,360.0f就设置了动画是从0°旋转到360°
//        animator = ObjectAnimator.ofFloat(iv_music, "rotation", 0f, 360.0f);
//        animator.setDuration(10000);//动画旋转一周的时间为10秒
//        animator.setInterpolator(new LinearInterpolator());//匀速
//        animator.setRepeatCount(-1);//-1表示设置动画无限循环
  //  }



    //用于实现连接服务，比较模板化，不需要详细知道内容
//    class MyServiceConn implements ServiceConnection {
//        @Override
//        public void onServiceConnected(ComponentName name, IBinder service) {
//            musicControl = (MusicService.MusicControl) service;
//        }
//
//        @Override
//        public void onServiceDisconnected(ComponentName name) {
//
//        }
//    }

    //判断服务是否被解绑
//    private void unbind(boolean isUnbind) {
//        //如果解绑了
//        if (!isUnbind) {
//            mVideoView.pause();//音乐暂停播放
//            unbindService(conn);//解绑服务
//        }
//    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_play://播放按钮点击事件
                String position = intent1.getStringExtra("position");
                int i = parseInt(position);
                 mVideoView.start();
                // musicControl.play(i);
               // animator.start();
                break;
            case R.id.btn_pause://暂停按钮点击事件
                //musicControl.pausePlay();
                mVideoView.pause();
                break;
            case R.id.btn_continue_play://继续播放按钮点击事件
             //   musicControl.continuePlay();
                mVideoView.start();
                break;
//            case R.id.btn_exit://退出按钮点击事件
//                unbind(isUnbind);
//                isUnbind = true;
//                finish();
//                break;
//            case R.id.btn_next:
//                unbind(isUnbind);
//                isUnbind = true;
//                Intent myintent=new Intent(MusicActivity.this,NextSongActivity.class);
//                startActivity(myintent);
//                break;
        }
    }

   // @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        unbind(isUnbind);//解绑服务
//    }
}