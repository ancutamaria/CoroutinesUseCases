package com.am.coroutinesusecases.usecases.coroutines.usecase15

import android.os.Bundle
import androidx.activity.viewModels
import com.am.coroutinesusecases.base.BaseActivity
import com.am.coroutinesusecases.base.useCase15Description
import com.am.coroutinesusecases.databinding.ActivityWorkmangerBinding

class WorkManagerActivity : BaseActivity() {

    override fun getToolbarTitle() = useCase15Description

    private val binding by lazy { ActivityWorkmangerBinding.inflate(layoutInflater) }
    private val viewModel: WorkManagerViewModel by viewModels {
        ViewModelFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel.performAnalyticsRequest()
    }
}