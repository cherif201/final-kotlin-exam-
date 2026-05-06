# Tunisia Heritage Quest — Product Requirements Document

> Android image-identification quiz game built with Kotlin + Jetpack Compose for an Advanced Programming course assignment.

> **This PRD ships with one companion file** — `QuestionBank.kt` (the full question data, 72 questions across 6 categories × 3 difficulties, with Wikipedia article titles for runtime image resolution). Images are fetched from Wikipedia's REST API at runtime via Coil — no manual image sourcing required. See §17 for the image strategy and §19 for everything still needed from the student.

---

## 1. Project Overview

**App name:** Tunisia Heritage Quest
**Platform:** Android (native)
**Language:** Kotlin
**UI toolkit:** Jetpack Compose (Material 3)
**Build system:** Gradle (Kotlin DSL preferred)
**Min SDK:** 24 (Android 7.0)  ·  **Target/Compile SDK:** 34

**Concept.** Single-player quiz game where the player is shown a photograph of a Tunisian heritage site (Roman, Islamic, Punic, Modern, Natural, or a city) and must pick the correct name from 4 options. Scoring, timer, difficulty levels, category selection, and a final results screen are included.

**Why it exists.** Course deliverable for an Advanced Programming class. The grading rubric drives most of the requirements — architecture, navigation, lifecycle, testing, adaptive UI, UI/UX polish, and code quality each carry weight. The PRD is written so that an implementing LLM (Copilot, Gemini, Codex, etc.) can produce the full project end-to-end.

---

## 2. Goals & Non-Goals

**Goals**
- Ship a feature-complete quiz game covering 6 screens (Splash → Menu → Categories → Difficulty/Settings → Quiz → Results).
- Hit every item in the evaluation rubric (architecture, navigation, lifecycle, testing, adaptive UI, UI/UX, functionality, code quality).
- Produce code that reads as if a real student wrote it (see §13).
- Cover all three test types: **unit tests**, **ViewModel tests**, and **navigation/UI instrumentation tests**.

**Non-Goals (out of scope)**
- Multiplayer, accounts, online leaderboards, or backend services.
- Localization beyond what's needed to display Arabic/French strings on screen (English is the primary UI language; Arabic title can appear on the splash).
- Push notifications, analytics SDKs, monetization.
- iOS or web versions.
- **Gameplay extras visible in the reference mockup but explicitly out of scope:** coin/currency system, XP/levels, leaderboard / "Top Players", daily challenge with countdown, bottom-tab navigation (Home / Explore / Quiz / Profile). The app adopts the *visual style* of the mockup but keeps the original 6-screen linear flow.
- **Optional bonus (not required, easy to add if time permits):** a "Hint" button on the quiz screen that removes one wrong answer, with a per-quiz hint budget (e.g., 2 hints). If implemented, it must be feature-flag-gated so disabling it doesn't break the screen.

---

## 3. Target Audience

Students, tourists, history enthusiasts, and the Tunisian diaspora aged 12+. The tone of all in-app copy is friendly, mildly educational, and respectful toward the cultural material (no jokes about religious sites, no flippant commentary on archaeology).

---

## 4. Tech Stack & Key Dependencies

| Concern | Choice |
|---|---|
| UI | Jetpack Compose, Material 3 |
| Architecture | MVVM + unidirectional data flow |
| State | `StateFlow` / `MutableStateFlow` exposed by ViewModels |
| DI | Manual DI via a small `AppContainer` (no Hilt required, but Hilt is acceptable if the implementer prefers — keep it light) |
| Persistence | Room (for stats: streak, sites completed, mastery %) + DataStore Preferences (for settings toggles: timer, sound, haptics) |
| Navigation | `androidx.navigation:navigation-compose` |
| Image loading | Coil (`io.coil-kt:coil-compose`) |
| Async | Kotlin Coroutines + Flow |
| Testing | JUnit4, Truth or kotlin.test assertions, Turbine for Flow tests, MockK or fakes (prefer hand-written fakes), Compose UI test, Navigation testing artifact |
| Lint/format | Default Android Lint; ktlint optional |

> Avoid pulling in heavy libraries we don't use. Keep the dependency list lean.

---

## 5. Architecture

Standard MVVM with Compose:

