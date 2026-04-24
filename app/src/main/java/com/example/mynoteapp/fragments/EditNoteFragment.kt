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

class EditNoteFragment : Fragment(R.layout.fragment_add_note), MenuProvider {

    private var _binding: FragmentAddNoteBinding? = null
    private val binding get() = _binding!!

    private val notesViewModel: NotesViewModel by activityViewModels()

    private lateinit var currentNote: Note

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ✅ GET NOTE (SafeArgs)
        val args = EditNoteFragmentArgs.fromBundle(requireArguments())
        currentNote = args.note

        // ✅ SET UI MODE (EDIT MODE)
        binding.screenTitle.text = "Edit Note"
        binding.deleteButton.visibility = View.VISIBLE

        // ✅ FILL EXISTING DATA
        binding.addNoteTitle.setText(currentNote.noteTitle)
        binding.addNoteDesc.setText(currentNote.noteDesc)

        // MENU
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        // SAVE BUTTON
        binding.saveButton.setOnClickListener {
            updateNote()
        }

        // DELETE BUTTON
        binding.deleteButton.setOnClickListener {
            deleteNote()
        }
    }

    // 📝 UPDATE NOTE
    private fun updateNote() {
        val title = binding.addNoteTitle.text.toString().trim()
        val desc = binding.addNoteDesc.text.toString().trim()

        if (title.isEmpty()) {
            Toast.makeText(requireContext(), "Title required", Toast.LENGTH_SHORT).show()
            return
        }

        val updatedNote = Note(
            id = currentNote.id,
            noteTitle = title,
            noteDesc = desc
        )

        notesViewModel.updateNote(updatedNote)

        Toast.makeText(requireContext(), "Note Updated", Toast.LENGTH_SHORT).show()

        findNavController().popBackStack(R.id.homeFragment, false)
    }

    // 🗑 DELETE NOTE
    private fun deleteNote() {
        notesViewModel.deleteNote(currentNote)

        Toast.makeText(requireContext(), "Note Deleted", Toast.LENGTH_SHORT).show()

        findNavController().popBackStack(R.id.homeFragment, false)
    }

    // MENU (top save icon)
    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.menu_add_note, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.saveMenu -> {
                updateNote()
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