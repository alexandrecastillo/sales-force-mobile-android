package biz.belcorp.salesforce.modules.postulants.features.indicador.components.resumen

import android.app.Activity
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewTreeObserver
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.*
import biz.belcorp.salesforce.modules.postulants.R
import biz.belcorp.salesforce.modules.postulants.features.indicador.components.LoadingView
import biz.belcorp.salesforce.modules.postulants.features.indicador.components.resumen.adapters.DetalleUneteAdapter
import biz.belcorp.salesforce.modules.postulants.features.indicador.components.resumen.adapters.FirstColumnAdapter
import biz.belcorp.salesforce.modules.postulants.features.indicador.components.resumen.entities.DetalleIndicadorModel
import biz.belcorp.salesforce.modules.postulants.features.indicador.entities.BaseGeo
import biz.belcorp.salesforce.modules.postulants.utils.ScreenUtils
import biz.belcorp.salesforce.modules.postulants.utils.toUpperCase
import com.google.android.material.textview.MaterialTextView
import kotlinx.android.synthetic.main.fragment_resumen_consolidado.*
import kotlinx.android.synthetic.main.fragment_resumen_consolidado.sections_navigation_bar_detail
import kotlinx.android.synthetic.main.sections_navigation_bar.*

class ResumenConsolidadoFragment : BaseFragment(), ResumenConsolidadoView,
    DetalleUneteAdapter.DetalleUneteAdapterListener, FirstColumnAdapter.ClickListener {

    override fun getLayout() = R.layout.fragment_resumen_consolidado

    private val consolidadoPresenter: ResumenConsolidadoPresenter by injectFragment()
    private val mListener by lazy { parentFragment as? ConsolidadoResumenFragmentListener }
    private var adapterColumnFroze: FirstColumnAdapter? = null
    private var frozen = listOf<String>()
    private var codigoPais: String? = Constant.EMPTY_STRING

    private var rvLeftWidth: Int = Constant.NUMERO_CERO
    private var ivArrowRightWidth: Int = Constant.NUMERO_CERO
    private var rvConsolidadoWidth: Int = Constant.NUMERO_CERO
    private var mChosenZone: String? = null

    private var visibilityArrows: Boolean = false
    private val loadingView: LoadingView? by lazy { parentFragment as? LoadingView }

    companion object {
        private const val SECCION_ZONA_REGION_KEY = "SECCION_ZONA_REGION_KEY"

        fun newInstance(
            geoModelList: List<BaseGeo>,
        ): ResumenConsolidadoFragment {
            return ResumenConsolidadoFragment().apply {
                arguments = Bundle().apply {
                    if (geoModelList.isNotEmpty()) putParcelableArrayList(
                        SECCION_ZONA_REGION_KEY, geoModelList as ArrayList<out Parcelable>
                    )
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!isAdded) return

        consolidadoPresenter.setView(this)
        codigoPais = consolidadoPresenter.obtenerCodigoPais()
        consolidadoPresenter.getGROptions()

        initListeners()
    }

    override fun showGrFilters() {
        tvCurrentSection?.gone()

        sections_navigation_bar_detail?.visible()
        initListeners()
        showZones()

        onLoadGROptions()
    }

    private fun showZones() {
        val zones: List<BaseGeo> =
            arguments?.getParcelableArrayList<BaseGeo>(SECCION_ZONA_REGION_KEY)
                ?.map {
                    it.apply {
                        descripcion = formatDescription(it.descripcion)
                    }
                }
                ?.distinctBy { it.descripcion }
                ?: emptyList()

        if (zones.isNotEmpty()) {
            mChosenZone = zones[Constant.NUMERO_CERO].descripcion.orEmpty()

            for (model in zones) {
                initZoneRegionCircles(
                    model.descripcion.orEmpty(),
                    mChosenZone == model.descripcion.orEmpty()
                )
            }
        }
    }

    private fun formatDescription(description: String?): String {
        return description?.replace(
            biz.belcorp.salesforce.modules.postulants.utils.Constant.PREFIX_ZONE,
            Constant.EMPTY_STRING
        )?.substring(Constant.NUMERO_CERO, Constant.NUMERO_CUATRO).orEmpty()
    }

    override fun onLoadGROptions() {
        consolidadoPresenter.loadConsolidado(mChosenZone)
    }

    private fun initZoneRegionCircles(codigo: String, isSelected: Boolean = false) {
        activity?.let {
            val inflater = LayoutInflater.from(it)
            val circleTextView =
                inflater.inflate(
                    R.layout.item_section_circle,
                    llSections,
                    false
                ) as? MaterialTextView
            circleTextView?.text = codigo
            circleTextView?.setOnClickListener(ZoneOnClickListener())

            if (isSelected) {
                circleTextView?.setTextColor(ContextCompat.getColor(it, R.color.white_5))
                circleTextView?.isSelected = isSelected
            }

            llSections?.addView(circleTextView)
        }
    }

    private inner class ZoneOnClickListener : View.OnClickListener {

        override fun onClick(view: View) {
            if (!view.isSelected) {
                llSections?.childCount?.let {
                    for (i in Constant.NUMERO_CERO until it) {
                        val textView = llSections?.getChildAt(i) as TextView
                        textView.isSelected = false
                        textView.setTextColor(
                            ContextCompat.getColor(view.context, R.color.gray_home)
                        )
                    }
                }

                view.isSelected = true
                (view as TextView).setTextColor(
                    ContextCompat.getColor(view.context, R.color.white_5)
                )
                mChosenZone = view.text.toString()

                consolidadoPresenter.loadConsolidado(mChosenZone)

                mListener?.onSelectZone(mChosenZone.orEmpty())
            }
        }
    }

    override fun showConsolidado(list: List<DetalleIndicadorModel>?) {
        if (!isAdded) return

        if (!list.isNullOrEmpty()) {

            ivArrowRight?.visibility = View.VISIBLE
            val adapter = DetalleUneteAdapter(
                list, this,
                codigoPais.orEmpty()
            )

            frozen = getColumnFrozen(list)

            handleRecyclerAdapterAutoFit(adapter)
        }

        handleConsolidadoVisibility(!list.isNullOrEmpty())

    }

    override fun onHeaderAdapterClick(modo: Int) {
        mListener?.onHeaderClick(modo)
    }

    override fun context() = context!!

    override fun showLoading() {
        loadingView?.showLoading()
    }

    override fun hideLoading() {
        loadingView?.hideLoading()
    }

    override fun onClick() {
        hsvHeaderConsolidado.pageScroll(View.FOCUS_LEFT)
    }

    private fun initListeners() {
        tv_section_left_arrow?.setOnClickListener {
            hsvSectionsLayout?.pageScroll(View.FOCUS_LEFT)
        }

        tv_section_right_arrow?.setOnClickListener {
            hsvSectionsLayout?.pageScroll(View.FOCUS_RIGHT)
        }

        ivArrowRight?.setOnClickListener {
            hsvHeaderConsolidado?.fullScroll(View.FOCUS_RIGHT)
        }
    }

    private fun handleConsolidadoVisibility(isData: Boolean) {
        tvNoData?.visibility = if (isData) View.GONE else View.VISIBLE
        ivArrowRight?.visibility = if (isData) View.VISIBLE else View.GONE
        rvConsolidado?.visibility = if (isData) View.VISIBLE else View.GONE
        rvLeft?.visibility = if (isData) View.VISIBLE else View.GONE

        if (isData) {
            rvLeft?.viewTreeObserver?.addOnGlobalLayoutListener(object :
                ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    rvLeft?.viewTreeObserver?.removeOnGlobalLayoutListener(this)
                    rvLeftWidth = rvLeft?.width.zeroIfNull()
                }
            })

            ivArrowRight?.viewTreeObserver?.addOnGlobalLayoutListener(object :
                ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    ivArrowRight?.viewTreeObserver?.removeOnGlobalLayoutListener(this)
                    ivArrowRightWidth = ivArrowRight?.width.zeroIfNull()
                }
            })

            rvConsolidado?.viewTreeObserver?.addOnGlobalLayoutListener(object :
                ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    rvConsolidado?.viewTreeObserver?.removeOnGlobalLayoutListener(this)
                    rvConsolidadoWidth = rvConsolidado?.width.zeroIfNull()

                    visibilityArrows = ScreenUtils.exceedScreenWidth(
                        activity as? Activity ?: return,
                        rvLeftWidth,
                        ivArrowRightWidth,
                        rvConsolidadoWidth
                    )
                    if (!visibilityArrows) {
                        ivArrowRight?.invisible()
                        adapterColumnFroze?.setLeftArrowVisibility(false)
                    } else {
                        adapterColumnFroze?.setLeftArrowVisibility(true)
                    }
                    adapterColumnFroze?.notifyDataSetChanged()
                }
            })
        }
    }

    private fun getColumnFrozen(list: List<DetalleIndicadorModel>?): List<String> {
        val lst = ArrayList<String>()
        if (list != null) {
            for (model in list) {
                lst.add(model.codigo.orEmpty())
            }
        }
        return lst
    }

    private fun handleRecyclerAdapterAutoFit(adapter: DetalleUneteAdapter) {

        rvConsolidado?.layoutManager = LinearLayoutManager(activity)
        rvConsolidado?.adapter = adapter

        adapterColumnFroze = FirstColumnAdapter(
            frozen,
            getZoneRegionCountryByRol(consolidadoPresenter.getRol()), this
        )

        adapterColumnFroze?.setLeftArrowVisibility(visibilityArrows)

        rvLeft?.layoutManager = LinearLayoutManager(activity)
        rvLeft?.adapter = adapterColumnFroze
        rvConsolidado?.visible()
        adapter.autoFit(rvConsolidado)

        ViewCompat.postOnAnimationDelayed(rvConsolidado ?: return, {
            rvConsolidado?.visible()
            hideLoading()
        }, 250L)

    }

    private fun getZoneRegionCountryByRol(rol: String): String {
        return when (rol.toUpperCase()) {
            Rol.GERENTE_ZONA.codigoRol -> getString(R.string.my_zone_one_line)
            Rol.GERENTE_REGION.codigoRol -> getString(R.string.my_region_one_line)
            Rol.DIRECTOR_VENTAS.codigoRol -> getString(R.string.my_country_one_line)
            else -> Constant.EMPTY_STRING
        }
    }

    interface ConsolidadoResumenFragmentListener {
        fun onHeaderClick(modo: Int)
        fun onSelectZone(zone: String)
    }

}
