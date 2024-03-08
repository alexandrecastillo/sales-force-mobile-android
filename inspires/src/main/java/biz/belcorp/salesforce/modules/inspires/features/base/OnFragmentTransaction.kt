package biz.belcorp.salesforce.modules.inspires.features.base

import androidx.fragment.app.FragmentTransaction

interface OnFragmentTransaction {

    fun onExecute(f: FragmentTransaction.() -> FragmentTransaction)

}
