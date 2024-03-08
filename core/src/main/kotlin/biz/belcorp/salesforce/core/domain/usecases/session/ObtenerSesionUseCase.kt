package biz.belcorp.salesforce.core.domain.usecases.session

import biz.belcorp.salesforce.core.domain.entities.session.Sesion
import biz.belcorp.salesforce.core.domain.exceptions.UnavailableSessionException
import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.core.utils.Logger

class ObtenerSesionUseCase(private val repository: SessionRepository) {

    fun obtener(): Sesion {
        return repository.getSession() ?: run {
            with(UnavailableSessionException()) {
                Logger.e(this)
                throw this
            }
        }
    }

    fun esSesionActiva(): Boolean {
        return repository.esSesionActiva()
    }

    fun isAvailable(): Boolean {
        return repository.isAvailable()
    }

}
