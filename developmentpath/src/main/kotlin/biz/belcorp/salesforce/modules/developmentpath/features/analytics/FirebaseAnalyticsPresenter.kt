package biz.belcorp.salesforce.modules.developmentpath.features.analytics

import android.util.Log
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.domain.entities.analytics.EventoModel
import biz.belcorp.salesforce.core.domain.entities.analytics.EventoPushModel
import biz.belcorp.salesforce.core.domain.entities.analytics.TagAnalytics
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.focos.FocoSeleccionado
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.plan.InfoPlanRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.unidadadministrativa.UaRegresable
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.analytics.FirebaseAnalyticsUseCase

class FirebaseAnalyticsPresenter(private val enviarVistaPantalla: FirebaseAnalyticsUseCase) {

    fun enviarPantalla(tagAnalytics: TagAnalytics, planId: Long) {
        val request =
            FirebaseAnalyticsUseCase.RequestPantallaRdd(tagAnalytics, planId, EventoObserver())
        enviarVistaPantalla.enviarPantallRDD(request)
    }

    fun enviarPantallaPerfil(tagAnalytics: TagAnalytics, rol: Rol, personaId: Long) {
        val request = FirebaseAnalyticsUseCase.RequestPantallaPerfil(
            tagAnalytics,
            rol,
            EventoObserver(),
            personaId
        )
        enviarVistaPantalla.enviarPantallPerfil(request)
    }

    fun enviarPantallaMetas(tagAnalytics: TagAnalytics, rol: Rol, personaId: Long) {
        val request = FirebaseAnalyticsUseCase.RequestPantallaMetas(
            tagAnalytics,
            rol,
            EventoObserver(),
            personaId
        )
        enviarVistaPantalla.enviarPantallaMetas(request)
    }

    fun enviarPantallaMarcas(tagAnalytics: TagAnalytics, rol: Rol, personaId: Long) {
        val request = FirebaseAnalyticsUseCase.RequestPantallaMarcas(
            tagAnalytics,
            rol,
            EventoObserver(),
            personaId
        )
        enviarVistaPantalla.enviarPantallaMarcas(request)
    }

    fun enviarPantallaVentas(tagAnalytics: TagAnalytics, rol: Rol, personaId: Long) {
        val request = FirebaseAnalyticsUseCase.RequestPantallaVentas(
            tagAnalytics,
            rol,
            EventoObserver(),
            personaId
        )
        enviarVistaPantalla.enviarPantallaVentas(request)
    }

    fun enviarPantallaDigital(tagAnalytics: TagAnalytics, rol: Rol, personaId: Long) {
        val request = FirebaseAnalyticsUseCase.RequestPantallaDigital(
            tagAnalytics,
            rol,
            EventoObserver(),
            personaId
        )
        enviarVistaPantalla.enviarPantallaDigital(request)
    }

    fun enviarPantallaMasVendido(tagAnalytics: TagAnalytics, rol: Rol, personaId: Long) {
        val request = FirebaseAnalyticsUseCase.RequestPantallaMasVendido(
            tagAnalytics,
            rol,
            EventoObserver(),
            personaId
        )
        enviarVistaPantalla.enviarPantallaMasVendido(request)
    }

    fun enviarPantallaAcuerdos(tagAnalytics: TagAnalytics, rol: Rol, personaId: Long) {
        val request = FirebaseAnalyticsUseCase.RequestPantallaAcuerdos(
            tagAnalytics,
            rol,
            EventoObserver(),
            personaId
        )
        enviarVistaPantalla.enviarPantallaAcuerdos(request)
    }

    fun enviarPantallaVentaGanancia(tagAnalytics: TagAnalytics, rol: Rol, personaId: Long) {
        val request = FirebaseAnalyticsUseCase.RequestPantallaVentaGanancia(
            tagAnalytics,
            rol,
            EventoObserver(),
            personaId
        )
        enviarVistaPantalla.enviarPantallaVentaGanancia(request)
    }

    fun enviarPantallaAcompanamientoDigital(tagAnalytics: TagAnalytics, rol: Rol, personaId: Long) {
        val request = FirebaseAnalyticsUseCase.RequestPantallaAcompaniamientoDigital(
            tagAnalytics,
            rol,
            EventoObserver(),
            personaId
        )
        enviarVistaPantalla.enviarPantallaAcompaniamientoDigital(request)
    }

