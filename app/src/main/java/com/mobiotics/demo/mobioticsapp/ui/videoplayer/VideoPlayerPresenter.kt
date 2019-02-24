package com.mobiotics.demo.mobioticsapp.ui.videoplayer

import com.mobiotics.demo.mobioticsapp.db.DatabaseHelper
import com.mobiotics.demo.mobioticsapp.models.RetroVideo

class VideoPlayerPresenter(private var view: VideoPlayerContract.View) : VideoPlayerContract.Presenter {
    override fun getVideoList(db: DatabaseHelper) {
        val videos: ArrayList<RetroVideo> = db.getAllVideos()
        view.setUpadater(videos)
    }

    override fun getVideo(db: DatabaseHelper, id: Int) {
        val videos: RetroVideo = db.getVideo(id)
        view.setDataExpoPlayer(videos)
    }

    override fun start() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}