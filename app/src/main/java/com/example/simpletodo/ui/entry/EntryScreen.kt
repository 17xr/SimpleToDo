package com.example.simpletodo.ui.entry

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.simpletodo.R
import com.example.simpletodo.model.container.TaskDetails
import com.example.simpletodo.model.container.TaskType
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryScreenTopBar(modifier: Modifier = Modifier, onBackClicked: () -> Unit = {}) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = "New Task",
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.SemiBold)
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackClicked) {
                Icon(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    painter = painterResource(id = R.drawable.arrow_back),
                    tint = MaterialTheme.colorScheme.onBackground,
                    contentDescription = null
                )
            }
        }
    )
}

@Composable
fun EntryScreen(
    modifier: Modifier = Modifier,
    entryUiState: EntryUiState,
    onValueChange: (TaskDetails) -> Unit,
    onSaveClicked: (TaskDetails) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(space = 16.dp)
    ) {
        item {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = entryUiState.taskDetails.title,
                onValueChange = { onValueChange(entryUiState.taskDetails.copy(title = it)) },
                label = { Text(text = "Title") },
                shape = RoundedCornerShape(size = 16.dp)
            )
        }

        item {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = entryUiState.taskDetails.description,
                onValueChange = { onValueChange(entryUiState.taskDetails.copy(description = it)) },
                label = { Text(text = "Description") },
                shape = RoundedCornerShape(size = 16.dp)
            )
        }

        item {
            val formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd", Locale.getDefault())

            DateEntryForm(
                modifier = Modifier.fillMaxWidth(),
                value = entryUiState.taskDetails.date.format(formatter) ?: "",
                onDateSelected = {
                    onValueChange(
                        entryUiState.taskDetails.copy(date = LocalDate.parse(it, formatter))
                    )
                },
                label = "Date"
            )
        }

        item {
            TypeEntryForm(
                modifier = Modifier.fillMaxWidth(),
                value = entryUiState.taskDetails.type.name,
                onTypeSelected = { onValueChange(entryUiState.taskDetails.copy(type = it)) },
                label = "Type"
            )
        }

        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .clip(shape = RoundedCornerShape(size = 16.dp))
            ) {
                Row(
                    modifier = Modifier
                        .clickable { onValueChange(entryUiState.taskDetails.copy(done = !entryUiState.taskDetails.done)) },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Done")

                    Spacer(modifier = Modifier.weight(weight = 1f))

                    Switch(
                        checked = entryUiState.taskDetails.done,
                        onCheckedChange = { onValueChange(entryUiState.taskDetails.copy(done = !entryUiState.taskDetails.done)) }
                    )
                }
            }
        }

        item {
            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = { onSaveClicked(entryUiState.taskDetails) },
                enabled = entryUiState.isTaskValid,
                shape = RoundedCornerShape(size = 16.dp)
            ) {
                Text(text = "Save")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateEntryForm(
    modifier: Modifier = Modifier,
    onDateSelected: (String) -> Unit,
    value: String,
    label: String
) {
    val datePickerState = rememberDatePickerState()
    val showDatePicker = remember { mutableStateOf(false) }

    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = {},
        readOnly = true,
        label = { Text(text = label) },
        trailingIcon = {
            IconButton(onClick = { showDatePicker.value = true }) {
                Icon(
                    painter = painterResource(id = R.drawable.calendar_month),
                    contentDescription = null
                )
            }
        },
        singleLine = true,
        shape = RoundedCornerShape(size = 16.dp)
    )

    if (showDatePicker.value) {
        DatePickerDialog(
            modifier = modifier,
            onDismissRequest = { showDatePicker.value = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDateSelected(
                            SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
                                .format(Date(datePickerState.selectedDateMillis ?: 0))
                        )
                        showDatePicker.value = false
                    },
                    enabled = datePickerState.selectedDateMillis != null
                ) {
                    Text(text = "Ok")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDatePicker.value = false
                    }
                ) { Text(text = "Cancel") }
            }
        ) {
            DatePicker(state = datePickerState, showModeToggle = false)
        }
    }
}

@Composable
fun TypeEntryForm(
    modifier: Modifier = Modifier,
    onTypeSelected: (TaskType) -> Unit,
    value: String,
    label: String
) {
    val showDropdownMenu = remember { mutableStateOf(false) }

    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = {},
        readOnly = true,
        label = { Text(text = label) },
        trailingIcon = {
            IconButton(onClick = { showDropdownMenu.value = true }) {
                Icon(
                    painter = painterResource(id = R.drawable.more_vert),
                    contentDescription = null
                )

                DropdownMenu(
                    expanded = showDropdownMenu.value,
                    onDismissRequest = { showDropdownMenu.value = false }) {
                    TaskType.entries.forEach {
                        DropdownMenuItem(
                            text = { Text(text = it.name) },
                            onClick = {
                                onTypeSelected(it)
                                showDropdownMenu.value = false
                            }
                        )
                    }
                }
            }
        },
        singleLine = true,
        shape = RoundedCornerShape(size = 16.dp)
    )
}
