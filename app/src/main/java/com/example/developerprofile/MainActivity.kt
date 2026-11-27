package com.example.developerprofile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.developerprofile.ui.theme.DeveloperProfileTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            DeveloperProfileTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    ProfileContainer(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

/* ############### Données locales ################ */

data class ProjectUi(
    val id: Int,
    val title: String,
    val description: String,
    val technologies: String,
    val year: String
)

data class ExperienceUi(
    val id: Int,
    val title: String,
    val place: String,
    val period: String,
    val description: String
)

data class LinkUi(
    val label: String,
    val url: String
)

data class DeveloperProfileUi(
    val name: String,
    val role: String,
    val description: String,
    val skills: List<String>,
    val projects: List<ProjectUi>,
    val experiences: List<ExperienceUi>,
    val links: List<LinkUi>,
    val email: String,
    val location: String,
    val availability: String,
    val isAvailable: Boolean
)

fun sampleProfile(): DeveloperProfileUi {
    return DeveloperProfileUi(
        name = "Pierre Marion",
        role = "Développeur Full-Stack",
        description = "Étudiant en Mastère architecture logiciel à l'ESGI. " +
                "Passionné par le développement web, les interfaces modernes et les nouvelles technologies.",
        skills = listOf(
            "Angular",
            "TypeScript",
            "C# / .NET",
            "Node.js",
            "PostgreSQL",
            "Jetpack Compose"
        ),
        projects = listOf(
            ProjectUi(
                id = 1,
                title = "Product Explorer",
                description = "Application Android de catalogue produits avec listes modernes et thème Material 3.",
                technologies = "Kotlin • Jetpack Compose • Material 3",
                year = "2026"
            ),
            ProjectUi(
                id = 2,
                title = "Gestionnaire de tâches",
                description = "Application web full-stack avec tableau de bord et suivi des tâches.",
                technologies = "Angular • Node.js • PostgreSQL",
                year = "2025"
            ),
            ProjectUi(
                id = 3,
                title = "API de réservation",
                description = "API REST pour gérer la réservation de salles dans une entreprise.",
                technologies = "C# / .NET • PostgreSQL",
                year = "2024"
            ),
            ProjectUi(
                id = 4,
                title = "Profil développeur",
                description = "Portfolio Android interactif : recherche de projets, favoris et filtres.",
                technologies = "Kotlin • Jetpack Compose • ViewModel • StateFlow",
                year = "2026"
            )
        ),
        experiences = listOf(
            ExperienceUi(
                id = 1,
                title = "Développeur Full-Stack (alternance)",
                place = "CGI",
                period = "Depuis 2024",
                description = "Développement d'applications web avec Angular et C# / .NET."
            ),
            ExperienceUi(
                id = 2,
                title = "Mastère Architecture logicielle",
                place = "ESGI – Paris",
                period = "2025 – 2027",
                description = "Architecture des applications, développement web et mobile."
            )
        ),
        links = listOf(
            LinkUi(label = "GitHub", url = "https://github.com/pierre-marion"),
            LinkUi(label = "LinkedIn", url = "https://www.linkedin.com/in/pierre-marion"),
            LinkUi(label = "Portfolio", url = "https://pierre-marion.dev")
        ),
        email = "pierre.marion@example.com",
        location = "Paris, France",
        availability = "En alternance chez CGI",
        isAvailable = false
    )
}

fun sampleJuniorProfile(): DeveloperProfileUi {
    return DeveloperProfileUi(
        name = "Alex Martin",
        role = "Développeur Android Junior",
        description = "Développeur passionné par la création d'applications mobiles avec Kotlin et Jetpack Compose.",
        skills = listOf(
            "Kotlin",
            "Jetpack Compose",
            "Android Studio",
            "Git"
        ),
        projects = listOf(
            ProjectUi(
                id = 1,
                title = "Fiche Profil développeur",
                description = "Premier écran Compose avec des composables réutilisables et des previews.",
                technologies = "Kotlin • Jetpack Compose",
                year = "2026"
            ),
            ProjectUi(
                id = 2,
                title = "Application météo",
                description = "Maquette d'une application météo affichant des données locales.",
                technologies = "Kotlin • Material 3",
                year = "2025"
            )
        ),
        experiences = listOf(
            ExperienceUi(
                id = 1,
                title = "Bachelor Développement mobile",
                place = "Lyon",
                period = "2023 – 2026",
                description = "Formation centrée sur Kotlin, Android et les interfaces mobiles."
            )
        ),
        links = listOf(
            LinkUi(label = "GitHub", url = "https://github.com/alex-martin"),
            LinkUi(label = "LinkedIn", url = "https://www.linkedin.com/in/alex-martin")
        ),
        email = "alex.martin@example.com",
        location = "Lyon, France",
        availability = "Disponible pour un stage",
        isAvailable = true
    )
}


fun projectCountText(count: Int): String {
    return when (count) {
        0 -> "Aucun projet affiché"
        1 -> "1 projet affiché"
        else -> "$count projets affichés"
    }
}

/* ############### Container ################ */


@Composable
fun ProfileContainer(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val uriHandler = LocalUriHandler.current

    fun openUrl(url: String) {
        // Pas d'application pour ouvrir le lien (ex. émulateur sans mail) : on ignore
        runCatching { uriHandler.openUri(url) }
    }

    ProfileScreen(
        profile = uiState.profile,
        visibleProjects = uiState.visibleProjects,
        searchQuery = uiState.searchQuery,
        onSearchQueryChange = viewModel::onSearchQueryChange,
        showOnlyFavorites = uiState.showOnlyFavorites,
        onToggleFavoriteFilter = viewModel::onToggleFavoriteFilter,
        favoriteProjectIds = uiState.favoriteProjectIds,
        onFavoriteClick = viewModel::onFavoriteClick,
        onContactClick = { openUrl("mailto:${uiState.profile.email}") },
        onPortfolioClick = {
            uiState.profile.links
                .find { it.label == "Portfolio" }
                ?.let { openUrl(it.url) }
        },
        onLinkClick = { link -> openUrl(link.url) },
        modifier = modifier
    )
}

/* ############### Écran principal (stateless) ################ */

@Composable
fun ProfileScreen(
    profile: DeveloperProfileUi,
    visibleProjects: List<ProjectUi>,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    showOnlyFavorites: Boolean,
    onToggleFavoriteFilter: () -> Unit,
    favoriteProjectIds: Set<Int>,
    onFavoriteClick: (Int) -> Unit,
    onContactClick: () -> Unit,
    onPortfolioClick: () -> Unit,
    onLinkClick: (LinkUi) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            ProfileHeaderCard(
                name = profile.name,
                role = profile.role,
                isAvailable = profile.isAvailable,
                projectsCount = profile.projects.size,
                skillsCount = profile.skills.size,
                favoritesCount = favoriteProjectIds.size
            )
        }
        item {
            ProfileDescription(description = profile.description)
        }
        item {
            SkillsSection(skills = profile.skills)
        }
        item {
            SectionTitle(title = "Parcours")
        }
        items(
            items = profile.experiences,
            key = { experience -> "experience-${experience.id}" }
        ) { experience ->
            ExperienceItem(experience = experience)
        }
        item {
            SectionTitle(title = "Projets")
        }
        item {
            ProjectSearchField(
                searchQuery = searchQuery,
                onSearchQueryChange = onSearchQueryChange
            )
        }
        item {
            ProjectFilterRow(
                visibleCount = visibleProjects.size,
                showOnlyFavorites = showOnlyFavorites,
                favoritesCount = favoriteProjectIds.size,
                onToggleFavoriteFilter = onToggleFavoriteFilter
            )
        }
        if (visibleProjects.isEmpty()) {
            item {
                EmptyProjectsMessage(
                    searchQuery = searchQuery,
                    showOnlyFavorites = showOnlyFavorites,
                    hasFavorites = favoriteProjectIds.isNotEmpty()
                )
            }
        }
        items(
            items = visibleProjects,
            key = { project -> "project-${project.id}" }
        ) { project ->
            ProjectCard(
                project = project,
                isFavorite = project.id in favoriteProjectIds,
                onFavoriteClick = { onFavoriteClick(project.id) }
            )
        }
        item {
            LinksSection(
                links = profile.links,
                onLinkClick = onLinkClick
            )
        }
        item {
            ContactSection(
                email = profile.email,
                location = profile.location,
                availability = profile.availability,
                onContactClick = onContactClick,
                onPortfolioClick = onPortfolioClick
            )
        }
    }
}

