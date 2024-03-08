package biz.belcorp.salesforce.modules.calculator.model

import android.os.Parcel
import android.os.Parcelable
import biz.belcorp.salesforce.modules.calculator.util.Constant.EMPTY_DOUBLE

class CalculatingResultDetailModel() : Parcelable {
    var codDetalle: Int? = null
    var cantidad: Int? = null
    var etiqueta: String? = null
    var descripcion: String? = null
    var valor: Double? = EMPTY_DOUBLE
    var orden: Int? = 0
    var calculado: Boolean = false

    constructor(parcel: Parcel) : this() {
        codDetalle = parcel.readInt()
        cantidad = parcel.readInt()
        etiqueta = parcel.readString()
        descripcion = parcel.readString()
        valor = parcel.readDouble()
        orden = parcel.readInt()
        calculado = parcel.readByte() != 0.toByte()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(codDetalle ?: 0)
        parcel.writeInt(cantidad ?: 0)
        parcel.writeString(etiqueta)
        parcel.writeString(descripcion)
        parcel.writeDouble(valor ?: 0.0)
        parcel.writeInt(orden ?: 0)
        parcel.writeByte(if (calculado) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CalculatingResultDetailModel> {
        override fun createFromParcel(parcel: Parcel): CalculatingResultDetailModel {
            return CalculatingResultDetailModel(parcel)
        }

        override fun newArray(size: Int): Array<CalculatingResultDetailModel?> {
            return arrayOfNulls(size)
        }
    }
}
