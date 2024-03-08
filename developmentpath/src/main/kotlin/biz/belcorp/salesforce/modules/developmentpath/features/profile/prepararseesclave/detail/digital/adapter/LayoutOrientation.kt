package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.digital.adapter

import androidx.annotation.IntDef

@IntDef(
    LayoutOrientation.HORIZONTAL,
    LayoutOrientation.VERTICAL
)
@Retention(AnnotationRetention.RUNTIME)
annotation class LayoutOrientation {
    companion object {
        const val HORIZONTAL = 1
        const val VERTICAL = 2
    }
}
