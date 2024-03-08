package biz.belcorp.salesforce.modules.creditinquiry.core.data.network.di

import biz.belcorp.salesforce.core.data.network.di.NAMED_SOMOS_BELCORP
import biz.belcorp.salesforce.core.data.network.di.NAMED_MOBILE
import biz.belcorp.salesforce.modules.creditinquiry.core.data.network.ConsultaCrediticiaRestApi
import biz.belcorp.salesforce.modules.creditinquiry.core.data.network.ValidacionCrediticiaRestApi
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit


val netWorkModule = module {

    single { provideCreditValidationApi(get(named(NAMED_SOMOS_BELCORP))) }
    single { provideCreditInquiryApi(get(named(NAMED_MOBILE))) }

}

private fun provideCreditInquiryApi(retrofit: Retrofit): ConsultaCrediticiaRestApi {
    return retrofit.create(ConsultaCrediticiaRestApi::class.java)
}

private fun provideCreditValidationApi(retrofit: Retrofit): ValidacionCrediticiaRestApi {
    return retrofit.create(ValidacionCrediticiaRestApi::class.java)
}
