package com.example.retrofit_app.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.retrofit_app.model.CepInterface
import com.example.retrofit_app.databinding.ActivityMainBinding
import com.example.retrofit_app.model.Endereco
import com.example.retrofit_app.model.NetworkConfig.retrofit
import com.example.retrofit_app.presenter.MainContract
import com.example.retrofit_app.presenter.MainPresenter
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager


class MainActivity : AppCompatActivity(), MainContract.View {

    private lateinit var binding: ActivityMainBinding
    private val service = retrofit.create(CepInterface::class.java)
    private val presenter: MainContract.Presenter = MainPresenter(this, service)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.searchButton.setOnClickListener {
            val cep = binding.cepEditText.text.toString()
            presenter.getAddressOrShowError(cep)
        }
    }

    override fun showResult(endereco: String) {
        binding.resultTextView.text = endereco
    }

    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun showInvalidCEPError() {
        binding.resultTextView.text = "CEP inválido, digite um CEP válido, por favor"
    }
}


