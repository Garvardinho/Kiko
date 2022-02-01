package com.garvardinho.kiko.model

import android.os.Parcel
import android.os.Parcelable

data class Movie(
    val title: String = "New movie",
    val imageRes: String?,
    val year: Int = 2022,
    val month: Int = 10,
    val dayOfMonth: Int = 10,
    val rating: Double = 10.0,
    val isFavourite: Boolean = false,
    val description: String? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readDouble(),
        parcel.readByte() != 0.toByte(),
        parcel.readString())

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(imageRes)
        parcel.writeInt(year)
        parcel.writeInt(month)
        parcel.writeInt(dayOfMonth)
        parcel.writeDouble(rating)
        parcel.writeByte(if (isFavourite) 1 else 0)
        parcel.writeString(description)
    }

    companion object CREATOR : Parcelable.Creator<Movie> {
        override fun createFromParcel(parcel: Parcel): Movie {
            return Movie(parcel)
        }

        override fun newArray(size: Int): Array<Movie?> {
            return arrayOfNulls(size)
        }
    }
}
