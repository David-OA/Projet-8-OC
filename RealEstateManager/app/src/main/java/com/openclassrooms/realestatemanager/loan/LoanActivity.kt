package com.openclassrooms.realestatemanager.loan

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.ActivityLoanBinding


class LoanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoanBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setOnClickListener()
    }


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
        var canCalculate = false
        val amount = binding.loanAmount.text.toString().toDoubleOrNull() ?: 0.0
        val downPayment = binding.loanDownPayment.text.toString().toDoubleOrNull() ?: 0.0
        val term = binding.loanTermYears.text.toString().toDoubleOrNull()
        val interest = binding.loanInterestRate.text.toString().toDoubleOrNull()

        when{
            binding.loanAmount.text.isNullOrEmpty() || binding.loanTermYears.text.isNullOrEmpty() || binding.loanInterestRate.text.isNullOrEmpty() || downPayment >= amount!! -> {
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
                if (downPayment != null && downPayment >= amount!!){
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
                result = (if (downPayment != null) ( amount!!-(downPayment)) else amount)!! / (term!! * 12)
                totalPrice = 0.0
            }else{
                result = (if (downPayment != null) (amount!! -(downPayment)) else amount)!! * ((interest!! / (100)) / (12)) / (1 - Math.pow( 1 + ((interest / 100) / 12), -term!! *12))
                totalPrice = 12 * term * result - (if (downPayment != null ) amount?.minus(downPayment)!! else amount!!)
            }
            binding.monthlyPay.setText(String.format("%.2f",result), TextView.BufferType.EDITABLE)
            binding.interestTotalCost.setText(String.format("%.2f",totalPrice), TextView.BufferType.EDITABLE)

        }
    }




}