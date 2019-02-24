package com.mobiotics.demo.mobioticsapp.ui.videolist

import com.mobiotics.demo.mobioticsapp.models.RetroVideo
import com.mobiotics.demo.mobioticsapp.retrofit.GetDataService
import com.mobiotics.demo.mobioticsapp.retrofit.RetrofitClientInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VideoListPresenter(private var view: VideoListContract.View) : VideoListContract.Presenter {
    override fun getVideoList() {
        /*Create handle for the RetrofitInstance interface*/
        view.showProgressDialogView()
        val service = RetrofitClientInstance().getRetrofitInstance()!!.create(GetDataService::class.java)
        val call = service.allPhotos
        call.enqueue(object : Callback<List<RetroVideo>> {
            override fun onResponse(call: Call<List<RetroVideo>>, response: Response<List<RetroVideo>>) {
                view.hideProgressDialogView()
                view?.generateDataList(response.body()!!)
            }

            override fun onFailure(call: Call<List<RetroVideo>>, t: Throwable) {
                view.hideProgressDialogView()
                view.displayToast("Something went wrong...Please try later!")

            }
        })
    }

    override fun start() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}