    fun enviarPantallaCaminoBrillante(tagAnalytics: TagAnalytics, rol: Rol, personaId: Long) {
        val request = FirebaseAnalyticsUseCase.RequestPantallaCaminoBrillante(
            tagAnalytics,
            rol,
            EventoObserver(),
            personaId
        )
        enviarVistaPantalla.enviarPantallaCaminoBrillante(request)
    }

    fun enviarPantallaAcompanamientoNovedades(
        tagAnalytics: TagAnalytics,
        rol: Rol,
        personaId: Long
    ) {
        val request = FirebaseAnalyticsUseCase.RequestPantallaAcompaniamientoNovedades(
            tagAnalytics,
            rol,
            EventoObserver(),
            personaId
        )
        enviarVistaPantalla.enviarPantallaAcompanamientoNovedades(request)
    }

    fun enviarPantallaConcursos(tagAnalytics: TagAnalytics, rol: Rol, personaId: Long) {
        val request = FirebaseAnalyticsUseCase.RequestPantallaConcursos(
            tagAnalytics,
            rol,
            EventoObserver(),
            personaId
        )
        enviarVistaPantalla.enviarPantallaConcursos(request)
    }

    fun enviarPantallaProgramaNuevas(tagAnalytics: TagAnalytics, rol: Rol, personaId: Long) {
        val request = FirebaseAnalyticsUseCase.RequestPantallaProgramaNuevas(
            tagAnalytics,
            rol,
            EventoObserver(),
            personaId
        )
        enviarVistaPantalla.enviarPantallaProgramaNuevas(request)
    }

    fun enviarPantallaProgramaNuevasYTipsEstablecidas(tagAnalytics: TagAnalytics, rol: Rol, personaId: Long) {
        val request = FirebaseAnalyticsUseCase.RequestPantallaProgramaNuevasYTipsEstablecidas(tagAnalytics, rol, EventoObserver(), personaId)
        enviarVistaPantalla.enviarPantallaProgramaNuevasYTipsEstablecidas(request)
    }

    fun enviarPantallaPlanificar(tagAnalytics: TagAnalytics, visitaId: Long) {
        val request =
            FirebaseAnalyticsUseCase.RequestPantallaVisita(tagAnalytics, visitaId, EventoObserver())
        enviarVistaPantalla.enviarPantallaVisita(request)
    }

    fun enviarPantallaEliminarCalendario(tagAnalytics: TagAnalytics, visitaId: Long) {
        val request = FirebaseAnalyticsUseCase.RequestEliminarCalendario(
            tagAnalytics,
            visitaId,
            EventoObserver()
        )
        enviarVistaPantalla.enviarPantallaEliminarCalendario(request)
    }

    fun enviarEvento(tagAnalytics: TagAnalytics) {
        val request = FirebaseAnalyticsUseCase.RequestEvento(tagAnalytics, EventoModelObserver())
        enviarVistaPantalla.enviarEvento(request)
    }

    fun enviarEvento2(tagAnalytics: TagAnalytics, nombreBoton: String, nombreDocumento: String) {
        val request = FirebaseAnalyticsUseCase.RequestEvento2(
            tagAnalytics,
            nombreBoton,
            nombreDocumento,
            EventoModelObserver()
        )
        enviarVistaPantalla.enviarEvento2(request)
    }

    fun enviarEventoPorRol(tagAnalytics: TagAnalytics, rol: Rol) {
        val request =
            FirebaseAnalyticsUseCase.RequestEventoPorRol(tagAnalytics, rol, EventoModelObserver())
        enviarVistaPantalla.enviarEventoPorRol(request)
    }

    fun enviarEventoCambioNivel(
        tagAnalytics: TagAnalytics,
        nivel: String = "",
        constancia: String = ""
    ) {
        val request = FirebaseAnalyticsUseCase.RequestEventoCambioNivel(
            tagAnalytics,
            nivel,
            constancia,
            EventoModelObserver()
        )
        enviarVistaPantalla.enviarEventoCambioNivel(request)
    }

    fun enviarEventoMarcador(tagAnalytics: TagAnalytics, personaId: Long, role: Rol) {
        val request = FirebaseAnalyticsUseCase.RequestEventoMarcador(
            tagAnalytics,
            personaId,
            role,
            EventoModelObserver()
        )
        enviarVistaPantalla.enviarEventoMarcador(request)
    }

