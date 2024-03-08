package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.eventos

import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.eventos.data.TiposEventoRddDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.eventos.TiposEventoRddMapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.eventos.TipoEventoRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.eventos.TiposEventoRddRepository

class TiposEventoRddDataRepository(private val dbStore: TiposEventoRddDataStore,
                                   private val mapper: TiposEventoRddMapper
)
    : TiposEventoRddRepository {

    override fun recuperar(rol: Rol): List<TipoEventoRdd> {
        return dbStore.recuperar(rol).map { mapper.map(it) }
    }

    override fun recuperar(): List<TipoEventoRdd> {
        return dbStore.recuperar().map { mapper.map(it) }
    }
}
