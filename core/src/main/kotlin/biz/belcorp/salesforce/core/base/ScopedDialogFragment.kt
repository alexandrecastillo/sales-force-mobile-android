package biz.belcorp.salesforce.core.base

import android.os.Bundle
import org.koin.android.ext.android.getKoin
import org.koin.android.scope.bindScope
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope

abstract class ScopedDialogFragment : BaseDialogFragment() {

    private lateinit var scope: Scope

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getScopeName()?.also { bindScope(getOrCreateScope(it) ?: return) }
    }

    protected open fun getScopeName(): String? = null

    private fun getOrCreateScope(qualifier: String): Scope? {
        scope = getKoin().getOrCreateScope(qualifier, named(qualifier))
        return scope
    }

    override fun onDestroyView() {
        getScopeName()?.also { getKoin().deleteScope(it) }
        closeDialog()
        super.onDestroyView()
    }
}