```
ui (composables, screens)        ← state in, events out
   │
   ▼
viewmodel (UI state holders)     ← exposes StateFlow<UiState>, handles events
   │
   ▼
domain (use cases — optional, only where it earns its keep)
   │
   ▼
data (repositories, Room DAO, DataStore, in-memory question bank)
```

**Required architecture components (rubric: 15 pts)**
- `ViewModel` (one per feature screen that needs logic: `MenuViewModel`, `CategoryViewModel`, `QuizViewModel`, `ResultsViewModel`, `SettingsViewModel`).
- `StateFlow` for UI state, `SharedFlow` for one-shot events (e.g. "navigate to results").
- `Room` database with at least one entity (`PlayerStatsEntity`) and DAO.
- `DataStore<Preferences>` for `SettingsRepository`.
- `Repository` layer between ViewModels and data sources.
- `LiveData` is **not** required — prefer `StateFlow`. Don't mix the two.

**State pattern.** Each screen has a sealed `UiState` (or a single data class with nullable fields if a sealed type is overkill). Events go up via lambdas, state comes down as a parameter. Composables are stateless wherever practical.

---

## 6. Project Structure

```
app/
 ├─ src/main/java/com/<student>/heritagequest/
 │   ├─ HeritageQuestApp.kt              // Application class, builds AppContainer
 │   ├─ MainActivity.kt                   // Single-activity host; sets up NavHost
 │   ├─ di/
 │   │   └─ AppContainer.kt
 │   ├─ data/
 │   │   ├─ db/
 │   │   │   ├─ HeritageDatabase.kt
 │   │   │   ├─ PlayerStatsDao.kt
 │   │   │   └─ PlayerStatsEntity.kt
 │   │   ├─ prefs/
 │   │   │   └─ SettingsDataStore.kt
 │   │   ├─ questions/
 │   │   │   └─ QuestionBank.kt           // hardcoded question list (or JSON in assets)
 │   │   └─ repo/
 │   │       ├─ QuizRepository.kt
 │   │       ├─ SettingsRepository.kt
 │   │       └─ StatsRepository.kt
 │   ├─ domain/
 │   │   └─ model/                        // Question, Category, Difficulty, QuizResult, etc.
 │   ├─ ui/
 │   │   ├─ navigation/
 │   │   │   ├─ HeritageNavHost.kt
 │   │   │   └─ Routes.kt
 │   │   ├─ theme/
 │   │   │   ├─ Color.kt
 │   │   │   ├─ Type.kt
 │   │   │   └─ Theme.kt
 │   │   ├─ splash/
 │   │   ├─ menu/
 │   │   ├─ category/
 │   │   ├─ difficulty/
 │   │   ├─ quiz/
 │   │   └─ results/
 │   └─ util/                             // small helpers (haptics, sound, formatters)
 └─ src/test/                             // unit tests
 └─ src/androidTest/                      // UI + navigation tests
```

---

## 7. Domain Model

```kotlin
enum class Category(val displayName: String, val iconRes: Int) {
    ROMAN("Roman"),
    ISLAMIC("Islamic"),
    PUNIC("Punic"),
    MODERN("Modern"),
    NATURE("Nature"),
    CITIES("Cities");
}

enum class Difficulty(val secondsPerQuestion: Int, val showHints: Boolean) {
    EASY(15, true),
    MEDIUM(20, true),
    HARD(15, false);
}

data class Question(
    val id: String,
    val category: Category,
    val difficulty: Difficulty,
    val wikipediaTitle: String,  // article title (URL-encoded form), e.g. "Amphitheatre_of_El_Jem"
    val imageSource: String,     // attribution caption, e.g. "Wikimedia Commons"
    val prompt: String,          // "What is the name of this monument?"
    val options: List<String>,   // exactly 4
    val correctIndex: Int,       // 0..3
    val funFact: String          // shown on the incorrect/explanation screen
)

data class AnswerOutcome(
    val wasCorrect: Boolean,
    val pointsAwarded: Int,
    val correctOption: String,
    val funFact: String
)

data class QuizSession(
    val category: Category,
    val difficulty: Difficulty,
    val questions: List<Question>,
    val currentIndex: Int,
    val score: Int,
    val correctCount: Int,
    val answers: List<AnswerOutcome>
)

data class PlayerStats(
    val sitesCompleted: Int,
    val masteryPercent: Int,
    val streakDays: Int
)
```

