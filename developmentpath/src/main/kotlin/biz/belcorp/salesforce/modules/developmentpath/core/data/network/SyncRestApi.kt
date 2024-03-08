package biz.belcorp.salesforce.modules.developmentpath.core.data.network

import biz.belcorp.salesforce.core.data.network.dto.BaseResponse
import biz.belcorp.salesforce.core.entities.sql.anotaciones.AnotacionEntity
import biz.belcorp.salesforce.core.entities.sql.comportamientos.ComportamientoDetalleEntity
import biz.belcorp.salesforce.core.entities.sql.datos.AcuerdoEntity
import biz.belcorp.salesforce.core.entities.sql.datos.MetasEntity
import biz.belcorp.salesforce.core.entities.sql.eventos.EventoRddEntity
import biz.belcorp.salesforce.core.entities.sql.horariovisita.HorarioVisitaConsultoraEntity
import biz.belcorp.salesforce.core.entities.sql.marcas.OtraMarcaDetalleEntity
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.acuerdos.AcuerdosRequest
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.acuerdos.AcuerdosResponse
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.anotaciones.AnotacionesResponse
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.cobranza.CobranzaYGananciaResponse
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.dashboard.DashboardResponse
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.eventos.EventoRddXUaRequest
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.eventos.EventosRddResponse
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.focos.DetalleFocoApiRequest
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.focos.DetalleFocoApiResponse
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.focos.DetalleFocoSEApiModel
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.habilidades.asignar.DetalleHabilidadApiRequest
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.habilidades.asignar.DetalleHabilidadApiResponse
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.habilidades.reconocer.HabilidadReconocidaApiRequest
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.habilidades.reconocer.HabilidadReconocidaApiResponse
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.indicators.IndicadoresResponse
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.marcas.OtraMarcaDetalleResponse
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.pedidos.IntencionPedidoResponse
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.perfil.DatosPerfilConsultoraResponse
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.perfil.DatosPerfilOtrosResponse
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.perfil.PuntajePremioResponse
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.plandetalle.PlanDetalleResponse
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.reconocimientos.CalificarVisitaRequest
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.ruta.DetallesRddRequest
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.ruta.IntencionPedidoCloudModel
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.ruta.VisitasPorFechaRequest
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.sync.DatosConsultoraRequest
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.sync.DatosConsultoraResponse
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.sync.SyncResponse
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.visitas.CrearVisitaRequest
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.visitas.CrearVisitaResponse
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.visitas.ResponsePedidosConsultoras
import io.reactivex.Completable
import io.reactivex.Single
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.*

interface SyncRestApi {

    /**
    Endpoint para enviar los datos de teléfonos, email y tipo de teléfono de consultoras.
    Se llama antes de descargar la nueva información en caso haya pendientes.
     */
    @POST("FFVVSyncService/api/consultoras")
    fun consultoras(@Body request: List<DatosConsultoraRequest>): Single<DatosConsultoraResponse>

    /**
    Endpoint para enviar los reconocimientos de SE a GZ.
    Se llama antes de descargar la nueva información en caso haya pendientes.
     */
    @POST("FFVVOnlineService/api/consultora/actualizarreconocimientos")
    fun reconocimientos(@Body request: List<CalificarVisitaRequest>): Completable

    /**
    Endpoint para crear detalles de ruta.
    Requiere de un parámetro llamado idLocal asociado a cada detalle por crear.
    En la respuesta se encuentran agrupados en pares el id del detalle insertado
    y el idLocal enviado.
    Se llama antes de descargar la nueva información en caso haya pendientes.
     */
    @POST("FFVVOnlineService/api/consultora/detalleplaRutardd")
    fun crearVisitas(@Body request: List<CrearVisitaRequest>): Single<CrearVisitaResponse>

    /**
    Endpoint para actualizar los detalles de ruta.
    Se llama antes de descargar la nueva información en caso haya pendientes.
     */
    @POST("FFVVOnlineService/api/consultora/manPlanRutaRDD")
    fun visitas(@Body request: DetallesRddRequest): Single<JSONObject>

