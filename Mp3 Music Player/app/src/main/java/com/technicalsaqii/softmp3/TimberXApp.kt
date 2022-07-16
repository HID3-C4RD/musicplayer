
@file:Suppress("unused")

package com.technicalsaqii.softmp3

import android.app.Application
import com.technicalsaqii.softmp3.BuildConfig.DEBUG
import com.technicalsaqii.softmp3.db.roomModule
import com.technicalsaqii.softmp3.logging.FabricTree
import com.technicalsaqii.softmp3.network.lastFmModule
import com.technicalsaqii.softmp3.network.lyricsModule
import com.technicalsaqii.softmp3.network.networkModule
import com.technicalsaqii.softmp3.notifications.notificationModule
import com.technicalsaqii.softmp3.permissions.permissionsModule
import com.technicalsaqii.softmp3.playback.mediaModule
import com.technicalsaqii.softmp3.repository.repositoriesModule
import com.technicalsaqii.softmp3.ui.viewmodels.viewModelsModule
import org.koin.android.ext.android.startKoin
import timber.log.Timber

class TimberXApp : Application() {

    override fun onCreate() {
        super.onCreate()

        if (DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(FabricTree())
        }

        val modules = listOf(
                mainModule,
                permissionsModule,
                mediaModule,
                prefsModule,
                networkModule,
                roomModule,
                notificationModule,
                repositoriesModule,
                viewModelsModule,
                lyricsModule,
                lastFmModule
        )
        startKoin(
                androidContext = this,
                modules = modules
        )
    }
}
