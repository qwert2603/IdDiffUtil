package com.qwert2603.sorted_diff_util

object SortedDiffUtil {

    inline fun <T : Any, I : Any> List<T>.positionsById(crossinline itemId: T.() -> I): HashMap<I, Int> {
        val result = HashMap<I, Int>()
        this.forEachIndexed { index, t -> result[t.itemId()] = index }
        return result
    }

    inline fun <T : Any, I : Any> calculateRemoves(
        oldList: List<T>,
        crossinline itemId: T.() -> I,
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

    inline fun <T : Any, I : Any> calculateInserts(
        newList: List<T>,
        crossinline itemId: T.() -> I,
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

    inline fun <T : Any, I : Any> calculateMoves(
        oldList: List<T>,
        newList: List<T>,
        crossinline itemId: T.() -> I,
        oldPositions: HashMap<I, Int>,
        newPositions: HashMap<I, Int>
    ): List<ItemMove> {
        val moves = mutableListOf<ItemMove>()

        val oldFiltered: List<T> = oldList.filter { it.itemId() in newPositions }
        val newFiltered: List<T> = newList.filter { it.itemId() in oldPositions }

        val movedIds = HashSet<I>()

        var oldIndex = 0
        var newIndex = 0
        while (newIndex < newFiltered.size) {
            while (oldFiltered[oldIndex].itemId() in movedIds) {
                ++oldIndex
            }
            if (oldFiltered[oldIndex].itemId() == newFiltered[newIndex].itemId()) {
                ++oldIndex
                ++newIndex
                continue
            }
            var moveFrom = oldPositions[newFiltered[newIndex].itemId()]!!
            moves.forEach {
                if (it.fromPosition > moveFrom) {
                    ++moveFrom
                }
            }
            if (moveFrom == newIndex) {
//                println("moveFrom == newIndex == $moveFrom")
                ++newIndex
                continue
            }

            var movedCount = 1
            var qOldIndex = moveFrom + 1
            while (qOldIndex < oldFiltered.size && oldFiltered[qOldIndex].itemId() in movedIds) {
                ++qOldIndex
            }
            var qNewIndex = newIndex + 1
            while (qOldIndex < oldFiltered.size && oldFiltered[qOldIndex].itemId() == newFiltered[qNewIndex].itemId()) {
                ++movedCount
                ++qOldIndex
                while (qOldIndex < oldFiltered.size && oldFiltered[qOldIndex].itemId() in movedIds) {
                    ++qOldIndex
                }
                ++qNewIndex
            }

//            println("be $moveFrom $newIndex $movedCount")
            if (Math.abs(moveFrom - newIndex) < movedCount) {
                val tFrom = moveFrom
                val tTo = newIndex
                val tCount = movedCount
                moveFrom = tTo
                newIndex = newIndex + tCount
                movedCount = tFrom - tTo
            }
//            println("af $moveFrom $newIndex $movedCount")

            for (i in 0 until movedCount) {
                moves.add(ItemMove(moveFrom + i, newIndex + i))
                movedIds.add(newFiltered[newIndex + i].itemId())
            }
            newIndex += movedCount
        }

        return moves
    }

    inline fun <T : Any, I : Any> calculateDiff(
        oldList: List<T>,
        newList: List<T>,
        crossinline itemId: T.() -> I,
        areContentsTheSame: (T, T) -> Boolean,
        getChangePayload: (T, T) -> Any?
    ): SortedDiffResult {

        val oldPositions: HashMap<I, Int> = oldList.positionsById(itemId)
        val newPositions: HashMap<I, Int> = newList.positionsById(itemId)

        val removes: List<ItemsRange> = calculateRemoves(oldList, itemId, newPositions)

        val moves: List<ItemMove> = calculateMoves(oldList, newList, itemId, oldPositions, newPositions)

        val inserts: List<ItemsRange> = calculateInserts(newList, itemId, oldPositions)

        return SortedDiffResult(
            removes = removes,
            moves = moves,
            changes = emptyList(),
            inserts = inserts
        )
    }
}