package com.pas.carrentalsmt.ui.base

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pas.carrentalsmt.data.DataState
import com.pas.carrentalsmt.data.ProgressState
import com.pas.carrentalsmt.data.repository.BaseRepository

import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONException
import javax.inject.Inject


abstract class BaseViewModel : ViewModel() {
    var actionLiveData: MutableLiveData<Int> = MutableLiveData()



    protected var TAG: String = javaClass.simpleName
    val sessionLiveData = MutableLiveData<Boolean>()
    val progressLiveData: MutableLiveData<ProgressState> = MutableLiveData()
    private val _messagesLiveData: MutableLiveData<String> = MutableLiveData()
    val messagesLiveData: LiveData<String> get() = _messagesLiveData





    abstract fun getRepository(): BaseRepository





    private fun logout() {

    }





    fun setSuccessMessage(message: String?) {
        message?.let {
            _messagesLiveData.value = it
        }
    }

    fun setErrorMessage(message: String?) {
        message?.let {
            _messagesLiveData.value = it
        }
    }







    fun setAction(action: Int) {
        actionLiveData.value = action
    }


    fun setProgressState(isShow: Boolean, which: Int? = null) {
        viewModelScope.launch {
            progressLiveData.value = ProgressState(state = which, isShow = isShow)
        }
    }

}