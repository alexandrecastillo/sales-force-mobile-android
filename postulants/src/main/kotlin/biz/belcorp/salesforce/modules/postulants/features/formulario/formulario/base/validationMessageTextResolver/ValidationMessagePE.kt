package biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.base.validationMessageTextResolver

import android.content.Context
import biz.belcorp.salesforce.modules.postulants.R
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.BuroResponse
import biz.belcorp.salesforce.modules.postulants.core.domain.enums.UneteEstadoCrediticio
import biz.belcorp.salesforce.modules.postulants.core.domain.enums.UneteResponse
import biz.belcorp.salesforce.modules.postulants.features.adapter.validacionmensaje.ValidacionMensajeModel
import biz.belcorp.salesforce.modules.postulants.utils.Constant

class ValidationMessagePE {

    var respuestaGlobal = -1

    fun getMessages(
        context: Context,
        responses: List<UneteResponse>,
        bloqueos: BuroResponse
    ): List<ValidacionMensajeModel> {
        val messages = mutableListOf<ValidacionMensajeModel>()

        messageApta(context, responses, bloqueos)?.also {
            messages.add(it)
        }

        messageBuroExterno(context, responses, bloqueos)?.also {
            messages.add(it)
        }

        messageDatosBelcorp(context, responses, bloqueos).also {
            messages.add(it)
        }

        return messages
    }

    // Datos apta
    private fun messageApta(
        context: Context,
        responses: List<UneteResponse>,
        buroResponse: BuroResponse
    ): ValidacionMensajeModel? {

        if (tieneUnProcesoActivo(responses)) {
            respuestaGlobal = 1

            return ValidacionMensajeModel(
                title = ValidacionMensajeModel.Text(
                    value = context.getString(R.string.unete_valid_bloqueo_title_already_process_pe),
                    color = context.getColor(R.color.estado_negativo),
                    isVisible = true
                ),
                subtitle = ValidacionMensajeModel.Text(
                    isVisible = false,
                ),
                description = ValidacionMensajeModel.Text(
                    isVisible = false,
                )
            )
        }

        if (yaEsConsultora(responses)) {
            respuestaGlobal = 2

            return ValidacionMensajeModel(
                title = ValidacionMensajeModel.Text(
                    value = context.getString(R.string.unete_valid_bloqueo_title_already_consultant_pe),
                    color = context.getColor(R.color.estado_negativo),
                    isVisible = true
                ),
                subtitle = ValidacionMensajeModel.Text(
                    isVisible = false,
                ),
                description = ValidacionMensajeModel.Text(
                    isVisible = false,
                )
            )
        }

        if (puedeSerConsultoraConCredito(responses)) {
            return when (UneteEstadoCrediticio.find(buroResponse.enumEstadoCrediticioID)) {
                UneteEstadoCrediticio.SIN_CONSULTAR -> {
                    respuestaGlobal = 6

                    ValidacionMensajeModel(
                        title = ValidacionMensajeModel.Text(
                            value = context.getString(R.string.unete_valid_bloqueo_title_can_be_consultant_credit_to_define_pe),
                            color = context.getColor(R.color.estado_positivo),
                            isVisible = true
                        ),
                        subtitle = ValidacionMensajeModel.Text(
                            isVisible = false,
                        ),
                        description = ValidacionMensajeModel.Text(
                            isVisible = false,
                        )
                    )
                }
                else -> {
                    respuestaGlobal = 3

                    ValidacionMensajeModel(
                        title = ValidacionMensajeModel.Text(
                            value = context.getString(R.string.unete_valid_bloqueo_title_can_be_consultant_w_credit_pe),
                            color = context.getColor(R.color.estado_positivo),
                            isVisible = true
                        ),
                        subtitle = ValidacionMensajeModel.Text(
                            isVisible = false,
                        ),
                        description = ValidacionMensajeModel.Text(
                            isVisible = false,
                        )
                    )
                }
            }
        }

        if (puedeSerConsultoraSinCredito(responses)) {
            respuestaGlobal = 4

            return ValidacionMensajeModel(
                title = ValidacionMensajeModel.Text(
                    value = context.getString(R.string.unete_valid_bloqueo_title_can_be_consultant_w_o_credit_pe),
                    color = context.getColor(R.color.estado_positivo),
                    isVisible = true
                ),
                subtitle = ValidacionMensajeModel.Text(
                    isVisible = false,
                ),
                description = ValidacionMensajeModel.Text(
                    isVisible = false,
                )
            )
        }

        if (noPuedeSerConsultoraSeEncuentraBloqueada(responses)) {
            respuestaGlobal = 5

            return ValidacionMensajeModel(
                title = ValidacionMensajeModel.Text(
                    value = context.getString(R.string.unete_valid_bloqueo_title_can_not_be_consultant_she_is_blocked_pe),
                    color = context.getColor(R.color.estado_negativo),
                    isVisible = true
                ),
                subtitle = ValidacionMensajeModel.Text(
                    isVisible = false,
                ),
                description = ValidacionMensajeModel.Text(
                    isVisible = false,
                )
            )
        }

        return null
    }

    private fun tieneUnProcesoActivo(responses: List<UneteResponse>): Boolean {
        return responses.any {
            it in arrayOf(
                UneteResponse.ES_POSTULANTE,
                UneteResponse.ES_POTENCIAL
            )
        }
    }

