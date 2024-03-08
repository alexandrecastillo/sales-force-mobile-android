package biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.prepostulantes

import biz.belcorp.salesforce.modules.postulants.features.entities.PrePostulanteModel
import biz.belcorp.salesforce.modules.postulants.features.entities.UneteConfiguracionModel
import biz.belcorp.salesforce.modules.postulants.utils.Constant


interface FormularioPreView {

    fun pais(): String

    fun paso(): Int

    fun continuar(paso: Int)

    fun finalizar()

    fun prePostulante(): PrePostulanteModel

    fun prePostulante(prepostulante: PrePostulanteModel)

    fun setPrePostulante(prepostulante: PrePostulanteModel)

    fun uneteConfiguracion(uneteConfiguracion: UneteConfiguracionModel?)

    fun uneteConfiguracion(): UneteConfiguracionModel?

    fun initStepper(pasos: Int, position: Int)

    fun showLoading()

    fun hideLoading()

    fun getPais(): String

}
