package com.example.journii

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.journii.data.Note
import com.example.journii.databinding.ItemNoteBinding

class NoteAdapter(
    private var notes: List<Note>,
    private val onItemClick: (Note) -> Unit,
    private val onItemLongClick: (Note) -> Unit
) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    inner class NoteViewHolder(val binding: ItemNoteBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ItemNoteBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]

        val parts = note.category.split("|")
        val category = parts[0]
        val type = if (parts.size > 1) parts[1] else "Mix"

        holder.binding.tvTitle.text = note.title
        holder.binding.tvContent.text = note.content
        holder.binding.tvCategory.text = category

        val iconRes = when (type) {
            "Road" -> R.drawable.road
            "Rail" -> R.drawable.rail
            "Air" -> R.drawable.air
            "Ship" -> R.drawable.ship
            else -> R.drawable.mix
        }

        holder.binding.ivType.setImageResource(iconRes)


        holder.itemView.setOnClickListener {
            onItemClick(note)
        }


        holder.itemView.setOnLongClickListener {
            onItemLongClick(note)
            true
        }
    }

    override fun getItemCount(): Int = notes.size

    fun updateList(newList: List<Note>) {
        notes = newList
        notifyDataSetChanged()
    }
}