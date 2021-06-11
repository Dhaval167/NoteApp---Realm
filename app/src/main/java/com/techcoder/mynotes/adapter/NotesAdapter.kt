package com.techcoder.mynotes.adapter

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.techcoder.mynotes.R
import com.techcoder.mynotes.activity.UpdateActivity
import com.techcoder.mynotes.model.NotesModel
import io.realm.Realm
import io.realm.RealmResults
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.card_notes_view.view.*

class NotesAdapter(var context: Context, var notes: RealmResults<NotesModel>) :
    RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        return NotesViewHolder(
            LayoutInflater.from(context).inflate(R.layout.card_notes_view, parent, false)
        )
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val currentPositions = notes[position]
        holder.bind(currentPositions!!)
    }

    override fun getItemCount(): Int {
        return notes.size
    }


    inner class NotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(note: NotesModel) {
            itemView.card_tv_id.text = note.id.toString()
            itemView.card_tc_descriptions.text = note.descriptions.toString()
            itemView.card_tv_title.text = note.title.toString()

            itemView.setOnClickListener {

                AlertDialog.Builder(context)
                    .setTitle("Note")
                    .setMessage("Select Options")
                    .setPositiveButton("Delete") { dialog, which ->
                        val realm = Realm.getDefaultInstance()

                        realm.executeTransaction {

                            val deleteNotes =
                                it.where(NotesModel::class.java).equalTo("id", note.id).findAll()
                            if (deleteNotes.deleteAllFromRealm()) {
                                Toast.makeText(context, "Delete Successful", Toast.LENGTH_LONG)
                                    .show()
                            }
                        }
                        notifyDataSetChanged()
                    }
                    .setNeutralButton("Update") { dialog, which ->

                        context.startActivity(Intent(context,UpdateActivity::class.java)
                            .putExtra("title",note.title)
                            .putExtra("descriptions",note.descriptions)
                            .putExtra("idNumber",note.id))
                        (context as Activity).finish()

                    }
                    .create().show()




            }

        }

    }
}