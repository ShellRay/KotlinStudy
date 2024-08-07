package com.kotlin.study.activity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.jennifer.andy.simpleeyes.utils.ScreenUtils;
import com.kotlin.study.R;
import com.kotlin.study.utils.ConvenientUtil;
import com.kotlin.study.widget.mobike.MobikeView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import me.yokeyword.fragmentation.helper.internal.AnimatorHelper;


public class MobikeDemo extends AppCompatActivity {

    private MobikeView mobikeView;
    private TextView tvAdd;
    private TextView tvRemove;

    private SensorManager sensorManager;
    private Sensor defaultSensor;

    private ArrayList<View> arrayList = new ArrayList<View>();
    private int[] imgs = {
            R.drawable.share_fb,
            R.drawable.share_kongjian,
            R.drawable.share_pyq,
            R.drawable.share_qq,
            R.drawable.share_tw,
            R.drawable.share_wechat,
            R.drawable.share_weibo,
    };

    private Timer timer = new Timer(true);
    private TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            removeByRecycle(radius);
            Log.e("shellray","centerX=="+centerX+"---centerY="+centerY);
            mobikeView.getmMobike().onBigRotate(centerX,centerY,angle);
        }
    };

    double angle = 0f;
    int radius = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobike_demo);
        mobikeView = (MobikeView) findViewById(R.id.mobike_view);
        tvAdd = (TextView) findViewById(R.id.tvAdd);
        tvRemove = (TextView) findViewById(R.id.tvRemove);

        initViews();

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        defaultSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);


        tvAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(arrayList.size() == 0) {
                    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ScreenUtils.INSTANCE.dp2px(MobikeDemo.this,84), ScreenUtils.INSTANCE.dp2px(MobikeDemo.this,84));
                    layoutParams.gravity = Gravity.CENTER;
                    ImageView imageView = new ImageView(MobikeDemo.this);
                    imageView.setImageResource(imgs[6]);
                    imageView.setTag(R.id.mobike_view_circle_tag,true);
                    mobikeView.addView(imageView,layoutParams);
                    arrayList.add(imageView);
                }else {
                    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ScreenUtils.INSTANCE.dp2px(MobikeDemo.this,60), ScreenUtils.INSTANCE.dp2px(MobikeDemo.this,60));
                    layoutParams.gravity = Gravity.TOP;
                    ImageView imageView = new ImageView(MobikeDemo.this);
                    imageView.setImageResource(imgs[1]);
                    imageView.setTag(R.id.mobike_view_circle_tag,true);
                    mobikeView.addView(imageView,layoutParams);
                    arrayList.add(imageView);
                }
            }
        });

        radius = 3* ScreenUtils.INSTANCE.dp2px(MobikeDemo.this, 84)/2 ;
        int screenHeight = ScreenUtils.INSTANCE.dp2px(MobikeDemo.this, 156) ;
        int screenWidth = ScreenUtils.getScreenWidth(MobikeDemo.this);

        int ban = screenWidth/2;

        tvRemove.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                timer.schedule(timerTask,100, 100);
            }
        });
    }

    public float centerX;
    public float centerY;
    /**
     * 小球按圆周运动
     */
    public void removeByRecycle(int radius) {
        double offsetx = radius * 1.1 * Math.sin(angle * Math.PI / 180);
        double offsety = radius * 1.1 * (1 - Math.cos(angle * Math.PI / 180));
//        angle += 0.5;
        angle += 15;
        if (angle > 360) {
            angle = 0;
        }
        double offsetx2 = radius * 1.1 * Math.sin(angle * Math.PI / 180);
        double offsety2 = radius * 1.1 * (1 - Math.cos(angle * Math.PI / 180));
        this.centerX += offsetx2 - offsetx;
        this.centerY += offsety2 - offsety;
    }

    private void initViews() {
//        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
//        layoutParams.gravity = Gravity.CENTER;
//        for(int i = 0; i < imgs.length  ; i ++){
//            ImageView imageView = new ImageView(this);
//            imageView.setImageResource(imgs[i]);
//            imageView.setTag(R.id.mobike_view_circle_tag,true);
//            mobikeView.addView(imageView,layoutParams);
//        }

        arrayList.clear();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mobikeView.getmMobike().onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mobikeView.getmMobike().onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        sensorManager.registerListener(listerner, defaultSensor, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        sensorManager.unregisterListener(listerner);
    }

    private SensorEventListener listerner = new SensorEventListener(){

        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
                float x =  event.values[0];
                float y =   event.values[1] * 2.0f;
                mobikeView.getmMobike().onSensorChanged(-x,y);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };
}
