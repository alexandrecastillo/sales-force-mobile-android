package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.indicators

import biz.belcorp.salesforce.core.entities.sql.indicators.IndicadorVisitasRDDEntity
import biz.belcorp.salesforce.core.entities.sql.indicators.IndicadorVisitasRDDEntity_Table
import biz.belcorp.salesforce.core.entities.sql.plan.DetallePlanRutaRDDEntity
import biz.belcorp.salesforce.core.entities.sql.plan.DetallePlanRutaRDDEntity_Table
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.visitas.VisitasPropias
import biz.belcorp.salesforce.modules.developmentpath.core.domain.exceptions.DatoNoEncontradoException
import com.raizlabs.android.dbflow.kotlinextensions.*
import com.raizlabs.android.dbflow.sql.language.Select
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.SingleEmitter

class RddIndicatorDVDBDataStore {

    fun obtenerRDDIndicadorVisitasPropias(): Single<VisitasPropias> {
        return Single.create { emitter ->

            val planificadas = (Select(DetallePlanRutaRDDEntity_Table.ConsultorasID.distinct())
                    from DetallePlanRutaRDDEntity::class
                    where (DetallePlanRutaRDDEntity_Table.Rol.eq(Rol.GERENTE_REGION.codigoRol)
                    and DetallePlanRutaRDDEntity_Table.FechaReprogramacion.isNotNull))
                    .queryList().size

            val visitadas = (Select(DetallePlanRutaRDDEntity_Table.ConsultorasID.distinct())
                    from DetallePlanRutaRDDEntity::class
                    where (DetallePlanRutaRDDEntity_Table.Rol.eq(Rol.GERENTE_REGION.codigoRol)
                    and DetallePlanRutaRDDEntity_Table.FechaVisita.isNotNull))
                    .queryList().size

            emitter.onSuccess(VisitasPropias(visitadas.toString(), planificadas.toString()))
        }
    }

    fun obtenerRDDIndicadorVisitasEquipo(): Single<IndicadorVisitasRDDEntity> {
        return Single.create { emitter ->
            val indicador = recuperarIndicadorDv()
            emitirRespuestaIndicador(indicador, emitter)
        }
    }

    private fun recuperarIndicadorDv(): IndicadorVisitasRDDEntity? {
        return (select from IndicadorVisitasRDDEntity::class
                where (IndicadorVisitasRDDEntity_Table.Region.withTable() eq "-")
                and (IndicadorVisitasRDDEntity_Table.Zona.withTable() eq "-")
                and (IndicadorVisitasRDDEntity_Table.Seccion.withTable() eq "-"))
                .querySingle()
    }


    private fun emitirRespuestaIndicador(indicador: IndicadorVisitasRDDEntity?,
                                         emitter: SingleEmitter<IndicadorVisitasRDDEntity>) {
        if (indicador != null) {
            emitter.onSuccess(indicador)
        } else {
            emitter.onError(DatoNoEncontradoException())
        }
    }

    fun guardarRddIndicadorVisitasEquipo(indicadores: List<IndicadorVisitasRDDEntity>): Completable {
        return Completable.create { emitter ->
            indicadores.processInTransaction { indicador, _ -> indicador.save() }

            emitter.onComplete()
        }
    }
}
