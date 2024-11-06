package com.syxfree.insertcalllog

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.provider.CallLog
import android.text.TextUtils
import android.util.Log
import android.widget.EditText
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.permissionx.guolindev.PermissionX
import com.syxfree.insertcalllog.utils.InsertCallLogService
import com.syxfree.insertcalllog.utils.InsertCallLogService2
import com.syxfree.insertcalllog.utils.PARAM_KEY
import com.syxfree.insertcalllog.utils.PARAM_PHONE
import com.syxfree.insertcalllog.utils.PARAM_TYPE
import com.syxfree.insertcalllog.utils.hideKeyboard
import com.syxfree.insertcalllog.utils.showToast

/**
 * 插入手机通话记录
 */
class MainActivity : AppCompatActivity() {

    /**
     * 刚才我们体验了StartActivityForResult和RequestPermission这两种Contract，分别用于在两个Activity之间交换数据，以及请求运行时权限。它们都是Activity Result API中内置的Contract。
     *
     * 那么除此之外，我们还有哪些内置Contract可以使用呢？
     *
     * StartActivityForResult()
     * StartIntentSenderForResult()
     * RequestMultiplePermissions()
     * RequestPermission()
     * TakePicturePreview()
     * TakePicture()
     * TakeVideo()
     * PickContact()
     * GetContent()
     * GetMultipleContents()
     * OpenDocument()
     * OpenMultipleDocuments()
     * OpenDocumentTree()
     * CreateDocument()
     *
     */


    //两个参数，第一个这里使用了StartActivityForResult这种Contract
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

