package biz.belcorp.salesforce.modules.inspires.model

import android.os.Parcel
import android.os.Parcelable
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import biz.belcorp.salesforce.core.constants.Constant

class IndicadorInspiraModel : IndicatorModel {

    var id: Int = 0
    var campania: Int = 0
    var ranking: Int = 0
    var destino: String? = null
    var nivel: String? = null
    var puntaje: String? = null
    var puntajeMax: String? = null
    var nombreSE: String? = null
    var campaniaInicioPrograma: Int = 0
    var campaniaFinPrograma: Int = 0
    var activa: Boolean = false
    var topeRanking: Int = 0
    var imagenDestino: String? = null

    constructor()

    protected constructor(`in`: Parcel) : super(`in`)


    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(id)
        dest.writeInt(campania)
        dest.writeInt(ranking)
        dest.writeString(destino)
        dest.writeString(nivel)
        dest.writeString(puntaje)
        dest.writeString(puntajeMax)
        dest.writeString(nombreSE)
        dest.writeInt(campaniaInicioPrograma)
        dest.writeInt(campaniaFinPrograma)
        dest.writeByte((if (activa) 1 else 0).toByte())
        dest.writeInt(topeRanking)
        dest.writeString(imagenDestino)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun loadHeader(fragmentTransaction: FragmentTransaction, fragmentManager: FragmentManager) {
        this.position = Constant.POSITION_VIAJE_INSPIRA
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<IndicadorInspiraModel> = object : Parcelable.Creator<IndicadorInspiraModel> {
            override fun createFromParcel(`in`: Parcel): IndicadorInspiraModel {
                return IndicadorInspiraModel(`in`)
            }

            override fun newArray(size: Int): Array<IndicadorInspiraModel?> {
                return arrayOfNulls(size)
            }
        }
    }
}
