package biz.belcorp.salesforce.core.domain.entities.analytics

import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.domain.entities.session.Sesion
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.domain.entities.analytics.TagAnalytics.*
import biz.belcorp.salesforce.core.domain.exceptions.AnalyticsEventException
import biz.belcorp.salesforce.core.utils.esHijoDirectoDe

object TagAnalyticsHelper {

    const val RDD = "Rutas de desarrollo"
    const val TAG_ANALYTICS = "TAG_ANALYTICS"
    const val MI_RUTA = "Mi ruta de desarrollo"
    private const val LISTADO = "Lista"
    private const val ACOMPANIAMIENTO = "Acompañamiento"
    private const val RUTA_PERFIL = "Perfil"
    private const val RUTA_PERFIL_META = "Meta"
    private const val RUTA_PERFIL_MARCAS = "Marcas"
    private const val DATOS = "Perfil"
    private const val MAPA = "Mapa"
    private const val PLANIFICAR = "Planificar visita"
    private const val RE_PLANIFICAR = "Replanificar"
    private const val ELIMINAR_CALENDARIO = "Elimina tu calendario"
    private const val META = "Meta"
    private const val MARCAS = "Marcas"
    private const val NO_FACTURARON = "No facturaron"
    private const val ACUERDOS = "Acuerdos"
    private const val CHANGE_LEVEL = "Cambio de Nivel"
    private const val LISTA = "Lista"
    const val FOCOS_DE_CAMPANIA = "Focos de campaña"
    const val FOCOS_PARA_TU_EQUIPO = "Focos para tu equipo"
    private const val ACTION_SELECT_TAB = "Seleccionar Tab"
    private const val CONSULTORA_VENTA = "Venta"
    private const val CONSULTORA_MAS_VENDIDO = "Lo más vendido"
    private const val CONSULTORA_DIGITAL = "Digital"
    private const val CONSULTORA_ACUERDOS = "Acuerdos"
    private const val LABEL_GANANCIA_Y_SALDOS = "Ganancia y Saldos"
    private const val LABEL_CALCULA_TU_GANANCIA = "Calcula tu ganancia"
    private const val LABEL_POSIBLE_GANANCIA = "Posible Ganancia"
    private const val ACOMPANIAMIENTO_VENTA_Y_GANANCIA = "Venta y Ganancia"
    private const val ACOMPANIAMIENTO_DIGITAL_C = "Digital"
    private const val AH_ACOMPANIAMIENTO_CAMINO_BRILLANTE = "Camino Brillante"
    private const val AH_ACOMPANIAMIENTO_NOVEDADES = "Novedades"
    private const val AH_ACOMPANIAMIENTO_PROGRAMA_NUEVAS = "Programa de Nuevas"
    private const val AH_ACOMPANIAMIENTO_CONCURSOS = "Concursos"
    private const val ACTION_TIPS_DESARROLLO = "Tips de Desarrollo"
    private const val ACTION_TIPS_DESARROLLO_VENTA_Y_GANANCIA = "Venta y Ganancia"
    private const val ACTION_TIPS_DESARROLLO_DIGITAL = "Digital"
    private const val ACTION_TIPS_DESARROLLO_CAMINO_BRILLANTE = "Camino Brillante"
    private const val ACTION_TIPS_DESARROLLO_NOVEDADES = "Novedades"
    private const val ACTION_TIPS_DESARROLLO_PROGRAMA_NUEVAS = "Programa de Nuevas"
    private const val SEPARADOR = "|"
    private const val COBRANZA = "Datos de la cobranza"
    private const val CONTACTO = "Datos de Contacto"
    private const val ESTADO_CUENTA = "Estado de cuenta"
    private const val MIS_ANOTACIONES = "Mis anotaciones"
    private const val METAS_CONSULTORA = "Metas de la consultora"
    private const val AVANCE_REGION = "AvanceRegion"
    private const val AVANCE_EN = "Avance en"
    private const val AVANCE = "Avance"
    private const val PANTALLA_INICIO = "Pantalla de Inicio"
    private const val CATEGORY_MENU_INFERIOR = "Menú inferior"
    private const val CATEGORY_MENU_BLOQUES = "Menú bloques"
    private const val ACTION_CLICK_ELEMENTO = "Clic elemento"
    private const val ACTION_CLICK_TAB = "Clic en Tab"
    private const val ACTION_REGISTRAR_VISITA = "Registrar Visita"
    private const val ACTION_COMPORTAMIENTO = "Comportamiento"
    const val ACTION_FOCOS_CAMPANIA = "Focos de Campaña"
    private const val ACTION_VER_LISTA = "Ver Lista"
    private const val ACTION_NO_FACTURARON = "No Facturaron - switch"
    private const val ACTION_ACUERDOS_CUMPLIDOS = "Acuerdos – Click botón"

    /**ROL -> CONSULTORA**/
    private const val ROL_CONSULTORA = "Consultora"

    private const val ACTION_CLICK_BOTON = "Click botón"
    private const val ACTION_VER_SECCION = "Ver Sección"
    private const val ACTION_POP_UP_MARCAS = "Pop up marcas – Click botón"
    private const val ACTION_VENDE_OTRA_MARCA = "Vende Otra marca"
    private const val ACTION_NO_VENDE_OTRA_MARCA = "No vende Otra marca"

    //LABEL
    private const val TAB = "Tab"
    private const val PERFIL = "Perfil"
    private const val GUARDAR = "Guardar"
    private const val FINALIZAR = "Finalizar"
    private const val VER_MAS = "Ver más"
    private const val COMPORTAMIENTOS_OBSERVADOS = "Comportamientos Observados"

    private const val EVENTO_ASIGNAR = "Asignar"
    private const val EVENTO_EDITAR = "Editar"

    private const val LABEL_COMPARTIR_VIDEO = "Compartir video"
    private const val LABEL_GUARDAR_ACUERDO = "Guardar acuerdo"
    private const val LABEL_RECONOCER = "Reconocer"
    private const val LABEL_HABILIDADES_EQUIPO = "Habilidades_equipo: "
    private const val LABEL_FOCOS_EQUIPO = "Focos_equipo: "
    private const val LABEL_ACUERDO_CUMPLIDO = "Acuerdo cumplido"
    private const val LABEL_REESTABLECER_ACUERDO = "Reestablecer Acuerdo"

    private const val LABEL_CALCULADORA_NIVEL_ACTUAL = "Nivel actual"
    private const val LABEL_CALCULADORA_OTRO_NIVEL = "Otro Nivel"
    private const val LABEL_CALCULADORA_CALCULAR = "Calcular"
    private const val LABEL_CALCULADORA_GUARDAR_GANANCIA = "Guardar Ganancia"
    private const val LABEL_CALCULADORA_NUEVO_CALCULO = "Nuevo Calculo"

    /** Cons Consultora**/

    private const val TIPO_POP_UP_MARCAS = 0
    private const val TIPO_VENDE_OTRA_MARCA = 1
    private const val TIPO_NO_VENDE_OTRA_MARCA = 2

    private const val ACTION_SECTION_ENTRY = "Ingresar a sección"

    private const val INSPIRA_PROGRAMA = "Programa Inspira"

    /**pantallas*/

    private fun miRuta(): String? {
        return "$RDD $SEPARADOR $MI_RUTA $SEPARADOR $LISTADO"
    }

    private fun dashBoard(): String? {
        return RDD
    }

    private fun miMapa(): String? {
        return "$RDD $SEPARADOR $MI_RUTA $SEPARADOR $MAPA"
    }

    private fun acompaniamiento(rolPersona: Rol?): String? {
        return "$RDD $SEPARADOR ${rolPersona?.comoTextoTag()} $SEPARADOR $ACOMPANIAMIENTO"
    }

