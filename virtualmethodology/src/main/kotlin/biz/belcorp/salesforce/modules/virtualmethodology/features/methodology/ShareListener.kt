package biz.belcorp.salesforce.modules.virtualmethodology.features.methodology

import android.widget.ImageView

interface ShareListener {
    fun clickOnFacebook(image: ImageView)
    fun clickOnWhatsApp(image: ImageView)
    fun clickOnSMS(image: String)
}
