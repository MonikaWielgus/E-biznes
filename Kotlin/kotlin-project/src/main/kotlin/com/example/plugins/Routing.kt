package com.example.plugins

import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import routes.authorRoutes
import routes.bookRouting
import routes.readerRouting

fun Application.configureRouting() {

    routing {
        readerRouting()
        bookRouting()
        authorRoutes()
    }
}
