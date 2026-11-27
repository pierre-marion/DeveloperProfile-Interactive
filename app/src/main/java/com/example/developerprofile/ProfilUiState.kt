package com.example.developerprofile

/**
 * État principal de l'écran Profil.
 * Tout ce qui est affiché et qui peut changer suite à une action de
 * l'utilisateur est regroupé ici.
 */
data class ProfileUiState(
    val profile: DeveloperProfileUi,
    val searchQuery: String = "",
    val showOnlyFavorites: Boolean = false,
    val favoriteProjectIds: Set<Int> = emptySet(),
    val visibleProjects: List<ProjectUi> = profile.projects
)


fun filterProjects(
    projects: List<ProjectUi>,
    searchQuery: String,
    showOnlyFavorites: Boolean,
    favoriteProjectIds: Set<Int>
): List<ProjectUi> {
    val query = searchQuery.trim()

    return projects.filter { project ->
        val matchesSearch = query.isEmpty() ||
                project.title.contains(query, ignoreCase = true) ||
                project.technologies.contains(query, ignoreCase = true) ||
                project.description.contains(query, ignoreCase = true) ||
                project.year.contains(query)

        val matchesFavoriteFilter = !showOnlyFavorites || project.id in favoriteProjectIds

        matchesSearch && matchesFavoriteFilter
    }
}
