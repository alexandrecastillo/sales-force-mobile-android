package biz.belcorp.salesforce.modules.postulants.features.formulario.componentes

interface IUnetePaso3 : IUnetePaso {

    fun onGPSRequestSuccess()
    fun onGPSRequestActivated()
    fun registerListenerGSP(listener: UnetePaso3.ListenerGpsNoActivo?)
    fun unRegisterListenerGSP()
    fun drawCircleRestriction(radio : Int)
}
