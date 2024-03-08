package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.visitas.view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.core.utils.isGZ
import biz.belcorp.salesforce.core.utils.isNotNull
import biz.belcorp.salesforce.core.utils.isSE
import biz.belcorp.salesforce.core.utils.visible
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant.BP_STRING
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant.CONSULTANT_STRING
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant.NEW_CONSULTANT_STRING
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.visitas.model.AvanceModel
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.visitas.model.ConsultantInfoModel
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.visitas.presenter.AvanceVisitasPresenter
import kotlinx.android.synthetic.main.fragment_avance_visitas.card_bp_quantity_visiting
import kotlinx.android.synthetic.main.fragment_avance_visitas.card_bp_quantity_visiting_eight_days
import kotlinx.android.synthetic.main.fragment_avance_visitas.card_consultants_visited
import kotlinx.android.synthetic.main.fragment_avance_visitas.card_new_consultants_visited
import kotlinx.android.synthetic.main.fragment_avance_visitas.progress_bp_quantity
import kotlinx.android.synthetic.main.fragment_avance_visitas.progress_consultant_visited
import kotlinx.android.synthetic.main.fragment_avance_visitas.progress_new_consultant_visited
import kotlinx.android.synthetic.main.fragment_avance_visitas.progress_visitas
import kotlinx.android.synthetic.main.fragment_avance_visitas.text_bp_eight_days_number
import kotlinx.android.synthetic.main.fragment_avance_visitas.text_bp_make_visits_denominator
import kotlinx.android.synthetic.main.fragment_avance_visitas.text_bp_make_visits_numerator
import kotlinx.android.synthetic.main.fragment_avance_visitas.text_bp_visitits_description
import kotlinx.android.synthetic.main.fragment_avance_visitas.text_bp_visitits_eight_days_description
import kotlinx.android.synthetic.main.fragment_avance_visitas.text_consultants_visits_denominator
import kotlinx.android.synthetic.main.fragment_avance_visitas.text_consultants_visits_numerator
import kotlinx.android.synthetic.main.fragment_avance_visitas.text_denominador_visitas
import kotlinx.android.synthetic.main.fragment_avance_visitas.text_hijas_visitadas
import kotlinx.android.synthetic.main.fragment_avance_visitas.text_new_consultants_visits_denominator
import kotlinx.android.synthetic.main.fragment_avance_visitas.text_new_consultants_visits_numerator
import kotlinx.android.synthetic.main.fragment_avance_visitas.text_new_visited_consultant_description
import kotlinx.android.synthetic.main.fragment_avance_visitas.text_numerador_visitas
import kotlinx.android.synthetic.main.fragment_avance_visitas.text_visited_consultant_description

class AvanceVisitasFragment : BaseFragment(), AvanceVisitasView {

    override fun getLayout() = R.layout.fragment_avance_visitas

    companion object {

        private const val PLAN_ID = "PLAN_ID"
        private const val ROLE = "ROLE"

        fun newInstance(planId: Long): AvanceVisitasFragment {
            val fragment = AvanceVisitasFragment()
            val arguments = Bundle()
            arguments.putLong(PLAN_ID, planId)
            fragment.arguments = arguments
            return fragment
        }

        fun newInstance(planId: Long, role: Rol): AvanceVisitasFragment {
            val fragment = AvanceVisitasFragment()
            val arguments = Bundle()
            arguments.putLong(PLAN_ID, planId)
            arguments.putSerializable(ROLE, role)
            fragment.arguments = arguments
            return fragment
        }
    }

