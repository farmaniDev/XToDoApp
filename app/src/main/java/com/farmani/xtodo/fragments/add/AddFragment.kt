package com.farmani.xtodo.fragments.add

import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.fragment.app.Fragment
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.farmani.xtodo.R
import com.farmani.xtodo.data.models.Priority
import com.farmani.xtodo.data.models.ToDoData
import com.farmani.xtodo.data.viewmodel.ToDoViewModel
import kotlinx.android.synthetic.main.fragment_add.*

class AddFragment : Fragment(), MenuProvider {

    private val mToDoViewModel: ToDoViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add, container, false)
        return view
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.add_fragment_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.itemId == R.id.menu_add) {
            insertDataToDb()
        }
        return false
    }

    private fun insertDataToDb() {
        val mTitle = title_editText.text.toString()
        val mPriority = priorities_spinner.selectedItem.toString()
        val mDescription = description_editText.text.toString()

        val validation = verifyDataFromUser(mTitle, mDescription)
        if (validation) {
            val newData = ToDoData(
                0,
                mTitle,
                parsePriority(mPriority),
                mDescription
            )
            mToDoViewModel.insertData(newData)
            Toast.makeText(requireContext(), R.string.note_saved_message, Toast.LENGTH_SHORT).show()
            // Redirect Back
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), R.string.note_saved_failed, Toast.LENGTH_SHORT).show()
        }
    }

    private fun verifyDataFromUser(title: String, description: String): Boolean {
        return if (TextUtils.isEmpty(title) || TextUtils.isEmpty(description)) {
            false
        } else !(title.isEmpty() || description.isEmpty())
    }

    private fun parsePriority(priority: String): Priority {
        return when (priority) {
            "High Priority" -> {
                Priority.HIGH
            }
            "Medium Priority" -> {
                Priority.MEDIUM
            }
            "Low Priority" -> {
                Priority.LOW
            }
            else -> Priority.LOW
        }
    }

}