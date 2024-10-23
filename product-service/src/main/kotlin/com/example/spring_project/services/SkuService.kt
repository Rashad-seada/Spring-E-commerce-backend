package com.example.spring_project.services


import com.example.spring_project.model.Sku
import com.example.spring_project.repo.SkuRepo
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SkuService(
    private val skuRepository: SkuRepo
) {

    @Transactional
    fun saveSku(sku: Sku): Sku {
        return skuRepository.save(sku)
    }

    @Transactional
    fun getSkuById(id: Long): Sku? {
        val sku = skuRepository.findById(id)
        return if (sku.isPresent) sku.get() else null
    }

    @Transactional
    fun deleteSku(id: Long) {
        skuRepository.deleteById(id)
    }

    @Transactional
    fun findSkusByIds(ids: List<Long>): List<Sku> {
        return skuRepository.findAllByIdIn(ids)
    }

    @Transactional
    fun getAllSkus(page: Int, size: Int): Page<Sku> {
        val pageable = PageRequest.of(page, size)
        return skuRepository.findAll(pageable)
    }

    @Transactional
    fun searchSkusByCode(query: String, page: Int, size: Int): Page<Sku> {
        val pageable = PageRequest.of(page, size)
        return skuRepository.findBySkuCodeContainingIgnoreCase(query, pageable)
    }
}
