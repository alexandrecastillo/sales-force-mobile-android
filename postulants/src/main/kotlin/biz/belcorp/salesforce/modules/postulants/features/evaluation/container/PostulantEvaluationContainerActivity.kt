package biz.belcorp.salesforce.modules.postulants.features.evaluation.container

import android.os.Bundle
import biz.belcorp.salesforce.core.base.BaseActivity
import biz.belcorp.salesforce.core.domain.entities.pais.Pais
import biz.belcorp.salesforce.core.utils.injectActivity
import biz.belcorp.salesforce.modules.postulants.R
import kotlinx.android.synthetic.main.activity_postulant_evaluation.*
import biz.belcorp.salesforce.modules.postulants.features.evaluation.cl.PostulantEvaluationFragment as postulantEvaluationCL
import biz.belcorp.salesforce.modules.postulants.features.evaluation.co.PostulantEvaluationFragment as postulantEvaluationCO
import biz.belcorp.salesforce.modules.postulants.features.evaluation.pe.PostulantEvaluationFragment as postulantEvaluationPE

class PostulantEvaluationContainerActivity : BaseActivity(), PostulantEvaluationContainerView {

    val presenter: PostulantEvaluationContainerPresenter by injectActivity()

    override fun getLayout(): Int {
        return R.layout.activity_postulant_evaluation
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btnBack.setOnClickListener {
            finish()
        }

        presenter.setView(this)
        presenter.getForm()
    }

    override fun loadForm(countryIso: String) {
        when (countryIso) {
            Pais.COLOMBIA.codigoIso -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, postulantEvaluationCO()).commit()
            }

            Pais.CHILE.codigoIso -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, postulantEvaluationCL()).commit()
            }

            Pais.PERU.codigoIso -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, postulantEvaluationPE()).commit()
            }
        }
    }
}
