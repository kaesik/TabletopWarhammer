package com.kaesik.tabletopwarhammer.android

import android.app.Application
import com.kaesik.tabletopwarhammer.di.initKoin
import org.koin.android.ext.koin.androidContext

class TabletopApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@TabletopApp)
        }
    }
}
