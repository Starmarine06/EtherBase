package com.starmarine06.etherbase

import io.supabase.postgrest.Postgrest
import io.supabase.gotrue.GoTrue
import io.supabase.storage.Storage
import io.supabase.supabase.SupabaseClient
import io.supabase.supabase.createSupabaseClient

object SupabaseManager {
    private const val SUPABASE_URL = "https://ijqmvzlirnvelxplkici.supabase.co"
    private const val SUPABASE_ANON_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImlqcW12emxpcm52ZWx4cGxraWNpIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NTgwMTM2MDksImV4cCI6MjA3MzU4OTYwOX0.HN6OalkMWWprXWSKCvj8JxGX2trWLPvfuFGujMpEaAo"

    val supabaseClient: SupabaseClient = createSupabaseClient(
        supabaseUrl = SUPABASE_URL,
        supabaseKey = SUPABASE_ANON_KEY
    )
}