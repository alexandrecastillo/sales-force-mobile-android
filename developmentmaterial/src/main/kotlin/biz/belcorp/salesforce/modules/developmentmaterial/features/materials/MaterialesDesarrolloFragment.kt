package biz.belcorp.salesforce.modules.developmentmaterial.features.materials

import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import biz.belcorp.salesforce.analytics.core.domain.entities.ScreenTag
import biz.belcorp.salesforce.analytics.features.trackAnalytics
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.events.ConsumableEvent
import biz.belcorp.salesforce.core.events.EventSubject
import biz.belcorp.salesforce.core.events.LiveDataBus
import biz.belcorp.salesforce.core.events.sync.SyncState
import biz.belcorp.salesforce.core.utils.DocumentHelper
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.modules.developmentmaterial.R
import biz.belcorp.salesforce.modules.developmentmaterial.features.materials.adapters.MaterialesDesarrolloAdapter
import biz.belcorp.salesforce.modules.developmentmaterial.features.materials.model.DocumentModel
import biz.belcorp.salesforce.modules.developmentmaterial.features.sync.utils.startModuleSyncWorker
import biz.belcorp.salesforce.modules.developmentmaterial.utils.AnalyticUtils
import kotlinx.android.synthetic.main.fragment_materiales_desarrollo.*


class MaterialesDesarrolloFragment : BaseFragment(), MaterialesDesarrolloView {

    private val presenter by injectFragment<MaterialesDesarrolloPresenter>()

    private val downloadHelper by lazy { DocumentHelper(requireContext()) }

    override fun getLayout() = R.layout.fragment_materiales_desarrollo

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ViewCompat.setTranslationZ(view, 2F)
        initEvents()
        observeSyncState()

        presenter.cargarCabecera()
        presenter.cargarDocumentos()

    }

    override fun onResume() {
        super.onResume()
        startSync()
        sendAnalytics()
    }

    private fun sendAnalytics() {
        AnalyticUtils.screen()
        trackAnalytics(ScreenTag.DEVELOPMENT_MATERIAL)
    }

    private fun initEvents() {

        btnBack?.setOnClickListener {
            activity?.onBackPressed()
        }

    }

    override fun mostrarCampaniaVenta(nombreCorto: String) {
        tvCampania?.text = getString(R.string.campania_tipo_v, nombreCorto)
    }

    override fun mostrarCampaniaFacturacion(nombreCorto: String) {
        tvCampania?.text = getString(R.string.campania_tipo_f, nombreCorto)
    }

    override fun pintarDocumentos(documentModels: List<DocumentModel>) {
        val adapter = MaterialesDesarrolloAdapter(documentModels)
        adapter.setDownloadDocumentListener { downloadHelper.downloadDocument(it) }
        adapter.setOpenDocumentListener { downloadHelper.openDocument(it) }
        rvMateriales.layoutManager = LinearLayoutManager(context)
        rvMateriales.adapter = adapter
    }

    private fun startSync() {
        context?.startModuleSyncWorker()
    }

    private fun observeSyncState() {
        LiveDataBus.from<MaterialesDesarrolloFragment>(EventSubject.DEVELOPMENT_MATERIAL_SYNC)
            .observe(viewLifecycleOwner, syncStatusObserver)
    }

    private val syncStatusObserver = Observer<ConsumableEvent> {
        it.runAndConsume {
            when (it.value as? SyncState) {
                SyncState.Updated -> presenter.cargarDocumentos()
            }
        }
    }

}