    private fun perfil(rolPersona: Rol?): String? {
        return "$RDD $SEPARADOR ${rolPersona?.comoTextoTag()} $SEPARADOR $RUTA_PERFIL"
    }

    private fun meta(rolPersona: Rol?): String? {
        return "$RDD $SEPARADOR ${rolPersona?.comoTextoTag()} $SEPARADOR $RUTA_PERFIL $SEPARADOR $RUTA_PERFIL_META"
    }

    private fun marcas(rolPersona: Rol?): String? {
        return "$RDD $SEPARADOR ${rolPersona?.comoTextoTag()} $SEPARADOR $RUTA_PERFIL_MARCAS"
    }

    private fun ventas(rolPersona: Rol?): String? {
        return "$RDD $SEPARADOR ${rolPersona?.comoTextoTag()} $SEPARADOR $CONSULTORA_VENTA"
    }

    private fun masVendido(rolPersona: Rol?): String? {
        return "$RDD $SEPARADOR ${rolPersona?.comoTextoTag()} $SEPARADOR $CONSULTORA_MAS_VENDIDO"
    }

    private fun digital(rolPersona: Rol?): String? {
        return "$RDD $SEPARADOR ${rolPersona?.comoTextoTag()} $SEPARADOR $CONSULTORA_DIGITAL"
    }

    private fun acuerdos(rolPersona: Rol?): String? {
        return "$RDD $SEPARADOR ${rolPersona?.comoTextoTag()} $SEPARADOR $CONSULTORA_ACUERDOS"
    }

    private fun ventaYganancia(): String? {
        return "$RDD $SEPARADOR $ACOMPANIAMIENTO $SEPARADOR $ACOMPANIAMIENTO_VENTA_Y_GANANCIA"
    }

    private fun acompaniamientoDigital(): String? {
        return "$RDD $SEPARADOR $ACOMPANIAMIENTO $SEPARADOR $ACOMPANIAMIENTO_DIGITAL_C"
    }

    private fun caminoBrillante(): String? {
        return "$RDD $SEPARADOR $ACOMPANIAMIENTO $SEPARADOR $AH_ACOMPANIAMIENTO_CAMINO_BRILLANTE"
    }

    private fun acompaniamientoNovedades(): String? {
        return "$RDD $SEPARADOR $ACOMPANIAMIENTO $SEPARADOR $AH_ACOMPANIAMIENTO_NOVEDADES"
    }

    private fun concursos(): String? {
        return "$RDD $SEPARADOR $ACOMPANIAMIENTO $SEPARADOR $AH_ACOMPANIAMIENTO_CONCURSOS"
    }

    private fun programaNuevas(): String? {
        return "$RDD $SEPARADOR $ACOMPANIAMIENTO $SEPARADOR $AH_ACOMPANIAMIENTO_PROGRAMA_NUEVAS"
    }

    private fun datos(rolPersona: Rol?): String? {
        return "$RDD $SEPARADOR ${rolPersona?.comoTextoTag()} $SEPARADOR $DATOS"
    }

    private fun planificar(): String? {
        return "$RDD $SEPARADOR $MI_RUTA $SEPARADOR $PLANIFICAR"
    }

    private fun rePlanificar(): String? {
        return "$RDD $SEPARADOR $MI_RUTA $SEPARADOR $RE_PLANIFICAR"
    }

    private fun eliminarCalendario(): String? {
        return "$RDD $SEPARADOR $MI_RUTA $SEPARADOR $ELIMINAR_CALENDARIO"
    }

    fun avanceRegionScreenName(rolPersona: Rol): String? {
        return "$RDD $SEPARADOR $AVANCE_REGION $SEPARADOR ${rolPersona.comoTextoTag()} $SEPARADOR $AVANCE_EN ${rolPersona.nameAsCode()}"
    }

    fun avanceRegionVerMasMenos(rolPersona: Rol): String? {
        return "$RDD $SEPARADOR $AVANCE_REGION $SEPARADOR ${rolPersona.comoTextoTag()} $SEPARADOR $AVANCE${rolPersona.nameAsCode()} $SEPARADOR $LISTADO"
    }

    fun focosDeCampana(focos: List<String?>): String {
        var cadena = Constant.EMPTY_STRING
        var cont = 0
        for (item in focos) {
            cont += 1
            cadena = if (cont == 1) {
                cadena.plus(item)
            } else {
                cadena.plus(SEPARADOR + item)
            }
        }
        return cadena
    }

    /**eventos*/

    private fun eventoMenuRdd(): EventoModel {
        return EventoModel(
            category = CATEGORY_MENU_INFERIOR,
            action = ACTION_CLICK_ELEMENTO,
            label = RDD,
            screenName = PANTALLA_INICIO
        )
    }

    private fun eventoHomeRdd(): EventoModel {
        return EventoModel(
            category = CATEGORY_MENU_BLOQUES,
            action = ACTION_CLICK_ELEMENTO,
            label = RDD,
            screenName = PANTALLA_INICIO
        )
    }

    private fun eventoPerfilDatos(): EventoModel {
        return EventoModel(
            category = RDD,
            action = "$ROL_CONSULTORA – $ACTION_CLICK_TAB",
            label = DATOS,
            screenName = "$RDD $SEPARADOR $ROL_CONSULTORA $SEPARADOR $ACOMPANIAMIENTO"
        )
    }

    private fun eventoClickGuardarAcuerdo(): EventoModel {
        return EventoModel(
            category = RDD,
            action = "$MI_RUTA – $ACTION_REGISTRAR_VISITA",
            label = GUARDAR,
            screenName = "$RDD $SEPARADOR $ACOMPANIAMIENTO $SEPARADOR $ACTION_REGISTRAR_VISITA"
        )
    }

    private fun eventoFinalizarVisita(): EventoModel {
        return EventoModel(
            category = RDD,
            action = "$MI_RUTA - $ACTION_REGISTRAR_VISITA",
            label = FINALIZAR,
            screenName = "$RDD $SEPARADOR $ACOMPANIAMIENTO $SEPARADOR $ACTION_REGISTRAR_VISITA"
        )
    }

    private fun eventoCobranza(): EventoModel {
        return EventoModel(
            category = RDD,
            action = "$MI_RUTA - $ROL_CONSULTORA",
            label = "$TAB - $COBRANZA",
            screenName = "$RDD $SEPARADOR $ROL_CONSULTORA $SEPARADOR $PERFIL"
        )
    }

    private fun eventoContacto(): EventoModel {
        return EventoModel(
            category = RDD,
            action = "$MI_RUTA - $ROL_CONSULTORA",
            label = "$TAB - $CONTACTO",
            screenName = "$RDD $SEPARADOR $ROL_CONSULTORA $SEPARADOR $PERFIL"
        )
    }

    private fun eventoEstadoCuenta(): EventoModel {
        return EventoModel(
            category = RDD,
            action = "$MI_RUTA - $ROL_CONSULTORA",
            label = "$TAB - $ESTADO_CUENTA",
            screenName = "$RDD $SEPARADOR $ROL_CONSULTORA $SEPARADOR $PERFIL"
        )
    }

    private fun eventoMisAnotaciones(): EventoModel {
        return EventoModel(
            category = RDD,
            action = "$MI_RUTA - $ROL_CONSULTORA",
            label = "$TAB - $MIS_ANOTACIONES",
            screenName = "$RDD $SEPARADOR $ROL_CONSULTORA $SEPARADOR $PERFIL"
        )
    }

    private fun eventoMetasConsultora(): EventoModel {
        return EventoModel(
            category = RDD,
            action = "$MI_RUTA - $ROL_CONSULTORA",
            label = "$TAB - $METAS_CONSULTORA",
            screenName = "$RDD $SEPARADOR $ROL_CONSULTORA $SEPARADOR $PERFIL"
        )
    }

