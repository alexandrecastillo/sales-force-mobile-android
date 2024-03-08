package biz.belcorp.salesforce.modules.developmentpath.features.focos.presenter

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.PersonaRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.utils.Seleccionable
import biz.belcorp.salesforce.modules.developmentpath.features.focos.model.PersonaModel

class AsignarFocosMapper {

    private val constructorDescripcion = ConstructorTitulo()

    fun map(seleccionable: Seleccionable<PersonaRdd>, seleccionHabilitada: Boolean): PersonaModel {

        return PersonaModel(
                id = seleccionable.elemento.id, seleccionado = seleccionable.seleccionado,
                habilitado = seleccionable.habilitado, iniciales = seleccionable.elemento.iniciales,
                descripcion = constructorDescripcion.crear(seleccionable.elemento),
                codigo = seleccionable.elemento.unidadAdministrativa.codigo,
                seleccionHabilitada = seleccionHabilitada)

    }

    fun map(seleccionables: List<Seleccionable<PersonaRdd>>,
            seleccionHabilitada: Boolean) =
            seleccionables.map { map(it, seleccionHabilitada) }

}
