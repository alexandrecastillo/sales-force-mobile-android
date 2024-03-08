package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.view

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import biz.belcorp.salesforce.base.utils.navigateTo
import biz.belcorp.salesforce.core.constants.CONSULTANT_ID
import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.features.permissions.PermissionsUtil
import biz.belcorp.salesforce.core.utils.isCallPhonePermissionGranted
import biz.belcorp.salesforce.core.utils.showSnackBar
import biz.belcorp.salesforce.modules.developmentpath.common.broadcast.SenderIrAPerfil
import biz.belcorp.salesforce.modules.developmentpath.common.broadcast.SenderIrAUbicacion
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.adicionar.AdicionarVisitaFragment
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eliminar.EliminarDePlanFragment
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.model.MenuPersonaModel
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.model.card.MiRutaCardModel
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.planificar.PlanificarFragment
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.replanificar.ReprogramarFragment
import biz.belcorp.salesforce.modules.developmentpath.utils.toUri
import biz.belcorp.salesforce.base.R as BaseR
import biz.belcorp.salesforce.core.utils.toast
import java.util.*

class EjecutorAccionesSobreVisita(val fragment: Fragment) : AlClikearPersonaListener,
    AccionesListener {

    private val childFragmentManager get() = fragment.childFragmentManager

    private val URI_WHATSAPP = "com.whatsapp"
    private val URI_WHATSAPP_BUSINESS = "com.whatsapp.w4b"

    override fun alClickearMenu(card: MiRutaCardModel) {
        abrirMenu(card)
    }

    private fun abrirMenu(card: MiRutaCardModel) {
        val menuPersonaFragment = MenuPersonaFragment()

        menuPersonaFragment.model = card.datosMenu
        menuPersonaFragment.listener = this

        menuPersonaFragment.show(
            childFragmentManager,
            menuPersonaFragment.tag
        )
    }

    override fun alClickearPlanificar(card: MiRutaCardModel) {
        PlanificarFragment
            .newInstance(card.datosVisita.id)
            .show(childFragmentManager, "Planificar")
    }

    override fun alClickearGeorefencia(card: MiRutaCardModel) {
        fragment.navigateTo(
            BaseR.id.globalToMapFeature,
            bundleOf(CONSULTANT_ID to card.datosBasicos.personaId.toInt())
        )
    }

    override fun alClickearCard(card: MiRutaCardModel) {
        val personIdentifier = PersonIdentifier(
            card.datosBasicos.personaId,
            card.datosBasicos.personCode,
            card.datosRol.rol
        )
        verPerfil(personIdentifier)
    }

    override fun alPresionarEliminacion(visitaId: Long, fechaAnterior: Date, nombre: String) {
        EliminarDePlanFragment
            .newInstance(visitaId, fechaAnterior, nombre)
            .show(childFragmentManager, "Eliminar")
    }

    override fun alPresionarRegistro(personIdentifier: PersonIdentifier) {
        verPerfil(personIdentifier)
    }

    override fun alPresionarReplanificacion(visitaId: Long, fechaAnterior: Date) {
        ReprogramarFragment
            .newInstance(visitaId, fechaAnterior)
            .show(childFragmentManager, "Reprogramar")
    }

    override fun alPresionarAdicionarVisita(personaId: Long, rol: Rol) {
        AdicionarVisitaFragment
            .newInstance(personaId, rol)
            .show(childFragmentManager, "Adicionar")
    }

    override fun alLlamar(numero: String) {
        if (checkPermision()) {
            llamarANumero(numero)
        }
    }

    private fun llamarANumero(numero: String) {
        val callIntent = Intent(Intent.ACTION_CALL)
        callIntent.data = Uri.parse("tel:$numero")
        fragment.startActivity(callIntent)
    }

    private fun checkPermision(): Boolean {
        return if (!fragment.requireActivity().isCallPhonePermissionGranted()) {
            fragment.requestPermissions(
                arrayOf(Manifest.permission.CALL_PHONE),
                PermissionsUtil.REQUEST_CALL_ID
            )
            false
        } else {
            true
        }
    }

    override fun alEnviarSMS(numero: String) {
        enviarMensaje("SMS", numero)
    }

    override fun alEnviarWhatsApp(numero: String) {
        enviarMensaje("WhatsApp", numero)
    }

    override fun alEviarEmail(email: String) {
        val subject = ""
        val body = ""
        val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + email))
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
        emailIntent.putExtra(Intent.EXTRA_EMAIL, email)
        emailIntent.putExtra(Intent.EXTRA_TEXT, body)
        try {
            fragment.startActivity(Intent.createChooser(emailIntent, "Selecionar Aplicación"))
        } catch (exception: ActivityNotFoundException) {
            fragment.requireContext().toast("No se ha encontrado email app en el teléfono.")
        }
    }

    private fun enviarMensaje(tipo: String, numero: String) {
        val intent = Intent(Intent.ACTION_SENDTO)
        when (tipo) {
            "SMS" -> {
                intent.addCategory(Intent.CATEGORY_DEFAULT)
                intent.type = "vnd.android-dir/mms-sms"
                intent.data = "sms:$numero".toUri()
            }
            "WhatsApp" -> {
                intent.data = "smsto:$numero".toUri()
                intent.putExtra("sms_body", "")
                intent.`package` = obtainPackageWhatsapp()
            }
        }
        try {
            fragment.startActivity(intent)
        } catch (exception: ActivityNotFoundException) {
            fragment.requireContext()
                .showSnackBar(
                    "No se ha encontrado $tipo en el teléfono.", fragment.view
                        ?: return
                )
        }
    }

    private fun obtainPackageWhatsapp() : String = if (appInstalledOrNot(URI_WHATSAPP)) {
            URI_WHATSAPP
        } else {
            URI_WHATSAPP_BUSINESS
        }

    override fun alPresionarUbicacion(modelo: MenuPersonaModel) {
        SenderIrAUbicacion(fragment.activity ?: return).irAUbicacion(modelo)
    }

    private fun verPerfil(personIdentifier: PersonIdentifier) {
        SenderIrAPerfil(fragment.activity ?: return).irAPerfil(personIdentifier)
    }

    private fun appInstalledOrNot(uri: String): Boolean {
        val pm = fragment.requireContext().packageManager
        return try {
            pm.getPackageInfo(uri, PackageManager.GET_META_DATA)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }
}
