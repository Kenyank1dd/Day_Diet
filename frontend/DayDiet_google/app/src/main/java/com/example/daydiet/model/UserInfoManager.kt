package com.example.daydiet.model

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserInfoManager(private val context: Context) {

    // 本地存储用户名和token
    companion object{
        private val Context.userStore: DataStore<Preferences> by preferencesDataStore("user_store")

        val LOGGED = booleanPreferencesKey("LOGGED")

        val USERNAME = stringPreferencesKey("USERNAME")

        val TOKEN = stringPreferencesKey("TOKEN")
    }

    // 获取方法
    val logged: Flow<Boolean> = context.userStore.data.map {
        it[LOGGED] ?: false
    }
    val userName: Flow<String> = context.userStore.data.map {
        it[USERNAME] ?: ""
    }
    val token: Flow<String> = context.userStore.data.map {
        it[TOKEN] ?: ""
    }

    // 存储用户信息
    suspend fun save(userName: String, token: String){
        context.userStore.edit {
            it[LOGGED] = userName.isNotEmpty()
            it[USERNAME] = userName
            it[TOKEN] = token
        }
    }
    suspend fun exit(){
        context.userStore.edit {
            it[LOGGED] = false
            it[USERNAME] = ""
            it[TOKEN] = ""
        }
    }
}