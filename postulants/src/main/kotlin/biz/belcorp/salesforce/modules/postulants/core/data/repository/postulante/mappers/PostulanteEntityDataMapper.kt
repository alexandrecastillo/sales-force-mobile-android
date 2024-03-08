package biz.belcorp.salesforce.modules.postulants.core.data.repository.postulante.mappers

import biz.belcorp.salesforce.core.entities.sql.unete.PostulanteEntity
import biz.belcorp.salesforce.core.utils.Mapper
import biz.belcorp.salesforce.modules.postulants.core.data.network.dto.unete.PostulanteDTO
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.Postulante
import biz.belcorp.salesforce.modules.postulants.utils.Constant
import biz.belcorp.salesforce.modules.postulants.utils.toInt

class PostulanteEntityDataMapper : Mapper<Postulante, PostulanteEntity>() {

    companion object {
        fun map(value: PostulanteDTO?): PostulanteEntity? {
            value?.let {
                val entity = PostulanteEntity()
                entity.apellidoFamiliar = value.apellidoFamiliar
                entity.apellidoMaterno = value.apellidoMaterno
                entity.apellidoMaternoAval = value.apellidoMaternoAval
                entity.apellidoNoFamiliar = value.apellidoNoFamiliar
                entity.apellidoPaterno = value.apellidoPaterno
                entity.apellidoPaternoAval = value.apellidoPaternoAval
                entity.campaniaID = value.campaniaID
                entity.celular = value.celular
                entity.celularAval = value.celularAval
                entity.celularEntrega = value.celularEntrega
                entity.celularFamiliar = value.celularFamiliar
                entity.celularNoFamiliar = value.celularNoFamiliar
                entity.ciudad = value.ciudad
                entity.codigoConsultora = value.codigoConsultora
                entity.codigoConsultoraRecomienda = value.codigoConsultoraRecomienda
                entity.codigoLote = value.codigoLote
                entity.codigoOtrasMarcas = value.codigoOtrasMarcas
                entity.codigoPostal = value.codigoPostal
                entity.codigoPremio = value.codigoPremio
                entity.codigoSeccion = value.codigoSeccion
                entity.codigoTerritorio = value.codigoTerritorio
                entity.codigoUbigeo = value.codigoUbigeo
                entity.codigoZona = value.codigoZona
                entity.correoElectronico = value.correoElectronico
                entity.descripcionMeta = value.descripcionMeta
                entity.direccion = value.direccion
                entity.direccionAval = value.direccionAval
                entity.direccionEntrega = value.direccionEntrega
                entity.direccionFamiliar = value.direccionFamiliar
                entity.direccionNoFamiliar = value.direccionNoFamiliar
                entity.edad = value.edad
                entity.estadoBurocrediticio = value.estadoBurocrediticio
                entity.estadoCivil = value.estadoCivil
                entity.estadoGEO = value.estadoGEO
                entity.estadoPostulante = value.estadoPostulante
                entity.estadoTelefonico = value.estadoTelefonico
                entity.fechaCreacion = value.fechaCreacion
                entity.fechaNacimiento = value.fechaNacimiento
                entity.fechaNacimientoAval = value.fechaNacimientoAval
                entity.fuenteIngreso = value.fuenteIngreso
                entity.indicadorActivo = value.indicadorActivo
                entity.indicadorOptin = value.indicadorOptin.toInt()
                entity.latitud = value.latitud
                entity.longitud = value.longitud
                entity.lugarHijo = value.lugarHijo
                entity.lugarPadre = value.lugarPadre
                entity.montoMeta = value.montoMeta
                entity.nit = value.nit
                entity.nivelEducativo = value.nivelEducativo
                entity.nombreConsultoraRecomienda = value.nombreConsultoraRecomienda
                entity.nombreEmpresaAval = value.nombreEmpresaAval
                entity.nombreFamiliar = value.nombreFamiliar
                entity.nombreNoFamiliar = value.nombreNoFamiliar
                entity.numeroDocumento = value.numeroDocumento
                entity.numeroDocumentoAval = value.numeroDocumentoAval
                entity.numeroDocumentoLegal = value.numeroDocumentoLegal
                entity.numeroPreimpreso = value.numeroPreimpreso
                entity.observacionEntrega = value.observacionEntrega
                entity.origenIngreso = value.origenIngreso?.toIntOrNull()
                entity.paso = value.paso
                entity.paisID = getCountryId(value.pais)
                entity.pais = value.pais
                entity.poblacionVilla = value.poblacionVilla
                entity.primerNombre = value.primerNombre
                entity.primerNombreAval = value.primerNombreAval
                entity.razonSocial = value.razonSocial
                entity.referencia = value.referencia
                entity.referenciaEntrega = value.referenciaEntrega
                entity.requiereAval = value.requiereAval.toInt()
                entity.respuestaGEO = value.respuestaGEO
                entity.regimenContable = value.regimenContable
                entity.segundoNombre = value.segundoNombre
                entity.segundoNombreAval = value.segundoNombreAval
                entity.sexo = value.sexo
                entity.solicitudPostulanteID = value.solicitudPostulanteID
                entity.subEstadoPostulante = value.subEstadoPostulante
                entity.telefono = value.telefono
                entity.telefonoAval = value.telefonoAval
                entity.telefonoEntrega = value.telefonoEntrega
                entity.telefonoFamiliar = value.telefonoFamiliar
                entity.telefonoNoFamiliar = value.telefonoNoFamiliar
                entity.teRecomendoOtraConsultora = value.teRecomendoOtraConsultora
                entity.tieneExperiencia = value.tieneExperiencia
                entity.tipoContacto = value.tipoContacto
                entity.tipoDocumento = value.tipoDocumento
                entity.tipoDocumentoAval = value.tipoDocumentoAval
                entity.tipoDocumentoLegal = value.tipoDocumentoLegal
                entity.tipoMeta = value.tipoMeta
                entity.tipoNacionalidad = value.tipoNacionalidad
                entity.tipoPersona = value.tipoPersona?.toIntOrNull()
                entity.tipoSolicitud = value.tipoSolicitud
                entity.tipoVia = value.tipoVia
                entity.tipoVinculoAval = value.tipoVinculoAval
                entity.tipoVinculoFamiliar = value.tipoVinculoFamiliar
                entity.tipoVinculoNoFamiliar = value.tipoVinculoNoFamiliar
                entity.usuarioCreacion = value.usuarioCreacion
                entity.vistoPorGZ = value.vistoPorGZ.toInt()
                entity.vistoPorSAC = value.vistoPorSAC.toInt()
                entity.vistoPorSE = value.vistoPorSE.toInt()
                entity.fechaEnvio = value.fechaEnvio
                entity.fechaIngreso = value.fechaIngreso
                entity.tipoRechazo = value.tipoRechazo
                entity.motivoRechazo = value.motivoRechazo
                entity.imagenCDD = value.imagenCDD
                entity.imagenIFE = value.imagenIFE
                entity.imagenContrato = value.imagenContrato
                entity.imagenPagare = value.imagenPagare
                entity.imagenDniAval = value.imagenDniAval
                entity.imagenReciboOtraMarca = value.imagenReciboOtraMarca
                entity.imagenReciboPagoAval = value.imagenReciboPagoAval
                entity.imagenCreditoAval = value.imagenCreditoAval
                entity.imagenConstanciaLaboralAval = value.imagenConstanciaLaboralAval
                entity.sincronizado = value.sincronizado == Constant.NUMERO_UNO
                entity.requiereAprobacionSAC = value.requiereAprobacionSAC
                entity.devueltoPorSAC = value.devueltoPorSAC
                entity.ingresoAnticipado = value.ingresoAnticipado
                entity.campaniaDeIngreso = value.campaniaDeIngreso

                entity.vieneDe = value.vieneDe
                entity.tipoPagoNombre = value.tipoPagoNombre
                entity.tipoPago = value.tipoPago
                entity.fechaEnvioValidarPorGZ = value.fechaEnvioValidarPorGZ
                entity.termsAceptados = value.termsAceptados
                entity.UrlFirma = value.UrlFirma
                entity.link = value.link

                return entity
            }
            return null
        }

        private fun getCountryId(country: String?) = country?.toInt() ?: Constant.NUMERO_CERO

        fun map(values: List<PostulanteDTO>): List<PostulanteEntity> {
            return values.map { map(it) as PostulanteEntity }
        }


    }


