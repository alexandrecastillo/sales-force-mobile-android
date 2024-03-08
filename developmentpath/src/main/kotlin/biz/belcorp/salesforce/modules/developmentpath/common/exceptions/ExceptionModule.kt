package biz.belcorp.salesforce.modules.developmentpath.common.exceptions

import org.koin.dsl.module

internal val exceptionModule = module {
    factory { ErrorMessageFactory2(get()) }
}
