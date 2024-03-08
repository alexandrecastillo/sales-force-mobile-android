package biz.belcorp.salesforce.modules.postulants.features.indicador.components.listado.adapters.holders

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.graphics.Color
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.core.domain.entities.session.Sesion
import biz.belcorp.salesforce.modules.postulants.R
import biz.belcorp.salesforce.modules.postulants.features.entities.BasePostulante
import biz.belcorp.salesforce.modules.postulants.features.entities.PostulanteModel
import biz.belcorp.salesforce.modules.postulants.utils.*
import java.text.ParseException

abstract class BaseHolder(view: View) : RecyclerView.ViewHolder(view) {

    abstract fun bind(model: BasePostulante, focus: Boolean)
    var focusEnabled: Boolean = false

    fun getNombres(model: BasePostulante): String {
        return getResources().getString(
            R.string.two_spaces,
            model.primerNombre,
            model.apellidoPaterno
        )
    }

    fun getSeccion(model: BasePostulante): String {
        val label = getResources().getString(R.string.unete_listado_seccion)
        return String.format(label, model.codigoSeccion?.takeIf { it.isNotEmpty() }
            ?: model.codigoSeccion?.takeIf { it.isNotEmpty() } ?: Constant.HYPHEN)
    }

    fun getFuente(model: BasePostulante): String {
        val label = getResources().getString(R.string.unete_listado_fuente)
        return String.format(label, model.fuenteIngreso.orEmpty())
    }

    fun getDocumento(model: BasePostulante): String {
        val label = getResources().getString(R.string.unete_listado_documento)
        return String.format(label, model.numeroDocumento)
    }

    fun getTelfCelular(model: BasePostulante): String {
        val label = getResources().getString(R.string.unete_listado_telefono_celular)
        return String.format(
            label, if (model.celular.isNullOrEmpty()) Constant.HYPHEN else model.celular
        )
    }

    fun getIngreso(model: PostulanteModel): String {
        val label = getResources().getString(R.string.unete_listado_ingreso)
        return String.format(label, model.campaniaDeIngreso)
    }

    fun getTipoPago(model: PostulanteModel): String {
        val label = getResources().getString(R.string.unete_listado_tipo_pago)
        return String.format(label, model.tipoPagoNombre)
    }

    fun getCorreoElectronico(model: PostulanteModel): String {
        val label = getResources().getString(R.string.unete_listado_correo_electronico)
        return String.format(label, model.correoElectronico)
    }

    fun getTelefono(model: PostulanteModel): String {
        val label = getResources().getString(R.string.unete_listado_telefono)
        return String.format(label, model.telefono)
    }

    fun getLink(model: PostulanteModel): String {
        val label = getResources().getString(R.string.unete_proactiva_finalizar_message_v2)
        return String.format(label, model.link)
    }

    fun getMessageLink(model: PostulanteModel, user: Sesion): String {
        return itemView.context.getString(
            R.string.unete_proactiva_finalizar_message_wsp_v2,
            model.primerNombre.orEmpty(),
            user.person.firstName,
            user.rol.comoTexto(),
            model.link
        )
    }

    private fun getResources() = itemView.context.resources

    fun getCondiciones() = getResources().getString(R.string.unete_condiciones)

    fun getValidacionGZ(model: PostulanteModel): String {

        return if (model.fechaEnvioValidarPorGZ.isNullOrEmpty()) {
            getResources().getString(R.string.textoValidacionGZSinFecha)
        } else {

            try {
                val label = getResources().getString(R.string.textoValidacionGZ)
                val data = model.fechaEnvioValidarPorGZ.orEmpty()
                val convert = Constant.EMPTY_STRING
                val fecha = convert.formatPubDate(data)
                String.format(label, fecha)
            } catch (data: ParseException) {
                getResources().getString(R.string.textoValidacionGZSinFecha)
            } catch (e: Exception) {
                Constant.EMPTY_STRING
            }
        }
    }

    fun getMotivoRechazo(model: PostulanteModel): String {
        val label = getResources().getString(R.string.unete_listado_rechazada_motivo)
        return String.format(label, model.motivoRechazo)
    }

    fun getDiasEspera(model: PostulanteModel): String {
        val label = getResources().getString(R.string.unete_listado_dias_espera)
        return if (model.fechaCreacion.isNullOrEmpty()) {
            String.format(label, Constant.NUMERO_CERO.toString())
        } else {
            val date = model.fechaCreacion?.toDate(formatLongT)
            val dias = date?.daysBeforeNow() ?: Constant.NUMERO_CERO
            String.format(label, dias.toString())
        }
    }

    fun getString(id: Int) = getResources().getString(id)

    fun focusIfNeeded(f: (Int) -> Unit) {
        if (focusEnabled) {
            val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), Color.LTGRAY, Color.WHITE)
            colorAnimation.duration = 4000
            colorAnimation.addUpdateListener { animator ->
                f(animator.animatedValue as Int)
            }
            colorAnimation.start()
            focusEnabled = false
        }
    }

}
