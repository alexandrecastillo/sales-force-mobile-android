package biz.belcorp.salesforce.modules.developmentpath.features.profile.header.loader

import biz.belcorp.salesforce.modules.developmentpath.features.profile.header.PerfilCabeceraModel

class CabeceraViewLoaderFactory {
    companion object {
        fun with(model: PerfilCabeceraModel): CabeceraViewLoader {
            return when (model) {
                is PerfilCabeceraModel.Consultora -> ConsultoraViewLoader(model)
                is PerfilCabeceraModel.SociaEmpresaria -> SociaViewLoader(model)
                is PerfilCabeceraModel.GerenteRegion,
                is PerfilCabeceraModel.GerenteZona,
                is PerfilCabeceraModel.ConsultoraConNivel,
                is PerfilCabeceraModel.PosibleConsultora -> throw ExceptionInInitializerError("Rol no creado")
            }
        }
    }
}
