package com.arvind.foodizone.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.outlined.Dashboard
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arvind.foodizone.ui.theme.colorBlack
import com.arvind.foodizone.ui.theme.colorWhite

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarHome() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(shape = CircleShape)
                .background(colorWhite)
        ) {
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Outlined.Dashboard,
                    contentDescription = "",
                    tint = colorBlack
                )

            }
        }

        var userselectaddress by remember { mutableStateOf("") }

        TextField(
            modifier = Modifier
                .height(50.dp)
                .weight(0.85f)
                .padding(start = 20.dp, end = 20.dp),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = colorWhite,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),
            value = userselectaddress,
            shape = RoundedCornerShape(24.dp),
            singleLine = true,
            onValueChange = { userselectaddress = it },
            placeholder = {
                Text(
                    text = "Nagpur",
                    color = colorBlack
                )
            },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowDown,
                    contentDescription = "",
                    tint = colorBlack
                )
            },
        )

        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(shape = CircleShape)
                .background(colorWhite)
        ) {
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Outlined.Notifications,
                    contentDescription = "",
                    tint = colorBlack
                )

            }
        }

    }
}

@Composable
@Preview
fun TopAppBarHomeScreenPreview() {
    TopAppBarHome()
}
