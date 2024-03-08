package biz.belcorp.salesforce.modules.developmentpath.core.data.utils

object Constant {

    const val AM = "AM"
    const val PM = "PM"

    const val CAMPANIA_FACTURACION = "F"
    const val CAMPANIA_VENTA = "V"
    const val CAMPANIA = "C"

    const val EMPTY_STRING = ""
    const val HYPHEN = "-"
    const val BLANK_SPACE = " "
    const val EMPTY_ARRAY = "[]"
    const val CERO = 0
    const val UNO = 1
    const val MENOS_UNO = -1

    const val BROADCAST_REFRESH = "biz.belcorp.salesforce.REFRESH"

    const val YES = "Sí"
    const val YES_SIN_TILDE = "Si"
    const val NO = "No"

    const val AUTHENTICATION = "AUTHETICATION"

    const val AUTH_TOKEN = "token"

    //Preferences
    const val USER_PREFERENCES = "UserPreferences"
    const val FIRST_NAME = "first_name"
    const val LAST_NAME = "last_name"
    const val SECOND_NAME = "second_name"
    const val ROL = "rol"
    const val COD_ROL = "cod_rol"
    const val COD_PAIS = "cod_pais"
    const val REGION = "region"
    const val ZONA = "zona"
    const val SECCION = "seccion"
    const val NIVEL = "nivel"
    const val CUB = "cub"
    const val USERNAME = "username"
    const val COD_CONSULTORA = "codConsultora"
    const val COD_USUARIO = "codUsuario"
    const val EMAIL = "email"
    const val DOCUMENTO_IDENTIDAD = "documento_identidad"
    const val CONSULTORA_ID = "consultora_id"
    const val LATITUD = "latitud"
    const val LONGITUD = "longitud"
    const val IS_LOGGED = "is_logged"

    const val POSITION_LANZAMIENTOS_ESTRATEGICOS = 10

    const val PEDIDOS_ALTO_VALOR = 30
    const val PEDIDOS_BAJO_VALOR = 31

    const val COD_ECUADOR = "EC"

    const val STATUS_ENABLE = "1"

    const val URL_COD_ISO = "CodigoISO"
    const val URL_COD_CONSULTORA = "CodigoConsultora"
    const val URL_COD_REGION = "CodigoRegion"
    const val URL_COD_ZONA = "CodigoZona"
    const val URL_COD_SECCION = "CodigoSeccion"
    const val URL_COD_ROL = "CodigoRol"
    const val URL_COD_CAMPANIA_ACTUAL = "CampaniaActual"
    const val URL_COD_DIGITO_VERIFICADOR = "MuestraDigitoVerificador"
    const val URL_COD_FASE = "Fase"
    const val URL_COD_PAGINA = "Pagina"
    const val URL_VALUE_PAGINA = "IpUnico"
    const val URL_COD_ORIGEN = "FuenteOrigen"
    const val URL_COD_ES_EXTERNO = "LayoutExterno"

    const val HASH_ORIGIN = "App"

    const val SEGMENTO_BRILLA = "Brilla%"
    const val SEGMENTO_EMPRESARIA = "Empresaria%"
    const val SEGMENTO_EXPERTA = "C1%"
    const val SEGMENTO_ESPECIAL = "C2%"
    const val SEGMENTO_ASESORA = "C3%"
    const val SEGMENTO_ESTABLECIDA = "Establecidas"
    const val SEGMENTO_TODOS = "Todos"

    // Prepararse es Clave
    const val VENTA = 0
    const val MASVENDIDO = 1
    const val DIGITAL = 2
    const val ACUERDOSU3C = 3
    const val RESULTADOSCX = 0
    const val NEGOCIO = 1

    const val TEXT_VENTA = "Ventas"
    const val TEXT_MASVENDIDO = "Lo más vendido"
    const val TEXT_DIGITAL = "Digital"
    const val TEXT_RESULTADOSCX = "Resultados %s"
    const val TEXT_NEGOCIO = "Negocio"

    //RESULTADOS CAMPANIA ANTERIOR
    const val META_REAL = 0
    const val RESULTADOS_CX = 1

    //AUTORIZADO
    const val AUTORIZADO_SI = 1
    const val AUTORIZADO_NO = 2

    const val ONE = "1"

    //CONFIRMAR EVENTO
    var CONFIRMAR = false

