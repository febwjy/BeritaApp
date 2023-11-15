package id.febwjy.beritaapp.ui.detailnews

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import id.febwjy.beritaapp.R
import id.febwjy.beritaapp.databinding.FragmentWebViewBinding
import id.febwjy.beritaapp.ui.extension.showToast


/**
 * Created by Febby Wijaya on 15/11/2023.
 */
@AndroidEntryPoint
class WebViewer : Fragment(R.layout.fragment_web_view) {

    private var mBinding: FragmentWebViewBinding? = null
    private val binding get() = mBinding!!

    private lateinit var url: String
    private val handler: Handler = Handler()
    private var progressStatus = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = FragmentWebViewBinding.bind(view)

        arguments?.let {
            url = requireArguments().getString("url").toString()
        }

        Thread {
            while (progressStatus < 100) {
                progressStatus += 1

                try {
                    Thread.sleep(80)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }

                handler.post(Runnable {
                    binding.pb.progress = progressStatus
                    if (progressStatus == 100) {
                        requireActivity().showToast("Loading Complete")
                        binding.webView.visibility = View.VISIBLE
                        displayWebView()
                    }
                })
            }
        }.start()
    }

    @SuppressLint("SetJavaScriptEnabled")
    fun displayWebView() {
        binding.webView.settings.useWideViewPort = true
        binding.webView.settings.loadWithOverviewMode = true
        binding.webView.settings.setSupportZoom(true)
        binding. webView.settings.builtInZoomControls = true
        binding.webView.settings.displayZoomControls = false
        binding.webView.loadUrl(url)
        // this will enable the javascipt.
        binding.webView.settings.javaScriptEnabled = true

        // WebViewClient allows you to handle
        // onPageFinished and override Url loading.
        binding.webView.webViewClient = WebViewClient()
    }
}