    override fun map(value: Postulante): PostulanteEntity {

        val entity = PostulanteEntity()

        entity.uuid = value.UUID
        entity.apellidoFamiliar = value.apellidoFamiliar
        entity.apellidoMaterno = value.apellidoMaterno
        entity.apellidoMaternoAval = value.apellidoMaternoAval
        entity.apellidoNoFamiliar = value.apellidoNoFamiliar
        entity.apellidoPaterno = value.apellidoPaterno
        entity.apellidoPaternoAval = value.apellidoPaternoAval
        entity.campaniaID = value.campaniaID.toIntOrNull() ?: Constant.NUMERO_CERO
        entity.celular = value.celular
        entity.celularAval = value.celularAval
        entity.celularEntrega = value.celularEntrega
        entity.celularFamiliar = value.celularFamiliar
        entity.celularNoFamiliar = value.celularNoFamiliar
        entity.ciudad = value.ciudad
        entity.codigoConsultora = value.codigoConsultora
        entity.codigoConsultoraRecomienda = value.codigoConsultoraRecomienda
        entity.codigoLote = value.codigoLote
        entity.codigoOtrasMarcas = value.codigoOtrasMarcas
        entity.codigoPostal = value.codigoPostal
        entity.codigoPremio = value.codigoPremio
        entity.codigoSeccion = value.codigoSeccion
        entity.codigoTerritorio = value.codigoTerritorio
        entity.codigoUbigeo = value.codigoUbigeo
        entity.codigoZona = value.codigoZona
        entity.correoElectronico = value.correoElectronico
        entity.descripcionMeta = value.descripcionMeta
        entity.direccion = value.direccion
        entity.direccionAval = value.direccionAval
        entity.direccionEntrega = value.direccionEntrega
        entity.direccionFamiliar = value.direccionFamiliar
        entity.direccionNoFamiliar = value.direccionNoFamiliar
        entity.edad = value.edad
        entity.estadoBurocrediticio = value.estadoBurocrediticio
        entity.estadoCivil = value.estadoCivil
        entity.estadoGEO = value.estadoGEO
        entity.estadoPostulante = value.estadoPostulante
        entity.estadoTelefonico = value.estadoTelefonico
        entity.fechaCreacion = value.fechaCreacion
        entity.fechaNacimiento = value.fechaNacimiento
        entity.fechaNacimientoAval = value.fechaNacimientoAval
        entity.fuenteIngreso = value.fuenteIngreso
        entity.indicadorActivo = value.indicadorActivo
        entity.indicadorOptin = value.indicadorOptin
        entity.latitud = value.latitud
        entity.longitud = value.longitud
        entity.lugarHijo = value.lugarHijo
        entity.lugarPadre = value.lugarPadre
        entity.montoMeta = value.montoMeta
        entity.nit = value.nit
        entity.nivelEducativo = value.nivelEducativo
        entity.nombreConsultoraRecomienda = value.nombreConsultoraRecomienda
        entity.nombreEmpresaAval = value.nombreEmpresaAval
        entity.nombreFamiliar = value.nombreFamiliar
        entity.nombreNoFamiliar = value.nombreNoFamiliar
        entity.numeroDocumento = value.numeroDocumento
        entity.numeroDocumentoAval = value.numeroDocumentoAval
        entity.numeroDocumentoLegal = value.numeroDocumentoLegal
        entity.numeroPreimpreso = value.numeroPreimpreso
        entity.observacionEntrega = value.observacionEntrega
        entity.origenIngreso = value.origenIngreso
        entity.paso = value.paso
        entity.paisID = value.paisID
        entity.pais = value.pais
        entity.poblacionVilla = value.poblacionVilla
        entity.primerNombre = value.primerNombre
        entity.primerNombreAval = value.primerNombreAval
        entity.razonSocial = value.razonSocial
        entity.referencia = value.referencia
        entity.referenciaEntrega = value.referenciaEntrega
        entity.requiereAval = value.requiereAval
        entity.respuestaGEO = value.respuestaGEO
        entity.regimenContable = value.regimenContable
        entity.segundoNombre = value.segundoNombre
        entity.segundoNombreAval = value.segundoNombreAval
        entity.sexo = value.sexo
        entity.solicitudPostulanteID = value.solicitudPostulanteID
        entity.subEstadoPostulante = value.subEstadoPostulante
        entity.telefono = value.telefono
        entity.telefonoAval = value.telefonoAval
        entity.telefonoEntrega = value.telefonoEntrega
        entity.telefonoFamiliar = value.telefonoFamiliar
        entity.telefonoNoFamiliar = value.telefonoNoFamiliar
        entity.teRecomendoOtraConsultora = value.teRecomendoOtraConsultora
        entity.tieneExperiencia = value.tieneExperiencia
        entity.tipoContacto = value.tipoContacto
        entity.tipoDocumento = value.tipoDocumento
        entity.tipoDocumentoAval = value.tipoDocumentoAval
        entity.tipoDocumentoLegal = value.tipoDocumentoLegal
        entity.tipoMeta = value.tipoMeta
        entity.tipoNacionalidad = value.tipoNacionalidad
        entity.tipoPersona = value.tipoPersona
        entity.tipoSolicitud = value.tipoSolicitud
        entity.tipoVia = value.tipoVia
        entity.tipoVinculoAval = value.tipoVinculoAval
        entity.tipoVinculoFamiliar = value.tipoVinculoFamiliar
        entity.tipoVinculoNoFamiliar = value.tipoVinculoNoFamiliar
        entity.usuarioCreacion = value.usuarioCreacion
        entity.vistoPorGZ = value.vistoPorGZ
        entity.vistoPorSAC = value.vistoPorSAC
        entity.vistoPorSE = value.vistoPorSE
        entity.fechaEnvio = value.fechaEnvio
        entity.fechaIngreso = value.fechaIngreso
        entity.tipoRechazo = value.tipoRechazo
        entity.motivoRechazo = value.motivoRechazo
        entity.imagenCDD = value.imagenCDD
        entity.imagenIFE = value.imagenIFE
        entity.imagenContrato = value.imagenContrato
        entity.imagenPagare = value.imagenPagare
        entity.imagenDniAval = value.imagenDniAval
        entity.imagenReciboOtraMarca = value.imagenReciboOtraMarca
        entity.imagenReciboPagoAval = value.imagenReciboPagoAval
        entity.imagenCreditoAval = value.imagenCreditoAval
        entity.imagenConstanciaLaboralAval = value.imagenConstanciaLaboralAval
        entity.sincronizado = value.sincronizado
        entity.requiereAprobacionSAC = value.requiereAprobacionSAC
        entity.devueltoPorSAC = value.devueltoPorSAC
        entity.ingresoAnticipado = value.ingresoAnticipado
        entity.campaniaDeIngreso = value.campaniaDeIngreso

        entity.vieneDe = value.vieneDe
        entity.tipoPagoNombre = value.tipoPagoNombre
        entity.tipoPago = value.tipoPago
        entity.fechaEnvioValidarPorGZ = value.fechaEnvioValidarPorGZ
        entity.termsAceptados = value.termsAceptados

        entity.ip = value.ip
        entity.soMobile = value.soMobile
        entity.deviceId = value.deviceId
        entity.UrlFirma = value.UrlFirma
        entity.link = value.link
        return entity
    }

