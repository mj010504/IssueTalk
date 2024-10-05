package com.example.kinddiscussion.feature.post

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kinddiscussion.core.network.model.Post
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class PostViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()

    private val _postList = mutableStateListOf<Post>()
    val postList: List<Post> get() = _postList
    private val _postIdList = mutableStateListOf<String>()
    val postIdList: List<String> get() = _postIdList

    private val _post : MutableState<Post> = mutableStateOf(Post())
    private val _postId : MutableState<String> = mutableStateOf("")
    val post: State<Post> get() = _post
    val postId : State<String> get() = _postId

    private val _isLiked : MutableState<Boolean> = mutableStateOf(false)
    val isLiked : State<Boolean> get() = _isLiked



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

                val postCountRef = db.collection("subject").document(post.subjectId)
                postCountRef.update("postCount", FieldValue.increment(1))

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

    fun updateCommentCount() {
        _post.value.commentCount = _post.value.commentCount + 1
    }

    fun decreaseCommentCount() {
        _post.value.commentCount = _post.value.commentCount - 1
    }
    fun deletePost() : Boolean {

        try {
            viewModelScope.launch(Dispatchers.IO) {
                db.collection("subject").document(post.value.subjectId).
                collection("post").document(postId.value).delete().await()
                val index = _postIdList.indexOf(postId.value)
                if(index >= 0) {
                    _postList.removeAt(index)
                    _postIdList.removeAt(index)
                }

                db.collection("subject").document(post.value.subjectId)
                    .update("postCount", FieldValue.increment(-1))
            }
            return true
        }
        catch (e : Exception) {
            return false
        }

    }

    fun isPostLikedByUser(userId : String) {

        viewModelScope.launch(Dispatchers.IO) {
            val postRef = db.collection("subject")
                .document(post.value.subjectId).collection("post")
                .document(postId.value)

            val likeRef = postRef.collection("like").document(userId)

            likeRef.get().addOnSuccessListener { document ->
                if(document.exists()) {
                    _isLiked.value = true
                    postRef.update("likeCount", FieldValue.increment(1))
                }
                else {
                    _isLiked.value = false
                }
            }

        }


    }
    fun likePost(userId : String) {

        viewModelScope.launch(Dispatchers.IO) {
            val postRef = db.collection("subject")
                .document(post.value.subjectId).collection("post")
                .document(postId.value)

            val likeRef = postRef.collection("like").document(userId)

            likeRef.get().addOnSuccessListener { document ->
                if(!document.exists()) {
                    likeRef.set("isLike" to true)
                    postRef.update("likeCount", FieldValue.increment(1))
                    _isLiked.value = true
                    _post.value.likeCount += 1
                }
                else {
                    likeRef.delete()
                    _isLiked.value = false
                    _post.value.likeCount -= 1
                    postRef.update("likeCount", FieldValue.increment(-1))
                }
            }

        }


    }

    fun editPost(title : String, content : String) : Boolean {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                val postRef = db.collection("subject")
                    .document(post.value.subjectId).collection("post")
                    .document(postId.value)

                postRef.update("title", title)
                postRef.update("content", content)
                val index = _postIdList.indexOf(postId.value)
                if(index >= 0) {
                    _postList[index].content = content
                    _postList[index].title = title
                }

            }

            return true
        }
        catch(e : Exception) {
            return false
        }


    }



}