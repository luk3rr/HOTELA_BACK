package com.hotela

import com.hotela.repository.impl.DatabaseIntegrationTestInterface
import io.kotest.common.runBlocking
import org.springframework.r2dbc.core.DatabaseClient

fun DatabaseClient.clearAllTables() =
    runBlocking {
        val excludeTables = setOf("room_type")

        val tableNames =
            this
                .sql("SELECT tablename FROM pg_tables WHERE schemaname = '${DatabaseIntegrationTestInterface.DATABASE_SCHEMA}'")
                .map { row -> row.get("tablename", String::class.java)!! }
                .all()
                .collectList()
                .block()

        if (tableNames.isNullOrEmpty()) {
            return@runBlocking
        }

        val tablesToTruncate = tableNames.filterNot { it in excludeTables }.joinToString(", ") { "\"$it\"" }
        val sql =
            """
            TRUNCATE TABLE $tablesToTruncate RESTART IDENTITY CASCADE;
            """.trimIndent()

        this.sql(sql).then().block()
    }
