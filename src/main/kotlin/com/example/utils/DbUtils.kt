package com.example.utils

import com.example.statuspages.InternetServerException
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

suspend fun <T> dbQuery(block: suspend () -> T): T {
    return newSuspendedTransaction(Dispatchers.IO) { block() }
}
