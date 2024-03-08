package biz.belcorp.salesforce.modules.postulants.core.data.repository.tablalogica.di

import biz.belcorp.salesforce.core.entities.sql.unete.TablaLogicaEntity
import biz.belcorp.salesforce.modules.postulants.core.data.repository.tablalogica.TablaLogicaDBStore
import biz.belcorp.salesforce.modules.postulants.core.data.repository.tablalogica.TablaLogicaDataRepository
import biz.belcorp.salesforce.modules.postulants.core.data.repository.tablalogica.TablaLogicaEntityDataMapper
import biz.belcorp.salesforce.modules.postulants.core.domain.repository.TablaLogicaRepository
import org.koin.dsl.module

internal val tablaLogicaModule = module {

    factory { TablaLogicaEntityDataMapper() }

    factory { TablaLogicaEntity() }

    factory { TablaLogicaDBStore() }

    factory { TablaLogicaDataRepository(get(), get()) as TablaLogicaRepository }
}