    /**
    Endpoint para enviar la cantidad de visitas por día.
    Se llama antes de descargar la nueva información en caso haya pendientes.
     */
    @POST("FFVVOnlineService/api/consultora/listVisitaXFecha")
    fun visitasCantidad(@Body request: VisitasPorFechaRequest): Single<JSONObject>

    /**
    Envía intenciones de pedido pendientes.
     */
    @POST("FFVVOnlineService/api/consultora/intencionPedido")
    fun enviarIntencionesPedido(@Body request: List<IntencionPedidoCloudModel>): Single<BaseResponse>

    /**
    Endpoint para enviar los focos de socia.
    Se mantendrán los endpoints /insertardetallefocos y /grabarfocosdetalle hasta unificarlos.
    Se llama antes de descargar la nueva información en caso haya pendientes.
     */
    @POST("FFVVOnlineService/api/consultora/insertardetallefocos")
    fun focosSE(@Body request: List<DetalleFocoSEApiModel>): Single<JSONObject>

    /**
    Endpoint para enviar detalles de focos (diferentes a 'socias').
    Se mantendrán los endpoints /insertardetallefocos y /grabarfocosdetalle hasta unificarlos.
    Se llama antes de descargar la nueva información en caso haya pendientes.
     */
    @POST("FFVVOnlineService/api/consultora/grabarfocosdetalle")
    fun focos(@Body request: List<DetalleFocoApiRequest>): Single<DetalleFocoApiResponse>

    /**
    Endpoint para enviar detalles de habilidades.
    Se llama antes de descargar la nueva información en caso haya pendientes.
     */
    @POST("FFVVOnlineService/api/consultora/grabarhabilidadesdetalle")
    fun habilidadesAsignadas(
        @Body
        request: List<DetalleHabilidadApiRequest>
    ): Single<DetalleHabilidadApiResponse>

    /**
    Endpoint para enviar detalles de habilidades.
    Se llama antes de descargar la nueva información en caso haya pendientes.
     */
    @POST("FFVVOnlineService/api/consultora/grabarreconocimientohabilidadesrdd")
    fun habilidadesReconocidas(
        @Body
        request: List<HabilidadReconocidaApiRequest>
    ): Single<HabilidadReconocidaApiResponse>

    /**
    Endpoint para enviar las metas.
    Se llama antes de descargar la nueva información en caso haya pendientes.
     */
    @POST("FFVVOnlineService/api/consultora/insertarmetalist")
    fun metas(@Body request: List<MetasEntity>): Single<JSONObject>

    /**
    Endpoint para enviar los acuerdos de visitas.
    Se llama antes de descargar la nueva información en caso haya pendientes.
     */
    @POST("FFVVOnlineService/api/consultora/listAcuerdosXmantRDD")
    fun acuerdos(@Body request: AcuerdosRequest): Single<JSONObject>

    @HTTP(method = "DELETE", path = "FFVVOnlineService/api/consultora/anotaciones", hasBody = true)
    fun eliminarAnotaciones(@Body params: List<AnotacionEntity>): Single<BaseResponse>

    @PUT("FFVVOnlineService/api/consultora/anotaciones")
    fun editarAnotaciones(@Body params: List<AnotacionEntity>): Single<BaseResponse>

    @POST("FFVVOnlineService/api/consultora/anotaciones")
    fun crearAnotaciones(@Body params: List<AnotacionEntity>): Single<AnotacionesResponse>

    @HTTP(method = "DELETE", path = "FFVVOnlineService/api/consultora/acuerdos", hasBody = true)
    fun eliminarAcuerdos(@Body params: List<AcuerdoEntity>): Single<BaseResponse>

    @PUT("FFVVOnlineService/api/consultora/acuerdos")
    fun editarAcuerdos(@Body params: List<AcuerdoEntity>): Single<BaseResponse>

    @POST("FFVVOnlineService/api/consultora/acuerdos")
    fun crearAcuerdos(@Body params: List<AcuerdoEntity>): Single<AcuerdosResponse>

    @GET("FFVVOnlineService/api/datosffvv/cobranzayganancia/{region}/{zona}/{seccion}")
    fun obtenerCobranzaGanancia(
        @Path("region") codigoRegion: String?,
        @Path("zona") codigoZona: String?,
        @Path("seccion")
        codigoSeccion: String?
    ): Single<CobranzaYGananciaResponse>

