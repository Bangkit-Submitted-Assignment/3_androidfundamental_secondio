package com.dicoding.myfirstsubmissionfundamental.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.myfirstsubmissionfundamental.R
import com.dicoding.myfirstsubmissionfundamental.data.SettingPreferences
import com.dicoding.myfirstsubmissionfundamental.data.dataStore
import com.dicoding.myfirstsubmissionfundamental.data.response.GithubResponse
import com.dicoding.myfirstsubmissionfundamental.data.response.ItemsItem
import com.dicoding.myfirstsubmissionfundamental.data.retrofit.ApiConfig
import com.dicoding.myfirstsubmissionfundamental.databinding.ActivityMainBinding
import com.dicoding.myfirstsubmissionfundamental.ui.adapter.UserAdapter
import com.dicoding.myfirstsubmissionfundamental.ui.viewmodel.ThemeViewModel
import com.dicoding.myfirstsubmissionfundamental.ui.viewmodel.ViewModelFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var progressBar: ProgressBar

    companion object {
        const val TAG = "MainActivity"
        const val USER_ID = "hehe"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        progressBar = binding.progressBar
        setContentView(binding.root)

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener{textView, actionId, event ->
                    searchBar.text = searchView.text
                    searchView.hide()
                    showUserSearch(searchBar.text.toString())
                    false
                }
        }

        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUser.addItemDecoration(itemDecoration)

        showUser()

        val pref = SettingPreferences.getInstance(application.dataStore)
        val mainViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(ThemeViewModel::class.java)

        mainViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    private fun showUser() {
        showLoading(true)
        val client = ApiConfig.getApiService().getListUser(USER_ID)
        client.enqueue(object : Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
//                        setRestaurantData(responseBody.restaurant)
                        setReviewData(responseBody.items)
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun showUserSearch(username: String) {
        showLoading(true)
        val client = ApiConfig.getApiService().getListUser(username)
        client.enqueue(object : Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
//                        setRestaurantData(responseBody.restaurant)
                        setReviewData(responseBody.items)
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun setReviewData(consumerReviews: List<ItemsItem?>?) {
        val adapter = UserAdapter(this)
        adapter.submitList(consumerReviews)
        binding.rvUser.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_list ->{
                val intent = Intent(this@MainActivity, ThemeActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.favUser -> {
                val intent = Intent(this@MainActivity, FavoriteActivity::class.java) // Replace FavoriteUserActivity with the actual name of your activity
                startActivity(intent)
                return true
            }else->{
            return super.onOptionsItemSelected(item)
        }
        }
    }

    fun getLoadingProgress():ProgressBar{
        return progressBar
    }

    override fun onResume() {
        super.onResume()
        showLoading(false)
    }
}