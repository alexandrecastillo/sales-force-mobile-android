package biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.comportamientos.progreso.detalle

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import biz.belcorp.salesforce.core.base.BaseDialogFragment
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.modules.developmentpath.R
import kotlinx.android.synthetic.main.fragment_progreso_reconocimiento.*

class ProgresoReconocimientoFragment : BaseDialogFragment(), ProgresoReconocimientoView {

    private var mProgresoReconocimientoAdapter: ProgresoReconocimientoAdapter? = null
    lateinit var rol: Rol
    var personaId: Long = 0

    private val progresoReconocimientoPresenter by injectFragment<ProgresoReconocimientoPresenter>()

    override fun getLayout(): Int {
        return R.layout.fragment_progreso_reconocimiento
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        personaId = arguments!!.getLong(PERSONA_ID)
        rol = arguments!!.getSerializable(ROL) as Rol
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        iniciarRecuclerView()
        btnBack.setOnClickListener { this.dismiss() }
        progresoReconocimientoPresenter.obtenerComportamientosSeisUltimasCampanas(personaId, rol)
    }

    override fun onStart() {
        super.onStart()
        fitFullScreen()
    }

    private fun iniciarRecuclerView() {
        val mLinearLayoutManager = LinearLayoutManager(activity)
        mLinearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        mLinearLayoutManager.reverseLayout = false
        rclvProgresReconocimiento.layoutManager = mLinearLayoutManager
        rclvProgresReconocimiento.hasFixedSize()
        rclvProgresReconocimiento.isNestedScrollingEnabled = false
    }

    override fun showComportamientosUltimasSeisCampanias(progresos: List<ProgresoModel>) {
        if (context == null) return
        mProgresoReconocimientoAdapter = ProgresoReconocimientoAdapter(progresos, rol)
        rclvProgresReconocimiento?.adapter = mProgresoReconocimientoAdapter
    }

    override fun pintarCampanias(campanias: List<String>) {
        campanias.forEachIndexed { index, campania ->
            if (index >= listaTextView.size) return
            listaTextView[index]?.text = campania
        }
    }

    private val listaTextView get() = listOf(txtCamp1, txtCamp2, txtCamp3, txtCamp4, txtCamp5, txtCamp6)

    class ProgresoModel(
        var iconProgreso: Int = 0,
        var mPorsentage: Int = 0,
        var mDescripcion: String?,
        var mCapania1: Boolean = false,
        var mCapania2: Boolean = false,
        var mCapania3: Boolean = false,
        var mCapania4: Boolean = false,
        var mCapania5: Boolean = false,
        var mCapania6: Boolean = false
    )

    companion object {

        private const val PERSONA_ID = "PERSONA_ID"
        private const val ROL = "ROL"

        fun newInstance(
            personaId: Long,
            rol: Rol
        ): ProgresoReconocimientoFragment {
            val mBundel = Bundle()
            val mFragment = ProgresoReconocimientoFragment()
            mBundel.putLong(PERSONA_ID, personaId)
            mBundel.putSerializable(ROL, rol)
            mFragment.arguments = mBundel

            return mFragment
        }
    }
}
