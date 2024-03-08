package biz.belcorp.salesforce.modules.developmentpath.features.profile.dream_add

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import biz.belcorp.salesforce.analytics.core.domain.entities.UserProperties
import biz.belcorp.salesforce.core.base.BaseDialogFragment
import biz.belcorp.salesforce.core.features.utils.lazyPersonIdentifier
import biz.belcorp.salesforce.core.utils.toast
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.dream.Dream
import biz.belcorp.salesforce.modules.developmentpath.features.profile.dream.DreamFragment
import biz.belcorp.salesforce.modules.developmentpath.utils.Constants
import kotlinx.android.synthetic.main.fragment_add_dreams.btn_save_accept
import kotlinx.android.synthetic.main.fragment_add_dreams.edt_amount_accomplish
import kotlinx.android.synthetic.main.fragment_add_dreams.edt_comments
import kotlinx.android.synthetic.main.fragment_add_dreams.edt_dream_reason
import kotlinx.android.synthetic.main.fragment_add_dreams.icon_close
import kotlinx.android.synthetic.main.fragment_add_dreams.spinner_campaigns
import kotlinx.android.synthetic.main.fragment_add_dreams.title_dream
import org.koin.android.viewmodel.ext.android.sharedViewModel


class AddDreamFragment(val dream: Dream?, manager: DreamFragment.CreateEditManager) :
    BaseDialogFragment() {

    private lateinit var campaigns: Array<String>
    private val personIdentifier by lazyPersonIdentifier()
    private val viewModel by sharedViewModel<AddDreamViewModel>(from = { this })
    private var saveEditSemaphore = false

    interface CreateEditDreamManager {
        fun onSuccess()
    }

    override fun getLayout(): Int = R.layout.fragment_add_dreams

    override fun onStart() {
        super.onStart()
        fitFullScreen()
        viewModel.actualCampaign(personIdentifier)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindFunction()
        initObservables()
        bindDream()
    }

    private fun bindFunction() {
        campaigns = resources.getStringArray(R.array.array_campaigns)
        with(spinner_campaigns)
        {
            adapter = ArrayAdapter(this.context!!, android.R.layout.simple_spinner_item, campaigns)
            onItemSelectedListener = SpinnerManager()
        }

        edt_dream_reason.doOnTextChanged { text, start, count, after ->
            viewModel.onDreamDescriptionChanged(text.toString())
            edt_dream_reason.error = null
        }
        edt_amount_accomplish.doOnTextChanged { text, start, count, after ->
            viewModel.onDreamAmountChanged(text.toString())
            edt_amount_accomplish.error = null
        }
        edt_comments.doOnTextChanged { text, start, count, after ->
            viewModel.onDreamCommentsChanged(text.toString())
            edt_comments.error = null
        }

        btn_save_accept.setOnClickListener {
            viewModel.checkData()
        }

        icon_close.setOnClickListener {
            closeDialog()
        }
    }

    private fun bindDream() {
        if (dream != null) {
            edt_dream_reason.setText(dream.dream)
            edt_amount_accomplish.setText(dream.amountToComplete.toString())
            edt_comments.setText(dream.comment)
            spinner_campaigns.setSelection(campaigns.indexOf(dream.numberCampaignsToComplete.toString()))
            viewModel.setDreamId(dream.dreamId!!)
            title_dream.text = getString(R.string.edit_dream)
            if (Constants.ACTIVE == dream.status && dream.campaignCreated.toString().toInt()
                >= UserProperties.session?.campaign?.codigo.toString().toInt()
            ) {
                viewModel.onDreamCampaignChanged(UserProperties.session?.campaign?.codigo.toString())
            } else {
                viewModel.onDreamCampaignChanged(dream.campaignCreated.toString())
            }
        }
    }


    private fun initObservables() {
        viewModel.viewState.observe(viewLifecycleOwner, viewStateObserver)
        viewModel.viewEvent.observe(viewLifecycleOwner, viewEventObserver)
    }

    inner class SpinnerManager : AdapterView.OnItemSelectedListener {

        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            viewModel.onDreamsCampaignsAchieveChanged(campaigns[p2])
        }

        override fun onNothingSelected(p0: AdapterView<*>?) {}

    }

    private val viewStateObserver = Observer<AddDreamViewModel.UiState> {
        if (it.validateDreamDescription) edt_dream_reason.error = getString(R.string.empty_value)
        if (it.validateDreamAmount) edt_amount_accomplish.error = getString(R.string.empty_value)
        if (it.validateDreamComments) edt_comments.error = getString(R.string.empty_value)
        if (!it.validateDreamDescription && !it.validateDreamAmount && !it.validateDreamComments
            && !it.validateDreamsCampaignsAchieve && !saveEditSemaphore
        ) {
            saveEditSemaphore = true
            if (dream != null) {
                viewModel.editDream(personIdentifier)
            } else {
                viewModel.saveDream(personIdentifier)
            }
        }
    }

    private val viewEventObserver = Observer<AddDreamViewModel.UiEvent> {
        when (it) {
            is AddDreamViewModel.UiEvent.OnFinished -> {
                saveEditSemaphore = false
                if (dream != null) {
                    toast(getString(R.string.dream_edited))
                } else {
                    toast(getString(R.string.dream_created))
                }
                manager.onSuccess()
                closeDialog()
            }

            is AddDreamViewModel.UiEvent.OnError -> {
                saveEditSemaphore = false
                toast(getString(R.string.dream_error))
            }
        }
    }

}


