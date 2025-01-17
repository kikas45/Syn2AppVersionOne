package sync2app.com.syncapplive.additionalSettings.utils
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.app.ActivityManager
import android.content.Context
import android.net.ConnectivityManager
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import sync2app.com.syncapplive.myService.ParsingSyncService
import sync2app.com.syncapplive.myService.RetryParsingSyncService

object Utility {

    fun hideKeyBoard(context: Context, editText: EditText) {
        try {
            editText.clearFocus()
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(editText.windowToken, 0)
        } catch (ignored: Exception) {
        }
    }

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }


    fun showToastMessage(applicationContext:Context, messages: String) {
        Toast.makeText(applicationContext, messages, Toast.LENGTH_SHORT).show()
    }



    fun fetchUrlsFromHtml(url: String): List<String> {
        val urls = mutableListOf<String>()


        val fileTypes = listOf(
            // Font formats
            ".ttf",

            // Image formats
            ".png", ".jpg", ".jpeg", ".gif", ".bmp", ".ico", ".svg", ".webp",

            // Video formats
            ".mp4", ".avi", ".mov", ".wmv", ".mkv", ".webm",

            // Audio formats
            ".mp3", ".wav", ".aac", ".ogg", ".flac", ".m4a", ".wma",

            // Document formats
            ".pdf", ".doc", ".docx", ".ppt", ".pptx", ".epub", ".xlsx", ".xls", ".csv", ".txt",

            // Web formats
            ".html", ".htm", ".asp", ".aspx", ".php", ".net", ".css", ".js", ".json", ".webmanifest",

            // Archive formats
            ".zip", ".rar", ".tar", ".gz",

            // Data formats
            ".sqlite", ".xml", ".yml", ".yaml", ".scss"
        )



        try {
            // Fetch the HTML document
            val document: Document = Jsoup.connect(url).get()

            // Base URL for relative paths
            val baseUrl = url.substringBeforeLast("/")

            // Select all anchor tags and get their href
            val links = document.select("a[href]")
            for (link in links) {
                urls.add(link.attr("abs:href")) // Get absolute URLs
            }

            // Select all img tags and get their src
            val images = document.select("img[src]")
            for (img in images) {
                urls.add(img.attr("abs:src"))
            }

            // Select relevant asset tags (CSS and JS)
            val otherAssets = document.select("link[href], script[src]")
            for (asset in otherAssets) {
                when (asset.tagName()) {
                    "link" -> urls.add(asset.attr("abs:href"))
                    "script" -> urls.add(asset.attr("abs:src"))
                }
            }

            // Add all files that match the specified types
            urls.addAll(urls.filter { url ->
                fileTypes.any { url.endsWith(it, ignoreCase = true) }
            })

            // Combine the base URL with relative paths if needed
            return urls.map { path ->
                if (path.startsWith("http")) path else "$baseUrl/$path"
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return emptyList()
    }



    /*
     fun fetchUrlsFromHtml(url: String): List<String> {
         val urls = mutableListOf<String>()

         val fileTypes = listOf(
             // Font formats
             ".ttf",

             // Image formats
             ".png", ".jpg", ".jpeg", ".gif", ".bmp", ".ico", ".svg", ".webp",

             // Video formats
             ".mp4", ".avi", ".mov", ".wmv", ".mkv", ".webm",

             // Audio formats
             ".mp3", ".wav", ".aac", ".ogg", ".flac", ".m4a", ".wma",

             // Document formats
             ".pdf", ".doc", ".docx", ".ppt", ".pptx", ".epub", ".xlsx", ".xls", ".csv", ".txt",

             // Web formats
             ".html", ".htm", ".asp", ".aspx", ".php", ".net", ".css", ".js", ".json", ".webmanifest",

             // Archive formats
             ".zip", ".rar", ".tar", ".gz",

             // Data formats
             ".sqlite", ".xml", ".yml", ".yaml", ".scss"
         )

         try {
             // Fetch the HTML document
             val document: Document = Jsoup.connect(url).get()

             // Base URL for relative paths
             val baseUrl = url.substringBeforeLast("/")

             // Select all anchor tags and get their href
             val links = document.select("a[href]")
             for (link in links) {
                 urls.add(cleanUrl(link.attr("abs:href"))) // Clean and add URLs
             }

             // Select all img tags and get their src
             val images = document.select("img[src]")
             for (img in images) {
                 urls.add(cleanUrl(img.attr("abs:src"))) // Clean and add URLs
             }

             // Select relevant asset tags (CSS and JS)
             val otherAssets = document.select("link[href], script[src]")
             for (asset in otherAssets) {
                 when (asset.tagName()) {
                     "link" -> urls.add(cleanUrl(asset.attr("abs:href")))
                     "script" -> urls.add(cleanUrl(asset.attr("abs:src")))
                 }
             }

             // Add all files that match the specified types
             urls.addAll(urls.filter { url ->
                 fileTypes.any { url.endsWith(it, ignoreCase = true) }
             })

             // Combine the base URL with relative paths if needed
             return urls.map { path ->
                 if (path.startsWith("http")) path else "$baseUrl/$path"
             }

         } catch (e: Exception) {
             e.printStackTrace()
         }
         return emptyList()
     }

     // Helper function to clean URLs
     private fun cleanUrl(url: String): String {
         return url.replace("#", "_") // Replace # with _
             .replace("?", "_") // Replace ? with _
             .replace("&", "_") // Replace & with _
             .replace("=", "_") // Replace = with _
             .replace("[^a-zA-Z0-9./:_-]".toRegex(), "_") // Remove unwanted characters
     }

 */



    fun startPulseAnimationForText(view: View) {
        val scaleUpX = ObjectAnimator.ofFloat(view, "scaleX", 1f, 1.2f).setDuration(300)
        val scaleUpY = ObjectAnimator.ofFloat(view, "scaleY", 1f, 1.2f).setDuration(300)

        val scaleDownX = ObjectAnimator.ofFloat(view, "scaleX", 1.2f, 1f).setDuration(300)
        val scaleDownY = ObjectAnimator.ofFloat(view, "scaleY", 1.2f, 1f).setDuration(300)

        // Play the animations together
        scaleUpX.start()
        scaleUpY.start()

        // Set up an animation listener to play the downward scale after the upward scale
        scaleUpX.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                scaleDownX.start()
                scaleDownY.start()
            }
        })

        // Repeat the animation
        scaleDownX.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                startPulseAnimationForText(view) // Restart the pulse effect
            }
        })
    }



    //// used to manage foreground services
    fun foregroundParsingServiceClass(context: Context): Boolean {
        val activityManager = context.applicationContext.getSystemService(AppCompatActivity.ACTIVITY_SERVICE) as ActivityManager
        for (service in activityManager.getRunningServices(Int.MAX_VALUE)) {
            if (ParsingSyncService::class.java.name == service.service.className) {
                return true
            }
        }
        return false
    }

    fun foregroundRetryParsingServiceClass(context: Context): Boolean {
        val activityManager = context.applicationContext.getSystemService(AppCompatActivity.ACTIVITY_SERVICE) as ActivityManager
        for (service in activityManager.getRunningServices(Int.MAX_VALUE)) {
            if (RetryParsingSyncService::class.java.name == service.service.className) {
                return true
            }
        }
        return false
    }


}
