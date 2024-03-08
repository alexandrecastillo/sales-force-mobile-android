package biz.belcorp.salesforce.modules.postulants.core.data.repository.indicadorunete.di

import biz.belcorp.salesforce.modules.postulants.core.data.repository.indicadorunete.IndicadorDataRepository
import biz.belcorp.salesforce.modules.postulants.core.data.repository.indicadorunete.datasource.IndicadorCloudDataStore
import biz.belcorp.salesforce.modules.postulants.core.data.repository.indicadorunete.datasource.IndicadorDBDataStore
import biz.belcorp.salesforce.modules.postulants.core.data.repository.indicadorunete.mappers.DetalleIndicadorEntityDataMapper
import biz.belcorp.salesforce.modules.postulants.core.data.repository.indicadorunete.mappers.IndicadorEntityDataMapper
import biz.belcorp.salesforce.modules.postulants.core.data.repository.indicadorunete.mappers.SeccionEntityDataMapper
import biz.belcorp.salesforce.modules.postulants.core.domain.repository.IndicadorRepository
import org.koin.dsl.module

internal val indicadorModule = module {
    factory { IndicadorEntityDataMapper() }
    factory { DetalleIndicadorEntityDataMapper() }
    factory { SeccionEntityDataMapper() }
    factory { IndicadorCloudDataStore(get()) }
    factory { IndicadorDBDataStore() }
    factory<IndicadorRepository> {
        IndicadorDataRepository(
            get(), get(), get(),
            get(), get()
        )
    }
}
