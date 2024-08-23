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

    private val _tab = mutableStateOf("전체")
    val tab : State<String> get() = _tab

    init {
        fetchSubjects()
    }

    fun fetchSubjects() {
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

    fun fetchSubjectByField(field: String) {


        viewModelScope.launch(Dispatchers.IO) {
            if(field == "전체" ) {
                fetchSubjects()
            }
            else {
                val subjectRef = db.collection("subject")
                    .orderBy("timestamp", Query.Direction.DESCENDING)

                val query = subjectRef.whereEqualTo("subjectField",field)

                val result = query.get().await()

                _tab.value = field
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

                if(subject.subjectField == tab.value) {
                    val subjectId = docRef.id
                    _subjectList.add(0, subject)
                    _subjectIdList.add(0, subjectId)
                }

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
            val subjectRef = db.collection("subject")
                .document(subjectId.value)

            val choiceRef = subjectRef.collection("choice").document(userId)

            choiceRef.get().addOnSuccessListener { result ->
                if(result.exists()) {
                    _choice.value = result.toObject(Choice::class.java)!!.choice

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

                    _choice.value = type
                    subjectRef.update(type + "Count", FieldValue.increment(1))
                    when(type) {
                        "agree" -> _subject.value.agreeCount += 1
                        "disagree" -> _subject.value.disagreeCount += 1
                        else -> _subject.value.neutralCount += 1
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
                        subjectRef.update(userChoice + "Count", FieldValue.increment(-1))
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
