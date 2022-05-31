package com.guillermocode.testpractico

import android.content.ClipData
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Button
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.guillermocode.testpractico.databinding.ActivityArticlesBinding

class ArticlesActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityArticlesBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityArticlesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarArticles.toolbar)

        binding.appBarArticles.fab.setOnClickListener { view ->
            //Snackbar.make(view, "Contactar con el Administrador", Snackbar.LENGTH_LONG)
                //.setAction("Action", null).show()
            var q = android.os.Process.myPid();
            android.os.Process.killProcess(q);
            this.finish(); System.exit(0);

        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_articles)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.articulos, R.id.perfil, R.id.mis_productos, R.id.configuraciones
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.articles, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_articles)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}