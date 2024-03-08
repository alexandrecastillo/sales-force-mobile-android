package biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.postulantes.paso3

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import biz.belcorp.salesforce.core.domain.exceptions.NetworkConnectionException
import biz.belcorp.salesforce.core.gps.RequestGPS
import biz.belcorp.salesforce.core.utils.DeviceUtil
import biz.belcorp.salesforce.core.utils.gone
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.core.utils.visible
import biz.belcorp.salesforce.modules.postulants.R
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.GeoZonificacion
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.ValidacionBuroResponse
import biz.belcorp.salesforce.modules.postulants.core.domain.enums.*
import biz.belcorp.salesforce.modules.postulants.core.domain.exception.ZonificacionNotFound
import biz.belcorp.salesforce.modules.postulants.features.entities.PostulanteModel
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.IUnetePaso3
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.UnetePaso3
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.UnetePaso3Factory
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.UneteActivity
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.base.BaseUneteFragment
import biz.belcorp.salesforce.modules.postulants.utils.Constant
import biz.belcorp.salesforce.modules.postulants.utils.KeyboardUtil
import biz.belcorp.salesforce.modules.postulants.utils.customDialog
import biz.belcorp.salesforce.modules.postulants.utils.solicitudRechazadaDialog
import com.google.android.gms.maps.model.LatLng
import com.stepstone.stepper.Step
import com.stepstone.stepper.VerificationError
import kotlinx.android.synthetic.main.dialog_material.*
import kotlinx.android.synthetic.main.fragment_unete_paso_3.*

