package com.example.developerprofile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
                    ProfileScreen(
                        profile = sampleProfile(),
                        onContactClick = {
                            // Plus tard : ouvrir l'application mail
                        },
                        onPortfolioClick = {
                            // Plus tard : ouvrir le portfolio
                        },
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

data class DeveloperProfileUi(
    val name: String,
    val role: String,
    val description: String,
    val skills: List<String>,
    val projects: List<ProjectUi>,
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
            )
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
        email = "alex.martin@example.com",
        location = "Lyon, France",
        availability = "Disponible pour un stage",
        isAvailable = true
    )
}

/* ############### Écran principal ################ */

@Composable
fun ProfileScreen(
    profile: DeveloperProfileUi,
    onContactClick: () -> Unit,
    onPortfolioClick: () -> Unit,
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
            ProfileHeader(
                name = profile.name,
                role = profile.role,
                isAvailable = profile.isAvailable,
                modifier = Modifier.fillMaxWidth()
            )
        }
        item {
            ProfileDescription(description = profile.description)
        }
        item {
            SkillsSection(skills = profile.skills)
        }
        item {
            SectionTitle(title = "Projets")
        }
        items(
            items = profile.projects,
            key = { project -> project.id }
        ) { project ->
            ProjectCard(project = project)
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

/* ############### Projets ################ */

@Composable
fun ProjectCard(
    project: ProjectUi,
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
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = android.R.drawable.ic_menu_agenda),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = project.title,
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = project.year,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.secondary
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

/* ############### Contact ################ */

@Composable
fun ContactSection(
    email: String,
    location: String,
    availability: String,
    onContactClick: () -> Unit,
    onPortfolioClick: () -> Unit,
    modifier: Modifier = Modifier
) {
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

@Preview(
    showBackground = true,
    name = "Profil - Thème clair"
)
@Composable
fun ProfileScreenLightPreview() {
    DeveloperProfileTheme(darkTheme = false) {
        ProfileScreen(
            profile = sampleProfile(),
            onContactClick = {},
            onPortfolioClick = {}
        )
    }
}
