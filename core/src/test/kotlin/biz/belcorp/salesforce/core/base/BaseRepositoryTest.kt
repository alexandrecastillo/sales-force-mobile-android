package biz.belcorp.salesforce.core.base

import biz.belcorp.salesforce.core.db.objectbox.ObjectBox.boxStore
import biz.belcorp.salesforce.core.entities.MyObjectBox
import io.objectbox.BoxStore
import io.objectbox.DebugFlags
import org.junit.After
import org.junit.Before
import java.io.File


abstract class BaseRepositoryTest {

    companion object {

        val TEST_DIRECTORY by lazy { File("objectbox/test-db") }

    }

    @Before
    @Throws(Exception::class)
    fun setUp() {
        BoxStore.deleteAllFiles(TEST_DIRECTORY)
        boxStore = MyObjectBox.builder()
            .directory(TEST_DIRECTORY)
            .debugFlags(DebugFlags.LOG_QUERIES or DebugFlags.LOG_QUERY_PARAMETERS)
            .build()
    }

    @After
    @Throws(Exception::class)
    fun tearDown() {
        boxStore.close()
        BoxStore.deleteAllFiles(TEST_DIRECTORY)
    }

}
