package com.pas.carrentalsmt.ui.find_car


import android.app.Application
import android.util.Log
import androidx.core.content.ContextCompat.getString
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pas.carrentalsmt.R
import com.pas.carrentalsmt.data.repository.CarFinderRepository
import com.pas.carrentalsmt.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FindCarViewModel @Inject constructor(
    private val _repository: CarFinderRepository, private val application: Application
) :
    BaseViewModel() {
    var pickupLocation: String? = null
    var dropoffLocation: String? = null
    var pickupDate: String? = null
    var dropoffDate: String? = null

    private val _url = MutableLiveData<String>()

    val url: LiveData<String> get() = _url

    override fun getRepository() = _repository


    suspend fun generateUrl(
        pickUpLocation: String?,
        dropOffLocation: String?,
        pickUpDate: String?,
        dropOffDate: String?
    ) {
        val generatedUrl =
            _repository.generateKayakUrl(pickUpLocation, dropOffLocation, pickUpDate, dropOffDate)
        Log.e(TAG, "generateUrl: $generatedUrl", )
        delay(1000)
        _url.value = generatedUrl
        setProgressState(false)
    }

    fun actionSearchKayak() {
        setAction(SearchAction.ACTION_SEARCH_KAYAK)
    }

    fun actionPickupDate() {
        setAction(SearchAction.ACTION_PICKUP_DATE)
    }

    fun actionDropoffDate() {
        setAction(SearchAction.ACTION_DROPOFF_DATE)
    }
    fun actionPickupLocation() {
        setAction(SearchAction.ACTION_PICKUP_LOCATION)
    }

    fun actionDropLocation() {
        setAction(SearchAction.ACTION_DROPOFF_LOCATION)
    }



    fun validateAndSearch() {
        if (isValidDeepLink()) {
            viewModelScope.launch {
                setProgressState(true)
                generateUrl(pickupLocation, dropoffLocation, pickupDate, dropoffDate)
            }
        }
    }

    private fun isValidDeepLink(): Boolean {
        var isValid = true
        if (pickupLocation.isNullOrEmpty()) {
            isValid = false
            setErrorMessage("Please select pickup location")
        } else if (pickupDate.isNullOrEmpty()) {
            isValid = false
            setErrorMessage("Please select pickup date")
        } else if (dropoffDate.isNullOrEmpty()) {
            isValid = false
            setErrorMessage("Please select drop-off date")
        }
        return isValid
    }
}