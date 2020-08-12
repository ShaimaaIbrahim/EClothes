package com.example.eclothe

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.ui.setupWithNavController

import com.example.eclothe.databinding.ActivityMainBinding
import com.squareup.picasso.Picasso
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    data class MenuItem(
        val label: String,
        val image: String,
        val destinationId: Int
    )

    private val menuItems = listOf(
        MenuItem(
            label = "Home",
            image = "https://i.ibb.co/B4nR76t/home-24px.png",
            destinationId = R.id.home_dest
        ),
        MenuItem(
            label = "Category",
            image = "https://i.ibb.co/6gG1M71/message-24px.png",
            destinationId = R.id.category_dest
        ),

        MenuItem(
            label = "Cart",
            image = "https://i.ibb.co/FHP56wG/perm-identity-24px.png",
            destinationId = R.id.cart_dest
        ),
                MenuItem(
                label = "Favorites",
        image = "https://i.ibb.co/6gG1M71/message-24px.png",
        destinationId = R.id.favorite_dest
    ),
    MenuItem(
    label = "Profile",
    image = "https://i.ibb.co/FHP56wG/perm-identity-24px.png",
    destinationId = R.id.profile_dest
    )
    )

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

      binding = DataBindingUtil.setContentView(this , R.layout.activity_main)


        data class Tuple(val menuItem: MenuItem, val bitmap: Bitmap)

        val picasso = Picasso.get()

        menuItems.forEachIndexed { index, menuItem ->
            binding.navView.menu.add(Menu.NONE, menuItem.destinationId, index, menuItem.label)
        }

      //  subscriptions.add(
            Observable.fromIterable(menuItems)
                .switchMap {
                    Observable.just(
                        Tuple(it, picasso.load(it.image).get())
                    )
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        val menuItem = binding.navView.menu.findItem(it.menuItem.destinationId)
                        menuItem.icon = BitmapDrawable(resources, it.bitmap)
                    },
                    {
                        // Handle errors here
                    },
                    {
                        // On complete we should setup nav controller
                        val navController = findNavController(R.id.nav_host_fragment)

                        binding.navView.setupWithNavController(navController)
                    }
                )
     //   )
    }
}













