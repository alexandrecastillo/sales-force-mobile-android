package biz.belcorp.salesforce.modules.developmentmaterial.core.data.network.di

import biz.belcorp.salesforce.core.data.network.di.NAMED_MOBILE
import biz.belcorp.salesforce.modules.developmentmaterial.core.data.network.DocumentsApi
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit


val networkModule = module {

    single { provideDocumentsApi(get(named(NAMED_MOBILE))) }

}

private fun provideDocumentsApi(retrofit: Retrofit): DocumentsApi {
    return retrofit.create(DocumentsApi::class.java)
}
