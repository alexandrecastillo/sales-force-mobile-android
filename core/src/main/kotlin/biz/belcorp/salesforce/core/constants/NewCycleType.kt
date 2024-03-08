package biz.belcorp.salesforce.core.constants

import androidx.annotation.IntDef
import biz.belcorp.salesforce.core.constants.NewCycleType.NewCycleType.HIGH_NEW_CYCLE_1D1
import biz.belcorp.salesforce.core.constants.NewCycleType.NewCycleType.HIGH_NEW_CYCLE_2D2
import biz.belcorp.salesforce.core.constants.NewCycleType.NewCycleType.HIGH_NEW_CYCLE_3D3
import biz.belcorp.salesforce.core.constants.NewCycleType.NewCycleType.HIGH_NEW_CYCLE_4D4
import biz.belcorp.salesforce.core.constants.NewCycleType.NewCycleType.HIGH_NEW_CYCLE_5D5
import biz.belcorp.salesforce.core.constants.NewCycleType.NewCycleType.HIGH_NEW_CYCLE_6D6
import biz.belcorp.salesforce.core.constants.NewCycleType.NewCycleType.NEW_CYCLE_1D1
import biz.belcorp.salesforce.core.constants.NewCycleType.NewCycleType.NEW_CYCLE_2D2
import biz.belcorp.salesforce.core.constants.NewCycleType.NewCycleType.NEW_CYCLE_3D3
import biz.belcorp.salesforce.core.constants.NewCycleType.NewCycleType.NEW_CYCLE_4D4
import biz.belcorp.salesforce.core.constants.NewCycleType.NewCycleType.NEW_CYCLE_5D5
import biz.belcorp.salesforce.core.constants.NewCycleType.NewCycleType.NEW_CYCLE_6D6
import biz.belcorp.salesforce.core.constants.NewCycleType.NewCycleType.NEW_CYCLE_NONE

@Retention(AnnotationRetention.RUNTIME)
@IntDef(
    NEW_CYCLE_NONE,
    NEW_CYCLE_1D1,
    NEW_CYCLE_2D2,
    NEW_CYCLE_3D3,
    NEW_CYCLE_4D4,
    NEW_CYCLE_5D5,
    NEW_CYCLE_6D6,
    HIGH_NEW_CYCLE_1D1,
    HIGH_NEW_CYCLE_2D2,
    HIGH_NEW_CYCLE_3D3,
    HIGH_NEW_CYCLE_4D4,
    HIGH_NEW_CYCLE_5D5,
    HIGH_NEW_CYCLE_6D6
)
annotation class NewCycleType {
    companion object NewCycleType {
        const val NEW_CYCLE_NONE = 0
        const val NEW_CYCLE_1D1 = 1
        const val NEW_CYCLE_2D2 = 2
        const val NEW_CYCLE_3D3 = 3
        const val NEW_CYCLE_4D4 = 4
        const val NEW_CYCLE_5D5 = 5
        const val NEW_CYCLE_6D6 = 6
        const val HIGH_NEW_CYCLE_1D1 = 7
        const val HIGH_NEW_CYCLE_2D2 = 8
        const val HIGH_NEW_CYCLE_3D3 = 9
        const val HIGH_NEW_CYCLE_4D4 = 10
        const val HIGH_NEW_CYCLE_5D5 = 11
        const val HIGH_NEW_CYCLE_6D6 = 12
    }
}
