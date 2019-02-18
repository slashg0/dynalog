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
            var json = JSONObject("{\n" +
                    "      \"title\":\"Title\",\n" +
                    "      \"color_scheme\":{\n" +
                    "         \"background_color\":\"#2d2d2d\",\n" +
                    "         \"accent_color\":\"#ff0000\",\n" +
                    "         \"primary_color\":\"#fafafa\",\n" +
                    "         \"secondary_color\":\"#d5d5d5\",\n" +
                    "         \"invert_color\":\"#000000\"\n" +
                    "      },\n" +
                    "      \"message\":\"This is a dynamically created dialog and you are required to respect the genius behind it :*\",\n" +
                    "\"header_image_url\":\"https://imgc.allpostersimages.com/img/print/posters/sean-pavone-hong-kong-china-skyline-panorama-from-across-victoria-harbor_a-G-14787778-14258375.jpg\"," +
                    "      \"is_dismissable\":false,\n" +
                    "      \"buttons\":[\n" +
                    "         {\n" +
                    "            \"text\":\"Leftmost button\",\n" +
                    "            \"type\":\"coloured|default|borderless\",\n" +
                    "            \"action\":\"dismiss|redirect\",\n" +
                    "            \"is_enabled\":true\n" +
                    "         },\n" +
                    "         {\n" +
                    "            \"text\":\"Rightmost button\",\n" +
                    "            \"type\":\"coloured|default|borderless\",\n" +
                    "            \"action\":\"dismiss|redirect\",\n" +
                    "            \"is_enabled\":true\n" +
                    "         }\n" +
                    "      ]\n" +
                    "   }")
            val builder = Dynalog.Builder.fromJSON(json)
            builder?.build(this)?.show()
        } catch (error: Exception) {
            Log.d("BLEH", "asdasd", error)
        }

    }
}
