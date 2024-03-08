package biz.belcorp.salesforce.core.utils

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import org.koin.android.ext.android.get
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.getSharedViewModel
import org.koin.core.KoinComponent
import org.koin.core.context.GlobalContext
import org.koin.core.get
import org.koin.core.inject
import org.koin.core.module.Module
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.core.scope.ScopeID
import org.koin.ext.getFullName

inline fun <reified T : Any> Activity.injectActivity(): Lazy<T> =
    inject(parameters = { parametersOf(this) })

inline fun <reified T : Any> Fragment.injectFragment(): Lazy<T> =
    inject(parameters = { parametersOf(this) })

inline fun <reified T : Any> KoinComponent.inject(vararg params: Any?): Lazy<T> =
    inject(parameters = { parametersOf(*params) })

inline fun <reified T : Any> Fragment.inject(vararg params: Any?): Lazy<T> =
    inject(parameters = { parametersOf(*params) })

inline fun <reified T : Any> Activity.get(vararg params: Any?): T =
    get(parameters = { parametersOf(*params) })

inline fun <reified T : Any> Fragment.get(vararg params: Any?): T =
    get(parameters = { parametersOf(*params) })

inline fun <reified T : Any> KoinComponent.get(vararg params: Any?): T =
    get(parameters = { parametersOf(*params) })

inline fun <reified T : Any> KoinComponent.injectAll(): Lazy<List<T>> =
    lazy { getKoin().getAll<T>() }

inline fun <reified T : Any> KoinComponent.getAll(): List<T> =
    getKoin().getAll()

inline fun <reified T : Any> Fragment.injectScoped(
    scopeId: ScopeID,
    scopeInstanceId: ScopeID? = null
): Lazy<T> =
    lazy {
        scopeInstanceId?.let { getKoin().getScopeOrNull(scopeId)?.get<T>(named(it)) }
            ?: run { getKoin().getScope(scopeId).get<T>() }
    }

fun loadModules(modules: List<Module>) {
    GlobalContext.get()
        .unloadModules(modules)
        .modules(modules)
}

inline fun <reified T : ViewModel, reified S : Fragment> Fragment.sharedViewModel(): Lazy<T?> =
    lazy {
        val storeOwner = getSourceFragment(S::class.getFullName())
        storeOwner?.let { getSharedViewModel<T>(null, { it }, null) }
    }

tailrec fun Fragment.getSourceFragment(className: String): Fragment? {
    return parentFragment?.takeIf { it.javaClass.name == className }
        ?: parentFragment?.getSourceFragment(className)
}
