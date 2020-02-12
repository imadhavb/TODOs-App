package edu.towson.cosc435.bajgai.todos_madhav

import android.graphics.Bitmap
import android.util.Log
import android.widget.Toast
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.BitmapRequestListener
import com.androidnetworking.interfaces.ParsedRequestListener
import edu.towson.cosc435.bajgai.todos_madhav.models.Todo



class NetworkHelper {

    companion object {
        val API_URL = "https://my-json-server.typicode.com/rvalis-towson/todos_api/todos"
    }
    fun fetchTodo(callback: (List<edu.towson.cosc435.bajgai.todos_madhav.Todo>) -> Unit) {
        AndroidNetworking.get(API_URL)
            .setTag(this)
            .setPriority(Priority.LOW)
            .build()
            .getAsObjectList(edu.towson.cosc435.bajgai.todos_madhav.Todo::class.java, object: ParsedRequestListener<List<edu.towson.cosc435.bajgai.todos_madhav.Todo>> {
                override fun onResponse(response: List<edu.towson.cosc435.bajgai.todos_madhav.Todo>?) {
                    if (response != null) {
                        callback(response)
                    } else {
                        throw Exception("Error Fetching People")
                    }
                }
                override fun onError(anError: ANError?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }


            })
    }

    fun fetchIcon(iconUrl: String, callback: (Bitmap) -> Unit) {
        AndroidNetworking.get(iconUrl)
            .setTag("imageRequestTag")
            .setPriority(Priority.LOW)
            .setBitmapMaxHeight(400)
            .setBitmapMaxWidth(400)
            .setBitmapConfig(Bitmap.Config.ARGB_8888)
            .build()
            .getAsBitmap(object: BitmapRequestListener {
                override fun onResponse(response: Bitmap?) {
                    if (response != null){
                        callback(response)
                    } else {
                        throw java.lang.Exception("error")
                    }
                }

                override fun onError(anError: ANError?) {
                    Log.e("tag", "$anError")
                }
            })
    }
}