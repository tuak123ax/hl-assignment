package com.minhtu.jokeesingleservingapp.view

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider
import com.minhtu.jokeesingleservingapp.constant.Constant
import com.minhtu.jokeesingleservingapp.databinding.ActivityMainBinding
import com.minhtu.jokeesingleservingapp.viewmodel.JokeViewModel

class MainActivity : ComponentActivity() {
    var binding : ActivityMainBinding? = null
    private lateinit var jokeViewModel : JokeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        //Set up ViewModel.
        jokeViewModel = ViewModelProvider(this)[JokeViewModel::class.java]
        setUpObserverForViewModel()
        jokeViewModel.onCreate(applicationContext)

        //Set up event for button.
        setUpEvent()
    }

    private fun setUpObserverForViewModel() {
        jokeViewModel.showLoading.observe(this){
            if(it) {
                showLoading()
            } else {
                hideLoading()
            }
        }
        jokeViewModel.showButtons.observe(this){
            if(it) {
                showButtons()
            } else {
                hideButtons()
            }
        }
        jokeViewModel.showJoke.observe(this){
            binding!!.jokeContent.text = it.toString()
        }
        jokeViewModel.changeOrientation.observe(this){
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR
        }
    }

    private fun showLoading() {
        binding!!.loadingView.visibility = View.VISIBLE
        binding!!.mainView.visibility = View.GONE
    }

    private fun hideLoading() {
        binding!!.loadingView.visibility = View.GONE
        binding!!.mainView.visibility = View.VISIBLE
    }

    private fun showButtons() {
        binding!!.funnyButton.visibility = View.VISIBLE
        binding!!.notFunnyButton.visibility = View.VISIBLE
    }

    private fun hideButtons() {
        binding!!.funnyButton.visibility = View.GONE
        binding!!.notFunnyButton.visibility = View.GONE
    }

    private fun setUpEvent() {
        binding!!.funnyButton.setOnClickListener {
            //When clicking button, add voted joke to local storage with vote value.
            jokeViewModel.addVotedJokeToSharedPreference(applicationContext , Constant.FUNNY_VOTE)
            //Show joke
            jokeViewModel.showJoke(applicationContext)
        }

        binding!!.notFunnyButton.setOnClickListener {
            //When clicking button, add voted joke to local storage with vote value.
            jokeViewModel.addVotedJokeToSharedPreference(applicationContext,
                Constant.NOT_FUNNY_VOTE
            )
            //Show joke
            jokeViewModel.showJoke(applicationContext)
        }
    }
}