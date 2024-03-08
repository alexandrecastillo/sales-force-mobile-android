package biz.belcorp.salesforce.core.data.preferences

import android.webkit.CookieManager

class WebViewCookieManager {
     fun clearCookies() {
        CookieManager.getInstance().removeAllCookies(null)
        CookieManager.getInstance().flush()
    }
}
