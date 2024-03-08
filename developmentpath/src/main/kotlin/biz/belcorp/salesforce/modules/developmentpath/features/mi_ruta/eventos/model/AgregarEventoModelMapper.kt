package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eventos.model

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.eventos.Alerta
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.eventos.TipoEventoRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.utils.Seleccionable

class AgregarEventoModelMapper {

    fun convertir(alerta: Seleccionable<Alerta?>):
            AgregarEventoViewModel.Alerta {
        return AgregarEventoViewModel.Alerta(
                cantidad = alerta.elemento?.valor ?: 0,
                unidad = convertir(alerta.elemento?.unidad),
                seleccionado = alerta.seleccionado)
    }

    fun convertir(unidadTiempoAlerta: Alerta.UnidadTiempoAlerta?):
            AgregarEventoViewModel.Unidad {

        return when (unidadTiempoAlerta) {
            null -> AgregarEventoViewModel.Unidad.SIN_ALERTA
            Alerta.UnidadTiempoAlerta.NINGUNO -> AgregarEventoViewModel.Unidad.A_LA_HORA
            Alerta.UnidadTiempoAlerta.MINUTOS -> AgregarEventoViewModel.Unidad.MINUTOS
            Alerta.UnidadTiempoAlerta.HORAS -> AgregarEventoViewModel.Unidad.HORAS
            Alerta.UnidadTiempoAlerta.DIAS -> AgregarEventoViewModel.Unidad.DIAS
            Alerta.UnidadTiempoAlerta.SEMANAS -> AgregarEventoViewModel.Unidad.SEMANAS
        }
    }

    fun convertir(unidadModel: AgregarEventoViewModel.Unidad): Alerta.UnidadTiempoAlerta? {
        return when (unidadModel) {
            AgregarEventoViewModel.Unidad.SIN_ALERTA -> null
            AgregarEventoViewModel.Unidad.A_LA_HORA -> Alerta.UnidadTiempoAlerta.MINUTOS
            AgregarEventoViewModel.Unidad.MINUTOS -> Alerta.UnidadTiempoAlerta.MINUTOS
            AgregarEventoViewModel.Unidad.HORAS -> Alerta.UnidadTiempoAlerta.HORAS
            AgregarEventoViewModel.Unidad.DIAS -> Alerta.UnidadTiempoAlerta.DIAS
            AgregarEventoViewModel.Unidad.SEMANAS -> Alerta.UnidadTiempoAlerta.SEMANAS
        }
    }

    fun convertir(tipoEventoRdd: TipoEventoRdd): AgregarEventoViewModel.TipoEvento {
        return AgregarEventoViewModel.TipoEvento(
                tipoEventoRdd.id,
                tipoEventoRdd.descripcion)
    }

    fun convertir(modelo: AgregarEventoViewModel.Alerta): Alerta? {
        return Alerta(valor = modelo.cantidad,
                      unidad = convertir(modelo.unidad) ?: return null)
    }
}
