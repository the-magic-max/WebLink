package de.themagicmax.weblink_crawler

import com.squareup.okhttp.MediaType
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import com.squareup.okhttp.RequestBody
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import org.slf4j.LoggerFactory
import java.util.concurrent.atomic.AtomicInteger

class Crawler(var links: HashSet<String>) {

    val logger = LoggerFactory.getLogger(javaClass)
    val MAX_DEPTH = 5
    val BASE_URL = "http://localhost"
    val PORT = 8445

    fun getLinkedPages(url: String, depth: AtomicInteger) {
        try {

            if (!links.contains(url) && depth.get() <= MAX_DEPTH) {
                if (links.add(url)) {
                    logger.info("URL: $url")
                    try {
                        sendToServer(url)
                    } catch (e: Exception) {
                        logger.info("Problem sending URL $url: ${e.message}")
                    }
                }

                val document: Document = Jsoup.connect(url.toString()).get()

                val pages: Elements = document.select("a[href]")

                depth.getAndAdd(1)
                pages.forEach { page ->
                    getLinkedPages(
                        page.attr("abs:href"),
                        AtomicInteger(depth.get())
                    )
                }
            }
        } catch (e: Exception) {
            logger.info("Exception for URL $url: ${e.message}")
        }
    }

    fun sendToServer(url: String) {
        try {

            val client = OkHttpClient()

            val json = """
                |{ 
                |   "name": "testName", 
                |   "url": "$url",
                |   "links": [
                |        "testLink1",
                |        "testLink2"
                |        ]
                |}""".trimMargin()

            val body = RequestBody.create(MediaType.parse("application/json"), json)

            val request = Request.Builder()
                .url("$BASE_URL:$PORT/add-website")
                .post(body)
                .build()

            val call = client.newCall(request)
            val response = call.execute()

        } catch (e: Exception) {
            logger.info("Failed to send URL $url: ${e.message}")
        }
    }
}
