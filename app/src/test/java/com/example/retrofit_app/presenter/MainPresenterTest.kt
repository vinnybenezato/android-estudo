package com.example.retrofit_app.presenter
import com.example.retrofit_app.model.CepInterface
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import io.mockk.verify
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Test

class MainPresenterTest {

    private lateinit var presenter: MainPresenter
    private val mockView: MainContract.View = mockk(relaxed = true)
    private val mockService: CepInterface = mockk(relaxed = true)

    @Before
    fun setUp() {
        presenter = MainPresenter(mockView, mockService, Schedulers.trampoline(), Schedulers.trampoline())
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `test getAddressOrShowError with valid CEP`() {
        every { mockService.getAddress(any()) } returns Single.just(mockk(relaxed = true))
        presenter.getAddressOrShowError("12345678")
        verify { mockView.showResult(any()) }
    }

    @Test
    fun `test getAddressOrShowError with service error`() {
        val errorMessage = "Erro no servidor"
        every { mockService.getAddress(any()) } returns Single.error(Throwable(errorMessage))
        presenter.getAddressOrShowError("12345678")
        verify { mockView.showError("Erro ao buscar CEP: $errorMessage") }
    }

    @Test
    fun `test getAddressOrShowError with invalid CEP`() {
        presenter.getAddressOrShowError("1234")
        verify { mockView.showInvalidCEPError() }
    }
}
