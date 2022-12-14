package com.experimental.emptycompose.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.experimental.emptycompose.theme.MigBackground
import com.experimental.emptycompose.theme.MigGrey
import com.experimental.emptycompose.theme.MigGreyText
import com.experimental.emptycompose.ui.data.BottomSheetType

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CustomTabs(
    tabIndex:Int,
    changeTabIndex:(Int)-> Unit,
    onBottomSheetValue: () -> ModalBottomSheetState,
    getIdForDialogs: (Int) -> Unit,
    getBottomSheetState: (BottomSheetType) -> Unit,
    dialogListener: (Boolean) -> Unit,
) {
    val list = listOf("Курсы Валют", "Ближайшие пункты")
    var selectedIndex by remember { mutableStateOf(0) }

    TabRow(selectedTabIndex = selectedIndex,
        backgroundColor = MigGrey,
        modifier = Modifier
            .fillMaxHeight(0.06f)
            .padding(horizontal = 18.dp)
            .clip(RoundedCornerShape(20)),
        indicator = {
        }
    ) {
        list.forEachIndexed { index, text ->
            val selected = selectedIndex == index
            Tab(
                modifier = if (selected) Modifier
                    .padding(vertical = 2.dp, horizontal = 2.dp)
                    .clip(RoundedCornerShape(20))
                    .background(
                        White
                    )
                else Modifier
                    .padding(vertical = 2.dp, horizontal = 2.dp)
                    .clip(RoundedCornerShape(20))
                    .background(
                        MigGrey
                    ),
                selected = selected,
                onClick = { selectedIndex = index },
                text = {
                    Text(
                        text = text,
                        color = MigGreyText,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        }
    }
    if (tabIndex!=-1){
        selectedIndex = tabIndex
        changeTabIndex(-1)
    }

    TabContent(
        selectedIndex,
        onBottomSheetValue,
        getIdForDialogs,
        getBottomSheetState,
        dialogListener
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TabContent(
    selectedIndex: Int,
    onBottomSheetValue: () -> ModalBottomSheetState,
    getIdForDialogs: (Int) -> Unit,
    getBottomSheetState: (BottomSheetType) -> Unit,
    dialogListener: (Boolean) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(top = 16.dp, start = 18.dp, end = 18.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(MigBackground)
//            .verticalScroll(rememberScrollState())
    ) {
        when (selectedIndex) {
            0 -> MainBody(
                onBottomSheetValue,
                getIdForDialogs,
                getBottomSheetState,
                dialogListener
            )
            else -> {
                AddressesBody()
                MainFooter()
            }
        }
    }
}



