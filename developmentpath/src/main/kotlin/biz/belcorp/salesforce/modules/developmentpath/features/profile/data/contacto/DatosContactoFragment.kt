package biz.belcorp.salesforce.modules.developmentpath.features.profile.data.contacto

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import biz.belcorp.mobile.components.core.extensions.gone
import biz.belcorp.mobile.components.core.extensions.visible
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.domain.entities.analytics.TagAnalytics
import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.core.features.utils.lazyPersonIdentifier
import biz.belcorp.salesforce.core.features.utils.withPersonIdentifier
import biz.belcorp.salesforce.core.utils.*
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.analytics.FirebaseAnalyticsPresenter
import biz.belcorp.salesforce.modules.developmentpath.utils.cambiarVisibilidad
import kotlinx.android.synthetic.main.fragment_datos_contacto.*
import org.koin.android.viewmodel.ext.android.viewModel

class DatosContactoFragment : BaseFragment() {

    private val personIdentifier by lazyPersonIdentifier()

    override fun getLayout() = R.layout.fragment_datos_contacto

    private val viewModel by viewModel<DatosContactoViewModel>()
    private val firebaseAnalyticsPresenter by injectFragment<FirebaseAnalyticsPresenter>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        escucharAcciones()
    }

    private fun setupViewModel() {
        viewModel.viewState.observe(viewLifecycleOwner, viewStateObserver)
        viewModel.obtener(personIdentifier)
    }

    private val viewStateObserver = Observer<DatosContactoViewState> {
        when (it) {
            DatosContactoViewState.PostulantsViewMode -> {
                ocultarDatosEnPosibleConsultora()
                expandirPorPosibleConsultora()
            }
            DatosContactoViewState.ManagersViewMode -> {
                ocultarDatosEnGerenteZona()
            }
            is DatosContactoViewState.ShowInfo -> {
                pintar(it.model)
                mostrarCamposConValores(it.model)
            }
        }
    }

    private fun escucharAcciones() {
        flDatosContacto?.setOnClickListener {
            cvDatosContacto?.cambiarVisibilidad()
            firebaseAnalyticsPresenter.enviarEvento(TagAnalytics.EVENTO_CONTACTO)
        }
        ivWhatsAppCelular?.setOnClickListener { context?.enviarAWhatsapp(tvCelular?.text.toString()) }
        tvWhatsAppOtroTelefono?.setOnClickListener { context?.enviarAWhatsapp(tvOtroTelefono?.text.toString()) }
        ivLlamadaCelular?.setOnClickListener { this.llamarATelefono(tvCelular?.text.toString()) }
        ivLlamadaTelefonoCasa?.setOnClickListener { this.llamarATelefono(tvTelefonoCasa?.text.toString()) }
        ivLlamadaOtroTelefono?.setOnClickListener { this.llamarATelefono(tvOtroTelefono?.text.toString()) }
        ivCorreoElectronico?.setOnClickListener { context?.enviarACorreo(tvCorreoElectronico?.text.toString()) }
    }

    private fun ocultarDatosEnPosibleConsultora() {
        flTelefonoCasa?.gone()
        flOtroTelefono?.gone()
        flAniversario?.gone()
    }

    private fun ocultarDatosEnGerenteZona() {
        flOtroTelefono?.gone()
        flDireccion?.gone()
        flCumpleanios?.gone()
        flAniversario?.gone()
    }

    private fun expandirPorPosibleConsultora() {
        cvDatosContacto?.cambiarVisibilidad()
    }

    private fun pintar(datos: DatosContactoModel) {
        tvCelular?.text = datos.celular
        tvTelefonoCasa?.text = datos.telefonoCasa
        tvOtroTelefono?.text = datos.otroTelefono
        tvDocumentoIdentidad?.text = datos.documentoIdentidad
        tvDireccion?.text = datos.direccion
        tvCorreoElectronico?.text = datos.correoElectronico
        tvCumpleanos?.text = datos.cumpleanios
        tvAniversario?.text = datos.aniversario
    }

    private fun mostrarCamposConValores(datosContacto: DatosContactoModel) {
        if (datosContacto.telefonoCasa.isNotEmptyNeitherDash()) {
            mostrarContactarConTelefonoCasa()
        }
        if (datosContacto.celular.isNotEmptyNeitherDash()) {
            mostrarContactarConCelular()
        }
        if (datosContacto.otroTelefono.isNotEmptyNeitherDash()) {
            mostrarContactarConOtroTelefono()
        }
        if (datosContacto.correoElectronico.isNotEmpty()) {
            mostrarContactarConCorreoElectronico()
        }
    }

    private fun mostrarContactarConCelular() {
        llContactoCelular?.visible()
    }

    private fun mostrarContactarConTelefonoCasa() {
        llContactoTelefonoCasa?.visible()
    }

    private fun mostrarContactarConOtroTelefono() {
        llContactoOtroTelefono?.visible()
    }

    private fun mostrarContactarConCorreoElectronico() {
        llOpcionesCorreoElectronico?.visible()
    }

    companion object {

        fun newInstance(personIdentifier: PersonIdentifier) =
            DatosContactoFragment()
                .withPersonIdentifier(personIdentifier)
    }

}
