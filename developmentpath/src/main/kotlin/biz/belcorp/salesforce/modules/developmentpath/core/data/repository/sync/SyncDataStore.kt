package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.sync


import biz.belcorp.salesforce.core.data.exceptions.ErrorException
import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import biz.belcorp.salesforce.core.domain.exceptions.SyncInsertDataException
import biz.belcorp.salesforce.core.domain.exceptions.SyncNoDataException
import biz.belcorp.salesforce.core.domain.usecases.sync.SyncType
import biz.belcorp.salesforce.core.utils.deleteAndInsert
import biz.belcorp.salesforce.core.utils.deleteAndInsertIfNotEmpty
import biz.belcorp.salesforce.core.utils.save
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.acuerdo.data.AcuerdosLocalDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.anotaciones.data.AnotacionDBStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.sync.SyncResponse
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.raizlabs.android.dbflow.config.FlowManager
import io.reactivex.Single
import io.reactivex.SingleEmitter


internal class SyncDataStore(
    private val acuerdosDBDataStore: AcuerdosLocalDataStore,
    private val anotacionesDBDataStore: AnotacionDBStore
) {

    companion object {

        private const val TAG = "SyncResponse =>"
        private const val ERROR_MESSAGE_2 = "No se pudieron obtener los datos."

    }

    fun guardar(resultado: SyncResponse.Resultado): Single<SyncType> {
        return Single.create {

            printResponse(resultado)

            requireNotNull(resultado.data) { throw ErrorException(ERROR_MESSAGE_2) }

            when (resultado.tipo) {
                SyncResponse.Resultado.NONE -> it.onSuccess(SyncType.None)
                SyncResponse.Resultado.PARCIAL -> updateOrInsert(resultado.data, it)
                SyncResponse.Resultado.FULL -> deleteAndInsert(resultado.data, it)
            }
        }
    }

    private fun updateOrInsert(
        data: SyncResponse.Resultado.Data?,
        emitter: SingleEmitter<SyncType>
    ) {
        requireNotNull(data) { throw SyncNoDataException() }
        val db = FlowManager.getDatabase(AppDatabase::class.java).writableDatabase
        return try {
            db.beginTransaction()
            data.apply {
                configuracionRDD.deleteAndInsertIfNotEmpty()
                listaVisitaXFechaRDD.deleteAndInsertIfNotEmpty()

                focosRDD.save()
                listadoTipoMeta.save()
                indicadorObservacionesVisitaRDDList.save()
                listaTipsVisitaRDD.save()
                listaTituloFocoCampania.save()

                planRutaRDD.save()
                detallePlanRutaRDD.save()
                reconocimientos.save()
                habilidadesAsignadasRDD.save()
                configuracionGraficosGrEntity.save()
                intencionPedidos.save()
                acuerdosDBDataStore.guardarSegunIdServer(acuerdos)
                anotacionesDBDataStore.guardarPorServerId(anotaciones)
                comportamientos.save()
                comportamientosDetalleRDD.save()
                comportamientoDetallePorcentaje.save()
            }
            db.setTransactionSuccessful()
            db.endTransaction()
            emitter.onSuccess(SyncType.PartiallyUpdated())
        } catch (ex: Exception) {
            db.endTransaction()
            throw SyncInsertDataException()
        }
    }

    private fun deleteAndInsert(
        data: SyncResponse.Resultado.Data?,
        emitter: SingleEmitter<SyncType>
    ) {
        requireNotNull(data) { throw SyncNoDataException() }
        val db = FlowManager.getDatabase(AppDatabase::class.java).writableDatabase
        return try {
            db.beginTransaction()
            data.apply {
                configuracionRDD.deleteAndInsert()
                planRutaRDD.deleteAndInsert()
                detallePlanRutaRDD.deleteAndInsert()
                reconocimientos.deleteAndInsert()
                desempenio.deleteAndInsert()
                desarrolloHabilidadesRDD.deleteAndInsert()
                reconocimientoHabilidadesRDD.deleteAndInsert()
                habilidadesAsignadasRDD.deleteAndInsert()
                anotaciones.deleteAndInsert()
                cobranzaYGanancia.deleteAndInsert()
                datoPadreUsuarioLogueado?.deleteAndInsert()
                puntajeReconocimientos.deleteAndInsert()
                acuerdos.deleteAndInsert()
                indicadorObservacionesVisitaRDDList.deleteAndInsert()
                listaTipsVisitaRDD.deleteAndInsert()
                listaDetalleTipsVisitaRDD.deleteAndInsert()
                listaVisitaXFechaRDD.deleteAndInsert()
                listadoNotasConsultora.deleteAndInsert()
                listadoMetasConsultoras.deleteAndInsert()
                listadoTipoMeta.deleteAndInsert()
                datosParaPlanificacion.deleteAndInsert()
                cronogramaEventos.deleteAndInsert()
                fechaNoHabilFacturacion.deleteAndInsert()
                listaTituloFocoCampania.deleteAndInsert()
                listaDetalleFocoCampania.deleteAndInsert()
                listaFocosRDD.deleteAndInsert()
                listaFocosDetalleSE.deleteAndInsert()
                focosRDD.deleteAndInsert()
                detalleHabilidadesLiderazgoRDD.deleteAndInsert()
                habilidadesMaestras.deleteAndInsert()
                tiposEventoRdd.deleteAndInsert()
                eventosRdd.deleteAndInsert()
                eventosXUaRdd.deleteAndInsert()
                configuracionGraficosGrEntity.deleteAndInsert()
                intencionPedidos.deleteAndInsert()
                otrasMarcas.deleteAndInsert()
                otrasMarcasDetalle.deleteAndInsert()
                cumplimientosAcuerdos.deleteAndInsert()
                comportamientos.deleteAndInsert()
                comportamientosDetalleRDD.deleteAndInsert()
                comportamientoDetallePorcentaje.deleteAndInsert()
                tipsNegocio.deleteAndInsert()
                tipsDesarrollo.deleteAndInsert()
                novedades.deleteAndInsert()
                nivelesCaminoBrillante.deleteAndInsert()
                nivelActualCaminoBrillante.deleteAndInsert()
                listaHorarioVisita.deleteAndInsert()
                listaHorarioVisitaConsultora.deleteAndInsert()
            }
            db.setTransactionSuccessful()
            db.endTransaction()
            emitter.onSuccess(SyncType.FullyUpdated())
        } catch (ex: Exception) {
            db.endTransaction()
            throw SyncInsertDataException()
        }
    }

    private fun printResponse(resultado: SyncResponse.Resultado) {

        val jsonObject = JsonObject().apply {
            addProperty("tipo", resultado.tipo)
            addProperty("motivo", resultado.motivo)
            addProperty("excepcion", resultado.excepcion)
            addProperty("data", resultado.data != null)
        }

        val gson = GsonBuilder().setPrettyPrinting().create()
        val json = gson.toJson(jsonObject)

        println("$TAG $json")
    }

}
