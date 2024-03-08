package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas


import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.entities.zonificacion.UnidadAdministrativa
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import java.util.*

open class Persona(
    val id: Long,
    val personCode: String,
    val primerNombre: String?,
    val segundoNombre: String?,
    val primerApellido: String?,
    val segundoApellido: String?,
    val email: String?,
    val ubicacion: Ubicacion,
    val tipoDocumento: TipoDocumento,
    val documento: String,
    val cumpleanios: String?,
    val fechaNacimiento: Date?,
    val directorio: DirectorioTelefonico?
) {

    @Deprecated("Don't use it, just for migration")
    constructor() : this(
        Constant.NUMERO_CERO.toLong(),
        Constant.EMPTY_STRING,
        null, null, null, null, null,
        Ubicacion.construirDummy(),
        TipoDocumento.NINGUNO, Constant.EMPTY_STRING,
        null, null,
        DirectorioTelefonico.construirDummy()
    )

    open val rol = Rol.NINGUNO

    open val unidadAdministrativa: UnidadAdministrativa
        get() {
            throw Exception()
        }

    val llaveUA: LlaveUA
        get() {
            return unidadAdministrativa.llave
        }

    val nombreApellido = "${primerNombre?.trim() ?: ""} ${(primerApellido?.trim() ?: "")}".trim()

    val iniciales
        get() =
            primerNombre.obtenerPrimerLetra() +
                primerApellido.obtenerPrimerLetra()

    val nombreCorto get() = "${primerNombre?.trim()} ${primerApellido?.trim()}"

    val nombreCompleto: String
        get() {
            val stringBuilder = StringBuilder()
            if (!primerNombre.isNullOrBlank()) stringBuilder.append(primerNombre)
            if (!segundoNombre.isNullOrBlank()) stringBuilder.append(" ${segundoNombre.trim()}")
            if (!primerApellido.isNullOrBlank()) stringBuilder.append(" ${primerApellido.trim()}")
            if (!segundoApellido.isNullOrBlank()) stringBuilder.append(" ${segundoApellido.trim()}")

            return stringBuilder.toString()
        }

    private fun String?.obtenerPrimerLetra(): String {
        return if (this.isNullOrBlank())
            ""
        else
            this.trim().substring(0, 1).toUpperCase()
    }

    enum class TipoDocumento { NINGUNO }

}
