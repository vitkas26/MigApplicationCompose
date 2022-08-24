package com.experimental.emptycompose

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import com.experimental.emptycompose.ui.theme.*
import com.experimental.emptycompose.data.BottomSheetType
import com.experimental.emptycompose.data.lisOfFullRates
import com.experimental.emptycompose.data.listOfDrawer
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val scaffoldState = rememberScaffoldState()
            val scope = rememberCoroutineScope()
            var id by remember { mutableStateOf(0) }
            val modalBottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
            val onBottomSheetValue = { modalBottomSheetState }
            val getIdForDialogs = { index: Int -> id = index }
            var bottomSheetType by remember { mutableStateOf(BottomSheetType.BUY)}
            val getBottomSheetState = {state:BottomSheetType -> bottomSheetType = state}
            val keyboardController = LocalSoftwareKeyboardController.current
            val openDialog = remember { mutableStateOf(false) }
            val dialogListener = {state:Boolean -> openDialog.value = state}


            ModalBottomSheetLayout(
                sheetState = modalBottomSheetState,
                sheetContent = {
                    BottomSheetContent(item = lisOfFullRates[id], type = bottomSheetType)
                }
            ) {
                if (!modalBottomSheetState.isVisible){
                    keyboardController?.hide()
                }
                Scaffold(
                    scaffoldState = scaffoldState,
                    topBar = {
                        com.experimental.emptycompose.ui.theme.navigation.AppBar(
                            onNavigationIconClick = {
                                scope.launch {
                                    scaffoldState.drawerState.open()
                                }
                            })
                    },
                    drawerElevation = 0.dp,
                    drawerContent = {
                        DrawerHeader()
                        DrawerBody(
                            items = listOfDrawer,
                            onItemClick = {
                                Toast.makeText(this@MainActivity, it.title, Toast.LENGTH_SHORT)
                                    .show()
                            },
                            modifier = Modifier.padding(16.dp)
                        )
                    }) {
                    Column {
                        CustomTabs(onBottomSheetValue, getIdForDialogs, getBottomSheetState, dialogListener)
                        }
                    }
                }
            if (openDialog.value){
                CallDialog(dialogListener, item = lisOfFullRates[id])
            }
        }
    }
}



