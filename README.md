# MyNoteApp

A feature-rich, cloud-synced Android application for managing notes, built with modern Android development practices and an offline-first architecture.

## 🚀 Features

- **Advanced Authentication**:
    - Secure **Email/Password** login and sign-up.
    - Seamless **Google Sign-In** integration.
    - Automatic session management (Auto-login).
- **Real-Time Cloud Sync**:
    - Instant synchronization with **Firebase Firestore**.
    - Real-time updates across multiple devices using Firestore Snapshot Listeners.
- **Offline-First Architecture**:
    - Full offline support using **Room Database**.
    - Notes are saved locally first and synced to the cloud when a connection is available.
- **Smart Note Templates**:
    - **To-Do List**: Quick bullet points for task management.
    - **Meeting Notes**: Structured format for date, attendees, and agenda.
    - **Idea Outline**: Framework for capturing and detailing new concepts.
- **Dynamic Note Management**:
    - Modern **Staggered Grid** layout for notes.
    - Real-time search by title or description.
    - Edit and delete functionality with smooth transitions.
- **Modern UI/UX**:
    - Material Design 3 components.
    - Responsive layouts with `ScrollView` and `TextInputLayout`.
    - User-friendly error handling and feedback.

## 🛠 Tech Stack

- **Language**: [Kotlin](https://kotlinlang.org/)
- **Architecture**: MVVM (Model-ViewModel-View) with Repository Pattern.
- **Database**:
    - **Room**: Local persistence and source of truth.
    - **Firebase Firestore**: Cloud storage and cross-device sync.
- **Authentication**: Firebase Auth (Email/Pass & Google).
- **Navigation**: Jetpack Navigation Component with SafeArgs.
- **UI Framework**: ViewBinding & DataBinding.
- **Concurrency**: Kotlin Coroutines & Flow.

## 🏁 Getting Started

### Prerequisites

- **Android Studio Ladybug** | 2024.2.1 or newer.
- **Firebase Project**:
    - Enable Authentication (Email/Password & Google).
    - Create a Firestore Database in your preferred region.
    - Add your `google-services.json` to the `app/` directory.
    - Register your **SHA-1 fingerprint** in the Firebase Console for Google Sign-In.

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/Tsiona23/MyNoteApp.git
   ```
2. Open the project in Android Studio.
3. Sync the project with Gradle files.
4. Run the app on an emulator or a physical device (API 25+).

## 📁 Project Structure

- `model`: Data entities (e.g., `Note`) with `Parcelize` and Firebase compatibility.
- `database`: Room database configuration, DAOs, and migration logic.
- `repository`: Data layer managing the bridge between Room and Firestore.
- `viewmodel`: Business logic and UI state management using `LiveData`.
- `fragments`: Navigation destinations (Login, Home, Add, Edit).
- `adapter`: RecyclerView logic for the staggered notes grid.
- `res/navigation`: Navigation graph for application flow.
