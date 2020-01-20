package de.themagicmax.weblink_service

import de.themagicmax.weblink_service.Entitys.Website
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class RestService {

    val logger = LoggerFactory.getLogger(javaClass)

    @PostMapping("/add-website")
    fun newWebsite(@RequestBody newWebsite: Website) {
        logger.info(newWebsite.url)
    }
}
