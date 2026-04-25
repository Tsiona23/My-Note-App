# MyNoteApp

A simple Android application for managing notes, built with modern Android development practices.

## Features

- **Create Notes**: Easily add new notes with a title and content.
- **Edit Notes**: Update existing notes.
- **Delete Notes**: Remove notes you no longer need.
- **Search**: Quickly find notes (if implemented in HomeFragment).
- **Local Storage**: Notes are persisted locally using a Room database.

## Tech Stack

- **Language**: [Kotlin](https://kotlinlang.org/)
- **Architecture**: MVVM (Model-ViewModel-View)
- **UI Framework**: Android XML with ViewBinding and DataBinding
- **Database**: [Room](https://developer.android.com/training/data-storage/room) for local data persistence.
- **Navigation**: [Jetpack Navigation Component](https://developer.android.com/guide/navigation) for handling fragment transactions.
- **Concurrency**: [Kotlin Coroutines](https://developer.android.com/kotlin/coroutines) for asynchronous tasks.
- **DI/Management**: Repository pattern for data abstraction.

## Getting Started

### Prerequisites

- Android Studio Jellyfish | 2023.3.1 or newer.
- Android SDK 25 or higher.

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/MyNoteApp.git
   ```
2. Open the project in Android Studio.
3. Sync the project with Gradle files.
4. Run the app on an emulator or a physical device.

## Project Structure

- `model`: Data classes (e.g., `Note`).
- `database`: Room database configuration, including DAO and Database class.
- `repository`: Repository class to manage data operations.
- `viewmodel`: ViewModels to hold and manage UI-related data.
- `fragments`: UI fragments for different screens (Home, Add, Edit).
- `adapter`: RecyclerView adapters for listing notes.
