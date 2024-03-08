package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.campania.di

import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.acuerdo.data.AcuerdoDBDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.acuerdos.AcuerdoEntityDataMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.campania.CampaniaACampaniaDataRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.campania.CampaniaACampaniaRepository
import org.koin.dsl.module

internal val campaniasModule = module {

    factory { AcuerdoDBDataStore() }
    factory { AcuerdoEntityDataMapper() }

    factory<CampaniaACampaniaRepository> {
        CampaniaACampaniaDataRepository(
            habilidadesDBDataStore = get(),
            habilidadesReconoceDBDataStore = get(),
            acuerdoDBDataStore = get(),
            acuerdoEntityDataMapper = get())
    }

}
