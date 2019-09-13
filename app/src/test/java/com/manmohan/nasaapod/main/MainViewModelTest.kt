package com.manmohan.nasaapod.main

import android.net.ConnectivityManager
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import com.jraska.livedata.TestObserver
import com.manmohan.nasaapod.data.AppDataManager
import com.manmohan.nasaapod.data.api.ApiClient
import com.manmohan.nasaapod.data.api.RxSingleSchedulers
import com.manmohan.nasaapod.data.api.model.Apod
import com.manmohan.nasaapod.data.database.ApodDatabase
import com.manmohan.nasaapod.data.database.AppDatabaseManager
import com.manmohan.nasaapod.ui.main.MainViewModel
import com.manmohan.nasaapod.ui.utils.ListViewState
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.Observable
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import java.util.*

@RunWith(JUnit4::class)
class MainViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var dataManager: AppDataManager

    var viewModel: MainViewModel? = null

    @Mock
    lateinit var appApiClient: ApiClient
    @Mock
    lateinit var databaseManager: AppDatabaseManager
    @Mock
    lateinit var connectivityManager: ConnectivityManager
    @Mock
    lateinit var lifecycleOwner: LifecycleOwner
    lateinit var lifecycle: Lifecycle

    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        lifecycle = LifecycleRegistry(lifecycleOwner)

        dataManager = mock(AppDataManager::class.java)
        // creating presenter with mock objects
        viewModel = MainViewModel(dataManager, RxSingleSchedulers.TEST_SCHEDULER)
    }

    @Test
    fun testLoadApodList() {
        //Assemble
        var date : Date = Date(System.currentTimeMillis())
        val apod = ArrayList<Apod>()
        `when`(dataManager.isNetworkConnected).thenReturn(true)
        `when`(dataManager.getApodList(date, date)).thenReturn(Observable.just<List<Apod>>(apod))

        viewModel!!.fetchList(date, date)
        verify<AppDataManager>(dataManager).getApodList(date, date)
        val dataObserver = TestObserver.test(viewModel!!.listState)
        assertEquals(ListViewState.SUCCESS_STATE.currentState, (dataObserver.value() as ListViewState).currentState)
        assertEquals(apod, (dataObserver.value() as ListViewState).data)
    }

    @Test
    fun testErrorTest() {
        //Assemble
        var date : Date = Date(System.currentTimeMillis())
        `when`(dataManager.isNetworkConnected).thenReturn(true)
        `when`(dataManager.getApodList(date, date)).thenReturn(Observable.error(Throwable("Error")))

        viewModel!!.fetchList(date, date)
        verify<AppDataManager>(dataManager).getApodList(date, date)
        val dataObserver = TestObserver.test(viewModel!!.listState)
        assertEquals(ListViewState.ERROR_STATE.currentState, (dataObserver.value() as ListViewState).currentState)
    }

    @After
    fun tearDown() {
        viewModel = null
    }
}
