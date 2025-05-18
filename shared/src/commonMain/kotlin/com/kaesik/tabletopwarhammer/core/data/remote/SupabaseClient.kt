package com.kaesik.tabletopwarhammer.core.data.remote

import com.kaesik.tabletopwarhammer.library.data.Const
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest

object SupabaseClient {
    val supabaseClient = createSupabaseClient(
        supabaseUrl = Const.SUPABASE_URL,
        supabaseKey = Const.SUPABASE_ANON_KEY
    ) {
        install(Postgrest)
        install(Auth)
    }
}
