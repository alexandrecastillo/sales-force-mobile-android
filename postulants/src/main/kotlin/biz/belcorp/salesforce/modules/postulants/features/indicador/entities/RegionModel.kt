package biz.belcorp.salesforce.modules.postulants.features.indicador.entities

import android.os.Parcel
import android.os.Parcelable
import biz.belcorp.salesforce.modules.postulants.utils.Constant

class RegionModel : BaseGeo {
    var regionId: Int = Constant.NUMERO_CERO
    var gerenteRegion: String? = null
    var grEmail: String? = null

    constructor()

    protected constructor(ing: Parcel) : super(ing) {
        regionId = ing.readInt()
        gerenteRegion = ing.readString()
        grEmail = ing.readString()
    }

    override fun describeContents(): Int {
        return Constant.NUMERO_CERO
    }

    override fun writeToParcel(parcel: Parcel, i: Int) {
        parcel.writeInt(regionId)
        parcel.writeString(gerenteRegion)
        parcel.writeString(grEmail)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<RegionModel> = object : Parcelable.Creator<RegionModel> {
            override fun createFromParcel(ing: Parcel): RegionModel {
                return RegionModel(ing)
            }

            override fun newArray(size: Int): Array<RegionModel?> {
                return arrayOfNulls(size)
            }
        }
    }
}
