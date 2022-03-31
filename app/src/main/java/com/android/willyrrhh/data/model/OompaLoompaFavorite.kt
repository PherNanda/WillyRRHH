package com.android.willyrrhh.data.model

import android.os.Parcel
import android.os.Parcelable

data class OompaLoompaFavorite(
    val color: String?,
    val food: String?,
    val random_string: String?,
    val song: String?
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(color)
        parcel.writeString(food)
        parcel.writeString(random_string)
        parcel.writeString(song)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<OompaLoompaFavorite> {
        override fun createFromParcel(parcel: Parcel): OompaLoompaFavorite {
            return OompaLoompaFavorite(parcel)
        }

        override fun newArray(size: Int): Array<OompaLoompaFavorite?> {
            return arrayOfNulls(size)
        }
    }

}
