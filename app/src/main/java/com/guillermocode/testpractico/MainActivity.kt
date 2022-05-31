package com.guillermocode.testpractico


import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Button

import com.facebook.*
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount

import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task

import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import org.json.JSONException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {

    lateinit var mGoogleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN = 9001
    private lateinit var callbackManager: CallbackManager
    private lateinit var loginButton: LoginButton
    private lateinit var entrar: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        var google_login_btn = findViewById(R.id.google_login_btn) as Button
        entrar = findViewById(R.id.button)
        entrar.setOnClickListener {
            val intent = Intent(applicationContext, ArticlesActivity::class.java)
            startActivity(intent)
        }
        //printHashKey()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("91905659121-r370hmuc5tdo3ohc96acbh5hj40eotj4.apps.googleusercontent.com")
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        google_login_btn.setOnClickListener {
            signIn()
        }

        //init
        loginButton = findViewById(R.id.login_button)

        callbackManager = CallbackManager.Factory.create()
        loginButton.setPermissions(listOf("email", "user_birthday"))

        //login callback
        loginButton.registerCallback(callbackManager, object: FacebookCallback<LoginResult> {

            override fun onSuccess(result: LoginResult) {
                val userId = result.accessToken.userId
                Log.d(TAG, "onSuccess: userId $userId")

                val bundle = Bundle()
                bundle.putString("fields", "id, email, first_name, last_name, gender,age_range")
                //Graph API to access the data of user's facebook account
                val request = GraphRequest.newMeRequest(
                    result.accessToken
                ) { fbObject, response ->
                    Log.v("Login Success", response.toString())
                    //For safety measure enclose the request with try and catch
                    try {
                        Log.d(TAG, "onSuccess: fbObject $fbObject")
                        val firstName = fbObject?.getString("first_name")
                        val lastName = fbObject?.getString("last_name")
//                        val gender = fbObject?.getString("gender")
                        val email = fbObject?.getString("email")
                        Log.d(TAG, "onSuccess: firstName $firstName")
                        Log.d(TAG, "onSuccess: lastName $lastName")
//                        Log.d(TAG, "onSuccess: gender $gender")
                        Log.d(TAG, "onSuccess: email $email")

                    } //If no data has been retrieve throw some error
                    catch (e: JSONException) {
                    }
                }
                //Set the bundle's data as Graph's object data
                request.parameters(bundle)

                //Execute this Graph request asynchronously
                request.executeAsync()
            }
            override fun onCancel() {
                    Log.d(TAG, "onCancel: called")
                }
            override fun onError(error: FacebookException) {
                    Log.d(TAG, "onError: called")
                }
        })
    }

    //fue necesario para crear el app dentro del panel de desarrollador de facebook
    fun printHashKey(){
        try {
            val info = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT))
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(
            signInIntent, RC_SIGN_IN
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task =
                GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task);

            val intent = Intent(applicationContext, ArticlesActivity::class.java)
            startActivity(intent)

        }else{
            Log.i("face", requestCode.toString());
            val intent = Intent(applicationContext, ArticlesActivity::class.java)
            startActivity(intent)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(
                ApiException::class.java
            )
            // Signed in successfully
            val googleId = account?.id ?: ""
            Log.i("Google ID",googleId)

            val googleFirstName = account?.givenName ?: ""
            Log.i("Google First Name", googleFirstName)

            val googleLastName = account?.familyName ?: ""
            Log.i("Google Last Name", googleLastName)

            val googleEmail = account?.email ?: ""
            Log.i("Google Email", googleEmail)

            val googleProfilePicURL = account?.photoUrl.toString()
            Log.i("Google Profile Pic URL", googleProfilePicURL)

            val googleIdToken = account?.idToken ?: ""
            Log.i("Google ID Token", googleIdToken)

        } catch (e: ApiException) {
            // Sign in was unsuccessful
            Log.e(
                "failed code=", e.statusCode.toString()
            )
        }
    }

    //incorporar en barra lateral
    private fun signOut() {
        mGoogleSignInClient.signOut()
            .addOnCompleteListener(this) {
                 //Update your UI here
            }
    }
    private fun GraphRequest.parameters(bundle: Bundle) {

    }


}