    private fun eventoVerMas(): EventoModel {
        return EventoModel(
            category = RDD,
            action = MI_RUTA,
            label = VER_MAS,
            screenName = "$MI_RUTA $SEPARADOR $VER_MAS"
        )
    }

    private fun eventoCompartirVideo(rol: Rol): EventoModel {
        return EventoModel(
            category = RDD,
            action = "$MI_RUTA - $ROL_CONSULTORA",
            label = LABEL_COMPARTIR_VIDEO,
            screenName = "$RDD $SEPARADOR ${rol.codigoRol} $SEPARADOR $ACOMPANIAMIENTO"
        )
    }

    private fun eventoCrearAcuerdo(): EventoModel {
        return EventoModel(
            category = RDD,
            action = "$MI_RUTA - $ACTION_REGISTRAR_VISITA",
            label = LABEL_GUARDAR_ACUERDO,
            screenName = "$RDD $SEPARADOR $ACOMPANIAMIENTO $SEPARADOR $ACTION_REGISTRAR_VISITA"
        )
    }

    private fun eventoReconocer(): EventoModel {
        return EventoModel(
            category = RDD,
            action = ACTION_COMPORTAMIENTO,
            label = LABEL_RECONOCER,
            screenName = "$RDD $SEPARADOR $COMPORTAMIENTOS_OBSERVADOS"
        )
    }

    private fun eventoReconocerHabilidades(): EventoModel {
        return EventoModel(
            category = RDD,
            action = "Habilidades de liderazgo",
            label = "Reconocer",
            screenName = "$RDD | Habilidades de Liderazgo"
        )
    }

    private fun eventoPlanificadas(): EventoModel {
        return EventoModel(
            category = RDD,
            action = "$MI_RUTA – $ACTION_CLICK_TAB",
            label = "Planificadas",
            screenName = "$RDD | $MI_RUTA | $LISTA"
        )
    }

    private fun eventoNoPlanificadas(): EventoModel {
        return EventoModel(
            category = RDD,
            action = "$MI_RUTA – $ACTION_CLICK_TAB",
            label = "No planificadas",
            screenName = "$RDD | $MI_RUTA | $LISTA"
        )
    }

    private fun pantallaCalculadoraCalculaTuGanancia(): String? = "$LABEL_GANANCIA_Y_SALDOS $SEPARADOR $LABEL_CALCULA_TU_GANANCIA"

    private fun pantallaCalculadoraResultado(): String? = "$LABEL_GANANCIA_Y_SALDOS $SEPARADOR $LABEL_CALCULA_TU_GANANCIA $SEPARADOR $LABEL_POSIBLE_GANANCIA"

    fun construirNombrePantallaCalculadora(tagAnalytics: TagAnalytics): String? {
        return when (tagAnalytics) {
            PANTALLA_CALCULADORA_CALCULA_TU_GANANCIA -> pantallaCalculadoraCalculaTuGanancia()
            PANTALLA_CALCULADORA_RESULTADO -> pantallaCalculadoraResultado()
            else -> throw AnalyticsEventException()
        }
    }

    suspend fun construirNombrePantallaInspira(tagAnalytics: TagAnalytics): String? {
        return when (tagAnalytics) {
            EVENTO_INSPIRA_SCREEN_VISTA -> Constant.SCREEN_VISTA_HOME_INSPIRA
            EVENTO_INSPIRA_SCREEN_VISTA_MI_AVANCE -> Constant.SCREEN_VISTA_MI_AVANCE
            EVENTO_INSPIRA_SCREEN_VISTA_CONDICIONES -> Constant.SCREEN_VISTA_CONDICIONES
            EVENTO_INSPIRA_SCREEN_VISTA_RANKING -> Constant.SCREEN_VISTA_RANKING
            else -> throw AnalyticsEventException()
        }
    }

    fun construirEventoCalculadora(tagAnalytics: TagAnalytics): EventoModel {
        return when (tagAnalytics) {
            EVENTO_CALCULADORA_NIVEL_ACTUAL -> eventoCalculadoraNivelActual()
            EVENTO_CALCULADORA_OTRO_NIVEL -> eventoCalculadoraOtroNivel()
            EVENTO_CALCULADORA_CALCULAR -> eventoCalculadoraCalcular()
            EVENTO_CALCULADORA_GUARDAR_GANANCIA -> eventoCalculadoraGuardarGanancia()
            EVENTO_CALCULADORA_NUEVO_CALCULO -> eventoCalculadoraNuevoCalculo()
            else -> throw AnalyticsEventException()
        }
    }

    private fun eventoCalculadoraNivelActual(): EventoModel {
        return EventoModel(category = LABEL_GANANCIA_Y_SALDOS,
            action = ACTION_SELECT_TAB,
            label = LABEL_CALCULADORA_NIVEL_ACTUAL,
            screenName = "$LABEL_GANANCIA_Y_SALDOS $SEPARADOR $LABEL_CALCULA_TU_GANANCIA")
    }

    private fun eventoCalculadoraOtroNivel(): EventoModel {
        return EventoModel(category = LABEL_GANANCIA_Y_SALDOS,
            action = ACTION_SELECT_TAB,
            label = LABEL_CALCULADORA_OTRO_NIVEL,
            screenName = "$LABEL_GANANCIA_Y_SALDOS $SEPARADOR $LABEL_CALCULA_TU_GANANCIA")
    }

    private fun eventoCalculadoraCalcular(): EventoModel {
        return EventoModel(category = LABEL_GANANCIA_Y_SALDOS,
            action = ACTION_CLICK_BOTON,
            label = LABEL_CALCULADORA_CALCULAR,
            screenName = "$LABEL_GANANCIA_Y_SALDOS $SEPARADOR $LABEL_CALCULA_TU_GANANCIA")
    }

    private fun eventoCalculadoraGuardarGanancia(): EventoModel {
        return EventoModel(category = LABEL_GANANCIA_Y_SALDOS,
            action = ACTION_CLICK_BOTON,
            label = LABEL_CALCULADORA_GUARDAR_GANANCIA,
            screenName = "$LABEL_GANANCIA_Y_SALDOS $SEPARADOR $LABEL_CALCULA_TU_GANANCIA $SEPARADOR $LABEL_POSIBLE_GANANCIA")
    }

    private fun eventoCalculadoraNuevoCalculo(): EventoModel {
        return EventoModel(category = LABEL_GANANCIA_Y_SALDOS,
            action = ACTION_CLICK_BOTON,
            label = LABEL_CALCULADORA_NUEVO_CALCULO,
            screenName = "$LABEL_GANANCIA_Y_SALDOS $SEPARADOR $LABEL_CALCULA_TU_GANANCIA $SEPARADOR $LABEL_POSIBLE_GANANCIA")
    }

    private fun eventoBuscar(): EventoModel {
        return EventoModel(
            category = RDD,
            action = "$MI_RUTA – $ACTION_CLICK_TAB",
            label = "Buscar",
            screenName = "$RDD | $MI_RUTA | $LISTA"
        )
    }

    private fun eventoSwitchMapa(): EventoModel {
        return EventoModel(
            category = RDD,
            action = "$MI_RUTA – switch",
            label = "Mapa",
            screenName = "$RDD | $MI_RUTA | $LISTA"
        )
    }

    private fun eventoSwitchLista(): EventoModel {
        return EventoModel(
            category = RDD,
            action = "$MI_RUTA – switch",
            label = LISTA,
            screenName = "$RDD | $MI_RUTA | $MAPA"
        )
    }

    private fun eventMarcadorMapa(codigoPersona: String): EventoModel {
        return EventoModel(
            category = RDD,
            action = "$MI_RUTA – $MAPA",
            label = "Seleccionar consultora – $codigoPersona}",
            screenName = "$RDD | $MI_RUTA | $MAPA"
        )
    }

