package biz.belcorp.salesforce.base.features.deeplinks

import android.net.Uri
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import biz.belcorp.salesforce.base.R
import biz.belcorp.salesforce.core.utils.AppUri.ACTION_KEY
import biz.belcorp.salesforce.core.utils.AppUri.CODE_KEY


class DeepLinkHandler(private val navController: NavController) {

    companion object {

        private const val NEWS_ACTION_VALUE = "GestorContenidosPush"
        private const val RDD_ACTION_VALUE = "Rutas de Desarrollo"
        private const val MATERIAL_ACTION_VALUE = "Materiales de Desarrollo"
        private const val RDD_RECOGNITION_ACTION_VALUE = "RDD"
        private const val RDD_EVENT_ACTION_VALUE = "RDD_EVENTOS"
        private const val POSTULANTS_ACTION_VALUE = "UNETE"

        private const val RDD_RECOGNITION_ID_KEY = "RECOGNITION_ID_KEY"

    }

    fun manage(uri: Uri?) {
        val resId = getResId(uri) ?: return
        val args = getArgs(uri) ?: return
        navController.navigate(resId, args)
    }

    private fun getResId(uri: Uri?) = when (uri?.actionParam) {
        NEWS_ACTION_VALUE -> R.id.globalToNewsFragment
        MATERIAL_ACTION_VALUE -> R.id.globalToDevelopmentMaterialFragment
        POSTULANTS_ACTION_VALUE -> R.id.globalToPostulants
        RDD_RECOGNITION_ACTION_VALUE -> R.id.globalToRecognitionFragment
        RDD_ACTION_VALUE,
        RDD_EVENT_ACTION_VALUE -> R.id.globalToDevelopmentPathFragment
        else -> null
    }

    private fun getArgs(uri: Uri?) = when (uri?.actionParam) {
        NEWS_ACTION_VALUE,
        MATERIAL_ACTION_VALUE,
        POSTULANTS_ACTION_VALUE,
        RDD_ACTION_VALUE,
        RDD_EVENT_ACTION_VALUE -> {
            bundleOf(
                ACTION_KEY to uri.actionParam,
                CODE_KEY to uri.codeParam
            )
        }
        RDD_RECOGNITION_ACTION_VALUE -> {
            bundleOf(
                ACTION_KEY to uri.actionParam,
                RDD_RECOGNITION_ID_KEY to uri.codeParam?.toLongOrNull()
            )
        }
        else -> null
    }

    private val Uri.actionParam get() = getQueryParameter(ACTION_KEY)
    private val Uri.codeParam get() = getQueryParameter(CODE_KEY)

}
