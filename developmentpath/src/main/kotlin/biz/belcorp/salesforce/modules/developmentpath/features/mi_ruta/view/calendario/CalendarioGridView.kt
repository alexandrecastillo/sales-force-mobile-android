package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.view.calendario

import android.content.Context
import android.database.DataSetObserver
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.GridView
import android.widget.ImageButton
import android.widget.ListAdapter
import biz.belcorp.salesforce.core.utils.getCompatDrawable
import biz.belcorp.salesforce.modules.developmentpath.R

class CalendarioGridView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    GridView(context, attrs, defStyleAttr), CalendarioTouchListener.OnMovementListener {

    var indiceSeleccionado: Int = -1

    private val scrollSeleccionado: Int
        get() {
            return if (indiceSeleccionado < 0) {
                0
            } else {
                indiceSeleccionado
            }
        }

    private var botonExpandir: ImageButton? = null
    private var expandido: Boolean = false
    private val touchListener = CalendarioTouchListener(this)

    private var necesitaComprimir = true

    init {
        this.setOnTouchListener(touchListener)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        verificarSiComprimir()
    }

    override fun setAdapter(adapter: ListAdapter) {
        adapter.registerDataSetObserver(alCambiarDataSeleccionarSemanaActual())
        super.setAdapter(adapter)
    }

    private fun alCambiarDataSeleccionarSemanaActual(): DataSetObserver {
        return object : DataSetObserver() {
            override fun onChanged() {
                if (!expandido) {
                    scrollearADiaActual()
                }
            }
        }
    }

    private fun verificarSiComprimir() {
        if (necesitaComprimir) {
            comprimir()
            necesitaComprimir = false
        }
    }

    private fun comprimir() {
        val layoutParams = this.layoutParams
        layoutParams.height = obtenerAltoDeFila()
        this.layoutParams = layoutParams

        scrollearADiaActual()

        val image = context.getCompatDrawable(R.drawable.ic_expand_more_blue)
        botonExpandir?.setImageDrawable(image)

        this.expandido = false
    }

    fun scrollearADiaActual() {
        this.post {
            this.smoothScrollToPosition(scrollSeleccionado)
            setSelection(scrollSeleccionado)
        }
    }

    // Sólo funciona porque los items son cuadrados perfectos
    private fun obtenerAltoDeFila(): Int = this.columnWidth

    private fun expandir() {
        val layoutParams = this.layoutParams

        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
        this.layoutParams = layoutParams

        val image = context.getCompatDrawable(R.drawable.ic_expand_less_blue)
        botonExpandir?.setImageDrawable(image)

        this.expandido = true
    }

    fun agregarBotonExpandir(boton: ImageButton) {
        botonExpandir = boton
        botonExpandir?.setOnClickListener {
            if (expandido) {
                comprimir()
            } else {
                expandir()
            }
        }
    }

    override fun onScrollUp() {
        comprimir()
    }

    override fun onScrollDown() {
        expandir()
    }
}
