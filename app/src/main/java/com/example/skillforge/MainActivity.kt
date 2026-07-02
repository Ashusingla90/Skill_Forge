package com.example.skillforge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.skillforge.ui.screens.HomeScreen
import com.example.skillforge.ui.screens.CourseDetailScreen
import com.example.skillforge.ui.screens.LessonScreen
import com.example.skillforge.ui.theme.SkillforgeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            SkillforgeTheme {
                val viewModel: SkillForgeViewModel = viewModel()
                val uiState by viewModel.uiState.collectAsState()
                val navController = rememberNavController()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    NavHost(navController = navController, startDestination = "home") {
                        composable("home") {
                            HomeScreen(
                                uiState = uiState,
                                onCourseClick = { course ->
                                    navController.navigate("course_detail/${course.title}")
                                }
                            )
                        }
                        composable("course_detail/{courseTitle}") { backStackEntry ->
                            val courseTitle = backStackEntry.arguments?.getString("courseTitle")
                            if (uiState is UiState.Success) {
                                val course = (uiState as UiState.Success).data.categories
                                    .flatMap { it.courses }
                                    .find { it.title == courseTitle }
                                
                                course?.let {
                                    CourseDetailScreen(
                                        course = it,
                                        onBackClick = { navController.popBackStack() },
                                        onLessonClick = { lesson ->
                                            navController.navigate("lesson/${course.title}/${lesson.title}")
                                        }
                                    )
                                }
                            }
                        }
                        composable("lesson/{courseTitle}/{lessonTitle}") { backStackEntry ->
                            val courseTitle = backStackEntry.arguments?.getString("courseTitle")
                            val lessonTitle = backStackEntry.arguments?.getString("lessonTitle")
                            
                            if (uiState is UiState.Success) {
                                val course = (uiState as UiState.Success).data.categories
                                    .flatMap { it.courses }
                                    .find { it.title == courseTitle }
                                
                                val lesson = course?.lessons?.find { it.title == lessonTitle }
                                
                                if (course != null && lesson != null) {
                                    LessonScreen(
                                        course = course,
                                        currentLesson = lesson,
                                        onBackClick = { navController.popBackStack() }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
