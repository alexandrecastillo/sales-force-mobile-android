package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas

import biz.belcorp.salesforce.core.domain.entities.zonificacion.ResponsableUA
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.zonificacion.ZonaRdd
import java.util.*

class GerenteZonaRdd(
    val esNueva: Boolean = false,
    val estadoProductividad: String?,
    val usuario: String,
    val nroCampaniasComoNueva: Int,
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
    fechaNacimiento: Date?,
    directorio: DirectorioTelefonico
) :
    PersonaRdd(
        id = id,
        personCode = usuario,
        primerNombre = primerNombre,
        segundoNombre = segundoNombre,
        primerApellido = primerApellido,
        segundoApellido = segundoApellido,
        email = email,
        ubicacion = ubicacion,
        tipoDocumento = tipoDocumento,
        documento = documento,
        cumpleanios = cumpleanios,
        fechaNacimiento = fechaNacimiento,
        directorio = directorio
    ),
    ResponsableUA {

    lateinit var zona: ZonaRdd

    override val rol = Rol.GERENTE_ZONA

    override val unidadAdministrativa get() = zona

    val textoNueva get() = "Nueva $nroCampaniasComoNueva"
}
