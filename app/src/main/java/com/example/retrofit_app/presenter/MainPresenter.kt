package com.example.retrofit_app.presenter

import com.example.retrofit_app.model.CepInterface
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable

class MainPresenter(
    private var view: MainContract.View?,
    private val service: CepInterface,
    private val subscribeOnScheduler: Scheduler,
    private val observerOnScheduler: Scheduler,
) : MainContract.Presenter {
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
            .subscribeOn(subscribeOnScheduler)
            .observeOn(observerOnScheduler)
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
