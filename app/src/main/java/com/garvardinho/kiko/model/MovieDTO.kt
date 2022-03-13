package com.garvardinho.kiko.model

import android.os.Parcel
import android.os.Parcelable

data class MovieDTO(
    val results: List<MovieResultDTO>,
)

data class MovieResultDTO(
    val title: String = "New movie",
    val poster_path: String?,
    val release_date: String?,
    val vote_average: Double = 10.0,
    var isFavorite: Boolean = false,
    val overview: String? = null,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString(),
        parcel.readString(),
        parcel.readDouble(),
        parcel.readByte() != 0.toByte(),
        parcel.readString())

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.apply {
            writeString(title)
            writeString(poster_path)
            writeString(release_date)
            writeDouble(vote_average)
            writeByte(if (isFavorite) 1 else 0)
            writeString(overview)
        }
    }

    companion object CREATOR : Parcelable.Creator<MovieResultDTO> {
        override fun createFromParcel(parcel: Parcel): MovieResultDTO {
            return MovieResultDTO(parcel)
        }

        override fun newArray(size: Int): Array<MovieResultDTO?> {
            return arrayOfNulls(size)
        }
    }

    fun toManaged(): MovieResultDTOManaged {
        return MovieResultDTOManaged(
            title,
            poster_path,
            release_date,
            vote_average,
            isFavorite,
            overview
        )
    }
}
