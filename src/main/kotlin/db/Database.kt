package db

import Books
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object Database {

    fun initDatabase() {

        val dbPort = System.getenv("DB_PORT") ?: "3306"
        val dbName = System.getenv("DB_NAME") ?: "bookdb"
        val dbUser = System.getenv("DB_USER") ?: "ktoruser"
        val dbPassword = System.getenv("DB_PASSWORD") ?: "ktorpass"

        val jdbcUrl = "jdbc:mysql://mysql:$dbPort/$dbName?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC"


        Database.connect(
            jdbcUrl,
            driver = "com.mysql.cj.jdbc.Driver",
            user = dbUser,
            password = dbPassword
        )

        transaction {
            SchemaUtils.create(Books)
        }
    }
}