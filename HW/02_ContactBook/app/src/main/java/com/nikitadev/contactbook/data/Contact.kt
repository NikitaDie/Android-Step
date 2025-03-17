package com.nikitadev.contactbook.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Contact(
    val id: Int,
    val name: String,
    val phone: String
) : Parcelable