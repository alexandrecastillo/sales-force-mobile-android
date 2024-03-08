package biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.base

import android.content.Context
import biz.belcorp.salesforce.core.base.BaseTextResolver
import biz.belcorp.salesforce.core.domain.entities.pais.Pais
import biz.belcorp.salesforce.modules.postulants.R
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.BuroResponse
import biz.belcorp.salesforce.modules.postulants.core.domain.enums.UneteResponse
import biz.belcorp.salesforce.modules.postulants.features.adapter.validacionmensaje.ValidacionMensajeModel
import biz.belcorp.salesforce.modules.postulants.utils.Constant

class PostulantTextResolver(private val context: Context) : BaseTextResolver() {

    fun getErrorMessage(error: UneteResponse): Pair<String, String> {
        return when(error){
            UneteResponse.ERROR_GENERIC  -> Pair(
                context.getString(R.string.unete_valid_documento_title),
                context.getString(R.string.unete_valid_documento_description)
            )
            UneteResponse.ERROR_SERVICE -> Pair(
                context.getString(R.string.unete_paso1_valid_postulante_title),
                String.format(context.getString(R.string.unete_paso1_valid_postulante_description))
            )
            else -> Pair(Constant.EMPTY_STRING, Constant.EMPTY_STRING)
        }
    }

    fun getMessageBloqueExterno(countryIso:String):String{
        return when (countryIso) {
            Pais.ECUADOR.codigoIso -> {
                context.getString(R.string.unete_pe_paso1_ec_valid_bloqueo_descripcion_crediticia)
            }
            else -> {
                context.getString(R.string.unete_pe_paso1_valid_bloqueo_descripcion_crediticia)
            }
        }
    }

    fun getPostulantTitle(): String {
        return context.getString(R.string.unete_paso1_valid_postulante_title)
    }

    fun getValidPostulantTitle(): String{
        return context.getString(R.string.unete_paso1_valid_consultora_title)
    }

    fun getBuroMessage(countryIso: String,response: UneteResponse, bloqueos: BuroResponse): Pair<String, String> {
        return when (response) {
            UneteResponse.ES_POSTULANTE_RECHAZADA -> Pair(
                context.getString(R.string.unete_paso1_valid_postulante_title),
                bloqueos.mensajeError
            )
            UneteResponse.ES_CONSULTORA -> Pair(
                context.getString(R.string.unete_paso1_valid_consultora_title), String.format(
                    context.getString(
                        R.string.unete_paso1_valid_consultora_existe
                    ), bloqueos.nombreCompleto
                )
            )
            UneteResponse.ES_POSTULANTE -> Pair(
                context.getString(R.string.unete_paso1_valid_consultora_title), context.getString(
                    R.string.unete_paso1_valid_consultora_postulante
                )
            )
            UneteResponse.ES_POTENCIAL -> Pair(
                context.getString(R.string.unete_paso1_valid_consultora_title), context.getString(
                    R.string.unete_paso1_valid_registro_proceso
                )
            )
            UneteResponse.BLOQUEOS_INTERNOS -> Pair(
                context.getString(R.string.unete_valid_bloqueo_title),
                getMessageBloqueosInternos(bloqueos, countryIso)
            )
            else -> Pair(Constant.EMPTY_STRING, Constant.EMPTY_STRING)
        }


    }

    fun getMessageBloqueosInternos(bloqueos: BuroResponse, countryIso: String): String {
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

    fun getSubtitleMessageDatosBelcorp(esApta: Boolean): String {
        with(context) {
            return when (esApta) {
                true -> getString(R.string.unete_valid_bloqueo_apta_default)
                false -> getString(R.string.unete_valid_bloqueo_no_apta_default)
            }
        }
    }


}
