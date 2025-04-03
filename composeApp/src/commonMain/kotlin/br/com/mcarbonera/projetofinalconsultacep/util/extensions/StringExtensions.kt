package br.com.mcarbonera.projetofinalconsultacep.util.extensions

fun String.formatarCep(): String = this.mapIndexed { index, char ->
    when(index) {
        5 -> "-$char"
        else -> char
    }
}.joinToString("")