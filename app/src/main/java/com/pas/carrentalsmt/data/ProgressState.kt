package com.pas.carrentalsmt.data

import com.pas.carrentalsmt.utils.AppConstants

data class ProgressState(val state: Int? = AppConstants.DEFAULT_PROGRESS_STATE, val isShow: Boolean) {
}