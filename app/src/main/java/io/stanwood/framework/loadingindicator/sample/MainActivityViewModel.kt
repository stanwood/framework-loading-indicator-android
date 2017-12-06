package io.stanwood.framework.loadingindicator.sample

import android.databinding.BaseObservable
import android.os.AsyncTask
import io.stanwood.framework.loadingindicator.ConnectivityViewModel

class MainActivityViewModel : BaseObservable() {

    private val refreshClickCallback = object : ConnectivityViewModel.ItemClickCallback {
        override fun onClick(trigger: ConnectivityViewModel) = loadData()
    }

    val connectivityViewModel = ConnectivityViewModel(
            refreshClickCallback,
            "Whoops, something went wrong!",
            BuildConfig.DEBUG)

    fun loadData() {
        if (connectivityViewModel.isVisible && !connectivityViewModel.isError) {
            // normally we would never check the current loading state by looking at the UI, but as this is just a sample this should suffice
            return
        }

        connectivityViewModel.loadingMessage = "Loading..."
        SleepyAsyncTask(connectivityViewModel).execute()
    }

    fun showError() {
        connectivityViewModel.error = "Some weird error happened"
    }
}

class SleepyAsyncTask(private val connectivityViewModel: ConnectivityViewModel) : AsyncTask<Void, Void, Void?>() {
    override fun doInBackground(vararg p0: Void?): Void? {
        Thread.sleep(3000)
        return null
    }

    override fun onPostExecute(result: Void?) {
        if (connectivityViewModel.isError) {
            // do not hide loading indicator if we are displaying an error
            return
        }

        connectivityViewModel.loadingMessage = null
    }
}