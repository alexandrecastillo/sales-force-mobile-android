package biz.belcorp.salesforce.modules.postulants.core.data.repository.postulante.di

import biz.belcorp.salesforce.modules.postulants.core.data.repository.postulante.PostulantesDataRepository
import biz.belcorp.salesforce.modules.postulants.core.data.repository.postulante.datasource.PostulantesCloudDataStore
import biz.belcorp.salesforce.modules.postulants.core.data.repository.postulante.datasource.PostulantesDBDataStore
import biz.belcorp.salesforce.modules.postulants.core.data.repository.postulante.mappers.*
import biz.belcorp.salesforce.modules.postulants.core.domain.repository.PostulantesRepository
import biz.belcorp.salesforce.modules.postulants.utils.PostulanteUtil
import org.koin.dsl.module

internal val postulantModule = module {

    factory { BuroResponseEntityDataMapper() }
    factory { GeoResponseEntityDataMapper() }
    factory { PostulanteAptaDataMapper() }
    factory { PostulanteEntityDataMapper() }
    factory { PrePostulanteEntityDataMapper() }
    factory { ValidacionBuroResponseEntitiyDataMapper() }
    factory { ValidarPinEntityDataMapper() }
    factory { EvaluacionBuroDataMapper(get()) }

    factory { PostulanteUtil(get()) }

    factory { PostulantesCloudDataStore(get(), get(), get(), get(), get(), get()) }
    factory { PostulantesDBDataStore() }

    factory {
        PostulantesDataRepository(
            get(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get()
        ) as PostulantesRepository
    }
}
