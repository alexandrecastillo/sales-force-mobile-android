package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.dashboard.data

import biz.belcorp.salesforce.core.entities.sql.campania.CampaniaUaEntity
import biz.belcorp.salesforce.core.entities.sql.comportamientos.ComportamientoDetallePorcentajeEntity
import biz.belcorp.salesforce.core.entities.sql.directorio.DirectorioVentaUsuarioEntity
import biz.belcorp.salesforce.core.entities.sql.habilidades.DesarrolloHabilidadEntity
import biz.belcorp.salesforce.core.entities.sql.path.PuntajeReconocimientoEntity
import biz.belcorp.salesforce.core.entities.sql.plan.PlanRutaRDDEntity
import biz.belcorp.salesforce.core.entities.sql.ua.SeccionEntity
import biz.belcorp.salesforce.core.entities.sql.ua.ZonaEntity
import biz.belcorp.salesforce.core.utils.doOnCompletable
import com.raizlabs.android.dbflow.kotlinextensions.processInTransaction
import io.reactivex.Completable

class DashboardLocalDataStore {

    fun crearOActualizarPlanRuta(planesRuta: List<PlanRutaRDDEntity>): Completable {
        planesRuta.processInTransaction { planRuta, _ -> planRuta.save() }

        return Completable.complete()
    }

    fun crearOActualizarDirectorio(directorios: List<DirectorioVentaUsuarioEntity>): Completable {
        directorios.processInTransaction { directorio, _ -> directorio.save() }

        return Completable.complete()
    }

    fun crearOActualizarZonas(zonas: List<ZonaEntity>): Completable {
        zonas.processInTransaction { zona, _ -> zona.save() }

        return Completable.complete()
    }

    fun crearOActualizarSecciones(secciones: List<SeccionEntity>): Completable {
        secciones.processInTransaction { seccion, _ -> seccion.save() }

        return Completable.complete()
    }

    fun crearOActualizarCampanias(campaniasUa: List<CampaniaUaEntity>): Completable {
        campaniasUa.processInTransaction { campaniaUa, _ -> campaniaUa.save() }

        return Completable.complete()
    }

    fun crearOActualizarPuntajeReconocimientos(puntajes: List<PuntajeReconocimientoEntity>): Completable {
        puntajes.processInTransaction { puntaje, _ -> puntaje.save() }

        return Completable.complete()
    }

    fun crearOActualizarDesarrolloHabilidades(habilidades: List<DesarrolloHabilidadEntity>): Completable {
        return doOnCompletable {
            habilidades.processInTransaction { habilidad, _ -> habilidad.save() }
        }
    }

    fun crearOActualizarComportamientoDetallePorcentaje(comportamientos: List<ComportamientoDetallePorcentajeEntity>): Completable {
        return doOnCompletable {
            comportamientos.processInTransaction { comportamiento, _ -> comportamiento.save() }
        }
    }

}
