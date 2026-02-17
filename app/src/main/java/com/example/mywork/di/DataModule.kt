package com.example.mywork.di

import android.content.Context
import com.example.mywork.data.OrganizationsDao
import com.example.mywork.data.WorkDatabase
import com.example.mywork.data.WorkRepositoryImpl
import com.example.mywork.data.WorkerRepositoryImpl
import com.example.mywork.data.WorkersDao
import com.example.mywork.data.WorksDao
import com.example.mywork.domain.work.WorkRepository
import com.example.mywork.domain.worker.WorkerRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Singleton
    @Binds
    fun bindsWorksRepository(impl: WorkRepositoryImpl): WorkRepository

    @Singleton
    @Binds
    fun bindsWorkersRepository(impl: WorkerRepositoryImpl): WorkerRepository

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

        @Singleton
        @Provides
        fun provideWorkersDao(
            database: WorkDatabase
        ): WorkersDao {
            return database.workersDao()
        }

        @Singleton
        @Provides
        fun provideOrganisationsDao(
            database: WorkDatabase
        ): OrganizationsDao {
            return database.organizationsDao()
        }
    }
}