package com.yagnesh.presence.cloud

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.GoTrue
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.Storage

object SupabaseClient {

    val client: SupabaseClient = createSupabaseClient(
        supabaseUrl = "YOUR_PROJECT_URL",
        supabaseKey = "YOUR_ANON_KEY"
    ) {
        install(GoTrue)
        install(Postgrest)
        install(Storage)
    }
}