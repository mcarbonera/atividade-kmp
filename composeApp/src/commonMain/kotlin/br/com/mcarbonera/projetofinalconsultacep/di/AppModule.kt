package br.com.mcarbonera.projetofinalconsultacep.di

import br.com.mcarbonera.projetofinalconsultacep.data.repository.CepRepository
import br.com.mcarbonera.projetofinalconsultacep.ui.buscacep.BuscaCepViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule: Module = module {
    singleOf(::CepRepository)
    viewModelOf(::BuscaCepViewModel)
}