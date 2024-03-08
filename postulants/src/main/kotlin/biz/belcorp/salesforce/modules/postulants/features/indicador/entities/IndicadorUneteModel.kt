package biz.belcorp.salesforce.modules.postulants.features.indicador.entities

import android.os.Parcel
import android.os.Parcelable
import biz.belcorp.salesforce.modules.postulants.utils.Constant

class IndicadorUneteModel : Parcelable, Cloneable {
    var campaniaActual: String? = null
    var enEvaluacion: Int? = Constant.NUMERO_CERO
    var preAprobadas: Int? = Constant.NUMERO_CERO
    var aprobadas: Int? = Constant.NUMERO_CERO
    var rechazadas: Int? = Constant.NUMERO_CERO
    var conversion: Int? = Constant.NUMERO_CERO
    var diasEnEspera: Int? = Constant.NUMERO_CERO
    var ingresosAnticipados: Int? = Constant.NUMERO_CERO
    var preInscritas: Int? = Constant.NUMERO_CERO
    var regularizarDocumento: Int? = Constant.NUMERO_CERO

    constructor()

    protected constructor(ing: Parcel) {
        campaniaActual = ing.readString()
        enEvaluacion = ing.readInt()
        preAprobadas = ing.readInt()
        aprobadas = ing.readInt()
        rechazadas = ing.readInt()
        conversion = ing.readInt()
        diasEnEspera = ing.readInt()
        ingresosAnticipados = ing.readInt()
        preInscritas = ing.readInt()
    }

    override fun describeContents(): Int {
        return Constant.NUMERO_CERO
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(campaniaActual)
        dest.writeInt(enEvaluacion ?: 0)
        dest.writeInt(preAprobadas ?: 0)
        dest.writeInt(aprobadas ?: 0)
        dest.writeInt(rechazadas ?: 0)
        dest.writeInt(conversion ?: 0)
        dest.writeInt(diasEnEspera ?: 0)
        dest.writeInt(ingresosAnticipados ?: 0)
        dest.writeInt(preInscritas ?: 0)
        dest.writeInt(regularizarDocumento ?: 0)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<IndicadorUneteModel> =
            object : Parcelable.Creator<IndicadorUneteModel> {
                override fun createFromParcel(par: Parcel): IndicadorUneteModel {
                    return IndicadorUneteModel(par)
                }

                override fun newArray(size: Int): Array<IndicadorUneteModel?> {
                    return arrayOfNulls(size)
                }
            }
    }
}
