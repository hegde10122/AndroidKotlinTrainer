package learning.kotlin_new.listmaker

import android.os.Parcel
import android.os.Parcelable

//class with primary constructor to TaskList so that it can be given a name and a list of associated tasks:
class TaskList(val name: String?, val tasks: ArrayList<String> = ArrayList<String>()) :Parcelable {

    //2
    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(name)
        dest?.writeStringList(tasks)
    }

    //2
    override fun describeContents() = 0

    //1
    constructor(source:Parcel) : this (
        source.readString(),
        source.createStringArrayList() as ArrayList<String>
    )

    //4
    companion object CREATOR : Parcelable.Creator<TaskList> {
        override fun createFromParcel(parcel: Parcel): TaskList {
            return TaskList(parcel)
        }

        override fun newArray(size: Int): Array<TaskList?> {
            return arrayOfNulls(size)
        }
    }


}