**Scoring rule:** +10 points per correct answer. No penalty for wrong answers. (Keep it simple — a more complex rule is fine if the implementer wants, but document it.)

---

## 8. Screens & Navigation

Six screens; single Activity (`MainActivity`) hosts a `NavHost`. Routes:

| Route | Screen | Args |
|---|---|---|
| `splash` | Splash | — |
| `menu` | Main Menu | — |
| `category` | Category Selection | — |
| `difficulty/{categoryId}` | Difficulty + Settings | `categoryId: String` |
| `quiz/{categoryId}/{difficulty}` | Quiz gameplay | `categoryId`, `difficulty` |
| `results/{score}/{total}/{correct}` | Results | `score`, `total`, `correct` |

Pop behavior: from Results, `popUpTo("menu")` so the back stack doesn't accumulate quiz sessions.

### 8.1 Splash Screen
- Background: deep Mediterranean blue (`#1B3A5C` or similar — see §12).
- Centered: white Roman-column icon (vector), Arabic title `تونس`, English title `Tunisia Heritage Quest` in an elegant serif.
- Visible for ~1.5s, then auto-navigates to Menu.
- Implement using either Android 12+ `SplashScreen` API + a Compose splash for older devices, **or** a simple Compose splash with a `LaunchedEffect` delay. Either is acceptable; pick one and be consistent.

### 8.2 Main Menu
- Title: "Tunisia Heritage Quest".
- Three stat tiles in a row: **Sites** (count), **Mastery** (% from `PlayerStats`), **Streak** (days). Pulled from `StatsRepository`.
- Big primary button: **START QUIZ** → navigates to Category Selection.
- (Optional) secondary text button: **Settings** → opens a settings sheet/screen reusing the toggles from Screen 4.

### 8.3 Category Selection
- Top app bar with back arrow + "Choose Category" title.
- 2-column grid of 6 cards: Roman, Islamic, Punic, Modern, Nature, Cities.
- Each card shows: category icon, category name, "{N} questions" subtitle, and a small horizontal progress bar indicating how many questions in that category the user has answered correctly at least once.
- Tapping a card navigates to `difficulty/{categoryId}`.

### 8.4 Difficulty & Settings
- Title: "Select Difficulty".
- Three difficulty cards (Easy / Medium / Hard) each with: icon, label, one-line description, time-per-question label, and a `PLAY` button.
  - Easy → "Famous landmarks · 15 seconds per question"
  - Medium → "Historical sites · 20 seconds per question"
  - Hard → "Archaeological details · 15 seconds, no hints"
- Below: "Game Options" panel with three switches:
  - Timer Enabled
  - Sound Effects
  - Haptic Feedback
- Switches persist via `DataStore`. They take effect immediately (no save button).
- Tapping `PLAY` on a difficulty navigates to the quiz with chosen category + difficulty.

### 8.5 Quiz Gameplay (the main loop)
- Top bar: progress text "Question X of N" + small countdown if timer enabled.
- Monument image (Coil, with placeholder + error states). Caption "Source: …" below the image.
- Question prompt below image.
- Four answer options as cards/buttons (A / B / C / D layout vertical).
- `SUBMIT ANSWER` button at the bottom, disabled until an option is selected.
- After submit, transition to a feedback overlay/state:
  - **Correct:** green check, "Correct! +10 points", auto-advance after ~1.5s.
  - **Incorrect:** red X, "Incorrect!", correct answer label, fun fact (e.g. "Built in 238 AD, this is one of the best preserved Roman amphitheaters"), tap to continue.
- If timer expires before submit → counts as incorrect, show feedback, advance.
- After last question, navigate to Results.
- **Settings honored here:** timer toggle, sound effect on submit (correct / incorrect), haptic feedback on submit.

### 8.6 Quiz Results
- Big score: "Your score: 80 / 100" + percentage.
- Performance message based on % correct:
  - 90–100: "Heritage Master!"
  - 70–89: "Well done."
  - 50–69: "Not bad — keep exploring."
  - <50: "Plenty of monuments left to discover."
