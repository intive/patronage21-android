package com.intive.audit.presentation.audit

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.intive.repository.Repository
import com.intive.repository.domain.model.Audit
import kotlinx.coroutines.launch
import com.intive.audit.presentation.audit.AuditListEvent.*

const val TAG = "AuditViewModel"
const val PAGE_SIZE = 30

class AuditViewModel(
    private val repository: Repository
) : ViewModel() {

    val audits: MutableState<List<Audit>> = mutableStateOf(listOf())

    val query = mutableStateOf("")

    val showSearchField = mutableStateOf(false)

    val showFilterField = mutableStateOf(false)

    val page = mutableStateOf(1)

    private var auditListScrollPosition = 0

    init {
        onTriggerEvent(NewSearchEvent)
    }

    fun onTriggerEvent(event: AuditListEvent){
        viewModelScope.launch {
            try {
                when (event) {
                    is NewSearchEvent -> {
                        newSearch()
                    }
                    is NextPageEvent -> {
                        nextPage()
                    }
                }
            } catch (e: Exception){
                Log.e(TAG, "onTriggerEvent: Exception: ${e}, ${e.cause}")
            }
        }
    }

    private suspend fun newSearch(){

        resetSearchState()

        val result = repository.searchAudits(
            page = 1,
            query = query.value
        )
        audits.value = result
    }

    private suspend fun nextPage(){

        /*
         prevent duplicate events due to recomposition happening to quickly -
         jezeli zjedziemy na dol ekranu, to zanim zaladuje sie kolejna strona mija kilka milisekud
         musimy unikac tego aby w przeciagu tych kilku milisekund nie wywolala sie funkcja nextPage (skoro juz czekamy na kolejna strone)
        */
        if ((auditListScrollPosition + 1) >= (page.value * PAGE_SIZE)) {
            incrementPage()
            val result = repository.searchAudits(
                page = page.value,
                query = query.value
            )
            appendAudits(result)
        }
    }

    private fun appendAudits(audits: List<Audit>) {
        val current = ArrayList(this.audits.value)
        current.addAll(audits)
        this.audits.value = current
    }

    private fun incrementPage() {
        this.page.value = page.value + 1
    }

    fun onChangeAuditScrollPosition(position: Int) {
        auditListScrollPosition = position
    }

    /**
     * Called when newSearch() is executed
     */
    private fun resetSearchState() {
        audits.value = listOf()
        page.value = 1
        onChangeAuditScrollPosition(0)
    }

    fun onQueryChanged(query: String) {
        this.query.value = query
    }

    fun onSearchIconClick(showSearchField: Boolean){
        this.showSearchField.value = showSearchField
    }

    fun onFilterIconClick(showFilterField: Boolean){
        this.showFilterField.value = showFilterField
    }
}