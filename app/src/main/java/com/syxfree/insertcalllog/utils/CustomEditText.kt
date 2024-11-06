package com.syxfree.insertcalllog.utils

import android.content.Context
import android.util.AttributeSet

class CustomEditText : androidx.appcompat.widget.AppCompatEditText {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onTextContextMenuItem(id: Int): Boolean {
        // Disable copy, paste, cut, select all
        when (id) {
            android.R.id.cut, android.R.id.paste, android.R.id.copy, android.R.id.selectAll -> return false
        }
        return super.onTextContextMenuItem(id)
    }
}