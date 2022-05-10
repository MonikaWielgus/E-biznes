package routes

import com.example.models.Reader
import com.example.models.readerStorage
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.readerRouting() {
    route("/reader") {
        get {
            if (readerStorage.isNotEmpty()) {
                call.respond(readerStorage)
            }
            else {
                call.respondText("No readers found", status = HttpStatusCode.OK)
            }
        }
        get("{nick?}") {
            val nick = call.parameters["nick"] ?: return@get call.respondText(
                "Missing nick",
                status = HttpStatusCode.BadRequest
            )
            val reader = readerStorage.find { it.nick == nick} ?: return@get call.respondText(
                "No reader with nick $nick",
                status = HttpStatusCode.NotFound
            )
            call.respond(reader)
        }
        post {
            val reader = call.receive<Reader>()
            readerStorage.add(reader)
            call.respondText("Reader stored correctly", status = HttpStatusCode.Created)
        }
        put {
            val newReader = call.receive<Reader>()
            if (readerStorage.removeIf { it.nick == newReader.nick }) {
                readerStorage.add(newReader)
                call.respondText("Reader updated correctly", status = HttpStatusCode.Accepted)
            } else {
                call.respondText("Not Found", status = HttpStatusCode.NotFound)
            }
        }
        delete("{nick?}") {
            val nick = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest)
            if (readerStorage.removeIf { it.nick == nick }) {
                call.respondText("Reader removed correctly", status = HttpStatusCode.Accepted)
            } else {
                call.respondText("Not Found", status = HttpStatusCode.NotFound)
            }
        }
    }
}