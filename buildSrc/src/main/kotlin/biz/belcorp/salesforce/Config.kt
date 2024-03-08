package biz.belcorp.salesforce

object Config {
    const val packageName = "biz.belcorp.salesforce"
    const val applicationId = "biz.belcorp.salesforce"
    const val name = "AppFFVV"
    const val testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    object App {
        val isCiServer = System.getenv().containsKey("CI")
    }

    object Signing {
        const val env = "config/key.properties"
        const val storeFile = "store.file"
        const val storePassword = "store.password"
        const val keyAlias = "key.alias"
        const val keyPassword = "key.password"
    }

    object Repository {
        const val jitpack = "https://jitpack.io"
        const val google = "https://maven.google.com/"
        const val belcorp = "http://artifacts.belcorp.biz/repository/maven-public/"
        //const val kotlinx = "https://kotlin.bintray.com/kotlinx"
        const val sonar = "https://oss.sonatype.org/content/repositories/snapshots"
    }

    object Android {
        const val dbPath = "schemas/db.json"
    }

    object Packaging {
        val excludes = arrayOf(
            "LICENSE.txt",
            "META-INF/DEPENDENCIES",
            "META-INF/ASL2.0",
            "META-INF/NOTICE",
            "META-INF/LICENSE"
        )
    }
}
