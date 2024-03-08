package biz.belcorp.salesforce.base.features.home.shortcuts

import androidx.annotation.DrawableRes
import biz.belcorp.salesforce.base.R
import biz.belcorp.salesforce.base.core.domain.usecases.options.OptionsIdentifier.ACADEMY
import biz.belcorp.salesforce.base.core.domain.usecases.options.OptionsIdentifier.BRIGHT_PATH
import biz.belcorp.salesforce.base.core.domain.usecases.options.OptionsIdentifier.CAMPAIGN_REPORTS
import biz.belcorp.salesforce.base.core.domain.usecases.options.OptionsIdentifier.DEVELOPMENT_PATH
import biz.belcorp.salesforce.base.core.domain.usecases.options.OptionsIdentifier.DIGITAL
import biz.belcorp.salesforce.base.core.domain.usecases.options.OptionsIdentifier.INSPIRE_PROGRAM
import biz.belcorp.salesforce.base.core.domain.usecases.options.OptionsIdentifier.JOIN_US
import biz.belcorp.salesforce.base.core.domain.usecases.options.OptionsIdentifier.MANAGE_BRIGHT_PATH_CHANGE
import biz.belcorp.salesforce.base.core.domain.usecases.options.OptionsIdentifier.MATERIALES_REDES_SOCIALES
import biz.belcorp.salesforce.base.core.domain.usecases.options.OptionsIdentifier.MY_PARTNERS
import biz.belcorp.salesforce.base.core.domain.usecases.options.OptionsIdentifier.POSTULANT_EVALUATION
import biz.belcorp.salesforce.base.core.domain.usecases.options.OptionsIdentifier.UCB
import biz.belcorp.salesforce.base.core.domain.usecases.options.OptionsIdentifier.PROJECTION
import biz.belcorp.salesforce.core.domain.entities.options.Option
import biz.belcorp.salesforce.core.utils.SingleMapper

class ShortcutMapper : SingleMapper<Option, ShortcutModel>() {

    override fun map(value: Option) = ShortcutModel(
        code = value.code.toInt(),
        text = value.description,
        iconRes = getIconRes(value.code.toInt())
    )

    @DrawableRes
    private fun getIconRes(code: Int): Int = when (code) {
        CAMPAIGN_REPORTS -> R.drawable.ic_shortcut_campaign_reports
        JOIN_US -> R.drawable.ic_shortcut_unete
        DEVELOPMENT_PATH -> R.drawable.ic_shortcut_development_path
        INSPIRE_PROGRAM -> R.drawable.ic_shortcut_inspire_program
        BRIGHT_PATH -> R.drawable.ic_shortcut_bright_path
        ACADEMY -> R.drawable.ic_shortcut_academy
        UCB -> R.drawable.ic_shortcut_ucb
        DIGITAL -> R.drawable.ic_shortcut_digital
        MATERIALES_REDES_SOCIALES -> R.drawable.ic_menu_link
        PROJECTION -> R.drawable.icon_calculator
        POSTULANT_EVALUATION -> R.drawable.ic_menu_link
        MANAGE_BRIGHT_PATH_CHANGE -> R.drawable.ic_shortcut_campaign_reports
        MY_PARTNERS -> R.drawable.ic_shortcut_unete
        else -> R.drawable.icon_circle
    }
}
