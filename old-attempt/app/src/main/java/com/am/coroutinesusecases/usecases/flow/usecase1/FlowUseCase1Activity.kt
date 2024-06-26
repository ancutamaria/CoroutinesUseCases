package com.am.coroutinesusecases.usecases.flow.usecase1

import android.os.Bundle
import com.am.coroutinesusecases.base.BaseActivity
import com.am.coroutinesusecases.databinding.ActivityFlowUsecase1Binding

class FlowUseCase1Activity : BaseActivity() {

    private val binding by lazy { ActivityFlowUsecase1Binding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    override fun getToolbarTitle() = "Flow Use Case 1 Activity"

}