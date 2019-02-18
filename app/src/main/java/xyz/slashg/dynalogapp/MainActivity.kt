package xyz.slashg.dynalogapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import org.json.JSONObject
import xyz.slashg.dynalog.Dynalog

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        try {
            var json = JSONObject("{\"title\":\"Title\",\"message\":\"This is a dynamically created dialog and you are required to respect the genius behind it \",\"header_image_url\":\"https://imgc.allpostersimages.com/img/print/posters/sean-pavone-hong-kong-china-skyline-panorama-from-across-victoria-harbor_a-G-14787778-14258375.jpg\",\"icon_image_url\":\"https://slashg.xyz/img/slashg_logo_64.png\",\"is_dismissable\":false,\"buttons\":[{\"text\":\"Button 0\",\"type\":\"borderless\",\"action\":\"dismiss\",\"is_enabled\":true},{\"text\":\"Open Website\",\"type\":\"coloured\",\"action\":\"redirect\",\"action_param\":\"https://www.kreatryx.com/\",\"is_enabled\":true}]}")
            val builder = Dynalog.Builder.fromJSON(json)
            builder?.build(this)?.show()
        } catch (error: Exception) {
            Log.d("BLEH", "asdasd", error)
        }

    }
}
