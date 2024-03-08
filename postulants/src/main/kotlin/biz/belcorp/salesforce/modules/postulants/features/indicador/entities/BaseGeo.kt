package biz.belcorp.salesforce.modules.postulants.features.indicador.entities

import android.os.Parcel
import android.os.Parcelable
import biz.belcorp.salesforce.modules.postulants.utils.Constant

open class BaseGeo : Parcelable {

    var codigo: String? = null
    var descripcion: String? = null

    constructor() {}

    protected constructor(ing: Parcel) {
        codigo = ing.readString()
        descripcion = ing.readString()
    }

    override fun describeContents(): Int {
        return Constant.NUMERO_CERO
    }

    override fun writeToParcel(parcel: Parcel, i: Int) {
        parcel.writeString(codigo)
        parcel.writeString(descripcion)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<BaseGeo> = object : Parcelable.Creator<BaseGeo> {
            override fun createFromParcel(ing: Parcel): BaseGeo {
                return BaseGeo(ing)
            }

            override fun newArray(size: Int): Array<BaseGeo?> {
                return arrayOfNulls(size)
            }
        }
    }
}
