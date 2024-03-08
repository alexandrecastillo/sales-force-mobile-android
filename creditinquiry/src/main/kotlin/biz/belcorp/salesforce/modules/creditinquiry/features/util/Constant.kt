package biz.belcorp.salesforce.modules.creditinquiry.features.util

import java.util.*

object Constant {

    const val REJECTION = "RECHAZAR"
    const val APROVE = "APROBAR"
    const val APROVED = "APROBADO"
    const val APROVE_AAA = "APROBAR AAA"

    const val REJECTED = "RECHAZADO"
    const val CHECK = "REVISAR"

    const val SENTINEL = "SENTINEL"
    const val EQUIFAX = "EQUIFAX"

    const val RESULT_A = "A"
    const val RESULT_N = "N"


    const val CONSULTANT_ID_KEY = "CONSULTANT_ID_KEY"
    const val CONSULTANT_CAMPAIGN_FULL_KEY = "CONSULTANT_CAMPAIGN_FULL_KEY"
    const val IS_BILLING_KEY = "IS_BILLING_KEY"
    const val ORIGIN_KEY = "ORIGIN_KEY"
    const val ROL_KEY = "ROL_KEY"

    const val START_TUTORIAL_ACTIVITY_CODE = 1
    const val INDICATOR_KEY = "INDICATOR_KEY"
    const val CONFIG_KEY = "CONFIG_KEY"
    const val CONSULTANT_KEY = "CONSULTANT_KEY"
    const val CONSULTANT_LIST_KEY = "CONSULTANT_LIST_KEY"
    const val CAMPAIGN_CODIGO = "CAMPAIGN_CODIGO"
    const val SECTION_ZONE_REGION_LIST_KEY = "SECTION_ZONE_REGION_LIST_KEY"
    const val IS_INVOICE_KEY = "IS_INVOICE_KEY"
    const val CREDIT_CONSULTING_KEY = "CREDIT_CONSULTING_KEY"
    const val CONSULTANT_LIST_SIZE_KEY = "CONSULTANT_LIST_SIZE_KEY"
    const val CONSOLIDADO_LISTENER = "CONSOLIDADO_LISTENER"
    const val SCROLL_LISTENER = "SCROLL_LISTENER"

    const val TYPE_NOTIFICACIONES = 0
    const val TYPE_POSTULANTES = 1
    const val TOKEN = "6310229b16ad4639e323a06939a704ca95d8c8ef"
    const val ZIP_DB_NAME = "ffvv.zip"
    const val SQLITE_DB_NAME = "ffvv.db"

    const val GZ = "gz"
    const val GR = "gr"
    const val DV = "dv"
    const val SE = "se"
    const val CO = "co"
    const val CAMPANIA_FACTURACION = "F"
    const val CAMPANIA_VENTA = "V"
    const val CAMPAIGN_ON_SALE = "VENTA"
    const val CAMPAIGN_ON_BILING = "FACTURACIÓN"
    const val SECTION_A = "A"
    const val CAMPANIA = "C"
    const val FCM_APP_SE_ID = 6
    const val KEY_FCM_TOKEN = "KEY_FCM_TOKEN"
    const val newTokenFireBase = "new_token_fire_base"

    const val EMPTY_DOUBLE = 0.0
    const val EMPTY_ZERO = 0
    const val EMPTY_STRING = ""
    const val HYPHEN = "-"
    const val BLANK_SPACE = " "
    const val CHAR_BLANK_SPACE = ' '
    const val COMMA = ","
    const val TEMP_COIN = "S/."

    const val CHILE = "CHILE"
    const val BOLIVIA = "BOLIVIA"
    const val COSTA_RICA = "COSTA RICA"
    const val EL_SALVADOR = "EL SALVADOR"
    const val GUATEMALA = "GUATEMALA"
    const val PANAMA = "PANAMÁ"
    const val PUERTO_RICO = "PUERTO RICO"
    const val VENEZUELA = "VENEZUELA"
    const val COLOMBIA = "COLOMBIA"
    const val PERU = "PERU"

    //PAISES UNETE
    const val COD_COLOMBIA = "CO"
    const val COD_CHILE = "CL"
    const val COD_PERU = "PE"
    const val COD_ECUADOR = "EC"
    const val COD_MEXICO = "MX"
    const val COD_SALVADOR = "SV"
    const val COD_PANAMA = "PA"
    const val COD_COSTA_RICA = "CR"
    const val COD_GUATEMALA = "GT"

    //PAISES NO UNETE
    const val COD_BOLIVIA = "BO"
    const val COD_DOMINICANA = "DO"
    const val COD_PUERTO_RICO = "PR"
    const val COD_VENEZUELA = "VE"

    @JvmField
    val PAISES_NO_UNETE: ArrayList<String> = object : ArrayList<String>() {
        init {
            add(COD_VENEZUELA)
        }
    }

    const val SLASH = "/"
    const val DIVIDE_SEPARATOR = "|"
    const val UNETE = "UNETE"
    const val D = "d"

    const val ELAPSED_TIME = 0
    const val ELAPSED_TIME_2 = 1000

    const val BROADCAST_REFRESH = "com.belcorp.ffvv.REFRESH"
    const val BROADCAST_REFRESH_UNETE = "com.belcorp.ffvv.REFRESH_UNETE"

    const val BROADCAST_REFRESH_TIME_SYNC = "com.belcorp.ffvv.REFRESH_TIME_SYNC"

    const val SOCIA_EMPRESARIA = "Socia Empresaria"
    const val LIDER = "Líder"

    const val VERSION_ERROR_MESSAGE = "versión"

    const val ACCOUNT_TYPE = "biz.belcorp.salesforce"

    const val ACCOUNT_NAME = "ffvv"

    const val AUTHTOKEN_TYPE_FULL_ACCESS = "Full access"

    const val AUTHORITY = "biz.belcorp.salesforce.provider"

    const val DEBE = "D"

    const val DEFAULT_DATE_FORMAT = "dd/MM/yyyy"
    const val DEFAULT_DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm:ss"

    //Preferences
    const val SINCRONIZACION_PREFERENCES = "SP_Sync_Time"
    const val USER_PREFERENCES = "UserPreferences"
    const val COD_USUARIO = "codUsuario"
    const val COD_CONSULTORA = "codConsultora"
    const val FIRST_NAME = "first_name"
    const val LAST_NAME = "last_name"
    const val SECOND_NAME = "second_name"
    const val ROL = "rol"
    const val COD_ROL = "cod_rol"
    const val PAIS = "pais"
    const val COD_PAIS = "cod_pais"
    const val REGION = "region"
    const val ZONA = "zona"
    const val SECCION = "seccion"
    const val CAMPANA = "campana"
    const val CAMPANA_RS = "campana_rs"
    const val PERIODO = "periodo"
    const val NOMBRE_CAMPANIA = "nombre_campaña"
    const val IS_LOGGED = "is_logged"
    const val SYNC = "sync"
    const val CUB = "cub"
    const val USERNAME = "username"
    const val START_INVOICE = "1"
    const val PASSWORD = "password"
    const val CountryIso = "countryIso"
    const val NIVEL = "nivel"
    const val EMAIL = "email"
    const val TELEFONO_MOVIL = "telefono_movil"
    const val TELEFONO_FIJO = "telefono_fijo"
    const val DOCUMENTO_IDENTIDAD = "documento_identidad"
    const val CONSULTORA_ID = "consultora_id"
    const val LATITUD = "latitud"
    const val LONGITUD = "longitud"

    @JvmField
    var CODIGO_TERRITORIO = "codTerritorio"

    @JvmField
    var SECCION_GESTION_LIDER = "seccionGestionLider"

    @JvmField
    var GEOREFERENCIA = "georeferencia"

    const val RDD_NOTIFICACION_CALIFICAR = 1


    const val POSITION_VENTA_NETA = 1
    const val POSITION_PEDIDOS = 2
    const val POSITION_ACTIVIDAD = 3
    const val POSITION_CAPITALIZACION = 4
    const val POSITION_GANACIA_SALDO = 5
    const val POSITION_COBRANZA = 6
    const val POSITION_POSIBLES_CONSECUTIVOS = 7
    const val POSITION_CICLO_NUEVAS = 8
    const val POSITION_PEG = 9
    const val POSITION_LANZAMIENTOS_ESTRATEGICOS =
        10 //public static final int POSITION_HERRAMIENTAS_DIGITALES = 10;
    const val POSITION_RUTA_DESARROLLO = 11
    const val POSITION_UNETE = 12
    const val POSITION_LEVEL = 13
    const val INDICATOR = "INDICATOR"

    const val ZERO = "0"
    const val ONE = "1"
    const val NUMBER_ONE = 1
    const val NUMBER_TWO = 2
    const val NUMBER_THREE = 3
    const val NUMBER_FOUR = 4
    const val NUMBER_FIVE = 5

    const val TWO_POINTS = ":"
    const val SPACING_WHITE = " "
    const val MAXIMUN_NUMBER_OF_CAMPAIGNS = 18
    const val MAXIMUN_NUMBER_OF_CAMPAIGNS_VE = 12
    const val ZERO_DOUBLE = "0.00"
    const val ZERO_NUMBER = 0
    const val ONE_NUMBER = 1

    const val NONE_LEVEL_NUMBER = -1

    //Suffix file
    const val PDF_SUFFIX = ".pdf"

    //Credit Consultant
    const val NOT_APT = "0" // non consults
    const val APT = "1" // no apt : APT = 2
    const val EMPTY_APT = "2"
    const val ALREADY_IS_CONSULTANT = "2"
    const val WITHDRAWAL = "Retirada"
    const val CONSULTANT_NON_CONSULT = 1
    const val CONSULTANT_APT = 2
    const val CONSULTANT_NON_APT = 3

    //Consultant Search
    const val SEPARATOR_DIGIT_VERIFICATOR = " -"

    //Development - Result
    const val CRITICAL = "1"
    const val SUCCESS = "1"
    const val NOT_SUCCESSFUL = "0"

    //Development - Level
    const val WORKSHOP_YES = "1"
    const val WORKSHOP_NOT = "0"

    const val CHANGE_LEVEL = "1"
    const val LOWER_LEVEL = "0"
    const val NONE_LEVEL = "-1"

    //Menu
    const val MAIN_MENU = "MP"
    const val FLOATING_MENU = "MF"

    //GZ Desarrollo - Nivel
    const val MOSTRAR_CUMPLIMIENTO_CAMPANIA = 1

    const val ACTIVO = 1

    const val INACTIVO = 0

    // DIAS COBRANZA
    const val COBRANZA_21 = 21
    const val COBRANZA_31 = 31

    // PEDIDO WEB
    const val ACTIVAR_BLOQUEO_PEDIDO = 1
    const val DESACTIVAR_BLOQUEO_PEDIDO = 0

    const val NUEVA_POSTULANTE = "3"
    const val NUEVA_CONSULTORA = "5"

    const val BROADCAST_VISIBILIDAD_MENU = "com.belcorp.ffvv.VISIBILIDAD_OCULTAR"
    const val MENU_MOSTRAR = 0
    const val MENU_OCULTAR = 1
    const val MENU_ACCION = "com.belcorp.ffvv.accion"
    const val NOTIFICACION = "com.belcorp.notificacion"
    const val TEXT_SMS_EMAIL = 1

    const val BROADCAST_NUEVOS_INDICADORES_RDD = "com.belcorp.ffvv.INDICADORES_RDD"
    const val INDICADOR_TOTAL_VISITADAS = "com.belcorp.ffvv.indicadores.visitadas"
    const val INDICADOR_TOTAL_PLANIFICADAS = "com.belcorp.ffvv.indicadores.planificadas"

    const val BROADCAST_POSTULANTES_SYNC_DOWNLOAD = "BROADCAST_POSTULANTES_SYNC_DOWNLOAD"

    @JvmField
    val BROADCAST_IR_A_UBICAR_PERSONA = "com.belcorp.ffvv.IR_A_UBICAR_PERSONA"

    const val BROADCAST_CAMBIO_PLANIFICACION_RDD = "com.belcorp.ffvv.RECALCULO_RDD"
    const val BROADCAST_ESTADO_PROGRESS = "com.belcorp.ffvv.ESTADO_PROGRESS"
    const val BROADCAST_EVENTO_EDITADO = "com.belcorp.ffvv.EVENTO_EDITADO"
    const val BROADCAST_CLICK_SECCION_RDD = "com.belcorp.ffvv.CLICK_SECCION_RDD"
    const val BROADCAST_REGISTRO_VISITA = "com.belcorp.ffvv.REGISTRO_VISITA"
    const val BROADCAST_CAMBIO_ACUERDOS = "com.belcorp.ffvv.CAMBIO_ACUERDOS"
    const val BROADCAST_PLANIFICAR_VISITA = "com.belcorp.ffvv.PLANIFICAR_VISITA"
    const val BROADCAST_CAMBIO_RECONOCIMIENTO = "com.belcorp.ffvv.CAMBIO_RECONOCIMIENTO"
    const val BROADCAST_RECARGAR_FOCOS = "com.belcorp.ffvv.RECARGAR_FOCOS"
    const val BROADCAST_RECARGAR_MIS_FOCOS = "com.belcorp.ffvv.RECARGAR_MIS_FOCOS"
    const val BROADCAST_RECARGAR_HABILIDADES = "com.belcorp.ffvv.RECARGAR_HABILIDADES"
    const val BROADCAST_RECARGAR_HABILIDADES_EQUIPO =
        "com.belcorp.ffvv.RECARGAR_HABILIDADES_ASIGNADAS"
    const val BROADCAST_RECARGAR_HABILIDADES_PROPIAS =
        "com.belcorp.ffvv.RECARGAR_HABILIDADES_PROPIAS"
    const val BROADCAST_ON_BACK = "com.belcorp.ffvv.BROADCAST_ON_BACK_SCREAM"
    const val BROADCAST_RECARGAR_CORREO = "com.belcorp.ffvv.BROADCAST_RECARGAR_CORREO"
    const val BROADCAST_RECARGAR_OTRO_TELEFONO = "com.belcorp.ffvv.BROADCAST_OTRO_TELEFONO"
    const val BROADCAST_CAMBIO_RECONOCIMIENTO_A_SUPERIOR =
        "com.belcorp.ffvv.CAMBIO_RECONOCIMIENTO_A_SUPERIOR"
    const val BROADCAST_IR_A_PERFIL = "com.belcorp.ffvvv.BROADCAST_VER_A_PERFIL"
    const val BROADCAST_IR_A_PERFIL_PARAM = "com.belcorp.ffvvv.BROADCAST_VER_PERFIL.PARAM"
    const val BROADCAST_IR_A_AGREGAR_EVENTO = "com.belcorp.ffvvv.BROADCAST_AGREGAR_EVENTO"
    const val BROADCAST_IR_A_AGREGAR_EVENTO_FECHA_PARAM =
        "com.belcorp.ffvvv.BROADCAST_AGREGAR_EVENTO_FECHA.PARAM"
    const val BROADCAST_IR_A_VER_DETALLE_EVENTO = "com.belcorp.ffvvv.BROADCAST_VER_DETALLE_EVENTO"
    const val BROADCAST_RECARGAR_VISTA_PLAN = "com.belcorp.ffvvv.BROADCAST_RECARGAR_VISTA_PLAN"
    const val BROADCAST_IR_A_VER_DETALLE_EVENTO_ID_PARAM =
        "com.belcorp.ffvvv.BROADCAST_VER_DETALLE_EVENTO_ID.PARAM"

    const val BROADCAST_NOTIFICACION = "com.belcorp.ffvv.NOTIFICACION"

    const val VENTA_NETA = "VENTA NETA"

    const val URL_ESIKA_APP_WEB = "https://www.somosbelcorp.com/EsikaConmigo.html"
    const val URL_LABEL_APP_WEB = "https://www.somosbelcorp.com/LBelConmigo.html"

    const val HABILIDADES_PERCENTAGE_LIMIT = 25
    const val DESCARGAR = "Descargar"
    const val VISUALIZAR = "Visualizar"
    const val INSERTAR = "Insertar"
    const val ACTUALIZAR = "Actualizar"
    const val ESTANDAR = "Estandar"
    const val PUSH_NOTIFICATION = "Push Notification"
    const val NOT_AVAILABLE = "(not available)"

    const val TYPE_PUSH_GESTOR_CONTENIDO = "GestorContenidosPush"
    const val NOTIFICACION_DATOS = "com.belcorp.notificacion.datos"

    const val DENSITY_LOW = 120
    const val DENSITY_MEDIUM = 160
    const val DENSITY_HIGH = 240
    const val DENSITY_XHIGH = 320
    const val DENSITY_XXHIGH = 480
    const val DENSITY_XXXHIGH = 640

    const val DELIMITER: String = "xhdpi"
    const val DEFAULT_DENSITY: String = "xxxhdpi"
    const val FILE_EXTENSION: String = "gif"

    //AvanceRegion

