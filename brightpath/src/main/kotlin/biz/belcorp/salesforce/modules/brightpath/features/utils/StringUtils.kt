package biz.belcorp.salesforce.modules.brightpath.features.utils

import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import java.util.*

fun String.doPrefixWithShortCampaignName(period: Campania.Periodo) =
    "${period.nombre().toUpperCase(Locale.getDefault())} $this"
