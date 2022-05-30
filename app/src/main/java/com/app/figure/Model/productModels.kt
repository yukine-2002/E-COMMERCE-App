package com.app.figure.Model

import android.os.Parcel
import android.os.Parcelable

data class productModels(
    val id_pro: Int,
    val id_Cate: Int,
    val name_pro: String?,
    val price_old: String?,
    val price_new: String?,
    val soluong: Int,
    val danhgia: Float,
    val mieuta: String?,
    val chieucao: String?,
    val image: String?,
    val xuatsu: String?,
    val noibat: Int,
    val created_at: String?,
    val updated_at: String?,

    ): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readFloat(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id_pro)
        parcel.writeInt(id_Cate)
        parcel.writeString(name_pro)
        parcel.writeString(price_old)
        parcel.writeString(price_new)
        parcel.writeInt(soluong)
        parcel.writeFloat(danhgia)
        parcel.writeString(mieuta)
        parcel.writeString(chieucao)
        parcel.writeString(image)
        parcel.writeString(xuatsu)
        parcel.writeInt(noibat)
        parcel.writeString(created_at)
        parcel.writeString(updated_at)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<productModels> {
        override fun createFromParcel(parcel: Parcel): productModels {
            return productModels(parcel)
        }

        override fun newArray(size: Int): Array<productModels?> {
            return arrayOfNulls(size)
        }
    }
}
data class commentModels(
    val adress: String,
    val cmnd: String,
    val content: String,
    val created_at: String,
    val date: String,
    val dates: String,
    val email: String,
    val img : String,
    val fullname: String,
    val id_com: Int,
    val id_cus: Int,
    val id_per: Int,
    val id_pro: Int,
    val sdt: Int,
    val updated_at: String
)
data class imgProduct(
    val id_img: Int,
    val img1: String,
    val img2: String,
    val img3: String,
    val created_at: String,
    val updated_at: String
)

