package com.qwert2603.sorted_diff_util

import java.util.*

object SortedDiffUtil {

    private class N<T : Any>(
        val t: T,
        var next: N<T>?
    )

    fun <T : Any, I : Any> calculateDiff(
        oldList: List<T>,
        newList: List<T>,
        itemId: T.() -> I,
        compareOrder: (T, T) -> Int,
        areContentsTheSame: (T, T) -> Boolean,
        getChangePayload: (T, T) -> Any?
    ): SortedDiffResult {

        val oldIds: HashSet<I> = oldList.mapTo(HashSet(), itemId)
        val newIds: HashSet<I> = newList.mapTo(HashSet(), itemId)

        val removes = mutableListOf<ItemsRange>()
        var rem = 0
        for (i in oldList.indices.reversed()) {
            if (oldList[i].itemId() !in newIds) {
                ++rem
            } else {
                if (rem > 0) {
                    removes.add(ItemsRange(i + 1, rem))
                    rem = 0
                }
            }
        }
        if (rem > 0) {
            removes.add(ItemsRange(0, rem))
        }

        val oldFiltered = oldList.filter { it.itemId() in newIds }
        val newFiltered = newList.filter { it.itemId() in oldIds }

        val oldNs = N(oldFiltered[0], null)
        var e: N<T> = oldNs
        for (i in 1..oldFiltered.lastIndex) {
            val n = N(oldFiltered[i], null)
            e.next = n
            e = n
        }

        var nOld = oldNs
        var iOld = 0
        val moves = mutableListOf<ItemMove>()
        while (nOld.next != null) {
            if (nOld.t.itemId() == newFiltered[iOld].itemId()) {
                nOld = nOld.next!!
                ++iOld
            } else {
                var sN = nOld // node before one, that is moved.
                var iWN = iOld + 1 // index from that node is moved.
                while (sN.next!!.t.itemId() != newFiltered[iOld].itemId()) {
                    sN = sN.next!!
                    ++iWN
                }
                var movedCount = 1

//                sN = sN.next!!
//                ++iWN
//                while (sN.next!=null&&sN.next!!.t.itemId() != newFiltered[iOld].itemId()) {
//                    sN = sN.next!!
//                    ++iWN
//                    ++movedCount
//                }

                for (i in -movedCount + 1..0) {
                    moves.add(ItemMove(iWN + i, iOld + i))
                }
                sN.next = sN.next!!.next
                iOld += movedCount
                repeat(movedCount - 1) { nOld = nOld.next!! }
            }
        }

        val inserts = mutableListOf<ItemsRange>()
        var ins = 0
        for (i in newList.indices) {
            if (newList[i].itemId() !in oldIds) {
                ++ins
            } else {
                if (ins > 0) {
                    inserts.add(ItemsRange(i - ins, ins))
                    ins = 0
                }
            }
        }
        if (ins > 0) {
            inserts.add(ItemsRange(newList.size - ins, ins))
        }

        return SortedDiffResult(
            removes = removes,
            moves = moves,
            changes = emptyList(),
            inserts = inserts
        )
    }
}