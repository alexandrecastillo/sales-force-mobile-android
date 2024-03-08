package biz.belcorp.salesforce.modules.kpis.core.domain

import androidx.annotation.IntDef

@Retention(AnnotationRetention.RUNTIME)
@IntDef(
    ProgramType.NO_BRIGHT,
    ProgramType.BRIGHT
)
annotation class ProgramType {
    companion object {
        const val NONE = -1
        const val NO_BRIGHT = 0
        const val BRIGHT = 1
    }
}
