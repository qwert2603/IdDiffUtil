package com.qwert2603.sorted_diff_util

import org.junit.Assert
import org.junit.Test

class SortedDiffUtilTest {
    @Test
    fun `only removes`() {
        Assert.assertEquals(
            SortedDiffResult(
                removes = listOf(
                    ItemsRange(6, 2),
                    ItemsRange(4, 1),
                    ItemsRange(0, 3)
                ),
                moves = emptyList(),
                changes = emptyList(),
                inserts = emptyList()
            ),
            SortedDiffUtil.calculateDiff(
                oldList = listOf(-1, 0, 1, 2, 3, 4, 5, 6),
                newList = listOf(2, 4),
                itemId = { this },
                compareOrder = Int::compareTo,
                areContentsTheSame = Int::equals,
                getChangePayload = { _, _ -> null }
            )
        )
    }

    @Test
    fun `one move`() {
        Assert.assertEquals(
            SortedDiffResult(
                removes = emptyList(),
                moves = listOf(ItemMove(5, 3)),
                changes = emptyList(),
                inserts = emptyList()
            ),
            SortedDiffUtil.calculateDiff(
                oldList = listOf(0, 1, 2, 3, 4, 5, 6),
                newList = listOf(0, 1, 2, 5, 3, 4, 6),
                itemId = { this },
                compareOrder = Int::compareTo,
                areContentsTheSame = Int::equals,
                getChangePayload = { _, _ -> null }
            )
        )

        Assert.assertEquals(
            SortedDiffResult(
                removes = emptyList(),
                moves = listOf(
                    ItemMove(3, 2),
                    ItemMove(4, 3),
                    ItemMove(5, 4)
                ),
                changes = emptyList(),
                inserts = emptyList()
            ),
            SortedDiffUtil.calculateDiff(
                oldList = listOf(0, 1, 2, 3, 4, 5, 6),
                newList = listOf(0, 1, 3, 4, 5, 2, 6),
                itemId = { this },
                compareOrder = Int::compareTo,
                areContentsTheSame = Int::equals,
                getChangePayload = { _, _ -> null }
            )
        )

        Assert.assertEquals(
            SortedDiffResult(
                removes = emptyList(),
                moves = listOf(ItemMove(4, 0)),
                changes = emptyList(),
                inserts = emptyList()
            ),
            SortedDiffUtil.calculateDiff(
                oldList = listOf(0, 1, 2, 3, 4, 5, 6),
                newList = listOf(4, 0, 1, 2, 3, 5, 6),
                itemId = { this },
                compareOrder = Int::compareTo,
                areContentsTheSame = Int::equals,
                getChangePayload = { _, _ -> null }
            )
        )

        Assert.assertEquals(
            SortedDiffResult(
                removes = emptyList(),
                moves = listOf(
                    ItemMove(5, 4),
                    ItemMove(6, 5)
                ),
                changes = emptyList(),
                inserts = emptyList()
            ),
            SortedDiffUtil.calculateDiff(
                oldList = listOf(0, 1, 2, 3, 4, 5, 6),
                newList = listOf(0, 1, 2, 3, 5, 6, 4),
                itemId = { this },
                compareOrder = Int::compareTo,
                areContentsTheSame = Int::equals,
                getChangePayload = { _, _ -> null }
            )
        )

        Assert.assertEquals(
            SortedDiffResult(
                removes = emptyList(),
                moves = listOf(ItemMove(6, 0)),
                changes = emptyList(),
                inserts = emptyList()
            ),
            SortedDiffUtil.calculateDiff(
                oldList = listOf(0, 1, 2, 3, 4, 5, 6),
                newList = listOf(6, 0, 1, 2, 3, 4, 5),
                itemId = { this },
                compareOrder = Int::compareTo,
                areContentsTheSame = Int::equals,
                getChangePayload = { _, _ -> null }
            )
        )

        Assert.assertEquals(
            SortedDiffResult(
                removes = emptyList(),
                moves = (0..5).map { ItemMove(it + 1, it) },
                changes = emptyList(),
                inserts = emptyList()
            ),
            SortedDiffUtil.calculateDiff(
                oldList = listOf(0, 1, 2, 3, 4, 5, 6),
                newList = listOf(1, 2, 3, 4, 5, 6, 0),
                itemId = { this },
                compareOrder = Int::compareTo,
                areContentsTheSame = Int::equals,
                getChangePayload = { _, _ -> null }
            )
        )
    }

    @Test
    fun `many moves`() {
        Assert.assertEquals(
            SortedDiffResult(
                removes = emptyList(),
                moves = listOf(
                    ItemMove(2, 1),
                    ItemMove(5, 2),
                    ItemMove(4, 3),
                    ItemMove(5, 4)
                ),
                changes = emptyList(),
                inserts = emptyList()
            ),
            SortedDiffUtil.calculateDiff(
                oldList = listOf(0, 1, 2, 3, 4, 5, 6),
                newList = listOf(0, 2, 5, 3, 4, 1, 6),
                itemId = { this },
                compareOrder = Int::compareTo,
                areContentsTheSame = Int::equals,
                getChangePayload = { _, _ -> null }
            )
        )
    }

    @Test
    fun `only inserts`() {
        Assert.assertEquals(
            SortedDiffResult(
                removes = emptyList(),
                moves = emptyList(),
                changes = emptyList(),
                inserts = listOf(
                    ItemsRange(0, 3),
                    ItemsRange(4, 1),
                    ItemsRange(6, 2)
                )
            ),
            SortedDiffUtil.calculateDiff(
                oldList = listOf(2, 4),
                newList = listOf(-1, 0, 1, 2, 3, 4, 5, 6),
                itemId = { this },
                compareOrder = Int::compareTo,
                areContentsTheSame = Int::equals,
                getChangePayload = { _, _ -> null }
            )
        )
    }
}