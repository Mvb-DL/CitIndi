package be.mariovonbassen.citindi.database.repositories

import be.mariovonbassen.citindi.database.dao.UserDao
import be.mariovonbassen.citindi.models.User
import kotlinx.coroutines.flow.Flow

class OfflineUserRepository(private val userDao: UserDao) : UserRepository {

    override fun getAllUsers(): Flow<List<User>> { return userDao.getAllUsers() }

    override fun getUserStream(userId: Int): Flow<User?> = userDao.getUser(userId)

    override suspend fun upsertUser(user: User) = userDao.upsertUser(user)

    override suspend fun deleteUser(user: User) = userDao.deleteUser(user)

}
