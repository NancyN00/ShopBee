package com.nancy.shopbee.domain.models

data class ProductDetailsItem(
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String,
    val rate: Double,
    val count: Int,
    val isFavorite: Boolean = false
) {
    fun toProductEntity(): FavoriteProductEntity {
        return FavoriteProductEntity(
            id = this.id,
            title = this.title,
            price = this.price,
            description = this.description,
            category = this.category,
            image = this.image,
            rate = this.rate,
            count = this.count,
        )
    }
}

