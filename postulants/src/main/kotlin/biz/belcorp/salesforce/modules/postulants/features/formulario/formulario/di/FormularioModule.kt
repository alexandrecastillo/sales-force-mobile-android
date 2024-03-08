package biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.di

import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.UneteDocumentoFactory
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.UnetePrePresenter
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.UnetePresenter
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.base.PostulantTextResolver
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.mapper.PlaceModelDataMapper
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.mapper.TablaLogicaModelDataMapper
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.mapper.TipoMetaModelDataMapper
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.mapper.ValidarPinModelDataMapper
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.postulantes.paso1.UnetePaso1Presenter
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.postulantes.paso2.UnetePaso2Presenter
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.postulantes.paso3.UnetePaso3Presenter
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.postulantes.paso4.UnetePaso4Presenter
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.postulantes.paso5.UnetePaso5Presenter
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.prepostulantes.paso1.UnetePrePaso1Presenter
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.prepostulantes.paso2.UnetePrePaso2Presenter
import biz.belcorp.salesforce.modules.postulants.features.indicador.components.listado.dialog.RechazoPostulantePresenter
import org.koin.dsl.module


val formularioModule = module {

    factory { TablaLogicaModelDataMapper() }
    factory { TipoMetaModelDataMapper() }
    factory { ValidarPinModelDataMapper() }

    factory { PlaceModelDataMapper() }

    factory { RechazoPostulantePresenter(get(), get(), get()) }
    factory { UnetePresenter(get(), get(), get(), get(), get(), get(), get()) }
    factory { UnetePrePresenter(get(), get(), get(), get(), get()) }


    factory {
        UnetePrePaso1Presenter(
            get(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get()
        )
    }
    factory { UnetePrePaso2Presenter(get(), get(), get(), get(), get()) }

    factory {
        UnetePaso1Presenter(
            get(), get(), get(), get(), get(), get(), get(), get(), get(), get()
        )
    }
    factory {
        UnetePaso2Presenter(
            get(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get()
        )
    }
    factory { UnetePaso3Presenter(get(), get(), get(), get(), get(), get()) }
    factory {
        UnetePaso4Presenter(
            get(), get(), get(), get(), get(), get(), get(), get(), get(), get()
        )
    }
    factory { UnetePaso5Presenter(get(), get(), get(), get(), get(), get()) }
    factory { UneteDocumentoFactory(get()) }
    factory { PostulantTextResolver(get()) }
}
