package de.themagicmax.weblink_crawler

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import org.slf4j.LoggerFactory
import java.util.concurrent.atomic.AtomicInteger

class Crawler(var links: HashSet<String>) {

    val logger = LoggerFactory.getLogger(javaClass)
    val MAX_DEPTH = 5

    fun getLinkedPages(url: String, depth: AtomicInteger) {
        try {

            if (!links.contains(url) && depth.get() <= MAX_DEPTH) {
                if (links.add(url)) {
                    logger.info("URL: $url")
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
}
