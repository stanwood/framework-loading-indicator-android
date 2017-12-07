package io.stanwood.framework.loadingindicator.sample

import android.databinding.BaseObservable
import android.os.AsyncTask
import io.stanwood.framework.loadingindicator.LoadingIndicatorViewModel

class MainActivityViewModel : BaseObservable() {

    private val refreshClickCallback = object : LoadingIndicatorViewModel.ItemClickCallback {
        override fun onClick(trigger: LoadingIndicatorViewModel) = loadData()
    }

    val loadingIndicatorViewModel = LoadingIndicatorViewModel(refreshClickCallback)

    fun loadData() {
        if (loadingIndicatorViewModel.isVisible && !loadingIndicatorViewModel.isError) {
            // normally we would never check the current loading state by looking at the UI, but as this is just a sample this should suffice
            return
        }

        loadingIndicatorViewModel.loadingMessage = "Loading..."
        SleepyAsyncTask(loadingIndicatorViewModel).execute()
    }

    fun showError() {
        loadingIndicatorViewModel.errorMessage = "Some weird errorMessage happened"
    }
}

class SleepyAsyncTask(private val loadingIndicatorViewModel: LoadingIndicatorViewModel) : AsyncTask<Void, Void, Void?>() {
    override fun doInBackground(vararg p0: Void?): Void? {
        Thread.sleep(3000)
        return null
    }

    override fun onPostExecute(result: Void?) {
        if (loadingIndicatorViewModel.isError) {
            // do not hide loading indicator if we are displaying an errorMessage
            return
        }

        loadingIndicatorViewModel.loadingMessage = null
    }
}