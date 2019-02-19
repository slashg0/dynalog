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
                    "   \"title\":\"Title\",\n" +
                    "   \"message\":\"This is a dynamically created dialog and you are required to respect the genius behind it :*\",\n" +
                    "   \"header_image\":\"<image_url>\",\n" +
                    "   \"is_dismissable\":false,\n" +
                    "   \"buttons\":[\n" +
                    "      {\n" +
                    "         \"text\":\"Leftmost button\",\n" +
                    "         \"type\":\"coloured|default|borderless\",\n" +
                    "         \"action\":\"dismiss|redirect\",\n" +
                    "         \"is_enabled\":true\n" +
                    "      },\n" +
                    "      {\n" +
                    "         \"text\":\"Rightmost button\",\n" +
                    "         \"type\":\"coloured|default|borderless\",\n" +
                    "         \"action\":\"dismiss|redirect\",\n" +
                    "         \"is_enabled\":true\n" +
                    "      }\n" +
                    "   ]\n" +
                    "}")
            val builder = Dynalog.Builder.fromJSON(json)
            builder?.build(this)?.show()
        } catch (error: Exception) {
            Log.d("BLEH", "asdasd", error)
        }

    }
}
