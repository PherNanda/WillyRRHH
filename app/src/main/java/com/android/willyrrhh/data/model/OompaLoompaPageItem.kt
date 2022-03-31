package com.android.willyrrhh.data.model

import android.os.Parcel
import android.os.Parcelable

data class OompaLoompaPageItem(
    val id: Int,
    val first_name: String?,
    val last_name: String?,
    val height: Int,
    val country: String?,
    val age: Int,
    val email: String?,
    val profession: String?,
    val image: String?,
    val gender: String?,
    val favorite: OompaLoompaFavorite?
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(OompaLoompaFavorite::class.java.classLoader)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(first_name)
        parcel.writeString(last_name)
        parcel.writeInt(height)
        parcel.writeString(country)
        parcel.writeInt(age)
        parcel.writeString(email)
        parcel.writeString(profession)
        parcel.writeString(image)
        parcel.writeString(gender)
        parcel.writeParcelable(favorite, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<OompaLoompaPageItem> {
        override fun createFromParcel(parcel: Parcel): OompaLoompaPageItem {
            return OompaLoompaPageItem(parcel)
        }

        override fun newArray(size: Int): Array<OompaLoompaPageItem?> {
            return arrayOfNulls(size)
        }
    }

}
