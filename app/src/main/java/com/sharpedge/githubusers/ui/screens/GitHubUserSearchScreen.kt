import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sharpedge.githubusers.viewmodel.GitHubUserViewModel



import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.ImeAction
import com.sharpedge.githubusers.ui.state.ErrorType
import com.sharpedge.githubusers.ui.models.UserUIModel
import com.sharpedge.githubusers.ui.others.UserListItem
import com.sharpedge.githubusers.utils.toUserFriendlyMessage


@Composable
fun GitHubUserSearchScreen(
    viewModel: GitHubUserViewModel,
    onUserClicked: (UserUIModel) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        TextFieldWithDebounce(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            onSearch = { searchText ->
                viewModel.searchUsers(searchText)
            }
        )

        UserListContent(viewModel, onUserClicked)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldWithDebounce(
    modifier: Modifier = Modifier,
    onSearch: (String) -> Unit
) {
    var searchText by remember { mutableStateOf("") }

    OutlinedTextField(
        value = searchText,
        onValueChange = { newText ->
            searchText = newText
            //if (newText.length >= 3) {
                onSearch(newText)
            //}
        },
        modifier = modifier,
        label = { Text("Search GitHub Users") },
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = {
            onSearch(searchText)
        })
    )
}


@Composable
fun UserListContent(
    viewModel: GitHubUserViewModel,
    onUserClicked: (UserUIModel) -> Unit
) {
    val viewState by viewModel.viewState.collectAsState()

    when {
        viewState.isLoading -> {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
        viewState.error != ErrorType.None -> {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = "Error: ${viewState.error.toUserFriendlyMessage()}",
                    modifier = Modifier.align(Alignment.Center)
                )
            }

        }
        viewState.users.isNotEmpty() -> {
            val userList = viewState.users
            LazyColumn {
                items(viewState.users.size) { index ->

                    UserListItem(userList[index], onUserClicked)
                }
            }
        }
    }
}
