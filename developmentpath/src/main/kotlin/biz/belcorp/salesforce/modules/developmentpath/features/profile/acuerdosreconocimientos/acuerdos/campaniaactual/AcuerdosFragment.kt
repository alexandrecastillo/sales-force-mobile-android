package biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.acuerdos.campaniaactual

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import biz.belcorp.mobile.components.core.decorations.DividerLinearDecoration
import biz.belcorp.mobile.components.core.extensions.getDrawableAttr
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_TWO
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ZERO
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.core.utils.isNotNull
import biz.belcorp.salesforce.core.utils.withArguments
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.acuerdos.AcuerdoAdapter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.acuerdos.AcuerdoModel
import kotlinx.android.synthetic.main.fragment_acuerdos_campania_actual.recyclerViewAcuerdos
import kotlinx.android.synthetic.main.fragment_acuerdos_campania_actual.textViewSinAcuerdos
import biz.belcorp.salesforce.base.R as BaseR
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import kotlinx.android.synthetic.main.fragment_acuerdos_campania_actual.*
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant

class AcuerdosFragment : BaseFragment(), AcuerdosView {

    private val LATEST_CAMPAIGN_FRAGMENT_INDEX = 1
    private val LATEST_MINUS_ONE_CAMPAIGN_FRAGMENT_INDEX = 2
    private val LATEST_MINUS_TWO_CAMPAIGN_FRAGMENT_INDEX = 3

    private var personaId = -1L
    private var campaignNumberFragment = -1
    private lateinit var rol: Rol

    private val presenter by injectFragment<AcuerdosPresenter>()
    private val acuerdosAdapter by lazy { instanciarAcuerdosAdapter() }

    private val registroVisitaReceiver = RegistroVisitaReceiver()


    private fun instanciarAcuerdosAdapter(): AcuerdoAdapter {
        return AcuerdoAdapter().apply {
            setGuardarAcuerdoListener { modelo -> presenter.editar(modelo) }
            setEliminarAcuerdoListener { modelo -> presenter.eliminar(modelo) }
        }
    }

    override fun getLayout(): Int = R.layout.fragment_acuerdos_campania_actual

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recuperarArgumentos()
    }

    private fun recuperarArgumentos() {
        arguments?.let {
            personaId = it.getLong(ARG_PERSONA_ID)
            rol = it.getSerializable(ARG_ROL) as Rol
            campaignNumberFragment = it.getInt(ARG_CAMPAIGN_NUMBER)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        suscribirARegistroVisita()
        configurarRecycler()
        cargarAcuerdos()
    }

    private fun suscribirARegistroVisita() {
        val filter = IntentFilter(Constant.BROADCAST_REGISTRO_VISITA)
        activity?.registerReceiver(registroVisitaReceiver, filter)
    }


    private fun cargarAcuerdos() {
        if(campaignNumberFragment == LATEST_CAMPAIGN_FRAGMENT_INDEX ){
            presenter.obtenerAcuerdosCampaniaActual(personaId, rol)
        }
        else{
            presenter.getLatestAgreement(personaId, rol)
        }
    }

    private fun configurarRecycler() {
        recyclerViewAcuerdos.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = acuerdosAdapter
            val divider = getDrawableAttr(BaseR.attr.listDivider)
            addItemDecoration(
                DividerLinearDecoration(
                    divider,
                    showFirstDivider = false,
                    showLastDivider = false
                )
            )
        }
    }

    override fun pintarAcuerdos(acuerdos: List<AcuerdoModel>) {
        //Only One Agreement per campaign
        if(acuerdos.isEmpty()){
            ocultarAcuerdos()
            mostrarMensajeVacio()
        }else{


            val latestCampaignAgreement = acuerdos[NUMBER_ZERO]
            val latestMinusOneCampaignAgreement = acuerdos.firstOrNull()
            val latestMinusTwoCampaignAgreement = acuerdos.elementAtOrNull(NUMBER_TWO)

            val oneElementReassignedAgreementList = arrayListOf<AcuerdoModel>()

            when(campaignNumberFragment){
                LATEST_CAMPAIGN_FRAGMENT_INDEX -> {
                    oneElementReassignedAgreementList.add(latestCampaignAgreement)
                }
                LATEST_MINUS_ONE_CAMPAIGN_FRAGMENT_INDEX -> {
                    if(latestMinusOneCampaignAgreement.isNotNull()){
                        oneElementReassignedAgreementList.add(latestMinusOneCampaignAgreement!!)
                    }else{
                        ocultarAcuerdos()
                        mostrarMensajeVacio()
                    }
                }
                LATEST_MINUS_TWO_CAMPAIGN_FRAGMENT_INDEX -> {
                    if(latestMinusTwoCampaignAgreement.isNotNull()){
                        oneElementReassignedAgreementList.add(latestMinusTwoCampaignAgreement!!)
                    }else{
                        ocultarAcuerdos()
                        mostrarMensajeVacio()
                    }
                }
            }
            acuerdosAdapter.actualizar(oneElementReassignedAgreementList)
        }
    }

    override fun mostrarAcuerdos() {
        recyclerViewAcuerdos?.visibility = View.VISIBLE
    }

    override fun ocultarAcuerdos() {
        recyclerViewAcuerdos?.visibility = View.GONE
    }

    override fun mostrarMensajeVacio() {
        textViewSinAcuerdos?.visibility = View.VISIBLE
    }

    override fun ocultarMensajeVacio() {
        textViewSinAcuerdos?.visibility = View.GONE
    }

    override fun mostrarMensaje(mensaje: String) {
        toast(mensaje)
    }

    override fun onDestroyView() {
        desuscribirARegistroVisita()
        super.onDestroyView()
    }

    private fun desuscribirARegistroVisita() =
        activity?.unregisterReceiver(registroVisitaReceiver)


    private inner class RegistroVisitaReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if(campaignNumberFragment == LATEST_CAMPAIGN_FRAGMENT_INDEX ){
                presenter.obtenerAcuerdosCampaniaActual(personaId, rol)
            }
        }
    }


    companion object {

        private const val ARG_PERSONA_ID = "param1"
        private const val ARG_ROL = "param2"
        private const val ARG_CAMPAIGN_NUMBER = "param3"

        const val LATEST_CAMPAIGN_AGREEMENT_FRAGMENT = 1
        const val LATEST_MINUS_ONE_CAMPAIGN_AGREEMENT_FRAGMENT = 2
        const val LATEST_MINUS_TWO_CAMPAIGN_AGREEMENT_FRAGMENT = 3


        fun newInstance(personaId: Long, rol: Rol, campaignNumber: Int ) = AcuerdosFragment()
            .withArguments(
                ARG_PERSONA_ID to personaId,
                ARG_ROL to rol,
                ARG_CAMPAIGN_NUMBER to campaignNumber
            )
    }
}
