package biz.belcorp.salesforce.modules.developmentpath.features.profile.dream

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.core.features.utils.lazyPersonIdentifier
import biz.belcorp.salesforce.core.features.utils.withPersonIdentifier
import biz.belcorp.salesforce.core.utils.formatWithNoDecimalThousands
import biz.belcorp.salesforce.core.utils.isNotNull
import biz.belcorp.salesforce.core.utils.toast
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.dream.Dream
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.dream.DreamBusinessPartner
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.dream.DreamCampaign
import biz.belcorp.salesforce.modules.developmentpath.features.profile.dream.deletedream.DeleteDreamDialogFragment
import biz.belcorp.salesforce.modules.developmentpath.features.profile.dream.viewstate.DreamBpViewState
import biz.belcorp.salesforce.modules.developmentpath.features.profile.dream.viewstate.DreamViewState
import biz.belcorp.salesforce.modules.developmentpath.features.profile.dream_add.AddDreamFragment
import biz.belcorp.salesforce.modules.developmentpath.utils.Constants
import kotlinx.android.synthetic.main.fragment_dream.*
import org.koin.android.viewmodel.ext.android.sharedViewModel

private const val ADD_DREAM_DIALOG = "add_fragment"

class DreamFragment : BaseFragment() {
    private val viewModel by sharedViewModel<DreamViewModel>(from = { this })
    private val personIdentifier by lazyPersonIdentifier()
    private var currentDream: Dream? = null
    private val deleteDreamDialogFragment by lazy {
        return@lazy DeleteDreamDialogFragment()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpSeekBar()
        setupViewModel()
        setUpButtons()
    }

