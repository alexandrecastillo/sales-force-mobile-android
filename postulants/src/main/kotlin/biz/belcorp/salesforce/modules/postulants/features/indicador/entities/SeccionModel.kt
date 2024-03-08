package biz.belcorp.salesforce.modules.postulants.features.indicador.entities

import android.os.Parcel
import android.os.Parcelable
import biz.belcorp.salesforce.modules.postulants.utils.Constant

class SeccionModel : BaseGeo {
    var seccionId: Int = Constant.NUMERO_CERO
    var sociaEmpresaria: String? = null

    constructor()

    protected constructor(parcel: Parcel) : super(parcel) {
        seccionId = parcel.readInt()
        sociaEmpresaria = parcel.readString()
    }

    override fun describeContents(): Int {
        return Constant.NUMERO_CERO
    }

    override fun writeToParcel(parcel: Parcel, i: Int) {
        parcel.writeInt(seccionId)

        parcel.writeString(sociaEmpresaria)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<SeccionModel> = object : Parcelable.Creator<SeccionModel> {
            override fun createFromParcel(parcel: Parcel): SeccionModel {
                return SeccionModel(
                    parcel
                )
            }

            override fun newArray(size: Int): Array<SeccionModel?> {
                return arrayOfNulls(size)
            }
        }

    }
}
