package com.mobiotics.demo.mobioticsapp.ui.videoplayer

import com.mobiotics.demo.mobioticsapp.BasePresenter
import com.mobiotics.demo.mobioticsapp.db.DatabaseHelper
import com.mobiotics.demo.mobioticsapp.models.RetroVideo

interface VideoPlayerContract {
    interface View {
        fun showProgressDialogView()
        fun hideProgressDialogView()
        fun generateDataList(photoList: List<RetroVideo>)
        fun displayToast(message: String)
        fun setUpadater(videoList: List<RetroVideo>)
        fun setDataExpoPlayer(retroVideo: RetroVideo)

    }

    interface Presenter : BasePresenter {
        fun getVideoList(db: DatabaseHelper)
        fun getVideo(db: DatabaseHelper, id: Int)


    }
}