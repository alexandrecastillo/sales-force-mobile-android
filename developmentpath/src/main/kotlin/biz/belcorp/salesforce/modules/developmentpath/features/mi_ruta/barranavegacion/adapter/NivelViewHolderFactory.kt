package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.barranavegacion.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.modules.developmentpath.R

class NivelViewHolderFactory(private val expandirListener: ExpandirListener?,
                             private val regresarUnNivelListener: RegresarUnNivelListener?,
                             private val salirListener: SalirListener?,
                             private val regresarARaizListener: RegresarARaizListener?) {

    fun crear(parent: ViewGroup,
              viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            BarraNavegacionAdapter.NIVEL_REGRESAR_A_DASHBOARD ->
                crearNivelRegresarADashboard(parent)
            BarraNavegacionAdapter.NIVEL_INTERMEDIO ->
                crearNivelIntermedio(parent)
            BarraNavegacionAdapter.NIVEL_PROPIO ->
                crearNivelPropio(parent)
            BarraNavegacionAdapter.NIVEL_EXPANDIBLE ->
                crearNivelExpandible(parent)
            else -> throw Exception("Tipo nivel barra inválido")
        }
    }

    private fun crearNivelRegresarADashboard(parent: ViewGroup): RecyclerView.ViewHolder {
        val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_barra_navegacion_rdd_regresar, parent, false)

        return NivelRegresarADashboardViewHolder(
                regresarARaizListener,
                view)
    }

    private fun crearNivelIntermedio(parent: ViewGroup): RecyclerView.ViewHolder {
        val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_barra_navegacion_rdd_intermedio, parent, false)

        return NivelIntermedioViewHolder(regresarUnNivelListener, view)
    }

    private fun crearNivelPropio(parent: ViewGroup): RecyclerView.ViewHolder {
        val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_barra_navegacion_rdd_propio, parent, false)

        return NivelPropioViewHolder(salirListener, view)
    }

    private fun crearNivelExpandible(parent: ViewGroup): RecyclerView.ViewHolder {
        val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_barra_navegacion_rdd_propio, parent, false)

        return NivelExpandibleViewHolder(expandirListener, regresarARaizListener, view)
    }
}
