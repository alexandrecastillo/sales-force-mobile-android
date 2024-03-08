package biz.belcorp.salesforce.base.features.exception

import android.os.Bundle
import biz.belcorp.salesforce.base.R
import biz.belcorp.salesforce.base.utils.caoc.Caoc.getConfigFromIntent
import biz.belcorp.salesforce.base.utils.caoc.Caoc.restartApplication
import biz.belcorp.salesforce.core.base.BaseActivity
import com.google.firebase.FirebaseApp
import kotlinx.android.synthetic.main.activity_uncaught_exception.*

class UncaughtExceptionActivity : BaseActivity() {

    override fun getLayout(): Int = R.layout.activity_uncaught_exception

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        btnRestart?.setOnClickListener { restartApp() }
    }

    private fun restartApp() {
        getConfigFromIntent(intent)?.also {
            restartApplication(this, it)
        } ?: finish()
    }

}
