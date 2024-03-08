package biz.belcorp.salesforce.core.entities.calculator

import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.google.gson.annotations.SerializedName
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel

@Table(database = AppDatabase::class, name = "LevelParameter")
class LevelParameterEntity : BaseModel() {

    @Column(name = "ID")
    @SerializedName("nivelID")
    @PrimaryKey
    var nivelID: Int = -1

    @Column(name = "GananciaPromedioPedido")
    @SerializedName("gananciaPromedioPedido")
    var gananciaPromedioPedido: Float? = null

    @Column(name = "PedidoMinimoNivel")
    @SerializedName("pedidoMinimoNivel")
    var pedidoMinimoNivel: Int? = null

    @Column(name = "VentaMinimaProximoNivel")
    @SerializedName("ventaMinimaProximoNivel")
    var ventaMinimaProximoNivel: Float? = null

    @Column(name = "MetaIngresos")
    @SerializedName("metaIngresos")
    var metaIngresos: Int? = null

    @Column(name = "MetaCapitalizacion")
    @SerializedName("metaCapitalizacion")
    var metaCapitalizacion: Int? = null

    @Column(name = "PorcentajeRetencionActivas")
    @SerializedName("porcentajeRetencionActivas")
    var porcentajeRetencionActivas: Float? = null

    @Column(name = "MetaPedidos")
    @SerializedName("metaPedidos")
    var metaPedidos: Int? = null

    @Column(name = "MetaVenta")
    @SerializedName("metaVenta")
    var metaVenta: Float? = null

    @Column(name = "IndicadorMedicionMeta")
    @SerializedName("indicadorMedicionMeta")
    var indicadorMedicionMeta: String? = null

    @Column(name = "IndicadorMedicionVariable")
    @SerializedName("indicadorMedicionVariable")
    var indicadorMedicionVariable: String? = null

    @Column(name = "IndicadorMedicionRetencion")
    @SerializedName("indicadorMedicionRetencion")
    var indicadorMedicionRetencion: String? = null

    @Column(name = "BonoCambioNivel")
    @SerializedName("bonoCambioNivel")
    var bonoCambioNivel: Float? = null

    @Column(name = "PorcentajeComisionEXAV")
    @SerializedName("porcentajeComisionEXAV")
    var porcentajeComisionEXAV: Float? = null

    @Column(name = "PorcentajeComisionEXBV")
    @SerializedName("porcentajeComisionEXBV")
    var porcentajeComisionEXBV: Float? = null

    @Column(name = "PorcentajeComisionNEAV")
    @SerializedName("porcentajeComisionNEAV")
    var porcentajeComisionNEAV: Float? = null

    @Column(name = "PorcentajeComisionNEBV")
    @SerializedName("porcentajeComisionNEBV")
    var porcentajeComisionNEBV: Float? = null

}
