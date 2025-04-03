package br.com.mcarbonera.projetofinalconsultacep

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform