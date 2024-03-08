package biz.belcorp.salesforce.modules.developmentpath.features.ingresosextra.more

import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.features.ingresosextra.section.OtraMarcaModel

interface IngresosExtraOtrosContract {
    interface View {
        fun mostrarOtraData(data: List<OtraMarcaModel>) {
            /* Empty */
        }

        fun checkSuccess() {
            /* Empty */
        }

        fun checkFailure(message: String) {
            /* Empty */
        }
    }

    interface Presenter {
        fun obtener(personaId: Long, personaRol: Rol)

        fun checkList(data: List<OtraMarcaModel>)
    }
}
