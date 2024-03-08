package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.persona


import biz.belcorp.salesforce.core.domain.exceptions.UnsupportedRoleException
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.personas.*
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.visitas.VisitaRddDBDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.PersonaRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.visitas.ModeloCreacionVisita
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.visitas.Visita
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.RddPersonaRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.plan.RddPlanRepository
import io.reactivex.Single

class PersonaDataRepository(
    private val posibleConsultoraDB: PostulanteDBDataStore,
    private val consultoraDB: ConsultoraDBDataStore,
    private val sociaEmpresariaDB: SociaEmpresariaDBDataStore,
    private val gerenteZonaDBDataStore: GerenteZonaDataStore,
    private val grDBDataStore: GerenteRegionDBDataStore,
    private val visitaDB: VisitaRddDBDataStore,
    private val planRddRepository: RddPlanRepository
) : RddPersonaRepository {

    override fun actualizarVisita(visita: Visita) {
        visitaDB.actualizarPlan(visita)
    }

    override fun recuperarVisita(id: Long): Visita? {
        val visita = visitaDB.obtenerVisita(id)
        val identificador = requireNotNull(visitaDB.obtenerIdentificadorPorVisita(id))
        val persona = requireNotNull(recuperarPersona(identificador))
        visita?.persona = persona

        return visita
    }

    override fun recuperarPersonaPorId(personaId: Long, rol: Rol) =
        when (rol) {
            Rol.POSIBLE_CONSULTORA ->
                posibleConsultoraDB.recuperarPorId(personaId)
            Rol.CONSULTORA ->
                consultoraDB.recuperarPorId(personaId)
            Rol.SOCIA_EMPRESARIA ->
                sociaEmpresariaDB.recuperarPorId(personaId)
            Rol.GERENTE_ZONA ->
                gerenteZonaDBDataStore.recuperarPorId(personaId)
            Rol.GERENTE_REGION ->
                grDBDataStore.recuperarPersonaPorId(personaId)
            Rol.DIRECTOR_VENTAS ->
                throw UnsupportedRoleException(rol)
            Rol.NINGUNO ->
                null
        }

    override fun singleRecuperarPersonaPorId(identificador: PersonaRdd.Identificador): Single<PersonaRdd> {
        return Single.create {
            val persona = checkNotNull(recuperarPersonaPorId(identificador.id, identificador.rol))

            it.onSuccess(persona)
        }
    }

    override fun recuperarPersona(identificador: PersonaRdd.Identificador): PersonaRdd? {
        return recuperarPersonaPorId(identificador.id, identificador.rol)
    }

    override fun crearVisita(visita: ModeloCreacionVisita) {
        visitaDB.crear(visita)
    }

    override fun recuperarIdentificadorDeDuenioDePlan(planId: Long): Pair<Long?, Rol> {
        val infoPlan = planRddRepository.obtenerInfoPlanRdd(planId)
            ?: throw Exception("No existe plan con ese id")

        val personaId = infoPlan.usuario?.run {
            when (infoPlan.rol) {
                Rol.GERENTE_ZONA -> gerenteZonaDBDataStore.recuperarIdSegunUsuario(this)
                Rol.SOCIA_EMPRESARIA -> sociaEmpresariaDB.recuperarIdDeSociaSegunCodigo(this)
                else -> throw Exception("")
            }
        }

        return Pair(personaId, infoPlan.rol)
    }
}
