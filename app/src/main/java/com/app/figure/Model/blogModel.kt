package com.app.figure.Model

import android.os.Parcel
import android.os.Parcelable

data class BlogModel(
    val adress: String?,
    val cmnd: String?,
    val content: String?,
    val created_at: String?,
    val dates: String?,
    val email: String?,
    val feeldislike: Int,
    val feellike: Int,
    val fullname: String?,
    val id_blog: Int,
    val id_cus: Int,
    val id_per: Int,
    val img_bg: String?,
    val sdt: Int,
    val title: String?,
    val updated_at: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(adress)
        parcel.writeString(cmnd)
        parcel.writeString(content)
        parcel.writeString(created_at)
        parcel.writeString(dates)
        parcel.writeString(email)
        parcel.writeInt(feeldislike)
        parcel.writeInt(feellike)
        parcel.writeString(fullname)
        parcel.writeInt(id_blog)
        parcel.writeInt(id_cus)
        parcel.writeInt(id_per)
        parcel.writeString(img_bg)
        parcel.writeInt(sdt)
        parcel.writeString(title)
        parcel.writeString(updated_at)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BlogModel> {
        override fun createFromParcel(parcel: Parcel): BlogModel {
            return BlogModel(parcel)
        }

        override fun newArray(size: Int): Array<BlogModel?> {
            return arrayOfNulls(size)
        }
    }
}
data class BlogCommentModel(
    val adress: String,
    val cmnd: String,
    val content: String,
    val created_at: String,
    val dates: String,
    val email: String,
    var img:String,
    val fullname: String,
    val id_blog: Int,
    val id_com: Int,
    val id_cus: Int,
    val id_per: Int,
    val sdt: Int,
    val updated_at: String
)