- Breakdown: correct count, time taken (optional), category, difficulty.
- Two buttons: **Play Again** (restarts same category/difficulty) and **Back to Menu**.
- On entering this screen, persist updated `PlayerStats` (increment sitesCompleted, update mastery %, update streak if eligible).

---

## 9. Functional Requirements Summary

| ID | Requirement |
|---|---|
| F-01 | User can start a quiz from the menu and pick any of 6 categories. |
| F-02 | User can pick Easy / Medium / Hard before each quiz. |
| F-03 | User can toggle timer, sound, and haptics; toggles persist across launches. |
| F-04 | Each quiz contains up to 8 questions sampled from the chosen category **at the chosen difficulty**. If fewer than 8 questions exist for that (category, difficulty) combination, the quiz uses all available ones. Question order randomized per session; option order randomized per question. |
| F-05 | Each correct answer awards 10 points. Final score = correct × 10. |
| F-06 | Timer (when enabled) auto-submits the question as incorrect on expiry. |
| F-07 | After each question, the app shows correct/incorrect feedback + a fun fact for incorrect answers. |
| F-08 | Results screen shows score, percentage, and a performance message. |
| F-09 | Stats (Sites, Mastery, Streak) persist via Room and reflect on the menu. |
| F-10 | App correctly survives configuration changes (rotation) without losing quiz progress. |
| F-11 | Back navigation from any screen behaves predictably (system back follows the screen stack). |

---

## 10. Non-Functional Requirements

- **Performance:** Cold start to splash visible in <1.5s on a mid-range device. Quiz screen interactions feel immediate (<100ms button feedback).
- **Reliability:** No crashes during a 10-question run-through. Image-load failures show a placeholder, never blank space.
- **Accessibility:** All interactive elements have content descriptions. Tap targets ≥48dp. Color contrast ≥ WCAG AA. Don't rely on color alone for correct/incorrect feedback (use icon + text).
- **Localization-readiness:** All strings live in `strings.xml`. (Adding French/Arabic later should be a string-file change.)
- **Offline:** App fully functional offline if images are bundled. If image URLs are used, app must still launch and show a placeholder.

---

## 11. Activity Lifecycle (rubric: 10 pts)

The app is single-activity, so most lifecycle work is on `MainActivity`. The implementing LLM must:
- Override and add **distinct** log statements for `onCreate`, `onStart`, `onResume`, `onPause`, `onStop`, `onRestart`, `onDestroy`. Use a single `TAG` constant.
- Demonstrate state preservation across rotation: the active `QuizViewModel` retains its session (via `viewModelScope` + `SavedStateHandle` for the current question index, score, and selected answer).
- Handle `onPause` during a quiz: pause the timer; resume in `onResume`.
- Persist sound/haptic/timer settings before `onStop` (DataStore writes are async-safe but ensure no UI state is lost).
- Add brief KDoc on `MainActivity` explaining which lifecycle stages do what — this also helps the rubric grader see the work.

---

## 12. UI / UX Design (rubric: 15 pts)

The visual direction is anchored to a reference mockup combining a warm cream background, a saturated teal primary, soft pastel category cards, fully rounded shapes, and an Islamic geometric pattern accent band. Light mode is primary; dark mode is supported but secondary.

### 12.1 Color tokens

| Token | Light | Notes |
|---|---|---|
| `primary` | `#0E7C7B` | Saturated teal — used for CTAs, selection state, headings |
| `onPrimary` | `#FFFFFF` | |
| `primaryContainer` | `#D4EDE9` | Soft mint — selected option background, primary surface tint |
| `secondary` | `#F4845F` | Warm coral — streak indicator, decorative accents |
| `tertiary` | `#F4B942` | Gold — stars, highlights, "Mastery" stat |
| `background` | `#FAF5EB` | Warm cream — base canvas |
| `surface` | `#FFFFFF` | Cards, buttons |
| `surfaceVariant` | `#F4EFE3` | Subtle fill for inactive elements |
| `surfacePeach` | `#F8E0CC` | Soft peach card background (one of the menu tiles) |
| `surfaceBlue` | `#DDEBF4` | Soft blue card background (one of the menu tiles) |
| `outline` | `#D8D2C2` | Borders, dividers |
| `error` | `#B3261E` | Incorrect-answer red |
| `success` | `#2E8B57` | Correct-answer green |
| `onBackground` | `#2A2620` | Body text |
| `onSurfaceMuted` | `#7A7468` | Secondary text, captions |

