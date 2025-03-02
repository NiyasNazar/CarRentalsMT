package com.pas.carrentalsmt.ui.find_car

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.acube.itms.utils.date_picker.DatePickerDialogFragment
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.pas.carrentalsmt.BuildConfig
import com.pas.carrentalsmt.R
import com.pas.carrentalsmt.databinding.ActivityCarFinderBinding
import com.pas.carrentalsmt.ui.base.BaseActivity
import com.pas.carrentalsmt.utils.AppConstants
import com.pas.carrentalsmt.utils.AppUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CarFinderActivity :
    BaseActivity<FindCarViewModel, ActivityCarFinderBinding>(),
    DatePickerDialogFragment.DatePickerListener {
    val fields = listOf(Place.Field.ID, Place.Field.NAME)

    var _pickupDate: String? = null
    var _dropOffDate: String? = null
    lateinit var placesClient: PlacesClient

    companion object {
        private const val REQUEST_CODE_PICK_UP = 1
        private const val REQUEST_CODE_DROP_OFF = 2
    }

    override fun getLayout() = R.layout.activity_car_finder
    override fun getViewModelType() = FindCarViewModel::class.java
    override fun bindViews() {
        binding.viewModel = viewModel
        observeData()
        initPlaces()
    }

    private fun initPlaces() {
        val apiKey = BuildConfig.PLACES_API_KEY
        if (apiKey.isEmpty() || apiKey == "DEFAULT_API_KEY") {
            Log.e("Places test", "No api key")
            finish()
            return
        }
        Places.initializeWithNewPlacesApiEnabled(applicationContext, apiKey)
        placesClient = Places.createClient(this)


    }

    private val startDropAutocomplete =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
                if (intent != null) {
                    val place = Autocomplete.getPlaceFromIntent(intent)
                    binding.etDropLocation.setText(place.name)
                    Log.i(
                        TAG, "Place: ${place.name}, ${place.id}"
                    )
                }
            } else if (result.resultCode == Activity.RESULT_CANCELED) {

                Log.i(TAG, "User canceled autocomplete")
            }
        }
    private val startPickupAutocomplete =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
                if (intent != null) {
                    val place = Autocomplete.getPlaceFromIntent(intent)
                    binding.etPickupLocation.setText(place.name)
                    Log.i(
                        TAG, "Place: ${place.name}, ${place.id}"
                    )
                }
            } else if (result.resultCode == Activity.RESULT_CANCELED) {

                Log.i(TAG, "User canceled autocomplete")
            }
        }

    private fun observeData() {
        viewModel.url.observe(this) { url ->
            Log.d("observeData", "observeData: " + url)
            openUrlInBrowser(this, url)
        }

        viewModel.messagesLiveData.observe(this) {
            if (this.lifecycle.currentState == Lifecycle.State.RESUMED) {
                showSnackBar(it)
            }
        }
        viewModel.actionLiveData.observe(this) {


            if (this.lifecycle.currentState == Lifecycle.State.RESUMED) {
                when (it) {
                    SearchAction.ACTION_SEARCH_KAYAK -> {
                        viewModel.validateAndSearch()

                    }

                    SearchAction.ACTION_PICKUP_DATE -> {
                        showPickupDatePicker()

                    }

                    SearchAction.ACTION_DROPOFF_DATE -> {
                        showDropoffDatePicker()

                    }

                    SearchAction.ACTION_PICKUP_LOCATION -> {
                        val intent =
                            Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
                                .build(this)
                        startPickupAutocomplete.launch(intent)

                    }

                    SearchAction.ACTION_DROPOFF_LOCATION -> {
                        val intent =
                            Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
                                .build(this)
                        startDropAutocomplete.launch(intent)

                    }
                }
            }
        }
        viewModel.progressLiveData.observe(this) {
            if (lifecycle.currentState == Lifecycle.State.RESUMED) {
                if (it.isShow) {
                    binding.progressButton.visibility = View.VISIBLE
                    binding.btnSearch.visibility = View.INVISIBLE
                } else {
                    binding.progressButton.visibility = View.GONE
                    binding.btnSearch.visibility = View.VISIBLE
                }
            }
        }
        binding.cbReturn.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                binding.tilDrop.visibility = View.GONE
                viewModel.dropoffLocation = null
                binding.etDropLocation.text?.clear()
            } else {
                binding.tilDrop.visibility = View.VISIBLE
            }
        }
    }

    private fun showPickupDatePicker() {
        val datePicker = DatePickerDialogFragment(REQUEST_CODE_PICK_UP, this)
        datePicker.show(supportFragmentManager, "pickup")
    }

    private fun showDropoffDatePicker() {
        val datePicker = DatePickerDialogFragment(REQUEST_CODE_DROP_OFF, this)
        datePicker.show(supportFragmentManager, "droppof")
    }

    override fun trackSessionData(): LiveData<Boolean>? {
        return null
    }

    override fun onDateSelected(requestCode: Int, year: Int, month: Int, dayOfMonth: Int) {
        val (isoDate, formattedDate) = AppUtils.formatDate(year, month, dayOfMonth)

        when (requestCode) {

            REQUEST_CODE_PICK_UP -> {
                binding?.etPickupDate?.setText(formattedDate)
                viewModel.pickupDate = isoDate

            }

            REQUEST_CODE_DROP_OFF -> {
                binding?.etDropOffDate?.setText(formattedDate)
                viewModel.dropoffDate = isoDate
            }
        }
    }


}

private fun openUrlInBrowser(context: Context, url: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    ContextCompat.startActivity(context, intent, null)
}

