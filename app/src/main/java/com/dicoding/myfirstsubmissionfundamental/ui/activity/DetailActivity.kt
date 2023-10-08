package com.dicoding.myfirstsubmissionfundamental.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.dicoding.myfirstsubmissionfundamental.R
import com.dicoding.myfirstsubmissionfundamental.data.response.DetailUserResponse
import com.dicoding.myfirstsubmissionfundamental.database.FavoriteUser
import com.dicoding.myfirstsubmissionfundamental.databinding.ActivityDetailBinding
import com.dicoding.myfirstsubmissionfundamental.ui.adapter.SectionPagerAdapter
import com.dicoding.myfirstsubmissionfundamental.ui.insert.FavoriteAddUpdateViewModel
import com.dicoding.myfirstsubmissionfundamental.ui.viewmodel.DetailViewModel
import com.dicoding.myfirstsubmissionfundamental.ui.viewmodel.FavViewModelFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel by viewModels<DetailViewModel>()
    private lateinit var favAddUpdateViewModel: FavoriteAddUpdateViewModel

    private var login: String? = null

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following
        )
        const val LOGIN = "login"
    }

    private var username =""
    private var isEdit = false
    private var fav: FavoriteUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lateinit var buttonShare : Button

        buttonShare = findViewById(R.id.action_share)
        buttonShare.setOnClickListener{
            val myIntent = Intent(Intent.ACTION_SEND)
            myIntent.type = "type/palin"
            val shareSub = "Mau Kirim Kemana nich?"
            myIntent.putExtra(Intent.EXTRA_TEXT, shareSub)
            startActivity(Intent.createChooser(myIntent, "Share app"))
        }

        login = intent.getStringExtra(LOGIN)

        val actionBar = supportActionBar
        actionBar?.title = login.toString()

        val sectionsPagerAdapter = SectionPagerAdapter(this)
        val viewPager: ViewPager2 = findViewById(R.id.viewpager)
        viewPager.adapter = sectionsPagerAdapter
        sectionsPagerAdapter.username = login.toString()
        val tabs: TabLayout = findViewById(R.id.tabLayout)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f

        detailViewModel.getDetail(login.toString())

        detailViewModel.detailResponse.observe(this) { show ->
            showDataDetail(show)
            fav?.username = show.login.toString()
            fav?.avatarUrl = show.avatarUrl
        }

        detailViewModel.isLoading.observe(this) { isLoading ->
            showLoading(isLoading)
        }

        favAddUpdateViewModel = obtainViewModel(this@DetailActivity)


        fav = intent.getParcelableExtra(LOGIN)
        if (fav != null) {
            isEdit = true
        } else {
            fav = FavoriteUser()
        }

        favAddUpdateViewModel.getFavoriteUserByUsername(login.toString()).observe(this, Observer { user ->
            if(user != null){
                binding.addFavorite.setImageResource(R.drawable.ic_favorite)
                binding.addFavorite.setOnClickListener{
                    username = login.toString()
                    favAddUpdateViewModel.delete(fav as FavoriteUser)
                    showToast(getString(R.string.deleted, username))
                }
            }else{
                binding.addFavorite.setImageResource(R.drawable.ic_favorite_empty)
                binding.addFavorite.setOnClickListener {
                    username = login.toString()
                    favAddUpdateViewModel.insert(fav as FavoriteUser)
                    showToast(getString(R.string.added,username))
                }
            }
        })
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteAddUpdateViewModel {
        val factory = FavViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavoriteAddUpdateViewModel::class.java)
    }

    private fun showDataDetail(show: DetailUserResponse) {
        Glide.with(this)
            .load(show.avatarUrl)
            .into(binding.image)
        binding.nama.text = show.name
        binding.username.text = show.login
        binding.followers.text = "${show.followers} Followers"
        binding.following.text = "${show.following} Following"
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}