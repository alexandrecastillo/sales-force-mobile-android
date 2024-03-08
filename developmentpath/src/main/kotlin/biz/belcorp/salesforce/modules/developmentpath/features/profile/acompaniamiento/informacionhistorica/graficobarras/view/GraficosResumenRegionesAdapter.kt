package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.informacionhistorica.graficobarras.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.informacionhistorica.contenedor.model.GraficoResumenModel

class GraficosResumenRegionesAdapter(fm: FragmentManager): FragmentStatePagerAdapter(fm) {

    private var fragments = mutableListOf<Fragment>()
    private var listener: PaginaCambiable? = null
    private var graficos = emptyList<GraficoResumenModel>()

    override fun getItem(p0: Int) = fragments[p0]

    override fun getCount() = fragments.size

    fun actualizar(personaId: Long, rol: Rol, graficos: List<GraficoResumenModel>) {
        this.graficos = graficos
        crearFragments(personaId, rol)
    }

    private fun crearFragments(personaId: Long, rol: Rol) {
        (0 until graficos.size).mapTo(fragments) {
            GraficoResumenRegionFragment.newInstance(personaId, rol, graficos[it].tipoGrafico)
                    .establecerListener(listener)
        }
        notifyDataSetChanged()
    }

    fun establecerListener(listener: PaginaCambiable) {
        this.listener = listener
    }
}
