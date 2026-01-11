package com.example.mywork.di

import android.content.Context
import com.example.mywork.data.WorkDatabase
import com.example.mywork.data.WorkRepositoryImpl
import com.example.mywork.data.WorksDao
import com.example.mywork.domain.WorkRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
interface DataModule {

    @Singleton
    @Binds
    fun bindsWorksRepository(impl: WorkRepositoryImpl): WorkRepository

    companion object {

        @Singleton
        @Provides
        fun provideWorkDatabase(
            @ApplicationContext context: Context
        ): WorkDatabase {
            return WorkDatabase.getInstance(context)
        }

        @Singleton
        @Provides
        fun provideWorksDao(
            database: WorkDatabase
        ): WorksDao {
            return database.worksDao()
        }
    }
}