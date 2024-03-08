package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.digital.herramientasdigitales

import android.os.Bundle
import android.view.View
import biz.belcorp.mobile.components.design.chip.ChipAdapter
import biz.belcorp.mobile.components.design.chip.ChipModel
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.core.utils.withArguments
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.helper.Params
import com.google.android.flexbox.*
import kotlinx.android.synthetic.main.fragment_herramientas_digitales.*

class HerramientaDigitalFragment : BaseFragment(), HerramientaDigitalContract.View {

    private val mPresenter by injectFragment<HerramientaDigitalContract.Presenter>()

    private val mDigitalAdapter by lazy { ChipAdapter(R.layout.item_digital_chip) }

    private var personaId: Long = -1L
    private lateinit var rol: Rol

    var opciones = emptyList<String>()

    override fun getLayout(): Int = R.layout.fragment_herramientas_digitales

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recuperarArgs()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inicializar()
    }

    override fun mostrarHerramientasDigitales(data: List<ChipModel>) {
        if (!isAttached()) return
        txtSinHerramientasDigitales?.visibility = View.GONE
        mDigitalAdapter.submitList(data)
    }

    override fun mostrarHerramientasDigitalesVacio() {
        if (!isAttached()) return
        txtSinHerramientasDigitales?.visibility = View.VISIBLE
    }

    private fun recuperarArgs() {
        arguments?.let {
            personaId = it.getLong(ARG_PERSONA_ID)
            rol = it.getSerializable(ARG_ROL) as Rol
        }
    }

    private fun inicializar() {
        configurarHerramientasDigitalesRecyclerView()
        inicializarPresenter()
    }

    private fun configurarHerramientasDigitalesRecyclerView() {
        rvHerramientasDigitales?.apply {
            isNestedScrollingEnabled = false

            val lm = FlexboxLayoutManager(context)
                .apply {
                    flexDirection = FlexDirection.ROW
                    justifyContent = JustifyContent.FLEX_START
                    flexWrap = FlexWrap.WRAP
                    alignItems = AlignItems.CENTER
                }
            layoutManager = lm

            adapter = mDigitalAdapter
        }
    }

    private fun inicializarPresenter() {
        val request = Params(personaId, rol, opciones)
        mPresenter.obtener(request)
    }

    companion object {

        private const val ARG_PERSONA_ID = "PERSONA_ID"
        private const val ARG_ROL = "ROL"

        fun newInstance(personaId: Long, rol: Rol): HerramientaDigitalFragment {
            return HerramientaDigitalFragment()
                .withArguments(
                    ARG_PERSONA_ID to personaId,
                    ARG_ROL to rol
                )
        }
    }
}
