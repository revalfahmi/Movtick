package com.example.movtick.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Checkout (
    var seat: String ?= "",
    var price: String ?= ""
) : Parcelable