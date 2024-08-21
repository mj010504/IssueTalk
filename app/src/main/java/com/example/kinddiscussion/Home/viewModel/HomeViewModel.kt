package com.example.kinddiscussion.Home.viewModel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kinddiscussion.Firebase.Choice
import com.example.kinddiscussion.Firebase.Comment
import com.example.kinddiscussion.Firebase.Post
import com.example.kinddiscussion.Firebase.Subject
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class SubjectViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()

    private val _subjectList = mutableStateListOf<Subject>()
    val subjectList: List<Subject> get() = _subjectList
    private val _subjectIdList = mutableStateListOf<String>()
    val subjectIdList: List<String> get() = _subjectIdList

    private val _threePosts = mutableStateListOf<Post>()
    val threePosts: List<Post> get() = _threePosts

    private val _subject: MutableState<Subject> = mutableStateOf(Subject())
    private val _subjectId: MutableState<String> = mutableStateOf("")
    val subject: State<Subject> get() = _subject
    val subjectId: State<String> get() = _subjectId
    private val _choice  = mutableStateOf("")
    val choice : State<String> get() =_choice

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

    fun fetchLatestThreePosts() {

        viewModelScope.launch(Dispatchers.IO) {
            val result = db.collection("subject").document(subjectId.value)
                .collection("post")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .limit(3)
                .get()
                .await()

            _threePosts.clear()
            result.documents.forEach { document ->
                val post = document.toObject(Post::class.java)
                if (post != null) {
                    _threePosts.add(post)
                }
            }


        }


    }


    fun writeSubject(subject: Subject): Boolean {
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
        } catch (e: Exception) {
            return false
        }

    }

    fun setSubject(subject: Subject, subjectId: String) {
        _subject.value = subject
        _subjectId.value = subjectId
    }

    fun updatePost(post: Post) {
        _threePosts.add(0, post)
        if (_threePosts.size > 3) _threePosts.removeAt(_threePosts.size - 1)
        subject.value.postCount = subject.value.postCount + 1
    }

    fun deleteSubject(): Boolean {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                db.collection("subject").document(subjectId.value).delete().await()  // .await() 사용
                val index = _subjectIdList.indexOf(subjectId.value)
                if (index >= 0) {
                    _subjectList.removeAt(index)
                    _subjectIdList.removeAt(index)
                }
            }

            return true
        } catch (e: Exception) {
            return  false
        }


    }

    fun isVotedByUser(userId : String) {

        viewModelScope.launch(Dispatchers.IO) {
            val postRef = db.collection("subject")
                .document(subjectId.value)

            val likeRef = postRef.collection("like").document(userId)

            likeRef.get().addOnSuccessListener { result ->
                if(result.exists()) {
                    val choiceText = result.toObject(Choice::class.java)
                    if(choiceText != null) _choice.value = choiceText.choice
                }
            }

        }


    }
   fun setUserVote(userId : String, type : String) {
       viewModelScope.launch(Dispatchers.IO) {
           val subjectRef = db.collection("subject")
               .document(subjectId.value)

           val choiceRef = subjectRef.collection("choice").document(userId)

           choiceRef.get().addOnSuccessListener { document ->
               if(!document.exists()) {
                    choiceRef.set(Choice(type))

                   if(type == "agree") {
                       _subject.value.agreeCount += 1
                       _choice.value = "agree"
                       subjectRef.update("agreeCount", FieldValue.increment(1))
                   }
                   else if(type == "disagree") {
                       _subject.value.disagreeCount += 1
                       _choice.value = "disagree"
                       subjectRef.update("disagreeCount", FieldValue.increment(1))
                   }
                   else {
                       _subject.value.neutralCount += 1
                       _choice.value = "neutral"
                       subjectRef.update("neutralCount", FieldValue.increment(1))
                   }
               }
               else {
                   val userChoice = document.toObject(Choice::class.java)!!.choice
                   if(userChoice == type) {
                       _choice.value = ""
                       choiceRef.delete()
                       subjectRef.update(type + "Count", FieldValue.increment(-1))
                       when(type) {
                           "agree" -> _subject.value.agreeCount -= 1
                           "disagree" -> _subject.value.disagreeCount -= 1
                           else -> _subject.value.neutralCount -= 1
                       }



                   }
                   else {
                       _choice.value = type
                       subjectRef.update(userChoice + "Count", FieldValue.increment(1))
                       subjectRef.update(type + "Count", FieldValue.increment(1))
                       choiceRef.update("choice", type)

                       when(userChoice) {
                           "agree" -> _subject.value.agreeCount -= 1
                           "disagree" -> subject.value.disagreeCount -= 1
                           else -> _subject.value.neutralCount -= 1
                       }

                       when(type) {
                           "agree" -> _subject.value.agreeCount += 1
                           "disagree" -> _subject.value.disagreeCount += 1
                            else -> _subject.value.neutralCount += 1
                       }


                   }


               }
           }

       }

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





}

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

                _commentList.add(0, comment)
                _commentIdList.add(0, commentId)
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