package com.openclassrooms.realestatemanager.loan

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.Toolbar
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.ActivityLoanBinding
import kotlin.math.pow


class LoanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoanBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        configureToolbar()

        setOnClickListener()
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // For ToolBar
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // ------ Toolbar ------
    private fun configureToolbar() {
        val mainActivityToolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(mainActivityToolbar)
        title = "Simulate your loan"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // For calculate the loan
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private fun setOnClickListener(){
        binding.loanTermYears.setOnClickListener{displayPopupMenu()}
        binding.calculateLoanButton.setOnClickListener { calculate() }
    }

    private fun displayPopupMenu(){
        val popupMenu = PopupMenu(this, binding.loanTermYears)
        popupMenu.menuInflater.inflate(R.menu.popup_menu_loan_term, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { item -> binding.loanTermYears.setText(item.title); true}
        popupMenu.show()
    }
    
    private fun calculate(){
        val canCalculate: Boolean
        val amount = binding.loanAmount.text.toString().toDoubleOrNull() ?: 0.0
        val downPayment = binding.loanDownPayment.text.toString().toDoubleOrNull() ?: 0.0
        val term = binding.loanTermYears.text.toString().toDoubleOrNull()
        val interest = binding.loanInterestRate.text.toString().toDoubleOrNull()

        when{
            binding.loanAmount.text.isNullOrEmpty() || binding.loanTermYears.text.isNullOrEmpty() || binding.loanInterestRate.text.isNullOrEmpty() || downPayment >= amount -> {
                canCalculate = false
                if (binding.loanAmount.text.isNullOrEmpty()){
                    binding.loanAmountInputlayout.error = resources.getString(R.string.loan_error)
                }
                if (binding.loanTermYears.text.isNullOrEmpty()){
                    binding.loanOptionTermInputlayout.error = resources.getString(R.string.loan_error)
                }
                if (binding.loanInterestRate.text.isNullOrEmpty()){
                    binding.loanInterestRateInputlayout.error = resources.getString(R.string.loan_error)
                }else if (interest!! < 0 || interest > 100){
                    binding.loanInterestRateInputlayout.error = resources.getString(R.string.loan_error_interest_value)
                }
                if (downPayment >= amount){
                    binding.loanDownPaymentInputlayout.error = resources.getString(R.string.loan_error_down_payment)
                }
            }
            else -> {
                canCalculate = true
                binding.loanAmountInputlayout.error = null
                binding.loanOptionTermInputlayout.error = null
                binding.loanInterestRateInputlayout.error = null
                binding.loanDownPaymentInputlayout.error = null
            }
        }

        if (canCalculate){
            val result: Double
            val totalPrice: Double
            if(interest == 0.0){
                result = (amount-(downPayment)) / (term!! * 12)
                totalPrice = 0.0
            }else{
                result = (amount -(downPayment)) * ((interest!! / (100)) / (12)) / (1 - (1 + ((interest / 100) / 12)).pow(-term!! * 12
                ))
                totalPrice = 12 * term * result - (amount.minus(downPayment))
            }
            binding.monthlyPay.setText(String.format("%.2f",result), TextView.BufferType.EDITABLE)
            binding.interestTotalCost.setText(String.format("%.2f",totalPrice), TextView.BufferType.EDITABLE)

        }
    }
}