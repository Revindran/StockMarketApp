package com.rar.devs.stockmarketapp.presentation.company_listing

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.rar.devs.stockmarketapp.presentation.destinations.CompanyInfoScreenDestination

@OptIn(ExperimentalMaterialApi::class)
@Composable
@Destination(start = true)
fun CompanyListingScreen(
    navigator: DestinationsNavigator,
    viewModel: CompanyListingsViewModel = hiltViewModel(),
) {
    val state = viewModel.state
    val pullRefreshState = rememberPullRefreshState(refreshing = state.isLoading, onRefresh = {
        viewModel.onEvent(CompanyListingsEvent.Refresh)
    })

    val context = LocalContext.current

    Column(modifier = Modifier.fillMaxSize()) {
        OutlinedTextField(
            value = state.searchQuery,
            onValueChange = { viewModel.onEvent(CompanyListingsEvent.SearchQuery(it)) },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            placeholder = { Text(text = "Search...") },
            maxLines = 1, singleLine = true
        )
        Box(
            modifier = Modifier
                .pullRefresh(pullRefreshState)
                .align(CenterHorizontally)
        ) {
            if (state.isLoading) {
                PullRefreshIndicator(
                    true,
                    pullRefreshState,
                    Modifier.align(Alignment.TopCenter)
                )
            }
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state.companies.size) { i ->
                    val company = state.companies[i]
                    CompanyItem(company = company, modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navigator.navigate(CompanyInfoScreenDestination(company.symbol))
                        }
                        .padding(16.dp)
                    )
                    if (i < state.companies.size) {
                        Divider(modifier = Modifier.padding(horizontal = 16.dp))
                    }
                }
            }
        }
    }
}