package com.andruhavuho.man.like.recipes.models

import android.os.Parcel
import android.os.Parcelable

data class Recipe(
    val id: Long,
    val title: String,
    val imageUri: String?,
    val createdByUser: Boolean,
    val categories: String,
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readByte() != 0.toByte(),
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(title)
        parcel.writeString(imageUri)
        parcel.writeByte(if (createdByUser) 1 else 0)
        parcel.writeString(categories)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Recipe> {
        override fun createFromParcel(parcel: Parcel): Recipe {
            return Recipe(parcel)
        }

        override fun newArray(size: Int): Array<Recipe?> {
            return arrayOfNulls(size)
        }
    }
}
