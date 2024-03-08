package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.reconocimientos

import biz.belcorp.salesforce.core.data.repository.campania.CampaniasDataStore
import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.sql.campania.CampaniaUaEntity
import biz.belcorp.salesforce.core.entities.sql.comportamientos.ComportamientoDetalleEntity
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.comportamientos.ComportamientosDbStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.reconocimiento.data.ReconocimientosDbStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.entities.comportamientos.PoolReconocimientos
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.comportamientos.ComportamientoMapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.comportamientos.Comportamiento
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.comportamientos.ReconocimientoComportamientoAsociador
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.reconocimiento.TodosLosReconocimientosEnCampania
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.reconocimientos.ReconocimientosEnCampaniaRepository
import com.google.gson.Gson

class ReconocimientosEnCampaniaDataRepository(
    private val gson: Gson,
    private val comportamientosStore: ComportamientosDbStore,
    private val reconocimientosStore: ReconocimientosDbStore,
    private val campaniasStore: CampaniasDataStore,
    private val comportamientoMapper: ComportamientoMapper
) : ReconocimientosEnCampaniaRepository {

    override fun obtenerParaCampaniaActual(llaveUA: LlaveUA):
        TodosLosReconocimientosEnCampania {
        val comportamientos = recuperarComportamientosPorRol(llaveUA.roleAssociated)
        val reconocimiento = recuperarReconocimientoCampaniaActual(llaveUA)
            ?: return construirReconocimientoNoRealizado(llaveUA)
        val campania = Campania.construirDummy(reconocimiento.campania)
        return combinar(reconocimiento, comportamientos, campania)
    }

    private fun construirReconocimientoNoRealizado(llaveUA: LlaveUA):
        TodosLosReconocimientosEnCampania {
        val modeloCampania = recuperarCampaniaActual(llaveUA)
        return construirReconocimientoNoRealizado(modeloCampania)
    }

    private fun recuperarCampaniaActual(llaveUA: LlaveUA) =
        requireNotNull(campaniasStore.obtenerCampaniaSincrono(llaveUA))

    private fun recuperarReconocimientoCampaniaActual(llaveUA: LlaveUA) =
        reconocimientosStore.obtenerReconocimientoCampaniaActual(llaveUA)

    private fun recuperarComportamientosPorRol(rol: Rol): List<Comportamiento> {
        val modelos = comportamientosStore.obtener(rol)
        return comportamientoMapper.convertir(modelos)
    }

    override fun obtenerParaPenultimasCampanias(llaveUA: LlaveUA, limiteCampanias: Int):
        List<TodosLosReconocimientosEnCampania> {
        val campanias = recuperarPenultimasCampanias(llaveUA, limiteCampanias)
        val comportamientos = recuperarComportamientosPorRol(llaveUA.roleAssociated)
        val reconocimientos = recuperarPoolReconocimientos(llaveUA)

        return campanias.map { modeloCampania ->
            val campania = Campania.construirDummy(
                codigo = modeloCampania.codigo,
                nombreCorto = modeloCampania.nombreCorto
            )
            val reconocimiento = reconocimientos.buscar(campania.codigo)
                ?: return@map construirReconocimientoNoRealizado(campania)
            return@map combinar(reconocimiento, comportamientos, campania)
        }
    }

    private fun recuperarPenultimasCampanias(
        llaveUA: LlaveUA,
        limiteCampanias: Int
    ): List<CampaniaUaEntity> {
        return campaniasStore
            .obtenerPenultimasCampaniasSincrono(llaveUA, limiteCampanias)
            .take(limiteCampanias)
    }

    private fun recuperarPoolReconocimientos(llaveUA: LlaveUA): PoolReconocimientos {
        val reconocimientos = reconocimientosStore.obtenerReconocimientos(llaveUA)
        return PoolReconocimientos(reconocimientos)
    }

    private fun combinar(
        reconocimiento: ComportamientoDetalleEntity,
        comportamientos: List<Comportamiento>,
        campania: Campania
    ): TodosLosReconocimientosEnCampania {
        val ids = obtenerIdsReconocidos(reconocimiento.comportamiento)
        val asociador = ReconocimientoComportamientoAsociador(comportamientos, ids)
        return TodosLosReconocimientosEnCampania(
            campania = campania,
            reconocimiento = asociador.asociar()
        )
    }

    private fun construirReconocimientoNoRealizado(modelo: CampaniaUaEntity):
        TodosLosReconocimientosEnCampania {
        val campania = Campania.construirDummy(
            codigo = modelo.codigo,
            nombreCorto = modelo.nombreCorto
        )
        return TodosLosReconocimientosEnCampania.construirNoRealizado(campania)
    }

    private fun construirReconocimientoNoRealizado(campania: Campania):
        TodosLosReconocimientosEnCampania {
        return TodosLosReconocimientosEnCampania.construirNoRealizado(campania)
    }

    private fun obtenerIdsReconocidos(jsonString: String?): List<Long> {
        return gson.fromJson(jsonString ?: "[]", Array<Long>::class.java).toList()
    }
}
