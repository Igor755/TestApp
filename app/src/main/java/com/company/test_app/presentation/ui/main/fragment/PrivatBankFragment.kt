package com.company.test_app.presentation.ui.main.fragment

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.company.test_app.R
import com.company.test_app.common.Navigate
import com.company.test_app.data.entity.network.Result
import com.company.test_app.data.entity.presentation.Currency
import com.company.test_app.presentation.ui.BaseFragment
import com.company.test_app.presentation.ui.main.MainActivity
import com.company.test_app.presentation.ui.main.adapter.PrivatBankAdapter
import com.company.test_app.presentation.viewmodel.main.PrivatBankViewModel
import kotlinx.android.synthetic.main.fragment_department.*
import kotlinx.android.synthetic.main.fragment_privat_bank.*
import java.util.*
import kotlin.collections.ArrayList

class PrivatBankFragment : BaseFragment() {

    var matchAdapter: PrivatBankAdapter = PrivatBankAdapter()
    private var mDateSetListener: OnDateSetListener? = null

    private val viewModel: PrivatBankViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(PrivatBankViewModel::class.java)
    }

    override fun getLayoutId(): Int = R.layout.fragment_privat_bank

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initListeners()
        initObservers()
    }

    private fun initViews(){
        rv_matches.adapter = matchAdapter
    }

    private fun initObservers(){
        viewModel.apply {
            navigationGlobalEvent.observe(this@PrivatBankFragment, Observer(this@PrivatBankFragment::handleGlobalNavigation))
        }
        getExchange(viewModel.currentDate)
    }

    private fun initListeners(){
        datePicker.setOnClickListener {
            val cal = Calendar.getInstance()
            val year = cal[Calendar.YEAR]
            val month = cal[Calendar.MONTH]
            val day = cal[Calendar.DAY_OF_MONTH]
            val datePickerDialog = DatePickerDialog(requireContext(), R.style.ThemeOverlay_AppCompat_Dialog, mDateSetListener,
                year,
                month,
                day
            )
            datePickerDialog.show()
        }
        mDateSetListener =
            OnDateSetListener { _, year, month, day ->
                var monthNew = month
                monthNew += 1
                val date = "$day.$monthNew.$year"
                tvDate.text = date
                getExchange(date)
            }
        btnDepartment.setOnClickListener {
            viewModel.goToDepartment()
        }
        btnTso.setOnClickListener {
            viewModel.goToTso()
        }
    }

    private fun getExchange(date : String){
        pwSpin.isVisible = true
        pwSpin.spin()
        viewModel.getExchangePrivatBank(date)
        viewModel.getCurrencyLiveData.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Result.Success -> {
                    if (result.data?.exchangeRate?.isNotEmpty() == true) {
                        pwSpin.stopSpinning()
                        matchAdapter.setNewData(result.data.exchangeRate)
                    }
                }
                is Result.Error -> {
                    pwSpin.stopSpinning()
                    Toast.makeText(context, result.exception.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
    
    private fun handleGlobalNavigation(event: Navigate.Global) {
        when(event){
            Navigate.Global.Department -> navigate(R.id.action_privatBankFragment_to_departmentFragment)
            Navigate.Global.TSO -> navigate(R.id.action_privatBankFragment_to_tsoFragment)
            else -> notImplementedToast()
        }
    }
}