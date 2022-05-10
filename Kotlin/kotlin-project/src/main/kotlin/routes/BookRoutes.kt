package routes

import com.example.models.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.bookRouting() {
    route("/book") {
        get {
            if (bookStorage.isNotEmpty()) {
                call.respond(bookStorage)
            }
            else {
                call.respondText("No books found", status = HttpStatusCode.OK)
            }
        }
        get("{title?}") {
            val title = call.parameters["title"] ?: return@get call.respondText(
                "Missing title",
                status = HttpStatusCode.BadRequest
            )
            val book = bookStorage.find { it.title.equals(title, true)} ?: return@get call.respondText(
                "No book with title $title",
                status = HttpStatusCode.NotFound
            )
            call.respond(book)
        }
        post {
            val book = call.receive<Book>()
            if (book !in bookStorage) {
                bookStorage.add(book)
                call.respondText("Book stored correctly", status = HttpStatusCode.Created)
            }
            else {
                call.respondText("Book already exists", status = HttpStatusCode.OK)
            }
            val author = book.author
            if (author !in authorStorage) {
                authorStorage.add(book.author)
            }
        }
        put {
            val newBook = call.receive<Book>()
            if (bookStorage.removeIf { it.title == newBook.title }) {
                bookStorage.add(newBook)
                val author = newBook.author
                if (author !in authorStorage) {
                    authorStorage.add(author)
                }
                call.respondText("Book updated correctly", status = HttpStatusCode.Accepted)
            } else {
                call.respondText("Not Found", status = HttpStatusCode.NotFound)
            }
        }
        delete("{title?}") {
            val title = call.parameters["title"] ?: return@delete call.respond(HttpStatusCode.BadRequest)
            if (bookStorage.removeIf { it.title == title }) {
                call.respondText("Book removed correctly", status = HttpStatusCode.Accepted)
            } else {
                call.respondText("Not Found", status = HttpStatusCode.NotFound)
            }
        }
    }
}