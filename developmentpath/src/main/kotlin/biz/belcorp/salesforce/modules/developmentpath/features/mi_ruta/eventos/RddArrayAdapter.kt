package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eventos

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import biz.belcorp.salesforce.modules.developmentpath.R
import kotlinx.android.synthetic.main.item_tipo_evento_spinner.view.*
import kotlinx.android.synthetic.main.item_tipo_evento_spinner_desplegado.view.*

class RddArrayAdapter(context: Context, private val strings: List<String>) :
        ArrayAdapter<String>(context, 0, strings) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val viewHolder = reciclarViewHolderContraido(convertView, parent)

        viewHolder.titulo.text = strings[position]

        return viewHolder.view
    }

    private fun reciclarViewHolderContraido(convertView: View?,
                                            parent: ViewGroup): ViewHolderContraido {
        lateinit var viewHolder: ViewHolderContraido

        if (convertView == null) {
            val view = LayoutInflater
                    .from(parent.context)
                    .inflate(
                        R.layout.item_tipo_evento_spinner,
                             parent, false)

            viewHolder = ViewHolderContraido(view)
            convertView?.tag = viewHolder
        } else {
            viewHolder = (convertView.tag as ViewHolderContraido)
        }

        return viewHolder
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val viewHolder = reciclarViewHolderDesplegado(convertView, parent)

        viewHolder.titulo.text = strings[position]
        viewHolder.separador.visibility = visibilidadDeLineaSegunPosicion(position)

        return viewHolder.view
    }

    private fun reciclarViewHolderDesplegado(convertView: View?,
                                             parent: ViewGroup): ViewHolderDesplegado {
        lateinit var viewHolder: ViewHolderDesplegado

        if (convertView == null) {
            val view = LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.item_tipo_evento_spinner_desplegado,
                             parent, false)

            viewHolder = ViewHolderDesplegado(view)
            viewHolder.view.tag = viewHolder
        } else {
            viewHolder = (convertView.tag as ViewHolderDesplegado)
        }

        return viewHolder
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
