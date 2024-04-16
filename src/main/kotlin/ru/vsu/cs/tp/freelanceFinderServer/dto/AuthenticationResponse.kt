package ru.vsu.cs.tp.freelanceFinderServer.dto

import ru.vsu.cs.tp.freelanceFinderServer.model.User

class AuthenticationResponse(val token: String, val user: User)
