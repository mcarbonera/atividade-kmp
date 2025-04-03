package br.com.mcarbonera.projetofinalconsultacep.ui.buscacep

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import br.com.mcarbonera.projetofinalconsultacep.ui.buscacep.visualtransformation.CepVisualTransformation
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuscaCepScreen(
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    viewModel: BuscaCepViewModel = koinViewModel<BuscaCepViewModel>()
) {
    LaunchedEffect(snackbarHostState, viewModel.uiState.ocorreuErroAoCarregar) {
        if(viewModel.uiState.ocorreuErroAoCarregar) {
            snackbarHostState.showSnackbar(
                message = "Ocorreu um erro ao consultar o CEP." +
                        " Aguarde um momento e tente novamente."
            )
        }
    }
    Scaffold(
        modifier = modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        topBar = {
            BuscaCepTopBar(
                modifier = modifier.fillMaxWidth()
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier.padding(innerPadding)
        ) {
            CepInput(
                modifier = modifier,
                cepFormState = viewModel.uiState.buscaCepState,
                onCepAlterado = viewModel::onCepAlterado
            )
            BuscarCepButton(
                modifier = modifier,
                isEnabled = viewModel.uiState.buscaCepState.isValid &&
                        !viewModel.uiState.buscaCepState.buscandoCep,
                isLoading = viewModel.uiState.carregando,
                onClick = viewModel::onClickBuscar
            )
            CepExibirResultado(
                modifier = modifier,
                viewModel.uiState.buscaCepState,
                exibir = viewModel.uiState.sucessoAoCarregar
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuscaCepTopBar(
    modifier: Modifier
) {
    TopAppBar(
        modifier = modifier.fillMaxWidth(),
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary
        ),
        title = { Text("Busca CEP") }
        //navigationIcon = navigationIcon,
        //actions = actions
    )
}

@Composable
fun CepInput(
    modifier: Modifier,
    cepFormState: BuscaCepState,
    onCepAlterado: (String) -> Unit,
) {
    FormTextField(
        modifier = modifier,
        label = "CEP",
        value = cepFormState.cep.value,
        onValueChanged = onCepAlterado,
        errorMessage = cepFormState.cep.errorMessage,
        keyboardType = KeyboardType.Number,
        visualTransformation = CepVisualTransformation(),
        enabled = !cepFormState.buscandoCep,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormTextField(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    onValueChanged: (String) -> Unit,
    enabled: Boolean = true,
    errorMessage: String? = null,
    keyboardCapitalization: KeyboardCapitalization = KeyboardCapitalization.Sentences,
    keyboardImeAction: ImeAction = ImeAction.Next,
    keyboardType: KeyboardType = KeyboardType.Text,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    OutlinedTextField(
        value = value,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        onValueChange = onValueChanged,
        label = { Text(label) },
        maxLines = 1,
        enabled = enabled,
        isError = errorMessage?.isNotEmpty() == true,
        trailingIcon = trailingIcon,
        keyboardOptions = KeyboardOptions(
            capitalization = keyboardCapitalization,
            imeAction = keyboardImeAction,
            keyboardType = keyboardType
        ),
        visualTransformation = visualTransformation
    )
    errorMessage?.let {
        Text(
            text = errorMessage,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

@Composable
fun BuscarCepButton(
    modifier: Modifier,
    isEnabled: Boolean,
    isLoading: Boolean,
    onClick: () -> Unit,
) {
    ElevatedButton(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        onClick = onClick,
        elevation = ButtonDefaults.buttonElevation(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        enabled = isEnabled
    ) {
        if(isLoading) {
            CircularProgressIndicator(
                modifier = modifier
                    .size(30.dp)
                    .padding(1.dp),
                strokeWidth = 2.dp,
                color = MaterialTheme.colorScheme.primary
            )
        } else {
            Text(
                modifier = modifier
                    .padding(vertical = 2.dp),
                text = "Buscar"
            )
        }
    }
}

@Composable
fun CepExibirResultado(
    modifier: Modifier,
    buscaCepState: BuscaCepState,
    exibir: Boolean
) {
    if(exibir) {
        Column(
             modifier = modifier
                 .padding(horizontal = 16.dp),
        ) {
            Text("CEP: ${buscaCepState.cep.value}")
            Text("Logradouro: ${buscaCepState.logradouro.value}")
            Text("Bairro: ${buscaCepState.bairro.value}")
            Text("Localidade: ${buscaCepState.localidade.value}")
            Text("UF: ${buscaCepState.uf.value}")
        }
    }
}