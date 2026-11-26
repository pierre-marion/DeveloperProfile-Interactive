# Profil développeur – Amélioration UI (Jetpack Compose)

Application Android réalisée avec **Jetpack Compose** et **Material Design 3**.

> Projet réalisé avec l'aide d'une IA (Claude), dans le cadre d'un exercice d'entraînement.

## Description

L'application affiche le profil d'un développeur sur un seul écran : un en-tête avec avatar et badge de disponibilité, une courte présentation, une liste de compétences qui défile horizontalement, une liste de projets et une zone de contact avec une action « Contacter ».

Toutes les données sont **locales** et écrites directement en Kotlin (`sampleProfile()` et `sampleJuniorProfile()`). Aucune API, aucune base de données, aucun ViewModel ni Navigation.

Cette version fait évoluer la première fiche « Profil développeur » (dépôt séparé) en appliquant les notions du chapitre Compose UI.

## Capture d'écran

![Capture de l'application](screenshot.png)

## Composants Compose utilisés

| Composant | Utilisation |
|---|---|
| `Text` | Nom, rôle, titres de section, descriptions, informations |
| `Button` | Bouton « Contacter » (callback `onContactClick`) |
| `Icon` | Icône des cartes projet, icônes de l'email, de la localisation et de la disponibilité |
| `Image` | Avatar placeholder du développeur (découpé en cercle) |
| `Card` | Section « À propos » et chaque projet (`ProjectCard`) |
| `OutlinedTextField` | Email affiché en lecture seule (`readOnly = true`) |
| `Surface` | Badge de disponibilité, pastilles de compétences, zone de contact |

## Layouts utilisés

| Layout | Utilisation |
|---|---|
| `Column` | Organisation verticale du contenu des cartes et des sections |
| `Row` | En-tête (avatar + nom/rôle), ligne titre/année des projets, lignes d'information du contact, bouton + lien portfolio |
| `Box` | Avatar avec le badge « Dispo / En poste » superposé en bas à droite (`Modifier.align(Alignment.BottomEnd)`) |
| `Spacer` | Espacements entre les éléments |
| `Surface` | Zone de contact cohérente avec le thème (`primaryContainer`) |

## Listes : LazyColumn et LazyRow

- **`LazyColumn`** : `ProfileScreen` est une seule liste verticale. Elle contient des éléments uniques avec `item { }` (en-tête, description, compétences, titre « Projets », contact) et une collection avec `items(profile.projects, key = { it.id })`. Chaque projet est affiché par le composable réutilisable **`ProjectCard`**. La `key` donne une identité stable à chaque projet.
- **`LazyRow`** : dans `SkillsSection`, les compétences défilent horizontalement. Chaque compétence est affichée par le composable réutilisable **`SkillChip`**.

## Choix du thème

Le thème est défini dans `ui/theme/Theme.kt` et `ui/theme/Type.kt` et appliqué avec `DeveloperProfileTheme`.

- **Couleurs (`ColorScheme`)** : une identité **violette**. En clair, un violet foncé (`primary = #6750A4`) sur un fond très clair. En sombre, un violet clair (`primary = #D0BCFF`) sur un fond très sombre, pour garder un bon contraste. Les couleurs `on...` garantissent un texte lisible sur chaque fond. La couleur `tertiary` (verte) sert au badge « Dispo ».
- **Couleur dynamique désactivée** : le thème n'utilise pas Material You (`dynamicColor`) pour que l'application garde toujours sa propre identité violette.
- **Typographie (`ProfileTypography`)** : une hiérarchie claire. `headlineMedium` en gras pour le nom, `titleLarge` pour les titres de section, `titleMedium` pour les titres de projets, `bodyMedium` pour les textes, `labelLarge` / `labelMedium` pour les pastilles et badges.
- **Formes (`ProfileShapes`)** : arrondis de 8 à 32 dp. `shapes.large` pour les cartes et la zone de contact, `shapes.extraLarge` pour les pastilles de compétences, `shapes.small` pour le badge.
- **Clair / sombre** : le thème suit `isSystemInDarkTheme()`. Les composants n'utilisent aucune couleur fixe, seulement `MaterialTheme.colorScheme`, donc tout l'écran s'adapte automatiquement.

## Previews

- `ProfileScreenLightPreview` : profil principal, thème clair
- `ProfileScreenDarkPreview` : profil principal, thème sombre
- `JuniorProfilePreview` : un autre profil (développeur Android junior disponible pour un stage), thème clair

## Quelles notions du chapitre Compose UI avez-vous réutilisées dans cette application ?

J'ai réutilisé les **composants fondamentaux** (Text, Button, Icon, Image, Card, OutlinedTextField en lecture seule), les **layouts** (Column, Row, Box pour superposer un badge sur l'avatar, Spacer, Surface), les **listes modernes** (LazyColumn pour l'écran complet avec `item` et `items`, LazyRow pour les compétences) avec des **cartes réutilisables** (`ProjectCard`, `SkillChip`), et la **personnalisation Material Design 3** (ColorScheme, Typography, Shapes, thème clair et sombre). J'ai aussi gardé les bonnes pratiques du premier devoir : composables courts avec des paramètres, `modifier: Modifier = Modifier`, callbacks pour les actions, accessibilité (`contentDescription`, `Role.Button`) et plusieurs previews.
