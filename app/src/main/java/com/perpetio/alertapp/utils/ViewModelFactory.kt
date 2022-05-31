
package com.example.android.kotlincoroutines.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

fun <T : ViewModel, A, B> doubleArgViewModelFactory(constructor: (A, B) -> T):
        (A, B) -> ViewModelProvider.NewInstanceFactory {
    return { arg1: A, arg2: B ->
        object : ViewModelProvider.NewInstanceFactory() {
            @Suppress("UNCHECKED_CAST")
            override fun <V : ViewModel> create(modelClass: Class<V>): V {
                return constructor(arg1,  arg2) as V
            }
        }
    }
}
