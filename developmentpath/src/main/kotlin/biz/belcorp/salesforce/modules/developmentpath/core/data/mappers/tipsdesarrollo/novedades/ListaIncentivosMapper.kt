package biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.tipsdesarrollo.novedades

import biz.belcorp.salesforce.core.entities.sql.novedades.NovedadesEntity
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.novedades.Documento

class ListaIncentivosMapper{

    fun parse(documentos: List<NovedadesEntity>): List<Documento> {
        return documentos.map { parseGrupo(it)}
    }

    fun parseGrupo(documento: NovedadesEntity): Documento {
        val titulo = documento.titulo.orEmpty()
        val nombreDocumento = documento.texto.orEmpty()
        val url = documento.url.orEmpty()
        return Documento(titulo,nombreDocumento, url)
    }
}
