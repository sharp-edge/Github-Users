package com.sharpedge.githubusers.ui

import GitHubUserSearchScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.sharpedge.githubusers.ui.theme.MyAppTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.sharpedge.githubusers.ui.screens.UserProfile
import com.sharpedge.githubusers.viewmodel.GitHubUserViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val viewModel: GitHubUserViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyAppTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MainScreen() {
        val navController = rememberNavController()
        val currentRoute = navController.currentRoute()
        Scaffold(
            topBar = {
                val canPop = navController.previousBackStackEntry != null
                TopAppBar(
                    colors = TopAppBarDefaults.mediumTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = Color.White
                    ),
                    title = { Text(text = when (currentRoute) {
                        "githubUserSearch" -> "GitHub User Finder"
                        "userDetail" -> "User Details"
                        else -> "GitHub User Finder"
                    }) },
                    navigationIcon = {
                        IconButton(onClick = {
                            if (canPop) {
                                navController.popBackStack()
                            }
                        }) {
                            Icon(Icons.Filled.ArrowBack, "backIcon",
                            tint = Color.White
                            )
                        }
                    }
//                    navigationIcon = if (canPop) {
//                        IconButton(onClick = { navController.popBackStack() }) {
//                            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
//                        }
//                    } else null

//                    backgroundColor = MaterialTheme.colors.primary,
//                    contentColor = MaterialTheme.colors.onPrimary
                )
            }
        ) { innerPadding ->
            AppNavigation(Modifier.padding(innerPadding), navController)
        }
    }

    @Composable
    fun Greeting(name: String, modifier: Modifier = Modifier) {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        MyAppTheme {
            Greeting("Android")
        }
    }

    @Composable
    fun AppNavigation(modifier: Modifier = Modifier, navController: NavHostController) {

        LaunchedEffect(key1 = viewModel.navigationEvents) {
            viewModel.navigationEvents.collect { event ->
                when (event) {
                    is GitHubUserViewModel.NavigationEvent.NavigateToUserDetail -> {
                        navController.navigate("userDetail")
                        viewModel.resetNavigationEvent()
                    }
                    null -> {}
                }
            }
        }

        NavHost(
            navController = navController,
            startDestination = "githubUserSearch",
            modifier = modifier
        ) {
            composable("githubUserSearch") {

                GitHubUserSearchScreen(viewModel = viewModel) { userId ->

                    viewModel.selectUser(userId)
                }
            }
            composable("userDetail") {
                //val userId = backStackEntry.arguments?.getString("userId") ?: return@composable
                UserProfile(viewModel = viewModel)
            }
        }
    }



    @Composable
    private fun NavController.currentRoute(): String? {
        val navBackStackEntry by currentBackStackEntryAsState()
        return navBackStackEntry?.destination?.route
    }



}