package com.arvind.foodizone.view

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.arvind.foodizone.common.SafeMutableLiveData
import com.arvind.foodizone.data.MyOrdersDataDummy
import com.arvind.foodizone.model.MyOrders
import com.arvind.foodizone.model.updateOrderListWithQuantity


inline fun <VM : ViewModel> viewModelFactory(crossinline f: () -> VM) =
    object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>):T = f() as T
    }


class OrderViewModel(): ViewModel() {

    private val stateDepricated = SafeMutableLiveData(OrderState())
    var state by mutableStateOf(OrderState())


    fun updateTip(tipPercentage:Double){

        val tip: Int = state.menuTotal.times(tipPercentage).toInt()

        state = state.copy(tipPercentage = tipPercentage)
        state = state.copy(tip = tip)
    }

    fun mapToDollarValue(value:Int): String{
        val cent = value.toString().takeLast(2)
        val dollar = value.toString().dropLast(2)
        return "$$dollar.$cent"
    }

    private fun updateOrderTotal(){
        var chickenQuantity = 0
        var beefQuantity = 0
        var pizzaQuantity = 0

        state.orders.forEach {
            when (it.name) {
                "Chicken Burger" -> {
                    chickenQuantity = it.quantity
                    state = state.copy(chickenBurgerPrice = it.price)
                }
                "Beef Burger" -> {
                    beefQuantity = it.quantity
                    state = state.copy(beefBurgerPrice = it.price)
                }
                "Chicken Pizza" -> {
                    pizzaQuantity = it.quantity
                    state = state.copy(chickenPizzaPrice = it.price)
                }
            }
        }

        val chickenTotalPrice = state.chickenBurgerPrice.times(chickenQuantity)
        val beefTotalPrice = state.beefBurgerPrice.times(beefQuantity)
        val pizzaTotalPrice = state.chickenPizzaPrice.times(pizzaQuantity)

        val total = chickenTotalPrice + beefTotalPrice + pizzaTotalPrice
        val tax = total.times(.0975).toInt()

        state = state.copy(tax = tax)
        state = state.copy(menuTotal = total)
        state = state.copy(orderTotal = mapToDollarValue(state.menuTotal + state.deliveryFees + state.tax + state.tip))
    }

    fun updateOrderList(orders: List<MyOrders>){ state = state.copy(orders = orders) }


    fun updateQuantityForOrder(currentOrder: MyOrders, quantity: Int) {
        state = state.copy(orders = state.orders.updateOrderListWithQuantity(currentOrder.id, quantity))
        updateOrderTotal()
        updateTip(state.tipPercentage)
    }

}

data class OrderState(
    //totals
    val orderTotal: String = "$2.75",
    val menuTotal: Int = 0,

    //variable fees
    val tipPercentage: Double = 0.0,
    val tip: Int = 0,
    val tax: Int = 0,

    //prices & fixed costs
    val chickenBurgerPrice: Int = 0,
    val beefBurgerPrice: Int = 0,
    val chickenPizzaPrice: Int = 0,
    val deliveryFees: Int = 275,

    //oder data
    val orders: List<MyOrders> = MyOrdersDataDummy.myOrdersList
)

