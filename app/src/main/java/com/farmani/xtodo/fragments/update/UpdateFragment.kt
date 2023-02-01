package com.farmani.xtodo.fragments.update

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.fragment.app.Fragment
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.navArgs
import com.farmani.xtodo.R
import com.farmani.xtodo.data.models.Priority
import com.farmani.xtodo.databinding.FragmentAddBinding
import com.farmani.xtodo.databinding.FragmentUpdateBinding
import com.farmani.xtodo.fragments.SharedViewModel

class UpdateFragment : Fragment(), MenuProvider {
    private val args by navArgs<UpdateFragmentArgs>()
    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!
    private val mSharedViewModel: SharedViewModel by viewModels()
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
        binding.prioritiesSpinnerUpdate.setSelection(parsePriority(args.currentItem.priority))
        binding.prioritiesSpinnerUpdate.onItemSelectedListener = mSharedViewModel.listener
        return binding.root
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.update_fragment_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return false
    }

    private fun parsePriority(priority: Priority): Int {
        return when (priority) {
            Priority.HIGH -> 0
            Priority.MEDIUM -> 1
            Priority.LOW -> 2
        }
    }
}