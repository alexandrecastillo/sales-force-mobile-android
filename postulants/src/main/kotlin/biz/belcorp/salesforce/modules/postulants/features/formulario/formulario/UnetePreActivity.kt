package biz.belcorp.salesforce.modules.postulants.features.formulario.formulario

import android.content.Context
import android.content.Intent
import android.os.Bundle
import biz.belcorp.salesforce.core.base.BaseActivity
import biz.belcorp.salesforce.core.utils.gone
import biz.belcorp.salesforce.core.utils.injectActivity
import biz.belcorp.salesforce.core.utils.visible
import biz.belcorp.salesforce.modules.postulants.R
import biz.belcorp.salesforce.modules.postulants.features.adapter.UnetePreStepperAdapter
import biz.belcorp.salesforce.modules.postulants.features.entities.PrePostulanteModel
import biz.belcorp.salesforce.modules.postulants.features.entities.UneteConfiguracionModel
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.postulantes.paso3.ListenerSolicitudGPS
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.postulantes.paso3.ListenerSuscriberGPS
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.prepostulantes.FormularioPreView
import com.google.android.gms.location.LocationSettingsStatusCodes
import kotlinx.android.synthetic.main.activity_unete.*

class UnetePreActivity : BaseActivity(), FormularioPreView, ListenerSuscriberGPS {

    private var configuracionUnete: UneteConfiguracionModel? = null
    private var id: Int = 0
    private var uuid: String? = null
    private var modelPre: PrePostulanteModel = PrePostulanteModel()
    private var listenerSolicitudGPS: ListenerSolicitudGPS? = null

    private val presenter: UnetePrePresenter by injectActivity()

    override fun getLayout() = R.layout.activity_unete

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter.setPreView(this)
        presenter.getConfiguracionUnete()

        id = intent.getIntExtra(UneteActivity.ID_KEY, 0)
        uuid = intent.getStringExtra(UneteActivity.UUID_KEY)

        btnBack.setOnClickListener {
            finalizar()
        }

    }

    companion object {
        private const val UUID_KEY = "UUID_KEY"
        private const val ID_KEY = "ID_KEY"
        @JvmStatic
        fun getCallingIntent(context: Context, uuid: String?, id: Int) {
            val intent = Intent(context, UnetePreActivity::class.java)
            intent.putExtra(ID_KEY, id)
            intent.putExtra(UUID_KEY, uuid)
            context.startActivity(intent)
        }
    }

    override fun suscribirListener(listener: ListenerSolicitudGPS) {
        listenerSolicitudGPS = listener
    }

    override fun desuscribirListener() {
        listenerSolicitudGPS = null
    }


    override fun pais() = presenter.getCountry()

    override fun paso() = modelPre.paso

    override fun continuar(paso: Int) {
        stpPasos?.currentStepPosition = paso - 1
    }

    override fun finalizar() {
        finish()
    }

    override fun prePostulante() = modelPre

    override fun prePostulante(prepostulante: PrePostulanteModel) {
        this.modelPre = prepostulante
        if (prepostulante.paso >= 2) {
            presenter.initPreStepper(prepostulante)
        }
    }

    override fun setPrePostulante(prepostulante: PrePostulanteModel) {
        this.modelPre = prepostulante
    }

    override fun uneteConfiguracion(uneteConfiguracion: UneteConfiguracionModel?) {
        this.configuracionUnete = uneteConfiguracion
        obtenerPrePostulante()
    }

    private fun obtenerPrePostulante() {
        presenter.obtenerPrePostulante(uuid, id)
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

    override fun initStepper(pasos: Int, position: Int) {
        stpPasos?.adapter = UnetePreStepperAdapter(supportFragmentManager, baseContext, pasos)
        stpPasos?.currentStepPosition = position
    }

    override fun showLoading() {
        progressBar?.visible()
    }

    override fun hideLoading() {
        progressBar?.gone()
    }

    override fun getPais() = presenter.getPais()

}
