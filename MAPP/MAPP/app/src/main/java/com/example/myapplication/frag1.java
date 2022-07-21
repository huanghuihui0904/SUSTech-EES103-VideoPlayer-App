package com.example.myapplication;

import static java.lang.Integer.parseInt;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

public class frag1 extends Fragment {
    private View view;
    //创建名字的String数组和路径的int数组
     public String[] name={"test0","test1","test2","music0","自己的网址"};
     public static String[] icons={"android.resource://" + "com.example.myapplication" + "/"+R.raw.test0,
             "android.resource://" + "com.example.myapplication" + "/"+R.raw.test1,
             "android.resource://" + "com.example.myapplication" + "/"+R.raw.test2,

             "android.resource://" + "com.example.myapplication" + "/"+R.raw.music0,
             "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4" };   //自己的视频网址

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        //绑定布局，只不过这里是用inflate()方法
        view=inflater.inflate(R.layout.video_list,null);
        //创建listView列表并且绑定控件
        ListView listView=view.findViewById(R.id.lv);
        //实例化一个适配器
        MyBaseAdapter adapter=new MyBaseAdapter();
        //列表设置适配器
        listView.setAdapter(adapter);
        //列表元素的点击监听器
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //创建Intent对象，参数就是从frag1跳转到MusicActivity
                Intent intent=new Intent(frag1.this.getContext(), VideoActivity.class);
                //将名和下标存入Intent对象
                intent.putExtra("name",name[position]);
                intent.putExtra("position",String.valueOf(position));
                //开始跳转

                startActivity(intent);
            }
        });
        return view;
    }
    //这里是创建一个自定义适配器，可以作为模板
    class MyBaseAdapter extends BaseAdapter{
        @Override
        public int getCount(){return  name.length;}
        @Override
        public Object getItem(int i){return name[i];}
        @Override
        public long getItemId(int i){return i;}

        @Override
        public View getView(int i ,View convertView, ViewGroup parent) {
            //绑定好VIew，然后绑定控件

            View view=View.inflate(frag1.this.getContext(),R.layout.item_layout,null);
            ImageView videoContainer=view.findViewById(R.id.list_item_container);
            String uri=frag1.icons[i];
            //这些是获取封面，但是没有实现，因为会使整个project闪退
       //     Bitmap bitmap = null;
//            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
//            try {
//                //根据文件路径获取缩略图
//                retriever.setDataSource(uri);
//                //获得第一帧图片
//                bitmap = retriever.getFrameAtTime();
//            } catch (IllegalArgumentException e) {
//                e.printStackTrace();
//            } finally {
//                retriever.release();
//            }
//            videoContainer.setImageBitmap(bitmap);
          return view;



   }
}
}
