package com.qwert2603.id_diff_util

import org.junit.Assert
import org.junit.Ignore
import org.junit.Test

class IdDiffUtilTest {
    @Test
    fun `only removes`() {
        Assert.assertEquals(
            IdDiffResult(
                removes = listOf(
                    ItemsRange(6, 2),
                    ItemsRange(4, 1),
                    ItemsRange(0, 3)
                ),
                moves = emptyList(),
                changes = emptyList(),
                inserts = emptyList()
            ),
            IdDiffUtil.calculateDiff(
                oldList = listOf(-1, 0, 1, 2, 3, 4, 5, 6),
                newList = listOf(2, 4),
                itemId = { this },
                areContentsTheSame = Int::equals,
                getChangePayload = { _, _ -> null }
            )
        )
    }

    @Test
    fun `one move`() {
        Assert.assertEquals(
            IdDiffResult(
                removes = emptyList(),
                moves = listOf(ItemMove(5, 3)),
                changes = emptyList(),
                inserts = emptyList()
            ),
            IdDiffUtil.calculateDiff(
                oldList = listOf(0, 1, 2, 3, 4, 5, 6, 7, 8),
                newList = listOf(0, 1, 2, 5, 3, 4, 6, 7, 8),
                itemId = { this },
                areContentsTheSame = Int::equals,
                getChangePayload = { _, _ -> null }
            )
        )

        Assert.assertEquals(
            IdDiffResult(
                removes = emptyList(),
                moves = listOf(ItemMove(2, 5)),
                changes = emptyList(),
                inserts = emptyList()
            ),
            IdDiffUtil.calculateDiff(
                oldList = listOf(0, 1, 2, 3, 4, 5, 6, 7, 8),
                newList = listOf(0, 1, 3, 4, 5, 2, 6, 7, 8),
                itemId = { this },
                areContentsTheSame = Int::equals,
                getChangePayload = { _, _ -> null }
            )
        )

        Assert.assertEquals(
            IdDiffResult(
                removes = emptyList(),
                moves = listOf(ItemMove(4, 0)),
                changes = emptyList(),
                inserts = emptyList()
            ),
            IdDiffUtil.calculateDiff(
                oldList = listOf(0, 1, 2, 3, 4, 5, 6),
                newList = listOf(4, 0, 1, 2, 3, 5, 6),
                itemId = { this },
                areContentsTheSame = Int::equals,
                getChangePayload = { _, _ -> null }
            )
        )

        Assert.assertEquals(
            IdDiffResult(
                removes = emptyList(),
                moves = listOf(ItemMove(4, 6)),
                changes = emptyList(),
                inserts = emptyList()
            ),
            IdDiffUtil.calculateDiff(
                oldList = listOf(0, 1, 2, 3, 4, 5, 6),
                newList = listOf(0, 1, 2, 3, 5, 6, 4),
                itemId = { this },
                areContentsTheSame = Int::equals,
                getChangePayload = { _, _ -> null }
            )
        )

        Assert.assertEquals(
            IdDiffResult(
                removes = emptyList(),
                moves = listOf(ItemMove(6, 0)),
                changes = emptyList(),
                inserts = emptyList()
            ),
            IdDiffUtil.calculateDiff(
                oldList = listOf(0, 1, 2, 3, 4, 5, 6),
                newList = listOf(6, 0, 1, 2, 3, 4, 5),
                itemId = { this },
                areContentsTheSame = Int::equals,
                getChangePayload = { _, _ -> null }
            )
        )

        Assert.assertEquals(
            IdDiffResult(
                removes = emptyList(),
                moves = listOf(ItemMove(0, 6)),
                changes = emptyList(),
                inserts = emptyList()
            ),
            IdDiffUtil.calculateDiff(
                oldList = listOf(0, 1, 2, 3, 4, 5, 6),
                newList = listOf(1, 2, 3, 4, 5, 6, 0),
                itemId = { this },
                areContentsTheSame = Int::equals,
                getChangePayload = { _, _ -> null }
            )
        )
    }

    @Test
    fun `many moves`() {
        Assert.assertEquals(
            IdDiffResult(
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
            IdDiffUtil.calculateDiff(
                oldList = listOf(0, 1, 2, 3, 4, 5, 6),
                newList = listOf(0, 2, 5, 3, 4, 1, 6),
                itemId = { this },
                areContentsTheSame = Int::equals,
                getChangePayload = { _, _ -> null }
            )
        )

        Assert.assertEquals(
            IdDiffResult(
                removes = emptyList(),
                moves = listOf(
                    ItemMove(1, 3),
                    ItemMove(9, 6)
                ),
                changes = emptyList(),
                inserts = emptyList()
            ),
            IdDiffUtil.calculateDiff(
                oldList = listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9),
                newList = listOf(0, 2, 3, 1, 4, 5, 9, 6, 7, 8),
                itemId = { this },
                areContentsTheSame = Int::equals,
                getChangePayload = { _, _ -> null }
            )
        )

        Assert.assertEquals(
            IdDiffResult(
                removes = emptyList(),
                moves = listOf(
                    ItemMove(3, 1),
                    ItemMove(6, 9)
                ),
                changes = emptyList(),
                inserts = emptyList()
            ),
            IdDiffUtil.calculateDiff(
                oldList = listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9),
                newList = listOf(0, 3, 1, 2, 4, 5, 7, 8, 9, 6),
                itemId = { this },
                areContentsTheSame = Int::equals,
                getChangePayload = { _, _ -> null }
            )
        )
    }

    @Test
    fun `nested moves`() {
        Assert.assertEquals(
            IdDiffResult(
                removes = emptyList(),
                moves = listOf(
                    ItemMove(5, 1),
                    ItemMove(4, 7)
                ),
                changes = emptyList(),
                inserts = emptyList()
            ),
            IdDiffUtil.calculateDiff(
                oldList = listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9),
                newList = listOf(0, 5, 1, 2, 4, 6, 7, 3, 8, 9),
                itemId = { this },
                areContentsTheSame = Int::equals,
                getChangePayload = { _, _ -> null }
            )
        )
    }

    @Test
    fun `only inserts`() {
        Assert.assertEquals(
            IdDiffResult(
                removes = emptyList(),
                moves = emptyList(),
                changes = emptyList(),
                inserts = listOf(
                    ItemsRange(0, 3),
                    ItemsRange(4, 1),
                    ItemsRange(6, 2)
                )
            ),
            IdDiffUtil.calculateDiff(
                oldList = listOf(2, 4),
                newList = listOf(-1, 0, 1, 2, 3, 4, 5, 6),
                itemId = { this },
                areContentsTheSame = Int::equals,
                getChangePayload = { _, _ -> null }
            )
        )
    }

    @Test
    @Ignore
    fun perf() {
        repeat(1000000) {
            val diff = IdDiffUtil.calculateDiff(
                oldList = listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19),
                newList = listOf(0, 3, 1, 2, 4, 5, 7, 8, 9, 6, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19),
                itemId = { this },
                areContentsTheSame = Int::equals,
                getChangePayload = { _, _ -> null }
            )
        }
    }
}