package com.company.test_app.presentation.ui.main.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.company.test_app.R
import com.company.test_app.common.Navigate
import com.company.test_app.data.entity.network.Result
import com.company.test_app.data.entity.presentation.Currency
import com.company.test_app.presentation.ui.BaseFragment
import com.company.test_app.presentation.ui.main.MainActivity
import com.company.test_app.presentation.ui.main.adapter.DepartmentAdapter
import com.company.test_app.presentation.viewmodel.main.PrivatBankViewModel
import kotlinx.android.synthetic.main.fragment_department.*
import kotlinx.android.synthetic.main.fragment_privat_bank.*

class DepartmentFragment: BaseFragment() {

    var departmentAdapter: DepartmentAdapter = DepartmentAdapter()

    private val viewModel: PrivatBankViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(PrivatBankViewModel::class.java)
    }

    override fun getLayoutId(): Int = R.layout.fragment_department

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initListeners()
        initObservers()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun initViews(){
        val dividerItemDecoration = DividerItemDecoration(context, RecyclerView.VERTICAL)
        dividerItemDecoration.setDrawable(resources.getDrawable(R.drawable.decorator_with_padding))
        rvDepartment.addItemDecoration(dividerItemDecoration)
        rvDepartment.adapter = departmentAdapter
    }

    fun initListeners(){
        val navOptions = NavOptions.Builder()
            .setEnterAnim(R.anim.enter_from_left)
            .setExitAnim(R.anim.exit_to_right)
            .setPopEnterAnim(R.anim.enter_from_right)
            .setPopExitAnim(R.anim.exit_to_left)
            .build()
        (activity as MainActivity).onFragmentBackPressed = {
            NavHostFragment.findNavController(this)
                .navigate(R.id.privatBankFragment, null, navOptions)
        }
    }

    fun initObservers(){
        viewModel.apply {
            navigationGlobalEvent.observe(this@DepartmentFragment, Observer(this@DepartmentFragment::handleGlobalNavigation))
        }
        pwSpinDepartment.isVisible = true
        pwSpinDepartment.spin()
        viewModel.getDepartmentPrivatBank()
        viewModel.getDepartmentLiveData.observe(viewLifecycleOwner, Observer { result->
            when (result) {
                is Result.Success -> {
                    if (result.data?.isNotEmpty() == true) {
                        pwSpinDepartment.stopSpinning()
                        departmentAdapter.setNewData(result.data)
                    }
                }
                is Result.Error -> {
                    pwSpinDepartment.stopSpinning()
                    Toast.makeText(context, result.exception.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun handleGlobalNavigation(event: Navigate.Global) {
        when(event){
            Navigate.Global.Close -> navigateUp()
            else -> notImplementedToast()
        }
    }
}