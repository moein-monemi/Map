package com.example.googlemap.base

import android.app.Activity
import android.app.AlertDialog
import android.app.AlertDialog.Builder
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

open class BaseDialog<T: ViewDataBinding>(private val activity: Activity, private val layout: Int): AlertDialog.Builder(activity) {

    private lateinit var alertDialog: AlertDialog
    private lateinit var binding: T
    fun buildDialog() {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(
                context
            ), layout, null, false
        )
        alertDialog = create()
        alertDialog.setView(binding.root)
    }



    fun getBinding (): T {
        return binding
    }

    fun showDialog (){
        alertDialog.show()
    }
}