package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.persona

import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.DirectorioTelefonico
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.Persona
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.sesion.persona
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.Ubicacion
import biz.belcorp.salesforce.core.entities.sql.directorio.DirectorioVentaUsuarioEntity
import biz.belcorp.salesforce.core.entities.sql.directorio.DirectorioVentaUsuarioEntity_Table
import biz.belcorp.salesforce.core.entities.sql.plan.DetallePlanRutaRDDEntity
import biz.belcorp.salesforce.core.entities.sql.plan.DetallePlanRutaRDDEntity_Table
import biz.belcorp.salesforce.core.entities.sql.ua.RegionEntity
import biz.belcorp.salesforce.core.entities.sql.ua.RegionEntity_Table
import biz.belcorp.salesforce.core.entities.sql.visitas.GrDetalleRutaJoinned
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.visitas.VisitaMapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.GerenteRegionRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.visitas.AgendaVisitas
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.zonificacion.RegionRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.GerenteRegionRepository
import com.raizlabs.android.dbflow.kotlinextensions.*

class GerenteRegionDataRepository(private val visitaMapper: VisitaMapper) :
    GerenteRegionRepository {

    override fun obtener(planId: Long): List<GerenteRegionRdd> {
        val query = (select
            from DetallePlanRutaRDDEntity::class
            innerJoin DirectorioVentaUsuarioEntity::class
            on (DetallePlanRutaRDDEntity_Table.CodigoConsultora
            eq DirectorioVentaUsuarioEntity_Table.Usuario)
            innerJoin RegionEntity::class
            on (DirectorioVentaUsuarioEntity_Table.CodRegion
            eq RegionEntity_Table.Codigo)
            where (DetallePlanRutaRDDEntity_Table.Rol
            eq Rol.GERENTE_REGION.codigoRol))

        return parsearDetalles(query.queryCustomList(GrDetalleRutaJoinned::class.java))
    }

    private fun parsearDetalles(modelos: List<GrDetalleRutaJoinned>): List<GerenteRegionRdd> {
        val gerentesAgrupadas = modelos.groupBy { it.codRegion }

        return gerentesAgrupadas.map { parsearGr(it.value) }
    }

    private fun parsearGr(modelos: List<GrDetalleRutaJoinned>): GerenteRegionRdd {

        val modelo = modelos[0]

        val directorio =
            DirectorioTelefonico(
                celular = DirectorioTelefonico.Telefono.construirFavorito(
                    modelo.telefMovil
                ),
                casa = DirectorioTelefonico.Telefono.construir(
                    modelo.telefCasa
                ),
                otro = null
            )

        val gerente = GerenteRegionRdd(
            id = checkNotNull(modelo.consultoraId),
            usuario = checkNotNull(modelo.usuario),
            estadoProductividad = modelo.estado,
            primerNombre = modelo.primerNombre,
            segundoNombre = null,
            primerApellido = modelo.primerApellido,
            segundoApellido = modelo.segundoApellido,
            email = modelo.mailBelcorp,
            ubicacion = Ubicacion.construirDummy(),
            tipoDocumento = Persona.TipoDocumento.NINGUNO,
            documento = modelo.nroDoc ?: "",
            cumpleanios = null,
            fechaNacimiento = null,
            directorio = directorio)

        val region = RegionRdd(codigo = modelo.codRegion ?: "",
            campania = Campania.construirDummy(),
            zonas = emptyList(),
            focos = mutableListOf(),
            habilidades = mutableListOf(),
            gerenteRegion = gerente,
            planId = -1L,
            planValido = false)

        val agenda = AgendaVisitas(visitaMapper.parseFromGr(modelos))

        gerente.region = region
        gerente.establecerAgenda(agenda)
        agenda.establecerPersona(gerente)

        return gerente
    }
}
