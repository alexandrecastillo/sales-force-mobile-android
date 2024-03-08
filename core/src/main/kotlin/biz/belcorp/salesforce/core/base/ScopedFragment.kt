package biz.belcorp.salesforce.core.base

import android.os.Bundle
import org.koin.android.ext.android.getKoin
import org.koin.android.scope.bindScope
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope

abstract class ScopedFragment : BaseFragment() {

    protected var scope: Scope? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getScopeName()?.apply { bindScope(getOrCreateScope(this) ?: return) }
    }

    protected open fun getScopeName(): String? = null

    private fun getOrCreateScope(qualifier: String): Scope? {
        scope = getKoin().getOrCreateScope(qualifier, named(qualifier))
        return scope
    }

    override fun onDestroyView() {
        getScopeName()?.apply { scope?.close() }
        super.onDestroyView()
    }
}
