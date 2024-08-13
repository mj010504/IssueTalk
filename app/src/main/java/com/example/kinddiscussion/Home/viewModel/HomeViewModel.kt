package com.example.kinddiscussion.Home.viewModel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kinddiscussion.Firebase.Post
import com.example.kinddiscussion.Firebase.Subject
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class SubjectViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()

    private val _subjectList = mutableStateListOf<Subject>()
    val subjectList: List<Subject> get() = _subjectList
    private val _subjectIdList = mutableStateListOf<String>()
    val subjectIdList: List<String> get() = _subjectIdList


    private val _subject :  MutableState<Subject> = mutableStateOf(Subject())
    private val _subjectId :  MutableState<String> = mutableStateOf("")
    val subject: State<Subject> get() = _subject
    val subjectId : State<String> get() = _subjectId

    init {
        fetchSubjects()
    }

    private fun fetchSubjects() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = db.collection("subject")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .get()
                .await()

            _subjectList.clear()
            _subjectIdList.clear()

            result.documents.forEach { document ->
                val subject = document.toObject(Subject::class.java)
                if (subject != null) {
                    _subjectList.add(subject)
                    _subjectIdList.add(document.id)
                }
            }
        }
    }

    fun writeSubject(subject : Subject) : Boolean {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                val docRef = db.collection("subject")
                    .add(subject)
                    .await()

                val subjectId = docRef.id

                _subjectList.add(0, subject)
                _subjectIdList.add(0, subjectId)
            }

            return true
        }
        catch(e : Exception) {
            return false
        }

    }

    fun setSubject(subject: Subject, subjectId: String) {
        _subject.value = subject
        _subjectId.value = subjectId
    }


}

class PostViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()

    private val _postList = mutableStateListOf<Post>()
    val postList: List<Post> get() = _postList
    private val _postIdList = mutableStateListOf<String>()
    val postIdList: List<String> get() = _postIdList

    private val _post :  MutableState<Post> = mutableStateOf(Post())
    private val _postId :  MutableState<String> = mutableStateOf("")
    val post: State<Post> get() = _post
    val postId : State<String> get() = _postId


    fun fetchPosts(subjectId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = db.collection("subject").document(subjectId).collection("post")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .get()
                .await()

            _postList.clear()
            _postIdList.clear()

            result.documents.forEach { document ->
                val post = document.toObject(Post::class.java)
                if (post != null) {
                    _postList.add(post)
                    _postIdList.add(document.id)
                }
            }
        }
    }

    fun writePost(post : Post) : Boolean {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                val docRef = db.collection("subject").document(post.subjectId).collection("post")
                    .add(post)
                    .await()

                val postId = docRef.id

                _postList.add(0, post)
                _postIdList.add(0, postId)
            }

            return true
        }
        catch(e : Exception) {
            return false
        }

    }

    fun setPost(post: Post, postId : String) {
        _post.value = post
        _postId.value = postId

    }

}