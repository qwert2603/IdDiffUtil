package com.qwert2603.id_diff_util

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import org.junit.Test

class qqqIdDiffUtilTest {

    data class Item(val id: Long, val name: String)

    @Test
    fun `q q`() {
        val old = listOf(
            Item(1, "a"),
            Item(2, "b"),
            Item(3, "c"),
            Item(4, "d")
        )
        val new = listOf(
            Item(4, "d"),
            Item(1, "a"),
            Item(3, "c"),
            Item(5, "e"),
            Item(6, "f")
        )

//        repeat(1000000) {
        val diffResult = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize(): Int = old.size
            override fun getNewListSize(): Int = new.size
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                old[oldItemPosition].id == new[newItemPosition].id

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                old[oldItemPosition] == new[newItemPosition]
        })
//        }
        diffResult.dispatchUpdatesTo(object : ListUpdateCallback {
            override fun onChanged(position: Int, count: Int, payload: Any?) {
                println("onChanged $position $count")
            }

            override fun onMoved(fromPosition: Int, toPosition: Int) {
                println("onMoved $fromPosition $toPosition")
            }

            override fun onInserted(position: Int, count: Int) {
                println("onInserted $position $count")
            }

            override fun onRemoved(position: Int, count: Int) {
                println("onRemoved $position $count")
            }
        })
    }


}