package biz.belcorp.salesforce.core.entities.sql.cuenta


import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.google.gson.annotations.SerializedName
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel

@Table(database = AppDatabase::class, name = "CdrProducto")
class CdrProductoEntity : BaseModel() {

    @Column(name = "IdCdrProducto")
    @SerializedName("idCdrProducto")
    @PrimaryKey
    var idCdrProducto: Int = 0

    @Column(name = "Ncdr")
    @SerializedName("ncdr")
    var nCdr: String? = null

    @Column(name = "CodigoConsultora")
    @SerializedName("codigoConsultora")
    var codigoConsultora: String? = null

    @Column(name = "PeriodoProceso")
    @SerializedName("periodoProceso")
    var periodoProceso: String? = null

    @Column(name = "CodMotivoDevolucion")
    @SerializedName("codMotivoDevolucion")
    var codMotivoDevolucion: String? = null

    @Column(name = "DescripcionMotivo")
    @SerializedName("descripcionMotivo")
    var descripcionMotivo: String? = null

    @Column(name = "CuvProductoDevuelto")
    @SerializedName("cuvProductoDevuelto")
    var cuvProductoDevuelto: String? = null

    @Column(name = "DescripcionProductoDevuelto")
    @SerializedName("descripcionProductoDevuelto")
    var descripcionProductoDevuelto: String? = null

    @Column(name = "CantidadProductoDevuelto")
    @SerializedName("cantidadProductoDevuelto")
    var cantidadProductoDevuelto: String? = null

    @Column(name = "ImporteProductoDevuelto")
    @SerializedName("importeProductoDevuelto")
    var importeProductoDevuelto: String? = null

    @Column(name = "CuvProductoSolicitado")
    @SerializedName("cuvProductoSolicitado")
    var cuvProductoSolicitado: String? = null

    @Column(name = "DescripcionProductoSolicitado")
    @SerializedName("descripcionProductoSolicitado")
    var descripcionProductoSolicitado: String? = null

    @Column(name = "CantidadProductoSolicitado")
    @SerializedName("cantidadProductoSolicitado")
    var cantidadProductoSolicitado: String? = null

    @Column(name = "ImporteProductoSolicitado")
    @SerializedName("importeProductoSolicitado")
    var importeProductoSolicitado: String? = null

    @Column(name = "EstadoCdr")
    @SerializedName("estadoCdr")
    var estadoCDR: String? = null

    @Column(name = "CodigoEstadoCdr")
    @SerializedName("codigoEstadoCdr")
    var codigoEstadoCDR: String? = null

    @Column(name = "ObservacionCdr")
    @SerializedName("observacionCdr")
    var observacionCDR: String? = null

    @Column(name = "ValorSimboloMoneda")
    @SerializedName("valorSimboloMoneda")
    var valorSimboloMoneda: String? = null

    @Column(name = "DescripcionMoneda")
    @SerializedName("descripcionMoneda")
    var descripcionMoneda: String? = null

}
