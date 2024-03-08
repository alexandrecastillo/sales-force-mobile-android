package biz.belcorp.salesforce.modules.developmentpath.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import biz.belcorp.salesforce.modules.developmentpath.R
import kotlinx.android.synthetic.main.item_main_spinner.view.*
import kotlinx.android.synthetic.main.item_main_spinner_desplegado.view.*

class MainSpinner @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : Spinner(context, attrs, defStyleAttr, MODE_DROPDOWN) {

    init {
        setPopupBackgroundResource(R.drawable.shadow_spinner_popup)
    }

    fun actualizar(strings: List<String>) {
        adapter = MainSpinnerAdapter(context, strings)
    }
}

class MainSpinnerAdapter(
    context: Context,
    private val strings: List<String>
) : ArrayAdapter<String>(context, 0, strings) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val viewHolder = reciclarViewHolderContraido(convertView, parent)
        viewHolder.titulo.text = strings[position]
        return viewHolder.view
    }

    private fun reciclarViewHolderContraido(
        convertView: View?,
        parent: ViewGroup
    ): ViewHolderContraido {
        lateinit var holder: ViewHolderContraido
        if (convertView == null) {
            val view = LayoutInflater
                .from(parent.context)
                .inflate(
                    R.layout.item_main_spinner,
                    parent, false
                )
            holder = ViewHolderContraido(view)
            holder.view.tag = holder
        } else {
            holder = (convertView.tag as ViewHolderContraido)
        }
        return holder
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val viewHolder = reciclarViewHolderDesplegado(convertView, parent)
        viewHolder.titulo.text = strings[position]
        viewHolder.separador.visibility = visibilidadDeLineaSegunPosicion(position)
        return viewHolder.view
    }

    private fun reciclarViewHolderDesplegado(
        convertView: View?,
        parent: ViewGroup
    ): ViewHolderDesplegado {
        lateinit var holder: ViewHolderDesplegado
        if (convertView == null) {
            val view = LayoutInflater
                .from(parent.context)
                .inflate(
                    R.layout.item_main_spinner_desplegado,
                    parent, false
                )
            holder = ViewHolderDesplegado(view)
            holder.view.tag = holder
        } else {
            holder = (convertView.tag as ViewHolderDesplegado)
        }
        return holder
    }

    private fun visibilidadDeLineaSegunPosicion(pos: Int): Int {
        return if (pos == strings.size - 1) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }

    private class ViewHolderContraido(val view: View) {
        val titulo: TextView = view.textViewTitulo
    }

    private class ViewHolderDesplegado(val view: View) {
        val titulo: TextView = view.textViewTituloDesplegado
        val separador: View = view.viewSeparador
    }
}
