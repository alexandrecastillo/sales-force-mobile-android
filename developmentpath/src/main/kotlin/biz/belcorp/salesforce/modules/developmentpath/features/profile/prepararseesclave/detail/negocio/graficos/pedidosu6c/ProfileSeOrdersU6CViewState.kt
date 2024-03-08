package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.pedidosu6c


open class ProfileSeOrdersU6CViewState {
    class Success(val data: OrderU6CGraphicModel) : ProfileSeOrdersU6CViewState()
    class Failure(val message: String) : ProfileSeOrdersU6CViewState()
}
