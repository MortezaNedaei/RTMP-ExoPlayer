package com.mone.rtmp_exoplayer.ui.player

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ext.rtmp.RtmpDataSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.mone.rtmp_exoplayer.databinding.FragmentPlayerBinding

class PlayerFragment : Fragment() {

    private lateinit var binding: FragmentPlayerBinding
    private val viewModel by viewModels<PlayerViewModel>()
    private lateinit var exoPlayer: ExoPlayer
    private lateinit var url: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        url = arguments?.getString("url") ?: ""
        binding = FragmentPlayerBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        exoPlayer.stop()
        exoPlayer.release()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializePlayer()
        playVideo()
        exoPlayer.playWhenReady = true
        exoPlayer.prepare()
    }

    /**
     * setup ExoPlayer
     */
    private fun initializePlayer() {

        val videoTrackSelectionFactory = AdaptiveTrackSelection.Factory()

        val trackSelector = DefaultTrackSelector(
            requireContext(),
            videoTrackSelectionFactory
        )
        trackSelector.setParameters(trackSelector.buildUponParameters().setMaxVideoSizeSd())

        exoPlayer = ExoPlayer.Builder(requireContext())
            .setTrackSelector(trackSelector)
            .setBandwidthMeter(DefaultBandwidthMeter.getSingletonInstance(requireContext()))
            .build()

        binding.styledPlayer.player = exoPlayer
        binding.styledPlayer.setShowBuffering(StyledPlayerView.SHOW_BUFFERING_ALWAYS)
    }

    /**
     * Play RTMP
     */
    private fun playVideo() {
        val videoSource = ProgressiveMediaSource.Factory(RtmpDataSource.Factory())
            .createMediaSource(MediaItem.fromUri(Uri.parse(url)))
        exoPlayer.setMediaSource(videoSource)
    }


    companion object {
        fun newInstance() = PlayerFragment()
    }
}