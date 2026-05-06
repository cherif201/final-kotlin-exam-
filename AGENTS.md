# AGENTS.md — Guide rapide pour agents et nouveaux contributeurs

But: aide un agent (ou dev) à devenir productif rapidement dans ce dépôt Android Kotlin Compose.

1) Vue d'ensemble (big picture)
- Type de projet : application Android single-module (`:app`) Kotlin + Jetpack Compose (single-activity). Voir `settings.gradle.kts` (include ":app") et `app/src/main/java/.../MainActivity.kt`.
- UI : Compose + Material3 (dépendances dans `app/build.gradle.kts`, thème dans `app/src/main/java/.../ui/theme/`)
- Versions et dépendances centralisées : `gradle/libs.versions.toml` (aliases libs/plugins). Les plugins sont appliqués via alias dans `build.gradle.kts`.
- Entrée de l'application : `MainActivity` (single-activity, utilise `Scaffold`, `setContent { ... }`).

2) Architecture et motifs observés
- Single-Activity pattern (launcher Activity = `MainActivity`). Pas de navigation multi-module visible ni de DI frameworks (aucune dépendance comme Hilt/DI détectée).
- Composables simples et previews : `Greeting`, `GreetingPreview` dans `MainActivity.kt` et thème réutilisable `MohamedCherifKhcherifTheme`.
- Ressources déclaratives : thèmes/colors/typography dans `app/src/main/java/.../ui/theme/` ; strings et themes XML dans `res/values/`.

3) Fichiers clés à lire / modifier (exemples absolus)
- C:\Users\wwwkh\AndroidStudioProjects\mohamedcherifkhcherif\app\build.gradle.kts — configuration du module app (minSdk=24, targetSdk=36, compose=true)
- C:\Users\wwwkh\AndroidStudioProjects\mohamedcherifkhcherif\gradle\libs.versions.toml — versions centralisées (AGP, Kotlin, Compose BOM)
- C:\Users\wwwkh\AndroidStudioProjects\mohamedcherifkhcherif\settings.gradle.kts — structure du projet
- C:\Users\wwwkh\AndroidStudioProjects\mohamedcherifkhcherif\app\src\main\java\com\example\mohamedcherifkhcherif\MainActivity.kt — point d'entrée et exemples de Composables
- C:\Users\wwwkh\AndroidStudioProjects\mohamedcherifkhcherif\app\src\main\java\com\example\mohamedcherifkhcherif\ui\theme\{Color.kt,Theme.kt,Type.kt} — tokens UI et logique de thème
- C:\Users\wwwkh\AndroidStudioProjects\mohamedcherifkhcherif\app\proguard-rules.pro — règles proguard pour release

4) Workflows essentiels (commandes, Windows PowerShell)
- Build debug: .\gradlew.bat assembleDebug
- Install on device (debug): .\gradlew.bat installDebug
- Build release (signed/configure signing separately): .\gradlew.bat assembleRelease
- Run unit tests: .\gradlew.bat test
- Run instrumentation tests on connected device/emulator: .\gradlew.bat connectedAndroidTest
- Clean: .\gradlew.bat clean
- Useful to run after dependency changes: .\gradlew.bat --refresh-dependencies assembleDebug

Exemple (PowerShell):

    .\gradlew.bat clean; .\gradlew.bat assembleDebug

