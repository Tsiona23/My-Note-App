# MyNoteApp

A feature-rich Android application for managing notes with local persistence and cloud synchronization.

## Features

- **Authentication**: Secure login and sign-up using **Firebase Authentication**.
    - Supports Email/Password authentication.
    - Supports **Google Sign-In**.
- **Note Management**: 
    - Create, Edit, and Delete notes.
    - Staggered grid layout for a modern look.
- **Quick Templates**: Speed up note-taking with built-in templates for:
    - **To-Do Lists**: Pre-filled bullet points.
    - **Meeting Notes**: Structured format for attendees, agenda, and action items.
    - **Ideas**: Outlines for concepts and next steps.
- **Cloud Sync**: Notes are automatically synced to **Firebase Firestore**, ensuring your data is backed up.
- **Offline Support**: Uses an offline-first approach with **Room Database**. Notes are saved locally first and synced when an internet connection is available.
- **Search**: Real-time search functionality to find notes quickly by title or content.

## Tech Stack

- **Language**: [Kotlin](https://kotlinlang.org/)
- **Architecture**: MVVM (Model-View-ViewModel) with Repository Pattern.
- **Database**: 
    - [Room](https://developer.android.com/training/data-storage/room) (Local Persistence)
    - [Firebase Firestore](https://firebase.google.com/docs/firestore) (Cloud Sync)
- **Authentication**: [Firebase Auth](https://firebase.google.com/docs/auth) & [Google Sign-In](https://developers.google.com/identity/sign-in/android)
- **UI Components**: 
    - Material Design 3
    - ViewBinding & DataBinding
    - Navigation Component (SafeArgs)
- **Concurrency**: Kotlin Coroutines & Flow.

## Getting Started

### Prerequisites

- Android Studio Ladybug or newer.
- A Firebase project with **Authentication** (Email/Google) and **Firestore** enabled.
- A `google-services.json` file placed in the `app/` directory.

### Installation

1. Clone the repository.
2. Add your `google-services.json` from the Firebase Console.
3. Ensure your SHA-1 fingerprint is registered in Firebase for Google Sign-In.
4. Build and run on an Android device (API 25+).

## Project Structure

- `model`: Data entities (Note) with Parcelize support.
- `database`: Room DB setup and Data Access Objects (DAOs).
- `repository`: Handles logic for switching between local and remote data sources.
- `viewmodel`: Manages UI state and business logic.
- `fragments`: Navigation destinations (Login, Home, Add, Edit).
- `adapter`: RecyclerView logic for displaying the note grid.
