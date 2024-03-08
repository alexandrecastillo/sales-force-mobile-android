package biz.belcorp.salesforce.modules.postulants.features.indicador.components.listado.adapters

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.core.domain.entities.configuration.Configuration
import biz.belcorp.salesforce.core.domain.entities.session.Sesion
import biz.belcorp.salesforce.core.utils.inflate
import biz.belcorp.salesforce.modules.postulants.R
import biz.belcorp.salesforce.modules.postulants.core.domain.enums.UneteAccionFFVV
import biz.belcorp.salesforce.modules.postulants.core.domain.enums.UneteAprobadaFiltro
import biz.belcorp.salesforce.modules.postulants.core.domain.enums.UneteEstado
import biz.belcorp.salesforce.modules.postulants.core.domain.enums.UneteListado
import biz.belcorp.salesforce.modules.postulants.features.entities.BasePostulante
import biz.belcorp.salesforce.modules.postulants.features.entities.PostulanteModel
import biz.belcorp.salesforce.modules.postulants.features.entities.PrePostulanteModel
import biz.belcorp.salesforce.modules.postulants.features.entities.UneteConfiguracionModel
import biz.belcorp.salesforce.modules.postulants.features.indicador.components.listado.adapters.holders.*
import biz.belcorp.salesforce.modules.postulants.utils.Constant
import biz.belcorp.salesforce.modules.postulants.utils.toLowerCase

