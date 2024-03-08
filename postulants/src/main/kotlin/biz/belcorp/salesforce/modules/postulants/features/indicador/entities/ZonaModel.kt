package biz.belcorp.salesforce.modules.postulants.features.indicador.entities

import android.os.Parcel
import android.os.Parcelable
import biz.belcorp.salesforce.modules.postulants.utils.Constant

class ZonaModel : BaseGeo {
    var zonaId: Int = Constant.NUMERO_CERO

    var gerenteZona: String? = null
    var gzEmail: String? = null

    constructor()

    protected constructor(ing: Parcel) : super(ing) {
        zonaId = ing.readInt()
        gerenteZona = ing.readString()
        gzEmail = ing.readString()
    }

    override fun describeContents(): Int {
        return Constant.NUMERO_CERO
    }

    override fun writeToParcel(parcel: Parcel, i: Int) {
        parcel.writeInt(zonaId)
        parcel.writeString(gerenteZona)
        parcel.writeString(gzEmail)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<ZonaModel> = object : Parcelable.Creator<ZonaModel> {
            override fun createFromParcel(ing: Parcel): ZonaModel {
                return ZonaModel(ing)
            }

            override fun newArray(size: Int): Array<ZonaModel?> {
                return arrayOfNulls(size)
            }
        }
    }
}
