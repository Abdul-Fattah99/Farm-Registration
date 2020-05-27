package com.agromall.farmregistration.ui.farmRegFragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.agromall.farmregistration.data.Farmer
import com.agromall.farmregistration.data.NODE_FARMERS
import com.google.firebase.database.*
import java.lang.Exception

class FarmListViewModel : ViewModel() {

    private val dbFarmers = FirebaseDatabase.getInstance().getReference(NODE_FARMERS)

    private val _farmers = MutableLiveData<List<Farmer>>()
    val farmers: LiveData<List<Farmer>>
        get() = _farmers

    private val _farmer = MutableLiveData<Farmer>()
    val farmer: LiveData<Farmer>
        get() = _farmer

    private val _result = MutableLiveData<Exception?>()
    val result: LiveData<Exception?>
        get() = _result

    fun addFarmer(farmer: Farmer) {
        farmer.id = dbFarmers.push().key
        dbFarmers.child(farmer.id!!).setValue(farmer)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    _result.value = null
                } else {
                    _result.value = it.exception
                }
            }
    }

    private val childEventListener = object : ChildEventListener {
        override fun onCancelled(error: DatabaseError) {}

        override fun onChildMoved(snapshot: DataSnapshot, p1: String?) {}

        override fun onChildChanged(snapshot: DataSnapshot, p1: String?) {
            val farmer = snapshot.getValue(Farmer::class.java)
            farmer?.id = snapshot.key
            _farmer.value = farmer
        }

        override fun onChildAdded(snapshot: DataSnapshot, p1: String?) {
            val farmer = snapshot.getValue(Farmer::class.java)
            farmer?.id = snapshot.key
            _farmer.value = farmer
        }

        override fun onChildRemoved(snapshot: DataSnapshot) {
            val farmer = snapshot.getValue(Farmer::class.java)
            farmer?.id = snapshot.key
            farmer?.isDeleted = true
            _farmer.value = farmer
        }
    }

    fun getRealtimeUpdates() {
        dbFarmers.addChildEventListener(childEventListener)
    }


    fun fetchFilteredFarmers(index: Int) {
        val dbFarmers =
            when (index) {
                1 ->
                    //#1 SELECT * FROM Farmers
                    FirebaseDatabase.getInstance().getReference(NODE_FARMERS)

                2 ->
                    //#2 SELECT * FROM Farmers WHERE id = ?
                    FirebaseDatabase.getInstance().getReference(NODE_FARMERS)
                        .child("-M-3fFw3GbovXWguSjp8")

                3 ->
                    //#3 SELECT * FROM Farmers WHERE age = ?
                    FirebaseDatabase.getInstance().getReference(NODE_FARMERS)
                        .orderByChild("age")
                        .equalTo("Hyderabad")

                4 ->
                    //#4 SELECT * FROM Farmers LIMIT 2
                    FirebaseDatabase.getInstance().getReference(NODE_FARMERS)
                        .limitToFirst(2)

                5 ->
                    //#5 SELECT * FROM Farmers WHERE sex < 500
                    FirebaseDatabase.getInstance().getReference(NODE_FARMERS)
                        .orderByChild("sex")
                        .endAt(500.toDouble())

                6 ->
                    //#6 SELECT * FROM Farmers WHERE name LIKE "A%"
                    FirebaseDatabase.getInstance().getReference(NODE_FARMERS)
                        .orderByChild("name")
                        .startAt("A")
                        .endAt("A\uf8ff")

                7 ->
                    //#7 SELECT * FROM Farmers Where sex < 500 AND age = Bangalore
                    FirebaseDatabase.getInstance().getReference(NODE_FARMERS)
                else -> FirebaseDatabase.getInstance().getReference(NODE_FARMERS)
            }

        dbFarmers.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {}

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val farmers = mutableListOf<Farmer>()
                    for (farmerSnapshot in snapshot.children) {
                        val farmer = farmerSnapshot.getValue(Farmer::class.java)
                        farmer?.id = farmerSnapshot.key
                        farmer?.let { farmers.add(it) }
                    }
                    _farmers.value = farmers
                }
            }
        })
    }


    fun fetchFarmers() {
        dbFarmers.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {}

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val farmers = mutableListOf<Farmer>()
                    for (farmerSnapshot in snapshot.children) {
                        val farmer = farmerSnapshot.getValue(Farmer::class.java)
                        farmer?.id = farmerSnapshot.key
                        farmer?.let { farmers.add(it) }
                    }
                    _farmers.value = farmers
                }
            }
        })
    }

    fun updateFarmer(farmer: Farmer) {
        dbFarmers.child(farmer.id!!).setValue(farmer)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    _result.value = null
                } else {
                    _result.value = it.exception
                }
            }
    }

    fun deleteFarmer(farmer: Farmer) {
        dbFarmers.child(farmer.id!!).setValue(null)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    _result.value = null
                } else {
                    _result.value = it.exception
                }
            }
    }

    override fun onCleared() {
        super.onCleared()
        dbFarmers.removeEventListener(childEventListener)
    }
}