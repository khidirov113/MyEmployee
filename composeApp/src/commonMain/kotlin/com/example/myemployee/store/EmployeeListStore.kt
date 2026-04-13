package com.example.myemployee.store

import com.arkivanov.mvikotlin.core.store.Store
import com.example.myemployee.presentation.EmployeeListState
import com.example.myemployee.presentation.Intent
import com.example.myemployee.presentation.Label

interface EmployeeListStore : Store<Intent, EmployeeListState, Label>