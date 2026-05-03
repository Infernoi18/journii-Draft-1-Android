package com.example.journii

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.journii.data.Note
import com.example.journii.databinding.ItemNoteBinding

class NoteAdapter(
    private var notes: List<Note>
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

        holder.binding.tvTitle.text = note.title
        holder.binding.tvContent.text = note.content
        holder.binding.tvCategory.text = note.category
    }

    override fun getItemCount(): Int = notes.size

    fun updateList(newList: List<Note>) {
        notes = newList
        notifyDataSetChanged()
    }
}