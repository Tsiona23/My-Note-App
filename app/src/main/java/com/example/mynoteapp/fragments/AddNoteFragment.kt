package com.example.mynoteapp.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.example.mynoteapp.R
import com.example.mynoteapp.databinding.FragmentAddNoteBinding
import com.example.mynoteapp.model.Note
import com.example.mynoteapp.viewmodel.NotesViewModel

class AddNoteFragment : Fragment(R.layout.fragment_add_note), MenuProvider {

    private var _binding: FragmentAddNoteBinding? = null
    private val binding get() = _binding!!

    private val notesViewModel: NotesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)


        binding.saveButton.setOnClickListener {
            saveNote()
        }
    }

    private fun saveNote() {
        val title = binding.addNoteTitle.text.toString().trim()
        val desc = binding.addNoteDesc.text.toString().trim()

        if (title.isEmpty()) {
            Toast.makeText(requireContext(), "Please enter title", Toast.LENGTH_SHORT).show()
            return
        }

        val note = Note(
            id = 0,
            noteTitle = title,
            noteDesc = desc
        )

        notesViewModel.addNote(note)

        Toast.makeText(requireContext(), "Note Saved", Toast.LENGTH_SHORT).show()


        findNavController().navigate(R.id.action_addNoteFragment_to_homeFragment)
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.menu_add_note, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.saveMenu -> {
                saveNote()
                true
            }
            else -> false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}