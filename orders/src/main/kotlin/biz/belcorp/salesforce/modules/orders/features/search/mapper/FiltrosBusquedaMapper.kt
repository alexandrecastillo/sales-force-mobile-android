package biz.belcorp.salesforce.modules.orders.features.search.mapper


import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.domain.entities.search.TipoEstado
import biz.belcorp.salesforce.core.domain.entities.search.TipoSegmento
import biz.belcorp.salesforce.core.domain.entities.ua.Seccion
import biz.belcorp.salesforce.core.features.search.models.TipoEstadoModel
import biz.belcorp.salesforce.core.features.search.models.TipoSaldoModel
import biz.belcorp.salesforce.core.features.search.models.TipoSegmentoModel
import biz.belcorp.salesforce.modules.orders.core.domain.entities.filters.FiltrosBusqueda
import biz.belcorp.salesforce.modules.orders.core.domain.entities.filters.TipoSaldo
import biz.belcorp.salesforce.modules.orders.features.search.model.CampaniaModel
import biz.belcorp.salesforce.modules.orders.features.search.model.FiltrosBusquedaModel
import biz.belcorp.salesforce.modules.orders.features.search.model.SeccionModel

class FiltrosBusquedaMapper {

    fun map(filtros: FiltrosBusqueda): FiltrosBusquedaModel {
        return FiltrosBusquedaModel(
            tipoEstadoList = mapEstadoList(filtros.tipoEstadoList),
            tipoSaldoList = mapSaldoList(filtros.tipoSaldoList),
            tipoSegmentoList = mapSegmentoList(filtros.tipoSegmentoList),
            seccionesList = mapSeccionList(filtros.seccionesList),
            campaniasList = mapCampaniasList(filtros.campaniasList)
        )
    }

    private fun mapEstadoList(list: List<TipoEstado>): List<TipoEstadoModel> {
        return list.map { item ->
            TipoEstadoModel().apply {
                idEstadoActividad = item.idEstadoActividad
                descripcion = item.descripcion
                estadoActivo = item.estadoActivo
            }
        }
    }

    private fun mapSaldoList(list: List<TipoSaldo>): List<TipoSaldoModel> {
        return list.map { item ->
            TipoSaldoModel().apply {
                idSaldo = item.idSaldo
                descripcion = item.descripcion
            }
        }
    }

    private fun mapSegmentoList(list: List<TipoSegmento>): List<TipoSegmentoModel> {
        return list.map { item ->
            TipoSegmentoModel().apply {
                segmentoInternoId = item.segmentoInternoId
                descripcion = item.descripcion
                abreviatura = item.abreviatura
                estadoActivo = item.estadoActivo
            }
        }
    }

    private fun mapSeccionList(list: List<Seccion>): List<SeccionModel> {
        return list.map { item ->
            SeccionModel().apply {
                codigo = item.codigo
            }
        }
    }

    private fun mapCampaniasList(list: List<Campania>): List<CampaniaModel> {
        return list.map { item ->
            CampaniaModel().apply {
                codigo = item.codigo
                nombreCorto = item.nombreCorto
            }
        }
    }


}
