package com.pas.carrentalsmt.data.repository


import com.pas.carrentalsmt.utils.AppConstants
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class CarFinderRepository @Inject constructor(
) : BaseRepository() {

    fun generateKayakUrl(
        pickUpLocation: String?,
        dropOffLocation: String?,
        pickUpDate: String?,
        dropOffDate: String?
    ): String {


        return if (dropOffLocation.isNullOrEmpty()) {
            "${AppConstants.BASE_URL}/$pickUpLocation/$pickUpDate/$dropOffDate"
        } else {
            "${AppConstants.BASE_URL}/$pickUpLocation/$dropOffLocation/$pickUpDate/$dropOffDate"
        }
    }



}