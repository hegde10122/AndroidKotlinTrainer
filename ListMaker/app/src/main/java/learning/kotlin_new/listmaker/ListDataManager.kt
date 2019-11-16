package learning.kotlin_new.listmaker

import android.content.Context
import androidx.preference.PreferenceManager


class ListDataManager(private val context: Context) {

    fun saveList(list:TaskList){
        //1
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context).edit()

        //2
        sharedPreferences.putStringSet(list.name,list.tasks.toHashSet())

        //3
        sharedPreferences.apply()

    }

    fun readLists(): ArrayList<TaskList> {
        //1
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

        //2
        val sharedPreferenceContents = sharedPreferences.all

        //3
        val taskLists = ArrayList<TaskList>()

        //4
        for(tasklist in sharedPreferenceContents){
            val itemHashSet = tasklist.value as HashSet<String>
            val list = TaskList(tasklist.key,ArrayList(itemHashSet))
            //5
            taskLists.add(list)
        }
        return taskLists
    }


}