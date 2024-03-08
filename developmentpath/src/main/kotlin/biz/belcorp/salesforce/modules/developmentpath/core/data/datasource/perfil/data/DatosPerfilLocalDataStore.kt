package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.perfil.data

import biz.belcorp.salesforce.core.entities.sql.anotaciones.AnotacionEntity
import biz.belcorp.salesforce.core.entities.sql.datos.AcuerdoEntity
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.acuerdo.data.AcuerdosLocalDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.anotaciones.data.AnotacionDBStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.perfil.DatosPerfilConsultoraResponse
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.perfil.DatosPerfilOtrosResponse
import com.raizlabs.android.dbflow.kotlinextensions.processInTransaction
import io.reactivex.Completable

class DatosPerfilLocalDataStore(
    private val acuerdosStore: AcuerdosLocalDataStore,
    private val anotacionesStore: AnotacionDBStore
) {

    fun actualizarEntidades(response: DatosPerfilConsultoraResponse.Response): Completable {
        return Completable.create {
            response.apply {
                metas.processInTransaction { it, _ -> it.save() }
                cdrs.processInTransaction { it, _ -> it.save() }
                productosCdr.processInTransaction { it, _ -> it.save() }
                comportamientos.processInTransaction { it, _ -> it.save() }
                actualizarAcuerdos(acuerdos)
                actualizarNotas(anotaciones)
                detallesPlanRdd.processInTransaction { it, _ -> it.save() }
                intencionPedidos.processInTransaction { it, _ -> it.save() }
                campaniasUaList.processInTransaction { campania, _ -> campania.save() }
                otrasMarcas.processInTransaction { it, _ -> it.save() }
                otrasMarcasDetalle.processInTransaction { it, _ -> it.save() }
                cumplimientosAcuerdos.processInTransaction { it, _ -> it.save() }
                tipsNegocio.processInTransaction { it, _ -> it.save() }
                tipsDesarrollo.processInTransaction { it, _ -> it.save() }
                comportamientosDetalleRDD.processInTransaction { it, _ -> it.save() }
                nivelActualCaminoBrillante.processInTransaction { it, _ -> it.save() }
            }
            it.onComplete()
        }
    }

    fun actualizarEntidades(response: DatosPerfilOtrosResponse.Response): Completable {
        return Completable.create {
            response.apply {
                actualizarNotas(anotaciones)
                actualizarAcuerdos(acuerdos)
                desempenio.processInTransaction { it, _ -> it.save() }
                cobranzaYGanancia.processInTransaction { it, _ -> it.save() }
                listaTipsVisitaRDD.processInTransaction { it, _ -> it.save() }
                listaDetalleTipsVisitaRDD.processInTransaction { it, _ -> it.save() }
                listaComportamientos.processInTransaction { it, _ -> it.save() }
                reconocimientoHabilidadesRDD.processInTransaction { it, _ -> it.save() }
                campaniasUaList.processInTransaction { it, _ -> it.save() }
                habilidadesMaestras.processInTransaction { it, _ -> it.save() }
                habilidadesAsignadasRDD.processInTransaction { it, _ -> it.save() }
                detalleHabilidadesLiderazgoRDD.processInTransaction { it, _ -> it.save() }
                cumplimientosAcuerdos.processInTransaction { it, _ -> it.save() }
                comportamientosDetalleRDD.processInTransaction { it, _ -> it.save() }
            }
            it.onComplete()
        }
    }

    private fun actualizarAcuerdos(acuerdos: List<AcuerdoEntity>) {
        if (acuerdos.isNotEmpty())
            acuerdosStore.eliminarSegunUa(acuerdos.first())

        acuerdosStore.guardarSegunIdServer(acuerdos)
    }

    private fun actualizarNotas(anotaciones: List<AnotacionEntity>) {

        if (anotaciones.isNotEmpty())
            anotacionesStore.eliminarSegunUa(anotaciones.first())

        anotacionesStore.guardarPorServerId(anotaciones)
    }
}
