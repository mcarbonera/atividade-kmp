package br.com.mcarbonera.projetofinalconsultacep.ui.buscacep

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.mcarbonera.projetofinalconsultacep.data.model.Cep
import br.com.mcarbonera.projetofinalconsultacep.data.repository.CepRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class BuscaCepViewModel(
    private val cepRepository: CepRepository
): ViewModel() {
    private val tag: String = "BuscaCepViewModel"

    var uiState: BuscaCepUiState by mutableStateOf(BuscaCepUiState())
        private set

    fun onClickBuscar() {
        val cepParaBuscar = uiState.buscaCepState.cep
        val podeBuscar = cepParaBuscar.errorMessage == null && cepParaBuscar.value.length == 8
        if(podeBuscar) {
            buscarCep(cepParaBuscar.value)
        }
    }

    fun onCepAlterado(valor: String) {
        val novoCep = valor.replace("\\D".toRegex(), "")
        if(novoCep.length <= 8 && uiState.buscaCepState.cep.value != novoCep) {
            val mensagemValidacao = validarCep(novoCep)
            uiState = uiState.copy(
                buscaCepState = uiState.buscaCepState.copy(
                    cep = FormField(
                        value = novoCep,
                        errorMessage = mensagemValidacao
                    )
                )
            )
        }
    }

    private fun validarCep(cep: String): String? = if(cep.isBlank()) {
        "O CEP é obrigatório"
    } else if(cep.length != 8) {
        "Informe um CEP válido"
    } else {
        null
    }

    private fun buscarCep(cep: String) {
        if(uiState.buscaCepState.buscandoCep) return

        uiState = uiState.copy(
            carregando = true,
            ocorreuErroAoCarregar = false,
            carregou = false,
            buscaCepState = uiState.buscaCepState.copy(
                buscandoCep = true,
                cep = FormField(
                    value = cep,
                    errorMessage = null
                ),
                logradouro = FormField(""),
                bairro = FormField(""),
                localidade = FormField(""),
                uf = FormField("")
            )
        )
        viewModelScope.launch {
            delay(1000)
            uiState = try {
                // para testar Erro na consulta:
                throw RuntimeException("<<ERRO MUITO BRABO>>");

                val cepRetornado: Cep = cepRepository.buscarCep(cep)
                uiState.copy(
                    carregando = false,
                    ocorreuErroAoCarregar = false,
                    carregou = true,
                    buscaCepState = uiState.buscaCepState.copy(
                        logradouro = FormField(cepRetornado.logradouro),
                        bairro = FormField(cepRetornado.bairro),
                        localidade = FormField(cepRetornado.localidade),
                        uf = FormField(cepRetornado.uf),
                        buscandoCep = false
                    )
                )
            } catch(ex: Exception) {
                println("[$tag]: Erro ao consultar o CEP $cep")
                ex.printStackTrace()
                uiState.copy(
                    carregando = false,
                    ocorreuErroAoCarregar = true,
                    carregou = false,
                    buscaCepState = uiState.buscaCepState.copy(
                        buscandoCep = false
                    )
                )
            }
        }
    }
}