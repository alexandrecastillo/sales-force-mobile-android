package biz.belcorp.salesforce.modules.virtualmethodology.features.contacts

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import biz.belcorp.mobile.components.core.extensions.getColor
import biz.belcorp.mobile.components.core.extensions.tinted
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.utils.*
import biz.belcorp.salesforce.modules.virtualmethodology.R
import biz.belcorp.salesforce.modules.virtualmethodology.features.contacts.adapter.ListContactsAdapter
import biz.belcorp.salesforce.modules.virtualmethodology.features.contacts.model.ListContacts
import kotlinx.android.synthetic.main.fragment_list_consultant.*
import kotlinx.android.synthetic.main.fragment_progressbar.*
import kotlinx.android.synthetic.main.include_toolbar_methodology.*
import org.koin.android.viewmodel.ext.android.viewModel
import biz.belcorp.salesforce.base.R as BaseR

class ListContactsFragment : BaseFragment() {

    override fun getLayout(): Int = R.layout.fragment_list_consultant

    companion object {
        const val PERMISSIONS_REQUEST_READ_CONTACTS = 100
        const val PERMISSION_REQUEST_CODE = 4000
        const val PUT_EXTRA_EVENT = "sms_body"
    }

    private val listContactsViewModel by viewModel<ListContactsViewModel>()
    private var filterListContacts: List<ListContacts> = listOf()
    private var filterCheckContacts: List<ListContacts> = listOf()
    private var adapter = ListContactsAdapter()

    private val args
        get() = arguments?.let {
            ListContactsFragmentArgs.fromBundle(it)
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listContactsViewModel.viewState.observe(viewLifecycleOwner, observeViewState)
        prepareToolbar()
        layoutLoading?.visible()
        loadContacts()
        filterContacts()
        sendMessageContacts()
    }

    private fun prepareToolbar() {
        actionBar(toolbarHeaderMethodology) {
            title = getString(R.string.consultants_contacts_options)
            setBackgroundColor(getColor(R.color.color_purple))
            setNavigationIcon(BaseR.drawable.icon_backspace)
            navigationIcon?.tinted(getColor(BaseR.color.white))
            setNavigationOnClickListener {
                activity?.onBackPressed()
                dismissKeyboard()
            }
        }
    }

    private fun showListContacts(contacts: List<ListContacts>) {
        if (contacts.isEmpty()) { toast(getString(R.string.no_contacts_available)) }
        filterListContacts = contacts
        mRecyclerContacts?.layoutManager = LinearLayoutManager(context)
        adapter = ListContactsAdapter(
            contacts
        ) {
            mRecyclerContacts?.performClick()
        }
        mRecyclerContacts?.adapter = adapter

    }

    private fun filterContacts() {
        consultantsFilter?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) = Unit
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) =
                Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                mRecyclerContacts?.adapter.apply {
                    adapter.filter.filter(s.toString())
                }
            }
        })
    }

    private fun multipleNumbers(contactsCheck: List<ListContacts>): String {
        var sms = "smsto:"
        var i = 0

        contactsCheck.forEach {
            if (!it.phone.isNullOrBlank()) {
                if (i < 19) {
                    sms += it.phone + ";"
                    i++
                } else
                    sms += it.phone
            }
        }
        return sms
    }

    private fun sendMessageContacts() {
        buttonSend?.setOnClickListener {
            filterCheckContacts = filterListContacts.filter { it.checked }
            if (filterCheckContacts.isEmpty()) {
                Toast.makeText(
                    context,
                    getString(R.string.must_select_contact),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val smsIntent = Intent(
                    Intent.ACTION_SENDTO,
                    Uri.parse(multipleNumbers(filterCheckContacts))
                ).apply {
                    putExtra(PUT_EXTRA_EVENT, args?.imageUrl)
                }
                startActivity(smsIntent)
            }

        }
    }

    private val observeViewState = Observer { state: ListContactsViewState ->
        when (state) {
            is ListContactsViewState.MyContacts -> {
                showListContacts(state.contacts)
                layoutLoading?.gone()
            }
        }
    }

    private fun loadContacts() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ActivityCompat.checkSelfPermission(
                requireActivity(), Manifest.permission.READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf(Manifest.permission.READ_CONTACTS),
                PERMISSIONS_REQUEST_READ_CONTACTS
            )
            ActivityCompat.requestPermissions(
                requireActivity(), arrayOf(Manifest.permission.READ_CONTACTS),
                PERMISSION_REQUEST_CODE
            )
        } else {
            listContactsViewModel.getListContacts()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                loadContacts()
            else
                Toast.makeText(context, R.string.permission, Toast.LENGTH_SHORT).show()
        }
    }
}
