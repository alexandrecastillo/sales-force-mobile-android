package biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.postulantes

import biz.belcorp.salesforce.modules.postulants.features.entities.PostulanteModel
import biz.belcorp.salesforce.modules.postulants.features.entities.UneteConfiguracionModel

interface FormularioView {

    fun pais(): String

    fun paso(): Int

    fun continuar(paso: Int)

    fun finalizar()

    fun postulante(): PostulanteModel

    fun postulante(postulante: PostulanteModel)

    fun setPostulante(postulante: PostulanteModel)

    fun uneteConfiguracion(uneteConfiguracion: UneteConfiguracionModel?)

    fun uneteConfiguracion(): UneteConfiguracionModel?

    fun initStepper(pasos: Int, position: Int)

    fun showLoading()

    fun hideLoading()

    fun validarZonaSMS(pasoBloqueado: Int)

    fun getPasoBloqueado() : Int

    fun getPais() : String

    fun esPagoContado() : Boolean

    fun validarPagoContado()

    fun disableSteps(): Boolean

}
