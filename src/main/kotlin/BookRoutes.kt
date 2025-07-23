import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import model.Book
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

fun Route.bookRoutes() {

    route("/books") {

        // 查询图书列表（分页 + 模糊搜索）
        get {
            val page = call.request.queryParameters["page"]?.toIntOrNull() ?: 1
            val size = call.request.queryParameters["size"]?.toIntOrNull() ?: 10
            val query = call.request.queryParameters["query"]

            val books = transaction {
                val baseQuery = if (!query.isNullOrBlank()) {
                    Books.select { Books.title like "%$query%" }
                } else {
                    Books.selectAll()
                }

                baseQuery
                    .orderBy(Books.id to SortOrder.DESC)
                    .limit(size, offset = ((page - 1) * size).toLong())
                    .map {
                        Book(
                            id = it[Books.id],
                            title = it[Books.title],
                            author = it[Books.author]
                        )
                    }
            }

            call.respond(books)
        }

        // 查询单本图书
        get("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "Invalid ID")
                return@get
            }

            val book = transaction {
                Books.select { Books.id eq id }
                    .map {
                        Book(it[Books.id], it[Books.title], it[Books.author])
                    }
                    .firstOrNull()
            }

            if (book != null) {
                call.respond(book)
            } else {
                call.respond(HttpStatusCode.NotFound, "Book not found")
            }
        }

        // 添加图书
        post {
            val book = call.receive<Book>()
            val id = transaction {
                Books.insert {
                    it[title] = book.title
                    it[author] = book.author
                } get Books.id
            }
            call.respond(HttpStatusCode.Created, book.copy(id = id))
        }

        // 更新图书
        put("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "Invalid ID")
                return@put
            }

            val updatedBook = call.receive<Book>()
            val updated = transaction {
                Books.update({ Books.id eq id }) {
                    it[title] = updatedBook.title
                    it[author] = updatedBook.author
                }
            }

            if (updated > 0) {
                call.respond(HttpStatusCode.OK)
            } else {
                call.respond(HttpStatusCode.NotFound, "Book not found")
            }
        }

        // 删除图书
        delete("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "Invalid ID")
                return@delete
            }

            val deleted = transaction {
                Books.deleteWhere { Books.id eq id }
            }

            if (deleted > 0) {
                call.respond(HttpStatusCode.NoContent)
            } else {
                call.respond(HttpStatusCode.NotFound, "Book not found")
            }
        }
    }
}
