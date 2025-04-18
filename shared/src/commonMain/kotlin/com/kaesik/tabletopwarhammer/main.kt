package com.kaesik.tabletopwarhammer

import com.kaesik.tabletopwarhammer.core.data.library.LibraryEnum
import com.kaesik.tabletopwarhammer.library.data.library.KtorLibraryClient
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val client = KtorLibraryClient()

    try {
        val attributes = client.getLibraryList(LibraryEnum.ATTRIBUTE)
        println("📜 Otrzymane atrybuty: $attributes")
    } catch (e: Exception) {
        println("❌ Wystąpił błąd: ${e.message}")
    }
}
