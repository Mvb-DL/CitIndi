package be.mariovonbassen.citindi.navigation


import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import be.mariovonbassen.citindi.database.repositories.UserRepository
import be.mariovonbassen.citindi.ui.MainViewModelFactory
import be.mariovonbassen.citindi.ui.screens.authenticated.AddCityScreen
import be.mariovonbassen.citindi.ui.screens.authenticated.MainDashBoardScreen
import be.mariovonbassen.citindi.ui.screens.authenticated.ProfileScreen
import be.mariovonbassen.citindi.ui.screens.authenticated.SettingsScreen
import be.mariovonbassen.citindi.ui.screens.unauthenticated.LoginScreen
import be.mariovonbassen.citindi.ui.screens.unauthenticated.ResetPasswordScreen
import be.mariovonbassen.citindi.ui.screens.unauthenticated.SignUpScreen
import be.mariovonbassen.citindi.ui.viewmodels.SignUpViewModel


fun NavGraphBuilder.unauthenticatedGraph(navController: NavController) {

    navigation(
        route = NavigationRoutes.Unauthenticated.NavigationRoute.route,
        startDestination = NavigationRoutes.Unauthenticated.LoginScreen.route
    ) {

        // Login
        composable(route = NavigationRoutes.Unauthenticated.LoginScreen.route) {

            LoginScreen(
                onNavigateToRegistration = {
                    navController.navigate(route = NavigationRoutes.Unauthenticated.SignUpScreen.route)
                },
                onNavigateToForgotPassword = {
                    navController.navigate(route= NavigationRoutes.Unauthenticated.ResetPasswordScreen.route)
                                             },

                onNavigateToAuthenticatedRoute = {
                    navController.navigate(route = NavigationRoutes.Authenticated.NavigationRoute.route) {
                        popUpTo(route = NavigationRoutes.Unauthenticated.NavigationRoute.route) {
                            inclusive = true
                        }
                    }
                },
            )
        }

        // Registration
        composable(route = NavigationRoutes.Unauthenticated.SignUpScreen.route) {

            SignUpScreen(

                onNavigateBack = {
                    navController.navigateUp()
                },

                onNavigateToLogin = {
                    navController.navigate(route = NavigationRoutes.Unauthenticated.LoginScreen.route)
                },

                onNavigateToAuthenticatedRoute = {
                    navController.navigate(route = NavigationRoutes.Authenticated.NavigationRoute.route) {
                        popUpTo(route = NavigationRoutes.Unauthenticated.NavigationRoute.route) {
                            inclusive = true
                        }
                    }
                },

            )
        }

        composable(route = NavigationRoutes.Unauthenticated.ResetPasswordScreen.route) {
            ResetPasswordScreen(navController = navController)
        }
    }
}


fun NavGraphBuilder.authenticatedGraph(navController: NavController, currentRoute: String) {

    navigation(
        route = NavigationRoutes.Authenticated.NavigationRoute.route,
        startDestination = NavigationRoutes.Authenticated.MainDashBoardScreen.route

    ) {

        // Dashboard
        composable(route = NavigationRoutes.Authenticated.MainDashBoardScreen.route) {
            MainDashBoardScreen(navController = navController)
        }

        composable(route = NavigationRoutes.Authenticated.AddCityScreen.route){
            AddCityScreen(navController = navController, currentRoute=currentRoute)
        }

        composable(route = NavigationRoutes.Authenticated.ProfileScreen.route){
            ProfileScreen(navController = navController, currentRoute=currentRoute)
        }

        composable(route = NavigationRoutes.Authenticated.SettingsScreen.route){
            SettingsScreen(navController = navController, currentRoute=currentRoute)
        }
    }
}

