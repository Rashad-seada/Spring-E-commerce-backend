package com.example.spring_project.repo

import com.example.spring_project.model.Category
import com.example.spring_project.model.Product
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CategoryRepo : JpaRepository<Category,Long>{
    fun findByNameContainingIgnoreCase(name: String, pageable: Pageable): Page<Category>
}