    private fun eventoSwitchActivarMapa(): EventoModel {
        return EventoModel(
            category = RDD,
            action = "$MI_RUTA – $MAPA",
            label = "Switch",
            screenName = "$RDD | $MI_RUTA | $MAPA"
        )
    }

    private fun eventoReplanificarVisita(): EventoModel {
        return EventoModel(
            category = RDD,
            action = "$MI_RUTA – RePlanificar visita",
            label = "Guardar",
            screenName = "$RDD | $MI_RUTA | Replanificar visita"
        )
    }

    private fun eventoReplanificarAceptarVisita(): EventoModel {
        return EventoModel(
            category = RDD,
            action = "$MI_RUTA – RePlanificar visita",
            label = "Ok entendido",
            screenName = "$RDD | $MI_RUTA | Replanificar visita"
        )
    }

    private fun eventoPlanificarVisita(): EventoModel {
        return EventoModel(
            category = RDD,
            action = "$MI_RUTA – Planificar visita",
            label = "Guardar",
            screenName = "$RDD | $MI_RUTA | Planificar visita"
        )
    }

    private fun eventoPlanificarAceptarVisita(): EventoModel {
        return EventoModel(
            category = RDD,
            action = "$MI_RUTA – Planificar visita",
            label = "Aceptar",
            screenName = "$RDD | $MI_RUTA | Planificar visita"
        )
    }

    private fun eventoRegistrarVisita(): EventoModel {
        return EventoModel(
            category = RDD,
            action = "$MI_RUTA – Consultora",
            label = "Registrar Visita",
            screenName = "$RDD | Consultora | $ACOMPANIAMIENTO"
        )
    }

    private fun eventoPlanificarAcompaniamientoVisita(rolSesion: Rol, rol: Rol): EventoModel? {
        var result: EventoModel? = null
        rol.esHijoDirectoDe(rolSesion).takeIf { it }?.let {
            result = EventoModel(
                category = RDD,
                action = "$MI_RUTA - $rol",
                label = "Planificar",
                screenName = "$RDD | Consultora | $ACOMPANIAMIENTO"
            )
        }
        return result
    }

    private fun eventoRePlanificarAcompaniamientoVisita(rolSesion: Rol, rol: Rol): EventoModel? {
        var result: EventoModel? = null
        rol.esHijoDirectoDe(rolSesion).takeIf { it }?.let {
            result = EventoModel(
                category = RDD,
                action = "$MI_RUTA - $rol",
                label = "Replanificar",
                screenName = "$RDD | Consultora | $ACOMPANIAMIENTO"
            )
        }
        return result
    }

    private fun eventoVerMiRuta(): EventoModel {
        return EventoModel(
            category = RDD,
            action = ACTION_SECTION_ENTRY,
            label = "Ver mi ruta",
            screenName = "Rutas de desarrollo"
        )
    }

    private fun eventoMenuRddLlamada(): EventoModel {
        return EventoModel(
            category = RDD,
            action = ACTION_SECTION_ENTRY,
            label = "Contactar consultora - Teléfono",
            screenName = "$RDD | $MI_RUTA | $LISTA"
        )
    }

    private fun eventoMenuRddWhatsap(): EventoModel {
        return EventoModel(
            category = RDD,
            action = ACTION_SECTION_ENTRY,
            label = "Contactar consultora - Whatsapp",
            screenName = "$RDD | $MI_RUTA | $LISTA"
        )
    }

    private fun eventoMenuRddSMS(): EventoModel {
        return EventoModel(
            category = RDD,
            action = "$MI_RUTA – Ver más",
            label = "Contactar consultora - SMS",
            screenName = "$RDD | $MI_RUTA | $LISTA"
        )
    }

    private fun eventoMenuRddEliminar(): EventoModel {
        return EventoModel(
            category = RDD,
            action = "$MI_RUTA – Ver más",
            label = "Elimínala de tu calendario",
            screenName = "$RDD | $MI_RUTA | $LISTA"
        )
    }

    private fun eventoMenuRddReplanificar(): EventoModel {
        return EventoModel(
            category = RDD,
            action = "$MI_RUTA – Ver más",
            label = "Replanifica",
            screenName = "$RDD | $MI_RUTA | $LISTA"
        )
    }

    private fun eventoMenuRddVisitar(): EventoModel {
        return EventoModel(
            category = RDD,
            action = "$MI_RUTA – Ver más",
            label = "Registra tu visita",
            screenName = "$RDD | $MI_RUTA | $LISTA"
        )
    }

    private fun eventoMenuRddMapa(): EventoModel {
        return EventoModel(
            category = RDD,
            action = "$MI_RUTA – Ver más",
            label = "Ubicar su dirección en el mapa",
            screenName = "$RDD | $MI_RUTA | $LISTA"
        )
    }

    private fun eventoModalElimiarPlanifica(): EventoModel {
        return EventoModel(
            category = RDD,
            action = "$MI_RUTA - Calendario",
            label = "Eliminar",
            screenName = "$RDD | $MI_RUTA | Elimina tu calendario"
        )
    }

    private fun eventoModalAceptarEliminar(): EventoModel {
        return EventoModel(
            category = RDD,
            action = "$MI_RUTA - Calendario",
            label = "Ok entendido",
            screenName = "$RDD | $MI_RUTA | Elimina tu calendario"
        )
    }

    private fun eventoClickMas(): EventoModel {
        return EventoModel(
            category = "Menú inferior",
            action = ACTION_CLICK_ELEMENTO,
            label = "Mas opciones",
            screenName = Constant.EMPTY_STRING
        )
    }

    private fun eventoClickMaterialDesarrollo(): EventoModel {
        return EventoModel(
            category = "Menú Desplegable - Más opciones",
            action = ACTION_CLICK_BOTON,
            label = "Materiales Desarrollo",
            screenName = Constant.EMPTY_STRING
        )
    }

    private fun eventoClickMDVisualizarODescargar(
        nombreBoton: String,
        nombreDocumento: String
    ): EventoModel {
        return EventoModel(
            category = "Materiales de Desarrollo",
            action = nombreBoton,
            label = nombreDocumento,
            screenName = Constant.EMPTY_STRING
        )
    }

    private fun eventoAvanceDeRegion(): EventoModel {
        return EventoModel(
            category = RDD,
            action = "$MI_RUTA – $ACTION_CLICK_TAB",
            label = "Avance de Región",
            screenName = "$RDD | Avance de Región"
        )
    }

    private fun eventoFocosDeCampania(): EventoModel {
        return EventoModel(
            category = RDD,
            action = "$MI_RUTA – $ACTION_CLICK_TAB",
            label = "Focos de campaña",
            screenName = "$RDD | Avance de Region"
        )
    }



    private fun eventoAvancePorCodigo(codigo: String): EventoModel {
        return EventoModel(
            category = RDD,
            action = "$MI_RUTA – Avance de Región",
            label = " Avance de GZ : Zona: $codigo",
            screenName = "$RDD | Avance de Región"
        )
    }



    fun eventoRegresarAMiRuta(rol: Rol): EventoModel {
        return EventoModel(
            category = RDD,
            action = "Regresar a mi Ruta de desarrollo",
            label = "(not available)",
            screenName = "$RDD | AvanceRegión | ${rol.codigoRol} | Avance Sección | Lista"
        )
    }

    private fun eventoMiFocoCampania(tipo: Int): EventoModel {
        return EventoModel(
            category = RDD,
            action = "$MI_RUTA – $ACTION_FOCOS_CAMPANIA",
            label = "Mis_focos: " + portipoEvento(tipo),
            screenName = "$RDD | $FOCOS_DE_CAMPANIA"
        )
    }

    fun eventoFocoCampaniaEditar(focos: List<String?>): EventoModel {
        return EventoModel(
            category = RDD,
            action = "$MI_RUTA – $ACTION_FOCOS_CAMPANIA",
            label = "Selecciona_focos: " + focosDeCampana(focos),
            screenName = "$RDD | $FOCOS_DE_CAMPANIA | Mis focos de campañas"
        )
    }

