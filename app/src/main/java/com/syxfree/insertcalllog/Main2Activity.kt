package com.syxfree.insertcalllog

import android.app.role.RoleManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Telephony
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.syxfree.insertcalllog.databinding.ActivityTestBinding

//test
class Main2Activity : AppCompatActivity() {
    private lateinit var binding: ActivityTestBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        //setContentView(R.layout.activity_test)

        //var text_title_show = findViewById<AppCompatTextView>(R.id.text_title_show)
        //原默认短信
        val defaultSmsPkg = Telephony.Sms.getDefaultSmsPackage(this)
        binding.textTitleShow.text = defaultSmsPkg
        // 启动活动的示例代码
        binding.clickaddTestSms.setOnClickListener { _: View? ->
            //jumpStartCurrentActivity()

//            OpenSmsDefult()
            askDefaultSmsHandlerPermission()

        }
        // 恢复-启动活动的示例代码
        binding.clickseelogTestSms.setOnClickListener { _: View? ->
            //jumpStartCurrentActivity()
            val defaultSmsPkg = "com.samsung.android.messaging"
            CancleSmsDefult(defaultSmsPkg)

        }
    }

    private fun askDefaultSmsHandlerPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val roleManager: RoleManager = getSystemService(RoleManager::class.java)
            // check if the app is having permission to be as default SMS app
            val isRoleAvailable = roleManager.isRoleAvailable(RoleManager.ROLE_SMS)
            if (isRoleAvailable) {
                // check whether your app is already holding the default SMS app role.
                val isRoleHeld = roleManager.isRoleHeld(RoleManager.ROLE_SMS)
                if (!isRoleHeld) {
                    val roleRequestIntent =
                        roleManager.createRequestRoleIntent(RoleManager.ROLE_SMS)
                    startActivity(roleRequestIntent)
                }
            }
        } else {
            val intent = Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT)
            intent.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME, packageName)
            startActivity(intent)
        }
    }

    //打开默认的短信app。要将当前应用程序设置为默认的短信应用程序
    private fun OpenSmsDefult() {
        val defaultSmsPkg = Telephony.Sms.getDefaultSmsPackage(this)
        val mySmsPkg = this.packageName
        if (defaultSmsPkg != mySmsPkg) {
            //如果这个App不是默认的Sms App，则修改成默认的SMS APP
            //因为从Android 4.4开始，只有默认的SMS APP才能对SMS数据库进行处理
            val intent = Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT)
            intent.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME, mySmsPkg)
            startActivity(intent)
        }
    }


    //恢复
    private fun CancleSmsDefult(defaultSmsPkg: String) {

        //val defaultSmsPkg = Telephony.Sms.getDefaultSmsPackage(this)
        val mySmsPkg = this.packageName

        if (defaultSmsPkg != mySmsPkg) {
            //如果这个App不是默认的Sms App，则修改成默认的SMS APP
            //因为从Android 4.4开始，只有默认的SMS APP才能对SMS数据库进行处理
            val intent = Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT)
            intent.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME, mySmsPkg)
            startActivity(intent)
        }

    }


    private fun jumpStartCurrentActivity() {
        val intent = Intent(this, Main3Activity::class.java)
        intent.putExtra(KEY_STR1, "Hello from Main2Activity")
        activityResultLauncher.launch(intent)
    }


    //ActivityResultLauncher 用于启动活动并处理返回结果
    var activityResultLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult<Intent, ActivityResult>(
            ActivityResultContracts.StartActivityForResult()
        )  //当启动的活动返回结果时，onActivityResult() 方法中的 result -> {} 代码块将被执行
        { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                val data = result.data
                // 处理返回的数据
                if (data != null) {
                    val str2 = data.getStringExtra(Main3Activity.KEY_STR2)
                    // 处理返回的结果 str2
                    Log.e("TAG", "str2；$str2")
                }
            }
        }


    companion object {
        const val KEY_STR1: String = "str1"
        private const val REQUEST_CODE = 1
    }
}