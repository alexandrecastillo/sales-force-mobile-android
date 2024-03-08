package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.unidadadministrativa

import biz.belcorp.salesforce.core.data.repository.campania.CampaniasDataMapper
import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.entities.zonificacion.UnidadAdministrativa
import biz.belcorp.salesforce.core.domain.exceptions.UnsupportedRoleException
import biz.belcorp.salesforce.core.entities.sql.campania.CampaniaUaEntity
import biz.belcorp.salesforce.core.entities.sql.campania.CampaniaUaEntity_Table
import biz.belcorp.salesforce.core.entities.sql.directorio.DirectorioVentaUsuarioEntity
import biz.belcorp.salesforce.core.entities.sql.directorio.DirectorioVentaUsuarioEntity_Table
import biz.belcorp.salesforce.core.entities.sql.directorio.SociaEmpresariaEntity
import biz.belcorp.salesforce.core.entities.sql.directorio.SociaEmpresariaEntity_Table
import biz.belcorp.salesforce.core.entities.sql.ua.*
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.guionSiVacioONull
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.unidadadministrativa.LlaveUaDbStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.unidadadministrativa.RegionGrMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.unidadadministrativa.SeccionSociaMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.unidadadministrativa.ZonaGzMapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.DirectorVentas
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.GerenteRegionRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.GerenteZonaRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.SociaEmpresariaRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.sesion.persona
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.zonificacion.Pais
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.zonificacion.RegionRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.zonificacion.SeccionRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.zonificacion.ZonaRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.SessionPersonRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.plan.RddPlanRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.unidadadministrativa.UnidadAdministrativaRepository
import com.raizlabs.android.dbflow.kotlinextensions.*
import com.raizlabs.android.dbflow.sql.language.Select

