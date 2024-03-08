package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.perfil.di

import biz.belcorp.salesforce.core.utils.listByElementsOf
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.perfil.cloud.DatosPerfilCloudDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.perfil.data.DatosPerfilLocalDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.perfil.DatosPerfilDataRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.perfil.DatosPerfilRepository
import org.koin.core.module.Module
import org.koin.dsl.module

internal val datosPerfilModule by lazy {
    listByElementsOf<Module>(
        perfilModule
    )
}

private val perfilModule = module {
    factory { DatosPerfilLocalDataStore(acuerdosStore = get(), anotacionesStore = get()) }
    factory { DatosPerfilCloudDataStore(get(), get()) }
    factory<DatosPerfilRepository> { DatosPerfilDataRepository(database = get(), cloud = get()) }
}
