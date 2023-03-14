package com.arvind.foodizone.view


import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Minimize
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.arvind.foodizone.R
import com.arvind.foodizone.component.TopAppBarMyOrders
import com.arvind.foodizone.data.MyOrdersDataDummy
import com.arvind.foodizone.model.MyOrders
import com.arvind.foodizone.ui.theme.*
import com.finix.finixpaymentsheet.domain.model.PaymentSheetColors
import com.finix.finixpaymentsheet.domain.model.PaymentSheetResources
import com.finix.finixpaymentsheet.ui.viewModel.paymentSheet.CompletePaymentSheetOutlined

@Composable
fun OrderScreen(navController: NavHostController) {
    Scaffold(topBar = {
        TopAppBarMyOrders()
    },
        backgroundColor = if (isSystemInDarkTheme()) Color.Black else colorWhite,
        content = {
            OrderMainContent()
        })

}

@Composable
fun OrderMainContent() {

    val viewModel: OrderViewModel = viewModel(factory = viewModelFactory {
        OrderViewModel()
    })

    Column {
        OrderList(viewModel)
        OrderCalculateData(viewModel)
    }
}

@Composable
fun OrderCalculateData(viewModel: OrderViewModel) {

    var showFinixPaymentSheet by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val state = viewModel.state


    if (showFinixPaymentSheet) {
        CompletePaymentSheetOutlined(
            onDismiss = {
                showFinixPaymentSheet = !showFinixPaymentSheet
                Toast.makeText(context, "Dialog dismissed!", Toast.LENGTH_SHORT).show()
            },
            onNegativeClick = {
                showFinixPaymentSheet = !showFinixPaymentSheet
                Toast.makeText(context, "Negative Button Clicked!", Toast.LENGTH_SHORT).show()
            },
            onPositiveClick = { Token ->
                showFinixPaymentSheet = !showFinixPaymentSheet
                Toast.makeText(context, "Tokenize Response: $Token", Toast.LENGTH_SHORT).show()
            },
            paymentSheetResources = PaymentSheetResources(
                logoDrawable = R.drawable.burger,
                logoText = R.string.logo_text,
                tokenizeButtonText = R.string.buy
            ),
            paymentSheetColors = PaymentSheetColors(
                tokenizeButtonColor = colorRedDark,
                focusedIndicatorColor = colorRedDark,
                focusedLabelColor = colorRedLite,
                unfocusedLabelColor = Color.Gray,
                cancelButtonColor = colorBlack,
                placeholderColor = colorRedLite
            ),
            isSandbox = true
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 44.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(
                    start = 20.dp,
                    end = 20.dp,
                    top = 20.dp,
                    bottom = 20.dp
                ),

            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(0.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Button(
                    onClick = {
                              viewModel.updateTip(.10)
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = colorRedLite),
                    modifier = Modifier
                        .height(50.dp)
                        .align(Alignment.CenterVertically)
                        .weight(1f),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Text(
                        text = "10% \uD83E\uDD11",
                        color = colorWhite,
                        style = MaterialTheme.typography.button,
                        modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                    )
                }
                Button(
                    onClick = {
                        viewModel.updateTip(.15)
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = colorRedLite),
                    modifier = Modifier
                        .height(50.dp)
                        .align(Alignment.CenterVertically)
                        .weight(1f),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Text(
                        text = "15% \uD83E\uDD11",
                        color = colorWhite,
                        style = MaterialTheme.typography.button,
                        modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                    )
                }

                Button(
                    onClick = {
                        viewModel.updateTip(.25)
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = colorRedLite),
                    modifier = Modifier
                        .height(50.dp)
                        .align(Alignment.CenterVertically)
                        .weight(1f),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Text(
                        text = "25% \uD83E\uDD11",
                        color = colorWhite,
                        style = MaterialTheme.typography.button,
                        modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(0.dp))
            HorizontalDivider()

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Menu total",
                    color = Color.Gray,
                    style = MaterialTheme.typography.button
                )
                Text(
                    text = viewModel.mapToDollarValue(state.menuTotal),
                    color = colorBlack,
                    style = MaterialTheme.typography.button,
                    fontWeight = FontWeight.Bold
                )

            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Delivery fees",
                    color = Color.Gray,
                    style = MaterialTheme.typography.button
                )
                Text(
                    text = viewModel.mapToDollarValue(state.deliveryFees),
                    color = colorBlack,
                    style = MaterialTheme.typography.button,
                    fontWeight = FontWeight.Bold
                )

            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Tax",
                    color = Color.Gray,
                    style = MaterialTheme.typography.button
                )
                Text(
                    text = viewModel.mapToDollarValue(state.tax),
                    color = colorBlack,
                    style = MaterialTheme.typography.button,
                    fontWeight = FontWeight.Bold
                )

            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Tip",
                    color = Color.Gray,
                    style = MaterialTheme.typography.button
                )
                Text(
                    text = viewModel.mapToDollarValue(state.tip),
//                    text = "$0.01",
                    color = colorBlack,
                    style = MaterialTheme.typography.button,
                    fontWeight = FontWeight.Bold
                )

            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Total:",
                    color = Color.Gray,
                    style = MaterialTheme.typography.button
                )
                Text(
                    text = state.orderTotal,
                    color = colorRedDark,
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.Bold
                )

            }

        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            Button(
                onClick = {
                    showFinixPaymentSheet = !showFinixPaymentSheet
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = colorBlack),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                shape = RoundedCornerShape(24.dp)
            ) {
                Text(
                    text = "Confirm order \uD83D\uDE0B",
                    color = colorWhite,
                    style = MaterialTheme.typography.button,
                    modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                )
            }
        }

    }
}

