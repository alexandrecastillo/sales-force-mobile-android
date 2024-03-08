package biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.perfil

import biz.belcorp.salesforce.core.data.network.dto.BaseResponse
import biz.belcorp.salesforce.core.entities.sql.acompaniamiento.CumplimientoConsolidadoEntity
import biz.belcorp.salesforce.core.entities.sql.anotaciones.AnotacionEntity
import biz.belcorp.salesforce.core.entities.sql.campania.CampaniaUaEntity
import biz.belcorp.salesforce.core.entities.sql.comportamientos.ComportamientoDetalleEntity
import biz.belcorp.salesforce.core.entities.sql.comportamientos.ComportamientoEntity
import biz.belcorp.salesforce.core.entities.sql.cuenta.CdrEntity
import biz.belcorp.salesforce.core.entities.sql.cuenta.CdrProductoEntity
import biz.belcorp.salesforce.core.entities.sql.datos.AcuerdoEntity
import biz.belcorp.salesforce.core.entities.sql.datos.MetasEntity
import biz.belcorp.salesforce.core.entities.sql.marcas.OtraMarcaDetalleEntity
import biz.belcorp.salesforce.core.entities.sql.marcas.OtraMarcaEntity
import biz.belcorp.salesforce.core.entities.sql.path.IntencionPedidoDbModel
import biz.belcorp.salesforce.core.entities.sql.plan.DetallePlanRutaRDDEntity
import biz.belcorp.salesforce.core.entities.sql.tips.acompaniamiento.NivelActualCaminoBrillanteEntity
import biz.belcorp.salesforce.core.entities.sql.tips.acompaniamiento.TipsDesarrolloEntity
import biz.belcorp.salesforce.core.entities.sql.tips.acompaniamiento.TipsNegocioEntity
import com.google.gson.annotations.SerializedName

class DatosPerfilConsultoraResponse : BaseResponse() {

    @SerializedName("resultado")
    var response: Response? = null

    class Response {

        @SerializedName("listadoMetas")
        var metas: List<MetasEntity> = emptyList()

        @SerializedName("listadoCDR")
        var cdrs: List<CdrEntity> = emptyList()

        @SerializedName("listadoCDRProducto")
        var productosCdr: List<CdrProductoEntity> = emptyList()

        @SerializedName("listaComportamientos")
        var comportamientos: List<ComportamientoEntity> = emptyList()

        @SerializedName("listadoAcuerdosVisitas")
        var acuerdos: List<AcuerdoEntity> = emptyList()

        @SerializedName("anotaciones")
        var anotaciones: List<AnotacionEntity> = emptyList()

        @SerializedName("indicadorTotalRutaDesarrolloDetallesList")
        var detallesPlanRdd: List<DetallePlanRutaRDDEntity> = emptyList()

        @SerializedName("listIntencionPedido")
        var intencionPedidos: List<IntencionPedidoDbModel> = emptyList()

        @SerializedName("campaniasUA")
        var campaniasUaList: List<CampaniaUaEntity> = emptyList()

        @SerializedName("listadoMarca")
        var otrasMarcas: List<OtraMarcaEntity> = emptyList()

        @SerializedName("listadoMarcaVenta")
        var otrasMarcasDetalle: List<OtraMarcaDetalleEntity> = emptyList()

        @SerializedName("cumplimientoConsolidado")
        var cumplimientosAcuerdos: List<CumplimientoConsolidadoEntity> = emptyList()

        @SerializedName("listaDetalleTipsDesarrollo")
        var tipsNegocio: List<TipsNegocioEntity> = emptyList()

        @SerializedName("listaTipsDesarrollo")
        var tipsDesarrollo: List<TipsDesarrolloEntity> = emptyList()

        @SerializedName("listaComportamientosDetalleRDD")
        var comportamientosDetalleRDD: List<ComportamientoDetalleEntity> = emptyList()

        @SerializedName("listaNivelActualConsultora")
        var nivelActualCaminoBrillante: List<NivelActualCaminoBrillanteEntity> = emptyList()

    }
}
