package com.arvind.foodizone.model

data class MyOrders(
    val id: Int,
    val ordersImageId: Int = 0,
    val name: String,
    val price: Int,
    var quantity: Int = 0,
)

fun List<MyOrders>.select(quantity: Int) = map {
    it.copy(quantity = quantity)
}

fun List<MyOrders>.contains(targetId: Int){
    this.any { it.id == targetId }
}

fun List<MyOrders>.updateOrderListWithQuantity(targetId: Int, quantity: Int): List<MyOrders> {
    this.find { it.id == targetId }?.quantity = quantity
    return this
}
