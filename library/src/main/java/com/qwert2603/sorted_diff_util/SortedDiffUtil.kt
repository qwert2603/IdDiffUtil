package com.qwert2603.sorted_diff_util

object SortedDiffUtil {

    private fun <T : Any, I : Any> List<T>.positionsById(itemId: T.() -> I): HashMap<I, Int> {
        val result = HashMap<I, Int>()
        this.forEachIndexed { index, t -> result[t.itemId()] = index }
        return result
    }

    private fun <T : Any, I : Any> calculateRemoves(
        oldList: List<T>,
        itemId: T.() -> I,
        newPositions: HashMap<I, Int>
    ): List<ItemsRange> {
        val removes = mutableListOf<ItemsRange>()
        var rem = 0
        for (i in oldList.indices.reversed()) {
            if (oldList[i].itemId() !in newPositions) {
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
        return removes
    }

    private fun <T : Any, I : Any> calculateInserts(
        newList: List<T>,
        itemId: T.() -> I,
        oldPositions: HashMap<I, Int>
    ): List<ItemsRange> {
        val inserts = mutableListOf<ItemsRange>()
        var ins = 0
        for (i in newList.indices) {
            if (newList[i].itemId() !in oldPositions) {
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
        return inserts
    }

    fun <T : Any, I : Any> calculateDiff(
        oldList: List<T>,
        newList: List<T>,
        itemId: T.() -> I,
        compareOrder: (T, T) -> Int,
        areContentsTheSame: (T, T) -> Boolean,
        getChangePayload: (T, T) -> Any?
    ): SortedDiffResult {

        val oldPositions: HashMap<I, Int> = oldList.positionsById(itemId)
        val newPositions: HashMap<I, Int> = newList.positionsById(itemId)

        val removes: List<ItemsRange> = calculateRemoves(oldList, itemId, newPositions)

        val oldFiltered = oldList.filter { it.itemId() in newPositions }
        val newFiltered = newList.filter { it.itemId() in oldPositions }

        val inserts: List<ItemsRange> = calculateInserts(newList, itemId, oldPositions)

        return SortedDiffResult(
            removes = removes,
            moves = emptyList(),
            changes = emptyList(),
            inserts = inserts
        )
    }
}