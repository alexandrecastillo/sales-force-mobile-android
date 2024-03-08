package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.analitycs

import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.domain.entities.analytics.EventoModel
import biz.belcorp.salesforce.core.domain.entities.analytics.TagAnalytics
import biz.belcorp.salesforce.core.domain.entities.analytics.TagAnalyticsHelper
import biz.belcorp.salesforce.core.domain.entities.analytics.TagAnalyticsHelper.MI_RUTA
import biz.belcorp.salesforce.core.domain.entities.analytics.TagAnalyticsHelper.RDD
import biz.belcorp.salesforce.core.domain.exceptions.AnalyticsEventException
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.focos.FocoSeleccionado
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.plan.InfoPlanRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.unidadadministrativa.UaRegresable

object TagAnalyticsHelper {

    private fun eventoSeleccionDeDesarrollo(rol: InfoPlanRdd): EventoModel {
        val codigo = traerCodigo(rol)
        return EventoModel(
            category = RDD,
            action = "$MI_RUTA – Avance de Región",
            label = " Desarrollo de ${rol.rol.nameAsCode()}: " + codigo,
            screenName = "$RDD | Avance de Región"
        )
    }

    private fun eventoSalirAvance(rol: InfoPlanRdd): EventoModel {
        val codigo = traerCodigo(rol)
        return EventoModel(
            category = TagAnalyticsHelper.RDD,
            action = "${TagAnalyticsHelper.MI_RUTA} – Salir de ${rol.rol.nameAsCode()}",
            label = "${rol.rol.nameAsCode()}: " + codigo,
            screenName = TagAnalyticsHelper.avanceRegionScreenName(rol.rol)!!
        )
    }

    private fun eventoVerSalirRutaAvance(model: UaRegresable): EventoModel {
        val nameEvent = if (model.salir) "Salir" else "Ver Ruta"
        return EventoModel(
            category = TagAnalyticsHelper.RDD,
            action = "${TagAnalyticsHelper.MI_RUTA} – " + nameEvent + " de ${model.rolLiderAsociado.nameAsCode()}",
            label = "${model.rolLiderAsociado.nameAsCode()}: " + model.codigoUa,
            screenName = TagAnalyticsHelper.avanceRegionScreenName(model.rolLiderAsociado)!!
        )
    }

    private fun eventoVerMasMenos(rol: InfoPlanRdd, tipo: Int): EventoModel {
        var nameEvent = Constant.EMPTY_STRING
        val codigo = traerCodigo(rol)
        when (tipo) {
            0 -> nameEvent = "Ver menos"
            1 -> nameEvent = "Ver más"
        }
        return EventoModel(
            category = TagAnalyticsHelper.RDD,
            action = "${TagAnalyticsHelper.MI_RUTA} – " + nameEvent + " de ${rol.rol.nameAsCode()}",
            label = "${rol.rol.nameAsCode()}: " + codigo,
            screenName = TagAnalyticsHelper.avanceRegionVerMasMenos(rol.rol)!!
        )
    }

    private fun eventoSeleccionHabilidadesMiEquipo(foco: FocoSeleccionado): EventoModel {
        return EventoModel(
            category = TagAnalyticsHelper.RDD,
            action = "${TagAnalyticsHelper.MI_RUTA} – ${TagAnalyticsHelper.ACTION_FOCOS_CAMPANIA}",
            label = "Seleccion_HDL_equipo: " + foco.codigoua + " : " + TagAnalyticsHelper.focosDeCampana(
                foco.focos
            ),
            screenName = "${TagAnalyticsHelper.RDD} | ${TagAnalyticsHelper.FOCOS_DE_CAMPANIA} | Habilidades para tu equipo"
        )
    }

    private fun eventoAsignarSeleccionMiEquipoMultiple(foco: FocoSeleccionado): EventoModel {
        return EventoModel(
            category = TagAnalyticsHelper.RDD,
            action = "${TagAnalyticsHelper.MI_RUTA} – ${TagAnalyticsHelper.ACTION_FOCOS_CAMPANIA}",
            label = "Seleccion_foco_equipo_multiple: " + TagAnalyticsHelper.focosDeCampana(foco.focos),
            screenName = "${TagAnalyticsHelper.RDD} | ${TagAnalyticsHelper.FOCOS_DE_CAMPANIA} | ${TagAnalyticsHelper.FOCOS_PARA_TU_EQUIPO}"
        )
    }

