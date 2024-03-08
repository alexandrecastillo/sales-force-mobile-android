package biz.belcorp.salesforce.modules.postulants.features.indicador.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import biz.belcorp.salesforce.components.widgets.viewpager.HeightWrappingViewPager
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.postulants.R
import biz.belcorp.salesforce.modules.postulants.core.domain.enums.UneteListado
import biz.belcorp.salesforce.modules.postulants.features.indicador.components.listado.ConsolidadoFragment
import biz.belcorp.salesforce.modules.postulants.features.indicador.components.resumen.ResumenConsolidadoFragment
import biz.belcorp.salesforce.modules.postulants.features.indicador.entities.BaseGeo
import biz.belcorp.salesforce.modules.postulants.utils.toUpperCase

class IndicadorAdapter(
    fragmentManager: FragmentManager,
    val context: Context,
    private val mTipoListado: Int = -1,
    private val rol: String? = null,
    private val geoModelList: List<BaseGeo>
) : FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private var tabTitles: ArrayList<String> = arrayListOf()
    private var mCurrentPosition = -1

    init {
        init()
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            Constant.NUMERO_CERO -> getFirstTab()
            else -> getSecondTab()
        }
    }

    private fun getSecondTab(): ConsolidadoFragment {
        return ConsolidadoFragment.newInstance(
            UneteListado.TODOS.tipo, geoModelList, isConsolidado = false
        )
    }

    private fun getFirstTab(): BaseFragment {
        return when (rol) {
            Rol.SOCIA_EMPRESARIA.codigoRol -> getConsolidado()
            else -> getResumenConsolidado()
        }
    }

    private fun getResumenConsolidado(): BaseFragment {
        return if (mTipoListado == Constant.UNO_NEGATIVO)
            ResumenConsolidadoFragment.newInstance(geoModelList)
        else
            ConsolidadoFragment.newInstance(
                mTipoListado, geoModelList, isConsolidado = true
            )
    }

    private fun getConsolidado() =
        ConsolidadoFragment.newInstance(
            UneteListado.EVALUACION.tipo, geoModelList, isConsolidado = true
        )

    override fun getCount(): Int {
        return Constant.NUMERO_DOS
    }

    private fun init() {
        tabTitles = arrayListOf(
            context.resources.getString(R.string.consolidado).toUpperCase(),
            context.resources.getString(R.string.buscar_candidatas).toUpperCase()
        )
    }

    override fun setPrimaryItem(container: ViewGroup, position: Int, obj: Any) {
        super.setPrimaryItem(container, position, obj)
        if (position != mCurrentPosition) {
            val fragment = obj as Fragment
            val pager = container as HeightWrappingViewPager
            if (fragment.view != null) {
                mCurrentPosition = position
                fragment.view?.let {
                    pager.measureCurrentView(it)
                }
            }
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return tabTitles[position]
    }
}
