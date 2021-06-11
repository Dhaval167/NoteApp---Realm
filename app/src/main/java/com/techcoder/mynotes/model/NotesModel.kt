package com.techcoder.mynotes.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.io.Serializable


open class NotesModel(
    @PrimaryKey
    var id: Int? = null,
    var title: String? = null,
    var descriptions: String? = null
):RealmObject()