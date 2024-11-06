package com.syxfree.insertcalllog.countdown;


import android.annotation.TargetApi;
import android.os.Build;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.KeyboardShortcutGroup;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SearchEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;

import androidx.annotation.Nullable;

import java.util.List;

/**
 * 启动：
 * 请求----333333   onWindowAttributesChanged
 * 请求----333333   onAttachedToWindow
 * 请求----333333   onCreatePanelView
 * 请求----333333   onCreatePanelMenu
 * 请求----333333   onPreparePanel
 * 请求----333333   onWindowFocusChanged：true
 *
 * 跳转页面：
 * 请求----333333   onWindowFocusChanged：false
 */
public class TouchCallBack implements Window.Callback {
    Window.Callback callback;

    public TouchCallBack(Window.Callback callback) {
        this.callback = callback;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        //Log.e("请求----333333", "dispatchKeyEvent");
        return callback.dispatchKeyEvent(keyEvent);
    }

    @Override
    public boolean dispatchKeyShortcutEvent(KeyEvent keyEvent) {
        //Log.e("请求----333333", "dispatchKeyShortcutEvent");
        return callback.dispatchKeyShortcutEvent(keyEvent);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        //Log.e("请求----333333", "dispatchTouchEvent");
        return callback.dispatchTouchEvent(motionEvent);
    }

    @Override
    public boolean dispatchTrackballEvent(MotionEvent motionEvent) {
        //Log.e("请求----333333", "dispatchTrackballEvent");
        return callback.dispatchTrackballEvent(motionEvent);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    @Override
    public boolean dispatchGenericMotionEvent(MotionEvent motionEvent) {
        //Log.e("请求----333333", "dispatchGenericMotionEvent");
        return callback.dispatchGenericMotionEvent(motionEvent);
    }

    @Override
    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        //Log.e("请求----333333", "dispatchPopulateAccessibilityEvent");
        return callback.dispatchPopulateAccessibilityEvent(accessibilityEvent);

    }

    @Override
    public View onCreatePanelView(int featureId) {
        //Log.e("请求----333333", "onCreatePanelView");
        return callback.onCreatePanelView(featureId);
    }

    @Override
    public boolean onCreatePanelMenu(int featureId, Menu menu) {
        //Log.e("请求----333333", "onCreatePanelMenu");
        return callback.onCreatePanelMenu(featureId, menu);
    }

    @Override
    public boolean onPreparePanel(int featureId, View view, Menu menu) {
        //Log.e("请求----333333", "onPreparePanel");
        return callback.onPreparePanel(featureId, view, menu);
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        //Log.e("请求----333333", "onMenuOpened");
        return callback.onMenuOpened(featureId, menu);
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        //Log.e("请求----333333", "onMenuItemSelected");
        return callback.onMenuItemSelected(featureId, item);
    }

    @Override
    public void onWindowAttributesChanged(WindowManager.LayoutParams attrs) {
        //Log.e("请求----333333", "onWindowAttributesChanged");
        callback.onWindowAttributesChanged(attrs);
    }

    @Override
    public void onContentChanged() {
        //Log.e("请求----333333", "onContentChanged");
        callback.onContentChanged();
    }

    //当前window 是否还可见
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        //Log.e("请求----333333", "onWindowFocusChanged：" + hasFocus);
        callback.onWindowFocusChanged(hasFocus);
    }

    @Override
    public void onAttachedToWindow() {
        //Log.e("请求----333333", "onAttachedToWindow");
        callback.onAttachedToWindow();
    }

    @Override
    public void onDetachedFromWindow() {
        //Log.e("请求----333333", "onDetachedFromWindow");
        callback.onDetachedFromWindow();
    }

    @Override
    public void onPanelClosed(int featureId, Menu menu) {
        //Log.e("请求----333333", "onPanelClosed");
        callback.onPanelClosed(featureId, menu);
    }

    @Override
    public boolean onSearchRequested() {
        //Log.e("请求----333333", "onSearchRequested");
        return callback.onSearchRequested();
    }

    @Override
    public boolean onSearchRequested(SearchEvent searchEvent) {
        return false;
    }

    @Override
    public ActionMode onWindowStartingActionMode(ActionMode.Callback callback) {
        //Log.e("请求----333333", "onWindowStartingActionMode");
        return this.callback.onWindowStartingActionMode(callback);
    }

    @Nullable
    @Override
    public ActionMode onWindowStartingActionMode(ActionMode.Callback callback, int type) {
        //Log.e("请求----333333", "onWindowStartingActionMode");
        return null;
    }

    @Override
    public void onActionModeStarted(ActionMode mode) {
        //Log.e("请求----333333", "onActionModeStarted");
        callback.onActionModeStarted(mode);
    }

    @Override
    public void onActionModeFinished(ActionMode mode) {
        //Log.e("请求----333333", "onActionModeFinished");
        callback.onActionModeFinished(mode);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        //Log.e("请求----333333", "onPointerCaptureChanged");
        Window.Callback.super.onPointerCaptureChanged(hasCapture);
    }

    @Override
    public void onProvideKeyboardShortcuts(List<KeyboardShortcutGroup> data, @Nullable Menu menu, int deviceId) {
        //Log.e("请求----333333", "onProvideKeyboardShortcuts");
        Window.Callback.super.onProvideKeyboardShortcuts(data, menu, deviceId);
    }
}