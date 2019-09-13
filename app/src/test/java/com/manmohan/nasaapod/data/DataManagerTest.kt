package com.manmohan.nasaapod.data

import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import com.jraska.livedata.TestObserver
import com.manmohan.nasaapod.data.api.ApiClient
import com.manmohan.nasaapod.data.api.RxSingleSchedulers
import com.manmohan.nasaapod.data.api.model.Apod
import com.manmohan.nasaapod.data.database.AppDatabaseManager
import com.manmohan.nasaapod.ui.main.MainViewModel
import com.manmohan.nasaapod.ui.utils.ListViewState
import com.manmohan.nasaapod.ui.utils.toStr
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.Observable
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.util.*

@RunWith(JUnit4::class)
class DataManagerTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    var dataManager: AppDataManager ? = null

    @Mock
    lateinit var appApiClient: ApiClient
    @Mock
    lateinit var databaseManager: AppDatabaseManager
    @Mock
    lateinit var connectivityManager: ConnectivityManager
    @Mock
    lateinit var lifecycleOwner: LifecycleOwner

    @Mock
    internal var networkInfo: NetworkInfo? = null

    lateinit var lifecycle: Lifecycle

    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        lifecycle = LifecycleRegistry(lifecycleOwner)

        databaseManager = Mockito.mock(AppDatabaseManager::class.java)
        // creating presenter with mock objects
        dataManager = AppDataManager(appApiClient, databaseManager, connectivityManager)
    }

    @Test
    fun testLoadApodList() {
        //Assemble
        var date : Date = Date(System.currentTimeMillis())
        var dateStr : String = date.toStr()
        val apod = ArrayList<Apod>()
        `when`(appApiClient.fetchList(dateStr, dateStr)).thenReturn(Observable.just<List<Apod>>(apod))
        `when`(databaseManager.getApodList(date, date)).thenReturn(Observable.just<List<Apod>>(apod))
        `when`<NetworkInfo>(connectivityManager.activeNetworkInfo).thenReturn(networkInfo)

        // for network connected
        `when`<Boolean>(networkInfo?.isConnectedOrConnecting()).thenReturn(true)
        dataManager?.getApodList(date,date)
        //Verify
        verify<ApiClient>(appApiClient).fetchList(dateStr, dateStr)

        // for network not connected
        `when`<Boolean>(networkInfo?.isConnectedOrConnecting()).thenReturn(false)
        dataManager?.getApodList(date,date)
        //Verify
        verify<AppDatabaseManager>(databaseManager).getApodList(date, date)
    }

    @Test
    fun testSaveUserAction() {
        val apod =  Apod("",Date(),"","","","","","", false)
        //call test method
        dataManager?.saveUserAction(apod, true)
        verify<AppDatabaseManager>(databaseManager).saveUserAction(apod, true)

        dataManager?.saveUserAction(apod, false)
        verify<AppDatabaseManager>(databaseManager).saveUserAction(apod, false)
    }

    @After
    fun tearDown() {
        dataManager = null
    }
}
