package biz.belcorp.salesforce.core.entities.calculator

import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.google.gson.annotations.SerializedName
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel

@Table(database = AppDatabase::class, name = "PartnerVariable")
class PartnerVariableEntity : BaseModel() {

    @Column(name = "ID")
    @SerializedName("nivelID")
    @PrimaryKey
    var nivelID: Int = -1

    @Column(name = "IndicadorNuevaLider")
    @SerializedName("indicadorNuevaLider")
    var indicadorNuevaLider: String? = null

    @Column(name = "MetaPedido")
    @SerializedName("metaPedido")
    var metaPedido: Int? = null

    @Column(name = "MetaVenta")
    @SerializedName("metaVenta")
    var metaVenta: Double? = null

    @Column(name = "NumeroActivasIniciales")
    @SerializedName("numeroActivasIniciales")
    var numeroActivasIniciales: Int? = null

    @Column(name = "PorcentajeMetaRecuperacion")
    @SerializedName("porcentajeMetaRecuperacion")
    var porcentajeMetaRecuperacion: Double? = null

    @Column(name = "PromedioVentaPedidosAV")
    @SerializedName("promedioVentaPedidosAV")
    var promedioVentaPedidosAV: Double? = null

    @Column(name = "PromedioVentaPedidosBV")
    @SerializedName("promedioVentaPedidosBV")
    var promedioVentaPedidosBV: Double? = null

    @Column(name = "NivelCambioCampania")
    @SerializedName("nivelCambioCampania")
    var nivelCambioCampania: String? = null

    @Column(name = "MetaIngresos")
    @SerializedName("metaIngresos")
    var metaIngresos: Int? = null

    @Column(name = "MetaCapitalizacion")
    @SerializedName("metaCapitalizacion")
    var metaCapitalizacion: Int? = null

    @Column(name = "IndicadorMedicionVariable")
    @SerializedName("indicadorMedicionVariable")
    var indicadorMedicionVariable: String? = null

    @Column(name = "IndicadorMedicionMeta")
    @SerializedName("indicadorMedicionMeta")
    var indicadorMedicionMeta: String? = null

    @Column(name = "PorcentajeComisionEXAV")
    @SerializedName("porcentajeComisionEXAV")
    var porcentajeComisionEXAV: Double? = null

    @Column(name = "PorcentajeComisionEXBV")
    @SerializedName("porcentajeComisionEXBV")
    var porcentajeComisionEXBV: Double? = null

    @Column(name = "PorcentajeComisionNEAV")
    @SerializedName("porcentajeComisionNEAV")
    var porcentajeComisionNEAV: Double? = null

    @Column(name = "PorcentajeComisionNEBV")
    @SerializedName("porcentajeComisionNEBV")
    var porcentajeComisionNEBV: Double? = null

    @Column(name = "BonoCambioNivel")
    @SerializedName("bonoCambioNivel")
    var bonoCambioNivel: Double? = null

}
