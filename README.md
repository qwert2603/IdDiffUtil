# IdDiffUtil
Android's Diff Util based on Item's ids

```kotlin
@Test
fun items() {
    data class Item(val id: Long, val name: String)

    val actual = IdDiffUtil.calculateDiff(
        oldList = listOf(
            Item(1, "a"),
            Item(2, "b"),
            Item(3, "c"),
            Item(4, "d")
        ),
        newList = listOf(
            Item(4, "d"),
            Item(1, "a"),
            Item(3, "c"),
            Item(5, "e"),
            Item(6, "f")
        ),
        itemId = Item::id,
        areContentsTheSame = Item::equals,
        getChangePayload = { _, _ -> null }
    )

    val expected = IdDiffResult(
        removes = listOf(ItemsRange(startPosition = 1, count = 1)),
        moves = listOf(ItemMove(fromPosition = 2, toPosition = 0)),
        changes = emptyList(),
        inserts = listOf(ItemsRange(startPosition = 3, count = 2))
    )

    Assert.assertEquals(expected, actual)
}
```
