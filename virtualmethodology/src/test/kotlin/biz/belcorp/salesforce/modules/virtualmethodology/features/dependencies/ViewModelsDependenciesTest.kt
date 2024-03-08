package biz.belcorp.salesforce.modules.virtualmethodology.features.dependencies

import biz.belcorp.salesforce.core.utils.get
import biz.belcorp.salesforce.modules.virtualmethodology.base.BaseDependenciesTest
import biz.belcorp.salesforce.modules.virtualmethodology.features.contacts.ListContactsViewModel
import org.amshove.kluent.shouldNotBeNull
import org.junit.Test


class ViewModelsDependenciesTest : BaseDependenciesTest() {

    @Test
    fun `resolviendo dependencias para ShortcutsViewModel`() {
        get<ListContactsViewModel>().shouldNotBeNull()
    }

}
