package be.mariovonbassen.citindi.database

import android.content.Context
import be.mariovonbassen.citindi.database.repositories.OfflineUserRepository
import be.mariovonbassen.citindi.database.repositories.UserRepository

/**
 * App container for Dependency injection.
 */
interface AppContainer {
    val userRepository: UserRepository
}

/**
 * [AppContainer] implementation that provides instance of [OfflineItemsRepository]
 */
class AppDataContainer(private val context: Context) : AppContainer {
    /**
     * Implementation for [ItemsRepository]
     */
    override val userRepository: UserRepository by lazy {
        OfflineUserRepository(UserDatabase.getDatabase(context).userDao())
    }
}