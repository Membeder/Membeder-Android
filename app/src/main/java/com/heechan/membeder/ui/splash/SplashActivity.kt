package com.heechan.membeder.ui.splash

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.heechan.membeder.BuildConfig
import com.heechan.membeder.R
import com.heechan.membeder.base.BaseActivity
import com.heechan.membeder.databinding.ActivitySplashBinding
import com.heechan.membeder.model.data.auth.GoogleLoginRequest
import com.heechan.membeder.model.service.AuthService
import com.heechan.membeder.ui.login.LoginActivity
import com.heechan.membeder.ui.main.MainActivity
import com.heechan.membeder.ui.signUp.SignUpActivity
import com.heechan.membeder.ui.view.snack.BadSnackBar
import com.heechan.membeder.util.ExtraKey
import com.heechan.membeder.util.State.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SplashActivity : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {
    private val viewModel: SplashViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return SplashViewModel(application) as T
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.btnSplashStart.setOnClickListener(gotoRegister)
        binding.txtSplashGotoLogin.setOnClickListener(gotoLogin)
        binding.btnSplashGoogleLogin.setOnClickListener(googleLogin)

        viewModel.state.observe(this) {
            when (it) {
                SUCCESS -> {
                    val intent = Intent(this, MainActivity::class.java).apply {
                        putExtra(ExtraKey.USER_DATA.key, viewModel.userDate.value)
                    }
                    startActivity(intent)
                    finish()
                }
                LOADING -> {}
                FAIL -> {
                    BadSnackBar.make(
                        view = binding.root,
                        title = "계정 정보 가져오기 실패",
                        message = "계정 정보를 가죠오는데 실패했어요.\n다시 로그인 해주세요."
                    ).show()
                }
            }
        }

        viewModel.accessToken.observe(this) {
            Log.d("accessToken", it)
            if (it.isNotBlank()) {
                viewModel.getUserData()
            }
        }
    }

    private val googleLogin: (View) -> Unit = {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(BuildConfig.OAUTH_GOOGLE_ID)
            .requestEmail()
            .build()

        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        val intent = mGoogleSignInClient.signInIntent
        startActivityForResult(intent, GOOGLE_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            GOOGLE_SIGN_IN -> {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)

                val account = task.getResult(ApiException::class.java)
                val token = account.idToken

                val authService = getRetrofitService()

                Log.d("googleLogin", token!!)
                CoroutineScope(Dispatchers.IO).launch {
                    val googleCallback = authService.googleCallBack(
                        GoogleLoginRequest(idToken = token!!)
                    )

                    Log.d("googleLogin", googleCallback.body().toString())
                }

            }
        }
    }

    private fun getRetrofitService(): AuthService {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }
        val clientBuilder = OkHttpClient.Builder().apply {
            addInterceptor(loggingInterceptor)
        }

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.API_BASE_URL)
            .client(clientBuilder.build())
            .build()

        return retrofit.create(AuthService::class.java)
    }

    private val gotoRegister: (View) -> Unit = {
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
        finish()
    }

    private val gotoLogin: (View) -> Unit = {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    companion object {
        const val GOOGLE_SIGN_IN = 312
    }
}