class UnetePaso3Fragment : BaseUneteFragment(), Step, UnetePaso3View,
    ListenerSolicitudGPS, UnetePaso3.ListenerGpsNoActivo {

    private val presenter: UnetePaso3Presenter by injectFragment()

    private var content: IUnetePaso3? = null
    private lateinit var listenerGPS: ListenerSuscriberGPS
    private var shouldRequestPermission = true
    private var pasoBloqueado: Int = Constant.SIN_ZONA

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        validateDisable()
        btnContinuar?.setOnClickListener {

            KeyboardUtil.dismissKeyboard(context, view)
            if (content?.validate() == true) {
                content?.createModel()?.let {
                    mModel = it
                    pasoBloqueado = formularioView?.getPasoBloqueado() ?: Constant.NUMERO_CERO
                    presenter.geo(
                        formularioView?.pais().orEmpty(),
                        mModel?.latitud.orEmpty(),
                        mModel?.longitud.orEmpty(),
                        mModel?.lugarHijo.orEmpty()
                    )
                }
            }

        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        listenerGPS = activity as UneteActivity
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun validateDisable() {
        val validate = formularioView?.disableSteps() ?: false
        if (validate) content?.disable()
    }

    override fun paso() = Constant.NUMERO_TRES

    override fun getLayout() = R.layout.fragment_unete_paso_3

    override fun initView() {
        presenter.setView(this)
        val vw = UnetePaso3Factory.getView(activity as Context, formularioView?.pais(), this)
        content = vw as IUnetePaso3
        content?.registerListenerGSP(this)
        lyContainer?.removeAllViews()
        lyContainer?.addView(vw)
    }

    override fun validateMapRadio() {
        presenter.validateMapCircle()
    }

    override fun showMapRadio(radio: Int) {
        content?.drawCircleRestriction(radio)
    }

    override fun showLoading() {
        formularioView?.showLoading()
    }

    override fun hideLoading() {
        formularioView?.hideLoading()
    }

    override fun showError(message: String) {
        toastString(message)
    }

    override fun showSolicitudRechazada(message: String) {
        context?.solicitudRechazadaDialog(message) {
            activity?.finish()
        }?.show()
    }

    override fun verifyStep(): VerificationError? {
        return if (content?.validate() != true) {
            VerificationError(resources.getString(R.string.tx_complete_los_datos))
        } else {
            if (mEstado == Estado.Nuevo) {
                toast(R.string.unete_step_no_guardado)
                VerificationError(resources.getString(R.string.tx_complete_los_datos))
            } else null
        }
    }

    override fun onError(error: VerificationError) = Unit

    override fun onDestroy() {
        super.onDestroy()
        presenter.destroy()
    }

    override fun showZonificacion(zonificacion: GeoZonificacion) {
        context?.customDialog(R.layout.dialog_material) {
            tvTitle.setText(R.string.zonificacion_title)
            tvContent.text = getString(
                R.string.postulante_pertenece_a_zona_seccion,
                zonificacion.zona,
                zonificacion.seccion
            )
            tvContent.gravity = GravityCompat.START
            ivIcon.setImageResource(R.drawable.ic_location_green)
            btnCancel.visible()
            btnCancel.setText(R.string.zonificacion_cancelar)
            btnCancel.setOnClickListener {
                dismiss()
            }
            btnOk.setText(R.string.zonificacion_aceptar)
            btnOk.setOnClickListener {
                dismiss()

                mModel?.let {
                    it.codigoZona = zonificacion.zona
                    it.codigoSeccion = zonificacion.seccion
                    it.codigoTerritorio = zonificacion.territorio
                    it.respuestaGEO = zonificacion.respuesta
                    it.estadoGEO = UneteEstadoGEO.OK.value.toString()

                    if (it.estadoBurocrediticio == UneteEstadoCrediticio.PUEDE_SER_CONSULTORA.value.toString()
                        && (it.estadoTelefonico != UneteEstadoTelefonico.POR_VALIDAR.value &&
                            it.estadoTelefonico != UneteEstadoTelefonico.ZERO.value)
                    ) {
                        it.estadoPostulante = UneteEstado.EN_APROBACION_FFVV.estado.toString()
                    } else if (it.estadoTelefonico == UneteEstadoTelefonico.POR_VALIDAR.value) {
                        it.estadoPostulante = UneteEstado.GESTION_SAC.estado.toString()
                    }

                    if (paso() == pasoBloqueado && it.estadoTelefonico != UneteEstadoTelefonico.VALIDADO.value) {
                        it.paso = Constant.NUMERO_DOS
                    }

                    presenter.updatePostulante(it)
                }

            }
        }?.show()
    }

    override fun notInSection(postulanteModel: PostulanteModel) {
        context?.customDialog(R.layout.dialog_material) {
            tvTitle.setText(R.string.zonificacion_title)
            tvContent.setText(R.string.postulante_pertenece_otra_seccion)
            tvContent.gravity = GravityCompat.START
            ivIcon.setImageResource(R.drawable.ic_location_green)
            btnOk.setText(R.string.unete_aceptar)
            btnOk.setOnClickListener {
                dismiss()
                formularioView?.finalizar()
            }
        }?.show()
    }

    override fun showZonificacion(error: Exception) {

        when (error) {

            is ZonificacionNotFound -> {

                context?.customDialog(R.layout.dialog_material) {
                    tvTitle.setText(R.string.zonificacion_title)
                    tvContent.setText(R.string.unete_geo_failed)
                    tvContent.gravity = GravityCompat.START
                    ivIcon.setImageResource(R.drawable.ic_location_green)
                    btnOk.setText(R.string.zonificacion_aceptar)
                    btnOk.setOnClickListener {
                        dismiss()
                        mModel?.let {
                            it.estadoPostulante = UneteEstado.GESTION_SAC.estado.toString()
                            it.estadoGEO =
                                UneteEstadoGEO.NOENCONTROTERRITORIOSILATLONG.value.toString()

                            if (paso() == pasoBloqueado && it.estadoTelefonico != UneteEstadoTelefonico.VALIDADO.value) {
                                it.paso = Constant.NUMERO_DOS
                            }

                            presenter.updatePostulante(it)
                        }
                    }
                    btnCancel.visible()
                    btnCancel.setText(R.string.zonificacion_cancelar)
                    btnCancel.setOnClickListener {
                        dismiss()
                    }
                }?.show()

            }
            is NetworkConnectionException -> {

                mModel?.let {
                    it.estadoPostulante = UneteEstado.GESTION_SAC.estado.toString()
                    it.estadoGEO = UneteEstadoGEO.NOENCONTROTERRITORIOSILATLONG.value.toString()
                    presenter.updatePostulante(it)
                }

            }
            else -> {

                context?.customDialog(R.layout.dialog_material) {
                    tvTitle.setText(R.string.zonificacion_title)
                    tvContent.setText(R.string.error_conexion_reintentar)
                    tvContent.gravity = GravityCompat.START
                    ivIcon.setImageResource(R.drawable.ic_location_green)
                    btnOk.setText(R.string.unete_aceptar)
                    btnOk.setOnClickListener {
                        dismiss()
                    }
                }?.show()

            }
        }

    }

    private fun showMensajeRechazoBuro(title: String, msg: String) {
        context?.customDialog(R.layout.dialog_material) {
            tvTitle.text = title
            tvContent.text = msg
            tvContent.gravity = GravityCompat.START
            ivIcon.setImageResource(R.drawable.ic_location_green)
            btnOk.setText(R.string.accept)
            btnOk.setOnClickListener {
                dismiss()
                presenter.setIsUpdatePostulanteBuro(true)
                mModel?.let {
                    it.estadoPostulante = UneteEstado.RECHAZADOS.estado.toString()
                    presenter.updatePostulante(it)
                }
                formularioView?.finalizar()
            }
            btnCancel.visible()
            btnCancel.setText(R.string.cancelar)
            btnCancel.setOnClickListener {
                dismiss()
            }
        }?.show()
    }

    private fun showPagoContadoModal(bloqueos: ValidacionBuroResponse) {
        context?.customDialog(R.layout.dialog_pago_contado) {
            tvTitle.text = getString(R.string.unete_paso1_valid_consultora_title)
            tvContent.text = getString(R.string.unete_paso1_consultora_contado)
            ivIcon.setImageResource(R.drawable.ic_warning_alert)
            btnOk.setText(R.string.actualizacion_button_incorporar)
            btnCancel.visibility = View.VISIBLE
            btnCancel.setText(R.string.actualizacion_button_entendido)
            btnOk.setOnClickListener {
                dismiss()
                createPagoContadoModel(bloqueos)
            }
            btnCancel.setOnClickListener {
                dismiss()
                presenter.setIsUpdatePostulanteBuro(true)
                mModel?.estadoPostulante = UneteEstado.RECHAZADOS.estado.toString()
                presenter.updatePostulante(mModel!!)
                formularioView?.finalizar()
            }
        }?.show()
    }

    private fun createPagoContadoModel(bloqueos: ValidacionBuroResponse) {
        presenter.setIsUpdatePostulanteBuro(true)
        mModel?.tipoPago = TipoPago.CONTADO.id
        mModel?.estadoBurocrediticio = UneteEstadoCrediticio.PUEDE_SER_CONSULTORA.value.toString()
        bloqueos.enumEstadoCrediticio = UneteEstadoCrediticio.PUEDE_SER_CONSULTORA.value

        formularioView?.postulante(mModel!!)
        presenter.updatePostulante(mModel!!)

    }

    override fun initModel() = Unit

    override fun getModel(): PostulanteModel {
        return formularioView?.postulante() ?: PostulanteModel()
    }

    override fun updated(postulanteModel: PostulanteModel) {
        mModel = postulanteModel
        continuar(postulanteModel)
    }

    private fun continuar(postulanteModel: PostulanteModel) {
        if (paso() == pasoBloqueado &&
            mModel?.estadoTelefonico != UneteEstadoTelefonico.VALIDADO.value
        ) {
            formularioView?.setPostulante(postulanteModel)
            mostrarMensajeBloqueoPaso()
        } else {
            formularioView?.postulante(postulanteModel)
            formularioView?.continuar(Constant.NUMERO_CUATRO)
        }
    }

    override fun validacionExitosa(respuesta: ValidacionBuroResponse) {
        mModel?.let {
            it.estadoBurocrediticio = respuesta.enumEstadoCrediticio?.toString()
            continuar(it)
        }
    }

    override fun validarZonaPagoDeContado(respuesta: ValidacionBuroResponse) {
        val boolPagoDeContado = formularioView?.esPagoContado()
        if (boolPagoDeContado == true)
            showPagoContadoModal(respuesta)
        else
            showMensajeRechazoBuro(
                "Postulante rechazada",
                "La postulante no ha pasado las validaciones."
            )
    }

    override fun initEstado() {
        when (getEstado()) {
            Estado.Edicion -> {
                content?.disable(true)
                btnContinuar?.isEnabled = true
            }
            Estado.Visualizacion -> {
                content?.disable()
                btnContinuar?.isEnabled = false
            }
        }
    }

    override fun onResume() {
        listenerGPS.suscribirListener(this)
        super.onResume()
    }

    override fun onStop() {
        listenerGPS.desuscribirListener()
        content?.unRegisterListenerGSP()
        super.onStop()
    }

    override fun alResponderASolicitudGPS(requestCode: Int, resultCode: Int, data: Intent) {
        content?.onGPSRequestSuccess()
    }

    override fun requestGPS() {
        verificarConfiguracionUbicacion()
    }

    private fun verificarConfiguracionUbicacion() {
        if (context != null && DeviceUtil.hasGPS(context!!)) {
            val manager = activity!!.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                RequestGPS().requestGPS(activity!!)
            } else {
                content?.onGPSRequestActivated()
            }
        }
    }

    override fun checkLocationPermission(cb: () -> Unit) {
        if (context == null) return
        if (ContextCompat.checkSelfPermission(context!!, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
        ) {
            cb.invoke()
        } else if (shouldRequestPermission) {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 100)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 100 &&
            grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_DENIED &&
            !shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)
        ) {
            shouldRequestPermission = false
        }

    }

    private fun mostrarMensajeBloqueoPaso() {
        context?.customDialog(R.layout.dialog_material) {
            tvTitle.gone()
            tvContent.text = getString(R.string.alert_paso_bloqueado)
            ivIcon.gone()

            btnOk.text = getString(R.string.actualizacion_button_ok)
            btnCancel.visible()
            btnOk.setOnClickListener {
                dismiss()
            }
        }?.show()
    }

    override fun onSelected() = Unit

    override fun enableContinueButton(status: Boolean) {
        btnContinuar.isEnabled = status
    }

    override fun getCurrentRadius(posicionCentral: LatLng, posicionFinal: LatLng): Double {
        return presenter.obtenerRadioActual(posicionCentral, posicionFinal)
    }
}
