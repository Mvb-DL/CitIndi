package be.mariovonbassen.citindi.database.repositories

import be.mariovonbassen.citindi.models.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    /**
     * Retrieve all the items from the the given data source.
     */
    fun getAllUsers(): Flow<List<User>>

    /**
     * Retrieve an item from the given data source that matches with the [id].
     */
    fun getUserStream(userId: Int): Flow<User?>

    /**
     * Insert item in the data source
     */
    suspend fun upsertUser(user: User)

    /**
     * Delete item from the data source
     */
    suspend fun deleteUser(user: User)

}