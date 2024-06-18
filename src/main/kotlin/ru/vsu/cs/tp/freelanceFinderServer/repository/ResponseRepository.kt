package ru.vsu.cs.tp.freelanceFinderServer.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.vsu.cs.tp.freelanceFinderServer.model.Response

interface ResponseRepository : JpaRepository<Response, Long>
