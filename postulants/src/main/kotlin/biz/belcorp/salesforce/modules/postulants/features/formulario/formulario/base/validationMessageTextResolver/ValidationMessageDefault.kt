package biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.base.validationMessageTextResolver

import android.content.Context
import biz.belcorp.salesforce.core.domain.entities.pais.Pais
import biz.belcorp.salesforce.modules.postulants.R
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.BuroResponse
import biz.belcorp.salesforce.modules.postulants.core.domain.enums.PaisUnete
import biz.belcorp.salesforce.modules.postulants.core.domain.enums.UneteResponse
import biz.belcorp.salesforce.modules.postulants.features.adapter.validacionmensaje.ValidacionMensajeModel

class ValidationMessageDefault {

    fun getMessages(
        context: Context,
        countryIso: String,
        responses: List<UneteResponse>,
        bloqueos: BuroResponse
    ): List<ValidacionMensajeModel> {
        val messages = mutableListOf<ValidacionMensajeModel>()

        var thereIsErrorDatosApta = false
        var thereIsErrorDatosBelcorp = false
        var thereIsErrorDatosProveedorExterno = false

        responses.forEach {
            when (it) {
                UneteResponse.ES_POSTULANTE_RECHAZADA -> {
                    thereIsErrorDatosApta = true

                    messages.add(
                        ValidacionMensajeModel(
                            title = ValidacionMensajeModel.Text(
                                value = context.getString(R.string.unete_paso1_valid_postulante_title_datos_apta),
                                color = context.getColor(R.color.black),
                                isVisible = true
                            ),
                            subtitle = ValidacionMensajeModel.Text(
                                value = getSubtitleMessageBloqueoInterno(context, false),
                                isVisible = true,
                                color = context.getColor(R.color.estado_negativo)
                            ),
                            description = ValidacionMensajeModel.Text(
                                value = bloqueos.respuestaServicio.orEmpty(),
                                isVisible = true,
                            )
                        )
                    )
                }
                UneteResponse.ES_CONSULTORA -> {
                    thereIsErrorDatosApta = true

                    messages.add(
                        ValidacionMensajeModel(
                            title = ValidacionMensajeModel.Text(
                                value = context.getString(R.string.unete_paso1_valid_postulante_title_datos_apta),
                                color = context.getColor(R.color.black),
                                isVisible = true
                            ),
                            subtitle = ValidacionMensajeModel.Text(
                                value = getSubtitleMessageBloqueoInterno(context, false),
                                isVisible = true,
                                color = context.getColor(R.color.estado_negativo)
                            ),
                            description = ValidacionMensajeModel.Text(
                                value = context.getString(R.string.unete_paso1_valid_consultora_existe)
                                    .replace("%s", ""),
                                isVisible = true,
                            )
                        )
                    )
                }
                UneteResponse.ES_POSTULANTE -> {
                    thereIsErrorDatosApta = true

                    messages.add(
                        ValidacionMensajeModel(
                            title = ValidacionMensajeModel.Text(
                                value = context.getString(R.string.unete_paso1_valid_postulante_title_datos_apta),
                                color = context.getColor(R.color.black),
                                isVisible = true
                            ),
                            subtitle = ValidacionMensajeModel.Text(
                                value = getSubtitleMessageBloqueoInterno(context, false),
                                isVisible = true,
                                color = context.getColor(R.color.estado_negativo)
                            ),
                            description = ValidacionMensajeModel.Text(
                                value = context.getString(R.string.unete_paso1_valid_consultora_postulante),
                                isVisible = true,
                            )
                        )
                    )
                }
                UneteResponse.ES_POTENCIAL -> {
                    thereIsErrorDatosApta = true

                    messages.add(
                        ValidacionMensajeModel(
                            title = ValidacionMensajeModel.Text(
                                value = context.getString(R.string.unete_paso1_valid_postulante_title_datos_apta),
                                color = context.getColor(R.color.black),
                                isVisible = true
                            ),
                            subtitle = ValidacionMensajeModel.Text(
                                value = getSubtitleMessageBloqueoInterno(context, false),
                                isVisible = true,
                                color = context.getColor(R.color.estado_negativo)
                            ),
                            description = ValidacionMensajeModel.Text(
                                value = context.getString(R.string.unete_paso1_valid_registro_proceso),
                                isVisible = true,
                            )
                        )
                    )
                }
                UneteResponse.BLOQUEOS_INTERNOS -> {
                    thereIsErrorDatosBelcorp = true

                    messages.add(
                        ValidacionMensajeModel(
                            title = ValidacionMensajeModel.Text(
                                value = context.getString(R.string.unete_paso1_valid_postulante_title_datos_belcorp),
                                color = context.getColor(R.color.black),
                                isVisible = true
                            ),
                            subtitle = ValidacionMensajeModel.Text(
                                value = getSubtitleMessageDatosBelcorp(context, false),
                                isVisible = true,
                                color = context.getColor(R.color.estado_negativo)
                            ),
                            description = ValidacionMensajeModel.Text(
                                value = getMessageBloqueosInternos(context, bloqueos, countryIso),
                                isVisible = true,
                            )
                        )
                    )
                }
                UneteResponse.BLOQUEOS_EXTERNOS -> {
                    thereIsErrorDatosProveedorExterno = true

                    messages.add(
                        ValidacionMensajeModel(
                            title = ValidacionMensajeModel.Text(
                                value = context.getString(R.string.unete_paso1_valid_postulante_title_datos_proveedor_externo),
                                color = context.getColor(R.color.black),
                                isVisible = true
                            ),
                            subtitle = ValidacionMensajeModel.Text(
                                value = getSubtitleMessageBuroExterno(context, false),
                                isVisible = true,
                                color = context.getColor(R.color.estado_negativo)
                            ),
                            description = ValidacionMensajeModel.Text(
                                value = bloqueos.respuestaServicio.orEmpty(),
                                isVisible = true,
                            )
                        )
                    )
                }
                else -> Unit
            }
        }

        if (countryIso in PaisUnete.paisesAllErrors) {

            if (!thereIsErrorDatosApta) {
                messages.add(
                    ValidacionMensajeModel(
                        title = ValidacionMensajeModel.Text(
                            value = context.getString(R.string.unete_paso1_valid_postulante_title_datos_apta),
                            color = context.getColor(R.color.black),
                            isVisible = true
                        ),
                        subtitle = ValidacionMensajeModel.Text(
                            value = getSubtitleMessageBloqueoInterno(context, true),
                            isVisible = true,
                            color = context.getColor(R.color.estado_positivo)
                        ),
                        description = ValidacionMensajeModel.Text(
                            value = bloqueos.respuestaServicio.orEmpty(),
                            isVisible = true,
                        )
                    )
                )
            }

            if (!thereIsErrorDatosBelcorp) {
                messages.add(
                    ValidacionMensajeModel(
                        title = ValidacionMensajeModel.Text(
                            value = context.getString(R.string.unete_paso1_valid_postulante_title_datos_belcorp),
                            color = context.getColor(R.color.black),
                            isVisible = true
                        ),
                        subtitle = ValidacionMensajeModel.Text(
                            value = getSubtitleMessageDatosBelcorp(context, true),
                            isVisible = true,
                            color = context.getColor(R.color.estado_positivo)
                        ),
                        description = ValidacionMensajeModel.Text(
                            value = bloqueos.respuestaServicio.orEmpty(),
                            isVisible = true,
                        )
                    )
                )
            }

            if (!thereIsErrorDatosProveedorExterno) {
                messages.add(
                    ValidacionMensajeModel(
                        title = ValidacionMensajeModel.Text(
                            value = context.getString(R.string.unete_paso1_valid_postulante_title_datos_proveedor_externo),
                            color = context.getColor(R.color.black),
                            isVisible = true
                        ),
                        subtitle = ValidacionMensajeModel.Text(
                            value = getSubtitleMessageBuroExterno(context, true),
                            isVisible = true,
                            color = context.getColor(R.color.estado_positivo)
                        ),
                        description = ValidacionMensajeModel.Text(
                            value = bloqueos.respuestaServicio.orEmpty(),
                            isVisible = true,
                        )
                    )
                )
            }
        }

        if (messages.isEmpty()) {
            messages.add(
                ValidacionMensajeModel(
                    title = ValidacionMensajeModel.Text(
                        isVisible = false
                    ),
                    subtitle = ValidacionMensajeModel.Text(
                        value = context.getString(R.string.can_be_consultant),
                        isVisible = true,
                        color = context.getColor(R.color.estado_positivo)
                    ),
                    description = ValidacionMensajeModel.Text(
                        isVisible = false,
                    )
                )
            )
        }

        return messages
    }

