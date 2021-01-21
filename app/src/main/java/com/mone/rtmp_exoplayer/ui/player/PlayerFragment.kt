package com.mone.rtmp_exoplayer.ui.player

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ext.rtmp.RtmpDataSourceFactory
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.mone.rtmp_exoplayer.databinding.FragmentPlayerBinding

class PlayerFragment : Fragment() {

    private lateinit var binding: FragmentPlayerBinding
    private lateinit var viewModel: PlayerViewModel
    private lateinit var player: SimpleExoPlayer
    private val url = "rtmp://192.168.1.52/LiveApp/test"
    // audio: rtmp://202.123.27.133:1935/bestfm/livestream

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlayerBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PlayerViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializePlayer()
        playVideo()
        player.playWhenReady = true
        player.prepare()
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

        player = SimpleExoPlayer.Builder(requireContext())
            .setTrackSelector(trackSelector)
            .setBandwidthMeter(DefaultBandwidthMeter.getSingletonInstance(requireContext()))
            .build()

        binding.simplePlayer.player = player
        binding.simplePlayer.setShowBuffering(PlayerView.SHOW_BUFFERING_ALWAYS)
        binding.simplePlayer.setPlaybackPreparer {
            player.prepare()
        }
    }

    /**
     * Play RTMP
     */
    private fun playVideo() {
        val videoSource = ProgressiveMediaSource.Factory(RtmpDataSourceFactory())
            .createMediaSource(MediaItem.fromUri(Uri.parse(url)))
        player.setMediaSource(videoSource)
    }


    companion object {
        fun newInstance() = PlayerFragment()
    }
}