package com.example.retrofit_app.presenter

import com.example.retrofit_app.model.CepInterface
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainPresenter(private var view: MainContract.View?, private val service: CepInterface) :
    MainContract.Presenter {
    private val compositeDisposable = CompositeDisposable()
    private fun isValidCEP(cep: String): Boolean {
        return cep.length >= 8
    }

    override fun getAddressOrShowError(cep: String) {
        if (isValidCEP(cep)) {
            getAddress(cep)
        } else {
            view?.showInvalidCEPError()
        }
    }

    override fun unbindView() {
        compositeDisposable.dispose() // Aqui estamos cancelando todas as chamadas de serviço iniciadas
        view = null // Aqui estamos liberando a referencia da view para prevenir memory leak
    }

    override fun getAddress(cep: String) {
        val serviceCall = service.getAddress(cep)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ address ->
                val enderecoString = "CEP: ${address.cep}\n" +
                        "Logradouro: ${address.logradouro}\n" +
                        "Bairro: ${address.bairro}\n" +
                        "Cidade: ${address.localidade}\n" +
                        "Estado: ${address.uf}"
                view?.showResult(enderecoString)
            }, { error ->
                view?.showError("Erro ao buscar CEP: ${error.message}")
            })
        compositeDisposable.add(serviceCall)
    }
}