    //mis habilidades
    private fun eventoMisHabilidades(tipo: Int): EventoModel {
        return EventoModel(
            category = RDD,
            action = "$MI_RUTA – $ACTION_FOCOS_CAMPANIA",
            label = "Mis Habilidades de Liderazgo: " + portipoEvento(tipo),
            screenName = "$RDD | $FOCOS_DE_CAMPANIA"
        )
    }

    fun eventoMisHabilidadesEditar(misHabilidades: List<String?>): EventoModel {
        return EventoModel(
            category = RDD,
            action = "$MI_RUTA – $ACTION_FOCOS_CAMPANIA",
            label = "Selecciona tus HDL: " + focosDeCampana(misHabilidades),
            screenName = "$RDD | $FOCOS_DE_CAMPANIA | Mis Habilidades de Liderazgo"
        )
    }

    fun eventoAsignarMiEquipoMultiple(): EventoModel {
        return EventoModel(
            category = RDD,
            action = "$MI_RUTA – $ACTION_FOCOS_CAMPANIA",
            label = "Focos_equipo_multiple: Asignar",
            screenName = "$RDD | $FOCOS_DE_CAMPANIA"
        )
    }

    fun eventoEditarAsignarMiEquipo(codigoua: String, tipo: Int): EventoModel {
        return EventoModel(
            category = RDD,
            action = "$MI_RUTA – $ACTION_FOCOS_CAMPANIA",
            label = LABEL_FOCOS_EQUIPO + codigoua + " : " + portipoEvento(tipo),
            screenName = "$RDD $SEPARADOR $ACTION_FOCOS_CAMPANIA"
        )
    }

    fun eventoEditarAsignarHabilidadesMiEquipo(codigoua: String, tipo: Int): EventoModel {
        return EventoModel(
            category = RDD,
            action = "$MI_RUTA – $ACTION_FOCOS_CAMPANIA",
            label = LABEL_HABILIDADES_EQUIPO + codigoua + " : " + portipoEvento(tipo),
            screenName = "$RDD $SEPARADOR $ACTION_FOCOS_CAMPANIA"
        )
    }



    fun eventoSeleccionCodigoMiEquipoMultiple(codigo: List<String?>): EventoModel {
        return EventoModel(
            category = RDD,
            action = "$MI_RUTA – $ACTION_FOCOS_CAMPANIA",
            label = "Seleccion_foco_equipo: " + focosDeCampana(codigo),
            screenName = "$RDD | $FOCOS_DE_CAMPANIA | $FOCOS_PARA_TU_EQUIPO"
        )
    }

    private fun eventoIndicadorNivel(): EventoModel {
        return EventoModel(
            category = "Menú bloques",
            action = ACTION_CLICK_ELEMENTO,
            label = CHANGE_LEVEL,
            screenName = "Home | Pantalla principal"
        )
    }

    private fun eventoIndicadorNivelPorConstancia(constancia: String): EventoModel {
        return EventoModel(
            category = CHANGE_LEVEL,
            action = "Click constancia de consultora",
            label = constancia,
            screenName = CHANGE_LEVEL
        )
    }

    private fun eventoIndicadorNivelConsultora(nivel: String): EventoModel {
        return EventoModel(
            category = CHANGE_LEVEL,
            action = "Click detalle de consultora",
            label = nivel,
            screenName = CHANGE_LEVEL
        )
    }

    /** Consultara Marcaciones **/

    private fun eventoPerfilConsultora(label: String?): EventoModel {
        return EventoModel(
            category = RDD,
            action = ACTION_CLICK_BOTON,
            label = label ?: Constant.EMPTY_STRING,
            screenName = "$RDD | $ROL_CONSULTORA | $PERFIL"
        )
    }

    private fun eventoMetaConsultora(label: String?): EventoModel {
        return EventoModel(
            category = RDD,
            action = ACTION_CLICK_BOTON,
            label = label ?: Constant.EMPTY_STRING,
            screenName = "$RDD | $ROL_CONSULTORA | $PERFIL | $META"
        )
    }

    private fun eventoTipsDesarrolloVentaGanancia(label: String?): EventoModel {
        return EventoModel(
            category = RDD,
            action = ACTION_TIPS_DESARROLLO,
            label = label ?: Constant.EMPTY_STRING,
            screenName = "$RDD $SEPARADOR $ACOMPANIAMIENTO $SEPARADOR $AH_ACOMPANIAMIENTO_CONCURSOS"
        )
    }

    private fun eventoTipsDesarrolloDigital(label: String?): EventoModel {
        return EventoModel(
            category = RDD,
            action = ACTION_TIPS_DESARROLLO,
            label = label ?: Constant.EMPTY_STRING,
            screenName = "$RDD $SEPARADOR $ACOMPANIAMIENTO $SEPARADOR $ACOMPANIAMIENTO_DIGITAL_C"
        )
    }

    private fun eventoTipsDesarrolloCaminoBrillante(label: String?): EventoModel {
        return EventoModel(
            category = RDD,
            action = ACTION_TIPS_DESARROLLO,
            label = label ?: Constant.EMPTY_STRING,
            screenName = "$RDD $SEPARADOR $ACOMPANIAMIENTO $SEPARADOR $AH_ACOMPANIAMIENTO_CAMINO_BRILLANTE"
        )
    }

    private fun eventoTipsDesarrolloNovedades(label: String?): EventoModel {
        return EventoModel(
            category = RDD,
            action = ACTION_TIPS_DESARROLLO,
            label = label ?: Constant.EMPTY_STRING,
            screenName = "$RDD $SEPARADOR $ACOMPANIAMIENTO $SEPARADOR $AH_ACOMPANIAMIENTO_NOVEDADES"
        )
    }

    private fun eventoTipsDesarrolloProgramaNuevas(label: String?): EventoModel {
        return EventoModel(
            category = RDD,
            action = ACTION_TIPS_DESARROLLO,
            label = label ?: Constant.EMPTY_STRING,
            screenName = "$RDD $SEPARADOR $ACOMPANIAMIENTO $SEPARADOR $AH_ACOMPANIAMIENTO_PROGRAMA_NUEVAS"
        )
    }

    private fun eventoTipsDesarrolloConcursos(label: String?): EventoModel {
        return EventoModel(
            category = RDD,
            action = ACTION_TIPS_DESARROLLO,
            label = label ?: Constant.EMPTY_STRING,
            screenName = "$RDD $SEPARADOR $ACOMPANIAMIENTO $SEPARADOR $AH_ACOMPANIAMIENTO_CONCURSOS"
        )
    }

    private fun eventoTipsDesarrolloVentaGananciaCompartir(label: String?): EventoModel {
        return EventoModel(
            category = RDD,
            action = ACTION_TIPS_DESARROLLO_VENTA_Y_GANANCIA,
            label = label ?: Constant.EMPTY_STRING,
            screenName = "$RDD $SEPARADOR $ACOMPANIAMIENTO $SEPARADOR $ACOMPANIAMIENTO_VENTA_Y_GANANCIA"
        )
    }

    private fun eventoTipsDesarrolloDigitalCompartir(label: String?): EventoModel {
        return EventoModel(
            category = RDD,
            action = ACTION_TIPS_DESARROLLO_DIGITAL,
            label = label ?: Constant.EMPTY_STRING,
            screenName = "$RDD $SEPARADOR $ACOMPANIAMIENTO $SEPARADOR $ACOMPANIAMIENTO_DIGITAL_C"
        )
    }

