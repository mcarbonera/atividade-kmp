package br.com.mcarbonera.projetofinalconsultacep.data.repository

import br.com.mcarbonera.projetofinalconsultacep.data.model.Cep
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class CepRepository {
    private val client = HttpClient {
        expectSuccess = true
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }

    suspend fun buscarCep(cep: String): Cep =
        client.get("https://viacep.com.br/ws/${cep}/json").body()
}