Dark mode: keep the same primary/secondary/tertiary hues, swap background to a deep warm brown (`#1F1A14`) and surface to `#2A2620`. Let the implementer derive the rest.

### 12.2 Typography

- **Display** (app title, screen titles): a stylized serif. Use **Fraunces** via `androidx.compose.ui:ui-text-google-fonts` if the implementer wants the closest match to the mockup; **Playfair Display** is an acceptable fallback. Title weight 700, slight optical size opt-in if Fraunces is used.
- **Body / UI** (buttons, captions, options): **Inter** (or **DM Sans**). Weight 400 for body, 600 for emphasis.
- Never use the serif for body copy or option labels — only for titles and numbers (score, streak count).

### 12.3 Shape

- **Cards:** corner radius `24.dp`. Soft elevation (Material 3 `Level 1`), no hard borders.
- **Primary buttons (CTAs):** pill-shaped — corner radius equals half the height (`28.dp` for a `56.dp` button). Filled teal with white text.
- **Answer option rows:** rounded `20.dp`, full width, `64.dp` tall, with a circular `A` / `B` / `C` / `D` badge on the leading edge. Selected state: `primaryContainer` background + teal border + check icon on trailing edge.
- **Stat tiles** (Sites / Mastery / Streak on the menu): `24.dp` radius, alternating soft mint / soft peach / soft blue backgrounds.
- **Chips & counters** (timer pill, streak pill): pill-shaped, white surface, colored icon + text. Streak uses the coral accent + a flame icon; timer uses coral when <5s remaining, otherwise neutral.

### 12.4 Decorative elements

- **Geometric pattern band** along the bottom of the menu screen (just above any nav area) and as a thin strip under section titles on the category and difficulty screens. Implement as a tileable vector drawable (`ic_pattern_zellige`) using primary/coral/gold tones at low opacity. Don't overuse — one band per screen at most.
- **Splash screen**: deep teal background, white Roman-column icon, Arabic title `تونس` in serif, English title in serif below. Subtle pattern band at the very bottom.
- **Menu hero card**: large rounded card with a layered image collage of monuments behind a gradient overlay, primary CTA "Play Now" sits on the bottom edge of the card (per mockup). If the collage is too much work, a single hero photograph with a subtle teal gradient is acceptable.

### 12.5 Motion

- Crossfade between screens (`AnimatedNavHost`).
- Selected answer: scale-in + color transition (200ms).
- Correct/incorrect feedback overlay: scale-in from 0.85 to 1.0 with a small overshoot, fade out after 1.5s.
- Timer pill: subtle pulse on the last 3 seconds.
- No Lottie. No full-screen confetti. Keep motion confident and quick.

### 12.6 Iconography

- Material Symbols (rounded variant) for system icons.
- Custom vector drawables for: Roman column (splash + Roman category), mosque silhouette (Islamic), Punic anchor (Punic), modern building outline (Modern), palm/leaf (Nature), city skyline (Cities), flame (streak), star/medal (mastery).
- All custom icons share a 1.5dp stroke weight at 24dp to feel like one set.

### 12.7 States

Every async surface (image, stats, question list) must have explicit **loading**, **empty**, and **error** states. Image loading specifically:

- **Loading**: shimmering placeholder with the cream surface tint, never a blank white box.
- **Error / no network**: a neutral monument-silhouette placeholder drawable (`q_placeholder`) with a small "Image unavailable" caption. The quiz must still be playable — never block answering on a failed image load.

### 12.8 Accessibility

- All interactive elements have content descriptions.
- Tap targets ≥48dp.
- Color contrast ≥ WCAG AA on text.
- Don't rely on color alone for correct/incorrect — always pair with an icon (✓ / ✕) and text.

---

## 13. Code Style & "Human-feel" Guidelines

This is an explicit ask: the code must not read like a textbook AI dump. The implementing LLM should follow these rules:

