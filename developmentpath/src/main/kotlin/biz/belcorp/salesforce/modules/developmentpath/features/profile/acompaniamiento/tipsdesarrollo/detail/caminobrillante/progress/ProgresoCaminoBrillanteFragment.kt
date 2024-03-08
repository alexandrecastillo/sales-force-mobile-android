package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.caminobrillante.progress

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import biz.belcorp.salesforce.base.R as BaseR
import biz.belcorp.mobile.components.design.step.model.StepModel
import biz.belcorp.salesforce.components.utils.decoration.BoxOffsetDecoration
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.gone
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.core.utils.visible
import biz.belcorp.salesforce.core.utils.withArguments
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.caminobrillante.progress.model.ProgresoActualCaminoBrillanteModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.caminobrillante.progress.model.TipProgresoCaminoBrillanteModel
import kotlinx.android.synthetic.main.fragment_progreso.*

class ProgresoCaminoBrillanteFragment : BaseFragment(), ProgresoCaminoBrillanteView {

    private var personaId = -1L
    private var rol = Rol.NINGUNO

    private val presenter by injectFragment<ProgresoCaminoBrillantePresenter>()

    private val tipsProgresoAdapter by lazy { TipsProgresoCaminoBrillanteAdapter() }

    override fun getLayout(): Int = R.layout.fragment_progreso

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recuperarArgumentos()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configurarRecycler()
        presenter.obtenerProgreso(personaId, rol)
    }

    override fun mostrarNivelesCaminoBrillante(niveles: List<StepModel>) {
        nivelesCaminoBrillante?.visible()
        nivelesCaminoBrillante?.setData(niveles)
        nivelesCaminoBrillante?.enableAnimation = true
        nivelesCaminoBrillante?.showProgressBar = true
    }

    override fun ocultarNivelesCaminoBrillante() {
        nivelesCaminoBrillante?.gone()
    }

    override fun mostrarProgresoCaminoBrillante(entidad: ProgresoActualCaminoBrillanteModel) {
        clContenedorProgreso?.visible()
        barraProgreso?.valorMaximo = entidad.meta.toInt()
        barraProgreso?.valorHito = entidad.hito.toInt()
        barraProgreso?.progress = entidad.progreso.toInt()
    }

    override fun ocultarProgresoCaminoBrillante() {
        clContenedorProgreso?.gone()
    }

    override fun mostrarTipsCaminoBrillante(tips: List<TipProgresoCaminoBrillanteModel>) {
        rvTipsCaminoBrilllante?.visible()
        tipsProgresoAdapter.actualizar(tips)
    }

    override fun ocultarTipsCaminoBrillante() {
        rvTipsCaminoBrilllante?.gone()
    }

    override fun ocultarContenedorCaminoBrillante() {
        clContenedorPrincipal?.gone()
    }

    private fun recuperarArgumentos() {
        arguments?.let {
            personaId = it.getLong(ARG_PERSONA_ID)
            rol = it.getSerializable(ARG_ROL) as Rol
        }
    }

    private fun configurarRecycler() {
        rvTipsCaminoBrilllante?.apply {
            setHasFixedSize(true)
            isNestedScrollingEnabled = false
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(
                BoxOffsetDecoration(
                    context,
                    BaseR.dimen.ds_margin_other_2,
                    BaseR.dimen.zero
                )
            )
            adapter = tipsProgresoAdapter
        }
    }

    companion object {
        const val ARG_PERSONA_ID = "id"
        const val ARG_ROL = "rol"

        fun newInstance(personaId: Long, rol: Rol) = ProgresoCaminoBrillanteFragment()
            .withArguments(
                ARG_PERSONA_ID to personaId,
                ARG_ROL to rol
            )
    }
}
