package biz.belcorp.salesforce.modules.brightpath.core.data.mapper

import biz.belcorp.salesforce.core.entities.sql.consultora.IndicadorCambioNivelEntity
import biz.belcorp.salesforce.modules.brightpath.core.domain.entities.LevelIndicator


fun IndicadorCambioNivelEntity.mapToLevelIndicator() = LevelIndicator(
        campaniaAnterior = campaniaAnterior,
        acumuladoPorNivel = acumuladoPorNivel,
        periodoActual = periodoActual,
        totalCampaniaAnterior = totalCampaniaAnterior,
        acumuladoPeriodo = acumuladoPeriodo
)
