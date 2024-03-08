package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.indicador

class IndicadorRddMisVisitas(val realizadas: Int, val planificadas: Int)

class IndicadorRddVisitasSE(val misVisitasRealizadas: Int,
                            val misVisitasPlanificadas: Int,
                            val visitasSERealizadas: Int,
                            val visitasSEPlanificadas: Int)

class IndicadorRddMisVisitasEntity(val realizadas: Int, val planificadas: Int)

class IndicadorRddVisitasEntity(
    val visitasPlanificadasGRs: Int = 0,
    val visitasRealizadasGRs: Int = 0,
    val visitasPlanificadasGZs: Int = 0,
    val visitasRealizadasGZs: Int = 0,
    val visitasPlanificadasSEs: Int = 0,
    val visitasRealizadasSEs: Int = 0,
    val visitasPlanificadasCOs: Int = 0,
    val visitasRealizadasCOs: Int = 0)
