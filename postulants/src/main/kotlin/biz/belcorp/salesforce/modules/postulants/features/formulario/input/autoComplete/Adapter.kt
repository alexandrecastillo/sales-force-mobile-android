package biz.belcorp.salesforce.modules.postulants.features.formulario.input.autoComplete

import android.content.Context
import android.widget.ArrayAdapter
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.postulantes.paso2.entities.PlaceModel

class Adapter(context: Context, places: List<PlaceModel>) :
    ArrayAdapter<PlaceModel>(
        context, android.R.layout.simple_expandable_list_item_2, android.R.id.text1, places
    )
