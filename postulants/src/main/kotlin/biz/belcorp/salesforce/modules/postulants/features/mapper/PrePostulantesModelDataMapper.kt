package biz.belcorp.salesforce.modules.postulants.features.mapper

import biz.belcorp.salesforce.core.utils.Mapper
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.PrePostulante
import biz.belcorp.salesforce.modules.postulants.features.entities.PrePostulanteModel
import biz.belcorp.salesforce.modules.postulants.utils.isNullOrEmptyStringResult


class PrePostulantesModelDataMapper : Mapper<PrePostulante, PrePostulanteModel>() {

    override fun map(value: PrePostulante): PrePostulanteModel {
        val model = PrePostulanteModel()
        model.UUID = value.UUID
        model.apellidoMaterno = value.apellidoMaterno
        model.apellidoPaterno = value.apellidoPaterno
        model.celular = value.celular
        model.codigoConsultoraRecomienda = value.codigoConsultoraRecomienda
        model.codigoPostal = value?.codigoPostal
        model.correoElectronico = value.correoElectronico
        model.direccion = value.direccion
        model.estadoGEO = value.estadoGEO
        model.estadoPostulante = value.estadoPostulante
        model.fechaCreacion = value.fechaCreacion
        model.fechaModificacion = value.fechaModificacion
        model.ipOrigen = value.ipOrigen
        model.latitud = value.latitud
        model.longitud = value.longitud
        model.lugarHijo = value.lugarHijo
        model.lugarPadre = value.lugarPadre
        model.nombreConsultoraRecomienda = value.nombreConsultoraRecomienda
        model.nombres = value.nombres
        model.numeroDocumento = value.numeroDocumento
        model.rechazo = value.rechazo
        model.respuestaGEO = value.respuestaGEO
        model.solicitudPrePostulanteID = value.solicitudPrePostulanteID
        model.tipoDocumento = value.tipoDocumento
        model.vieneDe = value.vieneDe
        model.zonificacion = value.zonificacion
        model.codigoZona = value.codigoZona
        model.codigoSeccion = value.codigoSeccion
        model.codigoTerritorio = value.codigoTerritorio
        model.paisID = value.paisID
        model.pais = value.pais
        model.sincronizado = value.sincronizado
        model.paso = value.paso

        model.edad = value.edad
        model.primerNombre = value.primerNombre?.isNullOrEmptyStringResult(value.nombres)
        model.segundoNombre = value.segundoNombre
        model.fechaNacimiento = value.fechaNacimiento
        model.sexo = value.sexo
        model.telefono = value.telefono
        model.referencia = value.referencia
        model.tieneExperiencia = value.tieneExperiencia
        model.teRecomendoOtraConsultora = value.teRecomendoOtraConsultora

        model.campaniaDeRegistro = value.campaniaDeRegistro
        model.codigoRegion = value.codigoRegion
        model.fuenteIngreso = value.fuenteIngreso
        model.estadoPrePostulante = value.estadoPrePostulante

        model.tipoSolicitud = value.tipoSolicitud
        model.tipoContacto = value.tipoContacto
        model.campaniaID = value.campaniaID
        model.indicadorActivo = value.indicadorActivo
        model.usuarioCreacion = value.usuarioCreacion
        model.estadoBurocrediticio = value.estadoBurocrediticio
        model.vistoPorGZ = value.vistoPorGZ
        model.vistoPorSE = value.vistoPorSE
        model.vistoPorSAC = value.vistoPorSAC
        model.indicadorOptin = value.indicadorOptin
        model.estadoTelefonico = value.estadoTelefonico
        model.subEstadoPostulante = value.subEstadoPostulante
        model.fechaEnvio = value.fechaEnvio
        model.requiereAval = value.requiereAval
        model.offline = value.offline
        model.requiereAprobacionSAC = value.requiereAprobacionSAC
        model.devueltoPorSAC = value.devueltoPorSAC
        model.flagConsultoraDigital = value.flagConsultoraDigital

        return model
    }

    override fun reverseMap(value: PrePostulanteModel): PrePostulante {
        val model = PrePostulante()
        model.UUID = value.UUID
        model.apellidoMaterno = value.apellidoMaterno
        model.apellidoPaterno = value.apellidoPaterno
        model.celular = value.celular
        model.codigoConsultoraRecomienda = value.codigoConsultoraRecomienda
        model.codigoPostal = value.codigoPostal
        model.correoElectronico = value.correoElectronico
        model.direccion = value.direccion
        model.estadoGEO = value.estadoGEO
        model.estadoPostulante = value.estadoPostulante
        model.fechaCreacion = value.fechaCreacion
        model.fechaModificacion = value.fechaModificacion
        model.ipOrigen = value.ipOrigen
        model.latitud = value.latitud
        model.longitud = value.longitud
        model.lugarHijo = value.lugarHijo
        model.lugarPadre = value.lugarPadre
        model.nombreConsultoraRecomienda = value.nombreConsultoraRecomienda
        model.nombres = value.nombres
        model.numeroDocumento = value.numeroDocumento
        model.rechazo = value.rechazo
        model.respuestaGEO = value.respuestaGEO
        model.solicitudPrePostulanteID = value.solicitudPrePostulanteID
        model.tipoDocumento = value.tipoDocumento
        model.vieneDe = value.vieneDe
        model.zonificacion = value.zonificacion
        model.codigoZona = value.codigoZona
        model.codigoSeccion = value.codigoSeccion
        model.codigoTerritorio = value.codigoTerritorio
        model.paisID = value.paisID
        model.pais = value.pais
        model.sincronizado = value.sincronizado
        model.paso = value.paso
        model.offline = value.offline

        model.primerNombre = value.primerNombre?.isNullOrEmptyStringResult(value.nombres)
        model.segundoNombre = value.segundoNombre
        model.edad = value.edad
        model.fechaNacimiento = value.fechaNacimiento
        model.telefono = value.telefono
        model.sexo = value.sexo
        model.referencia = value.referencia
        model.tieneExperiencia = value.tieneExperiencia
        model.teRecomendoOtraConsultora = value.teRecomendoOtraConsultora
        model.fuenteIngreso = value.fuenteIngreso
        model.tipoSolicitud = value.tipoSolicitud
        model.tipoContacto = value.tipoContacto
        model.campaniaID = value.campaniaID
        model.indicadorActivo = value.indicadorActivo
        model.usuarioCreacion = value.usuarioCreacion
        model.estadoBurocrediticio = value.estadoBurocrediticio
        model.vistoPorGZ = value.vistoPorGZ
        model.vistoPorSAC = value.vistoPorSAC
        model.vistoPorSE = value.vistoPorSE
        model.indicadorOptin = value.indicadorOptin
        model.estadoTelefonico = value.estadoTelefonico
        model.subEstadoPostulante = value.subEstadoPostulante
        model.fechaEnvio = value.fechaEnvio
        model.requiereAval = value.requiereAval
        model.requiereAprobacionSAC = value.requiereAprobacionSAC
        model.devueltoPorSAC = value.devueltoPorSAC
        model.estadoPrePostulante = value.estadoPrePostulante

        return model

    }


}
