package biz.belcorp.salesforce.modules.postulants.features.formulario.formulario

import android.content.Context
import android.content.Intent
import android.os.Bundle
import biz.belcorp.salesforce.core.base.BaseActivity
import biz.belcorp.salesforce.core.utils.gone
import biz.belcorp.salesforce.core.utils.injectActivity
import biz.belcorp.salesforce.core.utils.visible
import biz.belcorp.salesforce.modules.postulants.R
import biz.belcorp.salesforce.modules.postulants.core.domain.enums.PostulantQueue
import biz.belcorp.salesforce.modules.postulants.features.adapter.UneteStepperAdapter
import biz.belcorp.salesforce.modules.postulants.features.entities.PostulanteModel
import biz.belcorp.salesforce.modules.postulants.features.entities.UneteConfiguracionModel
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.postulantes.FormularioView
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.postulantes.paso3.ListenerSolicitudGPS
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.postulantes.paso3.ListenerSuscriberGPS
import biz.belcorp.salesforce.modules.postulants.utils.Constant
import com.google.android.gms.location.LocationSettingsStatusCodes
import kotlinx.android.synthetic.main.activity_unete.*


class UneteActivity : BaseActivity(), FormularioView, ListenerSuscriberGPS {

    private var configuracionUnete: UneteConfiguracionModel? = null

    private var model: PostulanteModel = PostulanteModel()

    private var id: Int = Constant.NUMERO_CERO
    private var uuid: String? = null
    private var disableSteps: Boolean = false
    private var pasoBloqueado: Int = Constant.SIN_ZONA
    private var zonaPagoContado = false
    private var zonaAutocompletadoDireccion = false

    private val parametroZona: String? by lazy {
        intent.getStringExtra(ZONA_KEY)
    }

    private val presenter: UnetePresenter by injectActivity()

    private var listenerSolicitudGPS: ListenerSolicitudGPS? = null

    override fun getLayout() = R.layout.activity_unete

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppThemeUnete)
        super.onCreate(savedInstanceState)
        presenter.setView(this)
        validarZonasSMS()
        verificarPagoContado()

        id = intent.getIntExtra(ID_KEY, Constant.NUMERO_CERO)
        uuid = intent.getStringExtra(UUID_KEY)
        disableSteps = intent.getBooleanExtra(DISABLE_STEPS_KEY, false)

        PostulantQueue.removeAllQueue()

        btnBack.setOnClickListener {
            finalizar()
        }

    }

    private fun validarZonasSMS() {
        presenter.listSMSZonas(parametroZona)
    }

    private fun verificarPagoContado() {
        presenter.listZonasPagoContado(parametroZona)
    }

    companion object {
        const val UUID_KEY = "UUID_KEY"
        const val ID_KEY = "ID_KEY"
        const val DISABLE_STEPS_KEY = "DISABLE_STEPS_KEY"
        const val ZONA_KEY = "ZONA_KEY"

        @JvmStatic
        fun getCallingIntent(
            context: Context, uuid: String?, id: Int, zona: String?, disableSteps: Boolean = false
        ) {
            val intent = Intent(context, UneteActivity::class.java)
            intent.putExtra(ID_KEY, id)
            intent.putExtra(UUID_KEY, uuid)
            intent.putExtra(DISABLE_STEPS_KEY, disableSteps)
            intent.putExtra(ZONA_KEY, zona)
            context.startActivity(intent)
        }
    }

    override fun showLoading() {
        progressBar?.visible()
    }

    override fun hideLoading() {
        progressBar?.gone()
    }

    override fun initStepper(pasos: Int, position: Int) {
        stpPasos?.adapter = UneteStepperAdapter(supportFragmentManager, baseContext, pasos)
        stpPasos?.currentStepPosition = position
    }

    override fun continuar(paso: Int) {
        stpPasos?.currentStepPosition = paso - 1
    }

    override fun pais() = presenter.getPais()

    override fun paso() = model.paso

    override fun finalizar() {
        finish()
    }

    override fun postulante(postulante: PostulanteModel) {
        this.model = postulante
        if (postulante.paso >= Constant.NUMERO_DOS) {
            presenter.initStepper(postulante)
        }
    }

    override fun postulante() = model

    override fun setPostulante(postulante: PostulanteModel) {
        this.model = postulante
    }

    override fun uneteConfiguracion(uneteConfiguracion: UneteConfiguracionModel?) {
        this.configuracionUnete = uneteConfiguracion
        obtenerPostulante()
    }

    private fun obtenerPostulante() {
        presenter.obtenerPostulante(uuid, id)
    }

    override fun uneteConfiguracion() = configuracionUnete

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            LocationSettingsStatusCodes.RESOLUTION_REQUIRED ->
                if (listenerSolicitudGPS != null && data != null) {
                    listenerSolicitudGPS!!.alResponderASolicitudGPS(requestCode, resultCode, data)
                }
        }
    }

    override fun suscribirListener(listener: ListenerSolicitudGPS) {
        listenerSolicitudGPS = listener
    }

    override fun desuscribirListener() {
        listenerSolicitudGPS = null
    }

    override fun validarZonaSMS(pasoBloqueado: Int) {
        this.pasoBloqueado = pasoBloqueado
        presenter.getConfiguracionUnete()
    }

    override fun validarPagoContado() {
        zonaPagoContado = true
    }

    override fun getPasoBloqueado() = pasoBloqueado

    override fun esPagoContado() = zonaPagoContado

    override fun getPais() = presenter.getPais()

    override fun disableSteps() = disableSteps
}
