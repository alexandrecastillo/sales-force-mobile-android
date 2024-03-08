package biz.belcorp.salesforce.core.include

import androidx.fragment.app.Fragment


class IncludeManager(
    private val includes: List<ModuleIncludes>
) {

    fun getInclude(@Include id: Int): Fragment {
        val moduleInclude = includes.firstOrNull { id in it.includesId }
        val include = moduleInclude?.getInclude(id)
        return requireNotNull(include) { MESSAGE_ERROR }
    }

    companion object {

        const val MESSAGE_ERROR = "Include not found"

    }

}
