package biz.belcorp.salesforce.modules.inspires.util

import biz.belcorp.salesforce.core.domain.entities.pais.Pais

fun Pais?.isPeru() = this?.codigoIso.equals(Pais.PERU.codigoIso, ignoreCase = true)
