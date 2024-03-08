package biz.belcorp.salesforce.modules.calculator.core.data.repository.di

import biz.belcorp.salesforce.modules.calculator.core.data.repository.calculatingresult.CalculatingResultDataRepository
import biz.belcorp.salesforce.modules.calculator.core.data.repository.calculatingresult.cloud.CalculatingResultCloudDataStore
import biz.belcorp.salesforce.modules.calculator.core.data.repository.calculatingresult.data.CalculatingResultDBDataStore
import biz.belcorp.salesforce.modules.calculator.core.data.repository.calculatingresult.mapper.CalculatingResultEntityDataMapper
import biz.belcorp.salesforce.modules.calculator.core.data.repository.contestdetail.ContestDetailDataRepository
import biz.belcorp.salesforce.modules.calculator.core.data.repository.contestdetail.data.ContestDetailDBDataStore
import biz.belcorp.salesforce.modules.calculator.core.data.repository.contestdetail.mapper.ContestDetailEntityDataMapper
import biz.belcorp.salesforce.modules.calculator.core.data.repository.fixedamount.CalculadoraMontoFijoDataRepository
import biz.belcorp.salesforce.modules.calculator.core.data.repository.fixedamount.data.CalculadoraMontoFijoDBDataStore
import biz.belcorp.salesforce.modules.calculator.core.data.repository.levelparameter.LevelParameterDataRepository
import biz.belcorp.salesforce.modules.calculator.core.data.repository.levelparameter.data.LevelParameterDBDataStore
import biz.belcorp.salesforce.modules.calculator.core.data.repository.levelparameter.mapper.LevelParameterEntityDataMapper
import biz.belcorp.salesforce.modules.calculator.core.data.repository.partnervariable.PartnerVariableDataRepository
import biz.belcorp.salesforce.modules.calculator.core.data.repository.partnervariable.data.PartnerVariableDBDataStore
import biz.belcorp.salesforce.modules.calculator.core.data.repository.partnervariable.mapper.PartnerVariableEntityDataMapper
import biz.belcorp.salesforce.modules.calculator.core.data.repository.paymentdetail.PaymentDetailDataRepository
import biz.belcorp.salesforce.modules.calculator.core.data.repository.paymentdetail.data.PaymentDetailDBDataStore
import biz.belcorp.salesforce.modules.calculator.core.data.repository.paymentdetail.mapper.PaymentDetailEntityDataMapper
import biz.belcorp.salesforce.modules.calculator.core.data.repository.upperlevel.UpperLevelDataRepository
import biz.belcorp.salesforce.modules.calculator.core.data.repository.upperlevel.data.UpperLevelDBDataStore
import biz.belcorp.salesforce.modules.calculator.core.data.repository.upperlevel.mapper.UpperLevelEntityDataMapper
import biz.belcorp.salesforce.modules.calculator.core.domain.repository.calculatingresult.CalculatingResultRepository
import biz.belcorp.salesforce.modules.calculator.core.domain.repository.contestdetail.ContestDetailRepository
import biz.belcorp.salesforce.modules.calculator.core.domain.repository.fixedamount.CalculadoraMontoFijoRepository
import biz.belcorp.salesforce.modules.calculator.core.domain.repository.levelparameter.LevelParameterRepository
import biz.belcorp.salesforce.modules.calculator.core.domain.repository.partnervariable.PartnerVariableRepository
import biz.belcorp.salesforce.modules.calculator.core.domain.repository.paymentdetail.PaymentDetailRepository
import biz.belcorp.salesforce.modules.calculator.core.domain.repository.upperlevel.UpperLevelRepository
import org.koin.dsl.module

val calculatorModule = module {

    factory { CalculatingResultEntityDataMapper() }
    factory { ContestDetailEntityDataMapper() }
    factory { LevelParameterEntityDataMapper() }
    factory { PartnerVariableEntityDataMapper() }
    factory { PaymentDetailEntityDataMapper() }
    factory { UpperLevelEntityDataMapper() }

    factory { CalculatingResultDBDataStore() }
    factory { ContestDetailDBDataStore() }
    factory { CalculadoraMontoFijoDBDataStore() }
    factory { LevelParameterDBDataStore() }
    factory { PartnerVariableDBDataStore() }
    factory { PaymentDetailDBDataStore() }
    factory { UpperLevelDBDataStore() }
    factory { CalculatingResultCloudDataStore(get(), get()) }

    factory<CalculatingResultRepository> { CalculatingResultDataRepository(get(), get(), get(),get()) }
    factory<ContestDetailRepository> { ContestDetailDataRepository(get(), get()) }
    factory<LevelParameterRepository> { LevelParameterDataRepository(get(), get()) }
    factory<PartnerVariableRepository> { PartnerVariableDataRepository(get(), get()) }
    factory<PaymentDetailRepository> { PaymentDetailDataRepository(get(), get()) }
    factory<UpperLevelRepository> { UpperLevelDataRepository(get(), get()) }
    factory<CalculadoraMontoFijoRepository> { CalculadoraMontoFijoDataRepository(get(), get()) }
}
