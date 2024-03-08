package biz.belcorp.salesforce.core.entities.sql.datos


import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.google.gson.annotations.SerializedName
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel

@Table(database = AppDatabase::class, name = "CobranzaYGanancia")
class CobranzaYGananciaEntity : BaseModel() {

    @Column(name = "Campania")
    @SerializedName("campania")
    @PrimaryKey
    var campania: String? = ""

    @Column(name = "Region")
    @SerializedName("region")
    @PrimaryKey
    var region: String? = ""

    @Column(name = "Zona")
    @SerializedName("zona")
    @PrimaryKey
    var zona: String? = ""

    @Column(name = "Seccion")
    @SerializedName("seccion")
    @PrimaryKey
    var seccion: String? = ""

    @Column(name = "MontoFacturadoNeto")
    @SerializedName("montoFacturadoNeto")
    var montoFacturadoNeto: Float? = 0f

    @Column(name = "MontoRecuperado")
    @SerializedName("montoRecuperado")
    var montoRecuperado: Float? = 0f

    @Column(name = "SaldoDeuda")
    @SerializedName("saldoDeuda")
    var saldoDeuda: Float? = 0f

    @Column(name = "ConsultorasDeuda")
    @SerializedName("consultorasDeuda")
    var consultorasDeuda: Float? = 0f

    @Column(name = "Recuperacion")
    @SerializedName("recuperacion")
    var recuperacion: Int? = 0

    @Column(name = "RetencionVSC")
    @SerializedName("retencionVSC")
    var retencionVSC: Int? = 0

    @Column(name = "GananciaTotal")
    @SerializedName("gananciaTotal")
    var gananciaTotal: Float? = 0f

    @Column(name = "Ganancia6d6")
    @SerializedName("ganancia6d6")
    var ganancia6d6: Float? = 0f

    @Column(name = "GananciaPedidoAltoValor")
    @SerializedName("gananciaPedidoAltoValor")
    var gananciaPedidoAltoValor: Float? = 0f

    @Column(name = "GananciaCambioNivel")
    @SerializedName("gananciaCambioNivel")
    var gananciaCambioNivel: Float? = 0f

}
