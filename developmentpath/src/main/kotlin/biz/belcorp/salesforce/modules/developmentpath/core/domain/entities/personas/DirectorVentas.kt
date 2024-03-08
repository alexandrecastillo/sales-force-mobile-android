package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas


import biz.belcorp.salesforce.core.domain.entities.zonificacion.ResponsableUA
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.zonificacion.Pais
import java.util.*

class DirectorVentas(
    val estadoProductividad: String?,
    id: Long,
    usuario: String,
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
    Persona(
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

    lateinit var pais: Pais

    override val rol = Rol.DIRECTOR_VENTAS

    override val unidadAdministrativa get() = pais
}
