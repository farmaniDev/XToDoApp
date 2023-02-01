package com.farmani.xtodo.fragments.update

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.fragment.app.Fragment
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.farmani.xtodo.R
import com.farmani.xtodo.data.models.Priority
import com.farmani.xtodo.data.models.ToDoData
import com.farmani.xtodo.data.viewmodel.ToDoViewModel
import com.farmani.xtodo.databinding.FragmentAddBinding
import com.farmani.xtodo.databinding.FragmentUpdateBinding
import com.farmani.xtodo.fragments.SharedViewModel

class UpdateFragment : Fragment(), MenuProvider {
    private val args by navArgs<UpdateFragmentArgs>()
    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!
    private val mSharedViewModel: SharedViewModel by viewModels()
    private val mToDoViewModel: ToDoViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
        // Inflate the layout for this fragment
        _binding = FragmentUpdateBinding.inflate(inflater, container, false)
        binding.titleEditTextUpdate.setText(args.currentItem.title)
        binding.descriptionEditTextUpdate.setText(args.currentItem.description)
        binding.prioritiesSpinnerUpdate.setSelection(mSharedViewModel.parsePriorityToInt(args.currentItem.priority))
        binding.prioritiesSpinnerUpdate.onItemSelectedListener = mSharedViewModel.listener
        return binding.root
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.update_fragment_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.itemId == R.id.menu_save) {
            updateItem()
        }
        return false
    }

    private fun updateItem() {
        val title = binding.titleEditTextUpdate.text.toString()
        val description = binding.descriptionEditTextUpdate.text.toString()
        val getPriority = binding.prioritiesSpinnerUpdate.selectedItem.toString()

        val validation = mSharedViewModel.verifyDataFromUser(title, description)

        if (validation) {
            val updateItem = ToDoData(
                args.currentItem.id,
                title,
                mSharedViewModel.parsePriority(getPriority),
                description
            )
            mToDoViewModel.updateData(updateItem)
            Toast.makeText(requireContext(), R.string.note_updated_message, Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), R.string.note_saved_failed, Toast.LENGTH_SHORT).show()
        }
    }

}