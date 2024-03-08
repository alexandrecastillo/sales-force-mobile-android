package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.graficos.data

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.sql.graficos.ConfiguracionGraficosGrEntity
import biz.belcorp.salesforce.core.entities.sql.graficos.ConfiguracionGraficosGrEntity_Table
import biz.belcorp.salesforce.core.entities.sql.graficos.GraficoGerenteRegionEntity
import biz.belcorp.salesforce.core.entities.sql.graficos.GraficoGerenteRegionEntity_Table
import biz.belcorp.salesforce.core.utils.deleteAll
import biz.belcorp.salesforce.core.utils.doOnCompletable
import biz.belcorp.salesforce.core.utils.doOnSingle
import biz.belcorp.salesforce.core.utils.guionSiNull
import biz.belcorp.salesforce.modules.developmentpath.core.domain.exceptions.GraficoNoEncontradoException
import com.raizlabs.android.dbflow.kotlinextensions.*
import io.reactivex.Completable
import io.reactivex.Single

class GraficosGRLocalDataStore {

    fun obtenerGraficosGrPorUA(llave: LlaveUA): Single<String> {
        return doOnSingle {
            val where = (select from GraficoGerenteRegionEntity::class
                where (GraficoGerenteRegionEntity_Table.Region.withTable() eq llave.codigoRegion)
                and (GraficoGerenteRegionEntity_Table.Zona.withTable() eq llave.codigoZona.guionSiNull())
                and (GraficoGerenteRegionEntity_Table.Seccion.withTable() eq llave.codigoSeccion.guionSiNull()))

            val contenido = where.querySingle()?.contenido
            contenido ?: throw GraficoNoEncontradoException(llave)
        }
    }

    fun actualizarTituloGrafico(
        llave: LlaveUA, tipoGrafico: Int, valor: String
    ) {
        val where = (select from ConfiguracionGraficosGrEntity::class
            where (ConfiguracionGraficosGrEntity_Table.Region.withTable() eq llave.codigoRegion.guionSiNull())
            and (ConfiguracionGraficosGrEntity_Table.Zona.withTable() eq llave.codigoZona.guionSiNull())
            and (ConfiguracionGraficosGrEntity_Table.Seccion.withTable() eq llave.codigoSeccion.guionSiNull())
            and (ConfiguracionGraficosGrEntity_Table.TipoGrafico.withTable() eq tipoGrafico))

        where.querySingle().apply {
            this?.valor = valor
        }?.save()
    }


    fun guardarGraficosPorUA(llave: LlaveUA, graficoGrData: String): Completable {
        return doOnCompletable {
            val grafico = GraficoGerenteRegionEntity()
            grafico.region = llave.codigoRegion
            grafico.zona = llave.codigoZona.guionSiNull()
            grafico.seccion = llave.codigoSeccion.guionSiNull()
            grafico.contenido = graficoGrData
            grafico.save()
        }
    }

    fun obtenerTitulosGraficos(llave: LlaveUA): Single<List<ConfiguracionGraficosGrEntity>> {
        return doOnSingle {
            val where = (select from ConfiguracionGraficosGrEntity::class
                where (ConfiguracionGraficosGrEntity_Table.Region.withTable() eq llave.codigoRegion.guionSiNull())
                and (ConfiguracionGraficosGrEntity_Table.Zona.withTable() eq llave.codigoZona.guionSiNull())
                and (ConfiguracionGraficosGrEntity_Table.Seccion.withTable() eq llave.codigoSeccion.guionSiNull()))
                .orderBy(ConfiguracionGraficosGrEntity_Table.Orden, true)

            where.queryList()
        }
    }

    fun borrarDatosPrevios(): Completable {
        return doOnCompletable {
            GraficoGerenteRegionEntity().deleteAll()
        }
    }
}
