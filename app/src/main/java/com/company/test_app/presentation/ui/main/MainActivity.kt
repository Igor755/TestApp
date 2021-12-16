package com.company.test_app.presentation.ui.main

import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.company.test_app.R
import com.company.test_app.presentation.ui.BaseActivity
import com.company.test_app.presentation.viewmodel.BaseViewModel
import com.company.test_app.presentation.viewmodel.main.MainViewModel
import kotlinx.android.synthetic.main.progress.*

class MainActivity : BaseActivity() {

    var onFragmentBackPressed: (() -> Unit)? = null

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
    }

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun getNavContainerId(): Int = R.id.a_main_nav_container

    override fun getProgressLayout(): View? = progress_fl

    override fun getViewModel(): BaseViewModel = viewModel

    override fun onBackPressed() {
        if (onFragmentBackPressed != null) {
            onFragmentBackPressed?.invoke()
        } else {
            super.onBackPressed()
            finish()
        }
    }

}
