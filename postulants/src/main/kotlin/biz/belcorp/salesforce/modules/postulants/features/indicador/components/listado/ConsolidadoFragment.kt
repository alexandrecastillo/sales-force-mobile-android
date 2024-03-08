package biz.belcorp.salesforce.modules.postulants.features.indicador.components.listado

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.constants.CountryISO
import biz.belcorp.salesforce.core.domain.entities.configuration.Configuration
import biz.belcorp.salesforce.core.domain.entities.pais.Pais
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.events.EventSubject
import biz.belcorp.salesforce.core.events.sync.SyncState
import biz.belcorp.salesforce.core.events.utils.consumableObserver
import biz.belcorp.salesforce.core.events.utils.observeEventSubject
import biz.belcorp.salesforce.core.utils.*
import biz.belcorp.salesforce.modules.postulants.R
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.ValidacionBuroResponse
import biz.belcorp.salesforce.modules.postulants.core.domain.enums.*
import biz.belcorp.salesforce.modules.postulants.features.entities.*
import biz.belcorp.salesforce.modules.postulants.features.indicador.components.LoadingView
import biz.belcorp.salesforce.modules.postulants.features.indicador.components.listado.adapters.PostulantAdapter
import biz.belcorp.salesforce.modules.postulants.features.indicador.components.listado.dialog.OnRechazoInteractionListener
import biz.belcorp.salesforce.modules.postulants.features.indicador.components.listado.dialog.RechazarPostulanteFragment
import biz.belcorp.salesforce.modules.postulants.features.indicador.components.listado.dialog.RechazarPostulanteProactivoFragment
import biz.belcorp.salesforce.modules.postulants.features.indicador.components.listado.enums.EstadoValidacion
import biz.belcorp.salesforce.modules.postulants.features.indicador.components.listado.filters.FilterAdapter
import biz.belcorp.salesforce.modules.postulants.features.indicador.components.listado.filters.FilterView
import biz.belcorp.salesforce.modules.postulants.features.indicador.entities.BaseGeo
import biz.belcorp.salesforce.modules.postulants.utils.*
import com.google.android.material.textview.MaterialTextView
import kotlinx.android.synthetic.main.dialog_material.*
import kotlinx.android.synthetic.main.dialog_material_condiciones.*
import kotlinx.android.synthetic.main.dialog_material_condiciones.btnOk
import kotlinx.android.synthetic.main.fragment_unete_listado.*
import kotlinx.android.synthetic.main.sections_navigation_bar.*
import kotlinx.android.synthetic.main.view_aprobada.*

