package com.mobiotics.demo.mobioticsapp.ui.videoplayer

import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.SimpleExoPlayerView
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.mobiotics.demo.mobioticsapp.R
import com.mobiotics.demo.mobioticsapp.db.DatabaseHelper
import com.mobiotics.demo.mobioticsapp.models.RetroVideo
import kotlinx.android.synthetic.main.activity_video_player.*


class VideoPlayerActivity : AppCompatActivity(), VideoPlayerContract.View {
    var exoPlayerView: SimpleExoPlayerView? = null
    var exoPlayer: SimpleExoPlayer? = null
    private var db: DatabaseHelper? = null
    lateinit private var presenter: VideoPlayerPresenter
    private var adapter: RelatedListAdapter? = null
    private var recyclerView: RecyclerView? = null
    private var playerPosition: Long = 0
    lateinit var retroVideo: RetroVideo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_player)
        presenter = VideoPlayerPresenter(this)
        exoPlayerView = findViewById(R.id.exo_player_view) as SimpleExoPlayerView
        val args = intent.extras
        retroVideo = args.get("video_to_play") as RetroVideo

        if (retroVideo.resumeFrom != null) {
            playerPosition = retroVideo.resumeFrom.toLong()
        }
        retroVideo.let {
            titleTxt.text = retroVideo.title
            descriptionTxt.text = retroVideo.description
        }
        db = DatabaseHelper(this)
        presenter.getVideoList(db!!)
        playButton.setOnClickListener({
            playButton.visibility = View.GONE
            setDataExpoPlayer(retroVideo)
            //presenter.getVideo(db!!, retroVideo.id!!)
        })
    }

    private fun getUpdatedVideo(retroVideo: RetroVideo) {
        presenter.getVideo(db!!, retroVideo.id!!)
    }

    override fun setUpadater(videoList: List<RetroVideo>) {
        videoList.forEach {
            if (it.id!!.equals(retroVideo.id)) {
                retroVideo = it
            }
        }
        recyclerView = findViewById(R.id.relatedRecyclerView)
        adapter = RelatedListAdapter(this, videoList)
        val layoutManager = LinearLayoutManager(this@VideoPlayerActivity)
        recyclerView!!.layoutManager = layoutManager
        recyclerView!!.adapter = adapter
        adapter!!.itemOnClick = { view, video ->
            retroVideo = video

            retroVideo.let {
                titleTxt.text = retroVideo.title
                descriptionTxt.text = retroVideo.description
            }
            exoPlayer?.release()
            playButton.visibility = View.VISIBLE
        }
        videoList.forEach {
            if (it.id!!.equals(retroVideo.id)) {
                retroVideo = it
            }
        }
    }

    override fun setDataExpoPlayer(retroVideo: RetroVideo) {
        try {
            // if (retroVideo.resumeFrom != null) {
            playerPosition = retroVideo.resumeFrom.toLong()


            val bandwidthMeter = DefaultBandwidthMeter()
            val trackSelector = DefaultTrackSelector(AdaptiveTrackSelection.Factory(bandwidthMeter))
            exoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector)

            val videoURI = Uri.parse(retroVideo.url)
            val dataSourceFactory = DefaultHttpDataSourceFactory("exoplayer_video")
            val extractorsFactory = DefaultExtractorsFactory()
            val mediaSource = ExtractorMediaSource(videoURI, dataSourceFactory, extractorsFactory, null, null)

            exoPlayerView?.player = exoPlayer
            exoPlayer?.prepare(mediaSource)
            exoPlayer?.seekTo(playerPosition)
            exoPlayer?.playWhenReady = true
        } catch (e: Exception) {
            Log.e("MainAcvtivity", " exoplayer error " + e.toString())
        }

        retroVideo.let {
            titleTxt.text = retroVideo.title
            descriptionTxt.text = retroVideo.description
        }
    }


    override fun showProgressDialogView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideProgressDialogView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun generateDataList(photoList: List<RetroVideo>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun displayToast(message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
        if (exoPlayer != null) {
            playerPosition = exoPlayer?.getCurrentPosition()!!
            exoPlayer?.release()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        retroVideo.status = "1"
        retroVideo.resumeFrom = "" + playerPosition
        db?.insertVideos(retroVideo, true)
        if (exoPlayer != null)
            exoPlayer?.release()
    }
}

