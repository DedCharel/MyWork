package com.example.mywork.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [WorkDbModel::class],
    version = 1,
    exportSchema = false
)
abstract class WorkDatabase: RoomDatabase() {

    abstract fun worksDao(): WorksDao

    companion object{

        private var instance: WorkDatabase? = null
        private val LOCK = Any()

        fun getInstance(context: Context): WorkDatabase {
            instance?.let { return it }
            synchronized(LOCK){
                instance?.let { return it }

                return Room.databaseBuilder(
                    context =context,
                    klass = WorkDatabase::class.java,
                    name = "works.db"
                ).fallbackToDestructiveMigration(true)
                    .build().also {
                        instance = it
                    }
            }
        }
    }
}