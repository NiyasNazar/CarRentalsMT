package com.pas.carrentalsmt.data

import okhttp3.ResponseBody

sealed class DataState<out R> {

    data class Success<out T>(val data: T, val respCode: Int? = null): DataState<T>()
    data class Error(
        val isNetworkError: Boolean,
        val errorCode: Int?,
        val errorBody: ResponseBody?,
        val message: String?
    ): DataState<Nothing>()
}