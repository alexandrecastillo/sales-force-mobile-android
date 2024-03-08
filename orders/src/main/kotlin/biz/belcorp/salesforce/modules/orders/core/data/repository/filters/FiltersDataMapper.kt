package biz.belcorp.salesforce.modules.orders.core.data.repository.filters

import biz.belcorp.salesforce.core.domain.entities.search.TipoEstado
import biz.belcorp.salesforce.core.domain.entities.search.TipoSegmento
import biz.belcorp.salesforce.core.domain.entities.ua.Seccion
import biz.belcorp.salesforce.core.entities.sql.filtros.TipoEstadoEntity
import biz.belcorp.salesforce.core.entities.sql.filtros.TipoSaldoEntity
import biz.belcorp.salesforce.core.entities.sql.filtros.TipoSegmentoEntity
import biz.belcorp.salesforce.core.entities.sql.ua.SeccionEntity
import biz.belcorp.salesforce.modules.orders.core.domain.entities.filters.TipoSaldo


class FiltersDataMapper {

    private fun map(entity: TipoEstadoEntity): TipoEstado {
        return TipoEstado().apply {
            idEstadoActividad = entity.idEstadoActividad
            descripcion = entity.descripcion
            estadoActivo = entity.estadoActivo
        }
    }

    fun mapEstadoList(list: List<TipoEstadoEntity>): List<TipoEstado> {
        return list.asSequence().map { map(it) }.toList()
    }

    private fun map(entity: TipoSaldoEntity): TipoSaldo {
        return TipoSaldo().apply {
            idSaldo = entity.idSaldo
            descripcion = entity.descripcion
        }
    }

    fun mapSaldoList(list: List<TipoSaldoEntity>): List<TipoSaldo> {
        return list.asSequence().map { map(it) }.toList()
    }

    private fun map(entity: TipoSegmentoEntity): TipoSegmento {
        return TipoSegmento().apply {
            segmentoInternoId = entity.segmentoInternoId
            descripcion = entity.descripcion
            abreviatura = entity.abreviatura
            estadoActivo = entity.estadoActivo
        }
    }

    fun mapSegmentoList(list: List<TipoSegmentoEntity>): List<TipoSegmento> {
        return list.asSequence().map { map(it) }.toList()
    }

    private fun map(entity: SeccionEntity): Seccion {
        return Seccion().apply {
            codigo = entity.codigo
        }
    }

    fun mapSeccionList(list: List<SeccionEntity>): List<Seccion> {
        return list.asSequence().map { map(it) }.toList()
    }

}
