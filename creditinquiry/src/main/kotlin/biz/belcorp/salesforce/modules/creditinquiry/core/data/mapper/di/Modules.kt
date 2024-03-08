package biz.belcorp.salesforce.modules.creditinquiry.core.data.mapper.di

import biz.belcorp.salesforce.modules.creditinquiry.core.data.mapper.*
import org.koin.dsl.module

var mapperModules = module {

    factory { BloqueoEntityDataMapper() }
    factory { ConsultaCrediticiaEntityDataMapper() }
    factory { DeudaExternaEntityDataMapper() }
    factory { ExplicacionEntityDataMapper() }
    factory { ValidacionCrediticiaEntityDataMapper() }
    factory { ValoracionEntityDataMapper() }

}
