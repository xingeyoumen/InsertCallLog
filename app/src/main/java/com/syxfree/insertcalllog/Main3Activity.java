package com.syxfree.insertcalllog;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.syxfree.insertcalllog.countdown.TouchCallBack;

import java.util.ArrayList;
import java.util.List;

//test CountDownTimer
public class Main3Activity extends AppCompatActivity {


    public static final String KEY_STR2 = "str2";

    /**
     * 倒计时 时间
     * <p>
     * 10s
     */
    private long countDownNum = 10000l;


    private CountDownTimer countDownTimer;


    private RecyclerView recyclerView;
    private DemoStrAdapter horizontalAdapter;


    private ImageView[] dotViews;//创建存放图片集合

    private LinearLayout img_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        img_layout = findViewById(R.id.img_layout);

        String str1 = getIntent().getStringExtra(Main2Activity.KEY_STR1);

        Log.e("TAG", "str1；" + str1);


        // 启动活动的示例代码
        findViewById(R.id.clickadd_test_sms).setOnClickListener(view -> {
            //String str2 = "Hello from Main3Activity";
            //Intent intent = new Intent();
            //intent.putExtra(KEY_STR2, str2);
            //setResult(RESULT_OK, intent);
            //finish();


            Intent intent = new Intent(Main3Activity.this, Main2Activity.class);
            startActivity(intent);

        });


        touchCountdown();


        //使用当前的列表之中的最大倒计时市场，在外层倒计时，全部修改后重新刷新列表。每秒刷新一次。





        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        List<String> grtgstrings = new ArrayList<>();
        grtgstrings.add("1111111111");
        grtgstrings.add("1111111112");
        grtgstrings.add("1111111113");
        grtgstrings.add("1111111114");
        grtgstrings.add("1111111115");
        grtgstrings.add("1111111116");

        horizontalAdapter = new DemoStrAdapter(this, grtgstrings);
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);

        recyclerView.setAdapter(horizontalAdapter);


        //View snapView = snapHelper.findSnapView(recyclerView.getLayoutManager());
        //int position = recyclerView.getLayoutManager().getPosition(snapView);
        //Log.e("position","position:"+position);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);


                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                // 在这里处理当前显示的第一条数据的位置
                // firstVisibleItemPosition 就是当前显示的第一条数据的位置
                Log.e("firstVisibleItem", "firstVisibleItemPosition:" + firstVisibleItemPosition);
                hytgfredrfhgtyhgtfrd(firstVisibleItemPosition);
            }


        });


        //生成相应数量的导航小圆点
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //设置小圆点左右之间的间隔
        params.setMargins(10, 0, 10, 0);
        //得到页面个数
        dotViews = new ImageView[6];//需要设置圆点个数
        for (int i = 0; i < 6; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(params);
            imageView.setImageResource(R.mipmap.stsrerferc2);//初始化六个灰色Img
            if (i == 0) {
                //默认启动时，选中第一个小圆点
                imageView.setSelected(true);
            } else {
                imageView.setSelected(false);//其他的设置不选择
            }
            //得到每个小圆点的引用，用于滑动页面时，（onPageSelected方法中）更改它们的状态。
            dotViews[i] = imageView;
            dotViews[0].setImageResource(R.mipmap.stsrerferc1);//设置第一个页面选择
            //添加到布局里面显示
            img_layout.addView(imageView);//这里的img_layout就是我在布局中写的一个Linear Layout用来存放这些圆点img

        }







        // 假设您有一个TextView
        TextView textView = findViewById(R.id.textView);

        // 获取TextView中的文本内容
        String text = textView.getText().toString();

        // 根据文本长度决定是否添加图片
        if (text.length() > 0) {
            // 创建一个SpannableString对象，用于在文本末尾添加图片
            SpannableString spannableString = new SpannableString(text);

            // 获取您想要添加的图片资源
            Drawable drawable = getResources().getDrawable(R.mipmap.icon_arrow);

            // 设置图片的大小
            int imageSize = (int) (textView.getTextSize()); // 设置图片大小为文本大小
            drawable.setBounds(0, 0, imageSize, imageSize);

            // 创建一个ImageSpan对象，用于在文本末尾添加图片
            ImageSpan imageSpan = new ImageSpan(drawable, ImageSpan.ALIGN_BASELINE);

            // 将ImageSpan对象添加到SpannableString中
            spannableString.setSpan(imageSpan, text.length()-1, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            // 设置TextView的文本为带有图片的SpannableString
            textView.setText(spannableString);
        }


    }

    private void hytgfredrfhgtyhgtfrd(int firstVisibleItemPosition) {


        //这里是配合圆点指示器实现的，可以忽略
        for (int i = 0; i < dotViews.length; i++) {
            if (firstVisibleItemPosition == i) {
                dotViews[i].setSelected(true);
                dotViews[i].setImageResource(R.mipmap.stsrerferc1);
            } else {
                dotViews[i].setSelected(false);
                dotViews[i].setImageResource(R.mipmap.stsrerferc2);
            }
        }


    }

    private void touchCountdown() {

        Window window = getWindow();
        window.setCallback(new TouchCallBack(window.getCallback()) {
            @Override
            public boolean dispatchTouchEvent(MotionEvent motionEvent) {
                dispatchCountEvent();
                return super.dispatchTouchEvent(motionEvent);
            }
        });


    }

    //倒计时之后弹出
    private void dispatchCountEvent() {

        //防止触摸时多次执行
        if (null != countDownTimer) {
            countDownTimer.cancel();
            countDownTimer = null;
        }

        countDownTimer = new CountDownTimer(countDownNum, 1000) {
            public void onTick(long millisUntilFinished) {
                Log.e("请求----", "seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                Log.e("请求----", "onFinish()");

            }
        }.start();
    }

    private void countCancle() {

        if (null != countDownTimer) {
            countDownTimer.cancel();
            countDownTimer = null;
        }

    }


    @Override
    public void onStop() {
        super.onStop();
        countCancle();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        countCancle();

    }
}