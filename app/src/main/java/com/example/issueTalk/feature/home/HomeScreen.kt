
package com.example.issueTalk.feature.home

import androidx.compose.runtime.Composable

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import com.example.issueTalk.core.network.model.Subject



@Composable
fun HomeRoute(
//    subjectViewModel: SubjectViewModel
) {
    HomeScreen()
}


@Composable
fun HomeScreen(

) {
//
//
//    subjectList = subjectViewModel.subjectList
//    subjectIdList = subjectViewModel.subjectIdList
//
//    val tabs = listOf("전체", "사회", "정치", "경제", "연예", "기타")
//    var selectedTabIndex by remember {mutableStateOf(0)}
//    var showLogInDialog by remember { mutableStateOf(false) }
//
//    var isRefreshing by remember { mutableStateOf(false) }
//    val pullRefreshState = rememberPullRefreshState(isRefreshing, {
//        isRefreshing = true
//        subjectViewModel.fetchSubjects()
//        isRefreshing = false
//    })
//
//    val coroutineScope = rememberCoroutineScope()
//    var isClickable by remember { mutableStateOf(true) }
//
//    Box(
//            modifier = Modifier
//                .pullRefresh(pullRefreshState)
//                .fillMaxSize()
//        ) {
//
//            LazyColumn(
//
//            ) {
//                item {
//                    TabRow(
//                        selectedTabIndex = selectedTabIndex,
//                        backgroundColor = tabGreenColor,
//                        contentColor = Color.White
//                    ){
//                        tabs.forEachIndexed {index, title ->
//                            Tab(
//                                text = {Text(title, style = TextStyle(fontWeight = FontWeight.Bold))},
//                                selected = selectedTabIndex == index,
//                                onClick = {
//                                    if(isClickable) {
//                                        isClickable = false
//                                        selectedTabIndex = index
//                                        subjectViewModel.fetchSubjectByField(tabs[index])
//
//                                        coroutineScope.launch {
//                                            delay(300)
//                                            isClickable = true
//                                        }
//                                    }
//
//                                }
//
//                            )
//                        }
//                    }
//
//                    writeSubjectButton()
//                    blackLine()
//                }
//                items(subjectList.size) { index ->
//                    subjectLayout(navCotnroller,index, subjectViewModel)
//                }
//            }
//
//            PullRefreshIndicator(
//                refreshing = isRefreshing,
//                state = pullRefreshState,
//                contentColor = Color.Black,
//                modifier = Modifier.align(Alignment.TopCenter)
//            )
//        }
//
//
//
//
//    if(showLogInDialog) {
//        checkCancleDialog(onCheck = {  },
//            onDismiss = { showLogInDialog = false}, dialogText = "로그인 후 이용이 가능합니다. 로그인하시겠습니까?" )
//    }
//
//}
//
//@Composable
//fun writeSubjectButton (
//
//) {
//
//    var isClickable by remember { mutableStateOf(true) }
//
//
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(top = 10.dp, end = 11.dp),
//        horizontalArrangement = Arrangement.End
//    ) {
//        OutlinedButton(
//            onClick ={
//                val auth = FirebaseAuth.getInstance()
//                val user = auth.currentUser
//                if(user!= null) {
//                    if(isClickable) {
//                        isClickable = false
////                        navCotnroller.navigate("writeSubject")
//                    }
//
//                }
//                else {
////                    onShowDialog()
//                }
//
//
//       },
//            colors = ButtonDefaults.outlinedButtonColors(
//                contentColor = selectedColor
//            ),
//            border = BorderStroke(1.dp, selectedColor)
//
//        ) {
//            Text(text = "주제 작성하기")
//        }
//    }
//
//}
//
//@Composable
//fun subjectLayout(
//    index : Int,
//    subjectViewModel: SubjectViewModel
//) {
//
//    val subject = subjectList[index]
//    val subjectId = subjectIdList[index]
//    var isClickable by remember { mutableStateOf(true) }
//    val coroutineScope = rememberCoroutineScope()
//
//    Box(
//            modifier = Modifier
//                .clickable {
//                    if (isClickable) {
//                        isClickable = false
//                        val auth = FirebaseAuth.getInstance()
//                        val user = auth.currentUser
//
//                        subjectViewModel.setSubject(subject, subjectId)
//                        subjectViewModel.fetchLatestThreePosts()
//                        if (user != null) subjectViewModel.isVotedByUser(user.uid)
////                        navCotnroller.navigate("subject")
//
//                        coroutineScope.launch {
//                            delay(300)
//                            isClickable = true
//                        }
//                    }
//                }
//                .wrapContentSize()
//        ) {
//            Row() {
//                val fieldImage = fieldToImage(subject.subjectField)
//                Icon(
//                    painter = painterResource(id = fieldImage), contentDescription = null,
//                    tint = Color.Unspecified,
//                    modifier = Modifier
//                        .padding(start = 6.dp)
//                        .width(30.dp)
//                        .height(30.dp)
//                )
//
//                Text(
//                    subject.title,
//                    modifier = Modifier
//                        .weight(1f)
//                        .padding(start = 18.dp, end = 15.dp),
//                    maxLines = 1
//                )
//
//            }
//        }
//
//
//    Box(
//        modifier = Modifier
//            .clickable {
//                if (isClickable) {
//                    isClickable = false
//                    subjectViewModel.setSubject(subject, subjectId)
//                    subjectViewModel.fetchLatestThreePosts()
////                    navCotnroller.navigate("subject")
//
//                    coroutineScope.launch {
//                        delay(300)
//                        isClickable = true
//                    }
//                }
//
//            }
//            .wrapContentSize()
//    ) {
//        Row(
//            modifier = Modifier.fillMaxWidth()
//        )
//        {
//            Text(subject.subjectField, modifier = Modifier.padding(start = 6.dp, top = 3.dp)
//            ,style  =TextStyle(fontWeight = FontWeight.Bold)
//            )
//            Icon(
//                painter = painterResource(id = R.drawable.agree), contentDescription = null,
//                modifier = Modifier
//                    .padding(start = 18.dp)
//                    .width(20.dp)
//                    .height(20.dp)
//
//            )
//
//            Text(subject.agreeCount.toString(), modifier = Modifier.padding(top = 5.dp, start = 4.dp))
//            Icon(
//                painter = painterResource(id = R.drawable.disagree), contentDescription = null,
//                modifier = Modifier
//                    .padding(start = 8.dp)
//                    .width(20.dp)
//                    .height(20.dp)
//
//            )
//
//            Text(subject.disagreeCount.toString(), modifier = Modifier.padding(top = 5.dp, start = 4.dp))
//
//            Icon(
//                painter = painterResource(id = R.drawable.scale), contentDescription = null,
//                modifier = Modifier
//                    .padding(start = 8.dp)
//                    .width(20.dp)
//                    .height(20.dp)
//
//            )
//
//            Text(subject.neutralCount.toString(), modifier = Modifier.padding(top = 5.dp, start = 4.dp))
//
//            Text(
//                subject.date,
//                modifier = Modifier.padding(top = 5.dp, start = 11.dp),
//                color = Color.Gray,
//                style = TextStyle(fontSize = 12.sp)
//            )
//
//
//        }
//    }
//
//    blackLine()
}


