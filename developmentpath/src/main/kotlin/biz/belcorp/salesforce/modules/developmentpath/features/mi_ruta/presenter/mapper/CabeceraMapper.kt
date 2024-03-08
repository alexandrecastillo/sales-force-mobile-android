package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.presenter.mapper

import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.ruta.RddUseCase
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.model.CabeceraViewModel
import java.text.SimpleDateFormat
import java.util.*

class CabeceraMapper {

    fun map(response: RddUseCase.Response): CabeceraViewModel {
        val tituloLista = obtenerStringLista(response.rdd.mesSeleccionado.fecha.time)
        val tituloMapa = obtenerStringMapa(response.rdd.hoy.dia.fecha.time)
        val subtitulo = obtenerStringAnio(response.rdd.mesSeleccionado.fecha.time)
        val mostrarRetroceder =
                (response.sesion.rol == Rol.SOCIA_EMPRESARIA) ||
                        (response.infoPlanRdd.rol != Rol.SOCIA_EMPRESARIA)

        return CabeceraViewModel(
                response.rdd.campaniaActual.periodo!!,
                response.rdd.campaniaActual.nombreCorto,
                tituloLista,
                subtitulo,
                tituloMapa,
                subtitulo,
                response.rdd.mesAnteriorValido,
                response.rdd.mesSiguienteValido,
                mostrarMapa(response.infoPlanRdd.rol),
                mostrarRetroceder)
    }

    private fun obtenerStringLista(fecha: Date): String {
        val df = SimpleDateFormat("MMMM", Locale.getDefault())

        return df.format(fecha).capitalize()
    }

    private fun obtenerStringMapa(fecha: Date): String {
        val df = SimpleDateFormat("MMMM, dd", Locale.getDefault())

        return df.format(fecha).capitalize()
    }

    private fun obtenerStringAnio(fecha: Date): String {
        val df = SimpleDateFormat("yyyy", Locale.getDefault())

        return df.format(fecha)
    }

    private fun mostrarMapa(rolPlan: Rol): Boolean {
        return rolPlan != Rol.DIRECTOR_VENTAS && rolPlan != Rol.GERENTE_REGION
    }
}
