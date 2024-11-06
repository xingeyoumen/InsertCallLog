package com.syxfree.insertcalllog.utils

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.provider.CallLog
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import java.util.Calendar
import java.util.Random
import java.util.concurrent.ThreadLocalRandom
import java.util.concurrent.TimeUnit

/**
 * 1.INCOMING_TYPE: 呼入通话。
 * 2.0UTGOING_TYPE: 呼出通话。
 * 3.MISSED_TYPE: 未接通话
 * 4.VOICEMAIL_TYPE: 语音信箱
 * 5.REJECTED_TYPE: 被用户拒接的通话
 * 6.BLOCKED TYPE: 自动拦截的通话。
 * 7.ANSWERED EXTERNALLY_TYPE: 在另一个设备上接听的通话
 *
 *
 * CallLog.Calls.VOICEMAIL_TYPE 语音信箱，需要额外权限才可以
 */
fun insertMultipleCallLogs(context: Context, count: Int) {

    try {
        val contentResolver = context.contentResolver
        val random = Random()
        val callTypes = intArrayOf(
            CallLog.Calls.INCOMING_TYPE,
            CallLog.Calls.OUTGOING_TYPE,
            CallLog.Calls.MISSED_TYPE,
            //CallLog.Calls.VOICEMAIL_TYPE,
            CallLog.Calls.REJECTED_TYPE,
            CallLog.Calls.BLOCKED_TYPE,
            CallLog.Calls.ANSWERED_EXTERNALLY_TYPE
        )


        val currentTime = System.currentTimeMillis()

        for (i in 1..count) {
            //号码为8-13位
            val phoneNumber = generateRandomPhoneNumber(PHONE_NUM_MIN, PHONE_NUM_MAX, random)
            val callType = callTypes[random.nextInt(callTypes.size)]

            // 新方法
            val callDate = generateRandomTimestampWithinThreeMonths()

            if (callDate > currentTime) {
                Log.e("TAG---------------", "${(callDate > currentTime)}--$currentTime-------$callDate")
                continue
            }
            // 调用这个新方法来获取随机通话时长
            val callDuration = generateRandomCallDuration()
            // 调用 generateRandomName() 方法来获取随机姓名
            val randomName = generateRandomName()
            //Log.e("tag", randomName)
            val values = ContentValues().apply {
                //put(CallLog.Calls.CACHED_NAME, randomName)
                put(CallLog.Calls.NUMBER, phoneNumber)
                put(CallLog.Calls.TYPE, callType)
                put(CallLog.Calls.DATE, callDate)
                //呼入通话和呼出通话有时长
                if (callType == CallLog.Calls.INCOMING_TYPE || callType == CallLog.Calls.OUTGOING_TYPE) {
                    put(CallLog.Calls.DURATION, callDuration)
                } else {
                    put(CallLog.Calls.DURATION, 0)

                }
            }

            //在插入通话记录之前构建了新的 Uri，并设置了 allow_voicemails=true 参数，以便允许插入语音邮件记录
            // 设置allow_voicemails=true
            val uri =
                CallLog.Calls.CONTENT_URI.buildUpon()
                    .appendQueryParameter("allow_voicemails", "true")
                    .build()

            contentResolver.insert(uri, values)
        }
    } catch (e: Exception) {
    }

}


//对应的类型的
fun insertMultipleCallLogs(context: Context, count: Int, phone: String, type: Int) {

    try {
        val contentResolver = context.contentResolver
        val random = Random()
        val callTypes = intArrayOf(
            CallLog.Calls.INCOMING_TYPE,
            CallLog.Calls.OUTGOING_TYPE,
            CallLog.Calls.MISSED_TYPE,
            //CallLog.Calls.VOICEMAIL_TYPE,
            CallLog.Calls.REJECTED_TYPE,
            CallLog.Calls.BLOCKED_TYPE,
            CallLog.Calls.ANSWERED_EXTERNALLY_TYPE
        )


        val currentTime = System.currentTimeMillis()

        for (i in 1..count) {
            //号码为8-13位
            //val phoneNumber = generateRandomPhoneNumber(PHONE_NUM_MIN, PHONE_NUM_MAX, random)
            //val callType = callTypes[random.nextInt(callTypes.size)]
            val phoneNumber = phone
            val callType = type

            // 新方法
            val callDate = generateRandomTimestampWithinThreeMonths()

            if (callDate > currentTime) {
                Log.e("TAG---------------", "${(callDate > currentTime)}--$currentTime-------$callDate")
                continue
            }
            // 调用这个新方法来获取随机通话时长
            val callDuration = generateRandomCallDuration()
            // 调用 generateRandomName() 方法来获取随机姓名
            val randomName = generateRandomName()
            //Log.e("tag", randomName)
            val values = ContentValues().apply {
                //put(CallLog.Calls.CACHED_NAME, randomName)
                put(CallLog.Calls.NUMBER, phoneNumber)
                put(CallLog.Calls.TYPE, callType)
                put(CallLog.Calls.DATE, callDate)
                //呼入通话和呼出通话有时长
                if (callType == CallLog.Calls.INCOMING_TYPE || callType == CallLog.Calls.OUTGOING_TYPE) {
                    put(CallLog.Calls.DURATION, callDuration)
                } else {
                    put(CallLog.Calls.DURATION, 0)

                }
            }

            //在插入通话记录之前构建了新的 Uri，并设置了 allow_voicemails=true 参数，以便允许插入语音邮件记录
            // 设置allow_voicemails=true
            val uri =
                CallLog.Calls.CONTENT_URI.buildUpon()
                    .appendQueryParameter("allow_voicemails", "true")
                    .build()

            contentResolver.insert(uri, values)
        }
    } catch (e: Exception) {
    }

}

/**
 * 生成随机长度为8到13位的电话号码
 */
fun generateRandomPhoneNumber(minLength: Int, maxLength: Int, random: Random): String {
    val length = random.nextInt(maxLength - minLength + 1) + minLength
    val sb = StringBuilder()
    for (i in 0 until length) {
        sb.append(random.nextInt(10))
    }
    return sb.toString()
}


/**
 * 随机生成时间
 *
 * 三个月内
 */
fun generateRandomTimeWithinThreeMonths(): Long {
    val random = Random()
    val currentTime = System.currentTimeMillis()
    //三个月的毫秒数
    val threeMonthsInMillis = TimeUnit.DAYS.toMillis(PHONE_DAYS_CENTER)

    return currentTime - (random.nextLong() % threeMonthsInMillis)
}

fun generateRandomTimestampWithinThreeMonths(): Long {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.MONTH, -3) // 减去三个月
    val threeMonthsAgo = calendar.timeInMillis // 三个月前的时间戳

    val currentTime = System.currentTimeMillis() // 当前时间戳

    return ThreadLocalRandom.current().nextLong(threeMonthsAgo, currentTime)
}

/**
 * 随机时长500秒
 */

fun generateRandomCallDuration(): Int {
    val random = Random()
    return random.nextInt(PHONE_SECONDS_COUNT)
}


/**
 *
 * 生成姓名
 */
fun generateRandomName(): String {
    val nameLength = ThreadLocalRandom.current().nextInt(10, 16) // 生成一个长度在 10 到 15 之间的数字
    val sb = StringBuilder()

    // 生成开头大写字母
    sb.append(('A'..'Z').random())

    // 生成后续小写字母
    repeat(nameLength - 1) {
        sb.append(('a'..'z').random())
    }

    return sb.toString()
}


fun showToast(context: Context, message: Int) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}


fun Activity.hideKeyboard() {
    val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    if (currentFocus != null) {
        inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
    }
}