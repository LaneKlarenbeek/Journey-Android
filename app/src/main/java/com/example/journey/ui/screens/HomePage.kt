package com.example.journey.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.journey.R
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.TextButton
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.journey.data.local.entity.JourneyRecord
import com.example.journey.data.local.entity.JourneyRecordWithDetails
import com.example.journey.data.local.entity.JourneyTemplate
import com.example.journey.data.local.entity.JourneyTemplateWithStops
import com.example.journey.data.local.entity.NoteRecord
import com.example.journey.data.local.entity.StopRecord
import com.example.journey.data.local.entity.StopRecordWithNotes
import com.example.journey.data.local.entity.StopTemplate
import com.example.journey.utils.copyToClipboard
import com.example.journey.utils.formatFullJourney
import java.text.SimpleDateFormat
import java.util.logging.SimpleFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(
    userName: String,
    templates: List<JourneyTemplateWithStops>,
    completedJourneys: List<JourneyRecordWithDetails>,
    onSaveTemplate: (String, List<String>) -> Unit = { _, _ -> },
    onDeleteTemplate: (JourneyTemplate) -> Unit = {},
    onEditTemplate: (templateId: Long, newName: String, newStops: List<String>) -> Unit,
    onJourneyStart: (JourneyTemplateWithStops) -> Unit,
    onDeleteRecord: (journeyRecord: JourneyRecord) -> Unit
){
    var isMenuExpanded by remember { mutableStateOf(false) }
    var showCreateTemplateDialog by remember { mutableStateOf(false) }
    var showStopAddDialog by remember { mutableStateOf(false) }
    var templateToEdit by remember { mutableStateOf<JourneyTemplateWithStops?>(null) }

    var showTemplateList by remember { mutableStateOf(true)}
    var showCompletedJourneys by remember {mutableStateOf(false )}

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

                    /*************************
                     * AnimatedList for displaying the action menu
                     **************************/
                    AnimatedVisibility(
                        visible = isMenuExpanded,
                        enter = fadeIn() + slideInVertically(initialOffsetY = { it / 2 }),
                        exit = fadeOut() + slideOutVertically(targetOffsetY = { it / 2 })
                    )
                    {
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
                                        color = Color.Black,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                    )
                                }

                                SmallFloatingActionButton(
                                    onClick = {
                                        isMenuExpanded = false
                                        showCreateTemplateDialog = true
                                    },
                                    containerColor = Color(0xFF927155),
                                    contentColor = Color.Black
                                ) {
                                    Text("+")
                                }
                            }

                            // Additional Buttons can be added to the column here.
                        }
                    }

                    /*************************
                     * Floating Action Button for viewing the action menu
                     **************************/
                    if(showTemplateList) {
                        FloatingActionButton(
                            onClick = {
                                isMenuExpanded = !isMenuExpanded
                            },
                            containerColor = Color(0xFF927155),
                            contentColor = Color.White,
                            shape = RoundedCornerShape(12.dp)
                        )
                        {
                            if (isMenuExpanded) {
                                Text(
                                    text = "X",
                                    fontSize = 24.sp,
                                    color = Color.Black,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            } else {
                                Image(
                                    painter = painterResource(id = R.drawable.add_template_no_background_black),
                                    contentDescription = "Menu",
                                    modifier = Modifier.size(32.dp)
                                )
                            }
                        }
                    }

                }
            }
        )
        { innerPadding ->

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .windowInsetsPadding(WindowInsets.statusBars),
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Hello $userName!",
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
                Spacer(modifier = Modifier.height(16.dp))

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .windowInsetsPadding(WindowInsets.statusBars),
                ) {

                    Spacer(modifier = Modifier.height(32.dp))

                    Row(
                        modifier = Modifier
                            .height(25.dp)
                            .fillMaxWidth()
                        ,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ){
                        FloatingActionButton(
                            onClick = {
                                showTemplateList = true
                                showCompletedJourneys = false
                            },
                            containerColor = Color(0xFF927155),
                            contentColor = Color.White,
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = "Templates",
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .padding(horizontal = 24.dp),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,
                                textAlign = TextAlign.Left
                            )
                        }

                        FloatingActionButton(
                            onClick = {
                                showTemplateList = false
                                showCompletedJourneys = true
                            },
                            containerColor = Color(0xFF927155),
                            contentColor = Color.White,
                            shape = RoundedCornerShape(12.dp)
                        ){
                            Text(
                                text = "Completed",
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .padding(horizontal = 24.dp),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,
                                textAlign = TextAlign.Left
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    if(showTemplateList){
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 24.dp)
                                .weight(1f),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(templates) { templateData ->
                                TemplateListItem(
                                    templateData = templateData,
                                    onEdit = { templateToEdit = templateData },
                                    onDelete = { onDeleteTemplate(templateData.template) },
                                    onStart = { onJourneyStart(templateData) },
                                )
                            }
                        }
                    } else if(showCompletedJourneys){

                        //formatters for formatting the date and time of the records in list
                        val dayFormatter = SimpleDateFormat("MM-dd",java.util.Locale.getDefault())
                        val timeFormatter = SimpleDateFormat("HH:mm",java.util.Locale.getDefault())

                        //reverses the list so that the more recent data is displayed first
                        val displayedJourneys = remember(completedJourneys) {
                            completedJourneys.reversed()
                        }

                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 24.dp)
                                .weight(1f),
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                        ) {
                            items(displayedJourneys) { journeyData ->
                                CompletedListItem(
                                    journeyData = journeyData,
                                    dayFormatter,
                                    timeFormatter,
                                    onDelete = { onDeleteRecord(journeyData.journey) }
                                )
                            }
                        }
                    }
                }
            }

            /*********************************************
            Below this point is supporting code
            for the Home Page Dialog Boxes
            *********************************************/


            /*************************
             * Dialog page for Creating a new template
             * Specifically for the Template Name
             **************************/
            if(showCreateTemplateDialog)
            {
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

            /*************************
            * Dialog page for adding stops to a journey template
            **************************/
            if(showStopAddDialog)
            {
                Dialog(
                    onDismissRequest = { showStopAddDialog = false },
                ){
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
                                                Image(
                                                    painter = painterResource(id = R.drawable.delete_icon_brown),
                                                    contentDescription = "Delete Stop",
                                                    modifier = Modifier.size(20.dp)

                                                )
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
                                        onSaveTemplate(templateName, stopsList.toList())
                                        stopsList.clear()
                                        templateName = ""
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

            /*************************
             * Dialog page for editing an existing template
             * Updates the name, deletes the existing stops
             * and replaces the list
             **************************/
            if (templateToEdit != null)
            {
                val currentTemplate = templateToEdit!!

                // Pre-fill the state with the existing data
                var editTemplateName by remember { mutableStateOf(currentTemplate.template.title) }
                val editStopsList = remember {
                    mutableStateListOf<String>().apply {
                        // Sort by sequenceOrder to ensure they load in the exact order they were saved
                        addAll(currentTemplate.stops.sortedBy { it.sequenceOrder }.map { it.locationName })
                    }
                }
                var editStopName by remember { mutableStateOf("") }

                Dialog(
                    onDismissRequest = { templateToEdit = null },
                ) {
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = Color.White,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)
                            .heightIn(max = 600.dp) // Prevents the dialog from getting too tall
                    ) {
                        Column(
                            modifier = Modifier.padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Edit Template",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF896A4E)
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            OutlinedTextField(
                                value = editTemplateName,
                                onValueChange = { editTemplateName = it },
                                label = { Text("Template Name") },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true,
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color(0xFF927155),
                                    focusedLabelColor = Color(0xFF927155)
                                )
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            // Scrollable list of existing stops
                            LazyColumn(modifier = Modifier.weight(1f, fill = false)) {
                                items(editStopsList.size) { index ->
                                    ListItem(
                                        headlineContent = { Text(editStopsList[index]) },
                                        trailingContent = {
                                            IconButton(
                                                onClick = { editStopsList.removeAt(index) }
                                            ) {
                                                Image(
                                                    painter = painterResource(id = R.drawable.delete_icon_brown),
                                                    contentDescription = "Delete Stop",
                                                    modifier = Modifier.size(20.dp)
                                                )
                                            }
                                        }
                                    )
                                    HorizontalDivider()
                                }
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                OutlinedTextField(
                                    value = editStopName,
                                    onValueChange = { editStopName = it },
                                    label = { Text("Add Stop") },
                                    modifier = Modifier.weight(1f),
                                    singleLine = true,
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = Color(0xFF927155),
                                        focusedLabelColor = Color(0xFF927155)
                                    )
                                )

                                TextButton(
                                    onClick = {
                                        if (editStopName.isNotBlank()) {
                                            editStopsList.add(editStopName)
                                            editStopName = ""
                                        }
                                    },
                                    modifier = Modifier.padding(end = 8.dp)
                                ) {
                                    Text("Add", color = Color.Gray)
                                }
                            }

                            Spacer(modifier = Modifier.height(24.dp))

                            Row(
                                horizontalArrangement = Arrangement.End,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                TextButton(
                                    onClick = { templateToEdit = null },
                                    modifier = Modifier.padding(end = 8.dp)
                                ) {
                                    Text("Cancel", color = Color.Gray)
                                }

                                Button(
                                    onClick = {
                                        // Pass the ID, the updated name, and the frozen list to the ViewModel
                                        onEditTemplate(
                                            currentTemplate.template.templateId,
                                            editTemplateName,
                                            editStopsList.toList()
                                        )
                                        templateToEdit = null // Close the dialog
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF927155))
                                ) {
                                    Text("Save Changes")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

/*************************
 * Composable to display a Template in list
 * on the page when creating a new Journey Template
 **************************/
@Composable
fun TemplateListItem(
    templateData: JourneyTemplateWithStops,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onStart: () -> Unit
)
{
    var showMenu by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = templateData.template.title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF896A4E)
                )
                Text(
                    text = "${templateData.stops.size} Stops",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }

            Box{
                IconButton(onClick = { onStart() }) {
                    Image(
                        painter = painterResource(id = R.drawable.play_icon_green),
                        contentDescription = "Template Options",
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Box {
                IconButton(onClick = { showMenu = true }) {
                    Icon(
                        painter = painterResource(id = R.drawable.more_menu_vertical),
                        contentDescription = "Template Options",
                        tint = Color.Gray,
                        modifier = Modifier.size(20.dp)
                    )
                }

                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Edit") },
                        onClick = {
                            showMenu = false
                            onEdit()
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Delete", color = Color.Red) },
                        onClick = {
                            showMenu = false
                            onDelete()
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun CompletedListItem(
    journeyData: JourneyRecordWithDetails,
    dayFormatter: SimpleDateFormat,
    timeFormatter: SimpleDateFormat,
    onDelete: () -> Unit
){
    var showMenu by remember { mutableStateOf(false)}

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        val context = LocalContext.current

        val day: String = dayFormatter.format(java.util.Date(journeyData.journey.startTimeStamp))
        val startTime = timeFormatter.format(java.util.Date(journeyData.journey.startTimeStamp))
        val endTime = timeFormatter.format(java.util.Date(journeyData.journey.endTimeStamp ?: (journeyData.journey.startTimeStamp + 20)))


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {

                Text(
                    text = journeyData.journey.title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF896A4E)
                )

                Text(
                    text = day,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF896A4E)
                )

                Text(
                    text = "$startTime - $endTime",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }

            Box{
                //Copy Button
                IconButton(onClick = {
                    val journeySummary = formatFullJourney(journeyData, timeFormatter)
                    context.copyToClipboard(journeySummary,"Journey Copy")
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.copy_all_black),
                        contentDescription = "Template Options",
                        tint = Color.Gray,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
            Box {
                //Dropdown menu for selections
                IconButton(onClick = { showMenu = true }) {
                    Icon(
                        painter = painterResource(id = R.drawable.more_menu_vertical),
                        contentDescription = "Template Options",
                        tint = Color.Gray,
                        modifier = Modifier.size(20.dp)
                    )
                }

                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Delete", color = Color.Red) },
                        onClick = {
                            showMenu = false
                            onDelete()
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CompletedJourneyPreview(){


    val timeStampFiller = System.currentTimeMillis()

    val temp = JourneyTemplate(0, "Rounds")
    val stops: List<StopTemplate> = listOf(
        StopTemplate(0, 0, "Higbie", 0),
        StopTemplate(0, 0, "Residence Village", 0)
    )

    val stos = StopRecord(0,0,"Higbie",0,timeStampFiller)
    val notesList: List<NoteRecord> = listOf(
        NoteRecord(0, 0, "Spill on floor cleaned up", timeStampFiller),
        NoteRecord(1, 0, "Told room 308 to quiet down", timeStampFiller),
        NoteRecord(2, 0, "Maintenance door unlocked", timeStampFiller)

    )


    val newRecord = JourneyRecord(0,"Rounds",timeStampFiller,timeStampFiller)
    val newStopList: List<StopRecordWithNotes> = listOf(
        StopRecordWithNotes(stos, notesList)
    )

    val journey: List<JourneyRecordWithDetails> = listOf(
        JourneyRecordWithDetails(newRecord, newStopList)
    )

    val templateWithStops: List<JourneyTemplateWithStops> = listOf(JourneyTemplateWithStops(temp, stops))

    HomePage(
        "Lane",
        templateWithStops,
        journey,
        onEditTemplate = { _, _, _ -> },
        onJourneyStart = {},
        onDeleteRecord = {}
    )
}