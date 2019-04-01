package com.qwert2603.id_diff_util

import androidx.recyclerview.widget.DiffUtil
import org.junit.Test

class qqqIdDiffUtilTest {

    data class Item(val id: Long, val name: String)

    @Test
    fun `q q`() {
        val old = listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19)
        val new = listOf(0, 3, 1, 2, 4, 5, 7, 8, 9, 6, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19)

        repeat(1000000) {
            val diffResult = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun getOldListSize(): Int = old.size
                override fun getNewListSize(): Int = new.size
                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                    old[oldItemPosition] == new[newItemPosition]

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                    old[oldItemPosition] == new[newItemPosition]
            })
        }
//        diffResult.dispatchUpdatesTo(object : ListUpdateCallback {
//            override fun onChanged(position: Int, count: Int, payload: Any?) {
//                println("onChanged $position $count")
//            }
//
//            override fun onMoved(fromPosition: Int, toPosition: Int) {
//                println("onMoved $fromPosition $toPosition")
//            }
//
//            override fun onInserted(position: Int, count: Int) {
//                println("onInserted $position $count")
//            }
//
//            override fun onRemoved(position: Int, count: Int) {
//                println("onRemoved $position $count")
//            }
//        })
    }


}