/* ############### En-tête ################ */

@Composable
fun ProfileHeaderCard(
    name: String,
    role: String,
    isAvailable: Boolean,
    projectsCount: Int,
    skillsCount: Int,
    favoritesCount: Int,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.extraLarge,
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = 3.dp
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            ProfileHeader(
                name = name,
                role = role,
                isAvailable = isAvailable,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(MaterialTheme.colorScheme.primaryContainer)
            )
            Spacer(modifier = Modifier.height(16.dp))
            ProfileStatsRow(
                projectsCount = projectsCount,
                skillsCount = skillsCount,
                favoritesCount = favoritesCount
            )
        }
    }
}

@Composable
fun ProfileStatsRow(
    projectsCount: Int,
    skillsCount: Int,
    favoritesCount: Int,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier.fillMaxWidth()) {
        StatItem(
            value = "$projectsCount",
            label = "Projets",
            modifier = Modifier.weight(1f)
        )
        StatItem(
            value = "$skillsCount",
            label = "Compétences",
            modifier = Modifier.weight(1f)
        )
        StatItem(
            value = "$favoritesCount",
            label = "Favoris",
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun StatItem(
    value: String,
    label: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.secondary
        )
    }
}

@Composable
fun ProfileHeader(
    name: String,
    role: String,
    isAvailable: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ProfileAvatar(
            name = name,
            isAvailable = isAvailable
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = name,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = role,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}

@Composable
fun ProfileAvatar(
    name: String,
    isAvailable: Boolean,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.size(96.dp)) {
        Image(
            painter = painterResource(id = android.R.drawable.ic_menu_gallery),
            contentDescription = "Photo de profil de $name",
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(20.dp)
        )
        AvailabilityBadge(
            isAvailable = isAvailable,
            modifier = Modifier.align(Alignment.BottomEnd)
        )
    }
}

