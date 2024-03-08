package biz.belcorp.salesforce.core.data.repository.directory.mappers

import biz.belcorp.salesforce.core.data.repository.directory.cloud.dto.ManagerDirectoryDto
import biz.belcorp.salesforce.core.entities.sql.directorio.DirectorioVentaUsuarioEntity

class ManagerDirectoryTableMapper {

    fun map(list: List<ManagerDirectoryDto.ManagerDirectory>): List<DirectorioVentaUsuarioEntity> {
        return list.map { map(it) }
    }

    private fun map(item: ManagerDirectoryDto.ManagerDirectory): DirectorioVentaUsuarioEntity {
        with(item) {
            return DirectorioVentaUsuarioEntity().apply {
                codCliente = userName
                consultoraId = consultantId.toLong()
                primerApellido = personalInfo.surname
                segundoApellido = personalInfo.secondSurname
                primerNombre = personalInfo.firstName
                nroDoc = personalInfo.document
                codRegion = region
                codZona = zone
                codRol = profile
                telefCasa = personalInfo.telephoneNumber
                telefMovil = personalInfo.cellphoneNumber
                usuario = userName
                fechaNacimiento = personalInfo.birthday
                estado = state.description
                esNueva = isNew
                nroCampaniasComoNueva = campaignsAsNew
            }
        }
    }

}
