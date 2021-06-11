package com.techcoder.mynotes.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.Toast
import com.techcoder.mynotes.R
import com.techcoder.mynotes.model.NotesModel
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_update.*
import kotlinx.android.synthetic.main.card_notes_view.*
import java.lang.Exception

class UpdateActivity : AppCompatActivity() {

    private var title: String? = null
    lateinit var realm:Realm
    private var id:Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        val intent = intent
        title = intent.getStringExtra("title")
        val descriptions = intent.getStringExtra("descriptions")
        et_descriptions_update.setText(descriptions)
        et_title_update.setText(title)
         id = intent.getIntExtra("idNumber",0)
        Log.d("id",id?.toLong().toString())



        btn_save_note_update.setOnClickListener {
            updateNote()
        }
    }

    private fun updateNote() {
        realm = Realm.getDefaultInstance()
          val result = realm.where(NotesModel::class.java)!!.equalTo("id",id?.toLong()).findFirst()
        updateNewData(result)
    }

    private fun updateNewData(result: NotesModel?) {
      try {


        realm.executeTransaction {
            result?.title = et_title_update.text.toString().trim()
            result?.descriptions = et_descriptions_update.text.toString().trim()
            it.copyToRealmOrUpdate(result)
            startActivity(Intent(applicationContext,MainActivity::class.java))
            finish()
        }
        }catch (e:Exception){
          Toast.makeText(applicationContext,"Failed ${e.message.toString()}",Toast.LENGTH_LONG).show()
      }

    }
}