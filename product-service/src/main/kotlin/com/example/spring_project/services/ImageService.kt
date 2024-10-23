package com.example.spring_project.services

import com.example.spring_project.core.error.CustomException
import com.example.spring_project.core.error.ErrorCode
import com.example.spring_project.model.Image
import com.example.spring_project.repo.ImageRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ImageService @Autowired constructor(
    private val imageRepository: ImageRepo
) {

    @Transactional
    fun saveImage(image: Image): Image {
        return imageRepository.save(image)
    }

    @Transactional
    fun getImageById(id: Long): Image {
        return imageRepository.findById(id).orElseThrow {
            CustomException.create(ErrorCode.RESOURCE_NOT_FOUND)
        }
    }

    @Transactional
    fun deleteImage(id: Long) {
        imageRepository.deleteById(id)
    }

    @Transactional
    fun getAllImages(page: Int, size: Int): Page<Image> {
        val pageable = PageRequest.of(page, size)
        return imageRepository.findAll(pageable)
    }

    @Transactional
    fun getImageUrl(image: Image): String {
        return "/api/images/${image.id}"
    }
}
