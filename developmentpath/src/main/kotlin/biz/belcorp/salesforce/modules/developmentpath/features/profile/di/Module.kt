package biz.belcorp.salesforce.modules.developmentpath.features.profile.di

import biz.belcorp.salesforce.core.utils.listByElementsOf
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.di.acompaniamientoModule
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.di.acuerdosReconocimientosModules
import biz.belcorp.salesforce.modules.developmentpath.features.profile.cobranzaestadodecuenta.estadocuenta.di.datosEstadoCuentaModule
import biz.belcorp.salesforce.modules.developmentpath.features.profile.cobranzaganancia.di.datosCobranzaGanaciaModule
import biz.belcorp.salesforce.modules.developmentpath.features.profile.data.di.profileData
import biz.belcorp.salesforce.modules.developmentpath.features.profile.goals.di.goalsModules
import biz.belcorp.salesforce.modules.developmentpath.features.profile.header.di.cabeceraPerfilModule
import biz.belcorp.salesforce.modules.developmentpath.features.profile.information.di.informationModule
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.di.prepararseEsClaveModules
import biz.belcorp.salesforce.modules.developmentpath.features.profile.puntajepremio.di.puntajePremioModule
import org.koin.core.module.Module

val profileModule by lazy {
    listByElementsOf<Module>(
        cabeceraPerfilModule,
        informationModule,
        datosCobranzaGanaciaModule,
        datosEstadoCuentaModule,
        goalsModules,
        prepararseEsClaveModules,
        acuerdosReconocimientosModules,
        acompaniamientoModule,
        profileData,
        puntajePremioModule
    )
}
