package br.com.mcarbonera.projetofinalconsultacep.ui.buscacep.visualtransformation

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import br.com.mcarbonera.projetofinalconsultacep.util.extensions.formatarCep

class CepVisualTransformation: VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val cepFormatado = text.text.formatarCep()

        return TransformedText(
            AnnotatedString(cepFormatado),
            CepOffsetMapping
        )
    }

    object CepOffsetMapping : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            return when {
                offset > 5 -> offset + 1
                else -> offset
            }
        }

        override fun transformedToOriginal(offset: Int): Int {
            return when {
                offset > 5 -> offset - 1
                else -> offset
            }
        }
    }
}