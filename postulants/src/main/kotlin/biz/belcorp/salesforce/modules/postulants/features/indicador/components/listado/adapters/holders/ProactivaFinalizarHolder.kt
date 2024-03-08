package biz.belcorp.salesforce.modules.postulants.features.indicador.components.listado.adapters.holders

import android.content.Intent
import android.net.Uri
import android.view.View
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.domain.entities.configuration.Configuration
import biz.belcorp.salesforce.core.domain.entities.session.Sesion
import biz.belcorp.salesforce.core.utils.*
import biz.belcorp.salesforce.modules.postulants.R
import biz.belcorp.salesforce.modules.postulants.features.entities.BasePostulante
import biz.belcorp.salesforce.modules.postulants.features.entities.PostulanteModel
import biz.belcorp.salesforce.modules.postulants.features.indicador.components.listado.adapters.PostulantAdapter
import kotlinx.android.synthetic.main.item_unete_proactiva_finalizar.view.*

class ProactivaFinalizarHolder(
    val view: View,
    private val user: Sesion,
    private val configuration: Configuration
) : BaseHolder(view) {

    var listener: PostulantAdapter.EvaluacionAdapterListener? = null

    override fun bind(model: BasePostulante, focus: Boolean) = with(itemView) {
        focusEnabled = focus
        if (model is PostulanteModel) {
            txtNombres?.text = getNombres(model)
            txtNroDocumento?.text = getDocumento(model)
            txtTelefonoCelular?.text = getTelfCelular(model)
            txtFuente?.text = getFuente(model)
            txtIngreso?.text = getIngreso(model)
            txtTipoPago?.text = getTipoPago(model)
            txtCorreElectronico?.text = getCorreoElectronico(model)

            val isNewExperience = model.link.isNotEmpty()

            if (isNewExperience) {

                txtLink?.text = getLink(model)

                btnWsp?.visibility = View.VISIBLE
                btnWsp?.setOnClickListener {
                    itemView.context.sendAWhatsappAPI(
                        numberFormatted(configuration.phoneCode, model.celular.orEmpty()),
                        getMessageLink(model, user)
                    )
                }
            } else {
                txtLink.setText(R.string.unete_proactiva_finalizar_message)
                btnWsp?.visibility = View.GONE
            }

            txtDireccion?.text =
                "${itemView.context.resources.getString(R.string.direccion)}: ${model.direccion}"
            if (model.tipoPagoNombre.isNullOrEmpty()) txtTipoPago?.gone()

            focusIfNeeded {
                itemView.cv_aprobada?.setCardBackgroundColor(it)
            }

            btnNoInteresada?.setOnClickListener {
                listener?.postulanteNoIntersada(model)
            }

            if (model.telefono.isNullOrEmpty()) {
                txtTelefono?.visibility = View.GONE
            } else {
                txtTelefono?.visibility = View.VISIBLE
                txtTelefono?.text = getTelefono(model)

                txtTelefono.setOnClickListener {
                    val callIntent: Intent = Uri.parse("tel:${model.telefono}").let { number ->
                        Intent(Intent.ACTION_DIAL, number)
                    }

                    itemView.context.startActivity(Intent.createChooser(callIntent, "Abrir con"))
                }
            }

            if (model.celular.isNullOrEmpty()) {
                txtTelefonoCelular?.visibility = View.GONE
            } else {
                txtTelefonoCelular?.visibility = View.VISIBLE
                txtTelefonoCelular?.text = getTelfCelular(model)

                txtTelefonoCelular.setOnClickListener {
                    val callIntent: Intent = Uri.parse("tel:${model.celular}").let { number ->
                        Intent(Intent.ACTION_DIAL, number)
                    }

                    itemView.context.startActivity(Intent.createChooser(callIntent, "Abrir con"))
                }
            }
        }
    }

    private fun numberFormatted(phoneCode: String, number: String): String {
        val phoneCodeWithoutPlus = phoneCode.replace(Constant.PLUS, Constant.EMPTY_STRING)
        val numberPhoneWithoutPlus = number.replace(Constant.PLUS, Constant.EMPTY_STRING)

        return when {
            numberPhoneWithoutPlus.startsWith(phoneCodeWithoutPlus) -> number
            else -> phoneCode.plus(number)
        }.replace(Constant.PLUS, Constant.EMPTY_STRING)
    }

}