@Composable
fun AvailabilityBadge(
    isAvailable: Boolean,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.small,
        color = if (isAvailable) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.secondary,
        contentColor = if (isAvailable) MaterialTheme.colorScheme.onTertiary else MaterialTheme.colorScheme.onSecondary
    ) {
        Text(
            text = if (isAvailable) "Dispo" else "En poste",
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelMedium
        )
    }
}

@Composable
fun ProfileDescription(
    description: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "À propos",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
fun SectionTitle(
    title: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = title,
        modifier = modifier,
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.primary
    )
}

/* ############### Compétences ################ */

@Composable
fun SkillsSection(
    skills: List<String>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        SectionTitle(title = "Compétences")
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(skills) { skill ->
                SkillChip(label = skill)
            }
        }
    }
}

@Composable
fun SkillChip(
    label: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.extraLarge,
        color = MaterialTheme.colorScheme.secondaryContainer,
        contentColor = MaterialTheme.colorScheme.onSecondaryContainer
    ) {
        Text(
            text = label,
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
            style = MaterialTheme.typography.labelLarge
        )
    }
}

/* ############### Parcours ################ */

@Composable
fun ExperienceItem(
    experience: ExperienceUi,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
        )
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Box(
                modifier = Modifier
                    .padding(top = 6.dp)
                    .size(12.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = experience.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "${experience.place} • ${experience.period}",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.secondary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = experience.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

/* ############### Projets : recherche et filtre ################ */

@Composable
fun ProjectSearchField(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = searchQuery,
        onValueChange = onSearchQueryChange,
        label = {
            Text(text = "Rechercher un projet")
        },
        placeholder = {
            Text(text = "Titre, technologie, année…")
        },
        leadingIcon = {
            Icon(
                painter = painterResource(id = android.R.drawable.ic_menu_search),
                contentDescription = null
            )
        },
        trailingIcon = if (searchQuery.isNotEmpty()) {
            {
                IconButton(onClick = { onSearchQueryChange("") }) {
                    Icon(
                        painter = painterResource(id = android.R.drawable.ic_menu_close_clear_cancel),
                        contentDescription = "Effacer la recherche"
                    )
                }
            }
        } else {
            null
        },
        singleLine = true,
        modifier = modifier.fillMaxWidth()
    )
}

@Composable
fun ProjectFilterRow(
    visibleCount: Int,
    showOnlyFavorites: Boolean,
    favoritesCount: Int,
    onToggleFavoriteFilter: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = projectCountText(visibleCount),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.weight(1f)
        )
        if (showOnlyFavorites) {
            Button(onClick = onToggleFavoriteFilter) {
                Text(text = "Tous les projets")
            }
        } else {
            OutlinedButton(onClick = onToggleFavoriteFilter) {
                Text(text = "Favoris uniquement ($favoritesCount)")
            }
        }
    }
}

