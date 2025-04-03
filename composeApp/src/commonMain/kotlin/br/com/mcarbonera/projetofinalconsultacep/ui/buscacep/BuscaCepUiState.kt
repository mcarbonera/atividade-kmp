package br.com.mcarbonera.projetofinalconsultacep.ui.buscacep

data class FormField(
    val value: String = "",
    val errorMessage: String? = null
)

data class BuscaCepState(
    val cep: FormField = FormField(),
    val logradouro: FormField = FormField(),
    val bairro: FormField = FormField(),
    val localidade: FormField = FormField(),
    val uf: FormField = FormField(),
    val buscandoCep: Boolean = false
) {
    val isValid get(): Boolean = cep.errorMessage == null &&
            cep.value.isNotEmpty()
}

data class BuscaCepUiState(
    val carregando: Boolean = false,
    val ocorreuErroAoCarregar: Boolean = false,
    val carregou: Boolean = false,
    val buscaCepState: BuscaCepState = BuscaCepState()
) {
    val sucessoAoCarregar get(): Boolean = carregou && !carregando && !ocorreuErroAoCarregar
}