    const val EVENTO_SALIR = true
    const val EVENTO_VER_RUTA = false
    const val EVENTO_REGRESAR_RUTA = 2

    var EVENTO_VER_MAS_MENOS = 1
    var EVENTO_VER_MAS = true

    //Focos Campaña

    const val EVENTO_ASIGNAR_FOCOS = 0
    const val EVENTO_EDITAR_FOCOS = 1
    var CODIGO_UA = ""
    var GR_CODIGO_ROL = "GR"

    //Nombre Label - Tipo Campana

    const val EVENTO_MIS_FOCOS_CAMPANA = "FC"
    const val EVENTO_MIS_HABILIDADES = "FH"
    const val EVENTO_FOCOS_MI_EQUIPO = "FE"
    const val EVENTO_HANILIDADES_LIDERAZGO = "FL"
    const val TODOS: String = "TODOS"

    const val VALIDACION_CELULAR_POR_VALIDAR = 1
    const val VALIDACION_CELULAR_VALIDADO = 2
    const val VALIDACION_CELULAR_NO_REQUIERIDA = 3
    const val VALIDACION_CELULAR_ZERO = 0

    const val PIN_VALIDADO = 1
    const val PIN_NO_VALIDADO = -1
    const val PIN_ZERO = 0

    const val SOLICITUD_NO_CREADA = 0

    const val CONSULTORAS = "Consultoras"

    const val NOTIFICACION_VALIDACION = "2"

    const val TIPO_DOCUMENTO_DEFAULT = "1"

    const val BEAUTY_CONSULTANT_TO_BE = "para ser"
    const val ID_LIST_POSIBLE_CAMBIO_NIVEL = 62
    const val ID_LIST_CAMBIO_NIVEL = 63
    const val SIN_ZONA = -1
    const val SIN_BLOQUEO = -5
    const val SIN_RADIO_MAPA = -5

    const val VALIDACION_FOCO_SELECCIONADO = 0
    const val VALIDACION_CODIGO_SELECCIONADO = 1

    /** Cons Consultora**/

    const val RADIO_PLANETA = 6378f
    const val VER_MAS_DATOS_CONSULTORA = "Ver más Datos de consultora"
    const val AGREGAR_META = "Agregar meta"
    const val VER_COBRANZA_ESTADO_CUENTA = "Ver cobranza y estado de cuenta"
    const val AGREGAR_OTRAS_MARCAS = "Agregar otras marcas"
    const val GUARDAR_META = "Guardar Meta"
    const val LIMPIAR_MARCAS = "Limpiar"
    const val GUARDAR_MARCAS = "Guardar"
    const val CONSULTORAS_FACTURARON = "Consultoras facturaron"
    const val CONSULTORAS_NO_FACTURARON = "Consultoras aún no facturaron"
    const val VER_CONSULTORAS_CON_PEDIDO_SB = "Ver consultoras con pedido en SB"
    const val NO_VER_CONSULTORAS_CON_PEDIDO_SB = "No ver consultoras con pedido en SB"

    const val NUEVA_POSTULANTE_POR_APROBAR = "3"
    const val FINALIZADO_PREPOSTULANTE = "13"
    const val SI_PUEDE_SER_CONSULTORA = "2"

    const val NOT_IMPLEMENTED = "not implemented"

    const val EMAIL_NO_COINCIDE = "El correo no coincide"

    val providersEmail = arrayOf(
        "@gmail.com", "@outlook.com", "@hotmail.com",
        "@icloud.com", "@yahoo.com", "@aol.com", "@gmx.com", "@gmx.us", "@yandex.com",
        "@live.com", "@me.com"
    )

    const val URL = "URL"
    const val TITLE = "title"
    const val IP_UNICO = "IP Unico"

    const val CONDICION_CREDITICIA = 1
    const val CONDICION_TELEFONO = 2
    const val CONDICION_UBICACION = 3

    const val PASO_2 = 2

    const val DOT_SLIDE_ANIM_DURATION: Long = 150
    const val TAG_VIEW_PAGER_INDICATOR = "ViewPagerIndicator"
    const val LAST_KNOW_CURRENT_PAGE = -1
    const val LAST_KNOW_POSITION_OFFSET = -1f
    const val DOT_PADDING_DIP = 9

    val COUNTRIES_NEW_TITLE = arrayOf("MX")
}