class PostulantAdapter<T : BasePostulante>(
    private var items: List<T>,
    val view: PostulanteAdapterView? = null,
    val context: Context? = null,
    val isGzOrGr: Boolean = false,
    val user: Sesion,
    val configuration: Configuration
) : RecyclerView.Adapter<BaseHolder>() {

    var buttonTitle: String = Constant.EMPTY_STRING
    var listener: EvaluacionAdapterListener? = null
    var isRegularizarDocumento: Boolean = false
    private var originalList: MutableList<T> = arrayListOf()

    private var focusEnabled = false
    var rol: String = Constant.EMPTY_STRING

    init {
        originalList.addAll(items)
    }

    fun filter(campaignId: Int?, paymentType: Int) {

        items =
            if (campaignId == Constant.NONE_LEVEL_NUMBER && paymentType == UneteAprobadaFiltro.TODOSTIPODEPAGO.code)
                originalList
            else {
                originalList.filter {
                    when (it) {
                        is PostulanteModel -> filtroPostulante(paymentType, it, campaignId)
                        is PrePostulanteModel -> filtroPrepostulante()
                        else -> true
                    }
                }
            }

        view?.adapterSetCount()
        notifyDataSetChanged()
    }

    fun filterSearch(search: String) {

        items = originalList.filter {
            buscarNombre(it, search)
        }

        notifyDataSetChanged()
    }

    private fun buscarNombre(it: BasePostulante, search: String): Boolean {
        val nombreCompleto =
            context?.getString(R.string.two_spaces, it.primerNombre, it.apellidoPaterno)
        return nombreCompleto?.toLowerCase().orEmpty().contains(search.toLowerCase())
    }

    private fun filtroPostulante(paymentType: Int, it: PostulanteModel, campaignId: Int?): Boolean {
        return when {
            paymentType == UneteAprobadaFiltro.TODOSTIPODEPAGO.code ->
                it.campaniaDeIngreso == campaignId
            campaignId == Constant.NONE_LEVEL_NUMBER ->
                it.tipoPago == paymentType
            else ->
                it.tipoPago == paymentType && it.campaniaDeIngreso == campaignId
        }
    }

    private fun filtroPrepostulante(): Boolean {
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder {
        return getHolder(parent, viewType)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: BaseHolder, position: Int) {
        holder.bind(items[position], focusEnabled)
    }

    override fun getItemViewType(position: Int): Int {
        return if (items[position] is PrePostulanteModel) return UneteListado.PRE_INSCRITAS.tipo
        else if (isRegularizarDocumento) return UneteListado.EVALUACION.tipo
        else {
            val item = items[position]

            when (item.estadoPostulante) {
                UneteEstado.EN_APROBACION_FFVV.estado.toString(),
                UneteEstado.PENDIENTE_CAMBIO_MODELO.estado.toString() -> UneteListado.PRE_APROBADOS.tipo

                UneteEstado.GENERANDO_CODIGO.estado.toString(),
                UneteEstado.EN_APROBACION_SAC.estado.toString(),
                UneteEstado.APROBACION_CAMBIO_MODELO.estado.toString()
                -> UneteListado.APROBADOS.tipo

                UneteEstado.PROACTIVO_FINALIZAR_14.estado.toString(),
                UneteEstado.PROACTIVO_FINALIZAR_15.estado.toString(),
                UneteEstado.PROACTIVO_FINALIZAR_16.estado.toString() -> {
                    when (item.subEstadoPostulante) {
                        UneteListado.PROACTIVO_POR_FINALIZAR.tipo -> UneteListado.PROACTIVO_POR_FINALIZAR.tipo

                        else -> {
                            UneteListado.APROBADOS.tipo
                        }
                    }
                }
                UneteEstado.YA_CON_CODIGO.estado.toString() -> {
                    when (item.subEstadoPostulante) {
                        UneteListado.PROACTIVO_FINALIZADO.tipo -> UneteListado.PROACTIVO_FINALIZADO.tipo

                        UneteListado.PROACTIVO_PRE_APROBADOS.tipo -> UneteListado.PROACTIVO_PRE_APROBADOS.tipo

                        else -> {
                            UneteListado.APROBADOS.tipo
                        }
                    }
                }

                UneteEstado.RECHAZADOS.estado.toString() -> UneteListado.RECHAZADOS.tipo

                else -> UneteListado.EVALUACION.tipo

            }
        }
    }

    private fun getHolder(parent: ViewGroup, viewType: Int): BaseHolder {
        return when (viewType) {
            UneteListado.PRE_APROBADOS.tipo -> getPreAprobadaHolder(parent)

            UneteListado.APROBADOS.tipo, UneteListado.INGRESOS_ANTICIPADOS.tipo -> getAprobadaHolder(
                parent
            )
            UneteListado.PRE_INSCRITAS.tipo -> getPreInscritaHolder(parent)

            UneteListado.RECHAZADOS.tipo -> getRechazadaHolder(parent, isGzOrGr)

            UneteListado.PROACTIVO_POR_FINALIZAR.tipo -> getProactivaFinalizarHolder(parent)

            UneteListado.PROACTIVO_FINALIZADO.tipo -> getProactivaFinalizadoHolder(parent)

            UneteListado.PROACTIVO_PRE_APROBADOS.tipo -> getProactivaPreAprobadaHolder(parent, isGzOrGr)

            else -> getEvaluationHolder(parent)
        }
    }

    private fun getEvaluationHolder(parent: ViewGroup): EvaluacionHolder {
        val view = parent.inflate(R.layout.item_unete_evaluacion)
        val holder = EvaluacionHolder(view)
        holder.buttonTitle = buttonTitle
        holder.listener = listener
        holder.rol = rol
        holder.isRegularizarDocumento = isRegularizarDocumento
        return holder
    }

    private fun getPreAprobadaHolder(parent: ViewGroup): PreAprobadaHolder {
        val view = parent.inflate(R.layout.item_unete_preaprobada)
        val holder = PreAprobadaHolder(view)
        holder.buttonTitle = buttonTitle
        holder.listener = listener
        holder.rol = rol
        return holder
    }

    private fun getAprobadaHolder(parent: ViewGroup): AprobadaHolder {
        val view = parent.inflate(R.layout.item_unete_aprobada)
        return AprobadaHolder(view)
    }

    private fun getPreInscritaHolder(parent: ViewGroup): PreInscritaHolder {
        val view = parent.inflate(R.layout.item_unete_pre_inscrito)
        val holder = PreInscritaHolder(view)
        holder.listener = listener
        return holder
    }

    private fun getRechazadaHolder(parent: ViewGroup, isGzOrGr: Boolean = false): RechazadaHolder {
        val view = parent.inflate(R.layout.item_unete_rechazada)
        val holder = RechazadaHolder(view, isGzOrGr)
        holder.listener = listener
        return holder
    }

    private fun getProactivaFinalizadoHolder(parent: ViewGroup): ProactivaFinalizadoHolder {
        val view = parent.inflate(R.layout.item_unete_proactiva_finalizado)
        val holder = ProactivaFinalizadoHolder(view)
        holder.listener = listener
        return holder
    }

    private fun getProactivaPreAprobadaHolder(
        parent: ViewGroup,
        isGzOrGr: Boolean = false
    ): ProactivaPreAprobadoHolder {
        val view = parent.inflate(R.layout.item_unete_proactiva_preaprobado)
        val holder = ProactivaPreAprobadoHolder(view, isGzOrGr)
        holder.listener = listener
        return holder
    }

    private fun getProactivaFinalizarHolder(parent: ViewGroup): ProactivaFinalizarHolder {
        val view = parent.inflate(R.layout.item_unete_proactiva_finalizar)
        val holder = ProactivaFinalizarHolder(view, user, configuration)
        holder.listener = listener
        return holder
    }

    fun activateFocus() {
        focusEnabled = true
        notifyDataSetChanged()
    }

    interface PostulanteAdapterView {
        fun adapterSetCount()
    }

    interface EvaluacionAdapterListener {
        fun updateEstado(accion: UneteAccionFFVV, postulanteModel: PostulanteModel)
        fun updatePreEstado(prePostulanteModel: PrePostulanteModel)
        fun uneteConfiguracion(uneteConfiguracion: UneteConfiguracionModel?)
        fun uneteConfiguracion(): UneteConfiguracionModel?
        fun devueltoSac(solicitudPostulanteId: Int)
        fun showRespuestaCondicion(observacion: Int, valido: Boolean)
        fun showValidacionGZ(postulanteModel: PostulanteModel)
        fun postulanteNoIntersada(postulanteModel: PostulanteModel)

        fun openWhatsapp(postulanteModel: PostulanteModel)
        fun onProactivaPreAprobadaUpdateEstado(status: Int, postulanteModel: PostulanteModel)
    }

}
