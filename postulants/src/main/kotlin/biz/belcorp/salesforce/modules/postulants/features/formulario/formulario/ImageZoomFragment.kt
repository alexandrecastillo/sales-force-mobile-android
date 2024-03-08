package biz.belcorp.salesforce.modules.postulants.features.formulario.formulario

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import biz.belcorp.salesforce.modules.postulants.R
import biz.belcorp.salesforce.modules.postulants.utils.fitFullScreen
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_multimedia_zoom.*

class ImageZoomFragment : DialogFragment() {

    companion object {
        const val TAG = "ImageZoom"
        const val IMAGEN_RUTA = "IMAGEN_RUTA"
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_multimedia_zoom, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imagenRuta = arguments?.get(IMAGEN_RUTA)

        Glide.with(context!!)
            .load(imagenRuta)
            .into(iv_photo_documento_unete)

        iv_close.setOnClickListener {
            dialog?.dismiss()
        }

    }

    override fun onStart() {
        super.onStart()
        fitFullScreen()
    }

}