    private fun eventoTipsDesarrolloCaminoBrillanteCompartir(label: String?): EventoModel {
        return EventoModel(
            category = RDD,
            action = ACTION_TIPS_DESARROLLO_CAMINO_BRILLANTE,
            label = label ?: Constant.EMPTY_STRING,
            screenName = "$RDD $SEPARADOR $ACOMPANIAMIENTO $SEPARADOR $AH_ACOMPANIAMIENTO_CAMINO_BRILLANTE"
        )
    }

    private fun eventoTipsDesarrolloCaminoBrillanteMaterialCompartir(label: String?): EventoModel {
        return EventoModel(
            category = RDD,
            action = ACTION_TIPS_DESARROLLO_CAMINO_BRILLANTE,
            label = label ?: Constant.EMPTY_STRING,
            screenName = "$RDD $SEPARADOR $ACOMPANIAMIENTO $SEPARADOR $AH_ACOMPANIAMIENTO_CAMINO_BRILLANTE"
        )
    }

    private fun eventoTipsDesarrolloCaminoBrillanteMaterialDescargar(label: String?): EventoModel {
        return EventoModel(
            category = RDD,
            action = ACTION_TIPS_DESARROLLO_CAMINO_BRILLANTE,
            label = label ?: Constant.EMPTY_STRING,
            screenName = "$RDD $SEPARADOR $ACOMPANIAMIENTO $SEPARADOR $AH_ACOMPANIAMIENTO_CAMINO_BRILLANTE"
        )
    }

    private fun eventoTipsDesarrolloNovedadesCompartirVideo(label: String?): EventoModel {
        return EventoModel(
            category = RDD,
            action = ACTION_TIPS_DESARROLLO_NOVEDADES,
            label = label ?: Constant.EMPTY_STRING,
            screenName = "$RDD $SEPARADOR $ACOMPANIAMIENTO $SEPARADOR $AH_ACOMPANIAMIENTO_NOVEDADES"
        )
    }

    private fun eventoTipsDesarrolloProgramasNuevasCompartirVideo(label: String?): EventoModel {
        return EventoModel(
            category = RDD,
            action = ACTION_TIPS_DESARROLLO_PROGRAMA_NUEVAS,
            label = label ?: Constant.EMPTY_STRING,
            screenName = "$RDD $SEPARADOR $ACOMPANIAMIENTO $SEPARADOR $AH_ACOMPANIAMIENTO_PROGRAMA_NUEVAS"
        )
    }

    private fun eventoTipsDesarrolloCaminoBrillanteMaterialVisualizar(label: String?): EventoModel {
        return EventoModel(
            category = RDD,
            action = ACTION_TIPS_DESARROLLO_CAMINO_BRILLANTE,
            label = label ?: Constant.EMPTY_STRING,
            screenName = "$RDD $SEPARADOR $ACOMPANIAMIENTO $SEPARADOR $AH_ACOMPANIAMIENTO_CAMINO_BRILLANTE"
        )
    }

    private fun eventoTipsDesarrolloNovedadesMaterialVisualizar(label: String?): EventoModel {
        return EventoModel(
            category = RDD,
            action = ACTION_TIPS_DESARROLLO_NOVEDADES,
            label = label ?: Constant.EMPTY_STRING,
            screenName = "$RDD $SEPARADOR $ACOMPANIAMIENTO $SEPARADOR $AH_ACOMPANIAMIENTO_NOVEDADES"
        )
    }

    private fun eventoTipsDesarrolloNovedadesMaterialCompartir(label: String?): EventoModel {
        return EventoModel(
            category = RDD,
            action = ACTION_TIPS_DESARROLLO_NOVEDADES,
            label = label ?: Constant.EMPTY_STRING,
            screenName = "$RDD $SEPARADOR $ACOMPANIAMIENTO $SEPARADOR $AH_ACOMPANIAMIENTO_NOVEDADES"
        )
    }

    private fun eventoTipsDesarrolloNovedadesMaterialDescargar(label: String?): EventoModel {
        return EventoModel(
            category = RDD,
            action = ACTION_TIPS_DESARROLLO_NOVEDADES,
            label = label ?: Constant.EMPTY_STRING,
            screenName = "$RDD $SEPARADOR $ACOMPANIAMIENTO $SEPARADOR $AH_ACOMPANIAMIENTO_NOVEDADES"
        )
    }

    private fun eventoTipsDesarrolloProgramaNuevasMaterialVisualizar(label: String?): EventoModel {
        return EventoModel(
            category = RDD,
            action = ACTION_TIPS_DESARROLLO_PROGRAMA_NUEVAS,
            label = label ?: Constant.EMPTY_STRING,
            screenName = "$RDD $SEPARADOR $ACOMPANIAMIENTO $SEPARADOR $AH_ACOMPANIAMIENTO_PROGRAMA_NUEVAS"
        )
    }

    private fun eventoTipsDesarrolloProgramaNuevasMaterialCompartir(label: String?): EventoModel {
        return EventoModel(
            category = RDD,
            action = ACTION_TIPS_DESARROLLO_PROGRAMA_NUEVAS,
            label = label ?: Constant.EMPTY_STRING,
            screenName = "$RDD $SEPARADOR $ACOMPANIAMIENTO $SEPARADOR $AH_ACOMPANIAMIENTO_PROGRAMA_NUEVAS"
        )
    }

    private fun eventoTipsDesarrolloProgramaNuevasMaterialDescargar(label: String?): EventoModel {
        return EventoModel(
            category = RDD,
            action = ACTION_TIPS_DESARROLLO_PROGRAMA_NUEVAS,
            label = label ?: Constant.EMPTY_STRING,
            screenName = "$RDD $SEPARADOR $ACOMPANIAMIENTO $SEPARADOR $AH_ACOMPANIAMIENTO_PROGRAMA_NUEVAS"
        )
    }

    private fun eventoVerSeccionConsultora(label: String?): EventoModel {
        return EventoModel(
            category = RDD,
            action = ACTION_VER_SECCION,
            label = label ?: Constant.EMPTY_STRING,
            screenName = "$RDD | $ROL_CONSULTORA | $PERFIL"
        )
    }

    private fun eventoOtrosingresosConsultora(label: String?, tipoVenta: Int): EventoModel {
        return EventoModel(
            category = RDD,
            action = validarTipoVenta(tipoVenta),
            label = label ?: Constant.EMPTY_STRING,
            screenName = "$RDD | $ROL_CONSULTORA | $MARCAS"
        )
    }

    private fun eventoVerListFacturaConsultora(label: String?): EventoModel {
        return EventoModel(
            category = RDD,
            action = ACTION_VER_LISTA,
            label = label ?: Constant.EMPTY_STRING,
            screenName = RDD
        )
    }

    private fun eventoVerListNoFacturaronConsultora(label: String?): EventoModel {
        return EventoModel(
            category = RDD,
            action = ACTION_NO_FACTURARON,
            label = label ?: Constant.EMPTY_STRING,
            screenName = "$RDD | $NO_FACTURARON"
        )
    }

    private fun eventoAcuerdoCumplidoonsultora(label: String?, tipoAcuerdo: String): EventoModel {
        return EventoModel(
            category = RDD,
            action = ACTION_ACUERDOS_CUMPLIDOS,
            label = "$label - $tipoAcuerdo",
            screenName = "$RDD | $ROL_CONSULTORA | $ACUERDOS"
        )
    }

    /**Por  Tipo*/
    private fun portipoEvento(tipo: Int): String {
        var nameEvent = Constant.EMPTY_STRING
        when (tipo) {
            0 -> nameEvent = EVENTO_ASIGNAR
            1 -> nameEvent = EVENTO_EDITAR
        }
        return nameEvent
    }

    private fun validarTipoVenta(tipo: Int): String {
        var nameAction = Constant.EMPTY_STRING
        when (tipo) {
            TIPO_POP_UP_MARCAS -> nameAction = ACTION_POP_UP_MARCAS
            TIPO_VENDE_OTRA_MARCA -> nameAction = ACTION_VENDE_OTRA_MARCA
            TIPO_NO_VENDE_OTRA_MARCA -> nameAction = ACTION_NO_VENDE_OTRA_MARCA
        }
        return nameAction
    }

