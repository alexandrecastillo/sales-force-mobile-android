package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas

import biz.belcorp.salesforce.core.domain.entities.zonificacion.ResponsableUA
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.zonificacion.ConsultoraUaRdd
import java.io.Serializable
import java.util.*

class ConsultoraRdd(
    val codigo: String,
    val segmento: String?,
    val segmentoInterno: String?,
    val digitoVerificador: String?,
    val campaniaIngreso: String?,
    val cantidadProductoPPU: Int,
    val ultimaFacturacion: String?,
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
    val nivel: String? = Constant.EMPTY_STRING,
    var shareCatalog: Boolean = false,
    var suggestedMessage: String? = Constant.EMPTY_STRING,
    id: Long,
    primerNombre: String?,
    segundoNombre: String?,
    primerApellido: String?,
    segundoApellido: String?,
    email: String? = null,
    ubicacion: Ubicacion,
    tipoDocumento: TipoDocumento,
    documento: String,
    cumpleanios: String?,
    directorio: DirectorioTelefonico?,
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
), ResponsableUA {

    override val rol = Rol.CONSULTORA

    override val unidadAdministrativa get() = consultoraUa

    lateinit var consultoraUa: ConsultoraUaRdd

    val tipo = when {
        segmentoInterno.isNullOrBlank() -> Tipo.NINGUNA
        segmentoInterno.contains(TIPO_NUEVA, true) -> Tipo.NUEVA
        segmentoInterno.contains(TIPO_C3, true) -> Tipo.C3
        else -> Tipo.ESTABLECIDA
    }

    val esNueva = tipo == Tipo.NUEVA

    private val esC3 = tipo == Tipo.C3

    fun mostrarCantidadProductosPPU(): Boolean {
        return (esC3 && (cantidadProductoPPU in CANTIDAD_PRODUCTO_PPU_MINIMA..CANTIDAD_PRODUCTO_PPU_MAXIMA))
    }

    enum class Tipo : Serializable { NINGUNA, NUEVA, ESTABLECIDA, C3 }

    companion object {
        const val CANTIDAD_PRODUCTO_PPU_MINIMA = 1
        const val CANTIDAD_PRODUCTO_PPU_MAXIMA = 3

        const val TIPO_NUEVA = "nueva"
        const val TIPO_C3 = "C3"
    }

}
