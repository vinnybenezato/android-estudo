package com.example.retrofit_app.presenter

import android.annotation.SuppressLint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import com.example.retrofit_app.model.Endereco
import com.example.retrofit_app.model.CepInterface
import com.example.retrofit_app.view.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainPresenter(private val view: MainActivity, private val service: CepInterface) :
    MainContract.Presenter {

    private fun isValidCEP(cep: String): Boolean {
        return cep.length >= 8
    }

    override fun getAddressOrShowError(cep: String) {
        if (isValidCEP(cep)) {
            getAddress(cep)
        } else {
            view.showInvalidCEPError()
        }
    }

    @SuppressLint("CheckResult")
    override fun getAddress(cep: String) {
        service.getAddress(cep)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ address ->
                val enderecoString = "CEP: ${address.cep}\n" +
                        "Logradouro: ${address.logradouro}\n" +
                        "Bairro: ${address.bairro}\n" +
                        "Cidade: ${address.localidade}\n" +
                        "Estado: ${address.uf}"
                view.showResult(enderecoString)
            }, { error ->
                view.showError("Erro ao buscar CEP: ${error.message}")
            })
    }
}
