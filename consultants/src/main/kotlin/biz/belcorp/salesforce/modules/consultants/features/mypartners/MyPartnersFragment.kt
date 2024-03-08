package biz.belcorp.salesforce.modules.consultants.features.mypartners

import android.os.Bundle
import android.view.View
import androidx.annotation.ColorInt
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import biz.belcorp.mobile.components.core.extensions.getColorAttr
import biz.belcorp.mobile.components.core.extensions.getDrawable
import biz.belcorp.mobile.components.core.extensions.tinted
import biz.belcorp.salesforce.core.base.BaseDialogFragment
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.constants.Constant.PARTNER_DATA
import biz.belcorp.salesforce.core.constants.Constant.SECTION_PARTNER
import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.core.entities.configuration.ConfigurationCountryEntity_.phoneCode
import biz.belcorp.salesforce.core.entities.mypartners.MyPartnerEntity
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.features.utils.PERSON_IDENTIFIER_KEY
import biz.belcorp.salesforce.core.include.Include
import biz.belcorp.salesforce.core.include.IncludeManager
import biz.belcorp.salesforce.core.utils.actionBar
import biz.belcorp.salesforce.core.utils.enviarAWhatsapp
import biz.belcorp.salesforce.core.utils.showOnce
import biz.belcorp.salesforce.core.utils.toUpperCase
import biz.belcorp.salesforce.core.utils.withArguments
import biz.belcorp.salesforce.modules.consultants.R
import biz.belcorp.salesforce.modules.consultants.core.data.network.dto.MyPartnersDto
import com.google.android.material.appbar.MaterialToolbar
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragments_my_partners.myPartnerToolbar
import kotlinx.android.synthetic.main.fragments_my_partners.myPartners
import kotlinx.android.synthetic.main.fragments_my_partners.searchPartner
import kotlinx.serialization.json.Json.Default.configuration
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class MyPartnersFragment : BaseDialogFragment() {


    private val viewModel by viewModel<MyPartnersViewModel>()
    private val includeManager by inject<IncludeManager>()

    private val adapter by lazy { MyPartnersAdapter() }
    override fun getLayout(): Int = R.layout.fragments_my_partners

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setSearchWidget()
        setupViewModel()

    }

    private fun setupToolbar() {
        actionBar(myPartnerToolbar as? MaterialToolbar) {
            title = context.getString(R.string.title_my_partners)
            val titleColor = getColorAttr(android.R.attr.textColorPrimary)
            customizeReturnableMode(titleColor)
        }
    }

    private fun Toolbar.customizeReturnableMode(@ColorInt titleColor: Int) {
        val icon = getDrawable(biz.belcorp.salesforce.core.R.drawable.ic_backspace)
        val iconTinted = icon?.tinted(titleColor)
        navigationIcon = iconTinted?.mutate()
        setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupViewModel() {
        viewModel.viewState.observe(viewLifecycleOwner, partnersViewStateObserver)
        viewModel.getMyPartnerInformation()
        viewModel.getConfiguration()

    }

    private val partnersViewStateObserver = Observer<MyPartnersViewModel.MyPartnersViewState> { state ->
        state?.let {
            when (it) {
                is MyPartnersViewModel.MyPartnersViewState.SuccessPartners -> loadPartnersData(it.data)
                else -> {}
            }
        }
    }

    private fun loadPartnersData(data: List<MyPartnerEntity>) {

        val order = data.sortedBy { it.section }

        myPartners.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = this@MyPartnersFragment.adapter
        }

        adapter.clickListener = object : MyPartnersAdapter.ClickListener {

            override fun callWhatsApp(numero: String?) {
                context?.enviarAWhatsapp(numberFormatted(viewModel.phoneCode.value.toString(), numero.toString()))
            }

            override fun clickDetailConsultant(personaId: Int, codigo: String, rol: Rol) {
                val personIdentifier = PersonIdentifier(personaId.toLong(), codigo, rol)
                goToProfile(personIdentifier)
            }

            override fun clickProjection(section: String) {
                val bundle = bundleOf(SECTION_PARTNER to section)
                findNavController().navigate(biz.belcorp.salesforce.base.R.id.navToCalculator, bundle)
            }

            override fun clickChangeLevel(model: MyPartnerEntity) {
                val dataLevel = MyPartnersDto.NewBusinessPartner(
                    campaign = model.campaign,
                    region = model.region,
                    zone = model.zone,
                    section = model.section,
                    consultantId = model.consultantId,
                    consultantCode = model.consultantCode,
                    personalInfo = MyPartnersDto.NewBusinessPartner.PersonalInfo(
                        firstName = model.personalInfo[0].firstName,
                        secondName = model.personalInfo[0].secondName,
                        surname = model.personalInfo[0].surname,
                        secondSurname = model.personalInfo[0].secondSurname,

                        ),

                    )

                val bundle = bundleOf(PARTNER_DATA to Gson().toJson(dataLevel))
                findNavController().navigate(biz.belcorp.salesforce.base.R.id.actionGoToBrightPathChangeLevelFragment, bundle)
            }
        }

        adapter.setList(order)

    }

    private fun setSearchWidget() {

        searchPartner.setOnClickListener {
            searchPartner.isIconified = false
        }

        searchPartner.setOnCloseListener {
            onSearch("")
            false
        }

        searchPartner.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                onSearch(newText ?: "")
                return true
            }
        })

    }

    fun onSearch(data: String) {
        val list = viewModel.originalData.value!!
        val filterData = list.filter {
            it.personalInfo[0].fullName.toUpperCase().contains(data.toUpperCase())
        }
        adapter.setList(filterData)
    }


    fun goToProfile(personIdentifier: PersonIdentifier) {
        val profileFragment = getProfileFragment(personIdentifier)
        profileFragment?.showOnce(childFragmentManager)
    }

    private fun getProfileFragment(personIdentifier: PersonIdentifier): BaseDialogFragment? {
        return includeManager.getInclude(Include.PROFILE)
            .withArguments(
                PERSON_IDENTIFIER_KEY to personIdentifier
            ) as? BaseDialogFragment
    }

    private fun numberFormatted(phoneCode: String, number: String): String {
        val phoneCodeWithoutPlus = phoneCode.replace(Constant.PLUS, Constant.EMPTY_STRING)
        val numberPhoneWithoutPlus = number.replace(Constant.PLUS, Constant.EMPTY_STRING)

        return when {
            numberPhoneWithoutPlus.startsWith(phoneCodeWithoutPlus) -> number
            else -> phoneCode.plus(number)
        }.replace(Constant.PLUS, Constant.EMPTY_STRING)
    }
}
