# Skillforge App

A small 3-screen Android learning app built with Jetpack Compose, Retrofit, and Coil.

## Features
- **Home Screen**: Browse categories and popular courses.
- **Course Detail**: View course information, instructor details, and lessons.
- **Lesson Player**: A video player interface with lesson navigation.
- **Data Driven**: Powered by a single API endpoint.
- **Modern Stack**: Kotlin, Jetpack Compose, Coroutines, Flow, ViewModel, and Retrofit.

## Architecture
- **MVVM**: Model-View-ViewModel pattern for separation of concerns.
- **Repository Pattern**: Centralized data access.
- **UiState**: Sealed class for handling Loading, Success, and Error states.

## Tech Stack
- **Jetpack Compose**: For the modern declarative UI.
- **Retrofit & Gson**: For network requests and JSON parsing.
- **Coil**: For efficient image loading.
- **Compose Navigation**: For screen-to-screen navigation.
- **Kotlin Coroutines & Flow**: For asynchronous operations.

## Setup
1. Clone the repository.
2. Open in Android Studio (Ladybug or newer recommended).
3. Sync Gradle and run the `:app` module.

## Unit Tests
- Simple unit test for `SkillForgeViewModel` located in `app/src/test`.
