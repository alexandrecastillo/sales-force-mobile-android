package biz.belcorp.salesforce.modules.developmentpath.utils

import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ONE
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_TWO
import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.modules.developmentpath.core.domain.utils.Constants.EMPTY_STRING
import biz.belcorp.salesforce.modules.developmentpath.core.domain.utils.Constants.PREFIJO_CAMPANIA

fun String.prefijoPeriodoConNumeroCampania(periodo: Campania.Periodo) =
    "${periodo.nombre().toUpperCase()} $this"

fun String.prefijoConNumeroCampania() =
    if (this.length >= 4) "$PREFIJO_CAMPANIA${this.removeRange(0, 4)}" else this

fun String.numeroCampania() = if (this.length >= 4) this.removeRange(0, 4) else this

fun Campania.numeroCampania() = this.codigo.numeroCampania()

fun String.maskCampaignWithPrefix() =
    if (this != EMPTY_STRING) PREFIJO_CAMPANIA + maskCampaign() else EMPTY_STRING

fun String.maskCampaign() =
    if (this.length > NUMBER_ONE) this.substring(this.length - NUMBER_TWO)
    else this

fun String.obtenerAnioCampania() =
    if (this.length >= 4) this.substring(0, 4).toIntOrNull()
    else null
