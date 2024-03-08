package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.model.card

import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.model.MenuPersonaModel
import java.util.*

class MiRutaCardModel(
    val datosBasicos: DatosBasicos,
    val datosVisita: DatosVisita,
    val datosRol: DatosRol,
    val datosMenu: MenuPersonaModel,
    val datosTipsDesarrollo: DatosTipsDesarrollo?
) {

    class DatosBasicos(
        val personaId: Long,
        val personCode: String,
        val nombre: String,
        val nombreApellido: String,
        val iniciales: String,
        val url: String?
    )

    class DatosVisita(
        val id: Long,
        val hora: String?,
        val mostrarPlanificar: Boolean,
        val mostrarVisitaRegistrada: Boolean,
        val fecha: Date
    ) {
        val mostrarHora = hora != null
    }

    abstract class DatosRol(
        val rol: Rol,
        var color: Color,
        val codigo: String
    )

    class DatosRolConsultora(
        val colorSegmento: Color,
        val segmento: String,
        val mostrarCantidadProductosPPU: Boolean,
        val cantidadProductosPPU: Int,
        rol: Rol,
        color: Color,
        codigo: String
    ) : DatosRol(rol, color, codigo)

    class DatosRolSociaEmpresaria(
        val nivelProductividad: String,
        val exitosa: Boolean,
        val esNueva: Boolean,
        val textoNueva: String?,
        rol: Rol,
        color: Color,
        codigo: String
    ) : DatosRol(rol, color, codigo)

    class DatosRolPosibleConsultora(
        val tipo: String,
        rol: Rol,
        color: Color,
        codigo: String
    ) : DatosRol(rol, color, codigo)

    class DatosRolGerenteZona(
        val estadoProductividad: String,
        val esNueva: Boolean,
        val textoNueva: String?,
        rol: Rol,
        color: Color,
        codigo: String
    ) : DatosRol(rol, color, codigo)

    class DatosRolGerenteRegion(
        val estadoProductividad: String,
        rol: Rol,
        color: Color,
        codigo: String
    ) : DatosRol(rol, color, codigo)

    class DatosTipsDesarrollo(val texto: String,
                              val color: biz.belcorp.salesforce.core.domain.entities.color.Color)

    enum class Color {
        NINGUNO, SANDIA, AMARILLO, VERDE, ROJO, AZUL_POSTULANTE, AMARILLO_ESTABLE, ROJO_CRITICA
    }
}
