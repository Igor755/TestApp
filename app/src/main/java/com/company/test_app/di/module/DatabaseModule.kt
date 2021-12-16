package com.company.test_app.di.module

import android.app.Application
import androidx.room.Room
import com.company.test_app.data.storage.database.AppDatabase
import com.company.test_app.data.storage.database.dao.IItemsDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(application: Application): AppDatabase = Room
        .databaseBuilder(application, AppDatabase::class.java, AppDatabase.DB_NAME)
        .addMigrations()
        .build()

    @Provides
    fun provideItemDao(db: AppDatabase): IItemsDao = db.itemDao()
}