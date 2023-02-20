package com.example.sleeptimer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.sleeptimer.databinding.FragmentFirstBinding
import android.content.Context
import android.media.AudioManager
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.sleeptimer.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    lateinit var startTimerButton: Button
    lateinit var cancelTimerButton: Button
    var job:Job = GlobalScope.launch{}
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)

        val numberPicker: NumberPicker = binding.root.findViewById(R.id.NumberPicker)
        numberPicker.minValue = 1
        numberPicker.maxValue = 1000

        startTimerButton = binding.root.findViewById(R.id.StartTimer)
        startTimerButton.setOnClickListener{
            job = CoroutineScope(Dispatchers.Main).launch{
                delay(numberPicker.value.toLong() * 1000)
                val audioManager = requireActivity().getSystemService(Context.AUDIO_SERVICE) as AudioManager
                val result: Int = audioManager.requestAudioFocus( null, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN )
                Toast.makeText(activity, "Sleepy Time", Toast.LENGTH_SHORT).show()
            }

        }

        cancelTimerButton = binding.root.findViewById(R.id.CancelTimer)
        cancelTimerButton.setOnClickListener{
            job.cancel()
            Toast.makeText(activity, "Sleep Timer Cancelled", Toast.LENGTH_SHORT).show()
        }

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /* binding.buttonFirst.setOnClickListener {
             findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
         }*/
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}