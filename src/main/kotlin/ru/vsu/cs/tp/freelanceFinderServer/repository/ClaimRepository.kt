package ru.vsu.cs.tp.freelanceFinderServer.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.vsu.cs.tp.freelanceFinderServer.model.Claim
import ru.vsu.cs.tp.freelanceFinderServer.model.Order
import ru.vsu.cs.tp.freelanceFinderServer.model.User
import java.util.*

interface ClaimRepository : JpaRepository<Claim, Long> {}
