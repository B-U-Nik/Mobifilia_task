package com.example.mobifillatask.activities

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.room.Room
import com.example.mobifillatask.R
import com.example.mobifillatask.databinding.ActivityMainBinding
import com.example.mobifillatask.fragments.*
import com.example.mobifillatask.roomDb.UserDetailDatabase
import com.example.mobifillatask.utils.SessionManager
import com.example.navigationandbottom.fragments.*
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var fragmentManager: FragmentManager
    lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences
    lateinit var sessionManager: SessionManager
    lateinit var database: UserDetailDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sessionManager = SessionManager(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database =
            Room.databaseBuilder(applicationContext, UserDetailDatabase::class.java, "userDetailDb")
                .build()

        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.toolbar,
            R.string.nav_open,
            R.string.nav_close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        binding.navigationDrawer.setNavigationItemSelectedListener(this)

        binding.bottomNavigation.background = null
        binding.bottomNavigation.setOnItemSelectedListener { item ->

            when (item.itemId) {

                R.id.bottomHome -> {

                    openFragment(HomeFragment())
                }

                R.id.bottomEntry -> {

                    openFragment(EntryFragment())
                }

                R.id.bottomScan -> {
                    openFragment(ScannerFragment())
                }

                R.id.bottomExit -> {
                    openFragment(VisitorsExitFragment())
                }

            }
            true
        }
        fragmentManager = supportFragmentManager

        openFragment(HomeFragment())

        database.userDetailDao.getUSer().observe(this) { Users ->
            val allNames = StringBuilder()
            for (user in Users) {
                allNames.append("${user.name} \n ${user.password}")


                binding.navigationDrawer.getHeaderView(0)
                    ?.findViewById<TextView>(R.id.tvNvmUserName)?.text = allNames.toString()

            }
        }

    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {


            R.id.navHome -> {

                openFragment(HomeFragment())
            }

            R.id.navEntry -> {

                openFragment(EntryFragment())
            }

            R.id.navScan -> {

                openFragment(ScannerFragment())
            }

            R.id.navVisitorsExit -> {

                openFragment(VisitorsExitFragment())
            }


            R.id.navPrivacyPolicy -> {

                openPrivacyPolicy()

            }

            R.id.navLogOut -> showLogoutConfirmationDialog()
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun showLogoutConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Logout Confirmation")
            .setMessage("Are you sure you want to logout?")
            .setPositiveButton("Yes") { dialog: DialogInterface, _: Int ->
                performLogout()
                dialog.dismiss()
            }
            .setNegativeButton("No") { dialog: DialogInterface, _: Int ->
                dialog.dismiss()

            }
            .setCancelable(false)
            .show()
    }

    private fun performLogout() {
        Toast.makeText(this, "Log Out Completed", Toast.LENGTH_SHORT).show()

        sharedPreferences = getSharedPreferences("LOGIN", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()

        deleteAllUsersFromDatabase()

        val intent = Intent(this, LoginScreen::class.java)
        startActivity(intent)
        finish()
    }

    private fun deleteAllUsersFromDatabase() {
        // Perform deletion on a background thread
        Thread {
            database.userDetailDao.deleteAllUsers()
        }.start()
    }

    override fun onBackPressed() {

        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)

        } else {
            super.onBackPressed()
        }
    }

    private fun openFragment(fragment: Fragment) {


        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.addToBackStack(null) // Add the transaction to the back stack
        fragmentTransaction.commit()
    }

    private fun openPrivacyPolicy() {
        val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.mobifilia.com/"))
        startActivity(webIntent)
    }

}