package com.agromall.farmregistration.data

import android.net.Uri
import com.google.firebase.database.Exclude



data class Farmer(
    @get:Exclude
    var id: String? = null,
    //var image: Uri,
    var name: String? = null,
    var dob: String? = null,
    var phoneNumber: String? = null,
    var farmName: String? = null,
    var farmSize: String? = null,
    var location: String? = null,

    @get:Exclude
    var isDeleted: Boolean = false
) {
    override fun equals(other: Any?): Boolean {
        return if (other is Farmer) {
            other.id == id
        } else false
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (name?.hashCode() ?: 0)
        return result
    }

}