package com.example.taskhub.TaskTrek.activities
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toolbar
import androidx.core.view.GravityCompat
import com.bumptech.glide.Glide
import com.example.taskhub.R
import com.example.taskhub.TaskTrek.Firebase.FirestoreClass
import com.example.taskhub.TaskTrek.models.BaseActivity
import com.example.taskhub.TaskTrek.models.Users
import com.example.taskhub.databinding.ActivityMainBinding
import com.example.taskhub.databinding.AppBarMainBinding
import com.example.taskhub.databinding.MainContentBinding
import com.example.taskhub.databinding.NavHeaderMainBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth


class MainActivity : BaseActivity(),NavigationView.OnNavigationItemSelectedListener{


    private lateinit var binding: ActivityMainBinding
    lateinit var appBarBinding: AppBarMainBinding
    private lateinit var contentBinding: MainContentBinding
    lateinit var navHeaderBinding: NavHeaderMainBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val rootView = binding.root
        setContentView(rootView)

//        binding.navView1.inflateMenu(R.menu.activity_main_drawer)
        appBarBinding = AppBarMainBinding.inflate(layoutInflater)
        navHeaderBinding=NavHeaderMainBinding.inflate(layoutInflater)
        contentBinding = MainContentBinding.inflate(layoutInflater)




        setUpActionBar()

        binding.navView1.setNavigationItemSelectedListener(this)


        FirestoreClass().loadUserData(this)




    }

    private fun setUpActionBar() {

        setSupportActionBar(appBarBinding.toolbarMainActivity)
        appBarBinding.toolbarMainActivity.setNavigationIcon(R.drawable.ic_action_navigation_menu)

        appBarBinding.toolbarMainActivity.title = "Task Trek"
        appBarBinding.toolbarMainActivity.setNavigationOnClickListener {
            toggleDrawer()
        }
    }

    private fun toggleDrawer() {

        if (binding.drawerLayout1.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout1.closeDrawer(GravityCompat.START)
        } else {
            binding.drawerLayout1.openDrawer(GravityCompat.START)
        }
    }


    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        if (binding.drawerLayout1.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout1.closeDrawer(GravityCompat.START)
        } else {
            doubleBackToExit()
        }
    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_my_profile -> {
                startActivity(Intent(this, MyProfileActivity::class.java))
            }
            R.id.nav_sign_out -> {
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this, IntroActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
            }
        }

        binding.drawerLayout1.closeDrawer(GravityCompat.START)
        return true
    }

    fun updateNavigationUserDetails(user: Users) {


        Glide.with(this)
            .load(user)
            .centerCrop()
            .placeholder(R.drawable.ic_user_image_holder_foreground)
            .into(navHeaderBinding.navUserImage)

        navHeaderBinding.tvUsername.text = user.name
    }
}
