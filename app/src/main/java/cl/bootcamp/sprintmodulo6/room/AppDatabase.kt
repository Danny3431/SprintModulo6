package cl.bootcamp.sprintmodulo6.room

import androidx.room.Database
import androidx.room.RoomDatabase
import cl.bootcamp.sprintmodulo6.data.ProductEntity


@Database(entities = [ProductEntity::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao


    }

