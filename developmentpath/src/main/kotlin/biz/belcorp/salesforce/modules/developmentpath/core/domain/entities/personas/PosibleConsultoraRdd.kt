package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas

import biz.belcorp.salesforce.core.entities.zonificacion.Rol

class PosibleConsultoraRdd(
    val solicitudId: Long,
    val fuenteIngreso: FuenteIngreso,
    val texto: String = "Posible consultora",
    val paso: Int?,
    val estado: Int?,
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
    data: String?
) : PersonaRdd(
    id = id,
    personCode = id.toString(),
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
    fechaNacimiento = null
) {

    override val rol = Rol.POSIBLE_CONSULTORA

    val tipo: Tipo
        get() {
            return when (fuenteIngreso) {
                FuenteIngreso.UB -> Tipo.PROACTIVA
                FuenteIngreso.MOVIL_SE -> Tipo.NO_PROACTIVA
                else -> Tipo.NINGUNA
            }
        }

    enum class FuenteIngreso { MOVIL_SE, UB, NINGUNO }

    enum class Tipo { PROACTIVA, NO_PROACTIVA, NINGUNA }
}
