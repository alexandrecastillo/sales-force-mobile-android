package biz.belcorp.salesforce.modules.kpis.utils

import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.constants.KpiType
import biz.belcorp.salesforce.core.constants.PeriodType
import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.domain.entities.configuration.Configuration
import biz.belcorp.salesforce.core.domain.entities.configuration.MainBrandType
import biz.belcorp.salesforce.core.domain.entities.people.Person
import biz.belcorp.salesforce.core.domain.entities.session.Sesion
import biz.belcorp.salesforce.core.domain.entities.ua.UaInfo
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.newcycles.NewCycleEntity
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.features.uainfo.UaInfoModel
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.KpiDetailParams
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.newcycle.NewCycleIndicator
import io.mockk.mockk
import java.util.*

object NewCycleIndicatorMock {

    val newCycleListEntity: List<NewCycleEntity>
        get() = listOf(
            NewCycleEntity(
                campaign = "201919",
                lowValueOrders6d6 = 23,
                lowValueOrders5d5 = 34,
                lowValueOrders4d4 = 34,
                lowValueOrders3d3 = 324,
                lowValueOrders2d2 = 232,
                lowValueOrders1d1 = 232,
                profile = "er",
                region = "er",
                section = "A",
                zone = "45"
            )
        )

    val newCycleListIndicator: List<NewCycleIndicator>
        get() = arrayListOf(
            NewCycleIndicator(
                campaign = "201919",
                lowValueOrders6d6 = 23,
                lowValueOrders5d5 = 34,
                lowValueOrders4d4 = 34,
                lowValueOrders3d3 = 324,
                lowValueOrders2d2 = 232,
                lowValueOrders1d1 = 232,
                lowValueOrdersRetentionPercentage = 0.0,
                highValueOrders6d6 = 23,
                highValueOrders5d5 = 34,
                highValueOrders3d3 = 324,
                highValueOrders4d4 = 34,
                highValueOrders2d2 = 232,
                highValueOrders1d1 = 232,
                highValueOrdersRetentionPercentage = 0.0,
                profile = "er",
                region = "er",
                section = "A",
                zone = "45"
            )
        )

    val uaKey = LlaveUA("80", "8020", null, 0)
    val days = "21"

    fun getSession(): Sesion {
        return Sesion(
            null,
            null,
            Constant.EMPTY_STRING,
            null,
            Rol.GERENTE_ZONA.codigoRol,
            Constant.EMPTY_STRING,
            Constant.EMPTY_STRING,
            Constant.EMPTY_STRING,
            Constant.EMPTY_STRING,
            Constant.EMPTY_STRING,
            Constant.EMPTY_STRING,
            Person(
                0,
                Constant.EMPTY_STRING,
                Constant.EMPTY_STRING,
                Constant.EMPTY_STRING,
                Constant.EMPTY_STRING,
                Constant.EMPTY_STRING,
                Constant.EMPTY_STRING
            ),
            Campania.construirDummy()
        )

    }

    val campaignMock = Campania(
        codigo = Constant.EMPTY_STRING,
        nombreCorto = Constant.EMPTY_STRING,
        esPrimerDiaFacturacion = false,
        fin = Date(),
        inicio = Date(),
        inicioFacturacion = Date(),
        orden = 1,
        periodo = Campania.Periodo.FACTURACION
    )

    val campaignSaleMock = Campania(
        codigo = Constant.EMPTY_STRING,
        nombreCorto = Constant.EMPTY_STRING,
        esPrimerDiaFacturacion = false,
        fin = Date(),
        inicio = Date(),
        inicioFacturacion = Date(),
        orden = 1,
        periodo = Campania.Periodo.VENTA
    )

    fun createCampaign(period: Campania.Periodo): Campania {
        return Campania(
            codigo = Constant.EMPTY_STRING,
            nombreCorto = Constant.EMPTY_STRING,
            esPrimerDiaFacturacion = period == Campania.Periodo.FACTURACION,
            fin = Date(),
            inicio = Date(),
            inicioFacturacion = Date(),
            orden = 1,
            periodo = period
        )
    }

    val uaInfo = UaInfo(
        code = 0,
        country = "PE",
        role = Rol.GERENTE_ZONA,
        person = null,
        campaign = null,
        uaKey = uaKey,
        isCovered = true,
        isThirdPerson = false
    )

    val uaInfoModel = mockk<UaInfoModel>()

    val configuration = Configuration(
        Constant.EMPTY_STRING,
        Constant.EMPTY_STRING,
        Constant.EMPTY_STRING,
        Constant.EMPTY_STRING,
        0,
        0,
        true,
        MainBrandType.ESIKA,
        false,
        false,
        Constant.EMPTY_STRING,
        Constant.EMPTY_STRING,
        Constant.EMPTY_STRING,
        Constant.NUMERO_UNO,
        false
    )

    val kpiParams = KpiDetailParams(
        uaKey,
        Rol.SOCIA_EMPRESARIA,
        PeriodType.BILLING,
        KpiType.NEW_CYCLES
    )
}
