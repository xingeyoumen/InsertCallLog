package com.syxfree.insertcalllog.utils

import android.app.job.JobParameters
import android.app.job.JobService
import android.os.Handler
import android.os.Looper
import android.provider.CallLog
import android.widget.Toast
import com.syxfree.insertcalllog.R


//具体插入通话记录的JobService--重写类型
class InsertCallLogService2 : JobService() {
    private val handler = Handler(Looper.getMainLooper())


    //CallLog.Calls.INCOMING_TYPE,
    //CallLog.Calls.OUTGOING_TYPE,

    override fun onStartJob(params: JobParameters): Boolean {
        val yourIntValue = params.extras.getInt(PARAM_KEY, PARAM_COUNT)
        val yourIntValuePhone = params.extras.getString(PARAM_PHONE).toString()
        val yourIntType = params.extras.getInt(PARAM_TYPE, CallLog.Calls.INCOMING_TYPE)
        // 使用 yourIntValue 进行后续操作
        Thread {
            // 在这里执行耗时操作
            insertMultipleCallLogs(this, yourIntValue,yourIntValuePhone,yourIntType)

            // 任务完成后调用 jobFinished() 方法
            jobFinished(params, false)

            //Log.d("CallLogJobService", "Inserting call log...") // 输出日志消息
            showToast(resources.getString(R.string.insert_counts_num, yourIntValue));
        }.start()
        // 返回 true 表示任务在后台执行
        return true
    }

    override fun onStopJob(params: JobParameters): Boolean {
        handler.removeCallbacksAndMessages(null)
        return true
    }


    private fun showToast(message: String) {
        handler.post {
            Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
        }
    }

}
