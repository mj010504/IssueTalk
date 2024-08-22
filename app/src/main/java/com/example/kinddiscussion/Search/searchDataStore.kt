package com.example.kinddiscussion.Search

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// DataStore 초기화
val Context.dataStore by preferencesDataStore(name = "search_dataStore")

val keywordArrayKey = stringPreferencesKey("keywordArray")
val autoStoreKey = booleanPreferencesKey("autoStoreKey")

// 문자열 배열 저장 함수
suspend fun saveKeyword(context: Context, newString: String) {
    context.dataStore.edit { preferences ->
        val currentArray = preferences[keywordArrayKey]?.split(",")?.toMutableList() ?: mutableListOf()
        currentArray.removeAll { it.isBlank() }
        currentArray.add(0, newString)
        preferences[keywordArrayKey] = currentArray.joinToString(",")
    }
}

fun getKeywordArray(context: Context) = context.dataStore.data.map { preferences ->
    val savedString = preferences[keywordArrayKey] ?: ""
    if (savedString.isEmpty()) emptyList() else savedString.split(",")
}

// 문자열 배열을 모두 삭제하는 함수
suspend fun clearKeywords(context: Context) {
    context.dataStore.edit { preferences ->
        preferences[keywordArrayKey] = ""
    }
}

// Boolean 값 저장 함수
suspend fun saveAutoStore(context: Context, value: Boolean) {
    context.dataStore.edit { preferences ->
        preferences[autoStoreKey] = value
    }
}

fun getAutoStore(context: Context): Flow<Boolean> {
    return context.dataStore.data.map { preferences ->
        preferences[autoStoreKey] ?: true
    }
}
