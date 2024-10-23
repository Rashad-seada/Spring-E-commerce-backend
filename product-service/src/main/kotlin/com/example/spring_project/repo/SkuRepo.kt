package com.example.spring_project.repo

import com.example.spring_project.model.Product
import com.example.spring_project.model.Sku
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SkuRepo : JpaRepository<Sku,Long>{
    fun findBySkuCodeContainingIgnoreCase(skuCode: String, pageable: Pageable): Page<Sku>

    fun findAllByIdIn(ids: List<Long>): List<Sku>

}