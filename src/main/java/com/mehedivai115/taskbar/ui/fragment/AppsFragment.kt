package com.mehedivai115.taskbar.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mehedivai115.taskbar.R
import com.mehedivai115.taskbar.ui.adapter.AppAdapter
import com.mehedivai115.taskbar.viewmodel.TaskbarViewModel

class AppsFragment : Fragment() {

    private lateinit var viewModel: TaskbarViewModel
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_apps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        viewModel = ViewModelProvider(requireActivity()).get(TaskbarViewModel::class.java)
        recyclerView = view.findViewById(R.id.appsRecyclerView)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        
        viewModel.installedApps.observe(viewLifecycleOwner) { apps ->
            recyclerView.adapter = AppAdapter(apps) { app ->
                viewModel.openApp(app)
            }
        }
    }
}