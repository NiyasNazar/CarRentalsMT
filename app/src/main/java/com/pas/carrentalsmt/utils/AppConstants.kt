package com.pas.carrentalsmt.utils

object AppConstants {

    const val KAYAK_DOMAIN = "www.kayak.com"
    const val AFFILIATE_ID = "awesomecars"

    const val BASE_URL = "https://$KAYAK_DOMAIN/in?a=$AFFILIATE_ID&url=/cars"

    const val DEFAULT_PROGRESS_STATE = 1010

    const val COMPANY_ID = ""

    const val FONT_NORMAL = 1
    const val FONT_LIGHT = 0
    const val FONT_BOLD = 2

    object UploadStatus {
        const val UPLOAD_PENDING = 1
        const val UPLOAD_FAILED = 2
        const val UPLOAD_COMPLETED = 3
    }


    object LoaderType {
        const val PRIMARY_COLOR = 1
        const val WHITE = 2
    }

    object ErrorLogType {
        const val API = "api"
        const val NETWORK = "network"
        const val NON_FATAL = "non_fatal"
        const val CRASH = "crash"
    }

}