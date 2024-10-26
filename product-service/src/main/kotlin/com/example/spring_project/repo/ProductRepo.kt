package com.example.spring_project.repo

import com.example.spring_project.model.Product
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepo : JpaRepository<Product,Long>{


    fun findByNameIgnoreCaseContaining(name: String, pageable: Pageable): Page<Product>

    fun findByIdIn(ids: List<Long>): List<Product>


}