    private fun eventoEditarAsignarSeleccionMiEquipo(foco: FocoSeleccionado): EventoModel {
        return EventoModel(
            category = TagAnalyticsHelper.RDD,
            action = "${TagAnalyticsHelper.MI_RUTA} – ${TagAnalyticsHelper.ACTION_FOCOS_CAMPANIA}",
            label = "Seleccion_foco_equipo: " + foco.codigoua + " : " + TagAnalyticsHelper.focosDeCampana(
                foco.focos
            ),
            screenName = "${TagAnalyticsHelper.RDD} | ${TagAnalyticsHelper.FOCOS_DE_CAMPANIA} | ${TagAnalyticsHelper.FOCOS_PARA_TU_EQUIPO}"
        )
    }

    private fun traerCodigo(rol: InfoPlanRdd): String {
        var codigo = Constant.EMPTY_STRING
        when (rol.rol.codigoRol) {
            Rol.DIRECTOR_VENTAS.codigoRol -> {
                codigo = rol.codigoPais
            }
            Rol.GERENTE_REGION.codigoRol -> {
                codigo = rol.codigoRegion!!
            }
            Rol.GERENTE_ZONA.codigoRol -> {
                codigo = rol.codigoZona!!
            }
            Rol.SOCIA_EMPRESARIA.codigoRol -> {
                codigo = rol.codigoSeccion!!
            }
        }
        return codigo
    }

    fun construirEventopPorAvance(
        tagAnalytics: TagAnalytics,
        rol: InfoPlanRdd,
        tipo: Int
    ): EventoModel {
        return when (tagAnalytics) {
            TagAnalytics.EVENTO_VER_SALIR_AVANCE -> eventoSalirAvance(rol)
            TagAnalytics.EVENTO_REGRESAR_A_MI_RUTA -> TagAnalyticsHelper.eventoRegresarAMiRuta(rol.rol)
            TagAnalytics.EVENTO_VER_MAS_MENOS -> eventoVerMasMenos(rol, tipo)
            TagAnalytics.EVENTO_SELECCION_DESARROLLO -> eventoSeleccionDeDesarrollo(rol)
            else -> throw AnalyticsEventException()
        }
    }

    fun construirEventoVerRuta(
        tagAnalytics: TagAnalytics,
        model: UaRegresable
    ): EventoModel {
        return when (tagAnalytics) {
            TagAnalytics.EVENTO_VER_SALIR_AVANCE -> eventoVerSalirRutaAvance(model)
            else -> throw AnalyticsEventException()
        }
    }

    fun construirEventoMiEquipo(tagAnalytics: TagAnalytics, foco: FocoSeleccionado): EventoModel {
        return when (tagAnalytics) {
            TagAnalytics.EVENTO_SELECCION_MIS_FOCOS_CAMPANA -> TagAnalyticsHelper.eventoFocoCampaniaEditar(
                foco.focos
            )
            TagAnalytics.EVENTO_SELECCION_MIS_HABILIDADES -> TagAnalyticsHelper.eventoMisHabilidadesEditar(
                foco.focos
            )
            TagAnalytics.EVENTO_SELECCION_CODIGO_MI_EQUIPO_MULTIPLE -> TagAnalyticsHelper.eventoSeleccionCodigoMiEquipoMultiple(
                foco.focos
            )
            TagAnalytics.EVENTO_SELECCION_MI_EQUIPO_MULTIPLE -> eventoAsignarSeleccionMiEquipoMultiple(foco)
            TagAnalytics.EVENTO_SELECCION_MI_EQUIPO -> eventoEditarAsignarSeleccionMiEquipo(foco)
            TagAnalytics.EVENTO_FOCOS_MI_EQUIPO__MULTIPLE_ASIGNAR -> TagAnalyticsHelper.eventoAsignarMiEquipoMultiple()
            TagAnalytics.EVENTO_FOCOS_MI_EQUIPO_ASIGNAR_EDITAR -> TagAnalyticsHelper.eventoEditarAsignarMiEquipo(
                foco.codigoua,
                foco.tipo
            )
            TagAnalytics.EVENTO_EDITAR_ASIGNAR_HABILIDADES_MI_EQUIPO -> TagAnalyticsHelper.eventoEditarAsignarHabilidadesMiEquipo(
                foco.codigoua,
                foco.tipo
            )
            TagAnalytics.EVENTO_SELECCION_HABILIDADES_MI_EQUIPO -> eventoSeleccionHabilidadesMiEquipo(foco)
            else -> throw AnalyticsEventException()
        }
    }
}
