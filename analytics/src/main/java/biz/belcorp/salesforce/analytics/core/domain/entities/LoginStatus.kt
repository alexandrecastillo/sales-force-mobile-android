package biz.belcorp.salesforce.analytics.core.domain.entities

import androidx.annotation.StringDef

@Retention(AnnotationRetention.RUNTIME)
@StringDef(
    LoginStatus.STARTED,
    LoginStatus.NOT_STARTED
)
annotation class LoginStatus {
    companion object {
        const val STARTED = "Sesión iniciada"
        const val NOT_STARTED = "Sesión no iniciada"
    }
}
