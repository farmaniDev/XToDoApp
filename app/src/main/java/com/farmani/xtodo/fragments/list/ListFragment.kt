package com.farmani.xtodo.fragments.list

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.farmani.xtodo.R
import com.farmani.xtodo.data.viewmodel.ToDoViewModel
import com.farmani.xtodo.databinding.FragmentListBinding


class ListFragment : Fragment(), MenuProvider {
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private val mToDoAdapter: ToDoViewModel by viewModels()
    private val mToDoViewModel: ToDoViewModel by viewModels()
    private val adapter: ListAdapter by lazy { ListAdapter() }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentListBinding.inflate(inflater, container, false)
        val view = binding.root

        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        mToDoAdapter.getAllData.observe(viewLifecycleOwner, Observer { data ->
            adapter.setData(data)
        })

        binding.fabAdd.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
        return view
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.main_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.itemId == R.id.menu_deleteAll) {
            confirmRemoval()
        }
        return false
    }

    private fun confirmRemoval() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            mToDoViewModel.deleteAll()
            Toast.makeText(
                requireContext(),
                R.string.all_note_deleted_message,
                Toast.LENGTH_SHORT
            ).show()
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete All Notes")
        builder.setMessage("Are you sure you want to delete all notes?")
        builder.create().show()
    }
}