    fun enviarEventoPush(
        tagAnalytics: TagAnalytics,
        tipoIngreso: String,
        nombrePushNotification: String
    ) {
        val request = FirebaseAnalyticsUseCase.RequestEventoPush(
            tagAnalytics,
            tipoIngreso,
            nombrePushNotification,
            EventoPushModelObserver()
        )
        enviarVistaPantalla.enviarEventoPush(request)
    }

    fun enviarRegionAvance(tagAnalytics: TagAnalytics, rol: InfoPlanRdd, tipo: Int) {
        val request = FirebaseAnalyticsUseCase.RequestSalirAvance(
            tagAnalytics,
            rol,
            tipo,
            EventoModelObserver()
        )
        enviarVistaPantalla.enviarEventoSalirAvance(request)
    }

    fun enviarRegionVerSalirRuta(tagAnalytics: TagAnalytics, model: UaRegresable) {
        val request =
            FirebaseAnalyticsUseCase.RequestVerSalirRuta(tagAnalytics, model, EventoModelObserver())
        enviarVistaPantalla.enviarEventoVerRuta(request)
    }

    fun enviarRegionAvancePorCodigo(tagAnalytics: TagAnalytics, codigo: String) {
        val request = FirebaseAnalyticsUseCase.RequestAvancePorCodigo(
            tagAnalytics,
            codigo,
            EventoModelObserver()
        )
        enviarVistaPantalla.enviarEventoAvancePorCodigo(request)
    }


    fun enviarFocosCampania(tagAnalytics: TagAnalytics, tipo: Int) {
        val request = FirebaseAnalyticsUseCase.RequestFocoCampaniaAsignarEditar(
            tagAnalytics,
            tipo,
            EventoModelObserver()
        )
        enviarVistaPantalla.enviarEventoAsignarCampania(request)
    }

    fun enviarFocosSeleccionado(tagAnalytics: TagAnalytics, foco: FocoSeleccionado) {
        val request = FirebaseAnalyticsUseCase.RequestFocoSeleccionado(
            tagAnalytics,
            foco,
            EventoModelObserver()
        )
        enviarVistaPantalla.eviarEventoFocoSeleccionado(request)
    }

    fun enviarEventoTagConsultora(tagAnalytics: TagAnalytics, tagName: String?) {
        val request = FirebaseAnalyticsUseCase.RequesEventoPerfilConsultora(
            tagAnalytics,
            tagName,
            EventoModelObserver()
        )
        enviarVistaPantalla.enviarEventoTagConsultora(request)
    }

    fun enviarEventoTagTipsDesarrollo(tagAnalytics: TagAnalytics, tagName: String?) {
        val request = FirebaseAnalyticsUseCase.RequesEventoTipsDesarrollo(
            tagAnalytics,
            tagName,
            EventoModelObserver()
        )
        enviarVistaPantalla.enviarEventoTagTipsDesarrollo(request)
    }

    fun enviarEventoSeleccionOpcion(tagAnalytics: TagAnalytics, tagName: String?) {
        val request = FirebaseAnalyticsUseCase.RequesEventoPerfilConsultora(
            tagAnalytics,
            tagName,
            EventoModelObserver()
        )
        enviarVistaPantalla.enviarEventoSeleccionOpcion(request)
    }

    fun enviarEventoSeleccionSwitch(tagAnalytics: TagAnalytics, tagName: String?) {
        val request = FirebaseAnalyticsUseCase.RequesEventoPerfilConsultora(
            tagAnalytics,
            tagName,
            EventoModelObserver()
        )
        enviarVistaPantalla.enviarEventoSeleccionSwitch(request)
    }

    private class EventoObserver : BaseSingleObserver<String>() {
        override fun onError(e: Throwable) {
            Log.e("TAG_ANALYTICS", e.localizedMessage.orEmpty())
        }

        override fun onSuccess(t: String) {
            Log.d("TAG_ANALYTICS", t)
        }
    }

    private class EventoModelObserver : BaseSingleObserver<EventoModel>() {
        override fun onError(e: Throwable) {
            Log.e("TAG_ANALYTICS", e.localizedMessage.orEmpty())
        }

        override fun onSuccess(t: EventoModel) {
            Log.d("TAG_ANALYTICS", t.label)
        }
    }

    private class EventoPushModelObserver : BaseSingleObserver<EventoPushModel>() {
        override fun onError(e: Throwable) {
            Log.e("TAG_ANALYTICS", e.localizedMessage.orEmpty())
        }

        override fun onSuccess(t: EventoPushModel) {
            Log.d("TAG_ANALYTICS", t.nombrePushNotification)
        }
    }
}
