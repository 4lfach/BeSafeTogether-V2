package com.selftutor.besafetogether.screens.home

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import com.selftutor.besafetogether.R
import com.selftutor.besafetogether.databinding.FragmentHomeBinding
import com.selftutor.besafetogether.model.database.stopwords.StopWord
import com.selftutor.besafetogether.screens.BaseFragment
import com.selftutor.besafetogether.screens.factory


class HomeFragment : BaseFragment(), RecognitionListener {

    private val viewModel: HomeViewModel by viewModels { factory() }
    private lateinit var binding: FragmentHomeBinding

    private lateinit var locationManager: LocationManager

    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    private var buttonCounter = 0

    private var speechRecognizer: SpeechRecognizer? = null
    private lateinit var recognizerIntent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        permissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){}

        locationManager = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        val context = requireContext()
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)
        Log.i(TAG, "isRecognitionAvailable: " + SpeechRecognizer.isRecognitionAvailable(context))
        speechRecognizer!!.setRecognitionListener(this)

        recognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "US-en")
        recognizerIntent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        permissionLauncher.launch(permissions)

        with(binding) {

            gpsOnButton.setOnClickListener {
                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    buildAlertMessageNoGps()
                    return@setOnClickListener
                }

                viewModel?.onGpsPermissionGranted(true)
            }
            setContactsButton.setOnClickListener {
                navigate(R.id.action_homeFragment_to_contactsFragment)
            }
            setStopWordsButton.setOnClickListener {
                navigate(R.id.action_homeFragment_to_profileFragment)
            }
            startScanButton.setOnClickListener {
                startScanButton()
            }
        }

        return binding.root
    }

    private fun buildAlertMessageNoGps() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
            .setCancelable(false)
            .setPositiveButton("Yes") { _, _ -> startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)) }
            .setNegativeButton("No") { dialog, _ -> dialog.cancel() }
        val alert: AlertDialog = builder.create()
        alert.show()
    }

    private fun checkPermissions(): Boolean {
        var counter = 0
        for (permission in permissions) {
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    permission
                ) == PackageManager.PERMISSION_GRANTED
            )
                counter++
        }
        if (counter == permissions.size)
            return true

        return false
    }

    private fun startScanButton() {
        if (checkPermissions()) {
            if (buttonCounter % 2 == 0) {
                startScanUi()
                speechRecognizer!!.startListening(recognizerIntent)
            } else {
                stopScanUi()
                speechRecognizer!!.stopListening()
            }
            buttonCounter++
        } else {
            showToast(R.string.permission_status_blocked)
        }
    }

    private fun stopScanUi() {
        with(binding) {
            startScanButton.text = resources.getString(R.string.start_scanning)
            progressBar.visibility = View.GONE
            progressBar.isIndeterminate = false
            requirementsCard.visibility = View.VISIBLE
            requirementsCard.visibility = View.VISIBLE
            welcomeTextView.visibility = View.VISIBLE
            detailTextView.visibility = View.VISIBLE
        }
    }

    private fun startScanUi() {
        with(binding) {
            startScanButton.text = resources.getString(R.string.scanning)
            progressBar.visibility = View.VISIBLE
            progressBar.isIndeterminate = true
            requirementsCard.visibility = View.GONE
            welcomeTextView.visibility = View.GONE
            detailTextView.visibility = View.GONE
        }
    }

    override fun onStop() {
        super.onStop()
        releaseRecognizer()
        Log.i(TAG, "destroy")
    }

    override fun onBeginningOfSpeech() {
        Log.i(TAG, "onBeginningOfSpeech")
        binding.progressBar.isIndeterminate = false
        binding.progressBar.max = 10
    }

    override fun onRmsChanged(rmsdB: Float) {
        binding.progressBar.progress = rmsdB.toInt()
    }

    override fun onEndOfSpeech() {
        stopScanUi()
    }

    override fun onError(error: Int) {
        val errorMessage: String = getErrorText(error)
        Log.d(TAG, "FAILED $errorMessage")
        binding.resultsTextView.text = errorMessage
        stopScanUi()
    }

    override fun onResults(results: Bundle?) {
        Log.i(TAG, "onResults")
        val matches = results!!.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
        var text = ""

        if (matches != null) {
            for (result in matches) text = result
        }
        binding.resultsTextView.text = text
        val speech = text
        val stopWords = viewModel.stopWords.value

        if (stopWords != null) {
            findStopWord(speech, stopWords)
        }
    }

    private fun findStopWord(speech: String, stopWords: List<StopWord>) {
        val speech = speech.split(" ")
        for (word in stopWords) {
            if (word.word in speech) {
                showToast("$word was found")
            }
        }
    }
    private fun releaseRecognizer() {
        try {
            if (null != speechRecognizer) {
                speechRecognizer!!.destroy()
                speechRecognizer = null
            }
        } catch (e: IllegalArgumentException) { // fix Service not registered: android.speech.SpeechRecognizer$Connection
            e.printStackTrace()
            Log.e(TAG, e.message!!)
        }
    }

    private fun getErrorText(error: Int): String {
        var message = ""
        message = when (error) {
            SpeechRecognizer.ERROR_AUDIO -> "Audio recording error"
            SpeechRecognizer.ERROR_CLIENT -> "Client side error"
            SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> "Insufficient permissions"
            SpeechRecognizer.ERROR_NETWORK -> "Network error"
            SpeechRecognizer.ERROR_NETWORK_TIMEOUT -> "Network timeout"
            SpeechRecognizer.ERROR_NO_MATCH -> "No match"
            SpeechRecognizer.ERROR_RECOGNIZER_BUSY -> "RecognitionService busy"
            SpeechRecognizer.ERROR_SERVER -> "error from server"
            SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> "No speech input"
            else -> "Didn't understand, please try again."
        }
        return message
    }

    override fun onReadyForSpeech(params: Bundle?) {}
    override fun onBufferReceived(buffer: ByteArray?) {}
    override fun onPartialResults(partialResults: Bundle?) {}
    override fun onEvent(eventType: Int, params: Bundle?) {}

    companion object {
        const val TAG = "HomeFragment"
        val permissions = arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.RECORD_AUDIO
        )
    }
}