5) Conventions et patterns spécifiques au projet
- Versions gérées via `gradle/libs.versions.toml` — ne pas hardcoder versions dans `app/build.gradle.kts` ; utiliser `libs.` alias comme existant.
- Plugins appliqués avec `alias(libs.plugins.*)` au niveau top et module. Si vous ajoutez un plugin, mettez à jour `gradle/libs.versions.toml` et utilisez l'alias.
- UI/theme : `MohamedCherifKhcherifTheme` expose `darkTheme` et `dynamicColor` flags. Respecter cet API quand vous ajoutez Composables.
- Code style Kotlin : `gradle.properties` indique `kotlin.code.style=official`.
- Resources naming: mipmap/ic_launcher*, drawable/*.xml, values/*.xml — suivre noms existants pour nouveaux assets.

6) Tests et outils de débogage
- Tests unitaires : JUnit (libs dans `libs.versions.toml`) ; exemples dans `app/src/test/...`.
- Instrumented tests : AndroidJUnit4 + InstrumentationRegistry ; voir `app/src/androidTest/...`.
- Compose tooling: `debugImplementation(libs.androidx.compose.ui.tooling)` est inclus — utilisez `@Preview` et l'inspecteur Compose au besoin.
- ProGuard/R8 : release build uses `proguard-rules.pro`. Vérifiez si vous ajoutez libs natives ou reflection-heavy libs.
 - Attendu pour tout agent : ajouter des tests unitaires, des tests de navigation, et des tests de ViewModel (model view) quand vous touchez aux features concernées.

7) Intégrations externes & points d'attention
- Aucune API réseau, DB, ou DI externe détectée dans le code actuel — le projet est minimal. Si vous ajoutez des dépendances, mettez-les dans `libs.versions.toml`.
- SDK path localisé dans `local.properties` (ne committez ce fichier). Documenter toute autre variable locale nécessaire.
- AGP/Kotlin versions : voir `gradle/libs.versions.toml` (agp=9.0.1, kotlin=2.0.21). Garder cohérence en cas de mise à niveau.

8) Recommandations pour un agent modifiant le dépôt
- Modifications de dépendances: modifier `gradle/libs.versions.toml` (ajouter versions et libraries) puis référencer via `libs.` dans `app/build.gradle.kts`.
- Modifications de plugin: mettre à jour les entrées dans `[plugins]` du toml et utiliser `alias(libs.plugins.*)`.
- Respecter le style Kotlin et conserver l'API publique des thèmes/Composables.
- Tester localement : exécuter `clean`, `assembleDebug`, `test` et `connectedAndroidTest` quand pertinent.
- Ne pas committer `local.properties` ; vérifier `gradle.properties` pour flags utiles.

9) Exemples concrets utiles pour code edits
- Ajout d'une dépendance (ex.) :
  1. Edit `gradle/libs.versions.toml` → ajouter version et library.
  2. Edit `app/build.gradle.kts` → add implementation(libs.your.new.lib).
  3. Run: .\gradlew.bat --refresh-dependencies assembleDebug

- Ajouter un Composable réutilisable :
  - Créer fichier dans `app/src/main/java/.../ui/` avec même package `com.example.mohamedcherifkhcherif.ui`.
  - Respecter `MohamedCherifKhcherifTheme` pour previews.

10) Fichiers de référence rapides (chemins)
- Project root: `build.gradle.kts`, `settings.gradle.kts`, `gradle/libs.versions.toml`, `gradle.properties`
- Module app: `app/build.gradle.kts`, `app/src/main/AndroidManifest.xml`, `app/src/main/java/.../MainActivity.kt`, `app/src/main/java/.../ui/theme/*`

11) FAQ courte
- Q: Où changer une version de Compose ? A: `gradle/libs.versions.toml` → `composeBom`.
- Q: Comment activer une feature Compose supplémentaire ? A: modifier `app/build.gradle.kts` → `buildFeatures { compose = true }` puis ajouter dépendance via toml.

---
Fichiers lus pour générer ce guide: (extraits)
- C:\Users\wwwkh\AndroidStudioProjects\mohamedcherifkhcherif\app\build.gradle.kts
- C:\Users\wwwkh\AndroidStudioProjects\mohamedcherifkhcherif\gradle\libs.versions.toml
- C:\Users\wwwkh\AndroidStudioProjects\mohamedcherifkhcherif\settings.gradle.kts
- C:\Users\wwwkh\AndroidStudioProjects\mohamedcherifkhcherif\app\src\main\java\com\example\mohamedcherifkhcherif\MainActivity.kt

Si vous voulez, je peux maintenant: 1) ajouter un README.md similaire ; 2) étendre AGENTS.md avec checklists de PRs et templates de commit ; 3) créer scripts Gradle personnalisés pour CI.
