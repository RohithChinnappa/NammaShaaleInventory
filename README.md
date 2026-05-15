📦 Namma-Shaale Inventory
MindMatrix VTU Internship Program — Project 59
> A Digital Asset Auditor for Karnataka Government Schools — built with Kotlin, Jetpack Compose, and Room Database.
---
📌 Problem Statement
Primary and secondary schools receive sports kits, lab equipment, and tablets, but there is no easy way to track their condition. A broken tablet or a lost football is only discovered months later. There is no simple way for a teacher to audit the school's assets.
---
💡 Solution
Namma-Shaale Inventory is a simplified Android asset management tool for school teachers. Teachers can register every item in the school, track its condition monthly, log issues, and generate shareable reports for the SDMC (School Development and Monitoring Committee).
---
✨ Features
Feature	Description
📋 Asset Register	Add assets with name, serial number, category and photo
🏥 Monthly Health Check	Update condition: Working / Needs Repair / Broken
⚠️ Issue Log	Log problems like "Football lost during match" with date
🔧 Repair Requests	Auto-filtered list of broken assets for SDMC attention
📄 Summary Report	Generate and share a full inventory report via WhatsApp/Email
📊 Dashboard	View Total Assets, Working count, and Needs Repair count at a glance
---
🛠️ Tech Stack
Layer	Technology
Language	Kotlin
UI Framework	Jetpack Compose (Material 3)
Architecture	MVVM (Model-View-ViewModel)
Database	Room DB (local, offline-first)
Camera	CameraX
Navigation	Jetpack Navigation Compose
Image Loading	Coil
Build System	Gradle with KSP
---
🏗️ Project Architecture
```
MVVM Architecture
─────────────────
UI Layer (Compose Screens)
        ↕
ViewModel Layer (MainViewModel)
        ↕
Repository Layer (AppRepository)
        ↕
Database Layer (Room DB)
    ├── AssetDao
    ├── HealthCheckDao
    └── IssueLogDao
```
---
📁 Project Structure
```
app/src/main/java/com/nammashaale/inventory/
├── data/
│   ├── model/
│   │   └── Models.kt          # Asset, HealthCheck, IssueLog entities
│   ├── db/
│   │   ├── AppDatabase.kt     # Room database
│   │   ├── AssetDao.kt        # Asset queries
│   │   ├── HealthCheckDao.kt  # Health check queries
│   │   ├── IssueLogDao.kt     # Issue log queries
│   │   └── Converters.kt      # Type converters
│   └── repository/
│       └── AppRepository.kt   # Data access layer
├── viewmodel/
│   └── MainViewModel.kt       # App business logic
├── ui/
│   ├── theme/
│   │   └── Theme.kt           # Material 3 theme
│   └── screens/
│       ├── DashboardScreen.kt
│       ├── AssetRegisterScreen.kt
│       ├── HealthCheckScreen.kt
│       ├── IssueLogScreen.kt
│       ├── RepairRequestScreen.kt
│       └── SummaryReportScreen.kt
└── MainActivity.kt            # Navigation host
```
---
🚀 Getting Started
Prerequisites
Android Studio Hedgehog or later
JDK 17 or higher
Android device or emulator (API 26+)
Setup Instructions
Clone the repository
```bash
git clone https://github.com/RohithChinnappa/NammaShaaleInventory.git
```
Open in Android Studio
File → Open → select the `NammaShaaleInventory` folder
Wait for Gradle sync to complete
Run the app
Connect an Android device or start an emulator
Click the ▶ Run button
---
✅ Success Criteria
[x] Monthly Health Check updates 10 items in under 2 minutes
[x] App generates a shareable Summary Report
[x] UI is professional, organized, and passes review
[x] Offline-first — works without internet connection
---
🎯 Impact Goals
Resource Optimization — Ensures taxpayer money spent on school kits is well-tracked
Educational Quality — Keeps labs and sports rooms functional for students
Accountability — Builds a culture of "Asset Care" in the public school system
---
🔮 Future Scope
GenAI integration — Auto-detect asset condition from a photo using Gemini API
Cloud sync — Backup data to Firebase for multi-device access
QR Code scanning — Scan asset tags instead of manual entry
Multi-language support — Kannada language UI for local teachers
---
👨‍💻 Developer
Rohith Chinnappa
VTU Internship — MindMatrix Program
Project 59 — Android App Development using GenAI
---
📄 License
This project was developed as part of the MindMatrix VTU Internship Program.
## 📸 Screenshots

| Dashboard | Asset Register |
|---|---|
| ![Dashboard](screenshots/WhatsApp%20Image%202026-05-14%20at%2012.02.29%20AM.jpeg) | ![Assets](screenshots/WhatsApp%20Image%202026-05-14%20at%2012.25.43%20AM.jpeg) |

| Health Check | Issue Log |
|---|---|
| ![Health](screenshots/WhatsApp%20Image%202026-05-14%20at%2012.26.03%20AM.jpeg) | ![Issues](screenshots/WhatsApp%20Image%202026-05-14%20at%2012.26.29%20AM.jpeg) |
