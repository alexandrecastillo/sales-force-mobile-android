package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas

import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.buscador.BuscadorRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.menu.VisibilidadMenuHandler
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.visitas.AgendaVisitas
import java.io.Serializable
import java.util.*

open class PersonaRdd(
    id: Long,
    personCode: String,
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
    directorio: DirectorioTelefonico?
) : Persona(
    id,
    personCode,
    primerNombre,
    segundoNombre,
    primerApellido,
    segundoApellido,
    email,
    ubicacion,
    tipoDocumento,
    documento,
    cumpleanios,
    fechaNacimiento,
    directorio
), BuscadorRdd.Buscable {

    override fun match(filtro: String): Boolean {
        return super.match(filtro) ||
            (primerNombre ?: "").contains(filtro, true) ||
            (segundoNombre ?: "").contains(filtro, true) ||
            (primerApellido ?: "").contains(filtro, true) ||
            (segundoApellido ?: "").contains(filtro, true)
    }

    override fun sugerir(): String? = null

    lateinit var agenda: AgendaVisitas

    fun establecerAgenda(agenda: AgendaVisitas) {
        this.agenda = agenda
    }

    val primeraVisitaNoRegistrada get() = agenda.primeraNoRegistrada

    val puedeAdicionarVisita get() = elRolPermiteAdicionarVisitas() && agenda.permiteAdicionarVisitas

    private fun elRolPermiteAdicionarVisitas(): Boolean {
        return rol in listOf(
            Rol.GERENTE_REGION,
            Rol.GERENTE_ZONA,
            Rol.SOCIA_EMPRESARIA,
            Rol.CONSULTORA,
            Rol.POSIBLE_CONSULTORA
        )
    }

    val visibilidadMenuHandler get() = VisibilidadMenuHandler.porPersona(this)

    val puedeRegistrarVisitaHoy get() = !agenda.tieneAlMenosUnaRegistradaEn(Date())

    val identificador get() = Identificador(id, rol)

    class Identificador(val id: Long, val rol: Rol) : Serializable

}
