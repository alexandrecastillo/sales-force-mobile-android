package biz.belcorp.salesforce.modules.postulants.core.data.repository.parametrounete

import biz.belcorp.salesforce.core.entities.sql.unete.ParametroUneteEntity
import biz.belcorp.salesforce.core.entities.sql.unete.ParametroUneteEntity_Table
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.ParametroUnete
import biz.belcorp.salesforce.modules.postulants.core.domain.enums.UneteTipoParametro
import biz.belcorp.salesforce.modules.postulants.core.domain.repository.ParametroUneteRepository
import io.reactivex.Single

class ParametroUneteDataRepository
constructor(
    private val dbStore: ParametroUneteDBStore,
    private val mapper: ParametroUneteEntityDataMapper
) : ParametroUneteRepository {

    override suspend fun listSuspend(tipoParametro: Int): List<ParametroUnete> {
        val list = dbStore.listSuspend(tipoParametro)
        return mapper.reverseMap(list)
    }

    override fun list2(
        tipoParametro: Int,
        nombre: String
    ): Single<List<ParametroUnete>> {
        val list = dbStore.listSuspend(tipoParametro, nombre)

        return list.map {
            mapper.reverseMap(it)
        }
    }

    override fun list(tipoParametro: Int, nombre: String): Single<ParametroUnete> {
        return dbStore.list(tipoParametro, nombre).map {
            mapper.reverseMap(it)
        }
    }

    override fun list(tipoParametro: Int): Single<List<ParametroUnete>> {
        val entity: Single<List<ParametroUneteEntity>>
        if (tipoParametro == UneteTipoParametro.TIPODOCUMENTO.tipo)
            entity = dbStore.list(tipoParametro, ParametroUneteEntity_Table.Valor)
        else
            entity = dbStore.list(tipoParametro)

        return entity.map {
            mapper.reverseMap(it)
        }
    }

    override fun listByPadre(parametroUnete: Int): Single<List<ParametroUnete>> {
        return dbStore.listByPadre(parametroUnete).map {
            mapper.reverseMap(it)
        }
    }

    override fun listZonasSMS(tipoParametro: Int, zona: String): Single<List<ParametroUnete>> {
        return dbStore.listZonasSMS(tipoParametro, zona).map {
            mapper.reverseMap(it)
        }
    }

    override fun getParametroUnete(tipoParametro: Int, zona: String): Single<List<ParametroUnete>> {
        return dbStore.getParametroUnete(tipoParametro, zona).map {
            mapper.reverseMap(it)
        }
    }

    override fun getParametroUnete(tipoParametro: Int): Single<List<ParametroUnete>> {
        return dbStore.getParametroUnete(tipoParametro).map {
            mapper.reverseMap(it)
        }
    }
}
