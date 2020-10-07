package lv.romstr.mobile.rtu_android.api

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import lv.romstr.mobile.rtu_android.ShoppingItem

object FirebaseRepository {

    private val db by lazy { Firebase.firestore }
    private val auth by lazy { FirebaseAuth.getInstance() }

    private val ref get() = "users/${auth.currentUser?.uid}/items"

    fun observeItems(): LiveData<List<ShoppingItem>> {
        val data = MutableLiveData<List<ShoppingItem>>()

        db.collection(ref)
            .addSnapshotListener { value, _ ->
                value?.let {
                    val items =
                        value.documents.mapNotNull { it.toObject<ShoppingItem>()?.copy(id = it.id) }
                    data.postValue(items)
                }
            }

        return data
    }

    fun addItem(item: ShoppingItem) {
        db.collection(ref)
            .add(item)
            .addOnFailureListener { println(it.message) }
    }


    fun removeItem(item: ShoppingItem) {
        db.collection(ref)
            .document(item.id)
            .delete()
            .addOnFailureListener { println(it.message) }
    }

    fun updateItem(item: ShoppingItem) {
        db.collection(ref)
            .document(item.id)
            .set(item)
            .addOnFailureListener { println(it.message) }
    }

    fun getItemById(id: String): LiveData<ShoppingItem> {
        val data = MutableLiveData<ShoppingItem>()

        db.collection(ref)
            .document(id)
            .get()
            .addOnSuccessListener {
                data.postValue(it.toObject<ShoppingItem>())
            }
            .addOnFailureListener { println(it.message) }

        return data
    }

}