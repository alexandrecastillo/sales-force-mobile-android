package biz.belcorp.salesforce.modules.developmentpath.features.profile.cobranzaganancia

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.core.utils.toast
import biz.belcorp.salesforce.core.utils.withArguments
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.PersonaRdd
import biz.belcorp.salesforce.modules.developmentpath.widgets.etiqueta.EtiquetaInfoAdapter
import biz.belcorp.salesforce.modules.developmentpath.widgets.etiqueta.EtiquetaInfoDecoration
import biz.belcorp.salesforce.modules.developmentpath.widgets.etiqueta.GridManagerBuilder
import biz.belcorp.salesforce.modules.developmentpath.widgets.etiqueta.model.ContenedorInfoBasica
import kotlinx.android.synthetic.main.fragment_cobranza_ganancia.*
import biz.belcorp.salesforce.base.R as BaseR

class CobranzaGananciaFragment : BaseFragment(), DatosCobranzaGananciaContract.View {

    private var personaId: Long = -1L
    private var rol = Rol.NINGUNO
    private var esActualizado = false

    private val adapter by lazy { EtiquetaInfoAdapter() }
    private val presenter by injectFragment<CobranzaGananciaPresenter>()

    override fun getLayout(): Int = R.layout.fragment_cobranza_ganancia

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recuperarArgs()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inicializar()
    }

    override fun mostrarProgreso() {
        iniciarCargandoDatos()
    }

    override fun ocultarProgreso(model: CobranzaYGananciaModel) {
        pararCargarDatos(model)
    }

    override fun pintarContenedorInfo(modelo: ContenedorInfoBasica, model: CobranzaYGananciaModel) {
        if (!isAttached())
            return
        recyclerCobranzaGanancia?.layoutManager =
            GridManagerBuilder.buildForContainer(requireContext(), modelo)
        adapter.actualizar(modelo.grupos)
    }

    override fun cobranzaGananciaError() {
        errorCargarDatos()
    }

    private fun recuperarArgs() {
        arguments?.let {
            personaId = it.getLong(PARAM_PERSONA_ID)
            rol = it.getSerializable(PARAM_ROL) as Rol
        }
    }

    private fun inicializar() {
        configurarRecyclerView()
        iniciarPresenter()
        btnActualizar?.setOnClickListener {
            iniciarPresenter()
            esActualizado = true
        }
    }

    private fun configurarRecyclerView() {
        recyclerCobranzaGanancia?.apply {
            isNestedScrollingEnabled = false
            addItemDecoration(EtiquetaInfoDecoration(resources.getDimensionPixelSize(BaseR.dimen.content_inset_medium)))
            adapter = this@CobranzaGananciaFragment.adapter
        }
    }

    private fun iniciarPresenter() {
        val persona = PersonaRdd.Identificador(personaId, rol)
        presenter.obtener(persona)
    }

    private fun iniciarCargandoDatos() {
        val rotate = RotateAnimation(
            ANIM_ROTATE_FROM_DEGREES, ANIM_ROTATE_TO_DEGREES,
            Animation.RELATIVE_TO_SELF, ANIM_ROTATE_PIVOT,
            Animation.RELATIVE_TO_SELF, ANIM_ROTATE_PIVOT
        )
        rotate.duration = TIEMPO_PROGRES
        rotate.repeatCount = Animation.INFINITE
        rotate.interpolator = LinearInterpolator()
        iconActualizar?.startAnimation(rotate)
    }

    private fun pararCargarDatos(model: CobranzaYGananciaModel) {
        if (model.server == true) {
            Handler().postDelayed({
                iconActualizar?.clearAnimation().apply {
                    if (esActualizado)
                        activity?.toast(getString(R.string.datos_cobranza_ganancia_actualizado))
                }

            }, LIMPIAR_PROGRES)
        } else {
            errorCargarDatos()
        }
    }

    private fun errorCargarDatos() {
        Handler().postDelayed({
            iconActualizar?.clearAnimation()
        }, LIMPIAR_PROGRES)
    }

    companion object {

        private const val PARAM_PERSONA_ID = "PERSONA_ID"
        private const val PARAM_ROL = "ROL"
        private const val TIEMPO_PROGRES = 1300L
        private const val LIMPIAR_PROGRES = 300L
        private const val ANIM_ROTATE_FROM_DEGREES = 0F
        private const val ANIM_ROTATE_TO_DEGREES = 360F * 8
        private const val ANIM_ROTATE_PIVOT = 0.5F

        fun newInstance(personaId: Long, personaRol: Rol): CobranzaGananciaFragment {
            return CobranzaGananciaFragment()
                .withArguments(
                    PARAM_PERSONA_ID to personaId,
                    PARAM_ROL to personaRol
                )
        }
    }
}
