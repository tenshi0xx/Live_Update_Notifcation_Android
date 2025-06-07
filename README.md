# Live Update Notification Android

[![Linktree](https://img.shields.io/badge/linktree-1de9b6?style=for-the-badge&logo=linktree&logoColor=white)](https://linktr.ee/nicos_nicolaou)
[![Static Badge](https://img.shields.io/badge/Site-blue?style=for-the-badge&label=Web)](https://nicosnicolaou16.github.io/)
[![X](https://img.shields.io/badge/X-%23000000.svg?style=for-the-badge&logo=X&logoColor=white)](https://twitter.com/nicolaou_nicos)
[![LinkedIn](https://img.shields.io/badge/linkedin-%230077B5.svg?style=for-the-badge&logo=linkedin&logoColor=white)](https://linkedin.com/in/nicos-nicolaou-a16720aa)
[![Medium](https://img.shields.io/badge/Medium-12100E?style=for-the-badge&logo=medium&logoColor=white)](https://medium.com/@nicosnicolaou)
[![Mastodon](https://img.shields.io/badge/-MASTODON-%232B90D9?style=for-the-badge&logo=mastodon&logoColor=white)](https://androiddev.social/@nicolaou_nicos)
[![Bluesky](https://img.shields.io/badge/Bluesky-0285FF?style=for-the-badge&logo=Bluesky&logoColor=white)](https://bsky.app/profile/nicolaounicos.bsky.social)
[![Dev.to blog](https://img.shields.io/badge/dev.to-0A0A0A?style=for-the-badge&logo=dev.to&logoColor=white)](https://dev.to/nicosnicolaou16)
[![YouTube](https://img.shields.io/badge/YouTube-%23FF0000.svg?style=for-the-badge&logo=YouTube&logoColor=white)](https://www.youtube.com/@nicosnicolaou16)
[![Static Badge](https://img.shields.io/badge/Developer_Profile-blue?style=for-the-badge&label=Google)](https://g.dev/nicolaou_nicos)

This repository provides a working example of implementing Live Update Notifications on Android 16 (
Android U). It demonstrates how to leverage both Firebase Cloud Messaging (FCM) and local
notifications to deliver rich, timely updates to the user.

# 🚀 Features

- 🔔 Live Notifications support on Android 16
- ☁️ Firebase Cloud Messaging integration
- 📦 Payload handling and dynamic content updates
- 📱 Local notification example to simulate live updates without a backend
- 💡 Best practices for targeting Android 16+ notification behavior

# 📦 What’s Inside

- Sample app written in Kotlin
- Firebase integration (with sample payload format)
- Notification channel setup and management
- Code examples to create/update live notifications

# 🔧 Requirements

- Target SDK 34 (Android 14), compiled against Android 16 preview SDK
- Firebase project (for push notifications)

# 📚 Getting Started

- Clone the repository
- Connect your Firebase project and update the google-services.json
- Run the app on a device or emulator running Android 16 (SDK 36)
- Send a test FCM with the appropriate payload, or trigger a local notification update

1️⃣ Clone the Repository
2️⃣ Set Up Firebase

- Go to Firebase Console and create a new project (or use an existing one).
- Add an Android app to the Firebase project using your app’s package name.
    - 💡 Tip: You can change the application ID (package name) in build.gradle and
      AndroidManifest.xml to match your preferred namespace (e.g., com.yourname.app).
- Download the google-services.json file and place it in the /app directory.
- Enable Firebase Cloud Messaging (FCM) in the Firebase Console under Build > Cloud Messaging.
- Launch the app and tap the "Get FCM Token" button:
    - The token will be shown in a toast and logged in Logcat
    - Copy this token for sending test messages

5️⃣ Run the App

- Connect a physical device or use an emulator running Android 16
- Press Run in Android Studio to build and install the app

6️⃣ Test Live Notifications
🔹 Option A: Test with Local Notification

- Tap the "Send Local Notification" button in the app
- It creates a live progress notification based on a local NotificationModel instance
  🔹 Option B: Test with Firebase Console
- Go to Firebase Console > Cloud Messaging > Send Your First Message
- Enter a title and body (these will be overridden by data payload)
- Open Advanced options → Data
- Add the following key-value pairs:

### 📝 Example FCM Payload

```json
{
  "title": "Test",
  "body": "Test",
  "currentProgress": "55",
  "currentProgressSegmentOne": "33",
  "currentProgressSegmentTwo": "33",
  "currentProgressSegmentThree": "33",
  "currentProgressPointOne": "33",
  "currentProgressPointTwo": "66"
}

```

- In the Target section, select Single Device
- Paste the FCM token from the app
- Click Send Message

📌 The payload demonstrates segmented and point-based progress tracking in a live notification,
perfect for use cases like order tracking, fitness goals, or download status.

# Versioning

Target SDK version: 36 <br />
Minimum SDK version: 29 <br />
Kotlin version: 2.1.21 <br />
Gradle version: 8.10.1 <br />

# References

https://developer.android.com/about/versions/16/features/progress-centric-notifications <br />
https://github.com/android/platform-samples/tree/main/samples/user-interface/live-updates <br />
https://developer.android.com/develop/ui/views/notifications/build-notification#Updating <br />