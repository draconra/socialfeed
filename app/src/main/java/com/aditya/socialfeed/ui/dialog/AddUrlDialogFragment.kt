package com.aditya.socialfeed.ui.dialog

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.aditya.socialfeed.application.SocialFeedApplication
import com.aditya.socialfeed.data.CardFeed
import com.aditya.socialfeed.databinding.DialogAddUrlBinding
import com.aditya.socialfeed.ui.adapter.UrlAdapter
import com.aditya.socialfeed.util.CommonUtil.enableStrictMode
import com.aditya.socialfeed.util.CommonUtil.getContentType
import com.aditya.socialfeed.viewmodel.CardFeedViewModelFactory
import com.aditya.socialfeed.viewmodel.FeedViewModel
import kotlin.random.Random


class AddUrlDialogFragment(private val listener: OnClickedListener) : DialogFragment(),
    UrlAdapter.UrlAdapterListener {

    private val feedViewModel: FeedViewModel by viewModels {
        CardFeedViewModelFactory((activity?.application as SocialFeedApplication).repository)
    }

    private var adapter: UrlAdapter? = null
    private val modelList = ArrayList<CardFeed>()
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
        enableStrictMode()
        adapter = UrlAdapter(modelList, this)
        binding.rvUrl.adapter = adapter

        binding.etInputUrl.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (s.isEmpty()) {
                    binding.btnPaste.visibility = View.VISIBLE
                    if (modelList.isEmpty()) {
                        binding.btnAddMore.visibility = View.VISIBLE
                    }
                } else {
                    binding.btnPaste.visibility = View.GONE
                }
            }
        })
    }

    private fun setupClickListeners() {
        binding.btnPostAll.setOnClickListener {
            modelList.forEach {
                feedViewModel.insertCard(it)
            }
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

        binding.btnAddMore.setOnClickListener {
            val url = binding.etInputUrl.text.toString()
            modelList.add(CardFeed(Random.nextLong(), url, getContentType(url) ?: ""))
            if (modelList.size == 10) {
                binding.btnAddMore.visibility = View.GONE
            }
            adapter?.updateList(modelList)
        }
    }

    interface OnClickedListener {
        fun onBtnStartClicked()
    }

    override fun onEmptyObject() {
        binding.btnAddMore.visibility = View.VISIBLE
    }
}