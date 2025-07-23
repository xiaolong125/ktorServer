package db

import Books
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object Database {

    fun initDatabase() {

        Database.connect(
            url = "jdbc:mysql://localhost:3306/bookdb?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true",
            driver = "com.mysql.cj.jdbc.Driver",
            user = "ktoruser",
            password = "ktorpass"
        )

        transaction {
            SchemaUtils.create(Books)
        }
    }
}