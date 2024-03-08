package biz.belcorp.salesforce.modules.developmentpath.features.profile.cobranzaganancia

import biz.belcorp.salesforce.core.utils.formatearConComas
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.widgets.etiqueta.model.ContenedorInfoBasica
import biz.belcorp.salesforce.modules.developmentpath.widgets.etiqueta.model.Etiqueta
import biz.belcorp.salesforce.modules.developmentpath.widgets.etiqueta.model.Fila
import biz.belcorp.salesforce.modules.developmentpath.widgets.etiqueta.model.Grupo

class DatosCobranzaGananciaGridModelMapper(private val datos: CobranzaYGananciaModel) {

    fun map(): ContenedorInfoBasica {
        val filaMontoFacturadoNetoYGananciaTotal = crearFilaMontoFacturadoNetoYGananciaTotal()
        val filaMontoRecuperadoYGanancia6d6 = crearFilaMontoRecuperadoYGanancia6d6()
        val filaSaldoDeDeudaYGananciaPedidoAltovalor =
            crearFilaSaldoDeDeudaYGananciaPedidoAltovalor()
        val filaConsultorasConDeudaYGananciaCambioNivel =
            crearFilaConsultorasConDeudaYGananciaCambioNivel()
        val filaRecuperacion = crearFilaRecuperacion()
        return ContenedorInfoBasica(
            listOf(
                filaMontoFacturadoNetoYGananciaTotal,
                filaMontoRecuperadoYGanancia6d6,
                filaSaldoDeDeudaYGananciaPedidoAltovalor,
                filaConsultorasConDeudaYGananciaCambioNivel,
                filaRecuperacion
            )
        )
    }

    private fun crearFilaMontoFacturadoNetoYGananciaTotal(): Fila {
        val grupo1 = crearGrupoMontoFacturadoNeto()
        val grupo2 = crearGrupoGananciaTotal()
        return Fila(listOf(grupo1, grupo2))
    }

    private fun crearGrupoMontoFacturadoNeto(): Grupo {
        val titulo = Etiqueta(
            tipo = Etiqueta.Tipo.LIGERO,
            idRecurso = R.string.datos_monto_facturado_neto
        )
        val valor = Etiqueta(
            tipo = Etiqueta.Tipo.FUERTE,
            valor = datos.montoFacturadoNeto?.formatearConComas() ?: ""
        )
        return Grupo(titulo = titulo, subtitulo = valor)
    }

    private fun crearGrupoGananciaTotal(): Grupo {
        val titulo = Etiqueta(
            tipo = Etiqueta.Tipo.LIGERO,
            idRecurso = R.string.datos_ganancia_total
        )
        val valor = Etiqueta(
            tipo = Etiqueta.Tipo.FUERTE,
            valor = datos.gananciaTotal?.formatearConComas() ?: ""
        )
        return Grupo(titulo = titulo, subtitulo = valor)
    }

    private fun crearFilaMontoRecuperadoYGanancia6d6(): Fila {
        val grupo1 = crearGrupoMontoRecuperado()
        val grupo2 = crearGrupoGanancia6d6()
        return Fila(listOf(grupo1, grupo2))
    }

    private fun crearGrupoMontoRecuperado(): Grupo {
        val titulo = Etiqueta(
            tipo = Etiqueta.Tipo.LIGERO,
            idRecurso = R.string.datos_monto_recuperado
        )
        val valor = Etiqueta(
            tipo = Etiqueta.Tipo.FUERTE,
            valor = datos.montoRecuperado?.formatearConComas() ?: ""
        )
        return Grupo(titulo = titulo, subtitulo = valor)
    }

    private fun crearGrupoGanancia6d6(): Grupo {
        val titulo = Etiqueta(
            tipo = Etiqueta.Tipo.LIGERO,
            idRecurso = R.string.datos_ganancia_6d6
        )
        val valor = Etiqueta(
            tipo = Etiqueta.Tipo.FUERTE,
            valor = datos.ganancia6d6?.formatearConComas() ?: ""
        )

        return Grupo(titulo = titulo, subtitulo = valor)
    }

    private fun crearFilaSaldoDeDeudaYGananciaPedidoAltovalor(): Fila {
        val grupo1 = crearGrupoSaldoDeDeuda()
        val grupo2 = crearGrupoGananciaPedidoAltovalor()
        return Fila(listOf(grupo1, grupo2))
    }

    private fun crearGrupoSaldoDeDeuda(): Grupo {
        val titulo = Etiqueta(
            tipo = Etiqueta.Tipo.LIGERO,
            idRecurso = R.string.datos_saldo_deuda
        )
        val valor = Etiqueta(
            tipo = Etiqueta.Tipo.FUERTE,
            valor = datos.saldoDeuda?.formatearConComas() ?: ""
        )
        return Grupo(titulo = titulo, subtitulo = valor)
    }

    private fun crearGrupoGananciaPedidoAltovalor(): Grupo {
        val titulo = Etiqueta(
            tipo = Etiqueta.Tipo.LIGERO,
            idRecurso = R.string.datos_ganancia_pedido_alto_valor
        )
        val valor = Etiqueta(
            tipo = Etiqueta.Tipo.FUERTE,
            valor = datos.gananciaPedidoAltoValor?.formatearConComas() ?: ""
        )

        return Grupo(titulo = titulo, subtitulo = valor)
    }

    private fun crearFilaConsultorasConDeudaYGananciaCambioNivel(): Fila {
        val grupo1 = crearGrupoConsultorasConDeuda()
        val grupo2 = crearGrupoGananciaCambioNivel()
        return Fila(listOf(grupo1, grupo2))
    }

    private fun crearGrupoConsultorasConDeuda(): Grupo {
        val titulo = Etiqueta(
            tipo = Etiqueta.Tipo.LIGERO,
            idRecurso = R.string.datos_consultoras_con_deuda
        )
        val valor = Etiqueta(
            tipo = Etiqueta.Tipo.FUERTE,
            valor = datos.consultorasDeuda?.formatearConComas() ?: ""
        )
        return Grupo(titulo = titulo, subtitulo = valor)
    }

    private fun crearGrupoGananciaCambioNivel(): Grupo {
        val titulo = Etiqueta(
            tipo = Etiqueta.Tipo.LIGERO,
            idRecurso = R.string.datos_ganancia_cambio_nivel
        )
        val valor = Etiqueta(
            tipo = Etiqueta.Tipo.FUERTE,
            valor = datos.gananciaCambioNivel?.formatearConComas() ?: ""
        )

        return Grupo(titulo = titulo, subtitulo = valor)
    }

    private fun crearFilaRecuperacion(): Fila {
        val grupo1 = crearGrupoRecuperacion()
        return Fila(listOf(grupo1))
    }

    private fun crearGrupoRecuperacion(): Grupo {
        val titulo = Etiqueta(
            tipo = Etiqueta.Tipo.LIGERO,
            idRecurso = R.string.datos_recuperacion
        )
        val valor = Etiqueta(
            tipo = Etiqueta.Tipo.FUERTE,
            valor = datos.recuperacion.toString().plus("%")
        )
        return Grupo(titulo = titulo, subtitulo = valor)
    }
}
