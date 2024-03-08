package biz.belcorp.salesforce.modules.brightpath.features.ua

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.utils.inflate
import biz.belcorp.salesforce.core.utils.withArguments
import biz.belcorp.salesforce.modules.brightpath.R
import kotlinx.android.synthetic.main.fragment_uasegmets.*
import kotlinx.android.synthetic.main.item_ua_circle.view.*
import org.koin.android.ext.android.inject
import biz.belcorp.salesforce.base.R as baseR


class UASegmentsFragment : BaseFragment(), UASegmentView {

    override fun getLayout() = R.layout.fragment_uasegmets

    private val uaSegmentPresenter: UASegmentPresenter by inject()

    private val segmentsAdapter = UASegmentsAdapter()

    private var mListener: OnUASegmentSelectedListener? = null


    companion object {
        private const val HAS_USER_UA_SEGMENT_VIEW_TO_BE_SHOWN = "HAS_USER_UA_SEGMENT_VIEW_TO_BE_SHOWN"
        const val ARG_PREV_UA_ID_SELECTED = "PREV_UA_SEGMENT_ID_SELECTED"

        fun newInstance(prevUaIdSelected: String,
                        showUserSegmentView: Boolean): UASegmentsFragment {
            return UASegmentsFragment().withArguments(
                    ARG_PREV_UA_ID_SELECTED to prevUaIdSelected,
                    HAS_USER_UA_SEGMENT_VIEW_TO_BE_SHOWN to showUserSegmentView
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        doWithArgs()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_uasegmets, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.uaSegmentPresenter.setView(this)
        initSegments()
        initEvents()
    }

    override fun onDestroy() {
        super.onDestroy()
        uaSegmentPresenter.destroy()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnUASegmentSelectedListener) {
            mListener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    private fun onCurrentUserSegmentSelected() = View.OnClickListener {
        if ((it as? TextView)?.isSelected == false) {
            mListener?.onSegmentSelected(null)
            rvSegments3?.getChildAt(
                    uaSegmentPresenter.getCurrentPosSelected()
            )?.let { view -> unSelectView(view as TextView) }

            uaSegmentPresenter.changeSegmentToSelected(-1)
            uaSegmentPresenter.setCurrentUASelected((it as? TextView)?.text.toString())

            selectCurrentSegmentView()
        }
    }

    private fun doWithArgs() {
        if (arguments != null) {
            arguments?.let {
                it.getString(ARG_PREV_UA_ID_SELECTED)?.apply {
                    uaSegmentPresenter.setCurrentUASelected(this)
                }
            }
            uaSegmentPresenter.setUserUaSelected(false)
        } else {
            uaSegmentPresenter.setUserUaSelected(true)
        }
    }

    override fun showUserSegment(countryInt: Int) {
        if (countryInt != 0) {
            tvCurrentUserSegment?.text = getString(countryInt)
            selectCurrentSegmentView()
        }
    }

    override fun setUpRecyclerView() {
        rvSegments3?.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
            adapter = segmentsAdapter
        }
    }

    override fun showUaItemAsSelected(pos: Int) {
        uaSegmentPresenter.setCurrentUaPosSelected(pos)
        segmentsAdapter.doOnSegmentSelected(pos)
        rvSegments3?.scrollToPosition(pos)
        mListener?.onSegmentSelected(uaSegmentPresenter.getSegment(pos))
    }

    private fun selectCurrentSegmentView() {
        tvCurrentUserSegment?.apply { selectView(this) }
    }

    private fun unSelectCurrentSegmentView() {
        tvCurrentUserSegment?.apply { unSelectView(this) }
    }

    private fun unSelectView(tv: TextView) {
        tv.apply {
            isSelected = false
            setTextColor(ContextCompat.getColor(tv.context, R.color.color_gray_home))
        }
    }

    private fun selectView(tv: TextView) {
        tv.apply {
            isSelected = true
            setTextColor(ContextCompat.getColor(tv.context, baseR.color.white))
        }
    }

    private fun onSegmentSelected(pos: Int, segment: UASegmentModel) {
        unSelectCurrentSegmentView()
        uaSegmentPresenter.changeSegmentToSelected(pos)
        uaSegmentPresenter.setCurrentUASelected(segmentsAdapter.getCurrentSegmentSelected())
        mListener?.onSegmentSelected(segment)
    }


    private fun initEvents() {
        tvCurrentUserSegment?.setOnClickListener(onCurrentUserSegmentSelected())
        ivLeftArrow2?.setOnClickListener {
            rvSegments3.smoothScrollBy(View.FOCUS_RIGHT * -3, 0)
        }
        ivRightArrow2?.setOnClickListener {
            rvSegments3.smoothScrollBy(View.FOCUS_RIGHT * 3, 0)
        }
    }

    private fun mostrarSegmentoDeUsuarioConectado() {
        if (uaSegmentPresenter.hasUserUaViewBeShown()) {
            tvCurrentUserSegment?.visibility = View.VISIBLE
        } else {
            tvCurrentUserSegment?.visibility = View.GONE
        }
    }

    private fun initSegments() {
        mostrarSegmentoDeUsuarioConectado()
        uaSegmentPresenter.showUserSegment()
        selectCurrentSegmentView()
        uaSegmentPresenter.getSegments()
    }

    fun setViewListener(listener: OnUASegmentSelectedListener) {
        mListener = listener
    }

    inner class UASegmentsAdapter : RecyclerView.Adapter<UASegmentsAdapter.ViewHolder>() {

        private var oldViewSelected: View? = null

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(parent.inflate(R.layout.item_ua_circle))
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(uaSegmentPresenter.getSegmentList()[position].segmentID)
            manageSelectedItem(holder.itemView.tv_item, position)
        }

        override fun getItemCount(): Int {
            return uaSegmentPresenter.getSegmentsListSize()
        }

        private fun manageSelectedItem(holder: TextView, position: Int) {
            if (uaSegmentPresenter.isItemSelected(position) == true) {
                selectView(holder)
            } else {
                unSelectView(holder)
            }
            oldViewSelected = holder
        }

        fun getCurrentSegmentSelected() = uaSegmentPresenter.getCurrentUASelected()

        fun doOnSegmentSelected(adapterPosition: Int) {
            uaSegmentPresenter.changeSegmentToSelected(adapterPosition)
            notifyItemChanged(adapterPosition)

            uaSegmentPresenter.setCurrentUASelected(
                    uaSegmentPresenter.getSegmentId(adapterPosition)
            )
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

            fun bind(segment: String) {
                itemView.tv_item?.text = segment
                itemView.setOnClickListener(this)
            }

            override fun onClick(tv: View?) {
                tv?.apply {
                    if (isSelected) return

                    notifyItemChanged(uaSegmentPresenter.getCurrentPosSelected())

                    doOnSegmentSelected(adapterPosition)

                    oldViewSelected = tv

                    onSegmentSelected(
                            adapterPosition,
                            uaSegmentPresenter.getSegment(adapterPosition)
                    )
                }
            }
        }
    }
}
