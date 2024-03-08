package biz.belcorp.salesforce.modules.postulants.core.data.repository.postulante.mappers

import biz.belcorp.salesforce.core.entities.sql.unete.PrePostulanteEntity
import biz.belcorp.salesforce.core.utils.Mapper
import biz.belcorp.salesforce.core.utils.ifEmptyOrNullToZero
import biz.belcorp.salesforce.modules.postulants.core.data.network.dto.unete.PrePostulanteDTO
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.PrePostulante
import biz.belcorp.salesforce.modules.postulants.utils.Constant
import biz.belcorp.salesforce.modules.postulants.utils.toInt

class PrePostulanteEntityDataMapper : Mapper<PrePostulante, PrePostulanteEntity>() {

    companion object {

        fun map(value: PrePostulanteDTO): PrePostulanteEntity? {

            val entity = PrePostulanteEntity()
            entity.apellidoMaterno = value.apellidoMaterno
            entity.apellidoPaterno = value.apellidoPaterno
            entity.celular = value.celular
            entity.codigoConsultoraRecomienda = value.codigoConsultoraRecomienda
            entity.codigoPostal = value.codigoPostal
            entity.correoElectronico = value.correoElectronico
            entity.direccion = value.direccion
            entity.estadoGEO = value.estadoGEO
            entity.estadoPostulante = value.estadoPostulante
            entity.fechaCreacion = value.fechaCreacion
            entity.fechaModificacion = value.fechaModificacion
            entity.ipOrigen = value.ipOrigen
            entity.latitud = value.latitud
            entity.longitud = value.longitud
            entity.lugarHijo = value.lugarHijo
            entity.lugarPadre = value.lugarPadre
            entity.nombreConsultoraRecomienda = value.nombreConsultoraRecomienda
            entity.nombres = value.nombres
            entity.numeroDocumento = value.numeroDocumento
            entity.rechazo = value.rechazo
            entity.respuestaGEO = value.respuestaGeo
            entity.solicitudPrePostulanteID = value.solicitudPrePostulanteID
            entity.tipoDocumento = value.tipoDocumento
            entity.vieneDe = value.vieneDe
            entity.zonificacion = value.zonificacion
            entity.paisID = value.paisID
            entity.sincronizado = value.sincronizado == Constant.NUMERO_UNO
            entity.paisID = value.pais?.toIntOrNull() ?: Constant.NUMERO_CERO
            entity.pais = value.pais
            entity.paso = value.paso
            entity.edad = value.edad
            entity.primerNombre = value.primerNombre
            entity.segundoNombre = value.segundoNombre
            entity.fechaNacimiento = value.fechaNacimiento
            entity.sexo = value.sexo
            entity.telefono = value.telefono
            entity.referencia = value.referencia
            entity.tieneExperiencia = value.tieneExperiencia
            entity.teRecomendoOtraConsultora = value.teRecomendoOtraConsultora
            entity.campaniaDeRegistro = value.campaniaDeRegistro
            entity.fuenteIngreso = value.fuenteIngreso
            entity.estadoPrePostulante = value.estadoPrePostulante
            entity.codigoZona = value.codigoZona
            entity.codigoSeccion = value.codigoSeccion
            entity.codigoTerritorio = value.codigoTerritorio
            entity.tipoSolicitud = value.tipoSolicitud
            entity.tipoContacto = value.tipoContacto
            entity.campaniaID = value.campaniaID
            entity.indicadorActivo = value.indicadorActivo
            entity.usuarioCreacion = value.usuarioCreacion
            entity.estadoBurocrediticio = value.estadoBurocrediticio
            entity.vistoPorGZ = value.vistoPorGZ.toInt()
            entity.vistoPorSAC = value.vistoPorSAC.toInt()
            entity.vistoPorSE = value.vistoPorSE.toInt()
            entity.indicadorOptin = value.indicadorOptin.toInt()
            entity.estadoTelefonico = value.estadoTelefonico
            entity.subEstadoPostulante = value.subEstadoPostulante
            entity.fechaEnvio = value.fechaEnvio
            entity.requiereAval = value.requiereAval.toInt()
            entity.offline = value.offline
            entity.requiereAprobacionSAC = value.requiereAprobacionSAC
            entity.devueltoPorSAC = value.devueltoPorSAC
            entity.flagConsultoraDigital = value.flagConsultoraDigital

            return entity
        }

        fun map(values: List<PrePostulanteDTO>): List<PrePostulanteEntity> {
            return values.map {
                map(it) as PrePostulanteEntity
            }
        }


    }

