package biz.belcorp.salesforce.modules.developmentpath.features.resultadovisitas

import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.ConsultoraRdd

data class ConsultoraModel(val id: Long,
                           val codigo: String,
                           val rol: Rol,
                           val nombre: String,
                           val codigoMasDigito: String,
                           val telefono: String?,
                           val mostrarPedido: Boolean,
                           val montoPedido: String,
                           val tipo: ConsultoraRdd.Tipo,
                           val segmento: String?,
                           val cantidadProductoPPU: Int,
                           val mostrarPPU : Boolean)
