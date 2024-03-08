package biz.belcorp.salesforce.modules.developmentpath.features.profile.information.mapper

import biz.belcorp.salesforce.core.utils.formatearConComas
import biz.belcorp.salesforce.core.utils.toDescriptionDay
import biz.belcorp.salesforce.core.utils.toDescriptionDayYear
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.profile.ProfileInfo
import biz.belcorp.salesforce.modules.developmentpath.widgets.etiqueta.model.ContenedorInfoBasica
import biz.belcorp.salesforce.modules.developmentpath.widgets.etiqueta.model.Etiqueta
import biz.belcorp.salesforce.modules.developmentpath.widgets.etiqueta.model.Fila
import biz.belcorp.salesforce.modules.developmentpath.widgets.etiqueta.model.Grupo

class DatosConsultoraMapper(
    private val datosConsultora: ProfileInfo.DatoPersonaCo
) : DatosPersonaMapper() {

    override fun map(): ContenedorInfoBasica {
        val filaCodigoYSaldo = crearFilaCodigoYSaldo()
        val filaIngresoYUltimaFacturacion = crearFilaIngresoYUltimaFacturacion()
        val filaDniYReingresos = crearFilaDniYReingresos()
        val filaTelefonoYAniversario = crearFilaTelefonoYAniversario()
        val filaAutorizadaYPayment = crearFilaAutorizadaYPayment()
        val filaCorreo = crearFilaCorreo()
        val filaDireccion = crearFilaDireccion()
        val rowReference = createRowReference()
//        val filaMultiMarca = createRowMultiBrand()

        return ContenedorInfoBasica(
            listOf(
                filaCodigoYSaldo,
                filaIngresoYUltimaFacturacion,
                filaDniYReingresos,
                filaAutorizadaYPayment,
                filaTelefonoYAniversario,
                filaCorreo,
                filaDireccion,
                rowReference/*,
                filaMultiMarca*/
            )
        )
    }

    private fun crearFilaCodigoYSaldo(): Fila {
        val grupoCodigo = crearGrupoCodigo()
        val grupoSaldoPendiente = crearGrupoSaldoPendiente()
        return Fila(listOf(grupoCodigo, grupoSaldoPendiente))
    }

    private fun crearGrupoCodigo(): Grupo {
        val tituloCodigo = Etiqueta(
            tipo = Etiqueta.Tipo.LIGERO,
            idRecurso = R.string.perfil_general_codigo_co
        )
        val valorCodigo = Etiqueta(
            tipo = Etiqueta.Tipo.FUERTE,
            valor = codigoMasDigitoVerificador()
        )
        return Grupo(titulo = tituloCodigo, subtitulo = valorCodigo)
    }

    private fun codigoMasDigitoVerificador() =
        ("${datosConsultora.codigo}-${datosConsultora.digitoVerificador}").trim()

    private fun crearGrupoSaldoPendiente(): Grupo {
        val tituloSaldo = Etiqueta(
            tipo = Etiqueta.Tipo.LIGERO,
            idRecurso = R.string.perfil_general_saldopendiente_co
        )
        val valorSaldo = Etiqueta(
            tipo = Etiqueta.Tipo.NEGATIVO,
            valor = datosConsultora.saldoPendiente.formatearConComas()
        )
        return Grupo(titulo = tituloSaldo, subtitulo = valorSaldo)
    }

    private fun crearFilaIngresoYUltimaFacturacion(): Fila {
        val grupoCampaniaIngreso = crearGrupoCampaniaIngreso()
        val grupoCampaniaUltimaFacturacion = crearGrupoCampaniaUltimaFacturacion()
        return Fila(listOf(grupoCampaniaIngreso, grupoCampaniaUltimaFacturacion))
    }

    private fun crearGrupoCampaniaIngreso(): Grupo {
        val tituloIngreso = Etiqueta(
            tipo = Etiqueta.Tipo.LIGERO,
            idRecurso = R.string.perfil_general_campaniaingreso
        )
        val valorIngreso = Etiqueta(
            tipo = Etiqueta.Tipo.FUERTE,
            valor = datosConsultora.codigoCampaniaIngreso.trim()
        )
        return Grupo(titulo = tituloIngreso, subtitulo = valorIngreso)
    }

    private fun crearGrupoCampaniaUltimaFacturacion(): Grupo {
        val tituloUltimaFacturacion = Etiqueta(
            tipo = Etiqueta.Tipo.LIGERO,
            idRecurso = R.string.perfil_general_ultimafacturacion
        )
        val valorUltimaFacturacion = Etiqueta(
            tipo = Etiqueta.Tipo.FUERTE,
            valor = datosConsultora.codigoCampaniaUltimaFacturacion.trim()
        )
        return Grupo(titulo = tituloUltimaFacturacion, subtitulo = valorUltimaFacturacion)
    }

    private fun crearFilaDniYReingresos(): Fila {
        val grupoDni = crearGrupoDni()
        val grupoReingresos = crearGrupoReingresos()
        return Fila(listOf(grupoDni, grupoReingresos))
    }

    private fun crearGrupoDni(): Grupo {
        val tituloDni = Etiqueta(
            tipo = Etiqueta.Tipo.LIGERO,
            idRecurso = R.string.perfil_general_documentoidentidad_co
        )
        val valorDni = Etiqueta(
            tipo = Etiqueta.Tipo.FUERTE,
            valor = datosConsultora.contact.document.trim()
        )
        return Grupo(titulo = tituloDni, subtitulo = valorDni)
    }

    private fun crearGrupoReingresos(): Grupo {
        val titulo = Etiqueta(
            tipo = Etiqueta.Tipo.LIGERO,
            idRecurso = R.string.perfil_general_cantidadreingresos_co
        )
        val valor = Etiqueta(
            tipo = Etiqueta.Tipo.FUERTE,
            valor = datosConsultora.cantidadReingresos.toString()
        )
        return Grupo(titulo = titulo, subtitulo = valor)
    }

    private fun crearFilaTelefonoYAniversario(): Fila {
        val grupoTelefono = crearGrupoTelefono()
        val grupoAniversario = crearGrupoAniversario()
        return Fila(listOf(grupoTelefono, grupoAniversario))
    }

    private fun crearGrupoTelefono(): Grupo {
        val titulo = Etiqueta(
            tipo = Etiqueta.Tipo.LIGERO,
            idRecurso = R.string.perfil_general_telefono_co
        )
        val valor = Etiqueta(
            tipo = Etiqueta.Tipo.FUERTE,
            valor = datosConsultora.celular.trim()
        )
        return Grupo(titulo = titulo, subtitulo = valor)
    }

    private fun crearGrupoAniversario(): Grupo {
        val titulo = Etiqueta(
            tipo = Etiqueta.Tipo.LIGERO,
            idRecurso = R.string.perfil_general_aniversario_co
        )
        val valor = Etiqueta(
            tipo = Etiqueta.Tipo.FUERTE,
            valor = datosConsultora.contact.anniversaryDate?.toDescriptionDayYear().orEmpty()
        )
        return Grupo(titulo = titulo, subtitulo = valor)
    }

    private fun crearFilaAutorizadaYPayment(): Fila {
        val grupoAutorizada = crearGrupoAutorizada()
        val grupoPayment = crearGrupoPayment()
        return Fila(listOf(grupoAutorizada, grupoPayment))
    }

    private fun crearFilaCorreo(): Fila {
        val grupoCorreo = crearGrupoCorreo()
        return Fila(listOf(grupoCorreo))
    }

    private fun crearGrupoCorreo(): Grupo {
        val tituloDni = Etiqueta(
            tipo = Etiqueta.Tipo.LIGERO,
            idRecurso = R.string.perfil_general_correo_co
        )
        val valorDni = Etiqueta(
            tipo = Etiqueta.Tipo.FUERTE,
            valor = datosConsultora.contact.email.trim(),
            idRecursoVacio = R.string.perfil_sin_correo_datos
        )
        return Grupo(titulo = tituloDni, subtitulo = valorDni)
    }

    private fun crearGrupoAutorizada(): Grupo {
        val tituloAutorizada = Etiqueta(
            tipo = Etiqueta.Tipo.LIGERO,
            idRecurso = R.string.perfil_general_autorizada_co
        )
        val razonBloqueo = if (datosConsultora.autorizada) {
            R.string.perfil_general_autorizada
        } else {
            R.string.perfil_general_no_autorizada
        }
        val valorAutorizada = Etiqueta(
            tipo = Etiqueta.Tipo.FUERTE,
            idRecurso = razonBloqueo
        )
        return Grupo(titulo = tituloAutorizada, subtitulo = valorAutorizada)
    }

    private fun crearGrupoPayment(): Grupo {
        val tituloPayment = Etiqueta(
            tipo = Etiqueta.Tipo.LIGERO,
            idRecurso = R.string.perfil_general_payment_co
        )
        val payment = if (datosConsultora.hasCashPayment) {
            R.string.perfil_general_has_cash_payment
        } else {
            R.string.perfil_general_has_not_cash_payment
        }
        val valorPayment = Etiqueta(
            tipo = Etiqueta.Tipo.FUERTE,
            idRecurso = payment
        )
        return Grupo(titulo = tituloPayment, subtitulo = valorPayment)
    }

    private fun crearFilaDireccion(): Fila {
        val grupoDireccion = crearGrupoDireccion()
        return Fila(listOf(grupoDireccion))
    }

    private fun crearGrupoDireccion(): Grupo {
        val tituloDireccion = Etiqueta(
            tipo = Etiqueta.Tipo.LIGERO,
            idRecurso = R.string.perfil_general_direccion_co
        )
        val valorDireccion = Etiqueta(
            tipo = Etiqueta.Tipo.FUERTE,
            valor = datosConsultora.contact.address.trim()
        )
        return Grupo(titulo = tituloDireccion, subtitulo = valorDireccion)
    }

    private fun crearGrupoMultiMarca(): Grupo {
        val tituloMultiMarca = Etiqueta(
            tipo = Etiqueta.Tipo.LIGERO,
            idRecurso = R.string.perfil_general_multi_marca
        )
        val valorMultiMarca = Etiqueta(
            tipo = Etiqueta.Tipo.FUERTE,
            valor = if (datosConsultora.contact.multiBrand == true) {
                "SI"
            } else {
                "NO"
            }
        )
        return Grupo(titulo = tituloMultiMarca, subtitulo = valorMultiMarca)
    }

    private fun createRowReference(): Fila {
        val group = createReferenceGroup()
        return Fila(listOf(group))
    }

    private fun createRowMultiBrand(): Fila {
        val group = crearGrupoMultiMarca()
        return Fila(listOf(group))
    }

    private fun createReferenceGroup(): Grupo {
        val referenceTitle = Etiqueta(
            tipo = Etiqueta.Tipo.LIGERO,
            idRecurso = R.string.perfil_general_reference_co
        )
        val referenceValue = Etiqueta(
            tipo = Etiqueta.Tipo.FUERTE,
            valor = datosConsultora.contact.addressReference.trim()
        )
        return Grupo(titulo = referenceTitle, subtitulo = referenceValue)
    }
}
