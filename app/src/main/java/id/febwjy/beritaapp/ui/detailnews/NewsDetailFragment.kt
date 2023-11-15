package id.febwjy.beritaapp.ui.detailnews

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import id.febwjy.beritaapp.R
import id.febwjy.beritaapp.databinding.FragmentDetailNewsBinding
import id.febwjy.beritaapp.utils.DateUtils

/**
 * Created by Febby Wijaya on 15/11/2023.
 */

@AndroidEntryPoint
class NewsDetailFragment : Fragment(R.layout.fragment_detail_news) {

    private var mBinding: FragmentDetailNewsBinding? = null

    private val binding get() = mBinding!!
    private lateinit var title: String
    private lateinit var content: String
    private lateinit var img: String
    private lateinit var publishedAt: String
    private lateinit var author: String
    private lateinit var url: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = FragmentDetailNewsBinding.bind(view)

        arguments?.let {
            title = requireArguments().getString("title").toString()
            content = requireArguments().getString("content").toString()
            img = requireArguments().getString("img").toString()
            publishedAt = requireArguments().getString("publishedAt").toString()
            author = requireArguments().getString("author").toString()
            url = requireArguments().getString("url").toString()
        }

        binding.txtNewsTitle.text = title
        binding.txtAuthor.text = author
        binding.txtNewsContent.visibility = if (content == "null") View.GONE else {
            binding.txtNewsContent.text = content
            View.VISIBLE
        }
        binding.txtNewsDate.text = DateUtils.getFormattedDate(publishedAt)

        binding.txtNewsReadmore.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("url", url)
            findNavController().navigate(R.id.action_goto_newsource, bundle)
        }

        if (img == "null") {
            Glide.with(this)
                .load(activity?.let { ContextCompat.getDrawable(it, R.drawable.img_placeholder) })
                .into(binding.imgNews)
        } else {
            val uri: Uri = Uri.parse(img)
            Glide.with(this).load(uri)
                .into(binding.imgNews)
        }


    }

}