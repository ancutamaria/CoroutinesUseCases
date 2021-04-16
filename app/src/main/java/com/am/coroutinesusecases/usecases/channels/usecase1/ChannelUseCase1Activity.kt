package com.am.coroutinesusecases.usecases.channels.usecase1

import android.os.Bundle
import com.am.coroutinesusecases.base.BaseActivity
import com.am.coroutinesusecases.databinding.ActivityChannelsUsecase1Binding

class ChannelUseCase1Activity : BaseActivity() {

    private val binding by lazy { ActivityChannelsUsecase1Binding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    override fun getToolbarTitle() = "Channel Use Case 1"
}