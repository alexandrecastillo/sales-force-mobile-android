package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.visitas

import biz.belcorp.salesforce.core.entities.sql.plan.DetallePlanRutaRDDEntity
import biz.belcorp.salesforce.core.entities.sql.plan.DetallePlanRutaRDDEntity_Table
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.ruta.data.SenderCambioRDD
import com.raizlabs.android.dbflow.kotlinextensions.*
import com.raizlabs.android.dbflow.sql.language.Where
import io.reactivex.Completable
import io.reactivex.Single

class VisitaDBDataStore(private val sender: SenderCambioRDD) {

    fun recuperarActualizacionesSingle(): Single<List<DetallePlanRutaRDDEntity>> {
        return Single.create {
            it.onSuccess(recuperarActualizacionesNoEnviadas())
        }
    }

    private fun recuperarActualizacionesNoEnviadas(): List<DetallePlanRutaRDDEntity> {
        val where = (select
            from DetallePlanRutaRDDEntity::class
            where (DetallePlanRutaRDDEntity_Table.Enviado eq 0)
            and (DetallePlanRutaRDDEntity_Table.ID greaterThanOrEq 0))
        return where.queryList()
    }

    private fun guardarEnvioDeActualizaciones() {
        val update = com.raizlabs.android.dbflow.kotlinextensions.update<DetallePlanRutaRDDEntity>()
            .set(DetallePlanRutaRDDEntity_Table.Enviado.eq(1))
            .where(DetallePlanRutaRDDEntity_Table.ID greaterThanOrEq 0)
        update.execute()
    }

    fun guardarEnvioDeActualizacionesCompletable(): Completable {
        return Completable.create {
            guardarEnvioDeActualizaciones()
            it.onComplete()
        }
    }

    private fun recuperarInsercionesNoEnviadas(): List<DetallePlanRutaRDDEntity> {
        val where = seleccionarPendientesInsertados()
        return where.queryList()
    }

    fun recuperarInsercionesNoEnviadasSingle(): Single<List<DetallePlanRutaRDDEntity>> {
        return Single.create { it.onSuccess(recuperarInsercionesNoEnviadas()) }
    }

    private fun guardarEnvioDeInserciones(pares: HashMap<Long, Long>) {
        val pendientes = (seleccionarPendientesInsertados()).queryList()
        pendientes.processInTransaction { modeloAntiguo, databasewrapper ->
            val modeloNuevo = clonar(modeloAntiguo)

            modeloNuevo.id = pares[modeloAntiguo.idLocal] ?: return@processInTransaction
            modeloNuevo.enviado = 1

            modeloAntiguo.delete(databasewrapper)
            modeloNuevo.insert(databasewrapper)
        }

        sender.notificarCambioEnPlanificacion()
    }

    private fun clonar(modeloAntiguo: DetallePlanRutaRDDEntity): DetallePlanRutaRDDEntity {
        val modeloNuevo = DetallePlanRutaRDDEntity()

        modeloNuevo.idPlanVisita = modeloAntiguo.idPlanVisita
        modeloNuevo.consultoraId = modeloAntiguo.consultoraId
        modeloNuevo.codigoConsultora = modeloAntiguo.codigoConsultora
        modeloNuevo.fechaPlanificacion = modeloAntiguo.fechaPlanificacion
        modeloNuevo.fechaReprogramacion = modeloAntiguo.fechaReprogramacion
        modeloNuevo.fechaVisita = modeloAntiguo.fechaVisita
        modeloNuevo.numeroDocumento = modeloAntiguo.numeroDocumento
        modeloNuevo.idLocal = modeloAntiguo.idLocal
        modeloNuevo.rol = modeloAntiguo.rol
        modeloNuevo.tipsid = modeloAntiguo.tipsid
        modeloNuevo.planInicial = modeloAntiguo.planInicial
        modeloNuevo.planificado = modeloAntiguo.planificado
        modeloNuevo.esAdicional = modeloAntiguo.esAdicional

        return modeloNuevo
    }

    private fun seleccionarPendientesInsertados(): Where<DetallePlanRutaRDDEntity> {
        return (select from DetallePlanRutaRDDEntity::class
            where (DetallePlanRutaRDDEntity_Table.Enviado eq 0)
            and (DetallePlanRutaRDDEntity_Table.ID lessThan 0))
    }

    fun marcarEnvioDeInsercionesCompletable(pares: HashMap<Long, Long>): Completable {
        return Completable.create {
            guardarEnvioDeInserciones(pares)
            it.onComplete()
        }
    }
}
