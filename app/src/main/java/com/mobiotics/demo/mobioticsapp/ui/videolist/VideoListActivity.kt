package com.mobiotics.demo.mobioticsapp.ui.videolist

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.mobiotics.demo.mobioticsapp.R
import com.mobiotics.demo.mobioticsapp.db.DatabaseHelper
import com.mobiotics.demo.mobioticsapp.models.RetroVideo
import com.mobiotics.demo.mobioticsapp.ui.videoplayer.VideoPlayerActivity


class VideoListActivity : AppCompatActivity(), VideoListContract.View {


    // private lateinit var googleSignInClient: GoogleSignInClient
    lateinit var presenter: VideoListPresenter
    private var adapter: CustomAdapter? = null
    private var recyclerView: RecyclerView? = null
    lateinit var progressDoalog: ProgressDialog

    private var db: DatabaseHelper? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_list)
        presenter = VideoListPresenter(this)
        progressDoalog = ProgressDialog(this@VideoListActivity)
        progressDoalog.setMessage("Loading....")
        db = DatabaseHelper(this);
        presenter.getVideoList()

    }

    /*Method to generate List of data using RecyclerView with custom adapter*/
    override fun generateDataList(videoList: List<RetroVideo>) {
        recyclerView = findViewById(R.id.customRecyclerView)
        adapter = CustomAdapter(this, videoList)
        val layoutManager = LinearLayoutManager(this@VideoListActivity)
        recyclerView!!.layoutManager = layoutManager
        recyclerView!!.adapter = adapter
        adapter!!.itemOnClick = { view, video ->
            playVideo(video)
        }
        videoList.forEach {
            db?.insertVideos(it, false)
        }
    }

    override fun showProgressDialogView() {
        progressDoalog.show()
    }

    override fun hideProgressDialogView() {
        progressDoalog.dismiss()
    }

    override fun displayToast(message: String) {
        Toast.makeText(this@VideoListActivity, message, Toast.LENGTH_SHORT).show()
    }

    private fun playVideo(video: RetroVideo) {
        val intent = Intent(this, VideoPlayerActivity::class.java)
        intent.putExtra("video_to_play", video)
        startActivity(intent)
    }
}
