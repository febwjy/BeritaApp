package id.febwjy.beritaapp.base

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Created by Febby Wijaya on 14/11/2023.
 */
@HiltAndroidApp
class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}