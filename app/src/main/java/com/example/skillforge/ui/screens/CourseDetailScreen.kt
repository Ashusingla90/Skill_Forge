package com.example.skillforge.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.skillforge.data.Course
import com.example.skillforge.data.Lesson

@Composable
fun CourseDetailScreen(
    course: Course,
    onBackClick: () -> Unit,
    onLessonClick: (Lesson) -> Unit
) {
    Scaffold(
        bottomBar = { CourseBottomBar() }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF9F9F7))
                .padding(padding)
        ) {
            item { HeroSection(course, onBackClick) }
            item {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = course.title, fontWeight = FontWeight.ExtraBold, fontSize = 24.sp, color = Color(0xFF1A1A1A))
                    Text(text = "Everything you need to start writing Kotlin", color = Color.Gray, fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(16.dp))
                    CourseStats(course)
                    Spacer(modifier = Modifier.height(24.dp))
                    InstructorCard(course.instructor)
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = "Start from zero and learn Kotlin's syntax, null safety, collections, and functions. By the end you'll be comfortable reading and writing idiomatic Kotlin.",
                        color = Color.Gray,
                        lineHeight = 20.sp
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Course content", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        val totalMinutes = course.lessons.sumOf { it.durationMinutes }
                        Text(text = "${course.lessons.size} lessons • $totalMinutes min", color = Color.Gray, fontSize = 14.sp)
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
            items(course.lessons) { lesson ->
                LessonItem(lesson = lesson, onClick = { if (lesson.isFree) onLessonClick(lesson) })
                Spacer(modifier = Modifier.height(12.dp))
            }
            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}

@Composable
fun HeroSection(course: Course, onBackClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(260.dp)
    ) {
        AsyncImage(
            model = course.thumbnailUrl,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.6f)),
                        startY = 300f
                    )
                )
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier
                    .size(40.dp)
                    .background(Color.White, CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.size(20.dp)
                )
            }
            IconButton(
                onClick = { /* TODO */ },
                modifier = Modifier
                    .size(40.dp)
                    .background(Color.White, CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.BookmarkBorder,
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
        Text(
            text = course.title,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
        )
    }
}

@Composable
fun CourseStats(course: Course) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFFFA000), modifier = Modifier.size(18.dp))
        Text(text = " ${course.rating}", fontWeight = FontWeight.Bold, fontSize = 14.sp)
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = "18,420", fontWeight = FontWeight.Bold, fontSize = 14.sp)
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = "⏱ ${course.durationHours}h", color = Color.Gray, fontSize = 14.sp)
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = course.level,
            color = Color(0xFF00C896),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun InstructorCard(instructor: com.example.skillforge.data.Instructor) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F0E8).copy(alpha = 0.5f))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = instructor.avatarUrl,
                contentDescription = null,
                modifier = Modifier.size(48.dp).clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = instructor.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(text = instructor.title, color = Color.Gray, fontSize = 12.sp)
            }
            TextButton(onClick = { /* TODO */ }) {
                Text(text = "Follow", color = Color(0xFF00C896), fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun LessonItem(lesson: Lesson, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = CardDefaults.outlinedCardBorder(),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(if (lesson.isFree) Color(0xFFE0F2F1) else Color(0xFFF5F5F5)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (lesson.isFree) Icons.Default.PlayArrow else Icons.Default.Lock,
                    contentDescription = null,
                    tint = if (lesson.isFree) Color(0xFF00C896) else Color.Gray,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = lesson.title, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Text(text = "${lesson.durationMinutes} min", color = Color.Gray, fontSize = 12.sp)
            }
            if (lesson.isFree) {
                Surface(
                    color = Color(0xFFE0F2F1),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        text = "FREE",
                        color = Color(0xFF00C896),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun CourseBottomBar() {
    Surface(
        shadowElevation = 8.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .navigationBarsPadding(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = "PRICE", color = Color.Gray, fontSize = 10.sp)
                Text(text = "Free", color = Color(0xFF00C896), fontWeight = FontWeight.Bold, fontSize = 20.sp)
            }
            Button(
                onClick = { /* TODO */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00C896)),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth(0.7f)
            ) {
                Text(text = "Enroll now", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
        }
    }
}
