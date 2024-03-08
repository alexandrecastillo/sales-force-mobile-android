package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.graficos.gerenteregion.topbottom

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.graficos.TopBottom
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.graficos.TopBottomHistorico
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.informacionhistorica.graficobarras.view.ProveedorFormato

class TopBottomMapper {

    private fun parse(topBottom: TopBottom, historico: TopBottomHistorico): TopBottomModel {
        val valorConSimbolos = enmascararValor(topBottom.valor, historico)
        return TopBottomModel(
            zona = "GZ ${topBottom.zona}:",
            valor = valorConSimbolos,
            porcentaje = "(${topBottom.porcentaje}%)",
            color = obtenerColor(checkNotNull(topBottom.tipo))
        )
    }

    private fun enmascararValor(valor: Double?, historico: TopBottomHistorico): String {
        val formatter = ProveedorFormato()
        val valorNoNulo = (valor ?: 0.toDouble()).toFloat()
        val valorFormateado = formatter.obtenerFormato(historico.tipoGrafico.codigo, valorNoNulo)
        return "${historico.simboloAIzquierda ?: ""}$valorFormateado${historico.simboloADerecha
            ?: ""}"
    }

    private fun obtenerColor(tipo: String): TopBottomModel.TopBottomColor {
        return if (tipo == TopBottomHistorico.TipoTopBottom.TOP.abreviatura)
            TopBottomModel.TopBottomColor.VERDE else TopBottomModel.TopBottomColor.ROJO
    }

    fun mapTop(historico: TopBottomHistorico): List<TopBottomModel> {
        return historico.top.map { parse(it, historico) }
    }

    fun mapBottom(historico: TopBottomHistorico): List<TopBottomModel> {
        return historico.bottom.map { parse(it, historico) }
    }
}
