package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.view.foco

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.core.domain.foco.FocoModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_mi_ruta_detalle_foco.*

class MiRutaDetalleFocoFragment : BaseFragment() {
    override fun getLayout() = R.layout.fragment_mi_ruta_detalle_foco

    private lateinit var foco: FocoModel

    companion object {
        private const val SEGMENTO_ID = "SEGMENTO_ID"

        fun newInstance(foco: FocoModel): MiRutaDetalleFocoFragment {
            val args = Bundle()
            val fragment = MiRutaDetalleFocoFragment()
            args.putSerializable(SEGMENTO_ID, foco)
            fragment.arguments = args

            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        foco = arguments!!.getSerializable(MiRutaDetalleFocoFragment.SEGMENTO_ID) as FocoModel
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(
                R.layout.fragment_mi_ruta_detalle_foco,
                container,
                false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mostrarDetalle()
    }

    private fun mostrarDetalle() {
        Glide.with(iv_detail_focus.context)
                .load(foco.rutaImagen)
                .into(iv_detail_focus)

        tv_detail_content_focus.text = foco.descripcion
        tv_detail_subcontent_focus.text = foco.descripcionDetalle
    }

}
