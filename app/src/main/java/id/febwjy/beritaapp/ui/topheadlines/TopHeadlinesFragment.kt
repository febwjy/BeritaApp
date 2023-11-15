package id.febwjy.beritaapp.ui.topheadlines

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import id.febwjy.beritaapp.R
import id.febwjy.beritaapp.data.model.NewsResponse
import id.febwjy.beritaapp.databinding.FragmentTopheadlinesListBinding
import id.febwjy.beritaapp.ui.BaseState
import id.febwjy.beritaapp.ui.extension.gone
import id.febwjy.beritaapp.ui.extension.showToast
import id.febwjy.beritaapp.ui.extension.visible
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * Created by Febby Wijaya on 15/11/2023.
 */

@AndroidEntryPoint
class TopHeadlinesFragment : Fragment(R.layout.fragment_topheadlines_list) {

    private var mBinding: FragmentTopheadlinesListBinding? = null
    private val binding get() = mBinding!!
    private var page: Int = 1

    private val viewModel: TopHeadlinesViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = FragmentTopheadlinesListBinding.bind(view)
        setupRecycler()
        observeState()
        observeMovieList()

        binding.newsListRecyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    val canLoad = viewModel.loadMore.value
                    if (!canLoad){
                        Log.d("LoadMore", "onScrollStateChanged: ${viewModel.loadMore.value}")
                    } else {
                        viewModel.getTopHeadlines(category = "technology", country = "us")
                        Log.d("LoadMore", "onScrollStateChanged: ${viewModel.loadMore.value}")
                    }
                    page++;
                }
            }
        })

        viewModel.getTopHeadlines(category = "technology", country = "us")
    }

    private fun observeMovieList() {
        viewModel.mNewsList.flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { movieList ->
                handleProducts(movieList)
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun handleProducts(movieList: List<NewsResponse.Articles>) {
        binding.newsListRecyclerview.adapter?.let {
            if (it is TopHeadlinesAdapter) {
                it.updateList(movieList)
            }
        }
    }

    private fun observeState() {
        viewModel.mState.flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach {
                handleState(it)
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun handleState(state: BaseState) {
        when (state) {
            is BaseState.IsLoading -> handleLoading(state.isLoading)
            is BaseState.ShowToast -> {
                binding.loadingProgressBar.gone()
                binding.newsListRecyclerview.gone()
                binding.errorLayout.visible()
                requireActivity().showToast(state.message)
            }

            is BaseState.Init -> Unit
        }
    }

    private fun handleLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.loadingProgressBar.visible()
            binding.errorLayout.gone()
            binding.newsListRecyclerview.gone()
        } else {
            binding.loadingProgressBar.gone()
            binding.errorLayout.gone()
            binding.newsListRecyclerview.visible()
        }
    }

    private fun setupRecycler() {
        val mAdapter = TopHeadlinesAdapter(mutableListOf(), TopHeadlinesAdapter.OnClickListener {
            val bundle = Bundle()
            bundle.putString("title", it.title)
            bundle.putString("content", it.content)
            bundle.putString("img", it.urlToImage)
            bundle.putString("publishedAt", it.publishedAt)
            bundle.putString("author", it.author)
            bundle.putString("url", it.url)
            findNavController().navigate(R.id.action_goto_newsdetail, bundle)

        })
        binding.newsListRecyclerview.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(requireActivity())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        super.onDestroyView()
        mBinding = null
    }

}