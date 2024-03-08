package biz.belcorp.salesforce.base.features.splash

import android.os.Bundle
import biz.belcorp.salesforce.base.R
import biz.belcorp.salesforce.core.BuildConfig.NEW_RELIC_TOKEN
import biz.belcorp.salesforce.core.base.BaseActivity
import biz.belcorp.salesforce.core.utils.AppBuildConfig.isDebug
import biz.belcorp.salesforce.core.utils.applyIf
import com.newrelic.agent.android.NewRelic
import com.newrelic.agent.android.logging.AgentLog.AUDIT

class SplashActivity : BaseActivity() {

    override fun getLayout() = R.layout.activity_splash

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setUpNewRelic()
    }

    private fun setUpNewRelic() {
        NewRelic
            .withApplicationToken(NEW_RELIC_TOKEN)
            .applyIf(isDebug()) {
                withLoggingEnabled(true)
                    .withLogLevel(AUDIT)
            }
            .start(application)
    }

}