    //由于这次是请求运行时权限，因此不能再使用刚才的StartActivityForResult来作为Contract了，而是要使用RequestPermission这种Contract
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            //另外由于指定了不同的Contract类似，Lambda表达式的参数也会发生变化。现在Lambda表达式会传入一个布尔型的参数，用于告诉我们用户是否允许了我们请求的权限。
            if (granted) {
                // User allow the permission.
            } else {
                // User deny the permission.
            }
        }


    //换了一下Contract类型
    private val takePictureLauncher = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
        // bitmap from camera


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)


        // Set an OnTouchListener to the root layout of the activity
        findViewById<ConstraintLayout>(R.id.rootView_layout).setOnClickListener {
            // Hide the keyboard when a touch event is detected
            hideKeyboard()


            //requestPermissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)

            //由于TakePicturePreview这个Contract不需要输入参数，所以我们调用launch()方法的时候直接传入null就可以了
            //takePictureLauncher.launch(null)
        }


        var countSingle = findViewById<EditText>(R.id.add_count_config)
        //点击获取权限，有权限上传
        findViewById<AppCompatTextView>(R.id.clickadd).setOnClickListener {

            hideKeyboard()

            PermissionX.init(this).permissions(
                android.Manifest.permission.READ_CALL_LOG,
                android.Manifest.permission.WRITE_CALL_LOG
            ).request { allGranted, _, _ ->
                if (allGranted) {

                    if (TextUtils.isEmpty(countSingle.getText().toString())) {
                        showToast(this, R.string.insert_empty)
                        return@request
                    }
                    InsertCallLog(countSingle.getText().toString().toInt())
                } else {

                    //拒绝了权限
                    showToast(this, R.string.rejected_call)
                }
            }
        }




        var countSinglePhone = findViewById<EditText>(R.id.add_count_config2)
        //点击获取权限，有权限上传--插入类型INCOMING_TYPE
        findViewById<AppCompatTextView>(R.id.clickadd2).setOnClickListener {

            hideKeyboard()

            PermissionX.init(this).permissions(
                android.Manifest.permission.READ_CALL_LOG,
                android.Manifest.permission.WRITE_CALL_LOG
            ).request { allGranted, _, _ ->
                if (allGranted) {

                    if (TextUtils.isEmpty(countSingle.getText().toString())) {
                        showToast(this, R.string.insert_empty)
                        return@request
                    }

                    if (TextUtils.isEmpty(countSinglePhone.getText().toString())) {
                        showToast(this, R.string.insert_empty)
                        return@request
                    }
                    InsertCallLog2(countSingle.getText().toString().toInt(),countSinglePhone.getText().toString(),CallLog.Calls.INCOMING_TYPE)
                } else {

                    //拒绝了权限
                    showToast(this, R.string.rejected_call)
                }
            }
        }
        //点击获取权限，有权限上传--插入类型OUTGOING_TYPE
        findViewById<AppCompatTextView>(R.id.clickseelog2).setOnClickListener {

            hideKeyboard()

            PermissionX.init(this).permissions(
                android.Manifest.permission.READ_CALL_LOG,
                android.Manifest.permission.WRITE_CALL_LOG
            ).request { allGranted, _, _ ->
                if (allGranted) {

                    if (TextUtils.isEmpty(countSingle.getText().toString())) {
                        showToast(this, R.string.insert_empty)
                        return@request
                    }

                    if (TextUtils.isEmpty(countSinglePhone.getText().toString())) {
                        showToast(this, R.string.insert_empty)
                        return@request
                    }
                    InsertCallLog2(countSingle.getText().toString().toInt(),countSinglePhone.getText().toString(),CallLog.Calls.OUTGOING_TYPE)
                } else {

                    //拒绝了权限
                    showToast(this, R.string.rejected_call)
                }
            }
        }

        //点击获取权限，有权限查看通话记录
        findViewById<AppCompatTextView>(R.id.clickseelog).setOnClickListener {

            hideKeyboard()

            PermissionX.init(this).permissions(
                android.Manifest.permission.READ_CALL_LOG,
                android.Manifest.permission.WRITE_CALL_LOG
            ).request { allGranted, _, _ ->
                if (allGranted) {
                    ViewCallLog()
                } else {
                    //拒绝了权限
                    showToast(this, R.string.rejected_call)
                }
            }
        }

        //点击获取test
        findViewById<AppCompatTextView>(R.id.clickadd_sms).setOnClickListener {

            hideKeyboard()

            val intent = Intent(this, Main2Activity::class.java)
            startActivity(intent)
        }


    }


    //开启一个IntentService InsertCallLog
    private fun InsertCallLog(yourIntValue: Int) {

        showToast(this, R.string.insert_wait)
        // 创建一个指定要启动的服务类
        scheduleJob(yourIntValue)
    }


    //执行JobService
    private fun scheduleJob(yourIntValue: Int) {
        val extras = PersistableBundle()
        extras.putInt(PARAM_KEY, yourIntValue)


        val serviceComponent = ComponentName(this, InsertCallLogService::class.java)
        val jobInfo = JobInfo.Builder(0, serviceComponent)
            // 设置一些可选的参数，比如网络条件，延迟执行时间等
            //有网时配置
            //.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
            .setExtras(extras).build()

        val jobScheduler = ContextCompat.getSystemService(this, JobScheduler::class.java)
        jobScheduler?.schedule(jobInfo)
    }


    //开启一个IntentService InsertCallLog
    private fun InsertCallLog2(yourIntValue: Int, toString: String, incomingType: Int) {

        showToast(this, R.string.insert_wait)
        // 创建一个指定要启动的服务类
        scheduleJob2(yourIntValue,toString,incomingType)
    }


    //执行JobService
    private fun scheduleJob2(yourIntValue: Int,toString: String, incomingType: Int) {
        val extras = PersistableBundle()
        extras.putInt(PARAM_KEY, yourIntValue)
        extras.putString(PARAM_PHONE, toString)
        extras.putInt(PARAM_TYPE, incomingType)


        val serviceComponent = ComponentName(this, InsertCallLogService2::class.java)
        val jobInfo = JobInfo.Builder(0, serviceComponent)
            // 设置一些可选的参数，比如网络条件，延迟执行时间等
            //有网时配置
            //.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
            .setExtras(extras).build()

        val jobScheduler = ContextCompat.getSystemService(this, JobScheduler::class.java)
        jobScheduler?.schedule(jobInfo)
    }


    //查看通话记录列表
    private fun ViewCallLog() {
        // 创建一个Intent对象，指定ACTION_VIEW动作以查看通话记录
        val intent = Intent(Intent.ACTION_VIEW)
        // 设置URI为通话记录内容提供程序的URI
        intent.setData(CallLog.Calls.CONTENT_URI)
        // 启动Intent以查看通话记录
        startActivity(intent)
    }

}
