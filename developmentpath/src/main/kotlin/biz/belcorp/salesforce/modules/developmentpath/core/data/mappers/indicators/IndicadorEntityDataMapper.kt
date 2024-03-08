package biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.indicators

import biz.belcorp.salesforce.core.entities.sql.indicators.*
import biz.belcorp.salesforce.core.utils.orZero
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.indicador.*

class IndicadorEntityDataMapper {

    fun parseRddIndicatorDataForGr(entity: IndicadorRddVisitasEntityForGr) =
            IndicadorRddArgsGR(
                    tPlanificadasGZs = entity.totalVisitasPlanificadasGZs.toString().orZero(),
                    tVisitadasGZs = entity.totalVisitasRealizadasGZs.toString().orZero(),
                    tPlanificadasSEs = entity.totalvisitasPlanificadasSEs.toString().orZero(),
                    tVisitadasSEs = entity.totalvisitasRealizadasSEs.toString().orZero(),
                    tPlanificadasCOs = entity.totalvisitasPlanificadasCOs.toString().orZero(),
                    tVisitadasCOs = entity.totalvisitasRealizadasCOs.toString().orZero()
            )

    fun parseRddIndicatorDataForGr(entity: IndicadorVisitasRDDEntity) =
            IndicadorRddArgsGR(
                    tPlanificadasGZs = entity.planificadasGR.toString().orZero(),
                    tVisitadasGZs = entity.visitadasGR.toString().orZero(),
                    tPlanificadasSEs = entity.planificadasGZ.toString().orZero(),
                    tVisitadasSEs = entity.visitadasGZ.toString().orZero(),
                    tPlanificadasCOs = entity.planificadasSE.toString().orZero(),
                    tVisitadasCOs = entity.visitadasSE.toString().orZero()
            )

    fun parseRddIndicatorDataForGz(entity: IndicadorRddVisitasEntityForGz) =
            IndicadorRddArgsGZ(
                    tPlanificadasSEs = entity.totalvisitasPlanificadasSEs.toString().orZero(),
                    tVisitadasSEs = entity.totalvisitasRealizadasSEs.toString().orZero(),
                    tPlanificadasCOs = entity.totalvisitasPlanificadasCOs.toString().orZero(),
                    tVisitadasCOs = entity.totalvisitasRealizadasCOs.toString().orZero()
            )

    fun parseRddIndicatorDataForSe(entity: IndicadorRddVisitasEntityForSe) =
            IndicadorRddArgsSE(
                    tPlanificadasCOs = entity.totalvisitasPlanificadasCOs.toString().orZero(),
                    tVisitadasCOs = entity.totalvisitasRealizadasCOs.toString().orZero()
            )

    fun parseRddIndicatorDataForDv(entidad: IndicadorVisitasRDDEntity) =
            IndicadorRddArgsDV(
                    visitasPlanificadasDV = entidad.planificadasDV.toString().orZero(),
                    visitadasDV = entidad.visitadasDV.toString().orZero(),
                    visitasPlanificadasGR = entidad.planificadasGR.toString().orZero(),
                    visitadasGR = entidad.visitadasGR.toString().orZero(),
                    visitasPlanificadasGZ = entidad.planificadasGZ.toString().orZero(),
                    visitadasGZ = entidad.visitadasGZ.toString().orZero(),
                    visitasPlanificadasSE = entidad.planificadasSE.toString().orZero(),
                    visitadasSE = entidad.visitadasSE.toString().orZero())

    fun parseIndicadorMisVisitas(entity: IndicadorRddMisVisitasEntity): IndicadorRddMisVisitas {
        return IndicadorRddMisVisitas(
                realizadas = entity.realizadas,
                planificadas = entity.planificadas)
    }

}
