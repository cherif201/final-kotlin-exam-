# Tunisia Heritage Quest

Tunisia Heritage Quest is a native Android quiz application built with Kotlin and Jetpack Compose for the `IT370` course. The project focuses on Tunisian heritage and challenges users to identify monuments, cities, and cultural sites through category-based quiz sessions with themed visuals, timer-based gameplay, and progress tracking.

## Project Overview

The application follows a linear gameplay flow:

`Splash -> Menu -> Category -> Difficulty -> Quiz -> Results`

The project was designed as an educational mobile app with a strong Mediterranean blue visual identity inspired by Tunisian architecture and heritage motifs.

## Main Features

- splash screen with heritage branding
- themed home screen and custom Compose design system
- category-based quiz selection
- three difficulty levels
- timer-enabled quiz flow
- results screen with score summary and replay support
- local persistence for settings and player statistics
- sound effects and haptic feedback toggles
- bundled local image assets for selected quiz questions, with remote fallback support

## Tech Stack

- `Kotlin`
- `Jetpack Compose`
- `Material 3`
- `Navigation Compose`
- `Room`
- `DataStore Preferences`
- `Kotlin Coroutines` and `Flow`
- `Coil`
- `JUnit`

## Project Structure

```text
app/
  src/main/java/com/example/heritagequest/
    data/
    di/
    domain/
    ui/
    viewmodel/
  src/main/res/
  src/main/assets/
report_assets/
gradle/
README.md
IT370_Tunisia_Heritage_Quest_Report.tex
IT370_Tunisia_Heritage_Quest_Report.pdf
```

## How to Run

From the project root:

```powershell
.\gradlew.bat assembleDebug
```

To install the debug build on a connected device or emulator:

```powershell
.\gradlew.bat installDebug
```

## Testing

Run unit tests:

```powershell
.\gradlew.bat testDebugUnitTest
```

Run a clean build and tests together:

```powershell
.\gradlew.bat clean :app:assembleDebug :app:testDebugUnitTest
```

Testing screenshots used in the academic report are stored in:

- `report_assets/test1.png`
- `report_assets/test2.png`
- `report_assets/test3.png`

## Assets and Images

Quiz images currently use bundled local assets for selected questions under:

`app/src/main/assets/quiz_images/`

The project also contains:

- `app/src/main/assets/icon.png`
- `app/src/assets/` copies of source assets used during integration

## Academic Report

The repository includes the final course report in both source and compiled forms:

- [IT370_Tunisia_Heritage_Quest_Report.tex](./IT370_Tunisia_Heritage_Quest_Report.tex)
- [IT370_Tunisia_Heritage_Quest_Report.pdf](./IT370_Tunisia_Heritage_Quest_Report.pdf)

## AI Usage Statement

Artificial intelligence was used in a limited supporting role for:

- question generation
- code review
- test support
- image generation
- a small portion of code implementation

Human work led the high-level design, product decisions, and UI/UX direction of the application.

## Author

- `Mohamed Cherif Khcherif`

## Course Information

- `Course:` IT370
- `Professor:` Najet Boughanmi
