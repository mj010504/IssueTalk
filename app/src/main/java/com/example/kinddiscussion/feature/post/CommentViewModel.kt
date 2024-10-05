package com.example.kinddiscussion.feature.post

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kinddiscussion.core.network.model.Comment
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await





class CommentViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()

    private val _commentList = mutableStateListOf<Comment>()
    val commentList: List<Comment> get() = _commentList
    private val _commentIdList = mutableStateListOf<String>()
    val commentIdList: List<String> get() = _commentIdList

    fun fetchComments(subjectId: String, postId : String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = db.collection("subject")
                .document(subjectId).collection("post")
                .document(postId).collection("comment")
                .orderBy("timestamp", Query.Direction.ASCENDING)
                .get()
                .await()

            _commentList.clear()
            _commentIdList.clear()

            result.documents.forEach { document ->
                val comment = document.toObject(Comment::class.java)
                if (comment != null) {
                    _commentList.add(comment)
                    _commentIdList.add(document.id)
                }
            }
        }
    }

    fun writeComment(subjectId : String, postId : String, comment : Comment) : Boolean {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                val docRef = db.collection("subject")
                    .document(subjectId).collection("post")
                    .document(postId).collection("comment")
                    .add(comment)
                    .await()

                val commentCountRef = db.collection("subject")
                    .document(subjectId).collection("post").document(postId)

                commentCountRef.update("commentCount", FieldValue.increment(1))

                val commentId = docRef.id

                _commentList.add(comment)
                _commentIdList.add(commentId)
            }

            return true
        } catch (e: Exception) {
            return false
        }
    }



    fun deleteComment(subjectId : String, postId : String,commentId : String) : Boolean {

        try {
            viewModelScope.launch(Dispatchers.IO) {

                db.collection("subject").document(subjectId).
                collection("post").document(postId)
                    .collection("comment").document(commentId).delete().await()

                val index = _commentIdList.indexOf(commentId)
                if(index >= 0) {
                    _commentList.removeAt(index)
                    _commentIdList.removeAt(index)

                    db.collection("subject").document(subjectId).
                    collection("post").document(postId)
                        .update("commentCount", FieldValue.increment(-1))
                }
            }

            return true
        }
        catch (e :Exception) {

            return false
        }

    }




}