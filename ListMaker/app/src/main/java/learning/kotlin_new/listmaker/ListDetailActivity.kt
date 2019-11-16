package learning.kotlin_new.listmaker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class ListDetailActivity : AppCompatActivity() {

     private var list: TaskList? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_detail)
        //1
        list = this.intent.getParcelableExtra(MainActivity.INTENT_LIST_KEY)
        //2
        title = list?.name
     }
}