1. **No emoji in code or comments.**
2. **Comments are sparse and useful.** Don't comment what the code obviously says. Do comment intent, edge cases, "why this and not that".
3. **Mix comment styles.** Sometimes a single-line `// note: …`, sometimes a 2-3 line block, occasionally a KDoc on a public API. Don't put a KDoc on every private function.
4. **Allow occasional informal phrasing.** Comments like `// keep this until we wire up the real images` or `// good enough for the assignment scope` are welcome where they fit.
5. **Realistic naming.** Mostly descriptive (`currentQuestionIndex`), but it's fine to have shorter names in tight scopes (`val q = questions[i]`). Don't over-rename.
6. **Don't create wrapper classes "for clarity"** when a `data class` or even a `Pair` is enough.
7. **Don't extract every constant to a `Constants.kt`.** Keep magic numbers near their usage when there's only one usage.
8. **Imports stay clean** but file-internal ordering doesn't have to be alphabetical.
9. **A couple of small, harmless TODOs are fine** — e.g. `// TODO: replace with proper analytics later`. Don't sprinkle them everywhere.
10. **Commit-message style if the LLM emits one:** lowercase, imperative, occasionally terse. (`add quiz timer`, not `feat(quiz): implement comprehensive timer functionality`.)
11. **No "this code was generated" boilerplate, no `// AI:` markers, no banner comments at the top of files.**
12. **Don't over-engineer.** Use a sealed class when there are clearly multiple states; otherwise a single data class is fine.
13. **One or two minor stylistic inconsistencies are acceptable** (e.g. some screens use `Modifier.fillMaxWidth().padding(16.dp)`, others use `Modifier.padding(horizontal = 16.dp).fillMaxWidth()`). Real codebases have this.

---

## 14. Testing Requirements (rubric: 15 pts)

All three categories must be present and meaningful. **Aim for ~25-35 total tests**, not 200, but every test must do real work — no `assertTrue(true)` filler.

### 14.1 Unit Tests (`src/test/`)
Plain JVM tests with JUnit4. Examples to implement:
- `QuestionBankTest` — verifies each category returns the expected number of questions and every question has exactly 4 unique options with `correctIndex in 0..3`.
- `ScoringTest` — `awardPoints(correct=true)` returns 10; `correct=false` returns 0.
- `QuizSessionReducerTest` (if you extract a reducer/use case) — submitting an answer advances the index, increments score correctly, marks completion.
- `PerformanceMessageTest` — `messageFor(percent)` returns correct bucket text.
- `DifficultyTest` — `Difficulty.EASY.secondsPerQuestion == 15`, etc.

### 14.2 ViewModel Tests (`src/test/`)
Use coroutines test (`StandardTestDispatcher` + `runTest`) and Turbine for `StateFlow`.
- `QuizViewModelTest`:
  - emits initial loading state, then a populated state after `start(category, difficulty)`.
  - `selectOption(idx)` updates UI state without advancing.
  - `submit()` while option selected → produces `AnswerOutcome` with correct points, advances `currentIndex`.
  - timer expiry path → marks question incorrect.
  - last question submit → emits a one-shot `NavigateToResults` event via `SharedFlow`.
- `MenuViewModelTest`:
  - exposes `PlayerStats` from a fake repository.
- `SettingsViewModelTest`:
  - toggling `timerEnabled` writes to the fake `SettingsRepository` and re-emits state.

Use **hand-written fakes** for the repositories (not MockK) for readability. One `FakeQuizRepository`, one `FakeStatsRepository`, one `FakeSettingsRepository`.

### 14.3 Navigation / UI Tests (`src/androidTest/`)
Compose UI test + `androidx.navigation:navigation-testing`:
- `NavigationTest`:
  - From menu, click **START QUIZ** → lands on Category Selection.
  - From category selection, click "Roman" → lands on Difficulty for `roman`.
  - From difficulty, click **PLAY** under Easy → lands on Quiz route with correct args.
  - System back from Quiz → returns to Difficulty.
- `QuizScreenTest`:
  - Renders the question prompt and 4 options.
  - `SUBMIT ANSWER` is disabled until an option is selected.
  - After submit with correct answer, the "+10 points" feedback shows.
- `SettingsToggleTest`:
  - Toggling Sound Effects updates the toggle's checked state and persists across recomposition.

> Use `createAndroidComposeRule<MainActivity>()` for nav tests and `createComposeRule()` for isolated screen tests.

---

## 15. Adaptive UI (rubric: 15 pts)

