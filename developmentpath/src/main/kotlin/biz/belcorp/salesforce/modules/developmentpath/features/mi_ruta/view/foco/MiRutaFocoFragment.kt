package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.view.foco

import android.os.Bundle
import android.view.View
import android.view.View.VISIBLE
import biz.belcorp.salesforce.core.base.BaseDialogFragment
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.common.view.FocoView
import biz.belcorp.salesforce.modules.developmentpath.core.domain.foco.FocoModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.foco.FocoPresenter
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_dialog_mi_ruta_foco.*


class MiRutaFocoFragment : BaseDialogFragment(), FocoView, TabLayout.OnTabSelectedListener {

    private val presenter: FocoPresenter by injectFragment()

    private var mPagerAdapter: FocusAdapter? = null

    lateinit var focos: List<FocoModel>

    companion object {
        private const val SEGMENTO_ID = "SEGMENTO_ID"
        private var segmentoId: Int = 0

        fun newInstance(segmentoId: Int): MiRutaFocoFragment {
            val args = Bundle()
            val fragment = MiRutaFocoFragment()
            args.putInt(SEGMENTO_ID, segmentoId)
            fragment.arguments = args

            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            segmentoId = it.getInt(SEGMENTO_ID)
        }
    }

    override fun getLayout() = R.layout.fragment_dialog_mi_ruta_foco

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isCancelable = false
        iv_close.setOnClickListener {
            dialog?.dismiss()
        }
        presenter.setView(this)
        presenter.obtenerFocos(segmentoId)
    }

    override fun mostrarFocos(focos: List<FocoModel>) {
        this.focos = focos
        mPagerAdapter = FocusAdapter(childFragmentManager, context, focos)
        viewpager.adapter = mPagerAdapter
        tablayout.setupWithViewPager(viewpager)
        tablayout.addOnTabSelectedListener(this)
        tv_subtitle_focus.text = focos[tablayout.selectedTabPosition].nombre
        viewpager.visibility = VISIBLE
    }

    override fun onTabReselected(tab: TabLayout.Tab?) = Unit

    override fun onTabUnselected(tab: TabLayout.Tab?) = Unit

    override fun onTabSelected(tab: TabLayout.Tab?) {
        tv_subtitle_focus.text = focos[tab!!.position].nombre
    }

    override fun mostrarBanner() = Unit

    override fun ocultarBanner() = Unit

}
