package biz.belcorp.salesforce.core.domain.entities.campania

import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.utils.deleteHyphen
import java.util.*

open class Campania(
    var codigo: String,
    val nombreCorto: String,
    val inicio: Date?,
    val fin: Date?,
    val inicioFacturacion: Date?,
    val orden: Int = 0,
    val esPrimerDiaFacturacion: Boolean,
    val periodo: Periodo?
) {

    enum class Periodo {
        VENTA,
        FACTURACION;

        fun inicial(): String {
            return when (this) {
                FACTURACION -> "F"
                VENTA -> "V"
            }
        }

        fun nombre(): String {
            return when (this) {
                FACTURACION -> "Facturación"
                VENTA -> "Venta"
            }
        }
    }

    companion object {

        fun construirDummy(
            codigo: String = "",
            nombreCorto: String = "C-"
        ) = Campania(
            codigo = codigo,
            nombreCorto = nombreCorto,
            inicio = Date(),
            fin = Date(),
            inicioFacturacion = Date(),
            orden = 0,
            esPrimerDiaFacturacion = false,
            periodo = Periodo.FACTURACION
        )

        fun construirPeriodo(inicial: String): Periodo {
            return if (inicial.equals("V", false))
                Periodo.VENTA
            else
                Periodo.FACTURACION
        }
    }

    val esCampaniaActual get() = orden == 1

    val shortNameOnly get() = nombreCorto.deleteHyphen()

    fun obtenerNombreNumerico(): String {
        return this.nombreCorto.takeLast(2)
    }

    fun getPhase(): String {
        return if (CampaignRules.isBilling(this)) Constant.PHASE_BILLING else Constant.PHASE_SALE
    }

}