    /** constructores */
    fun construirNombrePantalla(tagAnalytics: TagAnalytics, rolPlan: Rol?): String? {
        return when (tagAnalytics) {
            RUTA_DESARROLLO_DASHBOARD -> dashBoard()
            MI_RUTA_DESARROLLO_LISTADO -> miRuta()
            PERFIL_ACOMPANIAMIENTO -> acompaniamiento(rolPlan)
            PERFIL_PERFIL -> perfil(rolPlan)
            PERFIL_METAS -> meta(rolPlan)
            PERFIL_MARCAS -> marcas(rolPlan)
            PREPARARSE_ES_CLAVE_VENTAS -> ventas(rolPlan)
            PREPARARSE_ES_CLAVE_MAS_VENDIDO -> masVendido(rolPlan)
            PREPARARSE_ES_CLAVE_DIGITAL -> digital(rolPlan)
            PREPARARSE_ES_CLAVE_ACUERDOS -> acuerdos(rolPlan)
            PERFIL_DATOS -> datos(rolPlan)
            MI_RUTA_MAPA -> miMapa()
            PLANIFICAR_VISITA -> planificar()
            RE_PLANIFICAR_VISITA -> rePlanificar()
            ELIMINA_TU_CALENDARIO -> eliminarCalendario()
            ACOMPANIAMIENTO_VENTA_GANANCIA -> ventaYganancia()
            ACOMPANIAMIENTO_DIGITAL -> acompaniamientoDigital()
            ACOMPANIAMIENTO_CAMINO_BRILLANTE -> caminoBrillante()
            ACOMPANIAMIENTO_NOVEDADES -> acompaniamientoNovedades()
            ACOMPANIAMIENTO_CONCURSOS -> concursos()
            ACOMPANIAMIENTO_PROGRAMA_NUEVAS -> programaNuevas()
            else -> null
        }
    }

    fun construirEvento(tagAnalytics: TagAnalytics): EventoModel {
        return when (tagAnalytics) {
            EVENTO_HOME_MENU_INFERIOR_MI_RUTA -> eventoMenuRdd()
            EVENTO_HOME_INDICADORES_MI_RUTA -> eventoHomeRdd()
            EVENTO_PERFIL_DATOS -> eventoPerfilDatos()
            EVENTO_MI_RUTA_TAB_PLANIFICADAS -> eventoPlanificadas()
            EVENTO_MI_RUTA_TAB_NO_PLANIFICADAS -> eventoNoPlanificadas()
            EVENTO_MI_RUTA_TAB_BUSQUEDA -> eventoBuscar()
            EVENTO_SWITCH_LISTA -> eventoSwitchLista()
            EVENTO_SWITCH_MAPA -> eventoSwitchMapa()
            EVENTO_SWITCH_ACTIVAR_MAPA -> eventoSwitchActivarMapa()
            EVENTO_RE_PLANIFICAR_VISITA -> eventoReplanificarVisita()
            EVENTO_RE_PLANIFICAR_ACEPTAR_VISITA -> eventoReplanificarAceptarVisita()
            EVENTO_PLANIFICAR_VISITA -> eventoPlanificarVisita()
            EVENTO_PLANIFICAR_ACEPTAR_VISITA -> eventoPlanificarAceptarVisita()
            EVENTO_VER_MI_RUTA_MAPA -> eventoVerMiRuta()
            EVENTO_COBRANZA -> eventoCobranza()
            EVENTO_CONTACTO -> eventoContacto()
            EVENTO_ESTADO_CUENTA -> eventoEstadoCuenta()
            EVENTO_MIS_ANOTACIONES -> eventoMisAnotaciones()
            EVENTO_METAS_CONSULTORA -> eventoMetasConsultora()
            EVENTO_VER_MAS -> eventoVerMas()
            EVENTO_VER_MAS_CLICK -> eventoClickMas()
            EVENTO_MATERIAL_DE_DESARROLLO -> eventoClickMaterialDesarrollo()
            EVENTO_GUARDAR_ACUERDO -> eventoClickGuardarAcuerdo()
            EVENTO_AVANCE_DE_REGION -> eventoAvanceDeRegion()
            EVENTO_FOCOS_DE_CAMPANIA -> eventoFocosDeCampania()
            else -> construirEventoMenu(tagAnalytics)
        }
    }

    private fun construirEventoMenu(tagAnalytics: TagAnalytics): EventoModel {
        return when(tagAnalytics) {
            EVENTO_MENU_MI_RUTA_WHATSAPP -> eventoMenuRddWhatsap()
            EVENTO_MENU_MI_RUTA_LLAMAR -> eventoMenuRddLlamada()
            EVENTO_MENU_MI_RUTA_SMS -> eventoMenuRddSMS()
            EVENTO_MENU_MI_RUTA_ELIMINAR -> eventoMenuRddEliminar()
            EVENTO_MENU_MI_RUTA_REPLANIFICAR -> eventoMenuRddReplanificar()
            EVENTO_MENU_MI_RUTA_VISITA -> eventoMenuRddVisitar()
            EVENTO_MENU_MI_RUTA_MAPA -> eventoMenuRddMapa()
            EVENTO_MENU_MODAL_ELIMINAR_PLANIFICADA -> eventoModalElimiarPlanifica()
            EVENTO_MENU_MODAL_ELIMINAR_PLANIFICADA_ACEPTAR -> eventoModalAceptarEliminar()
            else -> throw AnalyticsEventException()
        }
    }

    fun construirEventoPorRol(tagAnalytics: TagAnalytics, sesion: Sesion, rol: Rol): EventoModel {
        val rolSesion = sesion.rol
        return when (tagAnalytics) {
            EVENTO_FINALIZAR_VISITA -> eventoFinalizarVisita()
            EVENTO_COMPARTIR_VIDEO -> eventoCompartirVideo(rol)
            EVENTO_CREAR_ACUERDO -> eventoCrearAcuerdo()
            EVENTO_REGISTRAR_VISITA -> eventoRegistrarVisita()
            EVENTO_RECONOCER_COMPORTAMIENTO -> eventoReconocer()
            EVENTO_RECONOCER_HABILIDADES -> eventoReconocerHabilidades()
            EVENTO_PLANIFICAR_ACOMPANIAMIENTO_VISITA -> eventoPlanificarAcompaniamientoVisita(
                rolSesion,
                rol
            )
                ?: throw AnalyticsEventException()
            EVENTO_RE_PLANIFICAR_ACOMPANIAMIENTO_VISITA -> eventoRePlanificarAcompaniamientoVisita(
                rolSesion,
                rol
            )
                ?: throw AnalyticsEventException()
            else -> throw AnalyticsEventException()
        }
    }

    fun construirEventoPorCodigo(codigoPersona: String): EventoModel {
        return eventMarcadorMapa(codigoPersona)
    }

    fun construirEvento2(
        tagAnalytics: TagAnalytics,
        nombreBoton: String,
        nombreDocumento: String
    ): EventoModel {
        return when (tagAnalytics) {
            EVENTO_MATERIAL_DE_DESARROLLO_VISUALIZAR_Y_DESCARGAR ->
                eventoClickMDVisualizarODescargar(nombreBoton, nombreDocumento)
            else -> throw AnalyticsEventException()
        }
    }

    fun construirEventoAvancePorCodigo(tagAnalytics: TagAnalytics, codigo: String): EventoModel {
        return when (tagAnalytics) {
            EVENTO_AVANCE_POR_CODIGO -> eventoAvancePorCodigo(codigo)
            else -> throw AnalyticsEventException()
        }
    }

