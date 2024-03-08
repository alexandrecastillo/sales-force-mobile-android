package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.masvendido.multimarkcategorie

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.prepararseesclavedetail.masvendido.productosmasvendidos.EsikaLbelData
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.salesconsultant.MultiCategories
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.salesconsultant.MultiMark

open class MultiMarkCategoryViewState {
    class Success(val data: Pair<List<MultiMark>, List<MultiCategories>>) : MultiMarkCategoryViewState()

    class SuccessProducts(val data: EsikaLbelData) : MultiMarkCategoryViewState()

    class Failed(val message: String) : MultiMarkCategoryViewState()
}