    override fun map(value: PrePostulante): PrePostulanteEntity {

        val entity = PrePostulanteEntity()

        entity.uuid = value.UUID
        entity.apellidoMaterno = value.apellidoMaterno
        entity.apellidoPaterno = value.apellidoPaterno
        entity.celular = value.celular
        entity.codigoConsultoraRecomienda = value.codigoConsultoraRecomienda
        entity.codigoPostal = value.codigoPostal
        entity.correoElectronico = value.correoElectronico
        entity.direccion = value.direccion
        entity.estadoGEO = value.estadoGEO
        entity.estadoPostulante = value.estadoPostulante
        entity.fechaCreacion = value.fechaCreacion
        entity.fechaModificacion = value.fechaModificacion
        entity.ipOrigen = value.ipOrigen
        entity.latitud = value.latitud
        entity.longitud = value.longitud
        entity.lugarHijo = value.lugarHijo
        entity.lugarPadre = value.lugarPadre
        entity.nombreConsultoraRecomienda = value.nombreConsultoraRecomienda
        entity.nombres = value.nombres
        entity.numeroDocumento = value.numeroDocumento
        entity.rechazo = value.rechazo
        entity.respuestaGEO = value.respuestaGEO
        entity.solicitudPrePostulanteID = value.solicitudPrePostulanteID
        entity.tipoDocumento = value.tipoDocumento
        entity.vieneDe = value.vieneDe
        entity.zonificacion = value.zonificacion
        entity.codigoZona = value.codigoZona
        entity.codigoSeccion = value.codigoSeccion
        entity.codigoTerritorio = value.codigoTerritorio
        entity.paisID = value.paisID
        entity.sincronizado = value.sincronizado
        entity.paisID = value.paisID
        entity.pais = value.pais
        entity.paso = value.paso
        entity.edad = value.edad
        entity.primerNombre = value.primerNombre
        entity.segundoNombre = value.segundoNombre
        entity.fechaNacimiento = value.fechaNacimiento
        entity.sexo = value.sexo
        entity.telefono = value.telefono
        entity.referencia = value.referencia
        entity.tieneExperiencia = value.tieneExperiencia
        entity.teRecomendoOtraConsultora = value.teRecomendoOtraConsultora
        entity.campaniaDeRegistro = value.campaniaDeRegistro
        entity.fuenteIngreso = value.fuenteIngreso
        entity.estadoPrePostulante = value.estadoPrePostulante
        entity.tipoSolicitud = value.tipoSolicitud
        entity.tipoContacto = value.tipoContacto
        entity.campaniaID = value.campaniaID.ifEmptyOrNullToZero().toInt()
        entity.indicadorActivo = value.indicadorActivo
        entity.usuarioCreacion = value.usuarioCreacion
        entity.estadoBurocrediticio = value.estadoBurocrediticio
        entity.vistoPorGZ = value.vistoPorGZ
        entity.vistoPorSE = value.vistoPorSE
        entity.vistoPorSAC = value.vistoPorSAC
        entity.indicadorOptin = value.indicadorOptin
        entity.estadoTelefonico = value.estadoTelefonico
        entity.subEstadoPostulante = value.subEstadoPostulante
        entity.fechaEnvio = value.fechaEnvio
        entity.requiereAval = value.requiereAval
        entity.offline = value.offline
        entity.requiereAprobacionSAC = value.requiereAprobacionSAC
        entity.devueltoPorSAC = value.devueltoPorSAC
        entity.flagConsultoraDigital = value.flagConsultoraDigital

        return entity

    }


    override fun reverseMap(value: PrePostulanteEntity): PrePostulante {

        val entity = PrePostulante()

        entity.UUID = value.uuid
        entity.apellidoMaterno = value.apellidoMaterno
        entity.apellidoPaterno = value.apellidoPaterno
        entity.celular = value.celular
        entity.codigoConsultoraRecomienda = value.codigoConsultoraRecomienda
        entity.codigoPostal = value.codigoPostal
        entity.correoElectronico = value.correoElectronico
        entity.direccion = value.direccion
        entity.estadoGEO = value.estadoGEO
        entity.estadoPostulante = value.estadoPostulante
        entity.fechaCreacion = value.fechaCreacion
        entity.fechaModificacion = value.fechaModificacion
        entity.ipOrigen = value.ipOrigen
        entity.latitud = value.latitud
        entity.longitud = value.longitud
        entity.lugarHijo = value.lugarHijo
        entity.lugarPadre = value.lugarPadre
        entity.nombreConsultoraRecomienda = value.nombreConsultoraRecomienda
        entity.nombres = value.nombres
        entity.numeroDocumento = value.numeroDocumento
        entity.rechazo = value.rechazo
        entity.respuestaGEO = value.respuestaGEO
        entity.solicitudPrePostulanteID = value.solicitudPrePostulanteID
        entity.tipoDocumento = value.tipoDocumento
        entity.vieneDe = value.vieneDe
        entity.zonificacion = value.zonificacion
        entity.codigoZona = value.codigoZona
        entity.codigoSeccion = value.codigoSeccion
        entity.codigoTerritorio = value.codigoTerritorio
        entity.paisID = value.paisID
        entity.sincronizado = value.sincronizado
        entity.paisID = value.paisID
        entity.pais = value.pais
        entity.paso = value.paso
        entity.edad = value.edad
        entity.primerNombre = value.primerNombre
        entity.segundoNombre = value.segundoNombre
        entity.fechaNacimiento = value.fechaNacimiento
        entity.sexo = value.sexo
        entity.telefono = value.telefono
        entity.referencia = value.referencia
        entity.tieneExperiencia = value.tieneExperiencia
        entity.teRecomendoOtraConsultora = value.teRecomendoOtraConsultora
        entity.campaniaDeRegistro = value.campaniaDeRegistro
        entity.fuenteIngreso = value.fuenteIngreso
        entity.estadoPrePostulante = value.estadoPrePostulante
        entity.tipoSolicitud = value.tipoSolicitud
        entity.tipoContacto = value.tipoContacto
        entity.campaniaID = value.campaniaID.toString()
        entity.indicadorActivo = value.indicadorActivo
        entity.usuarioCreacion = value.usuarioCreacion
        entity.estadoBurocrediticio = value.estadoBurocrediticio
        entity.vistoPorGZ = value.vistoPorGZ
        entity.vistoPorSE = value.vistoPorSE
        entity.vistoPorSAC = value.vistoPorSAC
        entity.indicadorOptin = value.indicadorOptin
        entity.estadoTelefonico = value.estadoTelefonico
        entity.subEstadoPostulante = value.subEstadoPostulante
        entity.fechaEnvio = value.fechaEnvio
        entity.requiereAval = value.requiereAval
        entity.offline = value.offline
        entity.requiereAprobacionSAC = value.requiereAprobacionSAC
        entity.devueltoPorSAC = value.devueltoPorSAC
        entity.flagConsultoraDigital = value.flagConsultoraDigital

        return entity
    }


}
