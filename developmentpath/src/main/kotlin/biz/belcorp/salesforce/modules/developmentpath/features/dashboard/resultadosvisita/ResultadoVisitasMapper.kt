package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.resultadosvisita

import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.visitas.RecuperarResultadoVisitasUseCase


class ResultadoVisitasMapper {

    fun parse(response: RecuperarResultadoVisitasUseCase.Response): ResultadoVisitasModel {
        return when (response) {
            is RecuperarResultadoVisitasUseCase.Response.Inactivo -> {
                ResultadoVisitasModel(
                    facturadas = Constant.NUMBER_ZERO.toLong(),
                    noFacturadas = Constant.NUMBER_ZERO.toLong()
                )
            }
            is RecuperarResultadoVisitasUseCase.Response.Activo -> {
                ResultadoVisitasModel(
                    facturadas = response.consultorasQueFacturaron,
                    noFacturadas = response.consultorasQueNoFacturaron
                )
            }
        }
    }
}
