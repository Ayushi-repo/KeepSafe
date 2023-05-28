package com.example.keepsafe

import android.content.Context
//import com.example.keepsafe.Note.message
//import com.example.keepsafe.Note.date
//import com.example.keepsafe.Note.id
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import com.example.keepsafe.R
import android.widget.TextView
import android.widget.RelativeLayout
import android.content.Intent
import android.view.View
import com.example.keepsafe.EditNoteActivity
import java.util.ArrayList

private class NotesAdapter(noteList: List<Note>, context: Context) :
    RecyclerView.Adapter<NotesAdapter.ViewHolder>() {
    var mNoteList: List<Note> = ArrayList()
    var mContext: Context

    init {
        mNoteList = noteList
        mContext = context
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.note_layout, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.message.text = mNoteList[position].message
        holder.date.text = mNoteList[position].date
    }

    override fun getItemCount(): Int {
        return mNoteList.size
    }

    internal inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var message: TextView
        var date: TextView
        var noteRelLayout: RelativeLayout

        init {
            message = itemView.findViewById(R.id.msgTextView)
            date = itemView.findViewById(R.id.dateTextView)
            noteRelLayout = itemView.findViewById(R.id.noteView)
            itemView.setOnClickListener {
                val noteData = mNoteList[adapterPosition]
                val jotNoteIntent = Intent(mContext, EditNoteActivity::class.java)
                jotNoteIntent.putExtra("id", noteData.id)
                jotNoteIntent.putExtra("message", noteData.message)
                jotNoteIntent.putExtra("date", noteData.date)
                mContext.startActivity(jotNoteIntent)
            }
        }
    }
}