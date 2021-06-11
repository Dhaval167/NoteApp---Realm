package com.techcoder.mynotes.activity

import android.content.Intent
import android.graphics.Canvas
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.techcoder.mynotes.R
import com.techcoder.mynotes.adapter.NotesAdapter
import com.techcoder.mynotes.model.NotesModel
import io.realm.Realm
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.card_notes_view.view.*

class MainActivity : AppCompatActivity() {

    lateinit var realm: Realm
    lateinit var adapter:NotesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        realm = Realm.getDefaultInstance()

        ItemTouchHelper(itemTouchHelpers).apply { attachToRecyclerView(view_recyclerview) }


        btn_add.setOnClickListener {
            startActivity(Intent(applicationContext, AddNoteActivity::class.java))
            finish()
        }

        view_recyclerview.layoutManager =
            StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        getAllNotes()


    }

    private fun getAllNotes() {

        val result = realm.where(NotesModel::class.java).findAll()

        adapter = NotesAdapter(this@MainActivity, result)
        view_recyclerview.adapter = adapter
        view_recyclerview.adapter!!.notifyDataSetChanged()

    }

    val itemTouchHelpers = object: ItemTouchHelper.SimpleCallback(
        ItemTouchHelper.UP or ItemTouchHelper.DOWN,
        ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            when(direction){

                ItemTouchHelper.LEFT ->{

                }

            }


        }

        override fun onChildDraw(
            c: Canvas,
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            dX: Float,
            dY: Float,
            actionState: Int,
            isCurrentlyActive: Boolean
        )
        {
            RecyclerViewSwipeDecorator.Builder(c,recyclerView,viewHolder,dX,dY,actionState,isCurrentlyActive).addSwipeLeftBackgroundColor(
                ContextCompat.getColor(this@MainActivity,
                    R.color.colorPrimaryDark
            )).addSwipeRightBackgroundColor(ContextCompat.getColor(this@MainActivity,R.color.colorAccent)).addActionIcon(R.mipmap.ic_launcher)
                .create().decorate()
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        }
    }
}