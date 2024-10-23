package com.example.spring_project.repo

import com.example.spring_project.model.Image
import com.example.spring_project.model.ImageOwnerType
import com.example.spring_project.model.Product
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ImageRepo : JpaRepository<Image,Long>{

    fun findByOwnerIdAndImageOwnerType(ownerId: Long, imageOwnerType: String): List<Image>

}