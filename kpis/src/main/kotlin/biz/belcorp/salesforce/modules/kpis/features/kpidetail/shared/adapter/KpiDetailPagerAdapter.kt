package biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.kpis.R

class KpiDetailPagerAdapter(context: Context, rol: Rol, fm: FragmentManager) :
    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val fragments = arrayListOf<Fragment>()
    private val titles = listOf(
        context.getString(R.string.title_consolidated),
        context.getString(
            R.string.title_tab_consolidated, getSubtitle(rol, context)
        )
    )

    private fun getSubtitle(
        rol: Rol,
        context: Context
    ): String {
        return when (rol) {
            Rol.DIRECTOR_VENTAS -> context.getString(R.string.header_dv)
            Rol.GERENTE_REGION -> context.getString(R.string.header_gr)
            else -> context.getString(R.string.header_gz)
        }
    }

    fun update(fragments: List<Fragment>) {
        this.fragments.clear()
        this.fragments.addAll(fragments)
        notifyDataSetChanged()
    }

    override fun getPageTitle(position: Int) = titles[position]

    override fun getCount() = fragments.size

    override fun getItem(position: Int) = fragments[position]
}
