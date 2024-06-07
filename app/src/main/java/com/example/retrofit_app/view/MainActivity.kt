package com.example.retrofit_app.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.retrofit_app.databinding.ActivityMainBinding
import com.example.retrofit_app.model.CepInterface
import com.example.retrofit_app.model.NetworkConfig.retrofit
import com.example.retrofit_app.presenter.MainContract
import com.example.retrofit_app.presenter.MainPresenter
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers


class MainActivity : AppCompatActivity(), MainContract.View {

    private lateinit var binding: ActivityMainBinding
    private val service = retrofit.create(CepInterface::class.java)
    private val presenter: MainContract.Presenter =
        MainPresenter(this, service, Schedulers.io(), AndroidSchedulers.mainThread())

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

    @SuppressLint("SetTextI18n")
    override fun showInvalidCEPError() {
        binding.resultTextView.text = "CEP inválido, digite um CEP válido, por favor"
    }

    override fun onDestroy() {
        presenter.unbindView()
        super.onDestroy()
    }
}


