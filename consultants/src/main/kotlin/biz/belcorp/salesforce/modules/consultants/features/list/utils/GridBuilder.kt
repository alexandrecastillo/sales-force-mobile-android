package biz.belcorp.salesforce.modules.consultants.features.list.utils

import android.content.Context
import biz.belcorp.salesforce.modules.consultants.R
import biz.belcorp.salesforce.modules.consultants.core.domain.constants.Constants
import biz.belcorp.salesforce.modules.consultants.features.list.models.ConsultantDetailListSetUpModel

class GridBuilder(private val context: Context) {

    var isAvailableBillingPeriod = false
    var isBillingPeriod = false

    private val pedidosAltoValorTitle by lazy { context.getString(R.string.label_pedidos_alto_valor) }
    private val pedidosBajoValorTitle by lazy { context.getString(R.string.label_pedidos_bajo_valor) }
    private val pedidosRechazadosTitle by lazy { context.getString(R.string.label_pedidos_rechazados) }
    private val posiblesIngresosTitle by lazy { context.getString(R.string.label_posibles_ingresos) }
    private val posiblesReIngresosTitle by lazy { context.getString(R.string.label_posibles_reingresos) }
    private val activasTitle by lazy { context.getString(R.string.label_activas) }
    private val ingresosTitle by lazy { context.getString(R.string.label_ingresos) }
    private val reIngresosTitle by lazy { context.getString(R.string.label_reingresos) }
    private val egresosTitle by lazy { context.getString(R.string.label_egresos) }
    private val gananciaSaldoTitle by lazy { context.getString(R.string.label_ganancia_y_saldo) }
    private val s6d6Title by lazy { context.getString(R.string.label_6d6) }
    private val s5d5Title by lazy { context.getString(R.string.label_5d5) }
    private val s4d4Title by lazy { context.getString(R.string.label_4d4) }
    private val s3d3Title by lazy { context.getString(R.string.label_3d3) }
    private val s2d2Title by lazy { context.getString(R.string.label_2d2) }
    private val pegTitle by lazy { context.getString(R.string.label_peg) }
    private val cambioDeNivel by lazy { context.getString(R.string.label_cambio_de_nivel) }
    private val posibleCambioDeNivel by lazy { context.getString(R.string.label_posibles_cambios_de_nivel) }

    private val pedidosAltoValorDesc by lazy { context.getString(R.string.label_desc_pedidos_alto_valor) }
    private val pedidosBajoValorDesc by lazy { context.getString(R.string.label_desc_pedidos_bajo_valor) }
    private val pedidosRechazadosDesc by lazy { context.getString(R.string.label_desc_pedidos_rechazados) }
    private val posiblesIngresosDesc by lazy { context.getString(R.string.label_desc_posibles_ingresos) }
    private val posiblesReIngresosDesc by lazy { context.getString(R.string.label_desc_posibles_reingresos) }
    private val activasDesc by lazy { context.getString(R.string.label_desc_activas) }
    private val ingresosVtaDesc by lazy { context.getString(R.string.label_desc_ingresos_venta) }
    private val reingresosVtaDesc by lazy { context.getString(R.string.label_desc_reingresos_venta) }
    private val egresosVtaDesc by lazy { context.getString(R.string.label_desc_egresos_venta) }
    private val ingresosFacDesc by lazy { context.getString(R.string.label_desc_ingresos_facturacion) }
    private val reingresosFacDesc by lazy { context.getString(R.string.label_desc_reingresos_facturacion) }
    private val egresosFacDesc by lazy { context.getString(R.string.label_desc_egresos_facturacion) }
    private val gananciaSaldocDesc by lazy { context.getString(R.string.label_desc_saldos) }
    private val s6d6Desc by lazy { context.getString(R.string.label_desc_6d6) }
    private val s5d5Desc by lazy { context.getString(R.string.label_desc_5d5) }
    private val s4d4Desc by lazy { context.getString(R.string.label_desc_4d4) }
    private val s3d3Desc by lazy { context.getString(R.string.label_desc_3d3) }
    private val s2d2Desc by lazy { context.getString(R.string.label_desc_2d2) }
    private val pegVtaDesc by lazy { context.getString(R.string.label_desc_peg_venta) }
    private val pegFacDesc by lazy { context.getString(R.string.label_desc_peg_facturacion) }
    private val posibleCambioDeNivelDesc by lazy { context.getString(R.string.text_posibles_changes_level_consultant_inf) }

    private val pedidosAltoValorGridDetailId = 30
    private val pedidosBajoValorGridDetailId = 31
    private val posiblesIngresosGridDetailId = 3
    private val posiblesReIngresosGridDetailId = 4
    private val pedidosRechazadosGridDetailId = 5
    private val activasGridDetailId = 7
    private val ingresosGridDetailId = 8
    private val reIngresosGridDetailId = 9
    private val egresosGridDetailId = 10
    private val gananciaSaldoGridDetailId = 11
    private val s6d6GridDetailId = 17
    private val s5d5GridDetailId = 18
    private val s4d4GridDetailId = 19
    private val s3d3GridDetailId = 20
    private val s2d2GridDetailId = 21
    private val pegGridDetailId = 22

    val posibleCambiosDeNivelId = Constants.ID_LIST_POSIBLE_CAMBIO_NIVEL
    val cambiosDeNivelId = Constants.ID_LIST_CAMBIO_NIVEL
    val finPeriodolId = Constants.ID_LIST_FIN_PERIODO

    fun build(): List<ConsultantDetailListSetUpModel> {
        return getCambioNivelGrids()
    }

    private fun getCambioNivelGrids(): List<ConsultantDetailListSetUpModel> {
        val list = mutableListOf<ConsultantDetailListSetUpModel>()
        list.add(doPosiblesCambioDeNivel())
        list.add(doCambioDeNivel())
        return list

    }

    private fun doPosiblesCambioDeNivel() = ConsultantDetailListSetUpModel(
        id = posibleCambiosDeNivelId,
        title = posibleCambioDeNivel,
        description = posibleCambioDeNivelDesc
    )

    private fun doCambioDeNivel() = ConsultantDetailListSetUpModel(
        id = cambiosDeNivelId,
        title = cambioDeNivel,
        description = ""
    )

}
