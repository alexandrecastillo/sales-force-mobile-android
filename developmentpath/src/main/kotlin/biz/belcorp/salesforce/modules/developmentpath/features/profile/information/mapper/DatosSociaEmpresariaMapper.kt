package biz.belcorp.salesforce.modules.developmentpath.features.profile.information.mapper

import biz.belcorp.salesforce.core.utils.formatearConComas
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.profile.ProfileInfo
import biz.belcorp.salesforce.modules.developmentpath.utils.guionSiEsNullOVacio
import biz.belcorp.salesforce.modules.developmentpath.widgets.etiqueta.model.ContenedorInfoBasica
import biz.belcorp.salesforce.modules.developmentpath.widgets.etiqueta.model.Etiqueta
import biz.belcorp.salesforce.modules.developmentpath.widgets.etiqueta.model.Fila
import biz.belcorp.salesforce.modules.developmentpath.widgets.etiqueta.model.Grupo

class DatosSociaEmpresariaMapper(
    private val datosSE: ProfileInfo.DatoPersonaSe
) : DatosPersonaMapper() {

    override fun map(): ContenedorInfoBasica {
        val filaCodigoYSaldo = crearFilaCodigoYSaldo()
        val filaIngresoYUltimaFacturacion = crearFilaCampaniaIngresoYUltimaFacturacion()
        val filaDniYReingresos = crearFilaDniYSeccion()
        val filaCorreo = crearFilaCorreo()
        val filaDireccion = crearFilaDireccion()
        val filaMultiMarca = createRowMultiBrand()

        return ContenedorInfoBasica(
            listOf(
                filaCodigoYSaldo,
                filaIngresoYUltimaFacturacion,
                filaDniYReingresos,
                filaCorreo,
                filaDireccion,
                filaMultiMarca
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
            idRecurso = R.string.perfil_general_codigo_se
        )
        val valorCodigo = Etiqueta(
            tipo = Etiqueta.Tipo.FUERTE,
            valor = datosSE.codigo.trim()
        )
        return Grupo(titulo = tituloCodigo, subtitulo = valorCodigo)
    }

    private fun crearGrupoSaldoPendiente(): Grupo {
        val tituloSaldo = Etiqueta(
            tipo = Etiqueta.Tipo.LIGERO,
            idRecurso = R.string.perfil_general_saldopendiente_co
        )
        val valorSaldo = Etiqueta(
            tipo = Etiqueta.Tipo.NEGATIVO,
            valor = datosSE.saldoPendiente.formatearConComas()
        )
        return Grupo(titulo = tituloSaldo, subtitulo = valorSaldo)
    }

    private fun crearFilaCampaniaIngresoYUltimaFacturacion(): Fila {
        val grupoCampaniaIngreso = crearGrupoCampaniaIngreso()
        val grupoUltimaFacturacion = crearGrupoUltimaFacturacion()
        return Fila(
            listOf(
                grupoCampaniaIngreso,
                grupoUltimaFacturacion
            )
        )
    }

    private fun crearGrupoCampaniaIngreso(): Grupo {
        val titulo = Etiqueta(
            tipo = Etiqueta.Tipo.LIGERO,
            idRecurso = R.string.perfil_general_campaniaingreso
        )
        val valor = Etiqueta(
            tipo = Etiqueta.Tipo.FUERTE,
            valor = datosSE.campaniaIngreso.guionSiEsNullOVacio()
        )
        return Grupo(titulo = titulo, subtitulo = valor)
    }

    private fun crearGrupoUltimaFacturacion(): Grupo {
        val titulo = Etiqueta(
            tipo = Etiqueta.Tipo.LIGERO,
            idRecurso = R.string.perfil_general_ultimafacturacion
        )
        val valor = Etiqueta(
            tipo = Etiqueta.Tipo.FUERTE,
            valor = datosSE.campaniaUltimaFacturacion.guionSiEsNullOVacio()
        )
        return Grupo(titulo = titulo, subtitulo = valor)
    }

    private fun crearFilaDniYSeccion(): Fila {
        val grupoDni = crearGrupoDni()
        val grupoSeccion = crearGrupoSeccion()
        return Fila(listOf(grupoDni, grupoSeccion))
    }

    private fun crearGrupoDni(): Grupo {
        val tituloDni = Etiqueta(
            tipo = Etiqueta.Tipo.LIGERO,
            idRecurso = R.string.perfil_general_documentoidentidad_co
        )
        val valorDni = Etiqueta(
            tipo = Etiqueta.Tipo.FUERTE,
            valor = datosSE.contact.document.trim()
        )
        return Grupo(titulo = tituloDni, subtitulo = valorDni)
    }

    private fun crearGrupoSeccion(): Grupo {
        val titulo = Etiqueta(
            tipo = Etiqueta.Tipo.LIGERO,
            idRecurso = R.string.perfil_general_seccion_se
        )
        val valor = Etiqueta(
            tipo = Etiqueta.Tipo.FUERTE,
            valor = datosSE.codigoSeccion
        )
        return Grupo(titulo = titulo, subtitulo = valor)
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
            valor = datosSE.contact.email.trim(),
            idRecursoVacio = R.string.perfil_sin_correo_datos
        )
        return Grupo(titulo = tituloDni, subtitulo = valorDni)
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
            valor = datosSE.contact.address.trim()
        )
        return Grupo(titulo = tituloDireccion, subtitulo = valorDireccion)
    }

    private fun createRowMultiBrand(): Fila {
        val group = crearGrupoMultiMarca()
        return Fila(listOf(group))
    }

    private fun crearGrupoMultiMarca(): Grupo {
        val tituloMultiMarca = Etiqueta(
            tipo = Etiqueta.Tipo.LIGERO,
            idRecurso = R.string.perfil_general_multi_marca
        )
        val valorMultiMarca = Etiqueta(
            tipo = Etiqueta.Tipo.FUERTE,
            valor = if (datosSE.contact.multiBrand == true) {
                "SI"
            } else {
                "NO"
            }
        )
        return Grupo(titulo = tituloMultiMarca, subtitulo = valorMultiMarca)
    }
}
