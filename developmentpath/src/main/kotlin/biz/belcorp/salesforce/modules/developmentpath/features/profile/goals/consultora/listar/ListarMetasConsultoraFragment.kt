package biz.belcorp.salesforce.modules.developmentpath.features.profile.goals.consultora.listar

import android.os.Bundle
import android.view.View
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.domain.entities.analytics.TagAnalytics
import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.core.features.utils.lazyPersonIdentifier
import biz.belcorp.salesforce.core.features.utils.withPersonIdentifier
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.core.utils.showOnce
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.analytics.AnalyticsConstants
import biz.belcorp.salesforce.modules.developmentpath.features.analytics.FirebaseAnalyticsPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.goals.consultora.crear.CrearMetaConsultoraFragment
import biz.belcorp.salesforce.modules.developmentpath.widgets.etiqueta.model.Etiqueta
import biz.belcorp.salesforce.modules.developmentpath.widgets.etiqueta.model.Grupo
import kotlinx.android.synthetic.main.fragment_lista_metas_consultora.*

class ListarMetasConsultoraFragment : BaseFragment(),
    ListarMetasConsultorasView,
    CrearMetaConsultoraFragment.ListenerExito {

    private val personIdentifier by lazyPersonIdentifier()
    private val listarMetasConsultoraPresenter by injectFragment<ListarMetasConsultoraPresenter>()
    private val firebaseAnalyticsPresenter by injectFragment<FirebaseAnalyticsPresenter>()

    private val crearMetaConsultoraFragment by lazy {
        CrearMetaConsultoraFragment
            .newInstance(personIdentifier.id, personIdentifier.role)
            .apply { listenerExito = this@ListarMetasConsultoraFragment }
    }

    override fun getLayout() = R.layout.fragment_lista_metas_consultora

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configurarBotonAgregarMeta()
        inicializarModeloEnLayout()
        recuperarListaMetas()
    }

    private fun configurarBotonAgregarMeta() {
        botonAgregarMeta?.setOnClickListener {
            crearMetaConsultoraFragment.showOnce(childFragmentManager)
            firebaseAnalyticsPresenter.enviarEventoTagConsultora(
                TagAnalytics.EVENTO_PERFIL_CONSULTORA,
                AnalyticsConstants.AGREGAR_META
            )
        }
    }

    private fun recuperarListaMetas() {
        listarMetasConsultoraPresenter.obtenerMetasConsultora(personIdentifier.id)
    }

    private fun inicializarModeloEnLayout() {
        layoutMetas?.modelo = Grupo(
            titulo = Etiqueta(
                tipo = Etiqueta.Tipo.LIGERO,
                idRecurso = R.string.listar_meta_consultora_titulo
            ),
            placeholderContenidovacio = Etiqueta(
                tipo = Etiqueta.Tipo.FUERTE,
                idRecurso = R.string.listar_meta_consultora_vacio
            )
        )
    }


    override fun pintarMetas(modelos: List<MetaConsultoraEnListaModel>) {
        if (!isAdded) return

        val contenidoMetas = convertirMetasAEtiquetas(modelos)

        layoutMetas?.actualizarContenido(contenidoMetas)
    }

    private fun convertirMetasAEtiquetas(modelos: List<MetaConsultoraEnListaModel>): List<Etiqueta> {
        val resultado = mutableListOf<Etiqueta>()

        modelos.forEach {
            resultado.add(construirEtiquetaMonto(it.montoDescripcion))
            resultado.add(construirEtiquetaCampaniaInicio(it.campanaInicio))
            resultado.add(construirEtiquetaCampaniaFin(it.campanaFin))

            if (it.comentario.isNotBlank()) {
                resultado.add(construirEtiquetaComentario(it.comentario))
            }
        }

        return resultado
    }

    private fun construirEtiquetaComentario(comentario: String) =
        Etiqueta(
            tipo = Etiqueta.Tipo.FUERTE,
            valor = construirStringComentario(comentario)
        )

    private fun construirEtiquetaCampaniaFin(inicio: String) =
        Etiqueta(
            tipo = Etiqueta.Tipo.FUERTE,
            valor = construirStringFin(inicio)
        )

    private fun construirEtiquetaCampaniaInicio(fin: String) =
        Etiqueta(
            tipo = Etiqueta.Tipo.FUERTE,
            valor = construirStringInicio(fin)
        )

    private fun construirEtiquetaMonto(montoDescripcion: String) =
        Etiqueta(tipo = Etiqueta.Tipo.FUERTE, valor = montoDescripcion)

    private fun construirStringComentario(comentario: String) =
        getString(R.string.listar_meta_consultora_comentario, comentario)

    private fun construirStringFin(campanaFin: String) =
        getString(R.string.listar_meta_consultora_fin, campanaFin)

    private fun construirStringInicio(campanaInicio: String) =
        getString(R.string.listar_meta_consultora_inicio, campanaInicio)

    override fun pintarMetasVacio() {
        layoutMetas?.actualizarSubtitulo(R.string.listar_meta_consultora_vacio)
    }

    override fun mostrarBotonAgregarMeta() {
        botonAgregarMeta?.visibility = View.VISIBLE
    }

    override fun ocultarBotonAgregarMeta() {
        botonAgregarMeta?.visibility = View.GONE
    }

    override fun alGuardarMetaConExito() {
        listarMetasConsultoraPresenter.obtenerMetasConsultora(personIdentifier.id)
    }

    companion object {

        fun newInstance(personIdentifier: PersonIdentifier) =
            ListarMetasConsultoraFragment()
                .withPersonIdentifier(personIdentifier)
    }
}
