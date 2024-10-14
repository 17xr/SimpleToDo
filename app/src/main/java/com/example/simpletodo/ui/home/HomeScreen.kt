package com.example.simpletodo.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.simpletodo.R
import com.example.simpletodo.model.container.Task
import com.example.simpletodo.model.container.TaskDetails
import com.example.simpletodo.model.container.toTaskDetails

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onTaskUpdate: (Task) -> Unit,
    onTaskClicked: (TaskDetails) -> Unit,
    homeUiState: HomeUiState
) {
    if (homeUiState.taskList.isEmpty()) {
        NoTaskList(
            modifier = modifier,
            message = "No Tasks Available."
        )
    } else {
        TaskList(
            modifier = modifier,
            taskList = homeUiState.taskList,
            onTaskUpdate = onTaskUpdate,
            onTaskClicked = onTaskClicked
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenTopAppBar(modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.SemiBold)
            )
        }
    )
}

@Composable
fun HomeScreenFAB(
    modifier: Modifier = Modifier,
    onAddClicked: () -> Unit
) {
    FloatingActionButton(
        modifier = modifier,
        onClick = onAddClicked
    ) {
        Icon(
            painter = painterResource(id = R.drawable.add),
            contentDescription = null
        )
    }
}

@Composable
fun TaskList(
    modifier: Modifier = Modifier,
    onTaskUpdate: (Task) -> Unit,
    onTaskClicked: (TaskDetails) -> Unit,
    taskList: List<Task>
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(space = 16.dp)
    ) {
        items(
            items = taskList
        ) { item ->

            TaskCard(
                task = item,
                onCheckedChange = {
                    onTaskUpdate(item.copy(done = it))
                },
                onTaskClicked = onTaskClicked
            )
        }
    }
}

@Composable
fun TaskCard(
    modifier: Modifier = Modifier,
    onCheckedChange: (Boolean) -> Unit,
    onTaskClicked: (TaskDetails) -> Unit,
    task: Task
) {
    Row(
        modifier = modifier
            .clickable { onTaskClicked(task.toTaskDetails()) }
            .height(intrinsicSize = IntrinsicSize.Max)
            .fillMaxWidth()
            .border(
                width = 2.dp,
                color = task.type.color,
                shape = RoundedCornerShape(size = 16.dp)
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        DateInformation(
            task = task
        )

        DetailedInformation(
            onCheckedChange = onCheckedChange,
            task = task
        )
    }
}

@Composable
fun DateInformation(
    modifier: Modifier = Modifier,
    task: Task
) {
    Column(
        modifier = modifier
            .clip(
                shape = RoundedCornerShape(
                    topStart = 16.dp,
                    bottomStart = 16.dp
                )
            )
            .background(color = task.type.color)
            .width(width = 48.dp)
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = task.date.dayOfMonth.toString(),
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold)
        )

        Text(
            text = task.date.month.toString().substring(0, 3),
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold)
        )
    }
}

@Composable
fun DetailedInformation(
    modifier: Modifier = Modifier,
    onCheckedChange: (Boolean) -> Unit,
    task: Task
) {
    Column(
        modifier = modifier
            .padding(
                start = 16.dp,
                top = 8.dp,
                end = 16.dp,
                bottom = 16.dp
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = task.title,
                textDecoration = if (task.done) TextDecoration.LineThrough else TextDecoration.None,
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(
                modifier = Modifier
                    .weight(weight = 1f)
            )

            Checkbox(
                checked = task.done,
                onCheckedChange = onCheckedChange,
                colors = CheckboxDefaults.colors(
                    checkedColor = task.type.color
                )
            )
        }

        Box(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = task.type.color,
                    shape = RoundedCornerShape(size = 8.dp)
                )
        ) {
            Text(
                modifier = Modifier
                    .padding(
                        horizontal = 8.dp,
                        vertical = 4.dp
                    ),
                text = task.type.toString(),
                style = MaterialTheme.typography.bodySmall
            )
        }

        Text(
            modifier = Modifier
                .padding(top = 8.dp),
            text = task.description,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.65f),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun NoTaskList(
    modifier: Modifier = Modifier,
    message: String
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}