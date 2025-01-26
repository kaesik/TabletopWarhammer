package com.kaesik.tabletopwarhammer

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform