package com.company.test_app.presentation.adapter.callback

interface OnSelectionListener<T> {

    fun onItemSelected(item: T?, isChecked: Boolean)
    fun onItemsSelected(items: List<T>)

}