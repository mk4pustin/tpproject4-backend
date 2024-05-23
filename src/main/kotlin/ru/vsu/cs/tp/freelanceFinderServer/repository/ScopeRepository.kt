package ru.vsu.cs.tp.freelanceFinderServer.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.vsu.cs.tp.freelanceFinderServer.model.Category
import ru.vsu.cs.tp.freelanceFinderServer.model.Scope

interface ScopeRepository : JpaRepository<Scope, Int> {

    fun findAllByNameIn(names: List<String>?): List<Scope>

    fun findAllByCategory(category: Category): List<Scope>
}