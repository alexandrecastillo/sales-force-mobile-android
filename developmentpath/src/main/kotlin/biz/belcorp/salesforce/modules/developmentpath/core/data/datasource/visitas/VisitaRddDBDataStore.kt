package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.visitas

import biz.belcorp.salesforce.core.domain.exceptions.UnsupportedRoleException
import biz.belcorp.salesforce.core.entities.sql.directorio.DirectorioVentaUsuarioEntity
import biz.belcorp.salesforce.core.entities.sql.directorio.DirectorioVentaUsuarioEntity_Table
import biz.belcorp.salesforce.core.entities.sql.plan.DetallePlanRutaRDDEntity
import biz.belcorp.salesforce.core.entities.sql.plan.DetallePlanRutaRDDEntity_Table
import biz.belcorp.salesforce.core.entities.sql.unete.PostulanteEntity
import biz.belcorp.salesforce.core.entities.sql.unete.PostulanteEntity_Table
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.plan.data.PlanRutaDBDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.visitas.VisitaMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.toLongString
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.*
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.visitas.AgendaVisitas
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.visitas.ModeloCreacionVisita
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.visitas.Visita
import com.raizlabs.android.dbflow.kotlinextensions.*
import com.raizlabs.android.dbflow.sql.language.Select

class VisitaRddDBDataStore(
    private val visitaMapper: VisitaMapper,
    private val planRutaDB: PlanRutaDBDataStore
) {

    fun obtenerVisitasDePosibleCandidata(documento: String): AgendaVisitas {
        val result = (Select()
            from DetallePlanRutaRDDEntity::class
            where (DetallePlanRutaRDDEntity_Table.NumeroDocumento.withTable() eq documento)
            and (DetallePlanRutaRDDEntity_Table.ConsultorasID.isNull)
            and (DetallePlanRutaRDDEntity_Table.Rol.withTable() eq Rol.POSIBLE_CONSULTORA.codigoRol))

        val visitas = visitaMapper.parse(result.queryList())

        return AgendaVisitas(visitas.toMutableList())
    }

    fun obtenerVisitasDeGZ(usuario: String): AgendaVisitas {
        val result = (Select()
            from DetallePlanRutaRDDEntity::class
            where (DetallePlanRutaRDDEntity_Table.CodigoConsultora.withTable() eq usuario)
            and (DetallePlanRutaRDDEntity_Table.Rol.withTable() eq Rol.GERENTE_ZONA.codigoRol))

        val visitas = visitaMapper.parse(result.queryList())

        return AgendaVisitas(visitas.toMutableList())
    }

    fun obtenerVisitasDeGR(usuario: String): AgendaVisitas {
        val result = (Select()
            from DetallePlanRutaRDDEntity::class
            where (DetallePlanRutaRDDEntity_Table.CodigoConsultora.withTable() eq usuario)
            and (DetallePlanRutaRDDEntity_Table.Rol.withTable() eq Rol.GERENTE_REGION.codigoRol))

        val visitas = visitaMapper.parse(result.queryList())

        return AgendaVisitas(visitas.toMutableList())
    }

    fun obtenerVisitasDeSociaEmpresaria(id: Long): AgendaVisitas {
        val result = (Select()
            from DetallePlanRutaRDDEntity::class
            where (DetallePlanRutaRDDEntity_Table.ConsultorasID.withTable() eq id)
            and (DetallePlanRutaRDDEntity_Table.Rol.withTable() eq (Rol.SOCIA_EMPRESARIA.codigoRol)))

        val visitas = visitaMapper.parse(result.queryList())

        return AgendaVisitas(visitas.toMutableList())
    }

    fun obtenerVisitasDeConsultora(id: Long): AgendaVisitas {
        val query = (
            select from DetallePlanRutaRDDEntity::class
                where (DetallePlanRutaRDDEntity_Table.ConsultorasID eq id)
                and (DetallePlanRutaRDDEntity_Table.Rol.withTable() eq (Rol.CONSULTORA.codigoRol)))

        val visitas = visitaMapper.parse(query.queryList())

        return AgendaVisitas(visitas.toMutableList())
    }

    fun obtenerTodasPorRol(planId: Long): List<DetallePlanRutaRDDEntity> {
        val query = (
            select from DetallePlanRutaRDDEntity::class
                where (DetallePlanRutaRDDEntity_Table.PlanVisitaID.withTable() eq planId))

        return query.queryList()
    }

    fun obtenerVisita(id: Long): Visita? {
        val where = (
            select from DetallePlanRutaRDDEntity::class
                where (DetallePlanRutaRDDEntity_Table.ID.withTable() eq id))

        val model = where.querySingle() ?: return null

        return visitaMapper.parse(model)
    }


    fun actualizarPlan(visita: Visita) {
        val nuevaReprogramacion = visita.fechaReprogramacion?.toLongString()
        val nuevaVisita = visita.fechaRegistro?.toLongString()
        val nuevaPlanificacion = visita.fechaProgramacionInicial?.toLongString()
        val planificado = if (visita.forzarEliminado) 0 else 1
        val enviado = if (visita.enviado) 1 else 0
        val usuarioRed = visita.usuarioRed

        val update = update<DetallePlanRutaRDDEntity>()
            .set(
                DetallePlanRutaRDDEntity_Table.FechaReprogramacion.eq(nuevaReprogramacion),
                DetallePlanRutaRDDEntity_Table.FechaVisita.eq(nuevaVisita),
                DetallePlanRutaRDDEntity_Table.FechaPlanificacion.eq(nuevaPlanificacion),
                DetallePlanRutaRDDEntity_Table.Planificado.eq(planificado),
                DetallePlanRutaRDDEntity_Table.TipoObservacion.eq(0),
                DetallePlanRutaRDDEntity_Table.Enviado.eq(enviado),
                DetallePlanRutaRDDEntity_Table.UsuarioRed.eq(usuarioRed)
            )
            .where((DetallePlanRutaRDDEntity_Table.ID eq visita.id))

        update.execute()

        if (visita.estaRegistrada) {
            actualizarCabecera(visita)
        }
    }

    private fun actualizarCabecera(visita: Visita) {
        val plan = planRutaDB.obtenerPorId(visita.planId.toInt())
        plan?.let {
            plan.totalVisitadas += 1
            planRutaDB.actualizarVisitadas(plan)
        }
    }

    fun crear(modeloCreacionVisita: ModeloCreacionVisita) {

        val detallePlanRutaRDDEntity = DetallePlanRutaRDDEntity()
        /* Uso negativos para identificar las filas creadas por la aplicación*/
        val id = -System.currentTimeMillis()
        detallePlanRutaRDDEntity.id = id
        detallePlanRutaRDDEntity.idLocal = id
        detallePlanRutaRDDEntity.idPlanVisita = modeloCreacionVisita.planId
        detallePlanRutaRDDEntity.fechaPlanificacion =
            modeloCreacionVisita.fechaPlanificacion.toLongString()
        detallePlanRutaRDDEntity.fechaReprogramacion =
            modeloCreacionVisita.fechaReprogramacion.toLongString()
        detallePlanRutaRDDEntity.rol = modeloCreacionVisita.persona.rol.codigoRol
        detallePlanRutaRDDEntity.tipsid = modeloCreacionVisita.tipsId.toInt()
        detallePlanRutaRDDEntity.planificado = 1
        detallePlanRutaRDDEntity.enviado = 0
        detallePlanRutaRDDEntity.esAdicional = modeloCreacionVisita.esAdicional
        detallePlanRutaRDDEntity.numeroDocumento = modeloCreacionVisita.persona.documento
        detallePlanRutaRDDEntity.consultoraId = modeloCreacionVisita.persona.mapearIdConsultora()
        detallePlanRutaRDDEntity.codigoConsultora =
            modeloCreacionVisita.persona.mapearCodigoConsultora()

        detallePlanRutaRDDEntity.save()
    }

    private fun PersonaRdd.mapearIdConsultora(): Long? {
        return when (this) {
            is PosibleConsultoraRdd -> null
            is ConsultoraRdd -> id
            is SociaEmpresariaRdd -> id
            is GerenteZonaRdd -> id
            is GerenteRegionRdd -> id
            else -> throw UnsupportedRoleException(
                rol
            )
        }
    }

    private fun PersonaRdd.mapearCodigoConsultora(): String? {
        return when (this) {
            is PosibleConsultoraRdd -> null
            is ConsultoraRdd -> codigo
            is SociaEmpresariaRdd -> codigo
            is GerenteZonaRdd -> usuario
            is GerenteRegionRdd -> usuario
            else -> throw UnsupportedRoleException(
                rol
            )
        }
    }

    fun obtenerIdentificadorPorVisita(visitaId: Long): PersonaRdd.Identificador? {
        val rol = requireNotNull(obtenerRolPorVisitaId(visitaId))

        return obtenerIdentificadorPorVisita(visitaId, rol)
    }

    private fun obtenerRolPorVisitaId(visitaId: Long): Rol? {
        val modelo = (select from DetallePlanRutaRDDEntity::class
            where (DetallePlanRutaRDDEntity_Table.ID.withTable() eq visitaId)).querySingle()

        return Rol.Builder.construir(modelo?.rol)
    }

    private fun obtenerIdentificadorPorVisita(visitaId: Long, rol: Rol): PersonaRdd.Identificador? {
        return when (rol) {
            Rol.POSIBLE_CONSULTORA ->
                obtenerIdentificadorDePosibleConsultora(visitaId)
            Rol.CONSULTORA ->
                obtenerIdentificadorDeConsultora(visitaId)
            Rol.SOCIA_EMPRESARIA ->
                obtenerIdentificadorDeSocia(visitaId)
            Rol.GERENTE_ZONA ->
                obtenerIdentificadorDeGz(visitaId)
            Rol.GERENTE_REGION ->
                obtenerIdentificadorDeGr(visitaId)
            else -> throw UnsupportedRoleException(rol)
        }
    }

    private fun obtenerIdentificadorDePosibleConsultora(visitaId: Long): PersonaRdd.Identificador? {
        val modelo = (select
            from PostulanteEntity::class
            innerJoin DetallePlanRutaRDDEntity::class on
            (PostulanteEntity_Table.NumeroDocumento.withTable()
                eq DetallePlanRutaRDDEntity_Table.NumeroDocumento.withTable())
            where (DetallePlanRutaRDDEntity_Table.ID.withTable() eq visitaId))
            .querySingle() ?: return null

        return PersonaRdd.Identificador(modelo.solicitudPostulanteID.toLong(), Rol.POSIBLE_CONSULTORA)
    }

    private fun obtenerIdentificadorDeConsultora(visitaId: Long): PersonaRdd.Identificador? {
        val modelo = (select
            from DetallePlanRutaRDDEntity::class
            where (DetallePlanRutaRDDEntity_Table.ID.withTable() eq visitaId))
            .querySingle() ?: return null

        return PersonaRdd.Identificador(requireNotNull(modelo.consultoraId), Rol.CONSULTORA)
    }

    private fun obtenerIdentificadorDeSocia(visitaId: Long): PersonaRdd.Identificador? {
        val modelo = (select
            from DetallePlanRutaRDDEntity::class
            where (DetallePlanRutaRDDEntity_Table.ID.withTable() eq visitaId))
            .querySingle() ?: return null

        return PersonaRdd.Identificador(requireNotNull(modelo.consultoraId), Rol.SOCIA_EMPRESARIA)
    }

    private fun obtenerIdentificadorDeGz(visitaId: Long): PersonaRdd.Identificador? {
        val modelo = recuperarGzGr(visitaId) ?: return null

        return PersonaRdd.Identificador(requireNotNull(modelo.consultoraId), Rol.GERENTE_ZONA)
    }

    private fun recuperarGzGr(visitaId: Long): DirectorioVentaUsuarioEntity? {
        return (select
            from DirectorioVentaUsuarioEntity::class
            innerJoin DetallePlanRutaRDDEntity::class on
            (DirectorioVentaUsuarioEntity_Table.Usuario.withTable()
                eq DetallePlanRutaRDDEntity_Table.CodigoConsultora.withTable())
            where (DetallePlanRutaRDDEntity_Table.ID.withTable() eq visitaId))
            .querySingle()
    }

    private fun obtenerIdentificadorDeGr(visitaId: Long): PersonaRdd.Identificador? {
        val modelo = recuperarGzGr(visitaId) ?: return null

        return PersonaRdd.Identificador(requireNotNull(modelo.consultoraId), Rol.GERENTE_REGION)
    }
}
