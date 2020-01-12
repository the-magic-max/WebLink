package de.themagicmax.weblink_crawler

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import simplechat.config.Settings.BANNER
import java.util.concurrent.atomic.AtomicInteger

@SpringBootApplication
class WeblinkCrawlerApplication

fun main(args: Array<String>) {
	runApplication<WeblinkCrawlerApplication>(*args) {
		setBanner(BANNER)
	}

	val crawler = Crawler(HashSet())

	crawler.getLinkedPages("https://www.google.de", AtomicInteger(0))
}
