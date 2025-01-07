package com.geneus.currenxy

import android.app.Application
import com.geneus.currenxy.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class CurrenxyApp: Application() {
    override fun onCreate() {
        super.onCreate()

        // Start Koin
        startKoin {
            androidContext(this@CurrenxyApp)
            modules(appModules)
        }
    }
}