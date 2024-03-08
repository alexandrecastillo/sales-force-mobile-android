package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.cobranza.di

import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.cobranza.cloud.CobranzaYGananciaCloudDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.cobranza.data.CobranzaYGananciaLocalDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.cobranza.data.DatosCobranzaCampaniaAnteriorGerenteZonaRegionDbStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.cobranza.data.DatosCobranzaConsultoraSociaDbStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.cobranza.CobranzaYGananciaDataMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.cobranza.CobranzaYGananciaDataRepository
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.cobranza.DatosCobranzaCampaniaAnteriorDataRepository
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.cobranza.DatosCobranzaDataRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.cobranza.CobranzaYGananciaRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.cobranza.DatosCobranzaCampaniaAnteriorRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.cobranza.DatosCobranzaRepository
import org.koin.dsl.module

internal val cobranzasModule = module {

    factory { DatosCobranzaCampaniaAnteriorGerenteZonaRegionDbStore() }
    factory<DatosCobranzaCampaniaAnteriorRepository> {
        DatosCobranzaCampaniaAnteriorDataRepository(dbStore = get())
    }

    factory { CobranzaYGananciaDataMapper() }
    factory { CobranzaYGananciaLocalDataStore() }
    factory { CobranzaYGananciaCloudDataStore(get(), get()) }
    factory<CobranzaYGananciaRepository> {
        CobranzaYGananciaDataRepository(get(), get(), get())
    }

    factory { DatosCobranzaConsultoraSociaDbStore() }
    factory<DatosCobranzaRepository> {
        DatosCobranzaDataRepository(get())
    }
}
