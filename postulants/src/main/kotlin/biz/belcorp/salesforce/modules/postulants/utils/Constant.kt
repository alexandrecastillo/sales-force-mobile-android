package biz.belcorp.salesforce.modules.postulants.utils

import biz.belcorp.salesforce.core.domain.entities.pais.Pais

object Constant {

    const val HYPHEN = "-"
    const val NUMERO_CERO = 0
    const val NUMERO_UNO = 1
    const val NUMERO_DOS = 2
    const val NUMERO_TRES = 3
    const val NUMERO_CUATRO = 4
    const val NUMERO_CINCO = 5
    const val NUMERO_SEIS = 6
    const val NUMERO_SIETE = 7
    const val NUMERO_OCHO = 8
    const val NUMERO_NUEVE = 9
    const val NUMERO_TRECE = 13
    const val NUMERO_DIECISEIS = 16
    const val UNO_NEGATIVO = -1
    const val DOS_NEGATIVO = -2
    const val EMPTY_STRING = ""
    const val STATUS_ENABLE = "1"
    const val NOT_IMPLEMENTED = "not implemented"
    const val ASTERISK = "*"
    const val EMAIL_NO_COINCIDE = "El correo no coincide"
    const val CHATBOT = "CHATBOT"

    const val NULL_STRING = "null"

    val providersEmail = arrayOf("@gmail.com", "@outlook.com", "@hotmail.com",
        "@icloud.com", "@yahoo.com", "@aol.com", "@gmx.com", "@gmx.us", "@yandex.com",
        "@live.com", "@me.com")

    const val GUION = "-"
    const val COMMA = ','
    const val DOT = '.'
    const val DIVIDE_SEPARATOR = "|"

    const val PREFIJO_CAMPANIA = "C"

    const val BLANK_SPACE = " "

    const val FORMAT_DIRECTION_PLACE = ", %s,"

    val DIAS_COBRANZAS = listOf(21)
    const val LIMITE_MAX_CAMPANIAS = 10

    const val NO_SE_PUDO_CREAR_ETIQUETA = "No se pudo crear Tag"

    const val RESULT_OK = 1

    const val PEDIDOS_ALTO_VALOR = 30
    const val PEDIDOS_BAJO_VALOR = 31

    const val NONE_LEVEL_NUMBER = -1

    const val RADIO_PLANETA = 6378f

    const val MASCULINO = "Masculino"
    const val FEMENINO = "Femenino"
    const val LETTER_F = "F"
    const val LETTER_M = "M"

    const val USER_PREFERENCES = "UserPreferences"
    const val COD_PAIS = "cod_pais"

    const val SIN_ZONA = -1
    const val SIN_BLOQUEO = -5
    const val TIPO_DOCUMENTO_DEFAULT = "1"

    const val INSERTAR = "Insertar"
    const val ACTUALIZAR = "Actualizar"

    const val PIN_VALIDADO = 1
    const val PIN_NO_VALIDADO = -1
    const val PIN_ZERO = 0

    const val SOLICITUD_NO_CREADA = 0
    const val UNETE = "UNETE"
    const val LINE_SPACE = "_"

    const val TEXT_IMAGES = "images"
    const val TEXT_UNETE_ = "UNETE_"
    const val FORMAT_PNG = ".png"

    const val QUALITY_IMAGE = 100
    const val DS_WIDTH_IMAGE = 800
    const val DS_HEIGHT_IMAGE = 600

    const val STORAGE_PERMISSION_GRANTED_CODE = 4000
    const val TEXT_SMS_EMAIL = 1

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

    const val URL_ESIKA_APP_WEB = "https://www.somosbelcorp.com/EsikaConmigo.html"
    const val URL_LABEL_APP_WEB = "https://www.somosbelcorp.com/LBelConmigo.html"

    const val NUEVA_POSTULANTE_POR_APROBAR = "3"
    const val FINALIZADO_PREPOSTULANTE = "13"
    const val SI_PUEDE_SER_CONSULTORA = "2"

    const val URL_TERMS_UNETE_SV =
        "https://s3.amazonaws.com/consultorasPRD/Autorizaci%C3%B3n%20Info%20Crediticia%20%28Belcorp%29%20-%20Belcorp%20SV%20VF-1.pdf"

    const val URL_TERMS_CO =
        "https://s3.amazonaws.com/consultorasPRD/Unete/T%26C_Unete_a_Belcorp-Unete2.0.pdf"

    val MTO_NAME_COUNTRIES = arrayOf("PE", "CO", "CL", "MX", "PA", "EC")
    val NEW_MESSAGE_CREDIT_EVALUATION = arrayOf(Pais.PERU.codigoIso)
    val LOAD_PDF_COUNTRIES = arrayOf("CL")
    val MTO_DISCLAIMER_APROBE_COUNTRIES = arrayOf("PE")

    const val THIRTY_SECONDS = 30L
    const val PREFIX_ZONE = "ZONA "
    const val INICIO_CONTRASENA_UNO = "1"
    const val INICIO_CONTRASENA_20000 = "20000"
}
