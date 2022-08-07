package com.openclassrooms.realestatemanager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.slidingpanelayout.widget.SlidingPaneLayout
import com.openclassrooms.realestatemanager.databinding.FragmentMaisonsListBinding

class MaisonsListFragment : Fragment() {

    private val maisonsViewModel: MaisonsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentMaisonsListBinding.inflate(inflater, container, false).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentMaisonsListBinding.bind(view)
        val slidingPaneLayout = binding.slidingPaneLayout
        slidingPaneLayout.lockMode = SlidingPaneLayout.LOCK_MODE_LOCKED

        // Connect the SlidingPaneLayout to the system back button.
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            MaisonsListOnBackPressedCallback(slidingPaneLayout)
        )

        // Initialize the adapter and set it to the RecyclerView.
        val adapter = MaisonsListAdapter {
            // Update the user selected sport as the current sport in the shared viewmodel
            maisonsViewModel.updateCurrentMaison(it)
            if (slidingPaneLayout.isSlideable && !slidingPaneLayout.isOpen) {
                // Navigate to the details screen
                val action = MaisonsListFragmentDirections.actionMaisonsListFragmentToNewsFragment()
                this.findNavController().navigate(action)

            }

        }
        binding.recyclerView.adapter = adapter
        adapter.submitList(maisonsViewModel.maisonsData)
    }

}

class MaisonsListOnBackPressedCallback(
    private val slidingPaneLayout: SlidingPaneLayout
) : OnBackPressedCallback(slidingPaneLayout.isSlideable && slidingPaneLayout.isOpen),
    SlidingPaneLayout.PanelSlideListener {

    init {
        slidingPaneLayout.addPanelSlideListener(this)
    }
    /**
     * Callback for handling the [OnBackPressedDispatcher.onBackPressed] event.
     */
    override fun handleOnBackPressed() {
        slidingPaneLayout.closePane()
    }

    /**
     * Called when a detail view's position changes.
     *
     * @param panel       The child view that was moved
     * @param slideOffset The new offset of this sliding pane within its range, from 0-1
     */
    override fun onPanelSlide(panel: View, slideOffset: Float) {
    }

    /**
     * Called when a detail view becomes slid completely open.
     *
     * @param panel The detail view that was slid to an open position
     */
    override fun onPanelOpened(panel: View) {
        isEnabled = true
    }

    /**
     * Called when a detail view becomes slid completely closed.
     *
     * @param panel The detail view that was slid to a closed position
     */
    override fun onPanelClosed(panel: View) {
        isEnabled = false
    }
}