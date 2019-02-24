package com.mobiotics.demo.mobioticsapp.ui.videolist

import com.mobiotics.demo.mobioticsapp.BasePresenter
import com.mobiotics.demo.mobioticsapp.models.RetroVideo

interface VideoListContract {
    interface View {
        fun showProgressDialogView()
        fun hideProgressDialogView()
        fun generateDataList(photoList: List<RetroVideo>)
        fun displayToast(message:String)

    }

    interface Presenter : BasePresenter {
        fun getVideoList()

    }
}