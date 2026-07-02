package com.example.skillforge.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.skillforge.data.Category
import com.example.skillforge.data.Course
import com.example.skillforge.UiState

@Composable
fun HomeScreen(
    uiState: UiState,
    onCourseClick: (Course) -> Unit
) {
    when (uiState) {
        is UiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is UiState.Success -> {
            val categories = uiState.data.categories
            val allCourses = categories.flatMap { it.courses }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .statusBarsPadding()
                    .padding(horizontal = 16.dp)
            ) {
                item { HomeHeader() }
                item { SearchBar() }
                item { SectionHeader(title = "Categories") }
                item { CategoryList(categories) }
                item { SectionHeader(title = "Popular courses") }
                items(allCourses) { course ->
                    CourseCard(course = course, onClick = { onCourseClick(course) })
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
        is UiState.Error -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = uiState.message, color = MaterialTheme.colorScheme.error)
            }
        }
    }
}

@Composable
fun HomeHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(text = "Welcome back", color = Color(0xFF757575), fontSize = 14.sp)
            Text(text = "Find your next skill", fontWeight = FontWeight.ExtraBold, fontSize = 24.sp, color = Color(0xFF1A1A1A))
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { /* TODO */ }) {
                Icon(Icons.Default.Notifications, contentDescription = null)
            }
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF00C896)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "AS", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar() {
    OutlinedTextField(
        value = "",
        onValueChange = {},
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        placeholder = { Text("Search courses, topics...", color = Color.Gray) },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = Color.Gray) },
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent
        )
    )
}

@Composable
fun SectionHeader(title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
        TextButton(onClick = { /* TODO */ }) {
            Text(text = "See all", color = Color(0xFF00C896))
        }
    }
}

@Composable
fun CategoryList(categories: List<Category>) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        items(categories) { category ->
            CategoryCard(category)
        }
    }
}

@Composable
fun CategoryCard(category: Category) {
    Card(
        modifier = Modifier
            .width(160.dp)
            .height(140.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFFE0F2F1)),
                contentAlignment = Alignment.Center
            ) {
                // Placeholder icon
                Box(modifier = Modifier.size(20.dp).background(Color(0xFF00C896), RoundedCornerShape(4.dp)))
            }
            Column {
                Text(text = category.name, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Text(text = "${category.courses.size} courses", color = Color.Gray, fontSize = 12.sp)
            }
        }
    }
}

@Composable
fun CourseCard(course: Course, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        onClick = onClick
    ) {
        Row(modifier = Modifier.fillMaxSize().padding(12.dp)) {
            AsyncImage(
                model = course.thumbnailUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(86.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = course.level.uppercase(),
                    color = Color(0xFF00C896),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = course.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    maxLines = 2
                )

                Text(text = course.instructor.name, color = Color.Gray, fontSize = 12.sp)

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFFFA000), modifier = Modifier.size(14.dp))
                    Text(text = " ${course.rating}", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "⏱ ${course.durationHours}h", color = Color.Gray, fontSize = 12.sp)
                }
            }
        }
    }
}
