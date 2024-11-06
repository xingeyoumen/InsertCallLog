package com.syxfree.insertcalllog;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;

import com.syxfree.insertcalllog.countdown.CustomCountDownTimer;
import com.syxfree.insertcalllog.countdown.TouchCallBack;

/**
 * test CountDownTimer
 *
 * 触摸屏幕后重新计时
 * 不触摸后开始倒计时，结束之后执行
 */
public class Main4Activity extends AppCompatActivity {


    public static final String KEY_STR2 = "str2";

    /**
     * 倒计时 时间
     *
     * 10s
     */
    private long countDownNum = 10000l;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        String str1 = getIntent().getStringExtra(Main2Activity.KEY_STR1);

        Log.e("TAG", "str1；" + str1);


        // 启动活动的示例代码
        findViewById(R.id.clickadd_test_sms).setOnClickListener(view -> {
            //String str2 = "Hello from Main3Activity";
            //Intent intent = new Intent();
            //intent.putExtra(KEY_STR2, str2);
            //setResult(RESULT_OK, intent);
            //finish();


            Intent intent = new Intent(Main4Activity.this, Main2Activity.class);
            startActivity(intent);

        });


        touchCountdown();


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
        CustomCountDownTimer instance = CustomCountDownTimer.getInstance();
        instance.start(countDownNum, new CustomCountDownTimer.CountDownListener() {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.e("请求----", "seconds remaining: " + millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                Log.e("请求----", "onFinish()");
            }
        });
    }

    private void countCancle() {

        CustomCountDownTimer.getInstance().cancel();

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