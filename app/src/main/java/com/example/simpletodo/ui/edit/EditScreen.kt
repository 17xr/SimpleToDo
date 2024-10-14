package com.example.simpletodo.ui.edit

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.simpletodo.R
import com.example.simpletodo.model.container.Task
import com.example.simpletodo.model.container.TaskDetails
import com.example.simpletodo.model.container.toTask
import com.example.simpletodo.ui.entry.DateEntryForm
import com.example.simpletodo.ui.entry.TypeEntryForm
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditScreenTopBar(modifier: Modifier = Modifier, onBackClicked: () -> Unit = {}) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = "Edit Task",
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
fun EditScreen(
    modifier: Modifier = Modifier,
    editUiState: EditUiState,
    onValueChange: (TaskDetails) -> Unit,
    onSaveClicked: (TaskDetails) -> Unit,
    onDeleteClicked: (Task) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(space = 16.dp)
    ) {
        item {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = editUiState.taskDetails.title,
                onValueChange = { onValueChange(editUiState.taskDetails.copy(title = it)) },
                label = { Text(text = "Title") },
                shape = RoundedCornerShape(size = 16.dp)
            )
        }

        item {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = editUiState.taskDetails.description,
                onValueChange = { onValueChange(editUiState.taskDetails.copy(description = it)) },
                label = { Text(text = "Description") },
                shape = RoundedCornerShape(size = 16.dp)
            )
        }

        item {
            val formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd", Locale.getDefault())

            DateEntryForm(
                modifier = Modifier.fillMaxWidth(),
                value = editUiState.taskDetails.date.format(formatter) ?: "",
                onDateSelected = {
                    onValueChange(
                        editUiState.taskDetails.copy(date = LocalDate.parse(it, formatter))
                    )
                },
                label = "Date"
            )
        }

        item {
            TypeEntryForm(
                modifier = Modifier.fillMaxWidth(),
                value = editUiState.taskDetails.type.name,
                onTypeSelected = { onValueChange(editUiState.taskDetails.copy(type = it)) },
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
                        .clickable { onValueChange(editUiState.taskDetails.copy(done = !editUiState.taskDetails.done)) },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Done")

                    Spacer(modifier = Modifier.weight(weight = 1f))

                    Switch(
                        checked = editUiState.taskDetails.done,
                        onCheckedChange = { onValueChange(editUiState.taskDetails.copy(done = !editUiState.taskDetails.done)) }
                    )
                }
            }
        }

        item {
            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = { onDeleteClicked(editUiState.taskDetails.toTask()) },
                shape = RoundedCornerShape(size = 16.dp)
            ) {
                Text(text = "Delete")
            }

            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = { onSaveClicked(editUiState.taskDetails) },
                enabled = editUiState.isTaskValid,
                shape = RoundedCornerShape(size = 16.dp)
            ) {
                Text(text = "Save")
            }
        }
    }
}