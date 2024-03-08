package biz.belcorp.salesforce.modules.postulants.features.formulario.componentes

import biz.belcorp.salesforce.modules.postulants.features.entities.PostulanteModel
import biz.belcorp.salesforce.modules.postulants.features.entities.PrePostulanteModel

interface IUnetePaso {

    fun createModel(): PostulanteModel? {
        TODO("not implemented")
    }

    fun createPreModel(): PrePostulanteModel? {
        TODO("not implemented")
    }

    fun validate(): Boolean

    fun disable(enEdicion: Boolean = false)
}