class UnidadAdministrativaDataRepository(
    private val planRepository: RddPlanRepository,
    private val regionGrMapper: RegionGrMapper,
    private val zonaGzMapper: ZonaGzMapper,
    private val seccionSociaMapper: SeccionSociaMapper,
    private val campaniaDataMapper: CampaniasDataMapper,
    private val sesionManager: SessionPersonRepository,
    private val llaveUaDbStore: LlaveUaDbStore
) : UnidadAdministrativaRepository {

    override fun obtenerUaPorPlan(planId: Long): UnidadAdministrativa? {
        val infoPlan = planRepository.obtenerInfoPlanRdd(planId) ?: return null

        return construirUA(infoPlan.llaveUA)
    }

    override fun obtenerUaPorLlave(llaveUA: LlaveUA): UnidadAdministrativa? {
        return construirUA(llaveUA)
    }

    override fun obtenerLlaveUaPorIdPersona(personaId: Long, rol: Rol): LlaveUA? {
        return llaveUaDbStore.recuperarLlaveUaPorIdRol(personaId, rol)
    }

    private fun construirUA(llaveUA: LlaveUA): UnidadAdministrativa {
        return when (llaveUA.rolLiderAsociado) {
            Rol.SOCIA_EMPRESARIA -> construirUaSeccion(llaveUA)
            Rol.GERENTE_ZONA -> construirUaZona(llaveUA)
            Rol.GERENTE_REGION -> construirUaRegion(llaveUA)
            Rol.DIRECTOR_VENTAS -> construirUAPais()
            else -> throw UnsupportedRoleException(llaveUA.rolLiderAsociado)
        }
    }

    private fun construirUaSeccion(llaveUA: LlaveUA): SeccionRdd {
        val seccion = construirSeccion(llaveUA)
        val zona = construirZona(llaveUA)
        seccion.zona = zona

        val region = construirRegion(llaveUA)
        zona.region = region

        return seccion
    }

    private fun construirUaZona(llaveUA: LlaveUA): ZonaRdd {
        val zona = construirZona(llaveUA)

        val region = construirRegion(llaveUA)
        zona.region = region

        return zona
    }

    private fun construirUaRegion(llaveUA: LlaveUA): RegionRdd {
        return construirRegion(llaveUA)
    }

    private fun construirUAPais(): UnidadAdministrativa {
        return construirPais(null)
    }

    private fun construirSeccion(llaveUA: LlaveUA): SeccionRdd {
        val seccion = parsearSeccion(llaveUA)
        val socia = construirSocia(llaveUA)

        seccion.sociaEmpresaria = socia
        socia?.seccion = seccion

        return seccion
    }

    private fun recuperarModeloSeccionSocia(llaveUA: LlaveUA): SeccionSociaJoinned? {
        val where = (Select(
            SeccionEntity_Table.Codigo.withTable(),
            SociaEmpresariaEntity_Table.ConsultorasId.withTable(),
            SociaEmpresariaEntity_Table.PrimerNombre.withTable(),
            SociaEmpresariaEntity_Table.PrimerApellido.withTable()
        )
            from SeccionEntity::class

            leftOuterJoin SociaEmpresariaEntity::class
            on (SeccionEntity_Table.ConsultoraCodigo.withTable()
            eq SociaEmpresariaEntity_Table.Codigo.withTable())

            where (SeccionEntity_Table.Region.withTable() eq llaveUA.codigoRegion)
            and (SeccionEntity_Table.Zona.withTable() eq llaveUA.codigoZona)
            and (SeccionEntity_Table.Codigo.withTable() eq llaveUA.codigoSeccion))

        return where.queryCustomSingle(SeccionSociaJoinned::class.java)
    }

    private fun parsearSeccion(llaveUA: LlaveUA): SeccionRdd {
        return SeccionRdd(
            codigo = requireNotNull(llaveUA.codigoSeccion),
            campania = obtenerCampaniaSeccion(llaveUA),
            sociaEmpresaria = null,
            consultoras = emptyList(),
            nivel = null,
            planId = null,
            visitasProgramadasInicialmente = 0,
            visitasRegistradas = 0
        )
    }

    private fun construirSocia(llaveUA: LlaveUA): SociaEmpresariaRdd? {
        val modelo = recuperarModeloSeccionSocia(llaveUA) ?: return null

        if (modelo.consultoraId == null) return null

        return seccionSociaMapper.parsearSocia(modelo)
    }

    private fun construirZona(llaveUA: LlaveUA): ZonaRdd {
        val zona = parsearZona(llaveUA)
        val gerenteZona = construirGerenteZona(llaveUA)

        zona.gerenteZona = gerenteZona
        gerenteZona?.zona = zona

        return zona
    }

    private fun construirGerenteZona(llaveUA: LlaveUA): GerenteZonaRdd? {

        val where = (Select(
            ZonaEntity_Table.Codigo.withTable(),
            ZonaEntity_Table.GerenteZona.withTable(),
            DirectorioVentaUsuarioEntity_Table.consultoraID.withTable(),
            DirectorioVentaUsuarioEntity_Table.PrimerNombre.withTable(),
            DirectorioVentaUsuarioEntity_Table.PrimerApellido.withTable(),
            DirectorioVentaUsuarioEntity_Table.Estado.withTable(),
            DirectorioVentaUsuarioEntity_Table.Usuario.withTable()
        )
            from ZonaEntity::class

            leftOuterJoin DirectorioVentaUsuarioEntity::class
            on ((ZonaEntity_Table.Codigo.withTable()
            eq DirectorioVentaUsuarioEntity_Table.CodZona.withTable())
            and (DirectorioVentaUsuarioEntity_Table.CodRol.withTable()
            eq Rol.GERENTE_ZONA.codigoRol))

            where (ZonaEntity_Table.Codigo.withTable() eq llaveUA.codigoZona))

        val modelo = where.queryCustomSingle(ZonaGzJoinned::class.java) ?: return null

        return zonaGzMapper.parsearGerente(modelo)
    }

    private fun parsearZona(llaveUA: LlaveUA): ZonaRdd {
        return ZonaRdd(
            codigo = requireNotNull(llaveUA.codigoZona),
            campania = obtenerCampaniaZona(llaveUA),
            secciones = emptyList(),
            gerenteZona = null,
            focos = mutableListOf(),
            planId = -1L,
            habilidades = mutableListOf()
        )
    }


    private fun construirRegion(llaveUA: LlaveUA): RegionRdd {
        val region = parsearRegion(llaveUA)
        val gerenteRegion = construirGerenteRegion(llaveUA)

        region.gerenteRegion = gerenteRegion
        gerenteRegion?.region = region

        return region
    }

    private fun parsearRegion(llaveUA: LlaveUA): RegionRdd {
        return RegionRdd(
            codigo = requireNotNull(llaveUA.codigoRegion),
            campania = obtenerCampaniaRegion(llaveUA),
            planId = -1L,
            focos = mutableListOf(),
            habilidades = mutableListOf(),
            gerenteRegion = null,
            visitasPlanificadas = 0,
            visitasRealizadas = 0,
            zonas = emptyList(),
            planValido = false
        )
    }

    private fun construirGerenteRegion(llaveUA: LlaveUA): GerenteRegionRdd? {

        val where = (Select(
            RegionEntity_Table.Codigo.withTable(),
            RegionEntity_Table.GerenteRegion.withTable(),
            DirectorioVentaUsuarioEntity_Table.consultoraID.withTable(),
            DirectorioVentaUsuarioEntity_Table.PrimerNombre.withTable(),
            DirectorioVentaUsuarioEntity_Table.PrimerApellido.withTable(),
            DirectorioVentaUsuarioEntity_Table.Estado.withTable(),
            DirectorioVentaUsuarioEntity_Table.Usuario.withTable()
        )
            from RegionEntity::class

            leftOuterJoin DirectorioVentaUsuarioEntity::class
            on ((RegionEntity_Table.Codigo.withTable()
            eq DirectorioVentaUsuarioEntity_Table.CodRegion.withTable())
            and (DirectorioVentaUsuarioEntity_Table.CodRol.withTable()
            eq Rol.GERENTE_REGION.codigoRol))

            where (RegionEntity_Table.Codigo.withTable() eq llaveUA.codigoRegion))

        val modelo = where.queryCustomSingle(RegionGrJoinned::class.java) ?: return null

        return regionGrMapper.parseGerente(modelo)
    }

    private fun construirPais(directorVentas: DirectorVentas?): Pais {

        val sesion = requireNotNull(sesionManager.get())

        return Pais(
            codigo = sesion.countryIso,
            campania = sesion.persona.unidadAdministrativa.campania,
            directorVentas = directorVentas,
            regiones = mutableListOf()
        )
    }

    private fun obtenerCampaniaSeccion(llaveUA: LlaveUA): Campania {
        val where = (select from CampaniaUaEntity::class
            where (CampaniaUaEntity_Table.Region.withTable()
            eq llaveUA.codigoRegion.guionSiVacioONull())
            and (CampaniaUaEntity_Table.Zona.withTable()
            eq llaveUA.codigoZona.guionSiVacioONull())
            and (CampaniaUaEntity_Table.Seccion.withTable()
            eq llaveUA.codigoSeccion.guionSiVacioONull())
            and (CampaniaUaEntity_Table.Orden.withTable()
            eq CampaniaUaEntity.ORDEN_CAMPANIA_ACTUAL))

        val modelo = where.querySingle() ?: return Campania.construirDummy()

        return campaniaDataMapper.parse(modelo)
    }

    private fun obtenerCampaniaZona(llaveUA: LlaveUA): Campania {
        val where = (select from CampaniaUaEntity::class
            where (CampaniaUaEntity_Table.Region.withTable()
            eq llaveUA.codigoRegion.guionSiVacioONull())
            and (CampaniaUaEntity_Table.Zona.withTable()
            eq llaveUA.codigoZona.guionSiVacioONull())
            and (CampaniaUaEntity_Table.Seccion.withTable() eq "-")
            and (CampaniaUaEntity_Table.Orden.withTable()
            eq CampaniaUaEntity.ORDEN_CAMPANIA_ACTUAL))

        val modelo = where.querySingle() ?: return Campania.construirDummy()

        return campaniaDataMapper.parse(modelo)
    }

    private fun obtenerCampaniaRegion(llaveUA: LlaveUA): Campania {
        val where = (select from CampaniaUaEntity::class
            where (CampaniaUaEntity_Table.Region.withTable()
            eq llaveUA.codigoRegion.guionSiVacioONull())
            and (CampaniaUaEntity_Table.Zona.withTable() eq "-")
            and (CampaniaUaEntity_Table.Seccion.withTable() eq "-")
            and (CampaniaUaEntity_Table.Orden.withTable()
            eq CampaniaUaEntity.ORDEN_CAMPANIA_ACTUAL))

        val modelo = where.querySingle() ?: return Campania.construirDummy()

        return campaniaDataMapper.parse(modelo)
    }
}
