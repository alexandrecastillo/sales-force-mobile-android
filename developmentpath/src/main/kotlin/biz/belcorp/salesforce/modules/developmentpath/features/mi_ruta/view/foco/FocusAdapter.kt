package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.view.foco

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import biz.belcorp.salesforce.modules.developmentpath.core.domain.foco.FocoModel

class FocusAdapter(fm: FragmentManager,
                   private val context: Context?,
                   private val focos: List<FocoModel>) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return MiRutaDetalleFocoFragment.newInstance(focos[position])
    }

    override fun getCount(): Int {
        return focos.size
    }


    override fun getPageTitle(position: Int): CharSequence? {
        return (position + 1).toString()
    }

}
