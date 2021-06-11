package com.techcoder.mynotes.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.techcoder.mynotes.model.NotesModel
import com.techcoder.mynotes.R
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_add_note.*
import java.lang.Exception

class AddNoteActivity : AppCompatActivity() {

    lateinit var realm:Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        realm = Realm.getDefaultInstance()

        btn_save_note.setOnClickListener {
            addNotesToDB()
        }
    }

    private fun addNotesToDB() {

        try{

            realm.beginTransaction()

            val currentId = realm.where(NotesModel::class.java).max("id")
            val nextId:Int

            nextId = if (currentId == null){1}else{currentId.toInt() + 1}

            val notes = NotesModel()
            notes.id = nextId
            notes.title = et_title.text.toString().trim()
            notes.descriptions = et_descriptions.text.toString().trim()

            realm.copyToRealmOrUpdate(notes)
            realm.commitTransaction()

            Toast.makeText(applicationContext,"Note Saved",Toast.LENGTH_LONG).show()

            startActivity(Intent(applicationContext, MainActivity::class.java))
            finish()


        }catch (e:Exception){

            Toast.makeText(applicationContext,"Failed ${e.message.toString()}",Toast.LENGTH_LONG).show()

        }

    }
}