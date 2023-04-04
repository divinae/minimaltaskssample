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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
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

        Scaffold(
            floatingActionButton = {
                FloatingActionButton(
                    modifier = Modifier.padding(bottom = 16.dp),
                    contentColor = Color.White,
                    onClick = {
                        viewModel.addTask("task ${tasks.size}")
                        lifecycleScope.launch {
                            if (tasks.isNotEmpty()) {
                                listState.scrollToItem(tasks.size - 1)
                            }
                        }
                    }
                ) {
                    Icon(Icons.Filled.Add, "")
                }
            },
            floatingActionButtonPosition = FabPosition.Center
        ) {
            MasterCard(listState, tasks, it)
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun MasterCard(listState: LazyListState, tasks: List<Task>, paddingValues: PaddingValues) {
        LazyColumn(
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
                val state = rememberDismissState(
                    confirmStateChange = {
                        if (it == DismissValue.DismissedToStart) {
                            viewModel.removeTask(task)
                        }
                        true
                    }
                )

                SwipeToDismiss(
                    state = state,
                    background = {
                        val color = when (state.dismissDirection) {
                            DismissDirection.StartToEnd -> Color.Transparent
                            DismissDirection.EndToStart -> Color.Red
                            null -> Color.Transparent
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
                        TaskItem(
                            task = task,
                            isFirst = index == 0,
                            isLast = index == tasks.size - 1
                        )
                    },
                    directions = setOf(DismissDirection.EndToStart)
                )
            }
        }
    }

    @Composable
    fun TaskItem(task: Task, isFirst: Boolean, isLast: Boolean) {
        val bottomPadding = if (isFirst) {
            110.dp
        } else {
            16.dp
        }

        val topPadding = if (isLast) {
            500.dp
        } else {
            0.dp
        }

        Surface(
            shape = RoundedCornerShape(8.dp),
            elevation = 2.dp,
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 0.dp, end = 0.dp, bottom = bottomPadding, top = topPadding)
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