@Composable
fun EmptyProjectsMessage(
    searchQuery: String,
    showOnlyFavorites: Boolean,
    hasFavorites: Boolean,
    modifier: Modifier = Modifier
) {
    val message = when {
        showOnlyFavorites && !hasFavorites ->
            "Aucun projet en favori pour l'instant. Touchez l'étoile d'un projet pour l'ajouter."
        searchQuery.isNotBlank() ->
            "Aucun projet ne correspond à « ${searchQuery.trim()} »."
        else ->
            "Aucun projet à afficher."
    }

    Text(
        text = message,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.secondary,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    )
}

/* ############### Projets : carte ################ */

@Composable
fun ProjectCard(
    project: ProjectUi,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        ),
        // Bordure colorée : l'état favori se voit sur toute la carte
        border = if (isFavorite) BorderStroke(2.dp, MaterialTheme.colorScheme.primary) else null
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = android.R.drawable.ic_menu_agenda),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = project.title,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = project.year,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
                FavoriteButton(
                    isFavorite = isFavorite,
                    onClick = onFavoriteClick
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = project.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = project.technologies,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun FavoriteButton(
    isFavorite: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(onClick = onClick, modifier = modifier) {
        Icon(
            painter = painterResource(
                id = if (isFavorite) {
                    android.R.drawable.btn_star_big_on
                } else {
                    android.R.drawable.btn_star_big_off
                }
            ),
            contentDescription = if (isFavorite) "Retirer des favoris" else "Ajouter aux favoris",
            // On garde les couleurs d'origine de l'étoile (jaune / grise)
            tint = Color.Unspecified
        )
    }
}

/* ############### Liens ################ */

@Composable
fun LinksSection(
    links: List<LinkUi>,
    onLinkClick: (LinkUi) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        SectionTitle(title = "Liens")
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(links) { link ->
                LinkChip(
                    label = link.label,
                    onClick = {
                        onLinkClick(link)
                    }
                )
            }
        }
    }
}

@Composable
fun LinkChip(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .clip(MaterialTheme.shapes.extraLarge)
            .clickable(
                role = Role.Button,
                onClickLabel = "Ouvrir $label",
                onClick = onClick
            )
            .semantics {
                contentDescription = "Ouvrir le lien $label"
            },
        shape = MaterialTheme.shapes.extraLarge,
        color = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.primary,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = android.R.drawable.ic_menu_share),
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

/* ############### Contact (contient un état LOCAL) ################ */


@Composable
fun ContactSection(
    email: String,
    location: String,
    availability: String,
    onContactClick: () -> Unit,
    onPortfolioClick: () -> Unit,
    modifier: Modifier = Modifier,
    initiallyExpanded: Boolean = false
) {
    var showContactDetails by rememberSaveable { mutableStateOf(initiallyExpanded) }

    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        color = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Contact",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedButton(
                onClick = { showContactDetails = !showContactDetails },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = if (showContactDetails) {
                        "Masquer les informations de contact"
                    } else {
                        "Afficher les informations de contact"
                    }
                )
            }

            AnimatedVisibility(visible = showContactDetails) {
                Column {
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedTextField(
                        value = email,
                        onValueChange = {},
                        readOnly = true,
                        label = {
                            Text(text = "Email")
                        },
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = android.R.drawable.ic_dialog_email),
                                contentDescription = null
                            )
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    ContactInfoRow(
                        iconRes = android.R.drawable.ic_menu_mylocation,
                        label = location
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    ContactInfoRow(
                        iconRes = android.R.drawable.ic_menu_my_calendar,
                        label = availability
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = onContactClick,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Contacter")
                }
                Spacer(modifier = Modifier.width(12.dp))
                PortfolioLink(onClick = onPortfolioClick)
            }
        }
    }
}

