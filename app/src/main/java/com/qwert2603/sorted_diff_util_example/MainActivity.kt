package com.qwert2603.sorted_diff_util_example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.qwert2603.sorted_diff_util.SortedDiffUtil

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        SortedDiffUtil.calculateDiff(
            oldList = listOf(1, 2, 3),
            newList = listOf(2, 3, 4),
            itemId = { this },
            compareOrder = Int::compareTo,
            areContentsTheSame = Int::equals,
            getChangePayload = { _, _ -> null }
        )
    }
}
