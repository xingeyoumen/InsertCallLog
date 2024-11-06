package com.syxfree.insertcalllog.testresult;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.syxfree.insertcalllog.R;

//test
public class MainTest3Activity extends AppCompatActivity {

    public static final String KEY_STR2 = "str2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        String str1 = getIntent().getStringExtra(MainTest2Activity.KEY_STR1);

        Log.e("TAG", "str1；" + str1);


        // 启动活动的示例代码
        findViewById(R.id.clickadd_test_sms).setOnClickListener(view -> {
            String str2 = "Hello from Main3Activity";
            Intent intent = new Intent();
            intent.putExtra(KEY_STR2, str2);
            setResult(RESULT_OK, intent);
            finish();
        });
    }
}