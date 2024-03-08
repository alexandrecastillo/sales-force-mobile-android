package biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.base.validationMessageTextResolver

import android.content.Context
import biz.belcorp.salesforce.modules.postulants.R
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.BuroResponse
import biz.belcorp.salesforce.modules.postulants.core.domain.enums.UneteResponse
import biz.belcorp.salesforce.modules.postulants.features.adapter.validacionmensaje.ValidacionMensajeModel

class ValidationMessageCL {

    fun getMessages(
        context: Context,
        responses: List<UneteResponse>,
        bloqueos: BuroResponse
    ): List<ValidacionMensajeModel> {
        val messages = mutableListOf<ValidacionMensajeModel>()

        var withMessage = false

        if (seEncuentraBloqueadaPorBelcorp(responses)) {
            val subtitle = when (calificaATenerCredito(responses)) {
                true -> context.getString(R.string.unete_valid_bloqueo_subtitle_postulante_califica_credito_cl)
                false -> {
                    withMessage = true
                    context.getString(R.string.unete_valid_bloqueo_subtitle_postulante_no_califica_credito_cl)
                }
            }

            messages.add(
                ValidacionMensajeModel(
                    title = ValidacionMensajeModel.Text(
                        value = context.getString(R.string.unete_valid_bloqueo_title_postulante_encuentra_bloqueada_cl),
                        color = context.getColor(R.color.black),
                        isVisible = true
                    ),
                    subtitle = ValidacionMensajeModel.Text(
                        value = subtitle,
                        isVisible = true,
                        color = context.getColor(R.color.black)
                    ),
                    description = ValidacionMensajeModel.Text(
                        value = bloqueos.respuestaServicio,
                        isVisible = withMessage
                    )
                )
            )

            return messages
        }

        if (tieneUnProcesoActivoOEsConsultora(responses)) {
            val subtitle = when (calificaATenerCredito(responses)) {
                true -> context.getString(R.string.unete_valid_bloqueo_subtitle_postulante_califica_credito_cl)
                false -> {
                    withMessage = true
                    context.getString(R.string.unete_valid_bloqueo_subtitle_postulante_no_califica_credito_cl)
                }
            }

            messages.add(
                ValidacionMensajeModel(
                    title = ValidacionMensajeModel.Text(
                        value = context.getString(R.string.unete_valid_bloqueo_title_postulante_proceso_activo_cl),
                        color = context.getColor(R.color.black),
                        isVisible = true
                    ),
                    subtitle = ValidacionMensajeModel.Text(
                        value = subtitle,
                        isVisible = true,
                        color = context.getColor(R.color.black)
                    ),
                    description = ValidacionMensajeModel.Text(
                        value = bloqueos.respuestaServicio,
                        isVisible = withMessage
                    )
                )
            )

            return messages
        }

        val title = when (calificaATenerCredito(responses)) {
            true -> context.getString(R.string.unete_valid_bloqueo_subtitle_postulante_califica_credito_no_bloqueada_cl)
            false -> {
                withMessage = true
                context.getString(R.string.unete_valid_bloqueo_subtitle_postulante_no_califica_credito_no_bloqueada_cl)
            }
        }

        messages.add(
            ValidacionMensajeModel(
                title = ValidacionMensajeModel.Text(
                    value = title,
                    color = context.getColor(R.color.black),
                    isVisible = true
                ),
                subtitle = ValidacionMensajeModel.Text(
                    value = bloqueos.respuestaServicio,
                    isVisible = withMessage,
                    color = context.getColor(R.color.black)
                ),
                description = ValidacionMensajeModel.Text(
                    isVisible = false
                )
            )
        )

        return messages
    }

    private fun seEncuentraBloqueadaPorBelcorp(responses: List<UneteResponse>): Boolean {
        return UneteResponse.BLOQUEOS_INTERNOS in responses
    }

    private fun calificaATenerCredito(responses: List<UneteResponse>): Boolean {
        return UneteResponse.BLOQUEOS_EXTERNOS !in responses
    }

    private fun tieneUnProcesoActivoOEsConsultora(responses: List<UneteResponse>): Boolean {
        return responses.any {
            it in arrayOf(
                UneteResponse.ES_CONSULTORA,
                UneteResponse.ES_POSTULANTE,
                UneteResponse.ES_POTENCIAL
            )
        }
    }

}
