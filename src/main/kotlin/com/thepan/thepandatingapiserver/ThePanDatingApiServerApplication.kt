package com.thepan.thepandatingapiserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing // JPA Auditing을 활성화
@SpringBootApplication
class ThePanDatingApiServerApplication
fun main(args: Array<String>) {
    runApplication<ThePanDatingApiServerApplication>(*args)
}
