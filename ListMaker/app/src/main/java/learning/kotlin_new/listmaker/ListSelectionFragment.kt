package learning.kotlin_new.listmaker

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import learning.kotlin_new.listmaker.dummy.DummyContent


class ListSelectionFragment : Fragment(),ListSelectionRecyclerViewAdapter.ListSelectionRecyclerViewClickListener {

    private lateinit var listDataManager:ListDataManager
    lateinit var listsRecyclerView: RecyclerView
    private var listener: OnListFragmentInteractionListener? = null

    //4
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    //5
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list_selection, container, false)
        return view
    }

    //3
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            listener = context
            listDataManager =  ListDataManager(context)
        } else {
            throw RuntimeException("$context must implement OnListFragmentInteractionListener")
        }
    }

    //6
    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson
     * [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */

    //1
    interface OnListFragmentInteractionListener {

        fun onListFragmentInteraction(item: DummyContent.DummyItem)
    }

    //2
    companion object {

        fun newInstance() : ListSelectionFragment {
            val fragment = ListSelectionFragment()
            return fragment
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //1 set the listsRecyclerView by referencing the ID of the Recyclerview setup in the layout
        val lists = listDataManager.readLists()

        view?.let {
            listsRecyclerView = it.findViewById(R.id.lists_recyclerview)

            //2 The Recyclerview will use the linear layout to present the items in our case. GridLayoutManager and
            //StaggeredGridLayoutManager are the other varieties available
            listsRecyclerView.layoutManager = LinearLayoutManager(activity)
            listsRecyclerView.adapter = ListSelectionRecyclerViewAdapter(lists,this)
        }
    }

    override fun listItemClicked(list: TaskList) {
        listener?.onListItemClicked(list)
    }

    fun addList(list: TaskList){
        listDataManager.saveList(list)
        val recyclerViewAdapter = listsRecyclerView.adapter as ListSelectionRecyclerViewAdapter
        recyclerViewAdapter.addList(list)
    }

    fun saveList(list:TaskList){
        listDataManager.saveList(list)
        updateLists()
    }

    private fun updateLists(){
        val lists = listDataManager.readLists()
        listsRecyclerView.adapter = ListSelectionRecyclerViewAdapter(lists,this)
    }
}

private fun ListSelectionFragment.OnListFragmentInteractionListener?.onListItemClicked(list: TaskList) {

}
