package biz.belcorp.salesforce.modules.calculator.model

import android.os.Parcel
import android.os.Parcelable
import biz.belcorp.salesforce.modules.calculator.util.Constant

class CalculatingResultModel : Parcelable {
    var codResultado: Int? = null
    var codRegion: String? = null
    var codZona: String? = null
    var codSeccion: String? = null
    var campania: String? = null
    var valorResultado: Double? = Constant.EMPTY_DOUBLE
    var exitoso: Boolean = false
    var bono: Double? = Constant.EMPTY_DOUBLE
    var detalleResultadoCalculadora: List<CalculatingResultDetailModel> = emptyList()
    var calculated: Boolean = false
    var isSaved: Boolean = false

    constructor()

    constructor(parcel: Parcel) : this() {
        codResultado = parcel.readValue(Int::class.java.classLoader) as? Int
        codRegion = parcel.readString()
        codZona = parcel.readString()
        codSeccion = parcel.readString()
        campania = parcel.readString()
        valorResultado = parcel.readValue(Float::class.java.classLoader) as? Double
        exitoso = parcel.readByte() != 0.toByte()
        bono = parcel.readValue(Float::class.java.classLoader) as? Double
        detalleResultadoCalculadora = parcel.createTypedArrayList(CalculatingResultDetailModel)!!
        calculated = parcel.readByte() != 0.toByte()
        isSaved = parcel.readByte() != 0.toByte()

    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(codResultado)
        parcel.writeString(codRegion)
        parcel.writeString(codZona)
        parcel.writeString(codSeccion)
        parcel.writeString(campania)
        parcel.writeValue(valorResultado)
        parcel.writeByte(if (exitoso) 1 else 0)
        parcel.writeValue(bono)
        parcel.writeTypedList(detalleResultadoCalculadora)
        parcel.writeByte(if (calculated) 1 else 0)
        parcel.writeByte(if (isSaved) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CalculatingResultModel> {
        override fun createFromParcel(parcel: Parcel): CalculatingResultModel {
            return CalculatingResultModel(parcel)
        }

        override fun newArray(size: Int): Array<CalculatingResultModel?> {
            return arrayOfNulls(size)
        }
    }
}
