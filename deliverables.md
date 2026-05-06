# Deliverables

## 2026-05-06

- Finished the `Implement ViewModels + quiz logic` task from the interrupted agent run.
- Completed the quiz loop wiring: timed questions, answer feedback, result navigation, replay support, and runtime image resolution/preload hooks.
- Fixed AGP 9 + built-in Kotlin/KSP build blockers so `:app:assembleDebug` succeeds again.
- Added focused regression tests for quiz flow and results messaging.
- Finished the Mediterranean blue UI overhaul for splash, menu, category, difficulty/settings, quiz, and results screens.
- Added a reusable heritage-themed Compose design system with custom palette, typography, cards, buttons, ornaments, and patterned surfaces.
- Fixed the difficulty `PLAY` button sizing so taps are reliable on-device.
- Enabled runtime Wikipedia image loading by adding internet permission and a safer API request setup.
- Removed explicit "Mediterranean Blue" marketing text from the home screen UI.
- Hooked quiz feedback into the settings toggles so sound effects and haptic feedback now trigger during answer resolution.
- Added an offline image asset checklist with exact filenames for every quiz question.
- Replaced runtime Wikipedia summary lookups with a baked-in map of direct Wikimedia image URLs for all quiz titles, plus safe aliases for stubborn pages.
- Switched image loading to prefer bundled asset files named by question id under `app/src/main/assets/quiz_images/`, with remote fallback for questions that still do not have local files.
- Added an academic LaTeX report for the IT370 project, including sections for architecture, testing, and the controlled use of AI.
- Revised the academic report to include a formal title page, a table of contents, and embedded testing evidence from the `report_assets` folder.
- Added a repository README covering the app overview, feature set, tech stack, setup, testing, assets, and academic report links.
