package ru.vsu.cs.tp.freelanceFinderServer.service

import ru.vsu.cs.tp.freelanceFinderServer.model.Category
import ru.vsu.cs.tp.freelanceFinderServer.repository.CategoryRepository
import ru.vsu.cs.tp.freelanceFinderServer.repository.ScopeRepository
import org.springframework.stereotype.Service

@Service
class CategoryService(
    private val categoryRepository: CategoryRepository,
    private val scopeRepository: ScopeRepository
) {
    fun getAllCategoriesWithScopes(): List<Category> {
        val categories = categoryRepository.findAll()
        categories.forEach { category ->
            category.scopes = scopeRepository.findAllByCategory(category)
        }
        return categories
    }

}