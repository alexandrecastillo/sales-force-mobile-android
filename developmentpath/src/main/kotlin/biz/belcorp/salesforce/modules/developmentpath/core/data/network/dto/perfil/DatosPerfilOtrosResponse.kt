package biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.perfil

import biz.belcorp.salesforce.core.data.network.dto.BaseResponse
import biz.belcorp.salesforce.core.entities.sql.acompaniamiento.CumplimientoConsolidadoEntity
import biz.belcorp.salesforce.core.entities.sql.anotaciones.AnotacionEntity
import biz.belcorp.salesforce.core.entities.sql.campania.CampaniaUaEntity
import biz.belcorp.salesforce.core.entities.sql.comportamientos.ComportamientoDetalleEntity
import biz.belcorp.salesforce.core.entities.sql.comportamientos.ComportamientoEntity
import biz.belcorp.salesforce.core.entities.sql.datos.AcuerdoEntity
import biz.belcorp.salesforce.core.entities.sql.datos.CobranzaYGananciaEntity
import biz.belcorp.salesforce.core.entities.sql.datos.DesempenioEntity
import biz.belcorp.salesforce.core.entities.sql.habilidades.DetalleHabilidadesLiderazgoRDDEntity
import biz.belcorp.salesforce.core.entities.sql.habilidades.HabilidadEntity
import biz.belcorp.salesforce.core.entities.sql.habilidades.HabilidadesAsignadasRDDEntity
import biz.belcorp.salesforce.core.entities.sql.habilidades.ReconocimientoHabilidadesRDDEntity
import biz.belcorp.salesforce.core.entities.sql.tips.rdd.DetalleTipsVisitaRDDEntity
import biz.belcorp.salesforce.core.entities.sql.tips.rdd.TipsVisitaRDDEntity
import com.google.gson.annotations.SerializedName

class DatosPerfilOtrosResponse : BaseResponse() {

    @SerializedName("resultado")
    var response: Response? = null

    class Response {

        @SerializedName("desempenio")
        val desempenio: List<DesempenioEntity> = emptyList()

        @SerializedName("cobranzaYGanancia")
        val cobranzaYGanancia: List<CobranzaYGananciaEntity> = emptyList()

        @SerializedName("anotaciones")
        var anotaciones: List<AnotacionEntity> = emptyList()

        @SerializedName("listaTipsVisitaRDD")
        var listaTipsVisitaRDD: List<TipsVisitaRDDEntity> = emptyList()

        @SerializedName("listaDetalleTipsVisitaRDD")
        var listaDetalleTipsVisitaRDD: List<DetalleTipsVisitaRDDEntity> = emptyList()

        @SerializedName("indicadorTotalAcuerdos")
        val acuerdos: List<AcuerdoEntity> = emptyList()

        @SerializedName("listaComportamientos")
        var listaComportamientos: List<ComportamientoEntity> = emptyList()

        @SerializedName("reconocimientoHabilidadesRDD")
        var reconocimientoHabilidadesRDD: List<ReconocimientoHabilidadesRDDEntity> = emptyList()

        @SerializedName("campaniasUA")
        var campaniasUaList: List<CampaniaUaEntity> = emptyList()

        @SerializedName("habilidadesRDD")
        var habilidadesMaestras: List<HabilidadEntity> = emptyList()

        @SerializedName("habilidadesAsignadasRDD")
        var habilidadesAsignadasRDD: List<HabilidadesAsignadasRDDEntity> = emptyList()

        @SerializedName("detalleHabilidadesLiderazgoRDD")
        var detalleHabilidadesLiderazgoRDD: List<DetalleHabilidadesLiderazgoRDDEntity> = emptyList()

        @SerializedName("cumplimientoConsolidado")
        var cumplimientosAcuerdos: List<CumplimientoConsolidadoEntity> = emptyList()

        @SerializedName("listaComportamientosDetalleRDD")
        var comportamientosDetalleRDD: List<ComportamientoDetalleEntity> = emptyList()
    }
}
