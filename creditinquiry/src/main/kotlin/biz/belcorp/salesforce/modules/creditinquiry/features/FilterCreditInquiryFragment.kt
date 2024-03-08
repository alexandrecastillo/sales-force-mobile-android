package biz.belcorp.salesforce.modules.creditinquiry.features

import android.app.Activity
import android.os.Bundle
import android.text.InputFilter
import android.view.View
import android.widget.EditText
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.navigation.fragment.findNavController
import biz.belcorp.mobile.components.core.extensions.setSafeOnClickListener
import biz.belcorp.salesforce.analytics.core.domain.entities.ScreenTag
import biz.belcorp.salesforce.analytics.features.trackAnalytics
import biz.belcorp.salesforce.base.utils.navigateSafe
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.utils.dismissKeyboard
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.modules.creditinquiry.R
import biz.belcorp.salesforce.modules.creditinquiry.features.model.ConsultaCrediticiaInternaModel
import biz.belcorp.salesforce.modules.creditinquiry.features.presenters.ConsultaCrediticiaPresenter
import biz.belcorp.salesforce.modules.creditinquiry.features.util.KeyboardUtil
import biz.belcorp.salesforce.modules.creditinquiry.utils.AnalyticUtils
import kotlinx.android.synthetic.main.fragment_credit_consulting_detail.scroll
import kotlinx.android.synthetic.main.fragment_credit_consulting_filter.*
import kotlinx.android.synthetic.main.fragment_credit_consulting_filter.iv_back
import kotlinx.android.synthetic.main.fragment_credit_consulting_filter.tv_error_message
import java.util.*
import biz.belcorp.salesforce.base.R as BaseR


const val INTERNAL_CREDIT_INQUIRY_ARGS = "INTERNAL_CREDIT_INQUIRY_ARGS"


class FilterCreditInquiryFragment : BaseFragment(), ConsultaCrediticiaView {

    override fun getLayout(): Int {
        return R.layout.fragment_credit_consulting_filter
    }

    private val presenter: ConsultaCrediticiaPresenter by injectFragment()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ViewCompat.setTranslationZ(view, 2F)

        presenter.setView(this)
        presenter.checkTitle()

        setMaxLengthIdentityDocument(et_identity_document)

        scroll?.setOnClickListener {
            KeyboardUtil.dismissKeyboard(activity, getView())
        }

        btn_search?.setSafeOnClickListener {
            et_identity_document?.dismissKeyboard()
            val documentId = et_identity_document?.text.toString().trim()

            et_identity_document?.setText(documentId)

            when (documentId.isEmpty()) {
                true -> tv_error_message?.text =
                    getString(R.string.you_must_enter_identity_document)
                false -> {
                    tv_error_message?.text = Constant.EMPTY_STRING
                    presenter.setView(this)
                    presenter.searchConsultaCrediticiaDeudaInterna(
                        documentId
                    )
                }
            }
        }

        iv_back?.setOnClickListener {
            et_identity_document?.dismissKeyboard()
            activity?.onBackPressed()
        }

    }

    override fun onResume() {
        super.onResume()
        sendAnalytics()
    }

    private fun sendAnalytics() {
        AnalyticUtils.screen()
        trackAnalytics(ScreenTag.CREDIT_INQUIRY)
    }

    private fun setMaxLengthIdentityDocument(editText: EditText?) {
        val filterArray = arrayOfNulls<InputFilter>(1)
        filterArray[0] = InputFilter.LengthFilter(lengthIdentityDocument())
        editText?.filters = filterArray
    }

    private fun lengthIdentityDocument(): Int {
        val sortItemList = ArrayList<Pair<String, String>>()
        val resources = resources
        val sortItemArray = resources.obtainTypedArray(R.array.identity_document)
        val arrayLength = sortItemArray.length()
        for (i in 0 until arrayLength) {
            val id = sortItemArray.getResourceId(i, -1)
            if (id != -1) {
                val item = resources.getStringArray(id)
                sortItemList.add(Pair(item[0], item[1]))
            }
        }
        sortItemArray.recycle()

        val codeCountry = presenter.session.countryIso

        for ((first, second) in sortItemList) {
            if (first == codeCountry) {
                return Integer.valueOf(second)
            }
        }
        return 0
    }

    override fun showConsultaCrediticia(consultaCrediticiaModel: ConsultaCrediticiaInternaModel?) {
        if (!isAdded) return
        consultaCrediticiaModel?.let {
            it.documentoIdentidad = et_identity_document?.text.toString().trim()

            val bundle = bundleOf(
                INTERNAL_CREDIT_INQUIRY_ARGS to it
            )
            findNavController()
                .navigateSafe(
                    BaseR.id.action_filterCreditInquiryFragment_to_creditConsultingDetailFragment,
                    bundle
                )

        }
    }

    override fun showMessageError(message: String) {
        tv_error_message?.text = message
    }

    override fun setNewTitle(title: String) {
        tv_credit_consulting_title.text = title
    }

    override fun getFragment(): BaseFragment? {
        return this
    }
}
