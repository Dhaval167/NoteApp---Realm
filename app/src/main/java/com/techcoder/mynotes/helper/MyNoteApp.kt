package com.techcoder.mynotes.helper

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class MyNoteApp:Application() {

    override fun onCreate() {
        super.onCreate()

        Realm.init(this)

        val configuration = RealmConfiguration.Builder()
            .name("notes.db")
            .deleteRealmIfMigrationNeeded()
            .schemaVersion(0)
            .build()

        Realm.setDefaultConfiguration(configuration)
    }
}