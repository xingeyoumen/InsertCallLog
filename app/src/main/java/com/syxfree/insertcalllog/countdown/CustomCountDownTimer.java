package com.syxfree.insertcalllog.countdown;

import android.os.CountDownTimer;

public class CustomCountDownTimer {

    private static CustomCountDownTimer instance;
    private CountDownTimer countDownTimer;
    private long countDownNum;
    private CountDownListener countDownListener;

    private CustomCountDownTimer() {
        // Private constructor to prevent instantiation
    }

    public static CustomCountDownTimer getInstance() {
        if (instance == null) {
            instance = new CustomCountDownTimer();
        }
        return instance;
    }

    public void start(long countDownNum, CountDownListener listener) {
        this.countDownNum = countDownNum;
        this.countDownListener = listener;

        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }

        countDownTimer = new CountDownTimer(countDownNum, 1000) {
            public void onTick(long millisUntilFinished) {
                if (countDownListener != null) {
                    countDownListener.onTick(millisUntilFinished);
                }
            }

            public void onFinish() {
                if (countDownListener != null) {
                    countDownListener.onFinish();
                }
            }
        }.start();
    }

    public void cancel() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
    }

    public interface CountDownListener {
        void onTick(long millisUntilFinished);
        void onFinish();
    }
}
