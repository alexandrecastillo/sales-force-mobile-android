package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.informacionhistorica.graficobarras.view

import android.content.res.ColorStateList
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.View
import androidx.core.content.ContextCompat
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.*
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.graficos.TipoGrafico
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.graficos.gerenteregion.topbottom.GraficoTopBottomFragment
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.informacionhistorica.graficobarras.model.GraficoHistoricoConCuadro
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.informacionhistorica.graficobarras.model.GraficoHistoricoSinCuadro
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.informacionhistorica.graficobarras.model.ValorXCampania
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.informacionhistorica.graficobarras.presenter.GraficoResumenRegionPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.informacionhistorica.productivasu3c.view.ProductivasU3CFragment
import biz.belcorp.salesforce.modules.developmentpath.utils.reemplazarSaltoLinea
import kotlinx.android.synthetic.main.fragment_region_resumen.*
import kotlinx.android.synthetic.main.item_title_table_venta_neta.*
import kotlinx.android.synthetic.main.layout_barras_gr_cuadro_valores.*

class GraficoResumenRegionFragment : BaseFragment(), GraficoResumenView {

    private val presenter by injectFragment<GraficoResumenRegionPresenter>()

    private var listener: PaginaCambiable? = null

    fun establecerListener(listener: PaginaCambiable?): GraficoResumenRegionFragment {
        this.listener = listener
        return this
    }

    private val personaId: Long by lazy {
        arguments?.getLong(ARG_PERSONA_ID) ?: -1L
    }

    private val rol: Rol by lazy {
        arguments?.getSerializable(ARG_ROL) as Rol
    }

    private val tipoGrafico: TipoGrafico by lazy {
        arguments?.getSerializable(ARG_TIPO_GRAFICO) as TipoGrafico
    }

    private val adapterCuadro: ValoresEnCuadroAdapter by lazy {
        ValoresEnCuadroAdapter()
    }

    override fun getLayout(): Int = R.layout.fragment_region_resumen


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        escucharAcciones()
        presenter.recuperar(personaId, rol, tipoGrafico)
    }

    private fun escucharAcciones() {
        imageAnterior?.setOnClickListener {
            listener?.alPresionarAnterior()
        }
        imagePosterior?.setOnClickListener {
            listener?.alPresionarSiguiente()
        }
    }

    override fun pintarTitulo(titulo: String) {
        textTituloIndicador?.text = titulo
    }

    override fun pintarTituloGrafico(titulo: String) {
        textTituloGrafico?.text = titulo
    }

    override fun pintarSubtitulo(subtitulo: String) {
        if (context == null) return
        textSubtituloIndicador?.text = cargarConDistintasFuentes(subtitulo.reemplazarSaltoLinea())
    }

    override fun dibujarIcono(iconoId: Int) {
        imageColorBarras?.imageResource = iconoId
    }

    private fun cargarConDistintasFuentes(texto: String): SpannableStringBuilder {
        return FontStyler
            .establecerTexto(texto)
            .conContext(requireContext())
            .conDelimitador(DELIMITADOR)
            .conFuentePrimaria(TipoFuente.MULISH_BOLD)
            .conFuenteSecundaria(TipoFuente.MULISH_LIGHT)
            .conColorPrimarioDesdeRecurso(R.color.rdd_evento)
            .conColorSecundarioDesdeRecurso(R.color.gris_escala_5)
            .procesar()
    }

    override fun pintarGraficoConCuadro(datos: GraficoHistoricoConCuadro) {
        cargarConfigurador()?.configurarConCuadro(datos)
    }

    override fun pintarGraficoSinCuadro(datos: GraficoHistoricoSinCuadro) {
        cargarConfigurador()?.configurarSinCuadro(datos)
    }

    override fun pintarBackgroundColor(color: Int) {
        if (context == null) return
        val colorStateList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), color))
        lollipopOrAbove { imageColorBarras?.backgroundTintList = colorStateList }
    }

    override fun mostrarGraficoTopBottom() {
        val topBottomFragment = GraficoTopBottomFragment.newInstance(personaId, tipoGrafico)
        incrustarFragment(topBottomFragment)
    }

    override fun mostrarGraficoCuadroProductivas() {
        val productivas3ucFragment = ProductivasU3CFragment.newInstance(personaId)
        incrustarFragment(productivas3ucFragment)
    }

    override fun pintarValoresEnCuadro(valores: List<ValorXCampania>) {
        mostrarCuadro()
        configurarRecycler(valores)
    }

    override fun pintarTituloEnCuadro(titulo: String) {
        txtTitleMonto?.text = titulo
    }

    private fun configurarRecycler(valores: List<ValorXCampania>) {
        recyclerValoresBarras?.layoutManager = NonScrollableLayoutManager()
            .withContext(context)
            .linearVertical()
        recyclerValoresBarras?.adapter = adapterCuadro
        adapterCuadro.actualizar(valores)
    }

    private fun cargarConfigurador(): ConfiguradorGraficoBarras? {
        return if (context == null)
            null
        else ConfiguradorGraficoBarras(requireContext(), graficoIndicador)
    }

    private fun incrustarFragment(fragment: BaseFragment) {
        childFragmentManager
            .beginTransaction()
            .replace(R.id.flGraficoInferior, fragment)
            .commit()
    }

    private fun mostrarCuadro() {
        layoutCuadroValores?.visibility = View.VISIBLE
    }

    companion object {

        const val ARG_PERSONA_ID = "PERSONA_ID"
        const val ARG_TIPO_GRAFICO = "TIPO_GRAFICO"
        const val ARG_ROL = "ROL"
        private const val DELIMITADOR = "|"

        fun newInstance(
            personaId: Long,
            rol: Rol,
            tipoGrafico: TipoGrafico
        ): GraficoResumenRegionFragment {
            return GraficoResumenRegionFragment()
                .withArguments(
                    ARG_ROL to rol,
                    ARG_PERSONA_ID to personaId,
                    ARG_TIPO_GRAFICO to tipoGrafico
                )
        }
    }
}