    const val NO_DATA = "No data"
    const val RESULT_NOT_FOUND = "No se encontraron datos de contacto"
    const val CONSULTANT_NOT_FOUND = "Consultora no encontrada"
    const val PERSON_NOT_FOUND = "No se ha encontrado a la persona"
    const val VISIT_ID_NOT_VALID = "Id de visita inválido"
    const val GR_NOT_FOUND = "Datos de Gerente Region no existentes"
    const val GZ_NOT_FOUND = "Datos de Gerente Zona no existentes"
    const val GAIN_NOT_FOUND = "No se encontraron datos de cobranza"
    const val NOT_COUNTRY_CONFIG = "No existe configuracion para el pais"

    const val INVALID_CAMPAIGN = "Campaña inválida"
    const val INVALID_RDD_CONFIGURATION = "Configuración RDD inválida"



    //ORIGEN
    const val ORIGEN_WEB_CODIGO = "W"
    const val ORIGEN_WEB_NOMBRE = "Web"
    const val ORIGEN_DIGITACION_CODIGO = "D"
    const val ORIGEN_DIGITACION_NOMBRE = "DD"

    const val VER_CONSULTORAS_CON_PEDIDO_SB = "Ver consultoras con pedido en SB"
    const val NO_VER_CONSULTORAS_CON_PEDIDO_SB = "No ver consultoras con pedido en SB"

    const val HABILIDADES_PERCENTAGE_LIMIT = 25

    const val BROADCAST_NUEVOS_INDICADORES_RDD = "biz.belcorp.salesforce.INDICADORES_RDD"
    const val INDICADOR_TOTAL_VISITADAS = "biz.belcorp.salesforce.indicadores.visitadas"
    const val INDICADOR_TOTAL_PLANIFICADAS = "biz.belcorp.salesforce.indicadores.planificadas"

    const val ZERO_NUMBER = 0
    const val NUMBER_ONE = 1
    const val NUMBER_TWO = 2
    const val NUMBER_THREE = 3
    const val NUMBER_FOUR = 4
    const val NUMBER_FIVE = 5
    const val NUMBER_SIX = 6
    const val CODIGO_UA = ""
    const val EVENTO_ASIGNAR_FOCOS = 0
    const val EVENTO_EDITAR_FOCOS = 1
    const val CONSULTORAS_FACTURARON = "Consultoras facturaron"
    const val CONSULTORAS_NO_FACTURARON = "Consultoras aún no facturaron"
    const val BROADCAST_CAMBIO_PLANIFICACION_RDD = "biz.belcorp.salesforce.RECALCULO_RDD"
    const val BROADCAST_ESTADO_PROGRESS = "biz.belcorp.salesforce.ESTADO_PROGRESS"
    const val BROADCAST_EVENTO_EDITADO = "biz.belcorp.salesforce.EVENTO_EDITADO"
    const val BROADCAST_CLICK_SECCION_RDD = "biz.belcorp.salesforce.CLICK_SECCION_RDD"
    const val BROADCAST_REGISTRO_VISITA = "biz.belcorp.salesforce.REGISTRO_VISITA"
    const val BROADCAST_CAMBIO_ACUERDOS = "biz.belcorp.salesforce.CAMBIO_ACUERDOS"
    const val BROADCAST_PLANIFICAR_VISITA = "biz.belcorp.salesforce.PLANIFICAR_VISITA"
    const val BROADCAST_CAMBIO_RECONOCIMIENTO = "biz.belcorp.salesforce.CAMBIO_RECONOCIMIENTO"
    const val BROADCAST_RECARGAR_FOCOS = "biz.belcorp.salesforce.RECARGAR_FOCOS"
    const val BROADCAST_RECARGAR_MIS_FOCOS = "biz.belcorp.salesforce.RECARGAR_MIS_FOCOS"
    const val BROADCAST_RECARGAR_HABILIDADES = "biz.belcorp.salesforce.RECARGAR_HABILIDADES"
    const val BROADCAST_RECARGAR_HABILIDADES_EQUIPO = "biz.belcorp.salesforce.RECARGAR_HABILIDADES_ASIGNADAS"
    const val BROADCAST_RECARGAR_HABILIDADES_PROPIAS = "biz.belcorp.salesforce.RECARGAR_HABILIDADES_PROPIAS"

