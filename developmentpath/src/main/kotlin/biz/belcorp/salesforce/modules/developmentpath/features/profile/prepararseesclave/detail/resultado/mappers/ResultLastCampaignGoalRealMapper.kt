package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.resultado.mappers

import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.resultado.ResultCampaign
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.resultado.validator.ResultDataTypeValidator
import biz.belcorp.salesforce.modules.developmentpath.widgets.etiqueta.model.ContenedorInfoBasica
import biz.belcorp.salesforce.modules.developmentpath.widgets.etiqueta.model.Etiqueta
import biz.belcorp.salesforce.modules.developmentpath.widgets.etiqueta.model.Fila
import biz.belcorp.salesforce.modules.developmentpath.widgets.etiqueta.model.Grupo

class ResultLastCampaignGoalRealMapper {

    private val validate = ResultDataTypeValidator()

    fun map(result: ResultCampaign?): ContenedorInfoBasica {
        val catalogSaleRow = createCatalogSale(result)
        val ordersRow = createOrders(result)
        val capitalizationRow = createCapitalization(result)
        return ContenedorInfoBasica(listOf(catalogSaleRow, ordersRow, capitalizationRow))
    }

    private fun createCatalogSale(result: ResultCampaign?): Fila {
        val catalogSaleGoal = createGroupCatalogSaleGoal(result?.catalogSaleGoal)
        val catalogSaleReal = createGroupCatalogSaleReal(result?.catalogSale)
        return Fila(listOf(catalogSaleGoal, catalogSaleReal))
    }

    private fun createOrders(result: ResultCampaign?): Fila {
        val ordersGoal = createGroupOrders(result?.ordersGoal)
        val ordersReal = createGroupOrders(result?.orders)
        return Fila(listOf(ordersGoal, ordersReal))
    }

    private fun createCapitalization(result: ResultCampaign?): Fila {
        val capitalizationGoal = createGroupCapitalization(result?.capitalizationGoal)
        val capitalizationReal = createGroupCapitalization(result?.capitalization)
        return Fila(listOf(capitalizationGoal, capitalizationReal))
    }

    private fun createGroupCatalogSaleGoal(amount: Double?): Grupo {
        val title = Etiqueta(
            tipo = Etiqueta.Tipo.LIGERO,
            idRecurso = R.string.rst_venta_catalogo
        )
        val valor = Etiqueta(
            tipo = Etiqueta.Tipo.FUERTE,
            valor = validate.valueDouble(amount)
        )
        return Grupo(titulo = title, subtitulo = valor)
    }

    private fun createGroupCatalogSaleReal(amount: Double?): Grupo {
        val title = Etiqueta(
            tipo = Etiqueta.Tipo.LIGERO,
            idRecurso = R.string.rst_venta_catalogo
        )
        val value = Etiqueta(
            tipo = Etiqueta.Tipo.FUERTE,
            valor = validate.valueDouble(amount)
        )
        return Grupo(titulo = title, subtitulo = value)
    }

    private fun createGroupOrders(amount: Int?): Grupo {
        val title = Etiqueta(
            tipo = Etiqueta.Tipo.LIGERO,
            idRecurso = R.string.rst_pedidos
        )
        val value = Etiqueta(
            tipo = Etiqueta.Tipo.FUERTE,
            valor = validate.valueInt(amount)
        )
        return Grupo(titulo = title, subtitulo = value)
    }

    private fun createGroupCapitalization(amount: Int?): Grupo {
        val title = Etiqueta(
            tipo = Etiqueta.Tipo.LIGERO,
            idRecurso = R.string.rst_capitalizacion
        )
        val value = Etiqueta(
            tipo = Etiqueta.Tipo.FUERTE,
            valor = validate.valueInt(amount)
        )
        return Grupo(titulo = title, subtitulo = value)
    }

}
