package biz.belcorp.salesforce.modules.developmentpath.features.profile.cobranzaestadodecuenta.cobranza

import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.widgets.etiqueta.model.ContenedorInfoBasica
import biz.belcorp.salesforce.modules.developmentpath.widgets.etiqueta.model.Etiqueta
import biz.belcorp.salesforce.modules.developmentpath.widgets.etiqueta.model.Fila
import biz.belcorp.salesforce.modules.developmentpath.widgets.etiqueta.model.Grupo

class DatosCobranzaGridModelMapper(private val datos: DatosCobranzaModel) {

    fun map(): ContenedorInfoBasica {
        val filaVentaGananciaYSaldoPendiente = crearFilaVentaGananciaYSaldoPendiente()

        return ContenedorInfoBasica(
            listOf(
                filaVentaGananciaYSaldoPendiente
            )
        )
    }

    private fun crearFilaVentaGananciaYSaldoPendiente(): Fila {
        val grupo1 = crearGrupoSaldoPendiente()
        val grupo2 = crearGrupoVentaGanancia()
        return Fila(listOf(grupo1, grupo2))
    }

    private fun crearGrupoVentaGanancia(): Grupo {
        val titulo = Etiqueta(
            tipo = Etiqueta.Tipo.LIGERO,
            idRecurso = R.string.str_empty
        )
        val valor = Etiqueta(
            tipo = Etiqueta.Tipo.FUERTE,
            valor = Constant.EMPTY_STRING
        )
        return Grupo(titulo = titulo, subtitulo = valor)
    }

    private fun crearGrupoSaldoPendiente(): Grupo {
        val titulo = Etiqueta(
            tipo = Etiqueta.Tipo.LIGERO,
            idRecurso = R.string.saldo_pendiente
        )
        val valor = Etiqueta(
            tipo = Etiqueta.Tipo.NEGATIVO,
            valor = datos.saldoPendiente.trim()
        )
        return Grupo(titulo = titulo, subtitulo = valor)
    }

    private fun crearFilaConsultoraConsecutivaYVentaFacturada(): Fila {
        val grupo1 = crearGrupoConsultoraConsecutiva()
        val grupo2 = crearGrupoVentaFacturada()
        return Fila(listOf(grupo1, grupo2))
    }

    private fun crearGrupoConsultoraConsecutiva(): Grupo {
        val titulo = Etiqueta(
            tipo = Etiqueta.Tipo.LIGERO,
            idRecurso = R.string.consultora_consecutiva
        )
        val valor = Etiqueta(
            tipo = Etiqueta.Tipo.FUERTE,
            valor = datos.consultoraConsecutiva.trim()
        )
        return Grupo(titulo = titulo, subtitulo = valor)
    }

    private fun crearGrupoVentaFacturada(): Grupo {
        val titulo = Etiqueta(
            tipo = Etiqueta.Tipo.LIGERO,
            idRecurso = R.string.venta_facturada
        )
        val valor = Etiqueta(
            tipo = Etiqueta.Tipo.FUERTE,
            valor = datos.ventaFacturada.trim()
        )
        return Grupo(titulo = titulo, subtitulo = valor)
    }

    private fun crearFilaRecaudacionComisionableYGanancia(): Fila {
        val grupo1 = crearGrupoRecaudacionComisionable()
        val grupo2 = crearGrupoGanancia()
        return Fila(listOf(grupo1, grupo2))
    }

    private fun crearGrupoRecaudacionComisionable(): Grupo {
        val titulo = Etiqueta(
            tipo = Etiqueta.Tipo.LIGERO,
            idRecurso = R.string.recaudacion_comisionable
        )
        val valor = Etiqueta(
            tipo = Etiqueta.Tipo.FUERTE,
            valor = datos.recaudoComisionable.trim()
        )
        return Grupo(titulo = titulo, subtitulo = valor)
    }

    private fun crearGrupoGanancia(): Grupo {
        val titulo = Etiqueta(
            tipo = Etiqueta.Tipo.LIGERO,
            idRecurso = R.string.ganancia
        )
        val valor = Etiqueta(
            tipo = Etiqueta.Tipo.FUERTE,
            valor = datos.ganancia.trim()
        )
        return Grupo(titulo = titulo, subtitulo = valor)
    }

    private fun crearFilaRecaudoTotalYRecaudacionNoComisionable(): Fila {
        val grupo1 = crearGrupoRecaudoTotal()
        val grupo2 = crearGrupoRecaudacionNoComisionable()
        return Fila(listOf(grupo1, grupo2))
    }

    private fun crearGrupoRecaudoTotal(): Grupo {
        val titulo = Etiqueta(
            tipo = Etiqueta.Tipo.LIGERO,
            idRecurso = R.string.recaudo_total
        )
        val valor = Etiqueta(
            tipo = Etiqueta.Tipo.FUERTE,
            valor = datos.recaudoTotal.trim()
        )
        return Grupo(titulo = titulo, subtitulo = valor)
    }

    private fun crearGrupoRecaudacionNoComisionable(): Grupo {
        val titulo = Etiqueta(
            tipo = Etiqueta.Tipo.LIGERO,
            idRecurso = R.string.recaudacion_no_comisionable
        )
        val valor = Etiqueta(
            tipo = Etiqueta.Tipo.FUERTE,
            valor = datos.recaudoNoComisionable.trim()
        )
        return Grupo(titulo = titulo, subtitulo = valor)
    }

    private fun crearFilaGananciaVentaRetailYVentaRetail(): Fila {
        val grupo1 = crearGrupoGananciaVentaRetail()
        val grupo2 = crearGrupoVentaRetail()
        return Fila(listOf(grupo1, grupo2))
    }

    private fun crearGrupoGananciaVentaRetail(): Grupo {
        val titulo = Etiqueta(
            tipo = Etiqueta.Tipo.LIGERO,
            idRecurso = R.string.ganancia_venta_retail
        )
        val valor = Etiqueta(
            tipo = Etiqueta.Tipo.FUERTE,
            valor = datos.gananciaVentaRetail.trim()
        )
        return Grupo(titulo = titulo, subtitulo = valor)
    }

    private fun crearGrupoVentaRetail(): Grupo {
        val titulo = Etiqueta(
            tipo = Etiqueta.Tipo.LIGERO,
            idRecurso = R.string.venta_retail
        )
        val valor = Etiqueta(
            tipo = Etiqueta.Tipo.FUERTE,
            valor = datos.ventaRetail.trim()
        )
        return Grupo(titulo = titulo, subtitulo = valor)
    }
}
