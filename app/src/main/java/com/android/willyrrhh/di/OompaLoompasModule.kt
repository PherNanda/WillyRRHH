package com.android.willyrrhh.di

import com.android.willyrrhh.ui.viewmodel.OompaLoompasViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val oompaLoompasModule = module {
    viewModel {
        OompaLoompasViewModel(
            oompaLoompaRepository = get()
        )
    }
}