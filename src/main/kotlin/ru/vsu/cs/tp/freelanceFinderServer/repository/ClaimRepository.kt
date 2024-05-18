package ru.vsu.cs.tp.freelanceFinderServer.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.vsu.cs.tp.freelanceFinderServer.model.Claim
import ru.vsu.cs.tp.freelanceFinderServer.model.Order

interface ClaimRepository : JpaRepository<Claim, Long> {}
