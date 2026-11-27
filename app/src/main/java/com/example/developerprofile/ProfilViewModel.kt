package com.example.developerprofile

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


class ProfileViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(
        ProfileUiState(profile = sampleProfile())
    )

    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    fun onSearchQueryChange(newQuery: String) {
        updateState { currentState ->
            currentState.copy(searchQuery = newQuery)
        }
    }

    fun onToggleFavoriteFilter() {
        updateState { currentState ->
            currentState.copy(showOnlyFavorites = !currentState.showOnlyFavorites)
        }
    }

    fun onFavoriteClick(projectId: Int) {
        updateState { currentState ->
            val newFavoriteIds =
                if (projectId in currentState.favoriteProjectIds) {
                    currentState.favoriteProjectIds - projectId
                } else {
                    currentState.favoriteProjectIds + projectId
                }

            currentState.copy(favoriteProjectIds = newFavoriteIds)
        }
    }


    private fun updateState(change: (ProfileUiState) -> ProfileUiState) {
        _uiState.update { currentState ->
            val newState = change(currentState)

            newState.copy(
                visibleProjects = filterProjects(
                    projects = newState.profile.projects,
                    searchQuery = newState.searchQuery,
                    showOnlyFavorites = newState.showOnlyFavorites,
                    favoriteProjectIds = newState.favoriteProjectIds
                )
            )
        }
    }
}
