package com.kasparpeterson.minibux.details

import com.kasparpeterson.minibux.api.BuxWebClient
import com.kasparpeterson.minibux.api.TradingQuote
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.junit.Test

import org.junit.Before
import java.math.BigDecimal

/**
 * Created by kaspar on 13/06/2017.
 */
class DetailsModelTest {

    lateinit var client: BuxWebClient
    lateinit var presenter: DetailsMVP.PresenterModelOperations
    lateinit var model: DetailsModel

    @Before
    fun setUp() {
        client = mock<BuxWebClient>()
        presenter = mock<DetailsMVP.PresenterModelOperations>()
        model = DetailsModel(client)
        model.presenter = presenter
    }

    @Test
    fun startListening() {

    }

    @Test
    fun onUpdate() {
        model.onUpdate(TradingQuote("mockId123", BigDecimal.ZERO))
        verify(presenter).onTradingQuoteUpdate(TradingQuote("mockId123", BigDecimal.ZERO))
    }

}