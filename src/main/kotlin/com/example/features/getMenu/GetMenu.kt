package com.example.features.getMenu

import entities.DBAdapter
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.getMenu() {
    routing {
        get("/menu") {
            val manager = DBAdapter
            call.respond(HttpStatusCode.OK, manager.getMenu())
        }
    }
}