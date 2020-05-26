package com.agromall.farmregistration.data

import com.google.firebase.database.Exclude
import kotlin.random.Random

private val cities = listOf("Bangalore", "Mumbai", "Ranchi", "Kolkata", "Hyderabad", "Pune")

data class Farmer(
    @get:Exclude
    var id: String? = null,
    var name: String? = null,
    var city: String? = cities.random(),
    var votes: Int = Random.nextInt(50, 5000),
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