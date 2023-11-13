package com.minhtu.jokeesingleservingapp.viewmodel

import android.content.Context
import android.content.SharedPreferences
import androidx.activity.ComponentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.minhtu.jokeesingleservingapp.constant.Constant
import com.minhtu.jokeesingleservingapp.R
import com.minhtu.jokeesingleservingapp.model.FirebaseHelper
import com.minhtu.jokeesingleservingapp.model.JokeEntity

class JokeViewModel : ViewModel() {
    private lateinit var jokeList : ArrayList<JokeEntity>
    private val PREF_NAME = Constant.JOKE_PREFERENCE_NAME
    var currentJoke : String = Constant.DEFAULT_STRING
    var showLoading : MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    var showButtons : MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    var showJoke : MutableLiveData<String> = MutableLiveData<String>()
    var changeOrientation : MutableLiveData<Boolean> = MutableLiveData<Boolean>()

    fun onCreate(context: Context) {
        //If this is tablet, it can rotate screen.
        val isTablet = context.resources.getBoolean(R.bool.isTablet)
        if(isTablet) {
            changeOrientation.postValue(true)
        }

        jokeList = ArrayList<JokeEntity>()

        //Show loading while waiting app gets jokes from Firebase.
        showLoading.postValue(true)

        val databaseReference = FirebaseHelper.getFirebaseReference()
        //Get list of jokes from Firebase.
        getJokeListOnFirebase(context, databaseReference)
    }

    /**
     * This function will get list of jokes from Firebase and add to a list.
     * @param databaseReference: database reference of Firebase.
     */
    private fun getJokeListOnFirebase(context: Context, databaseReference: DatabaseReference){
        //Jokes will be stored as a key-value pair on Firebase.
        databaseReference.child(Constant.JOKES_NODE_ON_FIREBASE).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //Get number of jokes on Firebase.
                val size = snapshot.childrenCount
                //Add jokes to list.
                for(i in 1..size){
                    val jokeName = snapshot.child(Constant.JOKE + i).key.toString()
                    val jokeContent = snapshot.child(Constant.JOKE + i).value.toString()
                    val joke = JokeEntity(jokeName, jokeContent)
                    jokeList.add(joke)
                }
                //Show joke when get data successfully.
                showJoke(context)
            }

            override fun onCancelled(error: DatabaseError) {
                //If cannot get data from Firebase, add joke from resources.
                val jokeName = Constant.JOKE_CASE_ERROR
                val jokeContent = context.getString(R.string.joke_content)
                val joke = JokeEntity(jokeName, jokeContent)
                jokeList.add(joke)
                //Show default joke when get data unsuccessfully.
                showJoke(context)
            }
        })
    }

    /**
     * This function will find the next joke to show for user.
     */
    fun findJokeNeedToShow(context: Context) : String{
        var jokeContentWillBeShown = Constant.THATS_ALL_JOKE
        val sharedPreferences = getSharedPreference(context)
        for(i in 0 until jokeList.size){
            val joke = sharedPreferences.getString(jokeList[i].getContent(), Constant.DEFAULT_STRING).toString()
            //Check if joke is voted or not, only show joke if it is not voted. So that, user won't see it twice.
            if(joke == "" || joke == "null") {
                val jokeWillBeShown = jokeList[i]
                jokeContentWillBeShown = jokeWillBeShown.getContent()
                break
            }
        }
        return jokeContentWillBeShown
    }

    /**
     * This function will show joke for user. If there is no more joke, show announcement and hide button.
     */
    fun showJoke(context: Context) {
        currentJoke = findJokeNeedToShow(context)
        //Hide button when there is no more joke.
        if(currentJoke == Constant.THATS_ALL_JOKE) {
            showButtons.postValue(false)
        }
        //Show button when there is joke.
        else {
            showButtons.postValue(true)
        }
        //Change the text of TextView.
        showJoke.postValue(currentJoke)
        //Hide loading after joke is shown.
        showLoading.postValue(false)
    }

    /**
     * This function will save joke and its vote to local storage.
     */
    fun addVotedJokeToSharedPreference(context: Context, vote: String) {
        //Voted joke will be stored in this format: {"Joke1":"Funny", "Joke2": "Not Funny"}
        val sharedPreferences = getSharedPreference(context)
        val editor = sharedPreferences.edit()
        editor.putString(currentJoke, vote)
        editor.apply()
    }

    fun getSharedPreference(context: Context) : SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, ComponentActivity.MODE_PRIVATE)
    }
}