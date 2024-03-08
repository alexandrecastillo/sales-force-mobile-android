package biz.belcorp.salesforce.modules.postulants.features.indicador.components.resumen.adapters

import android.graphics.Typeface
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.core.domain.entities.pais.Pais
import biz.belcorp.salesforce.core.utils.*
import biz.belcorp.salesforce.modules.postulants.R
import biz.belcorp.salesforce.modules.postulants.core.domain.enums.UneteListado
import biz.belcorp.salesforce.modules.postulants.features.indicador.components.resumen.entities.DetalleIndicadorModel
import biz.belcorp.salesforce.modules.postulants.features.indicador.components.resumen.entities.DetalleIndicadorUneteModel
import biz.belcorp.salesforce.modules.postulants.utils.Constant
import kotlinx.android.synthetic.main.detalle_item_unete.view.*
import kotlin.math.max
import biz.belcorp.salesforce.base.R as BaseR

class DetalleUneteAdapter(
    datos: List<DetalleIndicadorModel>,
    listener: DetalleUneteAdapterListener,
    val codigoPais: String
) : RecyclerView.Adapter<DetalleUneteAdapter.DetalleViewHolder>() {

    private var datos: MutableCollection<DetalleIndicadorModel> = ArrayList()
    private var mListener: DetalleUneteAdapterListener

    init {
        val first = DetalleIndicadorUneteModel()
        mListener = listener
        first.codigo = Constant.EMPTY_STRING
        this.datos.add(first)
        this.datos.addAll(datos)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetalleViewHolder {
        val view = parent.inflate(R.layout.detalle_item_unete)
        return DetalleViewHolder(view)
    }

    override fun getItemCount() = datos.size

    override fun onBindViewHolder(holder: DetalleViewHolder, position: Int) {
        val item = datos.elementAt(position) as DetalleIndicadorUneteModel
        item.position = position
        holder.bind(item, mListener, codigoPais)
    }

    class DetalleViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(
            model: DetalleIndicadorUneteModel, mListener: DetalleUneteAdapterListener,
            codPais: String?
        ) {

            setItemValues(model)
            setupHeaderListeners(mListener, codPais)
            itemView.iv_arrow?.gone()

            when (model.position) {
                Constant.NUMERO_CERO -> getPositionZero()
                Constant.NUMERO_UNO -> getPositionOne()
                else -> getOtherPosition(model)
            }
        }

        private fun getOtherPosition(model: DetalleIndicadorUneteModel) {
            setItemsFont(ResourcesCompat.getFont(itemView.context, BaseR.font.mulish_regular))
            setItemValues(model)
        }

        private fun getPositionOne() {
            setItemsFont(ResourcesCompat.getFont(itemView.context, BaseR.font.mulish_regular))
        }

        private fun getPositionZero() {
            itemView.iv_arrow?.invisible()
            setHeaderValues()
            setValuesColor(itemView.context.getCompatColor(R.color.indicator_unete))
            setItemsFont(ResourcesCompat.getFont(itemView.context, BaseR.font.mulish_bold))
        }

        private fun setHeaderValues() = with(itemView) {
            tv_regularizar_documento?.text = context.getString(R.string.regularizar_detail)
            tv_evaluacion?.text = context.getString(R.string.enEvaluacion)
            tv_preaprobada?.text = context.getString(R.string.preAprobadas)
            tv_aprobada?.text = context.getString(R.string.aprobadas)
            tv_ingresos_anticipados?.text = context.getString(R.string.ingAnticipados)
            tv_rechazada?.text = context.getString(R.string.rechazadas)
            tv_pre_inscritos?.text = context.getString(R.string.pre_inscritas)
            tv_proactivos_finalizar?.text = context.getString(R.string.proactivos_finalizar)
            tv_proactivos_finalizados?.text = context.getString(R.string.proactivos_finalizados)
            tv_proactivos_pre_aprobados?.text = context.getString(R.string.proactivos_preaprobadas)
        }

        private fun setValuesColor(color: Int) = with(itemView) {
            tv_regularizar_documento?.setTextColor(color)
            tv_evaluacion?.setTextColor(color)
            tv_preaprobada?.setTextColor(color)
            tv_aprobada?.setTextColor(color)
            tv_ingresos_anticipados?.setTextColor(color)
            tv_rechazada?.setTextColor(color)
            tv_pre_inscritos?.setTextColor(color)
            tv_proactivos_finalizar?.setTextColor(color)
            tv_proactivos_finalizados?.setTextColor(color)
            tv_proactivos_pre_aprobados?.setTextColor(color)
        }

        private fun setItemValues(model: DetalleIndicadorUneteModel) = with(itemView) {
            tv_regularizar_documento?.text = model.regularizarDocumento.toString()
            tv_evaluacion?.text = model.enEvaluacion.toString()
            tv_preaprobada?.text = model.preAprobadas.toString()
            tv_aprobada?.text = model.aprobadas.toString()
            tv_ingresos_anticipados?.text = model.ingresosAnticipados.toString()
            tv_rechazada?.text = model.rechazadas.toString()
            tv_pre_inscritos?.text = model.preInscritas.toString()
            tv_proactivos_finalizar?.text = model.proactivoFinalizar.toString()
            tv_proactivos_finalizados?.text = model.proactivoFinalizados.toString()
            tv_proactivos_pre_aprobados?.text = model.proactivoPreAprobados.toString()
        }

        private fun setItemsFont(font: Typeface?) = with(itemView) {
            tv_regularizar_documento?.typeface = font
            tv_evaluacion?.typeface = font
            tv_preaprobada?.typeface = font
            tv_aprobada?.typeface = font
            tv_ingresos_anticipados?.typeface = font
            tv_rechazada?.typeface = font
            tv_pre_inscritos?.typeface = font
            tv_proactivos_finalizar?.typeface = font
            tv_proactivos_finalizados?.typeface = font
            tv_proactivos_pre_aprobados?.typeface = font
        }

        private fun setupHeaderListeners(
            mListener: DetalleUneteAdapterListener, codPais: String?
        ) = with(itemView) {
            setupHeaderListener(tv_evaluacion, mListener, UneteListado.EVALUACION)
            setupHeaderListener(tv_preaprobada, mListener, UneteListado.PRE_APROBADOS)
            setupHeaderListener(tv_aprobada, mListener, UneteListado.APROBADOS)
            setupHeaderListener(tv_rechazada, mListener, UneteListado.RECHAZADOS)
            setupHeaderListener(
                tv_ingresos_anticipados, mListener, UneteListado.INGRESOS_ANTICIPADOS
            )

            val pais = Pais.find(codPais.orEmpty())
            if (pais == Pais.PUERTORICO) {
                showPreInscrito(mListener)
            } else if (pais != Pais.COLOMBIA) {
                showRegularizarDoc(mListener)
            }

            showProactivos(mListener)
        }

        private fun showProactivos(mListener: DetalleUneteAdapterListener) {
            with(itemView) {
                tv_proactivos_finalizar?.visible()
                tv_proactivos_finalizados?.visible()
                tv_proactivos_pre_aprobados?.visible()

                setupHeaderListener(
                    tv_proactivos_finalizar,
                    mListener,
                    UneteListado.PROACTIVO_POR_FINALIZAR
                )
                setupHeaderListener(
                    tv_proactivos_finalizados,
                    mListener,
                    UneteListado.PROACTIVO_FINALIZADO
                )
                setupHeaderListener(
                    tv_proactivos_pre_aprobados,
                    mListener,
                    UneteListado.PROACTIVO_PRE_APROBADOS
                )
            }

            showRegularizarDoc(mListener)
        }

        private fun showPreInscrito(mListener: DetalleUneteAdapterListener) {
            with(itemView) {
                tv_pre_inscritos?.visible()
                setupHeaderListener(tv_pre_inscritos, mListener, UneteListado.PRE_INSCRITAS)
            }
            showRegularizarDoc(mListener)
        }

        private fun showRegularizarDoc(mListener: DetalleUneteAdapterListener) {
            with(itemView) {
                tv_regularizar_documento?.visible()
                setupHeaderListener(
                    tv_regularizar_documento, mListener, UneteListado.REGULARIZAR_DOCUMENTO
                )
            }
        }

        private fun setupHeaderListener(
            view: View?, mListener: DetalleUneteAdapterListener, uneteListado: UneteListado
        ) {
            view?.setOnClickListener { mListener.onHeaderAdapterClick(uneteListado.tipo) }
        }
    }

    fun autoFit(rvConsolidado: RecyclerView?) {

        val viewTreeObserver = rvConsolidado?.viewTreeObserver

        if (viewTreeObserver?.isAlive == true) {

            viewTreeObserver.addOnGlobalLayoutListener(object :
                ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    viewTreeObserver.removeOnGlobalLayoutListener(this)

                    val headers = IntArray(9)
                    val columns = IntArray(9)
                    val positions = IntArray(9) { 1 }

                    if (rvConsolidado.childCount > 0) {
                        iterateChildren(rvConsolidado, headers, columns, positions)
                    }
                    iterateHeaders(headers, columns)
                    iterateDataRows(rvConsolidado, headers, columns)
                }
            })
        }
    }

    private fun iterateChildren(
        rvConsolidado: RecyclerView, headers: IntArray, columns: IntArray, positions: IntArray
    ) {
        for (i in 0 until rvConsolidado.childCount) {
            val viewGroup = (rvConsolidado.getChildAt(i) as ViewGroup)
                .getChildAt(1) as ViewGroup
            if (i == 0) {
                setHeaderSize(viewGroup, headers)
            } else {
                iterateColumns(viewGroup, columns, positions)
            }
        }
    }

    private fun setHeaderSize(viewGroup: ViewGroup, headers: IntArray) {
        val size = headers.size
        for (i in 0 until size) {
            headers[i] = viewGroup.getChildAt(i).width
        }
    }

    private fun iterateHeaders(headers: IntArray, columns: IntArray) {
        val size = headers.size
        for (i in 0 until size) {
            headers[i] = max(headers[i], columns[i])
        }
    }

    private fun iterateColumns(viewGroup: ViewGroup, columns: IntArray, positions: IntArray) {
        val size = columns.size
        for (i in 0 until size) {
            val childWidth = viewGroup.getChildAt(i).width
            positions[i] = if (childWidth > columns[i]) i else positions[i]
            columns[i] = max(childWidth, columns[i])
        }
    }

    private fun iterateDataRows(rvConsolidado: RecyclerView, headers: IntArray, columns: IntArray) {
        val relatives = arrayListOf<RelativeLayout>()
        val texts = arrayListOf<TextView>()

        for (i in 0 until rvConsolidado.childCount) {
            val viewGroup = (rvConsolidado.getChildAt(i) as ViewGroup).getChildAt(1) as ViewGroup
            createRelatives(viewGroup, relatives, headers)
            createTexts(relatives, texts)
            if (i == 0) {
                updateTextProperties(texts, headers, centered = true)
            } else {
                updateTextProperties(texts, columns, centered = false)
            }
            clear(relatives, texts)
        }
    }

    private fun createRelatives(
        viewGroup: ViewGroup,
        relatives: ArrayList<RelativeLayout>,
        headers: IntArray
    ) {
        val size = headers.size
        for (i in 0 until size) {
            val relative = (viewGroup.getChildAt(i) as RelativeLayout)
                .apply { minimumWidth = headers[i] }
            relatives.add(relative)
        }
    }

    private fun createTexts(relatives: List<RelativeLayout>, texts: ArrayList<TextView>) {
        for (relative in relatives) {
            texts.add(relative.getChildAt(0) as TextView)
        }
    }

    private fun updateTextProperties(
        texts: ArrayList<TextView>, dimension: IntArray, centered: Boolean = false
    ) {
        val size = dimension.size
        for (i in 0 until size) {
            texts[i].apply {
                width = dimension[i]
                gravity = if (centered) Gravity.CENTER else Gravity.END
            }
        }
    }

    private fun clear(relatives: ArrayList<RelativeLayout>, texts: ArrayList<TextView>) {
        relatives.clear()
        texts.clear()
    }

    interface DetalleUneteAdapterListener {
        fun onHeaderAdapterClick(modo: Int)
    }
}