@Composable
fun OrderList(viewModel: OrderViewModel) {
    val orders = remember { MyOrdersDataDummy.myOrdersList }
    viewModel.updateOrderList(orders)

    LazyColumn(verticalArrangement = Arrangement.spacedBy(18.dp)) {
        items(
            items = viewModel.state.orders,
            itemContent = {
                MyOrdersListItem(myOrders = it, viewModel)
            })
    }

}

@Composable
fun MyOrdersListItem(myOrders: MyOrders, viewModel: OrderViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            painter = painterResource(id = myOrders.ordersImageId),
            contentDescription = "",
            modifier = Modifier
                .width(82.dp)
                .height(82.dp)

        )
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .weight(0.9f)
                .padding(horizontal = 10.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = myOrders.name,
                style = MaterialTheme.typography.h6,
                color = colorBlack,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = viewModel.mapToDollarValue(myOrders.price),
                style = MaterialTheme.typography.h6,
                color = colorRedDark,
                fontWeight = FontWeight.Bold
            )

        }
        val counter = remember { mutableStateOf(0) }
        Box(
            modifier = Modifier
                .width(110.dp)
                .height(40.dp)
                .clip(shape = CircleShape)
                .background(colorRedLite)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Box(
                    modifier = Modifier
                        .clip(shape = CircleShape)
                        .background(colorWhite)
                        .size(32.dp, 32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    IconButton(onClick = {
                        counter.value--
                        viewModel.updateQuantityForOrder(currentOrder = myOrders, counter.value)
                    }) {
                        Icon(
                            imageVector = Icons.Default.Minimize,
                            contentDescription = "",
                            tint = colorRedDark,
                            modifier = Modifier.size(20.dp, 20.dp)
                        )
                    }
                }

                Text(
                    text = "${counter.value}",
                    color = colorBlack,
                    style = MaterialTheme.typography.button,
                    fontWeight = FontWeight.Bold
                )

                Box(
                    modifier = Modifier
                        .clip(shape = CircleShape)
                        .background(Color.Red)
                        .size(32.dp, 32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    IconButton(onClick = {
                        counter.value++
                        viewModel.updateQuantityForOrder(currentOrder = myOrders, counter.value)
                    }) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "",
                            tint = colorWhite,
                            modifier = Modifier.size(20.dp, 20.dp)
                        )
                    }
                }
            }
        }

    }

    HorizontalDivider()
}

@Composable
fun HorizontalDivider() {
    Divider(
        color = colorRedLite, thickness = 1.dp,
        modifier = Modifier.padding(start = 20.dp, end = 20.dp)
    )

}

@Composable
@Preview
fun OrderScreenPreview() {
    OrderScreen(navController = NavHostController(LocalContext.current))
}


@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
fun OrderScreenDarkPreview() {
    OrderScreen(navController = NavHostController(LocalContext.current))
}