package biz.belcorp.salesforce.modules.developmentpath.widgets.etiqueta

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.widgets.etiqueta.model.Etiqueta
import biz.belcorp.salesforce.modules.developmentpath.widgets.etiqueta.model.Grupo
import kotlinx.android.synthetic.main.inflate_custom_etiqueta_information.view.*

class GrupoEtiquetaLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    var modelo: Grupo = Grupo.crearGrupoVacioPoDefecto()
        set(value) {
            field = value
            actualizarModeloEnEtiquetas()
            calcularVisibilidadContenido()
        }

    private fun actualizarModeloEnEtiquetas() {
        textViewTitulo.modelo = modelo.titulo
        textViewSubtitulo.modelo = modelo.subtitulo
        textViewContenidoVacio.modelo = modelo.placeholderContenidovacio
        calcularVisibilidadContenido()
    }

    private var contenidoAdapter = GrupoEtiquetaAdapter()

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.inflate_custom_etiqueta_information, this, true)

        recyclerViewContenido.layoutManager = LinearLayoutManager(context)
        recyclerViewContenido.adapter = contenidoAdapter
    }

    fun actualizarSubtitulo(valor: String) {
        textViewSubtitulo.actualizarTexto(valor)
    }

    fun actualizarSubtitulo(recursoId: Int) {
        textViewSubtitulo.actualizarTexto(recursoId)
    }

    fun actualizarContenido(etiquetas: List<Etiqueta>) {
        contenidoAdapter.actualizar(etiquetas)
        calcularVisibilidadContenido()
    }

    private fun calcularVisibilidadContenido() {
        calcularVisibilidadDeRecycler()
        calcularVisibilidadDeTextoContenidoVacio()
    }

    private fun calcularVisibilidadDeRecycler() {
        if (contenidoAdapter.estaVacio) {
            recyclerViewContenido?.visibility = View.GONE
        } else {
            recyclerViewContenido?.visibility = View.VISIBLE
        }
    }

    private fun calcularVisibilidadDeTextoContenidoVacio() {
        if (contenidoAdapter.estaVacio &&
            modelo.placeholderContenidovacio.seMuestraAlgo
        ) {
            textViewContenidoVacio.visibility = View.VISIBLE
        } else {
            textViewContenidoVacio.visibility = View.GONE
        }
    }
}