    private val presenter: AvanceVisitasPresenter by injectFragment()
    private var planId = -1L
    private var role: Rol? = null
    private var recargaBroadcast = RecargaAvanceBroadcast()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recuperarArgs()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_avance_visitas, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        suscribirBroadcast()
    }

    override fun onResume() {
        super.onResume()
        comenzar()
    }

    override fun onDestroyView() {
        desuscribirBroadcast()
        super.onDestroyView()
    }

    private fun recuperarArgs() {
        arguments?.apply {
            planId = getLong(PLAN_ID)
            role = getSerializable(ROLE) as Rol?
        }
    }

    private fun comenzar() {
        presenter.calcularAvance(planId)
        if (role.isNotNull() && role?.isGZ() == true) {
            presenter.getGzProgressSections(planId)
        }
        if (role.isNotNull() && role?.isSE() == true) {
            presenter.getBpProgressSections(planId)
        }
    }

    override fun pintarAvance(avance: AvanceModel) {
        text_numerador_visitas?.text = avance.cantidadVisitadas.toString()
        text_denominador_visitas?.text =
            getString(R.string.denominador_avance, avance.cantidadPlanificadas.toString())
        progress_visitas?.progress = avance.porcentaje
        when (role) {
            Rol.SOCIA_EMPRESARIA -> {
                text_hijas_visitadas?.text = getString(
                    R.string.gz_description_roles_progress,
                    CONSULTANT_STRING
                )
            }

            Rol.GERENTE_ZONA -> {
                text_hijas_visitadas?.text = getString(
                    R.string.gz_description_roles_progress,
                    BP_STRING
                )
            }

            Rol.GERENTE_REGION -> {
                text_hijas_visitadas?.text =
                    getString(R.string.hijas_parametro_visitadas, avance.tipoHijas)
            }

            else -> {
                //nothing to do
            }
        }
    }

    override fun showProgressCampaignInfo(consultantInfoModel: ConsultantInfoModel) {
        if (role?.isGZ() == true) {
            showGzInfo(consultantInfoModel)
        } else {
            showBpInfo(consultantInfoModel)
        }
    }

    private fun showGzInfo(consultantInfoModel: ConsultantInfoModel) {
        card_consultants_visited?.visible()
        card_new_consultants_visited?.visible()
        card_bp_quantity_visiting?.visible()
        card_bp_quantity_visiting_eight_days?.visible()

        //consultant card
        text_consultants_visits_numerator?.text = consultantInfoModel.consultantVisited.toString()
        text_consultants_visits_denominator?.text = getString(
            R.string.denominador_avance, consultantInfoModel.totalConsultants.toString()
        )
        text_visited_consultant_description?.text = getString(
            R.string.gz_description_roles_progress, CONSULTANT_STRING
        )
        progress_consultant_visited?.progress = consultantInfoModel.progressConsultants
        //new consultant card
        text_new_consultants_visits_numerator?.text =
            consultantInfoModel.newConsultantVisited.toString()
        text_new_consultants_visits_denominator?.text =
            getString(
                R.string.denominador_avance, consultantInfoModel.totalNewConsultants.toString()
            )
        text_new_visited_consultant_description?.text = getString(
            R.string.gz_description_roles_progress, NEW_CONSULTANT_STRING
        )
        progress_new_consultant_visited?.progress = consultantInfoModel.progressNewConsultants
        //bp visiting quantity card
        text_bp_make_visits_numerator?.text = consultantInfoModel.bpMakeVisitsQuantity.toString()
        text_bp_make_visits_denominator?.text =
            getString(R.string.denominador_avance, consultantInfoModel.bpTotal.toString())
        text_bp_visitits_description?.text = getString(R.string.gz_bp_make_visits_description)
        progress_bp_quantity?.progress = consultantInfoModel.progressBpVisits
        //card visits 8 days
        text_bp_eight_days_number?.text = "${consultantInfoModel.bpWithMore8DaysVisitPercentage}%"
        text_bp_visitits_eight_days_description?.text =
            getString(R.string.gz_bp_eight_days_visits_description)
    }

    private fun showBpInfo(consultantInfoModel: ConsultantInfoModel) {
        card_new_consultants_visited?.visible()
        card_bp_quantity_visiting_eight_days?.visible()

        //new consultant card
        text_new_consultants_visits_numerator?.text =
            consultantInfoModel.newConsultantVisited.toString()
        text_new_consultants_visits_denominator?.text =
            getString(
                R.string.denominador_avance, consultantInfoModel.totalNewConsultants.toString()
            )
        text_new_visited_consultant_description?.text = getString(
            R.string.gz_description_roles_progress, NEW_CONSULTANT_STRING
        )
        progress_new_consultant_visited?.progress = consultantInfoModel.progressNewConsultants
        //bp days visiting consultants card
        text_bp_eight_days_number?.text =
            getString(
                R.string.se_visits_days_title,
                consultantInfoModel.bpVisitsAtLeastOneDay.toString()
            )
        text_bp_visitits_eight_days_description?.text =
            getString(R.string.se_visits_days_description)
    }


    private fun suscribirBroadcast() {
        val filter = IntentFilter(Constant.BROADCAST_CAMBIO_PLANIFICACION_RDD)
        activity?.registerReceiver(recargaBroadcast, filter)
    }

    private fun desuscribirBroadcast() {
        activity?.unregisterReceiver(recargaBroadcast)
    }

    private inner class RecargaAvanceBroadcast : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            presenter.calcularAvance(planId)
        }
    }
}