Support phone portrait, phone landscape, foldable inner display, and tablet. Concrete rules:
- Use `WindowSizeClass` (`androidx.compose.material3.windowsizeclass`) to branch layouts.
- **Compact width:** current designs (single column, 2-column grid for categories).
- **Medium / Expanded width:** category grid becomes 3 columns; quiz screen places the image on the left and the answer options on the right (50/50 split).
- Splash and Results stay centered with a max content width on large screens — don't let buttons stretch across a tablet.
- Use `Modifier.widthIn(max = 600.dp)` on content containers where stretching would look bad.
- Test with the resizable emulator or `@Preview` on multiple device specs (Pixel 5, Pixel Tablet, Foldable unfolded).
- Configuration changes (rotation) preserve the active quiz session.

---

## 16. Acceptance Criteria → Rubric Mapping

| Rubric Item | Pts | Where it's satisfied |
|---|---|---|
| Architecture Components | 15 | §5 (MVVM), §6 (structure), Room + DataStore + ViewModel + StateFlow + Repository |
| Navigation Components | 10 | §8 (NavHost, typed routes, args, popUpTo) |
| Activity Lifecycle | 10 | §11 (logged callbacks, SavedStateHandle, timer pause/resume) |
| Testing | 15 | §14 (unit + VM + UI/Nav) |
| Adaptive UI | 15 | §15 (WindowSizeClass branches, max-width containers, rotation safety) |
| UI/UX | 15 | §12 (palette, typography, motion, empty/loading/error states, accessibility) |
| Functionality | 10 | §9 functional reqs F-01 … F-11 all working end-to-end |
| Code Quality | 10 | §13 (clean naming, sparse comments, no over-engineering, no AI banners) |

---

## 17. Assets & Content

**Question content.** All six categories are fully implemented. The question bank (`QuestionBank.kt`) is supplied alongside this PRD and contains **72 questions** distributed as follows:

| Category | Easy | Medium | Hard | Total |
|---|---|---|---|---|
| Roman Heritage | 5 | 4 | 4 | 13 |
| Islamic Heritage | 5 | 4 | 4 | 13 |
| Punic & Pre-Roman | 4 | 3 | 3 | 10 |
| Modern Heritage | 5 | 3 | 3 | 11 |
| Natural & Mixed Sites | 5 | 3 | 4 | 12 |
| Cities | 5 | 4 | 4 | 13 |

The category selection screen shows actual question counts driven by the bank (no "coming soon" placeholders, no disabled cards). Quizzes pull `min(8, available)` questions per session — a Hard quiz in some categories will be 3-4 questions and that is intentional.

**Conventions used in `QuestionBank.kt`:**
- The correct answer is always at index 0 in `Question.options`. The `QuizViewModel` shuffles per session, so the UI never sees them in source order. This makes the data file readable when reviewing.
- All distractors are **real Tunisian sites** in the same category — no out-of-region or fabricated options.
- Each question carries a one-or-two-sentence `funFact` shown on the incorrect-answer feedback screen.
- Each question carries a `wikipediaTitle` (the URL slug of the corresponding Wikipedia article, e.g. `Amphitheatre_of_El_Jem`). Images are resolved at runtime — no drawables to bundle.

### 17.1 Image strategy: Wikipedia REST API

Bundling 70 photos manually is rejected. Instead, the app resolves images at runtime from Wikipedia's public REST API. The student does **zero image work**.

For each question, the implementing LLM must:

1. Build a `WikipediaImageRepository` interface:
   ```kotlin
   interface WikipediaImageRepository {
       suspend fun imageUrlFor(title: String): String?  // returns thumbnail URL or null
   }
   ```
2. Implement it against `https://en.wikipedia.org/api/rest_v1/page/summary/{title}`. The JSON response includes a `thumbnail.source` field — that's the image URL Coil will load. (`originalimage.source` is a higher-resolution version; prefer `thumbnail` for mobile.)
3. Cache results in memory for the session and on disk via Room (`CachedImageEntity { wikipediaTitle: String, imageUrl: String, fetchedAt: Long }`) so the network is hit at most once per question across the lifetime of the install.
4. Use Coil to load the resolved URL in the quiz screen. Coil itself caches the bytes — so the disk cache layer above only stores the URL→title mapping, not the image.
5. Pre-warm: when a quiz session starts, kick off resolution for **all** questions in the session in parallel, so the user doesn't hit a loading spinner on every question.

