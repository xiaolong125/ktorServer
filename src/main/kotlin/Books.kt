import org.jetbrains.exposed.sql.*


object Books : Table() {
    val id = integer("id").autoIncrement()
    val title = varchar("title", 255)
    val author = varchar("author", 255)
    override val primaryKey = PrimaryKey(id)
}