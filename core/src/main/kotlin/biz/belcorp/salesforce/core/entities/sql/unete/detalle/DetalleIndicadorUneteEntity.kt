package biz.belcorp.salesforce.core.entities.sql.unete.detalle

import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.google.gson.annotations.SerializedName
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel

@Table(database = AppDatabase::class, name = "DetalleIndicadorUnete")
open class DetalleIndicadorUneteEntity : BaseModel() {

    @PrimaryKey
    @Column(name = "uuid")
    var uuid: String = java.util.UUID.randomUUID().toString()

    @Column(name = "CampaniaActual")
    @SerializedName("campaniaActual")
    var campaniaActual: String? = null

    @Column(name = "Seccion")
    @SerializedName("seccion")
    var seccion: String? = null

    @Column(name = "Zona")
    @SerializedName("zona")
    var zona: String? = null

    @Column(name = "Nombre")
    @SerializedName("nombre")
    var nombre: String? = null

    @Column(name = "EnEvaluacion")
    @SerializedName("enEvaluacion")
    var enEvaluacion: Int? = 0

    @Column(name = "PreAprobadas")
    @SerializedName("preAprobadas")
    var preAprobadas: Int? = 0

    @Column(name = "Aprobadas")
    @SerializedName("aprobadas")
    var aprobadas: Int? = 0

    @Column(name = "Rechazadas")
    @SerializedName("rechazadas")
    var rechazadas: Int? = 0

    @Column(name = "Conversion")
    @SerializedName("conversion")
    var conversion: Int? = 0

    @Column(name = "DiasEnEspera")
    @SerializedName("diasEnEspera")
    var diasEnEspera: Int? = 0

    @Column(name = "IngresosAnticipados")
    @SerializedName("ingresosAnticipados")
    var ingresosAnticipados: Int? = 0

    @Column(name = "PreInscritas")
    @SerializedName("preInscritas")
    var preInscritas: Int? = 0

    @Column(name = "RegularizarDocumento")
    @SerializedName("regularizarDocumento")
    var regularizarDocumento: Int? = 0

    @Column(name = "ProactivoFinalizar")
    @SerializedName("proactivosPorFinalizar")
    var proactivoFinalizar: Int? = 0

    @Column(name = "ProactivoFinalizados")
    @SerializedName("proactivosFinalizados")
    var proactivoFinalizados: Int? = 0

    @Column(name = "ProactivoPreAprobados")
    @SerializedName("proactivosPreAprobados")
    var proactivoPreAprobados: Int? = 0

}
