package com.qwert2603.id_diff_util

import androidx.recyclerview.widget.RecyclerView

data class IdDiffResult(
    val removes: List<ItemsRange>,
    val moves: List<ItemMove>,
    val changes: List<ItemChange>,
    val inserts: List<ItemsRange>
) {
    fun dispatchToAdapter(adapter: RecyclerView.Adapter<*>) {
        removes.forEach { adapter.notifyItemRangeRemoved(it.startPosition, it.count) }
        moves.forEach { adapter.notifyItemMoved(it.fromPosition, it.toPosition) }
        changes.forEach { adapter.notifyItemChanged(it.position, it.payload) }
        inserts.forEach { adapter.notifyItemRangeInserted(it.startPosition, it.count) }
    }
}