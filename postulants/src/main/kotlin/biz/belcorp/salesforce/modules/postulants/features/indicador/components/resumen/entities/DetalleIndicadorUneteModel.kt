package biz.belcorp.salesforce.modules.postulants.features.indicador.components.resumen.entities

import android.os.Parcel
import android.os.Parcelable
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.DetalleIndicador
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.DetalleIndicadorUnete

class DetalleIndicadorUneteModel : DetalleIndicadorModel {

    var id: Int = 0
    var pais: String? = null
    var region: String? = null
    var zona: String? = null
    var seccion: String? = null
    var campania: String? = null
    var nombreCargo: String? = null

    var enEvaluacion: String? = null
    var preAprobadas: String? = null
    var aprobadas: String? = null
    var rechazadas: String? = null
    var ingresosAnticipados: String? = null
    var preInscritas: String? = null
    var regularizarDocumento: String? = null
    var proactivoFinalizar: String? = null
    var proactivoFinalizados: String? = null
    var proactivoPreAprobados: String? = null

    constructor()

    protected constructor(parcel: Parcel) {
        id = parcel.readInt()
        pais = parcel.readString()
        region = parcel.readString()
        zona = parcel.readString()
        seccion = parcel.readString()
        campania = parcel.readString()
        nombreCargo = parcel.readString()
        enEvaluacion = parcel.readString()
        preAprobadas = parcel.readString()
        aprobadas = parcel.readString()
        rechazadas = parcel.readString()
        ingresosAnticipados = parcel.readString()
        preInscritas = parcel.readString()
        regularizarDocumento = parcel.readString()
        proactivoFinalizar = parcel.readString()
        proactivoFinalizados = parcel.readString()
        proactivoPreAprobados = parcel.readString()
    }

    override fun parse(detalle: DetalleIndicador): DetalleIndicadorModel {
        val entity = detalle as DetalleIndicadorUnete
//        this.pais = entity.pais
//        this.region = entity.region
        this.codigo = entity.zona ?: entity.seccion
        this.zona = entity.zona
        this.seccion = entity.seccion
//        this.campania = entity.campania
//        this.nombreCargo = entity.nombreCargo
        this.enEvaluacion = entity.enEvaluacion.toString()
        this.preAprobadas = entity.preAprobadas.toString()
        this.aprobadas = entity.aprobadas.toString()
        this.rechazadas = entity.rechazadas.toString()
        this.ingresosAnticipados = entity.ingresosAnticipados.toString()
        this.preInscritas = entity.preInscritas.toString()
        this.regularizarDocumento = entity.regularizarDocumento.toString()
        this.proactivoFinalizar = entity.proactivoFinalizar.toString()
        this.proactivoFinalizados = entity.proactivoFinalizados.toString()
        this.proactivoPreAprobados = entity.proactivoPreAprobados.toString()

        return this
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, i: Int) {
        parcel.writeInt(id)
        parcel.writeString(pais)
        parcel.writeString(seccion)
        parcel.writeString(zona)
        parcel.writeString(region)
        parcel.writeString(campania)
        parcel.writeString(codigo)
        parcel.writeString(nombreCargo)
        parcel.writeString(enEvaluacion)
        parcel.writeString(preAprobadas)
        parcel.writeString(aprobadas)
        parcel.writeString(rechazadas)
        parcel.writeString(ingresosAnticipados)
        parcel.writeString(preInscritas)
        parcel.writeString(regularizarDocumento)
        parcel.writeString(proactivoFinalizar)
        parcel.writeString(proactivoFinalizados)
        parcel.writeString(proactivoPreAprobados)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<DetalleIndicadorUneteModel> =
            object : Parcelable.Creator<DetalleIndicadorUneteModel> {
                override fun createFromParcel(parcel: Parcel): DetalleIndicadorUneteModel {
                    return DetalleIndicadorUneteModel(parcel)
                }

                override fun newArray(size: Int): Array<DetalleIndicadorUneteModel?> {
                    return arrayOfNulls(size)
                }
            }
    }

}
