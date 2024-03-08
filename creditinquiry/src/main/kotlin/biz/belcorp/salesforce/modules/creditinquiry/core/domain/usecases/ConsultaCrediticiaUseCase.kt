package biz.belcorp.salesforce.modules.creditinquiry.core.domain.usecases

import biz.belcorp.salesforce.core.domain.entities.session.Sesion
import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.modules.creditinquiry.core.domain.entity.ConsultaCrediticia2
import biz.belcorp.salesforce.modules.creditinquiry.core.domain.repository.ConsultaCrediticiaRepository
import biz.belcorp.salesforce.modules.creditinquiry.features.model.ConsultaCrediticiaInterna
import io.reactivex.Single


class ConsultaCrediticiaUseCase constructor(
    private val consultaCrediticiaRepository: ConsultaCrediticiaRepository,
    private val sessionRepository: SessionRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : UseCase(threadExecutor, postExecutionThread) {

    private val session get() = requireNotNull(sessionRepository.getSession())

    fun consultaCrediticiaDeudaInterna(
        pais: String,
        documentoIdentidad: String,
        observer: BaseSingleObserver<ConsultaCrediticiaInterna>
    ) {
        val observable = consultaCrediticiaRepository.consultaCrediticiaDeudaInterna(
            pais,
            documentoIdentidad
        )
        execute(observable, observer)
    }

    fun validarRegionZona(region: String, zona: String, observer: BaseSingleObserver<Int>) {
        val observable = consultaCrediticiaRepository.validarRegionZona(region, zona)
        execute(observable, observer)
    }

    fun consultaCrediticiaDeudaExterna(
        documentoIdentidad: String,
        observer: BaseSingleObserver<ConsultaCrediticia2>
    ) {
        val observable = consultarDatosSession()
            .flatMap {

                val map = hashMapOf(
                    LOGIN_KEY to session.username,
                    LASTNAME_KEY to session.username,
                    COUNTRY_KEY to session.countryIso,
                    DOCUMENT_KEY to documentoIdentidad,
                    REGION_KEY to session.region.orEmpty(),
                    ZONE_KEY to session.zona.orEmpty()
                )

                consultaCrediticiaRepository.consultaCrediticiaDeudaExterna(map)
            }
        execute(observable, observer)
    }

    fun consultaBelcorpBuroCrediticioCO(
        documentoIdentidad: String,
        region: String?,
        zona: String?,
        primerApellido: String?,
        observer: BaseSingleObserver<ConsultaCrediticia2>
    ) {

        val map = hashMapOf(
            LOGIN_KEY to session.username,
            COUNTRY_KEY to session.countryIso,
            DOCUMENT_KEY to documentoIdentidad,
            REGION_KEY to (region ?: session.region).orEmpty(),
            ZONE_KEY to (zona ?: session.zona).orEmpty(),
            SECTION_KEY to session.seccion.orEmpty(),
            LASTNAME_KEY to primerApellido.orEmpty()
        )

        val observable2 = consultarDatosSession().flatMap {
            consultaCrediticiaRepository.consultaCrediticiaDeudaExternaBelcorpCO(map)
        }
        execute(observable2, observer)
    }

    private fun consultarDatosSession(): Single<Sesion> {
        return Single.create {
            it.onSuccess(session)
        }
    }

    companion object {

        private const val LOGIN_KEY = "login"
        private const val COUNTRY_KEY = "codigoISO"
        private const val DOCUMENT_KEY = "numeroDocumento"
        private const val LASTNAME_KEY = "apellido"
        private const val REGION_KEY = "codRegion"
        private const val ZONE_KEY = "codZona"
        private const val SECTION_KEY = "codSeccion"

    }

}
