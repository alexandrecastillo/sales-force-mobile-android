package biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.dashboard

import biz.belcorp.salesforce.core.data.network.dto.BaseResponse
import biz.belcorp.salesforce.core.entities.sql.campania.CampaniaUaEntity
import biz.belcorp.salesforce.core.entities.sql.comportamientos.ComportamientoDetallePorcentajeEntity
import biz.belcorp.salesforce.core.entities.sql.directorio.DirectorioVentaUsuarioEntity
import biz.belcorp.salesforce.core.entities.sql.habilidades.DesarrolloHabilidadEntity
import biz.belcorp.salesforce.core.entities.sql.path.PuntajeReconocimientoEntity
import biz.belcorp.salesforce.core.entities.sql.plan.PlanRutaRDDEntity
import biz.belcorp.salesforce.core.entities.sql.ua.SeccionEntity
import biz.belcorp.salesforce.core.entities.sql.ua.ZonaEntity
import com.google.gson.annotations.SerializedName

class DashboardResponse : BaseResponse() {
    @SerializedName("resultado")
    var resultado: Resultado? = null

    class Resultado {

        @SerializedName("indicadorTotalRutaDesarrolloList")
        var planRutaRDD: List<PlanRutaRDDEntity> = emptyList()

        @SerializedName("directorioVentaUsuario")
        var directorioVentaUsuario: List<DirectorioVentaUsuarioEntity> = emptyList()

        @SerializedName("listadoZonasGR")
        var zonas: List<ZonaEntity> = emptyList()

        @SerializedName("listadoSeccionesGZ")
        var secciones: List<SeccionEntity> = emptyList()

        @SerializedName("campaniasUA")
        var campaniasUa: List<CampaniaUaEntity> = emptyList()

        @SerializedName("puntajeReconocimientos")
        var puntajeReconocimientos: List<PuntajeReconocimientoEntity> = emptyList()

        @SerializedName("desarrolloHabilidadesRDD")
        var desarrolloHabilidadesRDD: List<DesarrolloHabilidadEntity> = emptyList()

        @SerializedName("listaComportamientosDetallePorcentaje")
        var comportamientoDetallePorcentaje: List<ComportamientoDetallePorcentajeEntity> =
            emptyList()
    }
}
