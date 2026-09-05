# MEHEDIVAI115 - Desktop Taskbar App for Android

## Overview
MEHEDIVAI115 is an advanced Android application that transforms your phone's interface into a desktop-like environment with a Windows-style taskbar. Manage multiple applications simultaneously with resizable windows, customization options, and multi-tasking capabilities.

## Features

### 🪟 Window Management
- **Multiple Windows**: Open and manage 10-15+ apps simultaneously
- **Resizable Windows**: Resize app windows like Windows desktop
- **Draggable Windows**: Move windows around the screen
- **Z-Index Management**: Bring windows to front/send to back

### 📱 App Management
- **Auto-detect Apps**: Automatically shows all installed apps on startup
- **App Launcher**: Quick access to all apps
- **Minimize/Maximize**: Minimize apps to taskbar and restore them
- **App Switcher**: Easy switching between open applications

### 🎨 Customization
- **Window Styling**: Customize window appearance
- **Theme Support**: Dark and light themes
- **Window Layout**: Save and restore window positions
- **Taskbar Customization**: Customize taskbar appearance

### ⚙️ System Features
- **Immersive Mode**: Full-screen experience
- **Persistent**: Keep screen on while using
- **Background Service**: Optional background service support
- **Gesture Support**: Touch gestures for window management

## Project Structure

```
MEHEDIVAI115/
├── src/main/
│   ├── java/com/mehedivai115/taskbar/
│   │   ├── MainActivity.kt
│   │   ├── viewmodel/
│   │   │   └── TaskbarViewModel.kt
│   │   ├── model/
│   │   │   └── AppInfo.kt
│   │   ├── service/
│   │   │   └── TaskbarService.kt
│   │   └── ui/
│   │       ├── adapter/
│   │       ├── widget/
│   │       └── fragment/
│   └── res/
│       ├── layout/
│       ├── values/
│       └── drawable/
├── build.gradle
└── AndroidManifest.xml
```

## Requirements

- Android SDK 24 (Android 7.0) or higher
- Target SDK 34 (Android 14)
- Kotlin 1.8+
- AndroidX libraries

## Permissions

- `QUERY_ALL_PACKAGES`: To list all installed applications
- `GET_PACKAGE_USAGE_STATS`: To track app usage
- `SYSTEM_ALERT_WINDOW`: For overlay windows (if needed)
- `INTERNET`: For future cloud features

## Installation

1. Clone the repository
2. Open in Android Studio
3. Build and run on your Android device

## Usage

1. Launch MEHEDIVAI115
2. Click "Apps" button to view all installed applications
3. Tap an app to open it in a window
4. Resize, move, and minimize windows as needed
5. Use the taskbar to switch between open apps

## Development Roadmap

- [ ] Window resizing with touch gestures
- [ ] Snap-to-grid functionality
- [ ] Multi-display support
- [ ] App window themes
- [ ] Taskbar customization UI
- [ ] Window maximize/restore
- [ ] App grouping and organizing
- [ ] Wallpaper support
- [ ] Custom shortcuts
- [ ] Performance optimization

## Architecture

### MVVM Pattern
- **Model**: AppInfo data class
- **ViewModel**: TaskbarViewModel for state management
- **View**: Activities and Fragments for UI

### Key Components
- `MainActivity`: Main activity hosting the taskbar interface
- `TaskbarViewModel`: Manages app list and window states
- `TaskbarService`: Background service for taskbar functionality
- `AppInfo`: Data model for application information

## License

MIT License - Feel free to use and modify

## Author

MEHEDI - MEHEDIVAI115

---

**Status**: 🚀 In Development
**Last Updated**: September 2026
