package biz.belcorp.salesforce.modules.brightpath.features.container.detail.filter

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.modules.brightpath.R
import biz.belcorp.salesforce.modules.brightpath.core.domain.constants.Constants
import biz.belcorp.salesforce.base.R as baseR
import kotlinx.android.synthetic.main.fragment_filter_constancy.*
import org.koin.android.ext.android.inject


class FilterConstancyFragment : BaseFragment(), FilterConstancyView {

    private val presenter: FilterConstancyPresenter by inject()

    private var listener: OnSelectedLevelFilter? = null

    override fun getLayout() = R.layout.fragment_filter_constancy

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.let {
            it.create(this)
            it.initView()
        }
    }

    override fun setupRecyclerView() {
        val typeSelection =
            arguments?.getInt(ARG_BEAUTY_CONSULTANT_TYPE_LIST, Constants.NUMBER_ZERO)
                ?: Constants.NUMBER_ZERO

        listener?.let {
            rvwFilterConsultancyView?.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
                addItemDecoration(
                    FilterConstancyMarginDecoration(
                        resources.getDimension(baseR.dimen.content_inset_small).toInt()
                    )
                )
                adapter = ConstancyFilterAdapter(it, typeSelection)
            }
        }
    }

    fun setListener(listener: OnSelectedLevelFilter) {
        this.listener = listener
    }

    companion object {
        const val ARG_BEAUTY_CONSULTANT_TYPE_LIST = "ARG_BEAUTY_CONSULTANT_TYPE_LIST"

        fun newInstance(): FilterConstancyFragment {
            return FilterConstancyFragment()
        }
    }

}
