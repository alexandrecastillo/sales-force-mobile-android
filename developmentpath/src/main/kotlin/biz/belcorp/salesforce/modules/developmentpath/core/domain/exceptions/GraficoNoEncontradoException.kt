package biz.belcorp.salesforce.modules.developmentpath.core.domain.exceptions

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA


class GraficoNoEncontradoException(val llaveUA: LlaveUA)
    : Exception("Graficos no encontrados para ${llaveUA.codigoRegion}")
