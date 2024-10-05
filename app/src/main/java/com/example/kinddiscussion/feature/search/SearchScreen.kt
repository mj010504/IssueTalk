package com.example.kinddiscussion.feature.search

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.kinddiscussion.core.network.model.Subject
import com.example.kinddiscussion.feature.home.SubjectViewModel
import com.example.kinddiscussion.R
import com.example.kinddiscussion.core.common.util.fieldToImage
import com.example.kinddiscussion.core.datastore.search.clearKeywords
import com.example.kinddiscussion.core.datastore.search.getAutoStore
import com.example.kinddiscussion.core.datastore.search.getKeywordArray
import com.example.kinddiscussion.core.datastore.search.removeKeywords
import com.example.kinddiscussion.core.datastore.search.saveAutoStore
import com.example.kinddiscussion.core.datastore.search.saveKeyword
import com.example.kinddiscussion.core.designsystem.component.blackLine
import com.example.kinddiscussion.core.designsystem.component.checkDialog
import com.example.kinddiscussion.core.designsystem.component.grayLine
import com.example.kinddiscussion.core.designsystem.theme.searchFieldColor
import com.example.kinddiscussion.core.designsystem.theme.selectedColor
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

lateinit var searchList : List<Subject>
lateinit var searchIdList : List<String>
@Composable
fun SearchScreen(
    navController : NavController,
    searchViewModel: SearchViewModel,
    subjectViewModel: SubjectViewModel,
    context: Context

) {

    var isSearched by remember { mutableStateOf(false) }
    var searchText by rememberSaveable { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var dialogText by remember { mutableStateOf("") }

    val keywordArray by getKeywordArray(context).collectAsState(initial = emptyList())
    val autoStore by getAutoStore(context).collectAsState(initial = true)
    val scope = rememberCoroutineScope()

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    searchList = searchViewModel.searchList
    searchIdList = searchViewModel.searchIdList



    
    BackHandler {
        navController.popBackStack()
    }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    painter = painterResource(id = R.drawable.arrow_back),
                    contentDescription = null,
                    modifier = Modifier.padding()
                )
            }

            TextField(
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 10.dp, bottom = 10.dp),
                value = searchText,
                leadingIcon = {
                    Icon(painter = painterResource(id = R.drawable.search),
                        contentDescription = null, modifier = Modifier
                            .width(25.dp)
                            .height(25.dp))
                },
                maxLines = 1,
                onValueChange = { newText -> searchText = newText },
                placeholder = {
                    Text(
                        text = "원하는 주제를 입력해주세요.",
                        style = TextStyle(fontSize = 20.sp)
                    )
                }, colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = searchFieldColor,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    cursorColor = selectedColor
                ),
                shape = RoundedCornerShape(20.dp),
                textStyle = TextStyle(fontSize = 20.sp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Search // 검색 동작 설정
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        isSearched = true
                        if(searchText != "")  {
                            searchViewModel.search(searchText)
                            focusManager.clearFocus()
                            keyboardController?.hide()
                            if(autoStore) scope.launch { saveKeyword(context, searchText) }

                        }
                        else {
                            showDialog = true
                            dialogText = "검색어를 입력해주세요!"
                        }

                    })
            )



        }



    if(!isSearched) {
        Text(
            text = "최근 검색어",
            color = Color.Gray,
            modifier = Modifier.padding(start = 10.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))

        if (autoStore) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(15.dp),
                modifier = Modifier.padding(start = 12.dp, end = 12.dp)

            ) {
                items(keywordArray.size) { index ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 10.dp)
                    ) {
                        TextButton(onClick = {
                            isSearched = true
                            searchViewModel.search(keywordArray[index])
                        }) {
                            Text(
                                text = keywordArray[index], color = Color.Black, 
                                modifier = Modifier.padding(start = 8.dp, bottom = 5.dp ), maxLines = 1,
                                style = TextStyle(fontSize = 16.sp)
                            )
                        }
                        Spacer(Modifier.weight(1f))
                        IconButton(onClick = {
                            scope.launch { removeKeywords(context, keywordArray[index]) }
                        }) {
                            Icon(painter = painterResource(id = R.drawable.x_ic),
                                contentDescription = null, tint = Color.Gray)
                        }
                    }
                    
                    grayLine()

                }
            }
        } else {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    "검색어 자동저장 기능이 꺼져있습니다.", color = Color.Gray,
                    style = TextStyle(fontSize = 18.sp)
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
        }




        Row(
            modifier = Modifier.padding(start = 15.dp)
        ) {
            TextButton(onClick = {
                scope.launch { clearKeywords(context) }
            }) {
                Text(text = "전체 삭제", color = Color.Gray, style = TextStyle(fontSize = 12.sp))
            }

            TextButton(onClick = {
                scope.launch {
                    saveAutoStore(context, !autoStore)
                }
            }) {
                Text(
                    text = if (autoStore) "자동저장 끄기" else "자동저장 켜기",
                    color = Color.Gray, style = TextStyle(fontSize = 12.sp)
                )

            }

        }
    } else {
        blackLine()
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.padding(start = 12.dp, end = 12.dp)

        ) {
            items(searchList.size) { index ->
                searchLayout(navController, index, subjectViewModel )
            }
        }

        }


    }


    if(showDialog) {
        checkDialog(onDismiss = { showDialog = false }, dialogText = dialogText)
    }
}