class ConsolidadoFragment : BaseFragment(), ConsolidadoView,
    PostulantAdapter.PostulanteAdapterView, PostulantAdapter.EvaluacionAdapterListener,
    OnRechazoInteractionListener {

    override fun getLayout() = R.layout.fragment_unete_listado

    private val consolidadoPresenter: ConsolidadoPresenter by injectFragment()

    private val mListener by lazy { parentFragment as? ConsolidadoFragmentListener }

    var savedInstanceState: Bundle? = null
    private var configuracionUnete: UneteConfiguracionModel? = null
    private var mChosenSection = Constant.EMPTY_STRING
    private var mChosenZona: String? = null

    private var listado: Int = UneteListado.EVALUACION.tipo
    private var postulanteSeleccionada: PostulanteModel? = null

    private var campaignSelected = Constant.NONE_LEVEL_NUMBER
    private var tipoPagoSelected = UneteAprobadaFiltro.TODOSTIPODEPAGO.code
    private var isConsolidado: Boolean = true
    private val loadingView: LoadingView? by lazy { parentFragment as? LoadingView }
    private var hayPostulantes: Boolean = false
    private var zonaPagoContado = false

    private var configuration: Configuration? = null

    companion object {
        private const val UNETE_CONSOLIDADO_KEY = "UNETE_CONSOLIDADO_KEY"
        private const val LISTADO_VIEW_KEY = "LISTADO_VIEW_KEY"
        private const val SECCION_ZONA_REGION_KEY = "SECCION_ZONA_REGION_KEY"

        fun newInstance(
            listadoId: Int,
            geoModelList: List<BaseGeo>,
            isConsolidado: Boolean = true
        ): ConsolidadoFragment {
            val fragment = ConsolidadoFragment()
            val bundle = Bundle()
            bundle.putBoolean(UNETE_CONSOLIDADO_KEY, isConsolidado)
            if (geoModelList.isNotEmpty()) bundle.putParcelableArrayList(
                SECCION_ZONA_REGION_KEY, geoModelList as ArrayList<out Parcelable>
            )
            bundle.putInt(LISTADO_VIEW_KEY, listadoId)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun obtenerPostulanteSeleccionado() = postulanteSeleccionada

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSyncObservers()
        consolidadoPresenter.setView(this)
        initConfiguration()
        consolidadoPresenter.getGZOptions()
    }

    override fun onResume() {
        super.onResume()
        consolidadoPresenter.getConfiguracionUnete()
        load()
    }

    private fun verificarPagoContado() {
        consolidadoPresenter.validarZonasPagoContado()
    }

    private fun setConfiguration() {
        consolidadoPresenter.getConfiguration()
    }

    override fun onDestroy() {
        super.onDestroy()
        consolidadoPresenter.onDestroy()
    }

    override fun showLoading() {
        loadingView?.showLoading()
    }

    override fun hideLoading() {
        loadingView?.hideLoading()
    }

    override fun showGZzonesFilter() {

        tvCurrentSection?.text = getString(R.string.my_zone)
        tvCurrentSection?.isSelected = true

        sections_navigation_bar_detail?.visible()
        initializeListeners()
        showSections()

        context?.let {
            tvCurrentSection?.setTextColor(it.getCompatColor(R.color.white_5))
        }

    }

    override fun showGRzonesFilter() {
        tvCurrentSection?.gone()

        sections_navigation_bar_detail?.visible()
        initializeListeners()
        showZones()
    }

    override fun toAssignValueToSE(section: String) {
        mChosenSection = section
    }

    override fun showListado(list: List<BasePostulante>) {
        getAdapter(list, rwListado)
        rwListado?.visible()
        rwPreListado?.gone()
    }

    override fun showPreListado(list: List<BasePostulante>) {
        getAdapter(list, rwPreListado)
        rwListado?.gone()
        rwPreListado?.visible()
    }

    override fun showListadoEmpty() {
        if (isConsolidado) showListadoEmptyPostulante() else showListadoEmptyPrepostulante()
    }

    private fun showListadoEmptyPostulante() {
        txtListadoEmpty?.text = context?.getString(
            R.string.unete_listado_empty,
            (spnEstados?.selectedItem() as UneteListaModel).descripcion?.toLowerCase()
        )
        txtListadoEmpty?.visible()
        rwListado?.gone()
        tvwListadoFound?.gone()
        cvwFiltroTipoDePago?.gone()
        cvwFiltroAprobado?.gone()
    }

    private fun showListadoEmptyPrepostulante() {
        rwListado?.gone()
        if (consolidadoPresenter.getPais() == Pais.PUERTORICO.codigoIso) {
            consolidadoPresenter.listPostulantes(
                UneteListado.PRE_INSCRITAS.tipo,
                mChosenSection,
                mChosenZona
            )
        } else {
            txtListadoEmpty?.visible()
        }
    }

    override fun showPreListadoEmpty() {
        if (hayPostulantes) {
            txtListadoEmpty?.gone()
        }
        rwPreListado?.gone()
    }

    override fun adapterSetCount() {
        val count = rwListado?.adapter?.itemCount ?: rwPreListado?.adapter?.itemCount
        tvwListadoFound?.text = wasFound(count)
        tvwListadoFound?.visible()
        hideLoading()
    }


    override fun showZonaRiesgo(esZonaRiesgo: Boolean) {
        val stringResourse =
            if (consolidadoPresenter.getCountryiso().equals(CountryISO.PERU, true)) {
                R.string.unete_confirmacion_aprobada_only_pe
            } else {
                R.string.unete_confirmacion_aprobada
            }

        val codRol = consolidadoPresenter.getRol()
        val msgConfirmacion = getString(
            stringResourse,
            if (codRol == Rol.GERENTE_REGION.codigoRol) getString(R.string.text_gerente_regional)
            else getString(R.string.text_gerente_zona)
        )

        context?.customDialog(R.layout.dialog_material) {
            ivIcon?.gone()
            tvTitle?.gone()
            tvContent?.text = msgConfirmacion
            btnOk?.text = getString(R.string.unete_si)
            btnOk?.setOnClickListener {
                var estado = UneteEstado.GENERANDO_CODIGO.estado
                var subestado = UneteSubEstadoGenerandoCodigo.POR_GERENTE_ZONA.estado
                if ((postulanteSeleccionada?.pais in listOf(
                        Pais.GUATEMALA.codigoIso,
                        Pais.CHILE.codigoIso
                    ))
                ) {
                    estado = UneteEstado.EN_APROBACION_SAC.estado
                }
                if (postulanteSeleccionada?.requiereAprobacionSAC == true || esZonaRiesgo) {
                    estado = UneteEstado.EN_APROBACION_SAC.estado
                    subestado = UneteSubEstadoEnAprobacionSAC.POR_GZ.estado
                }

                if (postulanteSeleccionada?.estadoPostulante == UneteEstado.PENDIENTE_CAMBIO_MODELO.toString()) {
                    estado = UneteEstado.APROBACION_CAMBIO_MODELO.estado
                }

                consolidadoPresenter.updateEstadoAprobada(
                    postulanteSeleccionada?.pais.orEmpty(),
                    postulanteSeleccionada?.solicitudPostulanteID!!,
                    estado, subestado, Constant.EMPTY_STRING, Constant.EMPTY_STRING
                )
                dismiss()
            }
            btnCancel?.text = getString(R.string.unete_no)
            btnCancel?.visible()
            btnCancel?.setOnClickListener {
                dismiss()
            }
        }?.show()
    }

    override fun postulanteAprobada(success: Boolean) {
        if (success) {
            load()
            context?.customDialog(R.layout.view_aprobada) {
                txtMensaje?.text = getString(
                    R.string.unete_aprobada_body,
                    postulanteSeleccionada?.primerNombre,
                    postulanteSeleccionada?.apellidoPaterno
                )

                btnAcept?.setText(R.string.unete_aceptar)
                btnAcept?.setOnClickListener {
                    dismiss()
                }
            }?.show()
        }
    }

    override fun postulanteNoInteresada(success: Boolean) {
        load()
    }

    override fun validacionExitosa() {
        if (postulanteSeleccionada?.pais == Pais.BOLIVIA.codigoIso) {
            consolidadoPresenter.validacionVerificacionPorZonas()
        } else {
            load()
        }
    }

    override fun showMensajeRechazoBuro(title: String, message: String) {
        if (postulanteSeleccionada?.pais != Pais.BOLIVIA.codigoIso) {
            context?.customDialog(R.layout.dialog_material) {
                ivIcon?.gone()
                tvTitle?.text = title
                tvContent?.text = message
                btnOk?.text = getString(R.string.accept)
                btnOk?.setOnClickListener {
                    dismiss()
                    load()
                }
            }?.show()
        } else load()
    }

    override fun uneteConfiguracion(uneteConfiguracion: UneteConfiguracionModel?) {
        this.configuracionUnete = uneteConfiguracion
    }

    override fun uneteConfiguracion(): UneteConfiguracionModel? {
        return this.configuracionUnete
    }

    override fun showObservacionDevueltoSac(observacion: String) {
        context?.customDialog(R.layout.dialog_material) {
            ivIcon?.gone()
            tvTitle?.text = getString(R.string.devuelto_sac_mensaje)
            tvContent?.text = observacion
            btnOk?.text = getString(R.string.accept)
            btnOk?.setOnClickListener {
                dismiss()
            }
        }?.show()
    }

    override fun validarEdadZonaRiesgo(postulanteModel: PostulanteModel) {
        if (postulanteSeleccionada?.pais == Pais.BOLIVIA.codigoIso) {
            validarBuro(postulanteModel)
        } else {
            validarZonaRiesgo()
        }
    }

    override fun showFilter(list: List<FiltroAprobadoUneteModel>) {
        filtroAprobadoView.showList(list)
        cvwFiltroAprobado?.visible()
        lvwFiltroAprobado?.gone()
        lvwFiltroAprobado?.setOnItemClickListener { parent, _, position, _ ->
            val selected = parent?.adapter?.getItem(position) as FiltroAprobadoUneteModel
            selected.campaign?.let {
                campaignSelected = it
                edtFiltroAprobado?.performClick()
                this.subFilter(selected)
            }
        }
    }


    override fun showPaymentTypeFilter(list: List<FiltroAprobadoUneteModel>) {
        if (list.isNotEmpty()) {
            cvwFiltroTipoDePago?.visible()
            filtroAprobadoView.showPaymentTypeList(list)
            lvwFiltroTipoDePago?.gone()
            lvwFiltroTipoDePago?.setOnItemClickListener { parent, _, position, _ ->
                val selected = parent?.adapter?.getItem(position) as FiltroAprobadoUneteModel
                tipoPagoSelected = selected.id
                edtFiltroTipoDePago.performClick()
                this.subFilterTipoDePago(selected)
            }
        }
    }

    override fun validarZonaRiesgo() {
        consolidadoPresenter.validarZonaRiesgo(
            postulanteSeleccionada?.codigoZona.orEmpty()
                + postulanteSeleccionada?.codigoSeccion.orEmpty()
        )
    }

    override fun showRevisionDocumentoSAC(revisionDocumentoSac: Boolean) {
        val stringResourse =
            if (consolidadoPresenter.getCountryiso().equals(CountryISO.PERU, true)) {
                R.string.unete_confirmacion_aprobada_only_pe
            } else {
                R.string.unete_confirmacion_aprobada
            }

        val codRol = consolidadoPresenter.getRol()
        val msgConfirmacion = getString(
            stringResourse, if (codRol == Rol.GERENTE_REGION.codigoRol)
                getString(R.string.text_gerente_regional)
            else getString(R.string.text_gerente_zona)
        )

        context?.customDialog(R.layout.dialog_material) {
            ivIcon?.gone()
            tvTitle?.gone()
            tvContent?.text = msgConfirmacion
            btnOk?.text = getString(R.string.unete_si)
            btnOk?.setOnClickListener {
                dismiss()
                var estado = UneteEstado.GENERANDO_CODIGO.estado
                var subestado = UneteSubEstadoGenerandoCodigo.POR_GERENTE_ZONA.estado
                if ((postulanteSeleccionada?.pais == Pais.GUATEMALA.codigoIso)) {
                    estado = UneteEstado.EN_APROBACION_SAC.estado
                }
                if (postulanteSeleccionada?.requiereAprobacionSAC == true || revisionDocumentoSac) {
                    estado = UneteEstado.EN_APROBACION_SAC.estado
                    subestado = UneteSubEstadoEnAprobacionSAC.POR_GZ.estado
                }

                if (postulanteSeleccionada?.estadoPostulante == UneteEstado.PENDIENTE_CAMBIO_MODELO.toString()) {
                    estado = UneteEstado.APROBACION_CAMBIO_MODELO.estado
                }

                postulanteSeleccionada?.solicitudPostulanteID?.let {
                    consolidadoPresenter.updateEstadoAprobada(
                        postulanteSeleccionada?.pais.orEmpty(),
                        it, estado, subestado, Constant.EMPTY_STRING, Constant.EMPTY_STRING
                    )
                }

            }
            btnCancel?.text = getString(R.string.unete_no)
            btnCancel?.visible()
            btnCancel?.setOnClickListener {
                dismiss()
            }
        }?.show()

    }

    override fun showTipoPostulanteFilter(list: MutableList<UneteListaModel>) {
        spnEstados?.load(list)

        spnEstados?.onItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {

                val selected = parent?.adapter?.getItem(position) as UneteListaModel
                listado = selected.id ?: Constant.NUMERO_CERO
                cvwFiltroTipoDePago?.gone()
                cvwFiltroAprobado?.gone()

                when (listado) {
                    UneteListado.APROBADOS.tipo -> {
                        consolidadoPresenter.fillApprovedFilter()
                        if (zonaPagoContado) {
                            tipoPagoSelected = UneteAprobadaFiltro.TODOSTIPODEPAGO.code
                            consolidadoPresenter.fillPaymentTypeFilter()
                        }
                    }
                    UneteListado.PRE_APROBADOS.tipo -> {
                        if (zonaPagoContado) {
                            campaignSelected = Constant.NONE_LEVEL_NUMBER
                            consolidadoPresenter.fillPaymentTypeFilter()
                        }
                    }
                }

                load()
            }

            override fun onNothingSelected(parent: AdapterView<*>) = Unit
        })

        spnEstados?.selectionId(listado)
        spnEstados?.setValidationIconVisibility(false)
    }

    override fun updateEstado(accion: UneteAccionFFVV, postulanteModel: PostulanteModel) {
        postulanteSeleccionada = postulanteModel
        when (accion) {
            UneteAccionFFVV.RECHAZAR -> {
                postulanteModel.pais?.let {
                    val dialogFragment = RechazarPostulanteFragment.newInstance(
                        it,
                        postulanteModel.solicitudPostulanteID,
                        UneteEstado.RECHAZADOS.estado,
                        UneteSubEstadoRechazada.POR_GERENTE_ZONA.estado
                    )
                    dialogFragment.mListener = this
                    dialogFragment.show(childFragmentManager, "RechazarPostulante")
                }
            }
            UneteAccionFFVV.APROBAR -> {
                consolidadoPresenter.listValidarRangoEdad(postulanteModel)
            }
            UneteAccionFFVV.VALIDAR -> {
                validarBuro(postulanteModel)
            }
        }
    }

    override fun updatePreEstado(prePostulanteModel: PrePostulanteModel) {
        prePostulanteModel.pais?.let {
            val dialogPreFragment = RechazarPostulanteFragment.newInstance(
                it,
                prePostulanteModel.solicitudPrePostulanteID,
                UneteEstado.RECHAZADOS.estado, UneteSubEstadoRechazada.POR_GERENTE_ZONA.estado
            )
            dialogPreFragment.mListener = this
            dialogPreFragment.esPostulante = false
            dialogPreFragment.show(childFragmentManager, "RechazarPostulante")
        }
    }

    override fun postulanteRechazada() {
        load()
        toast(R.string.unete_listado_rechaza_msg)
    }

    override fun devueltoSac(solicitudPostulanteId: Int) {
        consolidadoPresenter.getObservacionDevueltoSac(solicitudPostulanteId)
    }

    override fun context(): Context {
        return context!!
    }

    override fun showRespuestaCondicion(observacion: Int, valido: Boolean) {
        context?.customDialog(R.layout.dialog_material_condiciones) {

            ivIconCondiciones?.setImageResource(obtenerIcono(observacion))

            when (observacion) {
                EstadoValidacion.VALIDACION_CREDITICIA.estado -> {
                    tvContent1?.text = contenidoCrediticia1(valido)
                    tvContent2?.text = contenidoCrediticia2(valido)
                }
                EstadoValidacion.VALIDACION_TELEFONICA.estado -> {
                    tvContent1?.text = contenidoTelefono1(valido)
                    tvContent2?.text = contenidoTelefono2(valido)
                }
                EstadoValidacion.VALIDACION_UBICACION.estado -> {
                    tvContent1?.text = contenidoUbicacion1(valido)
                    tvContent2?.text = contenidoUbicacion2(valido)
                }
            }

            btnOk?.text = getString(R.string.agree)
            btnOk?.setOnClickListener {
                dismiss()
            }
        }?.show()
    }

    override fun postulanteNoIntersada(postulanteModel: PostulanteModel) {
        postulanteSeleccionada = postulanteModel
        postulanteSeleccionada?.solicitudPostulanteID?.let {
            consolidadoPresenter.updateEstadoNoInteresada(
                postulanteSeleccionada?.pais.orEmpty(),
                it,
                (postulanteSeleccionada?.estadoPostulante ?: "0").toInt(),
                UneteEstado.PROACTIVO_NO_INTERESADA.estado
            )
        }
    }

    override fun openWhatsapp(postulanteModel: PostulanteModel) {
        val toast =
            Toast.makeText(requireContext(), R.string.unete_no_numero_whatsapp, Toast.LENGTH_LONG)

        postulanteModel.celular?.let { c ->
            if (c.trim().isEmpty()) {
                toast.show()
                return
            }

            val pais = consolidadoPresenter.getPais()

            val prefijo = Pais.find(pais)?.prefijo ?: Constant.EMPTY_STRING

            val url = "https://api.whatsapp.com/send?phone=$prefijo${postulanteModel.celular}"
            openExternalWebPage(url)
        } ?: run {
            toast.show()
        }
    }

    override fun onProactivaPreAprobadaUpdateEstado(status: Int, postulanteModel: PostulanteModel) {
        if (status == UneteProactivaPreAprobadaStatus.APROBAR.value) {
            context?.customDialog(R.layout.dialog_material) {
                tvTitle?.gone()
                ivIcon?.gone()
                tvContent.text = getString(R.string.proactivaPreAprobadoWarning)
                btnOk?.setText(R.string.unete_si)
                btnOk?.setOnClickListener {
                    consolidadoPresenter.actualizarPostulanteProactivo(
                        estado = status,
                        solicitudPostulanteId = postulanteModel.solicitudPostulanteID.toString(),
                        nomPostulante = postulanteModel.primerNombre ?: Constant.EMPTY_STRING
                    )
                    dismiss()
                }
                btnCancel?.setText(R.string.unete_no)
                btnCancel?.visible()
                btnCancel?.setOnClickListener {
                    dismiss()
                }
            }?.show()
        } else {
            val dialogRechazo = RechazarPostulanteProactivoFragment.newInstance().apply {
                setListener(object : OnRechazoInteractionListener {
                    override fun postulanteProactivaRechazada(motivo: String) {
                        consolidadoPresenter.actualizarPostulanteProactivo(
                            estado = status,
                            solicitudPostulanteId = postulanteModel.solicitudPostulanteID.toString(),
                            nomPostulante = postulanteModel.primerNombre ?: Constant.EMPTY_STRING,
                            motivoRechazo = motivo
                        )
                    }
                })
            }

            dialogRechazo.show(
                childFragmentManager,
                RechazarPostulanteProactivoFragment::class.simpleName
            )

        }
    }

    override fun showValidacionGZ(postulanteModel: PostulanteModel) {
        val stringResourse =
            if (consolidadoPresenter.getCountryiso().equals(CountryISO.PERU, true)) {
                R.string.unete_validacion_GZ_only_pe
            } else {
                R.string.unete_validacion_GZ
            }

        context?.customDialog(R.layout.dialog_material) {
            tvTitle?.gone()
            ivIcon?.gone()
            tvContent.text = getString(stringResourse)
            btnOk?.setText(R.string.unete_si)
            btnOk?.setOnClickListener {
                dismiss()
                postulanteModel.estadoTelefonico = UneteEstadoTelefonico.VALIDADO_POR_GZ.value
                consolidadoPresenter.updatePostulante(postulanteModel)
            }
            btnCancel?.setText(R.string.unete_no)
            btnCancel?.visible()
            btnCancel?.setOnClickListener {
                dismiss()
            }
        }?.show()
    }

    override fun restaurarPantalla() {
        onResume()
    }

    private fun load() {
        consolidadoPresenter.listPostulantes(listado, mChosenSection, mChosenZona)
    }

    private fun subFilter(selected: FiltroAprobadoUneteModel) {
        filterAdapter(selected, edtFiltroAprobado, selected.campaign, tipoPagoSelected)
    }

    private fun subFilterTipoDePago(selected: FiltroAprobadoUneteModel) {
        filterAdapter(selected, edtFiltroTipoDePago, campaignSelected, selected.id)
    }

    private fun initializeSearchBox() {
        searchBox?.visible()
        txtSearch?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) =
                Unit

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) = Unit

            override fun afterTextChanged(s: Editable) {
                (rwListado?.adapter as? PostulantAdapter<*>)?.filterSearch(s.toString())
                if (rwPreListado?.adapter != null) {
                    (rwPreListado?.adapter as? PostulantAdapter<*>)?.filterSearch(s.toString())
                }
            }
        })
    }

    private fun filterAdapter(
        selected: FiltroAprobadoUneteModel, view: EditText?, campaignId: Int?, tipoPagoId: Int
    ) {
        view?.setText(selected.description)
        view?.tag = selected.id
        val postulantAdapter = rwListado?.adapter as? PostulantAdapter<*>
        postulantAdapter?.filter(campaignId, tipoPagoId)
        rwListado?.visible()
        txtListadoEmpty?.gone()
    }

    private fun showSections() {
        var sections: ArrayList<BaseGeo>? = null
        arguments?.let {
            sections = it.getParcelableArrayListCompat(SECCION_ZONA_REGION_KEY, BaseGeo::class.java)
        }

        sections?.let {
            if (it.isNotEmpty()) {
                if (sections?.size == Constant.NUMERO_UNO)
                    mChosenSection = it[Constant.NUMERO_CERO].codigo.orEmpty()

                for (model in it) {
                    initSectionZoneRegionCircles(model.codigo.orEmpty())
                }
            }
        }
    }

    private fun showZones() {
        val zones: List<BaseGeo> =
            arguments?.getParcelableArrayList<BaseGeo>(SECCION_ZONA_REGION_KEY)
                ?.map {
                    it.apply {
                        descripcion = formatDescription(it.descripcion)
                    }
                }
                ?.distinctBy { it.descripcion }
                ?: emptyList()

        if (zones.isNotEmpty()) {
            mChosenZona = zones[Constant.NUMERO_CERO].descripcion.orEmpty()

            for (model in zones) {
                initZoneRegionCircles(
                    model.descripcion.orEmpty(),
                    mChosenZona == model.descripcion.orEmpty()
                )
            }
        }
    }

    private fun formatDescription(description: String?): String {
        return description?.replace(Constant.PREFIX_ZONE, Constant.EMPTY_STRING)
            ?.substring(Constant.NUMERO_CERO, Constant.NUMERO_CUATRO).orEmpty()
    }

    private fun tituloBotonEditarVer(): String {
        var text = Constant.EMPTY_STRING
        activity?.resources?.let {
            text = when (consolidadoPresenter.esSocia()) {
                true -> it.getString(R.string.ver_informacion)
                false -> it.getString(R.string.editar_datos)
            }
        }
        return text
    }

    private fun wasFound(count: Int?): String {
        return String.format(
            getString(
                when (count) {
                    Constant.NUMERO_UNO -> R.string.unete_filter_found
                    else -> R.string.unete_filter_found_plural
                }
            ), count
        )
    }

    private fun initConfiguration() {
        arguments?.safeAlso {
            listado = it.getInt(LISTADO_VIEW_KEY)
        }
        validateConsolidado()
        verificarPagoContado()
        setConfiguration()
    }

    private fun validateConsolidado() {

        arguments?.let {
            isConsolidado = it.getBoolean(UNETE_CONSOLIDADO_KEY, true)
        }

        estadoBox?.visibility = if (isConsolidado) View.VISIBLE else View.GONE
        if (!isConsolidado) initializeSearchBox()
    }

    private fun initSectionZoneRegionCircles(codigo: String) {
        activity?.let {
            val inflater = LayoutInflater.from(it)
            val circleTextView =
                inflater.inflate(
                    R.layout.item_section_circle,
                    llSections,
                    false
                ) as? MaterialTextView
            circleTextView?.text = codigo
            circleTextView?.setOnClickListener(SectionOnClickListener())
            llSections?.addView(circleTextView)
        }
    }

    private fun initZoneRegionCircles(codigo: String, isSelected: Boolean = false) {
        activity?.let {
            val inflater = LayoutInflater.from(it)
            val circleTextView =
                inflater.inflate(
                    R.layout.item_section_circle,
                    llSections,
                    false
                ) as? MaterialTextView
            circleTextView?.text = codigo
            circleTextView?.setOnClickListener(ZoneOnClickListener())

            if (isSelected) {
                circleTextView?.setTextColor(ContextCompat.getColor(it, R.color.white_5))
                circleTextView?.isSelected = isSelected
            }

            llSections?.addView(circleTextView)
        }
    }

    private fun initializeListeners() {
        rl_filtro_aprobado?.setOnClickListener {
            collapseOrUnCollapseFiltro(lvwFiltroAprobado, ivwFiltroAprobado)
        }

        edtFiltroAprobado?.setOnClickListener {
            collapseOrUnCollapseFiltro(lvwFiltroAprobado, ivwFiltroAprobado)
        }

        rl_filtro_tipodepago?.setOnClickListener {
            collapseOrUnCollapseFiltro(lvwFiltroTipoDePago, ivwFiltroTipoDePago)
        }

        edtFiltroTipoDePago?.setOnClickListener {
            collapseOrUnCollapseFiltro(lvwFiltroTipoDePago, ivwFiltroTipoDePago)
        }

        tv_section_left_arrow?.setOnClickListener {
            hsvSectionsLayout?.pageScroll(View.FOCUS_LEFT)
        }

        tv_section_right_arrow?.setOnClickListener {
            hsvSectionsLayout?.pageScroll(View.FOCUS_RIGHT)
        }

        tvCurrentSection?.setOnClickListener {
            if (!it.isSelected) {
                resetScreen()
            }
        }
    }

    private fun resetScreen() {
        tvCurrentSection?.isSelected = true
        context?.let {
            tvCurrentSection?.setTextColor(it.getCompatColor(R.color.white_5))
        }

        for (i in Constant.NUMERO_CERO until llSections?.childCount!!) {
            context?.let {
                val tv = llSections?.getChildAt(i) as TextView
                tv.isSelected = false
                tv.setTextColor(it.getCompatColor(R.color.gray_home))
            }
        }
        mChosenSection = Constant.EMPTY_STRING
        load()
    }

    private fun collapseOrUnCollapseFiltro(listview: ListView?, imageView: ImageView?) {
        val visible = listview?.isVisible() ?: false
        KeyboardUtil.dismissKeyboard(activity, view)
        listview?.visibility = if (visible) View.GONE else View.VISIBLE
        imageView?.setImageResource(if (visible) R.drawable.triangle_purple else R.drawable.triangle_purple_up)
    }

    private fun getAdapter(
        list: List<BasePostulante>,
        recyclerView: RecyclerView?
    ) {
        val codRol = consolidadoPresenter.getRol()
        val user = consolidadoPresenter.getUser()

        val adapter = PostulantAdapter(
            list,
            this,
            context = context,
            isGzOrGr = codRol in arrayOf(Rol.GERENTE_REGION.codigoRol, Rol.GERENTE_ZONA.codigoRol),
            user = user,
            configuration = configuration ?: return
        )
        adapter.buttonTitle = tituloBotonEditarVer()
        adapter.listener = this
        adapter.isRegularizarDocumento = (listado == UneteListado.REGULARIZAR_DOCUMENTO.tipo)
        adapter.rol = consolidadoPresenter.getRol()
        recyclerView?.layoutManager = GridLayoutManager(context, Constant.NUMERO_UNO)
        recyclerView?.itemAnimator = DefaultItemAnimator()
        recyclerView?.isNestedScrollingEnabled = false
        recyclerView?.adapter = adapter

        recyclerView?.visible()
        txtListadoEmpty?.gone()
        if (isConsolidado) adapterSetCount()
    }

    private fun validarBuro(postulanteModel: PostulanteModel) {
        consolidadoPresenter.validarBuros(
            postulanteModel.pais.orEmpty(),
            postulanteModel.numeroDocumento.orEmpty(), postulanteModel.codigoZona.orEmpty(),
            postulanteModel.codigoSeccion.orEmpty(), postulanteModel.tipoDocumento!!.toInt(),
            UneteSubEstadoEnAprobacionFFVV.POR_GZ.estado, postulanteModel.solicitudPostulanteID
        )
    }

    private fun obtenerIcono(observacion: Int): Int {

        return when (observacion) {
            EstadoValidacion.VALIDACION_CREDITICIA.estado -> R.drawable.ic_icon_archivos
            EstadoValidacion.VALIDACION_TELEFONICA.estado -> R.drawable.ic_icon_celular
            EstadoValidacion.VALIDACION_UBICACION.estado -> R.drawable.ic_icon_localizacion
            else -> Constant.NUMERO_CERO
        }
    }

    private fun contenidoCrediticia1(valido: Boolean): String {
        return when {
            valido -> getString(R.string.unete_validacion_crediticia_valido)
            else -> getString(R.string.unete_validacion_crediticia1)
        }
    }

    private fun contenidoCrediticia2(valido: Boolean): String {
        return when {
            valido -> Constant.EMPTY_STRING
            else -> getString(R.string.unete_validacion_crediticia2)
        }
    }

    private fun contenidoTelefono1(valido: Boolean): String {
        return when {
            valido -> getString(R.string.unete_validacion_telefonica_valido)
            else -> getString(R.string.unete_validacion_telefonica1)
        }
    }

    private fun contenidoTelefono2(valido: Boolean): String {
        return when {
            valido -> Constant.EMPTY_STRING
            else -> getString(R.string.unete_validacion_telefonica2)
        }
    }

    private fun contenidoUbicacion1(valido: Boolean): String {
        return when {
            valido -> getString(R.string.unete_validacion_geografica_valido)
            else -> getString(R.string.unete_validacion_geografica1)
        }
    }

    private fun contenidoUbicacion2(valido: Boolean): String {
        return when {
            valido -> Constant.EMPTY_STRING
            else -> getString(R.string.unete_validacion_geografica2)
        }
    }

    private val filtroAprobadoView = object : FilterView {
        override fun showList(list: List<FiltroAprobadoUneteModel>) {
            context?.let {
                lvwFiltroAprobado?.adapter = FilterAdapter(it, list)
                if (list.isNotEmpty()) {
                    edtFiltroAprobado?.setText(list[0].description)
                    edtFiltroAprobado?.tag = list[0].id
                }
            }
            FormUtils.setListViewHeightBasedOnChildren(lvwFiltroAprobado)
        }

        override fun showPaymentTypeList(list: List<FiltroAprobadoUneteModel>) {
            try {
                context?.let {
                    lvwFiltroTipoDePago?.adapter = FilterAdapter(it, list)
                    if (list.isNotEmpty()) {
                        edtFiltroTipoDePago?.setText(list[Constant.NUMERO_CERO].description)
                        edtFiltroTipoDePago?.tag = list[Constant.NUMERO_CERO].id
                    }
                }
                FormUtils.setListViewHeightBasedOnChildren(lvwFiltroTipoDePago)
            } catch (e: Exception) {
                Logger.e(javaClass.simpleName, e.message.orEmpty(), e)
            }

        }
    }

    override fun esZonaPagoContado(esZona: Boolean) {
        zonaPagoContado = esZona
        if (isConsolidado) consolidadoPresenter.listTipoConsultoraSpinner()
    }

    override fun setConfiguration(config: Configuration) {
        configuration = config
    }

    override fun onProactivaRechazada(nomPostulante: String) {
        load()
        context?.customDialog(R.layout.dialog_pago_contado) {
            tvTitle.visibility = View.GONE
            tvContent.text = getString(
                R.string.unete_proactiva_rechazada_body,
                nomPostulante
            )
            ivIcon.setImageResource(R.drawable.ic_warning_alert)
            btnOk.setText(R.string.unete_aceptar)
            btnCancel.visibility = View.GONE
            ivIcon.visibility = View.GONE
            btnCancel.setText(R.string.actualizacion_button_entendido)
            btnOk.setOnClickListener {
                dismiss()
            }
            btnCancel.setOnClickListener {
                dismiss()
            }
        }?.show()
    }

    override fun onProactivaAprobada(nomPostulante: String) {
        load()
        context?.customDialog(R.layout.view_aprobada) {
            txtMensaje?.text = getString(
                R.string.unete_proactiva_aprobada_body,
                nomPostulante
            )

            btnAcept?.setText(R.string.unete_aceptar)
            btnAcept?.setOnClickListener {
                dismiss()
            }
        }?.show()
    }

    override fun esPagoContado() = zonaPagoContado

    override fun showPagoContadoModal(bloqueos: ValidacionBuroResponse) {
        context?.customDialog(R.layout.dialog_pago_contado) {
            tvTitle.text = getString(R.string.unete_paso1_valid_consultora_title)
            tvContent.text = getString(R.string.unete_paso1_consultora_contado)
            ivIcon.setImageResource(R.drawable.ic_warning_alert)
            btnOk.setText(R.string.actualizacion_button_incorporar)
            btnCancel.visible()
            btnCancel.setText(R.string.actualizacion_button_entendido)
            btnOk.setOnClickListener {
                dismiss()
                createPagoContadoModel(bloqueos)
            }
            btnCancel.setOnClickListener {
                dismiss()
            }
        }?.show()
    }

    private fun createPagoContadoModel(bloqueos: ValidacionBuroResponse) {
        postulanteSeleccionada?.tipoPago = TipoPago.CONTADO.id
        postulanteSeleccionada?.estadoBurocrediticio =
            UneteEstadoCrediticio.PUEDE_SER_CONSULTORA.value.toString()
        bloqueos.enumEstadoCrediticio = UneteEstadoCrediticio.PUEDE_SER_CONSULTORA.value
        consolidadoPresenter.updatePostulanteTipoPago(postulanteSeleccionada!!)
    }

    private inner class SectionOnClickListener : View.OnClickListener {

        override fun onClick(view: View) {
            if (!view.isSelected) {
                llSections?.childCount?.let {
                    for (i in Constant.NUMERO_CERO until it) {
                        val textView = llSections?.getChildAt(i) as TextView
                        textView.isSelected = false
                        textView.setTextColor(
                            ContextCompat.getColor(view.context, R.color.gray_home)
                        )
                    }
                }

                view.isSelected = true
                (view as TextView).setTextColor(
                    ContextCompat.getColor(view.context, R.color.white_5)
                )
                mChosenSection = view.text.toString()

                tvCurrentSection?.isSelected = false
                tvCurrentSection?.setTextColor(
                    ContextCompat.getColor(view.context, R.color.gray_home)
                )

                load()

            }
        }
    }

    private inner class ZoneOnClickListener : View.OnClickListener {

        override fun onClick(view: View) {
            if (!view.isSelected) {
                llSections?.childCount?.let {
                    for (i in Constant.NUMERO_CERO until it) {
                        val textView = llSections?.getChildAt(i) as TextView
                        textView.isSelected = false
                        textView.setTextColor(
                            ContextCompat.getColor(view.context, R.color.gray_home)
                        )
                    }
                }

                view.isSelected = true
                (view as TextView).setTextColor(
                    ContextCompat.getColor(view.context, R.color.white_5)
                )
                mChosenZona = view.text.toString()

                load()

                mListener?.onChangeZone(mChosenZona.orEmpty())
            }
        }
    }

    private fun setupSyncObservers() {
        observeEventSubject(EventSubject.POSTULANTS_APPLICATIONS_SYNC, observer = syncStateObserver)
    }

    private val syncStateObserver = consumableObserver<SyncState.Updated> {
        load()
    }

    private fun openExternalWebPage(externalUrl: String) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(externalUrl)))
    }

    interface ConsolidadoFragmentListener {
        fun onChangeZone(zone: String)
    }

}
