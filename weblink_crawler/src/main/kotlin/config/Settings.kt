package simplechat.config

import org.springframework.boot.Banner
import org.springframework.boot.SpringBootVersion
import org.springframework.core.SpringVersion
import java.net.InetAddress

object Settings {

    private const val version = "1.0.0-SNAPSHOT"

    val BANNER = Banner { _, _, out ->
        out.println(
            """
            |
            |WebLink Crawler
            |
            |Version          $version
            |Spring Boot      ${SpringBootVersion.getVersion()}
            |Spring Framework ${SpringVersion.getVersion()}
            |Kotlin           ${KotlinVersion.CURRENT}
            |OpenJDK          ${System.getProperty("java.runtime.version")}
            |Betriebssystem   ${System.getProperty("os.name")}
            |Rechnername      ${InetAddress.getLocalHost().hostName}
            |""".trimMargin("|")
        )
    }


}
