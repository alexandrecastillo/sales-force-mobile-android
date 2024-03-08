package biz.belcorp.salesforce.modules.consultants.utils

import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.domain.entities.people.Person
import biz.belcorp.salesforce.core.domain.entities.session.Sesion
import biz.belcorp.salesforce.core.entities.zonificacion.Rol

object ChatBotMock {
    fun getSession(): Sesion {
        return Sesion(
            Constant.EMPTY_STRING,
            Constant.EMPTY_STRING,
            Constant.EMPTY_STRING,
            Constant.EMPTY_STRING,
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

}