    private fun getSubtitleMessageDatosBelcorp(context: Context, esApta: Boolean): String {
        return when (esApta) {
            true -> context.getString(R.string.unete_valid_bloqueo_apta_default)
            false -> context.getString(R.string.unete_valid_bloqueo_no_apta_default)
        }
    }

    private fun getSubtitleMessageBuroExterno(context: Context, esApta: Boolean): String {
        return when (esApta) {
            true -> context.getString(R.string.unete_valid_bloqueo_apta_default)
            false -> context.getString(R.string.unete_valid_bloqueo_no_apta_default)
        }
    }

    private fun getSubtitleMessageBloqueoInterno(context: Context, esApta: Boolean): String {
        return when (esApta) {
            true -> context.getString(R.string.unete_valid_bloqueo_apta_default)
            false -> context.getString(R.string.unete_valid_bloqueo_no_apta_default)
        }
    }

    private fun getMessageBloqueosInternos(
        context: Context,
        bloqueos: BuroResponse,
        countryIso: String
    ): String {
        return with(context) {
            when (countryIso) {
                Pais.BOLIVIA.codigoIso -> {
                    getString(R.string.unete_bo_valid_bloqueo_description)
                }
                Pais.CHILE.codigoIso -> {
                    getString(R.string.unete_cl_valid_bloqueo_description)
                }
                Pais.COLOMBIA.codigoIso -> {
                    getString(R.string.unete_co_valid_bloqueo_description)
                }
                Pais.COSTARICA.codigoIso -> {
                    getString(R.string.unete_cr_valid_bloqueo_description)
                }
                Pais.DOMINICANA.codigoIso -> {
                    getString(R.string.unete_dm_valid_bloqueo_description)
                }
                Pais.ECUADOR.codigoIso -> {
                    getString(R.string.unete_ec_valid_bloqueo_description)
                }
                Pais.GUATEMALA.codigoIso -> {
                    getString(R.string.unete_gt_valid_bloqueo_description)
                }
                Pais.MEXICO.codigoIso -> {
                    getString(R.string.unete_mx_valid_bloqueo_description)
                }
                Pais.PANAMA.codigoIso -> {
                    getString(R.string.unete_pa_valid_bloqueo_description)
                }
                Pais.PERU.codigoIso -> {
                    val tipoBloqueo: String =
                        bloqueos.bloqueos?.firstOrNull()?.tipoBloqueo.orEmpty()
                    getString(R.string.unete_pe_valid_bloqueo_description, tipoBloqueo)
                }
                Pais.PUERTORICO.codigoIso -> {
                    getString(R.string.unete_pr_valid_bloqueo_description)
                }
                Pais.SALVADOR.codigoIso -> {
                    getString(R.string.unete_sv_valid_bloqueo_description)
                }
                else -> {
                    val tipoBloqueo: String =
                        bloqueos.bloqueos?.firstOrNull()?.tipoBloqueo.orEmpty()
                    getString(R.string.unete_pe_valid_bloqueo_description, tipoBloqueo)
                }
            }
        }
    }


}
