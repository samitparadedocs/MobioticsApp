package com.mobiotics.demo.mobioticsapp.ui.videoplayer

import android.util.Log
import com.mobiotics.demo.mobioticsapp.db.DatabaseHelper
import com.mobiotics.demo.mobioticsapp.models.RetroVideo

class VideoPlayerPresenter(private var view: VideoPlayerContract.View) : VideoPlayerContract.Presenter {
    override fun getVideoList(db: DatabaseHelper) {
        var videos: ArrayList<RetroVideo> = db.getAllVideos()!!
        videos.forEach {
            Log.e("MainAcvtivity", " exoplayer error " + it.title)
        }
        view.setUpadater(videos)
    }

    override fun getVideo(db: DatabaseHelper, id: Int) {
        var videos: RetroVideo= db.getVideo(id)
        view.setDataExpoPlayer(videos)
    }


    override fun start() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}