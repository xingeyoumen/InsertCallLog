package com.syxfree.insertcalllog.testresult;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.syxfree.insertcalllog.R;

//test
public class MainTest2Activity extends AppCompatActivity {

    public static final String KEY_STR1 = "str1";
    private static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_test);

        // 启动活动的示例代码
        findViewById(R.id.clickadd_test_sms).setOnClickListener(view -> {


            jumpStartCurrentActivity();


        });
    }



    private void jumpStartCurrentActivity() {
        Intent intent = new Intent(this, MainTest3Activity.class);
        intent.putExtra(KEY_STR1, "Hello from Main2Activity");
        activityResultLauncher.launch(intent);
    }


    //ActivityResultLauncher 用于启动活动并处理返回结果
    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            //当启动的活动返回结果时，onActivityResult() 方法中的 result -> {} 代码块将被执行
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    Intent data = result.getData();
                    // 处理返回的数据
                    if (data != null) {
                        String str2 = data.getStringExtra(MainTest3Activity.KEY_STR2);
                        // 处理返回的结果 str2
                        Log.e("TAG", "str2；" + str2);
                    }

                }
            });


}