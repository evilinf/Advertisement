package com.example.advertisement;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private TextView  imageDesc;
    private LinearLayout point_group;
    private final int[] imageids={R.mipmap.a,R.mipmap.b,R.mipmap.c,R.mipmap.d,R.mipmap.e};
    private ArrayList<ImageView> imageList;
    private final String[] description={"第一张","第二张","第三张","第四张","第五张"};
    private int lastPointPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager=(ViewPager)findViewById(R.id.viewpager);
        imageDesc=(TextView)findViewById(R.id.image_desc);
        point_group=(LinearLayout)findViewById(R.id.point_group);
        //初始化图片资源
        imageList=new ArrayList<ImageView>();
        for(int i=0;i<imageids.length;i++){
            ImageView image=new ImageView(this);
            image.setBackgroundResource(imageids[i]);
            imageList.add(image);
            //添加指示点
            ImageView point=new ImageView(this);
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(5,5);
            params.rightMargin=20;
            point.setLayoutParams(params);
            point.setBackgroundResource(R.drawable.point_bg);
            if(i==0){
                point.setEnabled(true);
            }else{
                point.setEnabled(false);
            }
            point_group.addView(point);
        }
        imageDesc.setText(description[0]);

        viewPager.setAdapter(new MyPagerAdapter());
        viewPager.setCurrentItem(Integer.MAX_VALUE/2-(Integer.MAX_VALUE/2)%imageList.size());
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            //当页面正在滑动时回掉
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            //页面切换后调用 position新的页面位置
            @Override
            public void onPageSelected(int position) {
                position=position%imageList.size();
                //设置页面切换时的文字
                imageDesc.setText(description[position]);
                //设置页面切换时的指示点状态，把当前页面的enabled设为true,把上个页面设为false
                point_group.getChildAt(position).setEnabled(true);
                point_group.getChildAt(lastPointPosition).setEnabled(false);
                lastPointPosition=position;
            }
            //当页面状态发生变化时回调
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        //自动循环：1、Timer定时器 2、开子线程while true 循环  3、ClockManager 4、Handler发送延时信息实现循环
//        handler.sendEmptyMessageDelayed(0,2000);
    }
//    private Handler handler=new Handler() {
//        @Override
//        public void handleMessage(android.os.Message msg){
//            viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
//            handler.sendEmptyMessageDelayed(0,2000);
//        };
////        @Override
////        public void publish(LogRecord logRecord) {
////
////        }
////
////        @Override
////        public void flush() {
////
////        }
////
////        @Override
////        public void close() throws SecurityException {
////
////        }
//    };
    private class MyPagerAdapter extends PagerAdapter{
        //获得页面的总数
        @Override
        public int getCount() {
//            return imageList.size();
            return Integer.MAX_VALUE;
        }
        //判断view和object的对应关系
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }
        //获得当前位置上的view   container是view的容器
        @Override
        public Object instantiateItem(ViewGroup container,int position){
            //给container添加内容
            container.addView(imageList.get(position%imageList.size()));
            return imageList.get(position%imageList.size());
        }
        //销毁对应位置上的object
        @Override
        public void destroyItem(ViewGroup container,int position,Object object){
            container.removeView((View)object);
            object=null;
        }
    }
}
