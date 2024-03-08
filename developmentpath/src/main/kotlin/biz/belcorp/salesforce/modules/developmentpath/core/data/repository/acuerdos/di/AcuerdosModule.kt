package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.acuerdos.di

import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.acuerdo.cloud.AcuerdosCloudDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.acuerdo.data.AcuerdosLocalDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.acuerdo.data.CumplimientoAcuerdosLocalDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.acuerdos.AcuerdosDataRepository
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.acuerdos.CumplimientoAcuerdosDataRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.acuerdos.AcuerdosRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.acuerdos.CumplimientoAcuerdosRepository
import org.koin.dsl.module

internal val acuerdosModule = module {
    factory { AcuerdosLocalDataStore() }
    factory { AcuerdosCloudDataStore(get(), get()) }
    factory<AcuerdosRepository> { AcuerdosDataRepository(get(), get()) }

    factory { CumplimientoAcuerdosLocalDataStore() }
    factory<CumplimientoAcuerdosRepository> { CumplimientoAcuerdosDataRepository(get()) }
}
