package id.febwjy.beritaapp.ui.extension

import android.content.Context
import android.widget.Toast

/**
 * Created by Febby Wijaya on 14/11/2023.
 */
fun Context.showToast(message: String){
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}