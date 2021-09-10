package com.aditya.socialfeed.ui.dialog

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.aditya.socialfeed.application.SocialFeedApplication
import com.aditya.socialfeed.data.CardFeed
import com.aditya.socialfeed.databinding.DialogAddUrlBinding
import com.aditya.socialfeed.util.CommonUtil.enableStrictMode
import com.aditya.socialfeed.util.CommonUtil.getContentType
import com.aditya.socialfeed.viewmodel.CardFeedViewModelFactory
import com.aditya.socialfeed.viewmodel.FeedViewModel
import kotlin.random.Random


class AddUrlDialogFragment(private val listener: OnClickedListener) : DialogFragment() {

    val feedViewModel: FeedViewModel by viewModels {
        CardFeedViewModelFactory((activity?.application as SocialFeedApplication).repository)
    }

    private var _binding: DialogAddUrlBinding? = null
    private val binding get() = _binding!!

    companion object {
        const val TAG = "DialogAddUrl"

        fun newInstance(listener: OnClickedListener): DialogFragment {
            val args = Bundle()
            val fragment = AddUrlDialogFragment(listener)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogAddUrlBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        enableStrictMode()

        setupView()
        setupClickListeners()
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    private fun setupView() {
    }

    private fun setupClickListeners() {
        binding.btnPostAll.setOnClickListener {
            val feed = CardFeed(
                id = Random.nextLong(),
                cardUrl = binding.etInputUrl.text.toString(),
                cardType = getContentType(binding.etInputUrl.text.toString()) ?: ""
            )
            feedViewModel.insertCard(feed)
            dismiss()
        }

        binding.btnPaste.setOnClickListener {
            var clipboardManager: ClipboardManager =
                activity?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager;
            val pasteData: ClipData = clipboardManager.primaryClip!!
            val item = pasteData.getItemAt(0)
            binding.etInputUrl.setText(item.text.toString())
            binding.btnPaste.visibility = View.GONE
        }

        binding.btnClose.setOnClickListener {
            dismiss()
        }
    }

    interface OnClickedListener {
        fun onBtnStartClicked()
    }
}