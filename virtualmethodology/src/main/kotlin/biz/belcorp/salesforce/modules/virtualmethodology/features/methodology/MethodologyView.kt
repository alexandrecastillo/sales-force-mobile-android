package biz.belcorp.salesforce.modules.virtualmethodology.features.methodology

import biz.belcorp.salesforce.modules.virtualmethodology.features.methodology.model.GroupsSegmentationModel

interface MethodologyView {
    fun showGroupsSeg(list: List<GroupsSegmentationModel>)
}
