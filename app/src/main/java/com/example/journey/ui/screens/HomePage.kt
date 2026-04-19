package com.example.journey.ui.screens

import android.widget.Button
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.journey.R
import com.example.journey.ui.theme.JourneyTheme
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(
    userName: String,
    onCreateTemplate: (templateName: String, stopNames: List<String>) -> Unit
){

    var isMenuExpanded by remember { mutableStateOf(false) }
    var showCreateTemplateDialog by remember { mutableStateOf(false) }
    var showStopAddDialog by remember { mutableStateOf(false) }

    //variables to be submitted to database
    var templateName by remember { mutableStateOf("") }
    val stopsList = remember { mutableStateListOf<String>()}

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    listOf(
                        Color(0xFFD0AE90),
                        Color(0xFF896A4E)
                    ),
                    startY = 0.0f,
                    endY = Float.POSITIVE_INFINITY
                )
            ),
        color = Color.Transparent
    ) {

        Scaffold(
            containerColor = Color.Transparent,

            floatingActionButton = {
                Column(
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    AnimatedVisibility(
                        visible = isMenuExpanded,
                        enter = fadeIn() + slideInVertically(initialOffsetY = { it / 2 }),
                        exit = fadeOut() + slideOutVertically(targetOffsetY = { it / 2 })
                    ) {
                        Column(
                            horizontalAlignment = Alignment.End,
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier.padding(bottom = 8.dp)
                        ) {

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = Color(0xFF927155),
                                    modifier = Modifier.padding(end = 4.dp)
                                ) {
                                    Text(
                                        text = "New Template",
                                        color = Color.White,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                    )
                                }

                                SmallFloatingActionButton(
                                    onClick = {
                                        isMenuExpanded = false
                                        showCreateTemplateDialog = true
                                    },
                                    containerColor = Color(0xFF927155),
                                    contentColor = Color.White
                                ) {
                                    Text("+")
                                }
                            }

                            // More Buttons can be added to the column here.
                        }
                    }

                    FloatingActionButton(
                        onClick = {
                            isMenuExpanded = !isMenuExpanded
                        },
                        containerColor = Color(0xFF927155),
                        contentColor = Color.White,
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        if (isMenuExpanded) {
                            Text(
                                text = "X",
                                fontSize = 24.sp,
                                color = Color.White,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        } else {
                            Image(
                                painter = painterResource(id = R.drawable.add_template_no_background_white),
                                contentDescription = "Menu",
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }
                }
            }
        ) { innerPadding ->

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .windowInsetsPadding(WindowInsets.statusBars),
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Hello",
                    modifier = Modifier
                        .width(200.dp)
                        .fillMaxWidth(),
                    fontSize = 34.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 36.sp,
                )

                Text(
                    text = userName,
                    modifier = Modifier
                        .width(200.dp)
                        .fillMaxWidth(),
                    fontSize = 34.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 36.sp,
                )

                Text(
                    text = "Ready to start a Journey?",
                    color = Color.Black,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(top = 4.dp)
                )

            }

            //Dialog page for creating a new Journey Template(name only)
            if(showCreateTemplateDialog) {
                Dialog(
                    onDismissRequest = { showCreateTemplateDialog = false },
                )
                {
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = Color.White,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Create New Template",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF896A4E)
                            )

                            Spacer(modifier = Modifier.height(16.dp))


                            OutlinedTextField(
                                value = templateName,
                                onValueChange = { templateName = it },
                                label = { Text("Template Name") },
                                placeholder = { Text("e.g., Nightly Rounds") },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true,
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color(0xFF927155),
                                    focusedLabelColor = Color(0xFF927155)
                                )
                            )

                            Spacer(modifier = Modifier.height(24.dp))

                            Row(
                                horizontalArrangement = Arrangement.End,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                TextButton(
                                    onClick = { showCreateTemplateDialog = false },
                                    modifier = Modifier.padding(end = 8.dp)
                                ) {
                                    Text("Cancel", color = Color.Gray)
                                }

                                Button(
                                    onClick = {
                                        // TODO: Save the template name to ViewModel here
                                        showStopAddDialog = true
                                        showCreateTemplateDialog = false
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(
                                            0xFF927155
                                        )
                                    )
                                ) {
                                    Text("Continue")
                                }
                            }
                        }
                    }
                }
            }

            //Dialog Page for adding stops to a Journey Template
            if(showStopAddDialog){
                Dialog(
                    onDismissRequest = { showStopAddDialog = false },
                )
                {
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = Color.White,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)
                    ){
                        Column(
                            modifier = Modifier.padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ){
                            Text(
                                text = "Add Stops",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF896A4E)
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            var stopName by remember { mutableStateOf("") }

                            LazyColumn {
                                items(stopsList.size) { index ->
                                    ListItem(
                                        headlineContent = { Text(stopsList[index]) },
                                        trailingContent = {
                                            IconButton(
                                                onClick = { stopsList.removeAt(index) }
                                            ){
                                                Text("❌")
                                            }
                                        }
                                    )
                                    HorizontalDivider()
                                }

                            }


                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ){
                                //Dynamic list of stops that can be added to or taken away from
                                OutlinedTextField(
                                    value = stopName,
                                    onValueChange = { stopName = it },
                                    label = { Text("Stop Name") },
                                    placeholder = { Text("e.g., Johnson Hall") },
                                    modifier = Modifier.weight(1f),
                                    singleLine = true,
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = Color(0xFF927155),
                                        focusedLabelColor = Color(0xFF927155)
                                    )
                                )

                                TextButton(
                                    onClick = {
                                        if(stopName.isNotBlank()){
                                            stopsList.add(stopName)
                                            stopName = ""
                                        }
                                    },
                                    modifier = Modifier.padding(end = 8.dp)
                                ) {
                                    Text(
                                        text ="Add Stop",
                                        color = Color.Gray,
                                    )
                                }

                            }

                            Spacer(modifier = Modifier.height(24.dp))

                            //Button Row to cancel or submit
                            Row(
                                horizontalArrangement = Arrangement.End,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                TextButton(
                                    onClick = { showStopAddDialog = false },
                                    modifier = Modifier.padding(end = 8.dp)
                                ) {
                                    Text("Cancel", color = Color.Gray)
                                }


                                Button(
                                    onClick = {
                                        onCreateTemplate(templateName, stopsList)
                                        showStopAddDialog = false

                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(
                                            0xFF927155
                                        )
                                    )
                                ) {
                                    Text("Create Template")
                                }
                            }

                        }
                    }
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomePagePreview(){
    JourneyTheme(darkTheme = false, dynamicColor = false) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        listOf(
                            Color(0xFFD0AE90),
                            Color(0xFF896A4E)
                        ),
                        startY = 0.0f,
                        endY = Float.POSITIVE_INFINITY
                    )
                ),
            color = Color.Transparent,
        ) {
            HomePage(
                userName = "User",
                onCreateTemplate = {_, _ ->}
            )
        }
    }
}