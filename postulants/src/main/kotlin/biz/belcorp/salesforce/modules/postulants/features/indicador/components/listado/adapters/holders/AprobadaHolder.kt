package biz.belcorp.salesforce.modules.postulants.features.indicador.components.listado.adapters.holders

import android.view.View
import androidx.core.content.ContextCompat
import biz.belcorp.salesforce.core.utils.gone
import biz.belcorp.salesforce.core.utils.visible
import biz.belcorp.salesforce.modules.postulants.R
import biz.belcorp.salesforce.modules.postulants.core.domain.enums.UneteEstado
import biz.belcorp.salesforce.modules.postulants.features.entities.BasePostulante
import biz.belcorp.salesforce.modules.postulants.features.entities.PostulanteModel
import biz.belcorp.salesforce.modules.postulants.utils.Constant
import biz.belcorp.salesforce.modules.postulants.utils.algoritmos.UneteAlgorithms
import kotlinx.android.synthetic.main.item_unete_aprobada.view.*
import kotlinx.android.synthetic.main.item_unete_aprobada.view.txtDireccion
import kotlinx.android.synthetic.main.item_unete_aprobada.view.txtFuente
import kotlinx.android.synthetic.main.item_unete_aprobada.view.txtNombres
import kotlinx.android.synthetic.main.item_unete_aprobada.view.txtNroDocumento
import kotlinx.android.synthetic.main.item_unete_aprobada.view.txtTipoPago

class AprobadaHolder(view: View) : BaseHolder(view) {

    override fun bind(model: BasePostulante, focus: Boolean) = with(itemView) {
        focusEnabled = focus
        if (model is PostulanteModel) {
            txtNombres?.text = getNombres(model)
            txtNroDocumento?.text = getDocumento(model)
            txtTelefonoCelular?.text = getTelfCelular(model)
            txtFuente?.text = getFuente(model)
            txtIngreso?.text = getIngreso(model)
            txtTipoPago?.text = getTipoPago(model)
            txtDireccion?.text = "${itemView.context.resources.getString(R.string.direccion)}: ${UneteAlgorithms.getDireccion(model.direccion.orEmpty())}"
            if (model.tipoPagoNombre.isNullOrEmpty()) txtTipoPago?.gone()

            txtCodConsultora?.visible()
            icStatus?.background =
                ContextCompat.getDrawable(itemView.context, R.drawable.circle_unete_aprobada)

            lyStatus?.visible()
            lyStatus_aprobacion_sac?.gone()

            when (model.estadoPostulante) {
                UneteEstado.GENERANDO_CODIGO.estado.toString() -> setGenerandoCodigo(model)
                UneteEstado.YA_CON_CODIGO.estado.toString(), UneteEstado.APROBACION_CAMBIO_MODELO.estado.toString() ->
                    setYaConCodigo(model)
                else -> setDefaultPostulantStatus()
            }

            focusIfNeeded {
                itemView.cv_aprobada?.setCardBackgroundColor(it)
            }
        }
    }

    private fun setDefaultPostulantStatus() = with(itemView) {
        lyStatus?.gone()
        lyStatus_aprobacion_sac?.visible()

        txtStatus?.text = getString(R.string.unete_listado_aprobacion_sac)
        txtCodConsultora?.text =
            getString(R.string.unete_listado_enaprobacion_sac)
    }

    private fun setYaConCodigo(model: PostulanteModel) = with(itemView) {
        txtStatus?.text = getString(R.string.unete_listado_ya_es_consultora)
        txtCodConsultora?.text = String.format(
            getString(R.string.unete_listado_codigo_consultora), model.codigoConsultora
        )

        if (model.ingresoAnticipado == Constant.NUMERO_UNO)
            txtStatusDescrip?.text = getString(R.string.ingresosAnticipadosM)
    }

    private fun setGenerandoCodigo(model: BasePostulante) = with(itemView) {
        txtStatus?.text = getString(R.string.unete_listado_generando_codigo)
        txtCodConsultora?.text = String.format(
            getString(R.string.unete_listado_prospecto_enviado),
            model.fechaEnvio
        )
    }

}
