package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eventos.detalle.view

import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eventos.model.AgregarEventoViewModel

interface DetalleEventoView {

    fun pintarUbicacion(ubicacion: String)

    fun pintarFecha(fecha: String)

    fun pintarTitulo(titulo: String)

    fun pintarTodoElDia()

    fun pintarIntervalo(horaInicio: String, horafin: String)

    fun pintarAlerta(cantidad: Int, unidad: AgregarEventoViewModel.Unidad)

    fun permitirEditar()

    fun prohibirEditar()

    fun emitirBroadcast()

    fun cerrar()

    fun mostrarToast()

    fun permitirEliminar()

    fun prohibirEliminar()

    fun mostrarComoEliminado(rolMadre: String)

    fun mostrarMensageRegistrado(confimar :  Boolean)

    fun mostrarConfirmar()

    fun eventoRegistrado()

    fun actualizarEventos()
}
