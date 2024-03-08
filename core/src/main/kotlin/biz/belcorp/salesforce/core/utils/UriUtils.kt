package biz.belcorp.salesforce.core.utils

import android.net.Uri


object AppUri {

    private const val SCHEME = "app"
    private const val AUTHORITY = "salesforce.belcorp.biz"

    private const val HOME_PATH = "/home"

    const val CODE_KEY = "CODE"
    const val ACTION_KEY = "ACTION"

    fun create(path: String = HOME_PATH): Uri.Builder {
        return Uri.Builder()
            .authority(AUTHORITY)
            .scheme(SCHEME)
            .path(path)
    }

}

fun Uri.Builder.withParameters(params: HashMap<String, String?>) = apply {
    params.forEach {
        appendQueryParameter(it.key, it.value)
    }
}

fun Uri.Builder.withParameters(vararg params: Pair<String, String?>) = apply {
    params.forEach {
        appendQueryParameter(it.first, it.second)
    }
}
