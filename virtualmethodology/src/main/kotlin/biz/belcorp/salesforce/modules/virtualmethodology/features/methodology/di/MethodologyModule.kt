package biz.belcorp.salesforce.modules.virtualmethodology.features.methodology.di

import biz.belcorp.salesforce.modules.virtualmethodology.features.methodology.MethodologyView
import biz.belcorp.salesforce.modules.virtualmethodology.features.methodology.VirtualMethodologyPresenter
import biz.belcorp.salesforce.modules.virtualmethodology.features.methodology.mappers.GroupSegMapper
import biz.belcorp.salesforce.modules.virtualmethodology.features.methodology.mappers.SegmentationMapper
import org.koin.dsl.module

val methodologyModule = module {

    factory { GroupSegMapper() }
    factory { SegmentationMapper() }

    factory { (view: MethodologyView) ->
        VirtualMethodologyPresenter(view, get(), get())
    }
}
