package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.graficos

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.graficos.TipoGrafico
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.graficos.TipoGraficoInferior

class DefinirGraficoInferior {

    fun definir(tipoGrafico: TipoGrafico): TipoGraficoInferior {
        return when (tipoGrafico) {
            TipoGrafico.PRODUCTIVAS -> TipoGraficoInferior.CuadroProductivas
            else -> TipoGraficoInferior.TopBottom
        }
    }
}
