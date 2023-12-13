package be.mariovonbassen.citindi.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import be.mariovonbassen.citindi.models.User
import be.mariovonbassen.citindi.models.city.City
import be.mariovonbassen.citindi.models.relations.UserWithCities
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Upsert
    suspend fun upsertUser(user: User)

    @Delete
    suspend fun deleteUser(contact: User)

    @Query("SELECT * from users WHERE userId = :userId")
    fun getUser(userId: Int): Flow<User>

    @Query("SELECT * from users ORDER BY userName ASC")
    fun getAllUsers(): Flow<List<User>>

    @Query("SELECT * FROM users WHERE password = :password AND userName = :userName")
    suspend fun getUserByPassword(password: String, userName: String): User?


/*
@Insert(onConflict = OnConflictStrategy.REPLACE)
suspend fun insertCity(city: City)


@Transaction
@Query("SELECT * FROM users WHERE userId = :userId")
suspend fun getUserWithCities(userId: Int): List<UserWithCities>*/

}
