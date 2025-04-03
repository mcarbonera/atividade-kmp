package br.com.mcarbonera.projetofinalconsultacep

import android.app.Application
import br.com.mcarbonera.projetofinalconsultacep.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidContext(this@MainApplication)
            androidLogger()
        }
    }
}