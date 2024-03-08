package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.PagerAdapter
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.core.domain.entities.analytics.TagAnalytics
import biz.belcorp.salesforce.modules.developmentpath.features.analytics.FirebaseAnalyticsPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.view.busqueda.BusquedaFragment
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.view.no_planificadas.NoPlanificadasFragment
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.view.planificadas.PlanificadasFragment
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_mi_ruta_content.*
import kotlinx.android.synthetic.main.tab_mi_ruta.view.*
import java.util.*

class MiRutaContentFragment : BaseFragment(),
        TabLayout.OnTabSelectedListener {

    override fun getLayout() = R.layout.fragment_mi_ruta_content

    companion object {
        private const val PLANIFICADAS_INDEX = 0
        private const val NO_PLANIFICADAS_INDEX = 1
        private const val BUSQUEDA_INDEX = 2
    }

    private val analyticsPresenter: FirebaseAnalyticsPresenter by injectFragment()
    private val planificadasFragment by lazy { PlanificadasFragment() }
    private val noPlanificadasFragment by lazy { NoPlanificadasFragment() }
    private val busquedaFragment by lazy { BusquedaFragment() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pager.adapter = crearAdapter()
        tabs.setupWithViewPager(pager)
        tabs.addOnTabSelectedListener(this)
        personalizarTabs()
    }

    private fun crearAdapter(): PagerAdapter {
        val adapter = SectionsPagerAdapter(childFragmentManager)

        adapter.addFragment(
                planificadasFragment,
                getString(R.string.mi_ruta_tab_planificadas))

        adapter.addFragment(
                noPlanificadasFragment,
                getString(R.string.mi_ruta_tab_no_planificadas))

        adapter.addFragment(busquedaFragment)

        return adapter
    }

    private fun personalizarTabs() {
        personalizarTabPlanificadas()
        personalizarTabNoplanificadas()
        personalizarTabBusqueda()
    }

    private fun personalizarTabPlanificadas() {
        val tabPlanificadas = LayoutInflater.from(context).inflate(R.layout.tab_mi_ruta, null) as RelativeLayout
        tabPlanificadas.tv_titulo.text = context?.getText(R.string.mi_ruta_tab_planificadas)
        tabPlanificadas.tv_titulo.setTextColor(ContextCompat.getColor(context!!, R.color.rdd_accent))
        tabs.getTabAt(PLANIFICADAS_INDEX)?.customView = tabPlanificadas
    }

    private fun personalizarTabNoplanificadas() {
        val tabNoPlanificadas = LayoutInflater.from(context).inflate(R.layout.tab_mi_ruta, null) as RelativeLayout
        tabNoPlanificadas.tv_titulo.text = context?.getText(R.string.mi_ruta_tab_no_planificadas)
        tabs.getTabAt(NO_PLANIFICADAS_INDEX)?.customView = tabNoPlanificadas
    }

    private fun personalizarTabBusqueda() {
        val tabBusqueda = LayoutInflater.from(context).inflate(R.layout.tab_mi_ruta, null) as RelativeLayout
        val icono = ContextCompat.getDrawable(requireContext(), R.drawable.ic_buscador_disabled)
        tabBusqueda.iv_icono.setImageDrawable(icono)
        tabBusqueda.iv_icono.visibility = View.VISIBLE

        tabs.getTabAt(BUSQUEDA_INDEX)?.tag = "tabBusqueda"
        tabs.getTabAt(BUSQUEDA_INDEX)?.customView = tabBusqueda
    }

    override fun onTabReselected(tab: TabLayout.Tab?) = Unit

    override fun onTabUnselected(tab: TabLayout.Tab) {
        val tabView = tab.customView as RelativeLayout

        tabView.tv_titulo.setTextColor(ContextCompat.getColor(context!!, R.color.black_50))

        if (tab.position == BUSQUEDA_INDEX) {
            val icono = ContextCompat.getDrawable(requireContext(), R.drawable.ic_buscador_disabled)
            tabView.iv_icono.setImageDrawable(icono)
        }
    }

    override fun onTabSelected(tab: TabLayout.Tab) {
        val tabView = tab.customView as RelativeLayout

        tabView.tv_titulo.setTextColor(ContextCompat.getColor(context!!, R.color.rdd_accent))

        analyticsPresenter.enviarEvento(obtenerTagEvento(tab.position))

        if (tab.position == BUSQUEDA_INDEX) {
            val icono = ContextCompat.getDrawable(requireContext(), R.drawable.ic_buscador_enabled)
            tabView.iv_icono.setImageDrawable(icono)
        }
    }

    private fun obtenerTagEvento(index: Int): TagAnalytics {
        return when(index) {
            PLANIFICADAS_INDEX -> TagAnalytics.EVENTO_MI_RUTA_TAB_PLANIFICADAS
            NO_PLANIFICADAS_INDEX -> TagAnalytics.EVENTO_MI_RUTA_TAB_NO_PLANIFICADAS
            BUSQUEDA_INDEX -> TagAnalytics.EVENTO_MI_RUTA_TAB_BUSQUEDA
            else -> throw Exception()
        }
    }

    inner class SectionsPagerAdapter(fm: FragmentManager) :
            FragmentPagerAdapter(fm) {

        private val fragments = ArrayList<Fragment>()

        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }

        override fun getCount(): Int {
            return fragments.size
        }

        fun addFragment(fragment: Fragment, title: String = "") {
            fragments.add(fragment)
        }
    }
}
