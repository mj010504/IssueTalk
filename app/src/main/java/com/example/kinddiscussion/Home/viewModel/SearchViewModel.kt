package com.example.kinddiscussion.Home.viewModel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

import com.example.kinddiscussion.Firebase.Subject
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.tasks.await

// 비효율적
class SearchViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val _searchList = mutableStateListOf<Subject>()
    val searchList : List<Subject> get() = _searchList
    private val _searchIdList = mutableStateListOf<String>()
    val searchIdList : List<String> get() = _searchIdList

    fun search(keyword : String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = db.collection("subject")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .get()
                .await()

            _searchList.clear()


            result.documents.forEach { document ->
                val subject = document.toObject(Subject::class.java)

                if (subject != null) {
                    val title = subject.title
                    val agreeText = subject.agreeText
                    val disagreeTeext = subject.disagreeText
                    if(title.contains(keyword) || agreeText.contains(keyword)
                        || disagreeTeext.contains(keyword)) {
                        _searchList.add(subject)
                        _searchIdList.add(document.id)
                    }

                }
            }
        }
    }
}