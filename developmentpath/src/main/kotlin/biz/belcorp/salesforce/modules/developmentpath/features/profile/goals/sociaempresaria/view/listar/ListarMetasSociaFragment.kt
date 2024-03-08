package biz.belcorp.salesforce.modules.developmentpath.features.profile.goals.sociaempresaria.view.listar

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.core.features.utils.lazyPersonIdentifier
import biz.belcorp.salesforce.core.features.utils.withPersonIdentifier
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.core.utils.toast
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.profile.goals.sociaempresaria.model.MetaSociaModelo
import biz.belcorp.salesforce.modules.developmentpath.features.profile.goals.sociaempresaria.view.crear.CrearMetaSociaFragment
import biz.belcorp.salesforce.modules.developmentpath.widgets.etiqueta.model.Etiqueta
import biz.belcorp.salesforce.modules.developmentpath.widgets.etiqueta.model.Grupo
import kotlinx.android.synthetic.main.fragment_listar_metas_socia.*

class ListarMetasSociaFragment : BaseFragment(), ListarMetaSociaView,
    MetaSociaAdapter.MetaListener {

    private val personIdentifier by lazyPersonIdentifier()
    private val listarMetasPresenter by injectFragment<ListarMetaSociaPresenter>()
    private val metasAdapter by lazy { crearMetasAdapter() }
    private val notificarExito = { obtenerMetas() }

    override fun getLayout(): Int = R.layout.fragment_listar_metas_socia

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        iniciar()
    }

    override fun mostrarDatos(metas: List<MetaSociaModelo>) {
        metasAdapter.actualizar(metas)
    }

    override fun ocultarDescripcionNoHayMetas() {
        layoutMetas?.modelo = Grupo(
            titulo = Etiqueta(
                tipo = Etiqueta.Tipo.LIGERO,
                idRecurso = R.string.listar_meta_consultora_titulo
            ),
            placeholderContenidovacio = Etiqueta(
                tipo = Etiqueta.Tipo.FUERTE,
                valor = ""
            )
        )
    }

    override fun mostrarDescripcionNoHayMetas() {
        iniciarVistaPorDefecto()
    }

    override fun ocultarContenedorMetas() {
        rvMetas?.visibility = View.GONE
    }

    override fun mostrarContenedorMetas() {
        rvMetas?.visibility = View.VISIBLE
    }

    override fun ocultarBotonCrear() {
        btnAgregarMeta?.visibility = View.GONE
    }

    override fun mostrarBotonCrear() {
        btnAgregarMeta?.visibility = View.VISIBLE
    }

    override fun mostrarContadorMetas() {
        txtContadorMetas?.visibility = View.VISIBLE
    }

    override fun ocultarContadorMetas() {
        txtContadorMetas?.visibility = View.GONE
    }

    override fun cargarTotalContadorMetas(indicador: String) {
        txtContadorMetas?.text = indicador
    }

    override fun mostrarMensajeError() {
        toast(R.string.mensaje_error_eliminar_meta)
    }

    override fun notificarExitoAlEliminar() {
        obtenerMetas()
    }

    override fun editarMeta(meta: MetaSociaModelo, posicion: Int) {
        mostrarCrearMeta(meta)
    }

    override fun eliminarMeta(meta: MetaSociaModelo, posicion: Int) {
        listarMetasPresenter.asignarModelo(meta)
        metasAdapter.eliminar(posicion)
        listarMetasPresenter.eliminar(personIdentifier.id, personIdentifier.role)
    }

    private fun iniciar() {
        iniciarVistaPorDefecto()
        iniciarEventoCrearMeta()
        configurarRecycler()
        obtenerMetas()
    }

    private fun iniciarVistaPorDefecto() {
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

    private fun iniciarEventoCrearMeta() {
        btnAgregarMeta?.setOnClickListener {
            mostrarCrearMeta()
        }
    }

    private fun mostrarCrearMeta(meta: MetaSociaModelo? = null) =
        crearFragmentNuevaMetaSocia(meta).show(childFragmentManager, null)

    private fun obtenerMetas() =
        listarMetasPresenter.listar(personIdentifier.id, personIdentifier.role)

    private fun configurarRecycler() {
        rvMetas?.setHasFixedSize(true)
        rvMetas?.layoutManager = LinearLayoutManager(context)
        rvMetas.adapter = metasAdapter
    }

    private fun crearFragmentNuevaMetaSocia(metaModelo: MetaSociaModelo? = null): CrearMetaSociaFragment {
        return CrearMetaSociaFragment.newInstance(personIdentifier.id, personIdentifier.role)
            .apply {
                listener = notificarExito
                meta = metaModelo
            }
    }

    private fun crearMetasAdapter(): MetaSociaAdapter {
        return MetaSociaAdapter().apply { listener = this@ListarMetasSociaFragment }
    }

    companion object {

        fun newInstance(personIdentifier: PersonIdentifier): ListarMetasSociaFragment {
            return ListarMetasSociaFragment()
                .withPersonIdentifier(personIdentifier)
        }
    }
}
