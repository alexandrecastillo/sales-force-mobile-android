package biz.belcorp.salesforce.base.features.home.shortcuts

import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.domain.entities.options.Option
import biz.belcorp.salesforce.core.domain.entities.pais.Pais

object ShortcutHelper {

    fun getShortcutSE() : ArrayList<Option>{
        //Only fixed buttons
        val options: ArrayList<Option> = ArrayList()
        val campaignReport = Option(10, "Reportes de campaña", 1, "")
        val joinUp = Option(11, "Únete", 2, "")
        val developmentPath = Option(12, "Ruta de Desarrollo",3, "")
        val campaignProjection = Option(9, "Proyección de Campaña", 9, "")
        val myAcademy= Option(15, "Mi Academia", 6, "")
        val socialMedia = Option(18, "Materiales Redes Sociales", 8, "")
        val changeLevel = Option(20, "Gestiona tu Cambio de Nivel",10, "")
        options.add(campaignReport)
        options.add(joinUp)
        options.add(developmentPath)
        options.add(campaignProjection)
        options.add(myAcademy)
        options.add(socialMedia)
        options.add(changeLevel)

        return options
    }

    fun getShortcutGZ(countryIso: String?): ArrayList<Option> {
        //Only fixed buttons
        val options: ArrayList<Option> = ArrayList()
        val campaignReport = Option(10, "Reportes de campaña", 1, "")
        val joinUp = Option(11, "Únete", 2, "")
        val developmentPath = Option(12, "Ruta de Desarrollo",3, "")
        val ucb = Option(16, "UCB", 7, "")
        val businessPartnerList= Option(21, "Listado Socias", 11, "")

        options.add(campaignReport)
        options.add(joinUp)
        options.add(developmentPath)
        options.add(ucb)
        if(!countryIso.equals(Pais.PUERTORICO.codigoIso)) {
            options.add(businessPartnerList)
        }

        return options
    }

    fun getShortcutGR(): ArrayList<Option> {
        //Only fixed buttons
        val options: ArrayList<Option> = ArrayList()
        val developmentPath = Option(12, "Ruta de Desarrollo",3, "")
        val ucb = Option(16, "UCB", 7, "")

        options.add(developmentPath)
        options.add(ucb)

        return options
    }

    fun getOptionsMenuBottom(): ArrayList<Option> {

        //Only fixed buttons
        val options: ArrayList<Option> = ArrayList()
        val initialize = Option(1, "Inicio", 1, "")
        val search = Option(2, "Buscar", 2, "")
        val web = Option(4, "Pedidos Web",4, "")
        val plus = Option(5, "Más", 99, "")

        options.add(initialize)
        options.add(search)
        options.add(web)
        options.add(plus)


        return options

    }

    fun getOptionsGRMenuBottom(): ArrayList<Option> {

        //Only fixed buttons
        val options: ArrayList<Option> = ArrayList()
        val initialize = Option(1, "Inicio", 1, "")
        val plus = Option(5, "Más", 99, "")

        options.add(initialize)
        options.add(plus)

        return options

    }
}