@Composable
fun searchLayout(
    navCotnroller: NavController,
    index : Int,
    subjectViewModel: SubjectViewModel
) {

    val subject = searchList[index]
    val subjectId = searchIdList[index]

    Box(
        modifier = Modifier
            .clickable {
                val auth = FirebaseAuth.getInstance()
                val user = auth.currentUser

                subjectViewModel.setSubject(subject, subjectId)
                subjectViewModel.fetchLatestThreePosts()
                if (user != null) subjectViewModel.isVotedByUser(user.uid)
                navCotnroller.navigate("subject")
            }
            .wrapContentSize()
    ) {
        Row() {
            val fieldImage = fieldToImage(subject.subjectField)
            Icon(
                painter = painterResource(id = fieldImage), contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier
                    .padding(start = 6.dp)
                    .width(30.dp)
                    .height(30.dp)
            )

            Text(
                subject.title,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 18.dp, end = 15.dp),
                maxLines = 1
            )

        }
    }


    Box(
        modifier = Modifier
            .clickable {
                subjectViewModel.setSubject(subject, subjectId)
                subjectViewModel.fetchLatestThreePosts()
                navCotnroller.navigate("subject")
            }
            .wrapContentSize()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        )
        {
            Text(subject.subjectField, modifier = Modifier.padding(start = 6.dp, top = 3.dp)
                ,style  =TextStyle(fontWeight = FontWeight.Bold)
            )
            Icon(
                painter = painterResource(id = R.drawable.agree), contentDescription = null,
                modifier = Modifier
                    .padding(start = 18.dp)
                    .width(20.dp)
                    .height(20.dp)

            )

            Text(subject.agreeCount.toString(), modifier = Modifier.padding(top = 5.dp, start = 4.dp))
            Icon(
                painter = painterResource(id = R.drawable.disagree), contentDescription = null,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .width(20.dp)
                    .height(20.dp)

            )

            Text(subject.disagreeCount.toString(), modifier = Modifier.padding(top = 5.dp, start = 4.dp))

            Icon(
                painter = painterResource(id = R.drawable.scale), contentDescription = null,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .width(20.dp)
                    .height(20.dp)

            )

            Text(subject.neutralCount.toString(), modifier = Modifier.padding(top = 5.dp, start = 4.dp))

            Text(
                subject.date,
                modifier = Modifier.padding(top = 5.dp, start = 11.dp),
                color = Color.Gray,
                style = TextStyle(fontSize = 12.sp)
            )


        }
    }

    blackLine()
}




