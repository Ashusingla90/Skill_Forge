package com.example.skillforge.ui.screens

import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.example.skillforge.data.Course
import com.example.skillforge.data.Lesson

@Composable
fun LessonScreen(
    course: Course,
    currentLesson: Lesson,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9F9F7))
            .statusBarsPadding()
    ) {
        VideoPlayer(videoUrl = currentLesson.content, onBackClick = onBackClick)
        
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            item {
                Text(
                    text = "LESSON ${course.lessons.indexOf(currentLesson) + 1} • ${course.title.uppercase()}",
                    color = Color(0xFF00C896),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = currentLesson.title, fontWeight = FontWeight.Bold, fontSize = 24.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "In this lesson, we will cover the core concepts of ${currentLesson.title}. Follow along with the video above.",
                    color = Color.Gray,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(24.dp))
                LessonTabs()
                Spacer(modifier = Modifier.height(16.dp))
            }
            items(course.lessons) { lesson ->
                LessonItemInPlayer(
                    lesson = lesson,
                    isPlaying = lesson == currentLesson
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

//@OptIn(UnstableApi::class)
//@Composable
//fun VideoPlayer(videoUrl: String, onBackClick: () -> Unit) {
//    val context = LocalContext.current
//    val exoPlayer = remember {
//        ExoPlayer.Builder(context).build().apply {
//            val mediaItem = MediaItem.Builder()
//                .setUri(videoUrl)
//                .build()
//            setMediaItem(mediaItem)
//            prepare()
//            playWhenReady = true
//        }
//    }
//
//    DisposableEffect(videoUrl) {
//        val mediaItem = MediaItem.Builder()
//            .setUri(videoUrl)
//            .build()
//        exoPlayer.setMediaItem(mediaItem)
//        exoPlayer.prepare()
//        exoPlayer.playWhenReady = true
//        onDispose {
//            exoPlayer.release()
//        }
//    }
//
//    Box(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(250.dp)
//            .background(Color.Black)
//    ) {
//        AndroidView(
//            factory = {
//                PlayerView(it).apply {
//                    player = exoPlayer
//                    useController = true
//                    setShowNextButton(false)
//                    setShowPreviousButton(false)
//                }
//            },
//            update = {
//                it.player = exoPlayer
//            },
//            modifier = Modifier.fillMaxSize()
//        )
//
//        IconButton(
//            onClick = onBackClick,
//            modifier = Modifier
//                .align(Alignment.TopStart)
//                .padding(16.dp)
//                .background(Color.Black.copy(alpha = 0.3f), CircleShape)
//        ) {
//            Icon(Icons.Default.ArrowBack, contentDescription = null, tint = Color.White)
//        }
//    }
//}

@OptIn(UnstableApi::class)
@Composable
fun VideoPlayer(videoUrl: String, onBackClick: () -> Unit) {
    val context = LocalContext.current

    // Player sirf ek baar banega, poori composable lifetime ke liye
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build()
    }

    // Sirf URL update karne ke liye — player ko release NAHI karta
    LaunchedEffect(videoUrl) {
        val mediaItem = MediaItem.Builder().setUri(videoUrl).build()
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.prepare()
        exoPlayer.playWhenReady = true
    }

    // Release sirf tab jab composable screen se hi hat jaye
    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .background(Color.Black)
    ) {
        AndroidView(
            factory = {
                PlayerView(it).apply {
                    player = exoPlayer
                    useController = true
                    setShowNextButton(false)
                    setShowPreviousButton(false)
                }
            },
            modifier = Modifier.fillMaxSize()
        )

        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
                .background(Color.Black.copy(alpha = 0.3f), CircleShape)
        ) {
            Icon(Icons.Default.ArrowBack, contentDescription = null, tint = Color.White)
        }
    }
}

@Composable
fun LessonTabs() {
    Column {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(text = "Lessons", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.Black)
            Spacer(modifier = Modifier.width(24.dp))
            Text(text = "Notes", fontSize = 16.sp, color = Color.Gray)
            Spacer(modifier = Modifier.width(24.dp))
            Text(text = "Resources", fontSize = 16.sp, color = Color.Gray)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Box(modifier = Modifier.width(60.dp).height(2.dp).background(Color(0xFF00C896)))
        HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f), thickness = 1.dp)
    }
}

@Composable
fun LessonItemInPlayer(lesson: Lesson, isPlaying: Boolean) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isPlaying) Color(0xFFE0F2F1).copy(alpha = 0.5f) else Color.White
        ),
        border = if (isPlaying) null else CardDefaults.outlinedCardBorder()
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(if (isPlaying) Color(0xFF00C896) else Color(0xFFF5F5F5)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (isPlaying) Icons.Default.Pause else if (lesson.isFree) Icons.Default.PlayArrow else Icons.Default.Lock,
                    contentDescription = null,
                    tint = if (isPlaying) Color.White else Color.Gray,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = lesson.title, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Text(
                    text = if (isPlaying) "Now playing • ${lesson.durationMinutes} min" else "${lesson.durationMinutes} min",
                    color = if (isPlaying) Color(0xFF00C896) else Color.Gray,
                    fontSize = 12.sp
                )
            }
            if (!isPlaying && lesson.isFree) {
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
