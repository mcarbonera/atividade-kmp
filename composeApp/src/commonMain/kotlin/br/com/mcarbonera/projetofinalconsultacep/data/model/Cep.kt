package br.com.mcarbonera.projetofinalconsultacep.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Cep(
    val cep: String = "",
    val logradouro: String = "",
    val bairro: String = "",
    val localidade: String = "",
    val uf: String = ""
)