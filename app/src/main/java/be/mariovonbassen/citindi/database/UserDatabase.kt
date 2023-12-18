package be.mariovonbassen.citindi.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import be.mariovonbassen.citindi.database.converters.Converters
import be.mariovonbassen.citindi.database.dao.CityDao
import be.mariovonbassen.citindi.database.dao.UserDao
import be.mariovonbassen.citindi.models.User
import be.mariovonbassen.citindi.models.city.City


@Database(
    entities = [User::class, City::class],
    version = 7,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class UserDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun cityDao(): CityDao

    companion object {
        @Volatile
        private var Instance: UserDatabase? = null

        fun getDatabase(context: Context): UserDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, UserDatabase::class.java, "users")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }

}

