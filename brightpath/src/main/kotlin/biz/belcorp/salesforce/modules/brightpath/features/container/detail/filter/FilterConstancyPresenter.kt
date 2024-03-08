package biz.belcorp.salesforce.modules.brightpath.features.container.detail.filter


class FilterConstancyPresenter {

    var view : FilterConstancyView?= null

    fun create(view: FilterConstancyView){ this.view = view }

    fun initView(){ view?.setupRecyclerView() }

}