@Composable
fun ContactInfoRow(
    iconRes: Int,
    label: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun PortfolioLink(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Text(
        text = "Voir le portfolio",
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.primary,
        modifier = modifier
            .clip(MaterialTheme.shapes.small)
            .clickable(
                role = Role.Button,
                onClickLabel = "Ouvrir le portfolio",
                onClick = onClick
            )
            .padding(8.dp)
            .semantics {
                contentDescription = "Voir le portfolio du développeur"
            }
    )
}

/* ############### Previews ################ */
/* Les previews utilisent directement ProfileScreen (stateless), sans ViewModel. */

@Preview(
    showBackground = true,
    name = "1 - Recherche vide, aucun favori (clair)"
)
@Composable
fun ProfileScreenDefaultPreview() {
    val profile = sampleProfile()

    DeveloperProfileTheme(darkTheme = false) {
        ProfileScreen(
            profile = profile,
            visibleProjects = profile.projects,
            searchQuery = "",
            onSearchQueryChange = {},
            showOnlyFavorites = false,
            onToggleFavoriteFilter = {},
            favoriteProjectIds = emptySet(),
            onFavoriteClick = {},
            onContactClick = {},
            onPortfolioClick = {},
            onLinkClick = {}
        )
    }
}

@Preview(
    showBackground = true,
    name = "2 - Recherche « kotlin » + favoris uniquement (sombre)"
)
@Composable
fun ProfileScreenSearchFavoritesPreview() {
    val profile = sampleProfile()
    val searchQuery = "kotlin"
    val favoriteIds = setOf(1, 2, 4)

    DeveloperProfileTheme(darkTheme = true) {
        ProfileScreen(
            profile = profile,
            visibleProjects = filterProjects(
                projects = profile.projects,
                searchQuery = searchQuery,
                showOnlyFavorites = true,
                favoriteProjectIds = favoriteIds
            ),
            searchQuery = searchQuery,
            onSearchQueryChange = {},
            showOnlyFavorites = true,
            onToggleFavoriteFilter = {},
            favoriteProjectIds = favoriteIds,
            onFavoriteClick = {},
            onContactClick = {},
            onPortfolioClick = {},
            onLinkClick = {}
        )
    }
}

@Preview(
    showBackground = true,
    name = "3 - Recherche sans résultat (clair)"
)
@Composable
fun ProfileScreenNoResultPreview() {
    val profile = sampleProfile()

    DeveloperProfileTheme(darkTheme = false) {
        ProfileScreen(
            profile = profile,
            visibleProjects = emptyList(),
            searchQuery = "python",
            onSearchQueryChange = {},
            showOnlyFavorites = false,
            onToggleFavoriteFilter = {},
            favoriteProjectIds = setOf(1),
            onFavoriteClick = {},
            onContactClick = {},
            onPortfolioClick = {},
            onLinkClick = {}
        )
    }
}

@Preview(
    showBackground = true,
    name = "4 - Profil junior disponible (clair)"
)
@Composable
fun JuniorProfilePreview() {
    val profile = sampleJuniorProfile()

    DeveloperProfileTheme(darkTheme = false) {
        ProfileScreen(
            profile = profile,
            visibleProjects = profile.projects,
            searchQuery = "",
            onSearchQueryChange = {},
            showOnlyFavorites = false,
            onToggleFavoriteFilter = {},
            favoriteProjectIds = setOf(2),
            onFavoriteClick = {},
            onContactClick = {},
            onPortfolioClick = {},
            onLinkClick = {}
        )
    }
}

@Preview(
    showBackground = true,
    name = "5 - Section contact ouverte (état local)"
)
@Composable
fun ContactSectionExpandedPreview() {
    val profile = sampleProfile()

    DeveloperProfileTheme {
        ContactSection(
            email = profile.email,
            location = profile.location,
            availability = profile.availability,
            onContactClick = {},
            onPortfolioClick = {},
            initiallyExpanded = true
        )
    }
}
