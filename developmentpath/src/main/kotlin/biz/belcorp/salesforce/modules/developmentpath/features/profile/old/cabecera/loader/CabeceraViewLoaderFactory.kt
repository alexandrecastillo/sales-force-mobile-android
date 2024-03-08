package biz.belcorp.salesforce.modules.developmentpath.features.profile.old.cabecera.loader

import biz.belcorp.salesforce.modules.developmentpath.features.profile.header.PerfilCabeceraModel


class CabeceraViewLoaderFactory {
    companion object {
        fun with(cabeceraModel: PerfilCabeceraModel): CabeceraViewLoader {
            return when (cabeceraModel) {
                is PerfilCabeceraModel.PosibleConsultora ->
                    PosibleConsultoraViewLoader(cabeceraModel)
                is PerfilCabeceraModel.GerenteZona ->
                    GerenteZonaViewLoader(cabeceraModel)
                is PerfilCabeceraModel.GerenteRegion ->
                    GerenteRegionViewLoader(cabeceraModel)
                else -> throw ExceptionInInitializerError("Rol no creado")
            }
        }
    }
}