    const val BROADCAST_CAMBIO_RECONOCIMIENTO_A_SUPERIOR = "biz.belcorp.salesforce.CAMBIO_RECONOCIMIENTO_A_SUPERIOR"
    const val BROADCAST_IR_A_PERFIL = "biz.belcorp.salesforcev.BROADCAST_VER_A_PERFIL"
    const val BROADCAST_IR_A_PERFIL_PARAM = "biz.belcorp.salesforcev.BROADCAST_VER_PERFIL.PARAM"
    const val BROADCAST_IR_A_AGREGAR_EVENTO = "biz.belcorp.salesforcev.BROADCAST_AGREGAR_EVENTO"
    const val BROADCAST_IR_A_AGREGAR_EVENTO_FECHA_PARAM = "biz.belcorp.salesforcev.BROADCAST_AGREGAR_EVENTO_FECHA.PARAM"
    const val BROADCAST_IR_A_VER_DETALLE_EVENTO = "biz.belcorp.salesforcev.BROADCAST_VER_DETALLE_EVENTO"
    const val BROADCAST_RECARGAR_VISTA_PLAN = "biz.belcorp.salesforcev.BROADCAST_RECARGAR_VISTA_PLAN"
    const val BROADCAST_IR_A_VER_DETALLE_EVENTO_ID_PARAM = "biz.belcorp.salesforcev.BROADCAST_VER_DETALLE_EVENTO_ID.PARAM"
    const val BROADCAST_ITEM_POSITION_EXTRA = "BROADCAST_ITEM_POSITION"

    var EVENTO_VER_MAS = true
    var EVENTO_VER_MAS_MENOS = 1
    const val EVENTO_REGRESAR_RUTA = 2
    const val EVENTO_VER_RUTA = false
    const val EVENTO_SALIR = true

    const val BROADCAST_VISIBILIDAD_MENU = "biz.belcorp.salesforce.VISIBILIDAD_OCULTAR"
    const val MENU_MOSTRAR = 0
    const val MENU_OCULTAR = 1
    const val MENU_ACCION = "biz.belcorp.salesforce.accion"
    @JvmField
    val BROADCAST_IR_A_UBICAR_PERSONA = "biz.belcorp.salesforce.IR_A_UBICAR_PERSONA"
    var GR_CODIGO_ROL = "GR"

    const val TAG_VIEW_PAGER_INDICATOR = "ViewPagerIndicator"
    const val DOT_SLIDE_ANIM_DURATION: Long = 150
    const val DOT_PADDING_DIP = 9
    const val LAST_KNOW_CURRENT_PAGE = -1
    const val LAST_KNOW_POSITION_OFFSET = -1f

    const val MAP_MARKER_SIZE = 40

    const val TIPS_DETALLE_CATALOGO_DIGITAL = "Catálogo Digital"
    const val TIPS_DETALLE_COMPARTE = "Comparte"
    const val TIPS_DETALLE_NO_COMPARTE = "No comparte"

    const val TITLE_ONLINE_STORE ="Tienda Online"

    val MTO_COUNTRIES = listOf("BO", "CR", "GT", "SV", "DO")

    const val ONLINE_STORE_ITEM_ID = 0
    const val CONSULTANT_APP_ITEM_ID = 1
    const val DIGITAL_ACCOMPANIMENT_ITEM_ID = 2
    const val GANA_MAS_ITEM_ID = 3
    const val UNIQUE_IP_ITEM_ID = 4
    const val MAKEUP_APP_ITEM_ID = 6

    val ITEM_DISABLES = listOf(DIGITAL_ACCOMPANIMENT_ITEM_ID, MAKEUP_APP_ITEM_ID)

    const val FRAGRANCES_TITLE =  "FRAGANCIAS"
    const val FACIAL_TREATMENT_TITLE = "TRATAMIENTO FACIAL"
    const val MAKE_UP_TITLE = "MAQUILLAJE"
    const val OTHERS_TITLE = "OTROS"
    const val CAMPAIGN_TITLE = "Campaña "
    const val CATEGORY_PRODUCTS_UNITS_TITLE = "Unidades: "
    const val CONSULTANT_STRING = "Consultoras"
    const val NEW_CONSULTANT_STRING = "Consultoras Nuevas"
    const val BP_STRING = "Socias"

}
