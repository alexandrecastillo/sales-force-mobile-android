package biz.belcorp.salesforce.modules.developmentpath.utils

import biz.belcorp.salesforce.core.utils.isNotNull
import biz.belcorp.salesforce.modules.developmentpath.utils.Constant.NUMBER_NEW_FIVE
import biz.belcorp.salesforce.modules.developmentpath.utils.Constant.NUMBER_NEW_FOUR
import biz.belcorp.salesforce.modules.developmentpath.utils.Constant.NUMBER_NEW_ONE
import biz.belcorp.salesforce.modules.developmentpath.utils.Constant.NUMBER_NEW_SIX
import biz.belcorp.salesforce.modules.developmentpath.utils.Constant.NUMBER_NEW_THREE
import biz.belcorp.salesforce.modules.developmentpath.utils.Constant.NUMBER_NEW_TWO

enum class IsConsultantNewCode(val number: Int) {
    NUMBER_ONE(NUMBER_NEW_ONE),
    NUMBER_TWO(NUMBER_NEW_TWO),
    NUMBER_THREE(NUMBER_NEW_THREE),
    NUMBER_FOUR(NUMBER_NEW_FOUR),
    NUMBER_FIVE(NUMBER_NEW_FIVE),
    NUMBER_SIX(NUMBER_NEW_SIX);

    companion object {
        infix fun isNewConsultant(value: Int): Boolean =
            IsConsultantNewCode.values().find { it.number == value }.isNotNull()
    }
}