    /** Recupera los datos necesarios para mostrar el dashboard. El servicio genera planes
     * a demanda, a partir de la UA, cuando el valor de planId es cero.**/
    @GET("FFVVOnlineService/api/datosffvv/dashboard/{planMasUA}")
    fun obtenerDashboard(@Path("planMasUA") planMasUA: String): Single<DashboardResponse>

    @GET("FFVVOnlineService/api/datosffvv/obtenerplandetalle/{planId}")
    fun obtenerPlanDetalle(@Path("planId") planId: Long): Single<PlanDetalleResponse>

    @GET("FFVVOnlineService/api/consultora/datosperfil/{personaId}/{region}")
    suspend fun obtenerDatosPerfilConsultora(
        @Path("personaId") personaId: Long,
        @Path("region")
        region: String
    ): Response<DatosPerfilConsultoraResponse>

    @GET("FFVVOnlineService/api/datosffvv/datosperfil/{rol}/{personaId}")
    suspend fun obtenerDatosPerfilOtrosRoles(
        @Path("personaId") personaId: Long,
        @Path("rol") rol: String
    ): Response<DatosPerfilOtrosResponse>

    @GET("FFVVOnlineService/api/consultora/puntajesconcurso/{pais}/{codigoConsultora}/{campania}/{idConsultora}")
    fun obtenerPuntajesConcurso(
        @Path("pais") pais: String,
        @Path("codigoConsultora") codigoConsultora: String,
        @Path("campania") campania: String,
        @Path("idConsultora")
        idConsultora: String
    ): Single<PuntajePremioResponse>

    /**
    Recupera los montos de pedido ingresado de las consultoras asociadas a una UA
     */
    @GET("FFVVOnlineService/api/consultora/pedidosweb/{campania}/{region}/{zona}/{seccion}")
    fun consultorasConPedidoIngresado(
        @Path("campania") codigoCampania: String,
        @Path("region") codigoRegion: String?,
        @Path("zona") codigoZona: String?,
        @Path("seccion")
        codigoSeccion: String?
    ): Single<ResponsePedidosConsultoras>

    @GET("FFVVSyncService/api/sincronizacion/rdd/{date}/{ua}")
    fun sincronizar(@Path("date") date: Long, @Path("ua") ua: String): Single<SyncResponse>

    @POST("FFVVOnlineService/api/rdd/eventos")
    fun crearEventos(@Body params: List<EventoRddEntity>): Single<EventosRddResponse>

    @PUT("FFVVOnlineService/api/rdd/eventos")
    fun editarEventos(@Body params: List<EventoRddEntity>): Single<EventosRddResponse>

    @PUT("FFVVOnlineService/api/rdd/eventosporua")
    fun editarEventosXUa(@Body params: List<EventoRddXUaRequest>): Completable

    @GET("FFVVOnlineService/api/datosffvv/eventordd/{date}/{campania}/{ua}")
    fun obtenerEventos(
        @Path("date") date: Long,
        @Path("ua") ua: String,
        @Path("campania") campania: String
    ): Single<EventosRddResponse>

    @GET("FFVVOnlineService/api/datosffvv/obtenerintencionpedido/{ua}")
    fun obtenerIntencionDePedido(@Path("ua") ua: String): Single<IntencionPedidoResponse>

    @POST("FFVVOnlineService/api/consultora/grabarmarcaventaconsultora")
    fun grabarMarcaVentaConsultora(@Body data: List<OtraMarcaDetalleEntity>): Single<OtraMarcaDetalleResponse>

    @POST("FFVVOnlineService/api/consultora/guardarcomportamientos")
    fun guardarComportamientos(@Body request: List<ComportamientoDetalleEntity>): Completable

    @GET("FFVVOnlineService/api/datosffvv/indicadorvisitasrdd/{campania}/{ua}")
    fun obtenerIndicadores(@Path("campania") campania: String,
                           @Path("ua") ua: String): Single<IndicadoresResponse>

    @POST("FFVVOnlineService/api/consultora/guardarhorariovisitaconsultora")
    fun guardarHorarioVisita(@Body request: List<HorarioVisitaConsultoraEntity>): Completable
}
