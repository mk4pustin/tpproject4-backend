package ru.vsu.cs.tp.freelanceFinderServer.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*
import ru.vsu.cs.tp.freelanceFinderServer.model.Category
import ru.vsu.cs.tp.freelanceFinderServer.model.Order
import ru.vsu.cs.tp.freelanceFinderServer.model.User
import ru.vsu.cs.tp.freelanceFinderServer.service.CategoryService
import ru.vsu.cs.tp.freelanceFinderServer.service.OrderService
import ru.vsu.cs.tp.freelanceFinderServer.service.UserService

@RestController
@RequestMapping("/api/all")
@Tag(name = "Контроллер для всех пользователей", description = "API для получения информации о заказах, фрилансерах и категориях")
class AllUsersController(
    private val userService: UserService,
    private val categoryService: CategoryService,
    private val orderService: OrderService
) {

    @GetMapping("/orders")
    @Operation(summary = "Получение активных заказов", description = "Возвращает список активных заказов")
    fun getActiveOrders(): List<Order> {
        return orderService.getActiveOrders()
    }

    @GetMapping("/freelancers")
    @Operation(summary = "Получение фрилансеров", description = "Возвращает список фрилансеров")
    fun getFreelancers(): List<User> {
        return userService.getFreelancers()
    }

    @GetMapping("/orders/{id}")
    @Operation(summary = "Получение заказа по id", description = "Возвращает заказ с указанным id")
    fun getOrderById(@PathVariable id: Long): Order {
        return orderService.getOrderById(id)
    }

    @GetMapping("/freelancers/{id}")
    @Operation(summary = "Получение фрилансера по id", description = "Возвращает фрилансера с указанным id")
    fun getUserById(@PathVariable id: Long): User {
        return userService.getUserById(id)
    }

    @GetMapping("/categories-with-scopes")
    @Operation(summary = "Получение категорий с областью деятельности", description = "Возвращает список категорий с указанной областью деятельности")
    fun getAllCategoriesWithScopes(): List<Category> {
        return categoryService.getAllCategoriesWithScopes()
    }

}