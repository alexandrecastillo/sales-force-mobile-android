package biz.belcorp.salesforce.modules.developmentpath.features.profile.data.desempenio.last6campaigns

import biz.belcorp.salesforce.modules.developmentpath.features.profile.data.desempenio.DesempenioModel

interface DesempenioGrGzView {
    fun pintar(desempenios: List<DesempenioModel>)
}