    private fun setUpButtons() {
        deleteDreamDialogFragment.setListener {
            viewModel.deleteDream(currentDream)
        }
        ll_button_delete.setOnClickListener {
            deleteDreamDialogFragment.show(
                childFragmentManager,
                DeleteDreamDialogFragment.TAG
            )
        }

        bt_add_dream.setOnClickListener {
            loadAddEditDream(null)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setUpSeekBar() {
        sb_dream_progress.setOnTouchListener { _, _ -> true }
    }

    private fun setupViewModel() {
        viewModel.viewState.observe(viewLifecycleOwner, viewStateObserver)
        viewModel.viewStateBp.observe(viewLifecycleOwner, viewStateBpObserver)
        viewModel.getDream(personIdentifier)
    }

    private val viewStateObserver = Observer<DreamViewState> {
        when (it) {
            is DreamViewState.Success -> {
                if (it.dream?.dream.isNullOrBlank()) {
                    showEmptyDream(it.dream?.currencySymbol)
                } else {
                    currentDream = it.dream
                    showInfo(it.dream)
                }
            }

            is DreamViewState.Failed -> showError()
        }
    }

    private val viewStateBpObserver = Observer<DreamBpViewState> {
        when (it) {
            is DreamBpViewState.Success -> {
                if (it.dreamBusinessPartner?.dream.isNullOrBlank()) {
                    showEmptyDream(it.dreamBusinessPartner?.currencySymbol)
                } else {
                    currentDream = (it.dreamBusinessPartner) as Dream
                    showBusinessPartnerInfo(it.dreamBusinessPartner)
                }
            }

            is DreamBpViewState.Failed -> showError()
        }
    }

    private fun showEmptyDream(currencySymbol: String?) {
        header.hitTitle = getString(R.string.dream_empty_header_title)
        tv_dream_question_answer_1.text = getString(R.string.dream_empty_answer_suggest)
        tv_dream_question_answer_2.text = getString(R.string.dream_empty_answer)
        tv_dream_question_answer_3.text = getString(R.string.dream_empty_answer)
        tv_dream_question_answer_4.text = getString(R.string.dream_empty_answer)
        tv_accumulated_money.text = getString(
            R.string.dream_accumulated_money, currencySymbol,
            Constants.NUMBER_ZERO.toString()
        )
        tv_remaining_money.text = getString(
            R.string.dream_remaining_money, currencySymbol,
            Constants.NUMBER_ZERO.toString()
        )
        ll_campaigns_numbers_container.visibility = View.GONE
        ll_actions_button_container.visibility = View.GONE
        rv_dream_campaigns.visibility = View.GONE
        bt_add_dream.visibility = View.VISIBLE

    }

    private fun showInfo(dream: Dream?) {
        dream?.let {
            header.hitTitle =
                getString(R.string.dream_header_title, dream.consultantName?.toLowerCase())
            tv_dream_question_answer_1.text = dream.dream
            tv_dream_question_answer_2.text = getString(
                R.string.dream_amount_remaining,
                dream.currencySymbol,
                dream.amountToComplete.toString()
            )
            tv_dream_question_answer_3.text = getString(
                R.string.dream_campaign_remaining,
                dream.numberCampaignsToComplete.toString(),
                dream.campaignCreated,
                dream.campaignEnd
            )
            tv_dream_question_answer_4.text = dream.comment

            val remainingAmount = dream.amountToComplete?.minus(dream.totalGain!!.toInt())

            if (remainingAmount!! > 0 && dream.amountToComplete.isNotNull()) {
                tv_remaining_money.text = getString(
                    R.string.dream_remaining_money, dream.currencySymbol,
                    remainingAmount.toString()
                )
                sb_dream_progress.progress =
                    dream.totalGain!!.toInt() * 100 / dream.amountToComplete
            } else {
                tv_remaining_money.text = getString(
                    R.string.dream_remaining_money, dream.currencySymbol,
                    Constants.NUMBER_ZERO.toString()
                )
                sb_dream_progress.progress = Constants.NUMBER_HUNDRED
            }

            tv_accumulated_money.text = getString(
                R.string.dream_accumulated_money, dream.currencySymbol,
                dream.totalGain?.formatWithNoDecimalThousands()
            )
            ll_actions_button_container.visibility =
                if (dream.status.equals(Constants.COMPLETED)) View.GONE else View.VISIBLE

            ll_button_edit.setOnClickListener {
                loadAddEditDream(dream)
            }
            bt_add_dream.visibility =
                if (dream.status.equals(Constants.COMPLETED)) View.VISIBLE else View.GONE

        }

        if (dream?.campaignList?.isNotEmpty()!!) loadProgressByCampaigns(dream.campaignList, dream)

    }


    private fun showBusinessPartnerInfo(dreamBp: DreamBusinessPartner?) {
        dreamBp?.let {
            header.hitTitle =
                getString(R.string.dream_header_title, dreamBp.bpName?.toLowerCase())
            tv_dream_question_answer_1.text = dreamBp.dream
            tv_dream_question_answer_2.text = getString(
                R.string.dream_amount_remaining,
                dreamBp.currencySymbol,
                dreamBp.amountToComplete.toString()
            )
            tv_dream_question_answer_3.text = getString(
                R.string.dream_campaign_remaining,
                dreamBp.numberCampaignsToComplete.toString(),
                dreamBp.campaignCreated,
                dreamBp.campaignEnd
            )
            tv_dream_question_answer_4.text = dreamBp.comment

            val remainingAmount = dreamBp.amountToComplete?.minus(dreamBp.totalGain!!.toInt())

            if (remainingAmount!! > 0 && dreamBp.amountToComplete.isNotNull()) {
                tv_remaining_money.text = getString(
                    R.string.dream_remaining_money, dreamBp.currencySymbol,
                    remainingAmount.toString()
                )
                sb_dream_progress.progress =
                    dreamBp.totalGain!!.toInt() * 100 / dreamBp.amountToComplete
            } else {
                tv_remaining_money.text = getString(
                    R.string.dream_remaining_money, dreamBp.currencySymbol,
                    Constants.NUMBER_ZERO.toString()
                )
                sb_dream_progress.progress = Constants.NUMBER_HUNDRED
            }

            tv_accumulated_money.text = getString(
                R.string.dream_accumulated_money, dreamBp.currencySymbol,
                dreamBp.totalGain?.formatWithNoDecimalThousands()
            )
            ll_actions_button_container.visibility =
                if (dreamBp.status.equals(Constants.COMPLETED)) View.GONE else View.VISIBLE

            ll_button_edit.setOnClickListener {
                loadAddEditDream(dreamBp)
            }
            bt_add_dream.visibility =
                if (dreamBp.status.equals(Constants.COMPLETED)) View.VISIBLE else View.GONE

        }

        if (dreamBp?.campaignList?.isNotEmpty()!!) loadProgressByCampaigns(
            dreamBp.campaignList,
            dreamBp
        )

    }


    private fun loadProgressByCampaigns(
        dreamCampaigns: List<DreamCampaign>?,
        dream: Dream?
    ) {

        val latestSixCampaigns = if (dreamCampaigns?.size!! >= 6) {
            dreamCampaigns?.takeLast(6)
        } else {
            dreamCampaigns
        }

        createNumberCampaignsToCompleteView(
            dream?.numberCampaignsToComplete,
            latestSixCampaigns.size
        )
        ll_campaigns_numbers_container.visibility = View.VISIBLE
        rv_dream_campaigns.visibility = View.VISIBLE
        rv_dream_campaigns.layoutManager = LinearLayoutManager(context)
        rv_dream_campaigns.adapter =
            DreamCampaignsAdapter(latestSixCampaigns, context!!, dream?.currencySymbol)
    }

    private fun createNumberCampaignsToCompleteView(
        numberCampaignsToComplete: Int?,
        campaignListSize: Int
    ) {
        rv_campaigns_numbers.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rv_campaigns_numbers.adapter =
            DreamCampaignsNumberAdapter(numberCampaignsToComplete!!, campaignListSize, context!!)
    }


    private fun loadAddEditDream(dream: Dream?) =
        AddDreamFragment(dream, CreateEditManager()).withPersonIdentifier(personIdentifier)
            .show(childFragmentManager, ADD_DREAM_DIALOG)

    private fun showError() {
        toast(R.string.dream_fetch_error)
    }

    override fun getLayout(): Int = R.layout.fragment_dream


    companion object {
        fun newInstance(personIdentifier: PersonIdentifier) =
            DreamFragment().withPersonIdentifier(personIdentifier)
    }

    inner class CreateEditManager : AddDreamFragment.CreateEditDreamManager {
        override fun onSuccess() {
            viewModel.getDream(personIdentifier)
        }
    }
}
