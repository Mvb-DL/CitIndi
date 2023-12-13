package be.mariovonbassen.citindi.database

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import be.mariovonbassen.citindi.database.dao.UserDao
import be.mariovonbassen.citindi.models.User


@Database(
    entities = [User::class],
    version = 2,
   /* autoMigrations = [
        AutoMigration (
            from = 1,
            to = 2,
            spec = UserDatabase.UserDatabaseAutoMigration::class
        )
    ]*/
)

abstract class UserDatabase : RoomDatabase() {


   // class UserDatabaseAutoMigration: AutoMigrationSpec {   }

    abstract fun userDao(): UserDao

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