### 17.2 Failure handling

- If the API call fails (no network, 404 on title), fall back to a bundled `q_placeholder` drawable showing a neutral monument silhouette. The quiz must still be playable.
- If the API returns no `thumbnail` (rare — happens for very stub articles), use the same placeholder.
- A misspelled `wikipediaTitle` in `QuestionBank.kt` is a content bug, not a runtime crash. Log it at WARN and show the placeholder.
- Never block the UI on image resolution. Show the question + options immediately; the image area shows the loading shimmer until resolved.

### 17.3 Required deps

- `io.coil-kt:coil-compose` for image loading (already in §4).
- A small HTTP client. **Ktor Client** (`io.ktor:ktor-client-android` + `ktor-client-content-negotiation` + `ktor-serialization-kotlinx-json`) is preferred for Kotlin idiom; **OkHttp + kotlinx-serialization** is acceptable. No Retrofit unless the implementer already has it.
- `kotlinx-serialization-json` for parsing the API response.

### 17.4 Attribution

Every quiz screen displays "Source: Wikipedia" (or the per-question `imageSource` value, which defaults to "Wikimedia Commons") as a small caption beneath the image, as shown in the Screen 5 mockup. This satisfies attribution for in-app educational use under CC-BY-SA / public domain.

### 17.5 Required bundled drawables

Despite zero monument photos, the app still ships a small set of vector/raster drawables:
- `q_placeholder` — fallback for failed image loads (neutral monument silhouette).
- `ic_pattern_zellige` — geometric pattern tile for decorative bands.
- `ic_column_roman`, `ic_mosque`, `ic_anchor_punic`, `ic_building_modern`, `ic_leaf_nature`, `ic_skyline_city` — category icons (vector drawables).
- App launcher icon (adaptive).

These the student must supply, but it's a manageable handful rather than 70 photos.

---

## 18. Out of Scope (do not build)

- Login / accounts.
- Cloud sync of stats.
- Question editor / admin UI.
- In-app purchases or ads.
- Achievements system beyond the simple Streak counter.
- Sharing results to social media.

---

## 19. Companion Files & Remaining Inputs

This PRD ships with one companion file:

1. **`PRD.md`** — this document. Defines architecture, screens, requirements, testing, rubric mapping.
2. **`QuestionBank.kt`** — drop directly into `app/src/main/java/<package>/data/questions/`. 72 fully written questions with prompts, options, correct indices, fun facts, and Wikipedia article titles for runtime image resolution.

(The previous `Image_Sourcing_Guide.md` is now obsolete — images are fetched from Wikipedia at runtime.)

**Resolved decisions** (no longer open):
- ~~Chosen category~~ → all 6 categories implemented.
- ~~Question list~~ → see `QuestionBank.kt`.
- ~~Image strategy~~ → Wikipedia REST API + Coil at runtime (§17.1). No image uploads required.
- ~~Image files (70 photos)~~ → no longer needed.

**Still required from the student before final build:**
1. **A handful of bundled drawables** (see §17.5):
   - `q_placeholder` — neutral monument silhouette for failed image loads.
   - `ic_pattern_zellige` — geometric pattern tile (decorative band).
   - 6 category icons (Roman column, mosque, Punic anchor, modern building, nature leaf, city skyline).
   - Adaptive launcher icon.
   These can be vector drawables hand-drawn in Android Studio's Vector Asset Studio or sourced from Material Symbols / The Noun Project.
2. **Package name** — default suggestion: `com.<student-handle>.heritagequest`. Update `applicationId` in `app/build.gradle.kts` and the package declaration at the top of `QuestionBank.kt` to match.
3. **(Optional)** Preferred Google Font names if different from §12 (Fraunces / Inter).

Once those are in, the project should build, run, fetch images automatically on first quiz, and pass all unit + UI tests.

---

## 20. Build & Run

```bash
# from project root
./gradlew :app:assembleDebug          # build
./gradlew :app:testDebugUnitTest       # unit + VM tests
./gradlew :app:connectedDebugAndroidTest   # nav + UI tests (needs emulator/device)
```

Implementer should ensure both `:app:testDebugUnitTest` and `:app:connectedDebugAndroidTest` pass before declaring the task complete.