    private fun yaEsConsultora(responses: List<UneteResponse>): Boolean {
        return UneteResponse.ES_CONSULTORA in responses
    }

    private fun puedeSerConsultoraConCredito(responses: List<UneteResponse>): Boolean {
        val tieneCredito = UneteResponse.BLOQUEOS_EXTERNOS !in responses
        val noTieneBloqueosYDeudas = UneteResponse.BLOQUEOS_INTERNOS !in responses

        return tieneCredito && noTieneBloqueosYDeudas
    }

    private fun puedeSerConsultoraSinCredito(responses: List<UneteResponse>): Boolean {
        val noTieneCredito = UneteResponse.BLOQUEOS_EXTERNOS in responses
        val noTieneBloqueosYDeudas = UneteResponse.BLOQUEOS_INTERNOS !in responses

        return noTieneCredito && noTieneBloqueosYDeudas
    }

    private fun noPuedeSerConsultoraSeEncuentraBloqueada(responses: List<UneteResponse>): Boolean {
        return UneteResponse.BLOQUEOS_INTERNOS in responses
    }

    // Datos buro externo
    private fun messageBuroExterno(
        context: Context,
        responses: List<UneteResponse>,
        buroResponse: BuroResponse
    ): ValidacionMensajeModel? {
        var withMessage = false

        return when (respuestaGlobal) {
            6 -> {
                ValidacionMensajeModel(
                    title = ValidacionMensajeModel.Text(
                        value = context.getString(R.string.unete_valid_bloqueo_no_se_pudo_realizar_consulta_externa_pe),
                        isVisible = true,
                        color = context.getColor(R.color.black)
                    ),
                    subtitle = ValidacionMensajeModel.Text(
                        isVisible = false,
                    ),
                    description = ValidacionMensajeModel.Text(
                        isVisible = false
                    )
                )
            }
            in arrayOf(2, 5) -> {
                val title = when (tieneBloqueoExterno(responses)) {
                    true -> {
                        withMessage = true
                        context.getString(R.string.unete_valid_bloqueo_subtitle_tiene_deuda_externa_pe)
                    }
                    false -> context.getString(R.string.unete_valid_bloqueo_subtitle_no_tiene_deuda_externa_pe)
                }

                ValidacionMensajeModel(
                    title = ValidacionMensajeModel.Text(
                        value = title,
                        isVisible = true,
                        color = context.getColor(R.color.black)
                    ),
                    subtitle = ValidacionMensajeModel.Text(
                        value = buroResponse.respuestaServicio,
                        isVisible = withMessage,
                        color = context.getColor(R.color.black)
                    ),
                    description = ValidacionMensajeModel.Text(
                        isVisible = false
                    )
                )
            }
            in arrayOf(1, 3, 4) -> {
                val title = when (tieneBloqueoExterno(responses)) {
                    true -> {
                        withMessage = true
                        context.getString(R.string.unete_valid_bloqueo_subtitle_no_califica_credito_pe)
                    }
                    false -> context.getString(R.string.unete_valid_bloqueo_subtitle_califica_credito_pe)
                }

                ValidacionMensajeModel(
                    title = ValidacionMensajeModel.Text(
                        value = title,
                        color = context.getColor(R.color.black),
                        isVisible = true
                    ),
                    subtitle = ValidacionMensajeModel.Text(
                        value = buroResponse.respuestaServicio,
                        isVisible = withMessage,
                        color = context.getColor(R.color.black)
                    ),
                    description = ValidacionMensajeModel.Text(
                        isVisible = false
                    )
                )
            }
            else -> null
        }
    }

    private fun tieneBloqueoExterno(responses: List<UneteResponse>): Boolean {
        return UneteResponse.BLOQUEOS_EXTERNOS in responses
    }

    // Datos belcorp
    private fun messageDatosBelcorp(
        context: Context,
        responses: List<UneteResponse>,
        buroResponse: BuroResponse
    ): ValidacionMensajeModel {
        if (tieneBloqueoInterno(responses)) {
            return ValidacionMensajeModel(
                title = ValidacionMensajeModel.Text(
                    value = context.getString(R.string.unete_valid_bloqueo_subtitle_presenta_bloqueo_pe),
                    isVisible = true,
                    color = context.getColor(R.color.estado_negativo)
                ),
                subtitle = ValidacionMensajeModel.Text(
                    value = buroResponse.respuestaServicio,
                    isVisible = true
                ),
                description = ValidacionMensajeModel.Text(
                    isVisible = false
                )
            )
        }

        return ValidacionMensajeModel(
            title = ValidacionMensajeModel.Text(
                value = context.getString(R.string.unete_valid_bloqueo_subtitle_no_presenta_bloqueo_pe),
                isVisible = true,
                color = context.getColor(R.color.estado_positivo)
            ),
            subtitle = ValidacionMensajeModel.Text(
                isVisible = false,
            ),
            description = ValidacionMensajeModel.Text(
                isVisible = false
            )
        )
    }

    private fun tieneBloqueoInterno(responses: List<UneteResponse>): Boolean {
        return UneteResponse.BLOQUEOS_INTERNOS in responses
    }

}
