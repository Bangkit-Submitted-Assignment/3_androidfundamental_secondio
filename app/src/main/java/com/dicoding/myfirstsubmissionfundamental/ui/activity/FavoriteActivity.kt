package com.dicoding.myfirstsubmissionfundamental.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.myfirstsubmissionfundamental.R
import com.dicoding.myfirstsubmissionfundamental.databinding.ActivityFavoriteBinding
import com.dicoding.myfirstsubmissionfundamental.ui.adapter.FavoriteAdapter
import com.dicoding.myfirstsubmissionfundamental.ui.viewmodel.FavViewModelFactory
import com.dicoding.myfirstsubmissionfundamental.ui.viewmodel.MainViewModel

class FavoriteActivity : AppCompatActivity() {

    private var _activityFavBinding: ActivityFavoriteBinding? = null
    private val binding get() = _activityFavBinding
    private lateinit var adapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        val actionBar = supportActionBar
        actionBar?.title = getString(R.string.favorite)

        _activityFavBinding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val favoriteVIewModel = obtainViewModel(this@FavoriteActivity)

        favoriteVIewModel.getAllNotes().observe(this){favorite ->
            adapter.setListNotes(favorite)
        }

        adapter = FavoriteAdapter()
        binding?.rvFav?.layoutManager = LinearLayoutManager(this)
        binding?.rvFav?.setHasFixedSize(true)
        binding?.rvFav?.adapter = adapter

        binding?.rvFav?.setOnClickListener {
            val intent = Intent(this@FavoriteActivity, DetailActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityFavBinding = null
    }

    private fun obtainViewModel(activity: AppCompatActivity): MainViewModel {
        val factory = FavViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(MainViewModel::class.java)
    }
}