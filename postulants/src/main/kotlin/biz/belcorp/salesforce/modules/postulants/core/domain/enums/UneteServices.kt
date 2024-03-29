package biz.belcorp.salesforce.modules.postulants.core.domain.enums

enum class UneteServices(val id: String, val value: String) {
    LIST("1", "LISTADO POSTULANTE"),
    LISTPRE("2", "LISTADO PREPOSTULANTE"),
    GEOBUROCO("3", "GEO BURO"),
    UPDATEESTADO("4", "UPDATE ESTADO POSTULANTE"),
    UPDATEESTADOPRE("5", "UPDATE ESTADO PREPOSTULANTE"),
    VALIDARBLOQUEO("6", "VALIDAR BLOQUEO"),
    VALIDARBUROS("7", "VALIDAR BUROS"),
    OBTENERNOMBRECONSULTORA("8", "OBTENER NOMBRE CONSULTORA"),
    GEO("9", "GEO"),
    CREARPOSTULANTE("10", "CREAR POSTULANTE"),
    CREARPOSTULANTES("11", "CREAR POSTULANTES"),
    UPDATEPOSTULANTE("12", "UPDATE POSTULANTE"),
    UPDATEPRE("13", "UPDATE PREPOSTULANTE"),
    UPDATEPOSTULANTES("14", "UPDATE POSTULANTES"),
    MENSAJEDEVUELTOSAC("15", "MENSAJE DEVUELTOS SAC"),
    VALIDARMAIL("16", "VALIDAR MAIL"),
    VALIDARCELULAR("17", "VALIDAR CELULAR"),
    ENVIARSMS("18", "ENVIAR SMS"),
    BLOQUEOMXPASO2("19", "BLOQUEO MX PASO 2"),
    ELIMINARPOSTULANTE("20", "ELIMINAR POSTULANTE"),
    ELIMINARPREPOSTULANTE("21", "ELIMINAR PREPOSTULANTE"),
    POSTULANTEAPTA("22", "POSTULANTE APTA"),
    ESTADOTELEFONICOZONA("23", "ESTADO TELEFONICO ZONA"),
    VALIDARPIN("24", "VALIDAR PIN"),
}