    fun construirEventoFocosCampana(tagAnalytics: TagAnalytics, tipo: Int): EventoModel {
        return when (tagAnalytics) {
            EVENTO_MIS_FOCOS_CAMPANA_ASIGNAR_EDITAR -> eventoMiFocoCampania(tipo)
            EVENTO_FOCOS_HABILIDADES_ASIGNAR_EDITAR -> eventoMisHabilidades(tipo)
            else -> throw AnalyticsEventException()
        }
    }

    fun construirEventoCambioNivel(
        tagAnalytics: TagAnalytics,
        nivel: String,
        constancia: String
    ): EventoModel {
        return when (tagAnalytics) {
            EVENTO_CAMBIO_NIVEL_MENU_DASHBOARD -> eventoIndicadorNivel()
            EVENTO_CAMBIO_NIVEL_CONSTANCIA -> eventoIndicadorNivelPorConstancia(constancia)
            EVENTO_CAMBIO_NIVEL_CONSULTORA -> eventoIndicadorNivelConsultora(nivel)
            else -> throw AnalyticsEventException()
        }
    }

    fun construirEventoConsultora(tagAnalytics: TagAnalytics, tagName: String?): EventoModel {
        return when (tagAnalytics) {
            EVENTO_PERFIL_CONSULTORA -> eventoPerfilConsultora(tagName)
            EVENTO_VER_SECCION_CONSULTORA -> eventoVerSeccionConsultora(tagName)
            EVENTO_GUARDAR_META_CONSULTORA -> eventoMetaConsultora(tagName)
            EVENTO_GUARDAR_LIMPIAR_MARCA_CONSULTORA -> eventoOtrosingresosConsultora(
                tagName,
                TIPO_POP_UP_MARCAS
            )
            EVENTO_VENDE_OTRA_MARCA -> eventoOtrosingresosConsultora(tagName, TIPO_VENDE_OTRA_MARCA)
            EVENTO_NO_VENDE_OTRA_MARCA -> eventoOtrosingresosConsultora(
                tagName,
                TIPO_NO_VENDE_OTRA_MARCA
            )
            EVENTO_VER_LISTA_FACTURA_CONSULTORA -> eventoVerListFacturaConsultora(tagName)
            EVENTO_NO_FACTURARON_CONSULTORA -> eventoVerListNoFacturaronConsultora(tagName)
            EVENTO_ACUERDO_CUMPLIDO_CONSULTORA -> eventoAcuerdoCumplidoonsultora(
                tagName,
                LABEL_ACUERDO_CUMPLIDO
            )
            EVENTO_REESTABLECER_ACUERDO_CONSULTORA -> eventoAcuerdoCumplidoonsultora(
                tagName,
                LABEL_REESTABLECER_ACUERDO
            )
            else -> throw AnalyticsEventException()
        }
    }

    fun construirEventoTipsDesarrollo(tagAnalytics: TagAnalytics, tagName: String?): EventoModel {
        return when (tagAnalytics) {
            EVENTO_TIPS_DESARROLLO_VENTA_Y_GANANCIA -> eventoTipsDesarrolloVentaGanancia(tagName)
            EVENTO_TIPS_DESARROLLO_DIGITAL -> eventoTipsDesarrolloDigital(tagName)
            EVENTO_TIPS_DESARROLLO_CONCURSOS -> eventoTipsDesarrolloConcursos(tagName)
            EVENTO_TIPS_DESARROLLO_NOVEDADES -> eventoTipsDesarrolloNovedades(tagName)
            EVENTO_TIPS_DESARROLLO_PROGRAMA_NUEVAS -> eventoTipsDesarrolloProgramaNuevas(tagName)
            EVENTO_TIPS_DESARROLLO_CAMINO_BRILLANTE -> eventoTipsDesarrolloCaminoBrillante(tagName)
            EVENTO_TIPS_DESARROLLO_VENTA_Y_GANANCIA_COMPARTIR -> eventoTipsDesarrolloVentaGananciaCompartir(
                tagName
            )
            EVENTO_TIPS_DESARROLLO_DIGITAL_COMPARTIR -> eventoTipsDesarrolloDigitalCompartir(tagName)
            EVENTO_TIPS_DESARROLLO_CAMINO_BRILLANTE_COMPARTIR -> eventoTipsDesarrolloCaminoBrillanteCompartir(
                tagName
            )
            EVENTO_TIPS_DESARROLLO_CAMINO_BRILLANTE_MATERIAL_COMPARTIR -> eventoTipsDesarrolloCaminoBrillanteMaterialCompartir(
                tagName
            )
            EVENTO_TIPS_DESARROLLO_CAMINO_BRILLANTE_MATERIAL_VISUALIZAR -> eventoTipsDesarrolloCaminoBrillanteMaterialVisualizar(
                tagName
            )
            EVENTO_TIPS_DESARROLLO_CAMINO_BRILLANTE_MATERIAL_DESCARGAR -> eventoTipsDesarrolloCaminoBrillanteMaterialDescargar(
                tagName
            )
            EVENTO_TIPS_DESARROLLO_NOVEDADES_COMPARTIR_VIDEO -> eventoTipsDesarrolloNovedadesCompartirVideo(
                tagName
            )
            EVENTO_TIPS_DESARROLLO_NOVEDADES_MATERIAL_COMPARTIR -> eventoTipsDesarrolloNovedadesMaterialCompartir(
                tagName
            )
            EVENTO_TIPS_DESARROLLO_NOVEDADES_MATERIAL_VISUALIZAR -> eventoTipsDesarrolloNovedadesMaterialVisualizar(
                tagName
            )
            EVENTO_TIPS_DESARROLLO_NOVEDADES_MATERIAL_DESCARGAR -> eventoTipsDesarrolloNovedadesMaterialDescargar(
                tagName
            )
            EVENTO_TIPS_DESARROLLO_PROGRAMA_NUEVAS_COMPARTIR -> eventoTipsDesarrolloProgramaNuevasMaterialCompartir(
                tagName
            )
            EVENTO_TIPS_DESARROLLO_PROGRAMA_NUEVAS_VISUALIZAR -> eventoTipsDesarrolloProgramaNuevasMaterialVisualizar(
                tagName
            )
            EVENTO_TIPS_DESARROLLO_PROGRAMA_NUEVAS_DESCARGAR -> eventoTipsDesarrolloProgramaNuevasMaterialDescargar(
                tagName
            )
            EVENTO_TIPS_DESARROLLO_PROGRAMA_NUEVAS_COMPARTIR_VIDEO -> eventoTipsDesarrolloProgramasNuevasCompartirVideo(
                tagName
            )
            else -> throw AnalyticsEventException()
        }
    }

    suspend fun construirEventoInspira(tagAnalytics: TagAnalytics, label: String, screenName: String): EventoModel {
        return when (tagAnalytics) {
            EVENTO_INSPIRA_PROGRAMA -> eventoInspiraPrograma(screenName)
            EVENTO_INSPIRA_POST -> eventoInspiraPost(label, screenName)
            EVENTO_INSPIRA_TAB -> eventoInspiraTab(label, screenName)
            else -> throw AnalyticsEventException()
        }
    }

    private fun eventoInspiraPrograma(screenName: String): EventoModel {
        return EventoModel(
            category = "Menú bloques",
            action = "Clic elemento",
            label = INSPIRA_PROGRAMA,
            screenName = screenName)
    }

    private fun eventoInspiraPost(label: String, screenName: String): EventoModel {
        return EventoModel(
            category = INSPIRA_PROGRAMA,
            action = "Click Botón",
            label = label,
            screenName = screenName)
    }

    private fun eventoInspiraTab(label: String, screenName: String): EventoModel {
        return EventoModel(
            category = INSPIRA_PROGRAMA,
            action = "Seleccionar Tab",
            label = label,
            screenName = "$INSPIRA_PROGRAMA | $screenName" )
    }

}