    override fun reverseMap(value: PostulanteEntity): Postulante {

        val entity = Postulante()

        entity.UUID = value.uuid
        entity.apellidoFamiliar = value.apellidoFamiliar
        entity.apellidoMaterno = value.apellidoMaterno
        entity.apellidoMaternoAval = value.apellidoMaternoAval
        entity.apellidoNoFamiliar = value.apellidoNoFamiliar
        entity.apellidoPaterno = value.apellidoPaterno
        entity.apellidoPaternoAval = value.apellidoPaternoAval
        entity.campaniaID = value.campaniaID.toString()
        entity.celular = value.celular
        entity.celularAval = value.celularAval
        entity.celularEntrega = value.celularEntrega
        entity.celularFamiliar = value.celularFamiliar
        entity.celularNoFamiliar = value.celularNoFamiliar
        entity.ciudad = value.ciudad
        entity.codigoConsultora = value.codigoConsultora
        entity.codigoConsultoraRecomienda = value.codigoConsultoraRecomienda
        entity.codigoLote = value.codigoLote
        entity.codigoOtrasMarcas = value.codigoOtrasMarcas
        entity.codigoPostal = value.codigoPostal
        entity.codigoPremio = value.codigoPremio
        entity.codigoSeccion = value.codigoSeccion
        entity.codigoTerritorio = value.codigoTerritorio
        entity.codigoUbigeo = value.codigoUbigeo
        entity.codigoZona = value.codigoZona
        entity.correoElectronico = value.correoElectronico
        entity.descripcionMeta = value.descripcionMeta
        entity.direccion = value.direccion
        entity.direccionAval = value.direccionAval
        entity.direccionEntrega = value.direccionEntrega
        entity.direccionFamiliar = value.direccionFamiliar
        entity.direccionNoFamiliar = value.direccionNoFamiliar
        entity.edad = value.edad
        entity.estadoBurocrediticio = value.estadoBurocrediticio
        entity.estadoCivil = value.estadoCivil
        entity.estadoGEO = value.estadoGEO
        entity.estadoPostulante = value.estadoPostulante
        entity.estadoTelefonico = value.estadoTelefonico
        entity.fechaCreacion = value.fechaCreacion
        entity.fechaNacimiento = value.fechaNacimiento
        entity.fechaNacimientoAval = value.fechaNacimientoAval
        entity.fuenteIngreso = value.fuenteIngreso
        entity.indicadorActivo = value.indicadorActivo
        entity.indicadorOptin = value.indicadorOptin
        entity.latitud = value.latitud
        entity.longitud = value.longitud
        entity.lugarHijo = value.lugarHijo
        entity.lugarPadre = value.lugarPadre
        entity.montoMeta = value.montoMeta
        entity.nit = value.nit
        entity.nivelEducativo = value.nivelEducativo
        entity.nombreConsultoraRecomienda = value.nombreConsultoraRecomienda
        entity.nombreEmpresaAval = value.nombreEmpresaAval
        entity.nombreFamiliar = value.nombreFamiliar
        entity.nombreNoFamiliar = value.nombreNoFamiliar
        entity.numeroDocumento = value.numeroDocumento
        entity.numeroDocumentoAval = value.numeroDocumentoAval
        entity.numeroDocumentoLegal = value.numeroDocumentoLegal
        entity.numeroPreimpreso = value.numeroPreimpreso
        entity.observacionEntrega = value.observacionEntrega
        entity.origenIngreso = value.origenIngreso
        entity.paso = value.paso
        entity.paisID = value.paisID
        entity.pais = value.pais
        entity.poblacionVilla = value.poblacionVilla
        entity.primerNombre = value.primerNombre
        entity.primerNombreAval = value.primerNombreAval
        entity.razonSocial = value.razonSocial
        entity.referencia = value.referencia
        entity.referenciaEntrega = value.referenciaEntrega
        entity.requiereAval = value.requiereAval
        entity.respuestaGEO = value.respuestaGEO
        entity.regimenContable = value.regimenContable
        entity.segundoNombre = value.segundoNombre
        entity.segundoNombreAval = value.segundoNombreAval
        entity.sexo = value.sexo
        entity.solicitudPostulanteID = value.solicitudPostulanteID
        entity.subEstadoPostulante = value.subEstadoPostulante
        entity.telefono = value.telefono
        entity.telefonoAval = value.telefonoAval
        entity.telefonoEntrega = value.telefonoEntrega
        entity.telefonoFamiliar = value.telefonoFamiliar
        entity.telefonoNoFamiliar = value.telefonoNoFamiliar
        entity.teRecomendoOtraConsultora = value.teRecomendoOtraConsultora
        entity.tieneExperiencia = value.tieneExperiencia
        entity.tipoContacto = value.tipoContacto
        entity.tipoDocumento = value.tipoDocumento
        entity.tipoDocumentoAval = value.tipoDocumentoAval
        entity.tipoDocumentoLegal = value.tipoDocumentoLegal
        entity.tipoMeta = value.tipoMeta
        entity.tipoNacionalidad = value.tipoNacionalidad
        entity.tipoPersona = value.tipoPersona
        entity.tipoSolicitud = value.tipoSolicitud
        entity.tipoVia = value.tipoVia
        entity.tipoVinculoAval = value.tipoVinculoAval
        entity.tipoVinculoFamiliar = value.tipoVinculoFamiliar
        entity.tipoVinculoNoFamiliar = value.tipoVinculoNoFamiliar
        entity.usuarioCreacion = value.usuarioCreacion
        entity.vistoPorGZ = value.vistoPorGZ
        entity.vistoPorSAC = value.vistoPorSAC
        entity.vistoPorSE = value.vistoPorSE
        entity.fechaEnvio = value.fechaEnvio
        entity.fechaIngreso = value.fechaIngreso
        entity.tipoRechazo = value.tipoRechazo
        entity.motivoRechazo = value.motivoRechazo
        entity.imagenCDD = value.imagenCDD
        entity.imagenIFE = value.imagenIFE
        entity.imagenContrato = value.imagenContrato
        entity.imagenPagare = value.imagenPagare
        entity.imagenDniAval = value.imagenDniAval
        entity.imagenReciboOtraMarca = value.imagenReciboOtraMarca
        entity.imagenReciboPagoAval = value.imagenReciboPagoAval
        entity.imagenCreditoAval = value.imagenCreditoAval
        entity.imagenConstanciaLaboralAval = value.imagenConstanciaLaboralAval
        entity.sincronizado = value.sincronizado
        entity.requiereAprobacionSAC = value.requiereAprobacionSAC
        entity.devueltoPorSAC = value.devueltoPorSAC
        entity.tipoRechazoExplanation = value.tipoRechazoExplanation
        entity.ingresoAnticipado = value.ingresoAnticipado
        entity.campaniaDeIngreso = value.campaniaDeIngreso

        entity.vieneDe = value.vieneDe
        entity.tipoPagoNombre = value.tipoPagoNombre
        entity.tipoPago = value.tipoPago
        entity.fechaEnvioValidarPorGZ = value.fechaEnvioValidarPorGZ
        entity.termsAceptados = value.termsAceptados

        entity.soMobile = value.soMobile
        entity.ip = value.ip
        entity.deviceId = value.deviceId
        entity.UrlFirma = value.UrlFirma
        entity.link = value.link

        return entity
    }

}
