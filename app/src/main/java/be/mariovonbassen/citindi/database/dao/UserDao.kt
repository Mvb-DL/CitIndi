package be.mariovonbassen.citindi.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import be.mariovonbassen.citindi.models.User
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

}
