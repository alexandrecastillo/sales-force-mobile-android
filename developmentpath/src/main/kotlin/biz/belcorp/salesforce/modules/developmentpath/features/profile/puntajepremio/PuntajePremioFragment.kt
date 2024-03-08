package biz.belcorp.salesforce.modules.developmentpath.features.profile.puntajepremio

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import biz.belcorp.mobile.components.core.extensions.gone
import biz.belcorp.mobile.components.core.extensions.invisible
import biz.belcorp.mobile.components.core.extensions.visible
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.core.utils.singularOPlural
import biz.belcorp.salesforce.core.utils.withArguments
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import kotlinx.android.synthetic.main.fragment_puntaje_regalo_perfil.*

class PuntajePremioFragment : BaseFragment(),
    PuntajePremioView {

    var personaId: Long = 0L
    lateinit var rol: Rol

    private val presenter by injectFragment<PuntajePremioPresenter>()

    override fun getLayout() = R.layout.fragment_puntaje_regalo_perfil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recuperarArgumentos()
        presenter.establecerVista(this)
    }

    override fun onDestroy() {
        presenter.destruirVista()
        super.onDestroy()
    }

    private fun recuperarArgumentos() {
        arguments?.let {
            personaId = it.getLong(ARG_PERSONA_ID)
            rol = it.getSerializable(ARG_ROL) as Rol
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configurarComponentesSegunRol()
        presenter.obtener(personaId)
    }

    private fun configurarComponentesSegunRol() {
        if (rol == Rol.SOCIA_EMPRESARIA) {
            ll_consultant_profile_visits_container?.cardElevation = 0f
            txtTitleDatos?.gone()
            removerMargenSuperiorDeCard()
        }
    }

    private fun removerMargenSuperiorDeCard() {
        val params =
            ll_consultant_profile_visits_container?.layoutParams as? ConstraintLayout.LayoutParams
        params?.also {
            it.setMargins(
                params.leftMargin,
                0,
                params.rightMargin,
                params.bottomMargin
            )
        }
        ll_consultant_profile_visits_container?.layoutParams = params
    }

    private fun pintarDatos(modelo: PuntajePremioModel) {
        text_nivel_puntaje_perfil?.text = getString(
            R.string.perfil_regalo_nivel,
            modelo.nivel.toString(),
            modelo.puntosTotal.toString()
        )
        text_premio_puntaje_perfil?.text = modelo.premio
        text_puntos_inicial_puntaje_perfil?.text = getString(
            R.string.perfil_regalo_puntos,
            Constant.ZERO_NUMBER.toString()
        )
        text_puntos_total_puntaje_perfil?.text = getString(
            R.string.perfil_regalo_puntos,
            modelo.puntosTotal.toString()
        )
        progress_puntaje_perfil?.progress = modelo.porcentajeAvance
    }

    override fun pintarParaPuntajeAlcanzado(modelo: PuntajePremioModel) {
        hacerVisibleLayout()
        pintarDatos(modelo)

        context?.let {
            image_regalo_puntaje_perfil?.setImageDrawable(
                ContextCompat.getDrawable(it, R.drawable.ic_premio_alcanzado)
            )
        }
        text_puntaje_no_alcanzado_perfil?.invisible()
        text_puntaje_alcanzado_perfil?.visible()
    }

    override fun pintarParaPuntajeNoAlcanzado(modelo: PuntajePremioModel) {
        hacerVisibleLayout()
        pintarDatos(modelo)

        val texto = getString(
            R.string.perfil_regalo_indicador,
            modelo.puntosObtenidos.toString(),
            modelo.puntosObtenidos.singularOPlural(),
            modelo.puntosRestantes.toString()
        )

        val spanned =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(texto, Html.FROM_HTML_MODE_LEGACY)
            } else {
                Html.fromHtml(texto)
            }

        text_puntaje_no_alcanzado_perfil?.visible()
        text_puntaje_no_alcanzado_perfil?.text = spanned
    }

    override fun pintarError() {
        progress_loading_puntaje?.gone()
        grupo_sin_puntaje?.visible()
    }

    override fun pintarCampania(campania: String) {
        text_campania_puntaje_perfil?.text = getString(R.string.perfil_regalo_campania, campania)
    }

    private fun hacerVisibleLayout() {
        layout_puntaje_premio?.visible()
        progress_loading_puntaje?.gone()
        grupo_sin_puntaje?.invisible()
    }

    companion object {

        private const val ARG_PERSONA_ID = "param1"
        private const val ARG_ROL = "param2"

        fun newInstance(personaId: Long, rol: Rol) = PuntajePremioFragment()
            .withArguments(
                ARG_PERSONA_ID to personaId,
                ARG_ROL to rol
            )

    }
}
