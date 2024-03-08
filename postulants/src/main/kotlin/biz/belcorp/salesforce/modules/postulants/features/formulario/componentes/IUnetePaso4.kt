package biz.belcorp.salesforce.modules.postulants.features.formulario.componentes

import biz.belcorp.salesforce.modules.postulants.features.entities.SexoModel
import biz.belcorp.salesforce.modules.postulants.features.entities.TablaLogicaModel
import biz.belcorp.salesforce.modules.postulants.features.entities.TipoMetaModel

interface IUnetePaso4 : IUnetePaso {

    fun showGeneros(generos: List<SexoModel>) = Unit

    fun showTipoMeta(tiposMetas: List<TipoMetaModel>) = Unit

    fun showNivelEducativo(nivelEducativo: List<TablaLogicaModel>) = Unit

    fun showEstadoCivil(estadoCivil: List<TablaLogicaModel>) = Unit

    fun showTipoVinculoFamiliar(tipoVinculo: List<TablaLogicaModel>) = Unit

    fun showTipoVinculoNoFamiliar(tipoVinculoNoFamiliar: List<TablaLogicaModel>) = Unit

    fun showTipoVinculoAval(tipoVinculo: List<TablaLogicaModel>) = Unit

    fun showOtrasMarcas(tipoVinculo: List<TablaLogicaModel>) = Unit

    fun showTipoPersona(tiposPersona: List<TablaLogicaModel>) = Unit

    fun showOrigenIngreso(origenesIngreso: List<TablaLogicaModel>) = Unit

    fun showConsultoraRecomienda(consultora: String) = Unit

    fun showCodigoConsultoraRecomienda(codigoConsultora: String) = Unit

    fun errorAlObtenerNombreConsultoraRecomendante() = Unit
}
