package com.example.taskhub.TaskTrek.models

import android.os.Parcel
import android.os.Parcelable


data class Users(
    val id:String="",
    val name: String="",
    val email: String="",
    val image: String="",
    val modile:Long=0,
    val fcmToken: String=""

): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readLong(),
        parcel.readString()!!
    )

    override fun describeContents()=0

    override fun writeToParcel(dest: Parcel, flags: Int)= with(dest) {
        writeString(id)
        writeString(name)
        writeString(email)
        writeString(image)
        writeLong(modile)
        writeString(fcmToken)
    }

    companion object CREATOR : Parcelable.Creator<Users> {
        override fun createFromParcel(parcel: Parcel): Users {
            return Users(parcel)
        }

        override fun newArray(size: Int): Array<Users?> {
            return arrayOfNulls(size)
        }
    }
}



