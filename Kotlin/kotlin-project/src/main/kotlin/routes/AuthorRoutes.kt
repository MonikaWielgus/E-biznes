package routes

import com.example.models.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.authorRoutes() {
    route("/author") {
        get {
            if (authorStorage.isNotEmpty()) {
                call.respond(authorStorage)
            }
            else {
                call.respondText("No authors found", status = HttpStatusCode.OK)
            }
        }
        get("{firstName?}/{lastName}") {
            val name = call.parameters["firstName"] ?: return@get call.respondText(
                "Missing name",
                status = HttpStatusCode.BadRequest
            )
            val surname = call.parameters["lastName"] ?: return@get call.respondText(
                "Missing surname",
                status = HttpStatusCode.BadRequest
            )
            val author = authorStorage.find { it.firstName.equals(name, true) && it.lastName.equals(surname, true)} ?: return@get call.respondText(
                "No author called $name $surname",
                status = HttpStatusCode.NotFound
            )
            call.respond(author)
        }
        post {
            val author = call.receive<Author>()
            authorStorage.add(author)
            call.respondText("Author stored correctly", status = HttpStatusCode.Created)
        }
        put {
            val newAuthor = call.receive<Author>()
            if (authorStorage.removeIf { it.firstName == newAuthor.firstName && it.lastName == newAuthor.lastName }) {
                authorStorage.add(newAuthor)
                call.respondText("Author updated correctly", status = HttpStatusCode.Accepted)
            } else {
                call.respondText("Not Found", status = HttpStatusCode.NotFound)
            }
        }
        delete("{firstName?}/{lastName?}") {
            val name = call.parameters["firstName"] ?: return@delete call.respondText(
                "Missing name",
                status = HttpStatusCode.BadRequest
            )
            val surname = call.parameters["lastName"] ?: return@delete call.respondText(
                "Missing surname",
                status = HttpStatusCode.BadRequest
            )
            if (authorStorage.removeIf { it.firstName == name && it.lastName == surname }) {
                call.respondText("Author removed correctly", status = HttpStatusCode.Accepted)
            } else {
                call.respondText("Not Found", status = HttpStatusCode.NotFound)
            }
        }
    }
}