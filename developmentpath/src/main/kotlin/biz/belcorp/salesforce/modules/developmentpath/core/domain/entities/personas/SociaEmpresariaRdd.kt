package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas

import biz.belcorp.salesforce.core.domain.entities.zonificacion.ResponsableUA
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.focos.Foco
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.zonificacion.SeccionRdd
import java.io.Serializable
import java.util.*

class SociaEmpresariaRdd(
    val codigo: String,
    clasificacionLider: String?,
    val subClasificacionLider: String?,
    val exitosa: Boolean,
    val productividad: String?,
    val campaniaIngreso: String?,
    val origenPedido: String?,
    val ultimaFacturacion: String?,
    val focos: MutableList<Foco>,
    val montoPedido: String?,
    val ventaGanancia: String?,
    val saldoPendiente: String?,
    val consultoraConsecutiva: String?,
    val ventaFacturada: String?,
    val recaudoComisionable: String?,
    val ganancia: String?,
    val recaudoTotal: String?,
    val recaudoNoComisionable: String?,
    val gananciaVentaRetail: String?,
    val ventaRetail: String?,
    val fechaAniversario: Date? = null,

    id: Long,
    primerNombre: String?,
    segundoNombre: String?,
    primerApellido: String?,
    segundoApellido: String?,
    email: String?,
    ubicacion: Ubicacion,
    tipoDocumento: TipoDocumento,
    documento: String,
    cumpleanios: String?,
    directorio: DirectorioTelefonico,
    fechaNacimiento: Date? = null
) : PersonaRdd(
    id = id,
    personCode = codigo,
    primerNombre = primerNombre,
    segundoNombre = segundoNombre,
    primerApellido = primerApellido,
    segundoApellido = segundoApellido,
    email = email,
    ubicacion = ubicacion,
    tipoDocumento = tipoDocumento,
    documento = documento,
    cumpleanios = cumpleanios,
    directorio = directorio,
    fechaNacimiento = fechaNacimiento
),
    ResponsableUA {

    lateinit var seccion: SeccionRdd

    override val rol = Rol.SOCIA_EMPRESARIA

    override val unidadAdministrativa get() = seccion

    val esNueva = clasificacionLider != null &&
        clasificacionLider.contains(TIPO_NUEVA, true)

    val nivel: Nivel get() = crearNivel()

    val nivelProductividad: String get() = crearNivelProductividad()

    enum class Nivel(var value: String) : Serializable {
        PRE_BRONCE("PRE-BRONCE"),
        BRONCE("BRONCE"),
        ORO("ORO"),
        PLATA("PLATA"),
        PLATINUM("PLATINUM"),
        DIAMANTE("DIAMANTE"),
        NINGUNA("NINGUNA")
    }

    private fun crearNivel(): Nivel {
        return when {
            seccion.nivel?.trim()?.startsWith(Nivel.BRONCE.value, true) ?: false -> Nivel.BRONCE
            seccion.nivel?.trim()?.startsWith(Nivel.PRE_BRONCE.value, true)
                ?: false -> Nivel.PRE_BRONCE
            seccion.nivel?.trim()?.startsWith(Nivel.ORO.value, true) ?: false -> Nivel.ORO
            seccion.nivel?.trim()?.startsWith(Nivel.PLATA.value, true) ?: false -> Nivel.PLATA
            seccion.nivel?.trim()?.startsWith(Nivel.PLATINUM.name, true) ?: false -> Nivel.PLATINUM
            seccion.nivel?.trim()?.startsWith(Nivel.DIAMANTE.value, true) ?: false -> Nivel.DIAMANTE
            else -> Nivel.NINGUNA
        }
    }

    private fun crearNivelProductividad(): String {
        val stringBuilder = StringBuilder()
        if (seccion.nivel != null) stringBuilder.append(seccion.nivel?.trim())
        if (seccion.nivel == null && productividad != null) stringBuilder.append(productividad.trim())
        if (seccion.nivel != null && productividad != null) stringBuilder.append(" - ${productividad.trim()}")
        if (seccion.nivel == null && productividad == null) stringBuilder.append("-")
        return stringBuilder.toString()
    }

    companion object {
        const val TIPO_NUEVA = "nueva"
    }
}
