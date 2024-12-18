package sync2app.com.syncapplive.DE_MO_TEST

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import sync2app.com.syncapplive.additionalSettings.utils.Utility
import sync2app.com.syncapplive.databinding.ActivityKoloWebviewPageBinding

class KoloWebviewPage : AppCompatActivity() {

    private lateinit var binding: ActivityKoloWebviewPageBinding

    private var LogKoloWebviewPage = "LogKoloWebviewPage"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKoloWebviewPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnFecthData.setOnClickListener {
            getAllIndexUrls()
        }

    }

    private fun getAllIndexUrls() {
      //  val url = "https://cp.cloudappserver.co.uk/app_base/public//CLO/DE_MO_2021001/App/index.html"
        val url = "https://www.cnbc.com/enterprise/"

        CoroutineScope(Dispatchers.IO).launch {
            val urls = Utility.fetchUrlsFromHtml(url)

            withContext(Dispatchers.Main) {
                urls.forEach {
                    Log.d("LogKoloWebviewPage", "Fetched URL $it")
                    Utility.showToastMessage(applicationContext, it)
                }
            }
        }
}
}
