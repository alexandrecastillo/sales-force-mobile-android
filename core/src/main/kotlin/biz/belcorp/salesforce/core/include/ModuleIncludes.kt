package biz.belcorp.salesforce.core.include

import androidx.fragment.app.Fragment


interface ModuleIncludes {

    val includesId: List<Int>

    fun getInclude(@Include includeId: Int): Fragment?

}
