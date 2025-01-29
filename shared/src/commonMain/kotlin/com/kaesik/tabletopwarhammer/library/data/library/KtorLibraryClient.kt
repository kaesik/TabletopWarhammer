package com.kaesik.tabletopwarhammer.library.data.library

import com.kaesik.tabletopwarhammer.library.domain.library.LibraryClient
import io.ktor.client.HttpClient

class KtorLibraryClient(
    private val httpClient: HttpClient
): LibraryClient {

}
