package com.dv.minimaltasksapp

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Checkbox
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dv.minimaltasksapp.data.Task
import com.dv.minimaltasksapp.ui.theme.MinimalTasksTheme
import com.dv.minimaltasksapp.viewmodels.TasksFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

// TODO write javaDoc in all public methods
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: TasksFragmentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MinimalTasksTheme {
                MainScreen()
            }
        }
    }

    @Composable
    fun MainScreen() {
        val tasks: List<Task> by viewModel.getTasks().collectAsState(initial = emptyList())
        val listState = rememberLazyListState()
        val scope = rememberCoroutineScope()

        Scaffold(
            floatingActionButton = {
                FloatingActionButton(
                    modifier = Modifier.padding(bottom = 16.dp),
                    contentColor = Color.White,
                    onClick = {
                        viewModel.addTask("task ${tasks.size}")
                        if (tasks.isNotEmpty()) {
                            scope.launch {
                                listState.scrollToItem(tasks.size - 1)
                            }
                        }
                    }
                ) {
                    Icon(Icons.Filled.Add, null)
                }
            },
            floatingActionButtonPosition = FabPosition.Center
        ) {
            MasterCard(listState, tasks, it)
        }
    }

    @Composable
    fun MasterCard(listState: LazyListState, tasks: List<Task>, paddingValues: PaddingValues) {
        LazyColumn(
            modifier = Modifier.padding(paddingValues),
            state = listState,
            contentPadding = PaddingValues(horizontal = 16.dp),
            reverseLayout = true
        ) {
            itemsIndexed(
                items = tasks,
                key = { _, listItem ->
                    listItem.hashCode()
                }
            ) { index, task ->
                DismissibleTask(
                    task = task,
                    isFirstElement = index == 0,
                    isLastElement = index == tasks.size - 1
                )
            }
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun DismissibleTask(task: Task, isFirstElement: Boolean, isLastElement: Boolean) {
        val dismissState = rememberDismissState(
            confirmStateChange = {
                if (it == DismissValue.DismissedToStart) {
                    viewModel.removeTask(task)
                }
                true
            }
        )

        val bottomPadding = if (isFirstElement) {
            110.dp
        } else {
            16.dp
        }

        val topPadding = if (isLastElement) {
            500.dp
        } else {
            0.dp
        }

        SwipeToDismiss(
            modifier = Modifier.padding(bottom = bottomPadding, top = topPadding),
            state = dismissState,
            background = {
                val color = when (dismissState.dismissDirection) {
                    DismissDirection.EndToStart -> Color.Red
                    else -> Color.Transparent
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .fillMaxWidth()
                        .height(56.dp)
                        .background(color)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(end = 8.dp)
                    )
                }
            },
            dismissContent = {
                TaskItem(task)
            },
            directions = setOf(DismissDirection.EndToStart)
        )
    }

    @Composable
    fun TaskItem(task: Task) {
        Surface(
            shape = RoundedCornerShape(8.dp),
            elevation = 2.dp,
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    viewModel.toggleTask(task)
                }
        ) {
            Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = task.done, onCheckedChange = null)
                Text(
                    modifier = Modifier.padding(start = 16.dp),
                    text = task.text,
                    style = typography.body1.copy(
                        fontSize = 18.sp,
                        textDecoration = if (task.done) {
                            TextDecoration.LineThrough
                        } else {
                            TextDecoration.None
                        }
                    )
                )
            }
        }
    }

    @Preview(name = "Light Mode", showBackground = true)
    @Preview(
        uiMode = Configuration.UI_MODE_NIGHT_YES,
        showBackground = true,
        name = "Dark Mode"
    )
    @Composable
    fun PreviewMasterCard() {
        MinimalTasksTheme {
            MainScreen()
        }
    }
}