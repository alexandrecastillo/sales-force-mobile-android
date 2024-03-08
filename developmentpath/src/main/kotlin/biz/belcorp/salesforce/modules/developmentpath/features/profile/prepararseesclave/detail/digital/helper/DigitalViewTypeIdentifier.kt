package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.digital.helper

import androidx.annotation.IntDef

@IntDef(
    DigitalViewTypeIdentifier.SINGLE_HORIZONTAL,
    DigitalViewTypeIdentifier.SINGLE_VERTICAL
)
@Retention(AnnotationRetention.RUNTIME)
annotation class DigitalViewTypeIdentifier {
    companion object {
        const val SINGLE_HORIZONTAL = 0
        const val SINGLE_VERTICAL = 1
    }
}
