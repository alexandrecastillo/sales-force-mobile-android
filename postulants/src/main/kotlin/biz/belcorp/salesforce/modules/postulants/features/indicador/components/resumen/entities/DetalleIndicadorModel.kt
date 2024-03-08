package biz.belcorp.salesforce.modules.postulants.features.indicador.components.resumen.entities

import android.os.Parcel
import android.os.Parcelable
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.DetalleIndicador
import biz.belcorp.salesforce.modules.postulants.utils.Constant

abstract class DetalleIndicadorModel : Parcelable {
    var position: Int = 0

    var isInvoice: Boolean = false

    var codigo: String? = null

    constructor()

    protected constructor(parcel: Parcel) {
        position = parcel.readInt()
        isInvoice = parcel.readByte().toInt() != 0
    }

    abstract fun parse(detalle: DetalleIndicador): DetalleIndicadorModel?

    override fun describeContents(): Int {
        return Constant.NUMERO_CERO
    }

    override fun writeToParcel(parcel: Parcel, i: Int) {
        parcel.writeInt(position)
        parcel.writeByte((if (isInvoice) 1 else 0).toByte())
    }

    companion object {

        val CREATOR: Parcelable.Creator<DetalleIndicadorModel> =
            object : Parcelable.Creator<DetalleIndicadorModel> {
                override fun createFromParcel(parcel: Parcel): DetalleIndicadorModel {
                    return object : DetalleIndicadorModel(parcel) {
                        override fun parse(detalle: DetalleIndicador): DetalleIndicadorModel? {
                            return null
                        }
                    }
                }

                override fun newArray(size: Int): Array<DetalleIndicadorModel?> {
                    return arrayOfNulls(size)
                }
            }
    }

}
