package com.example.patron_a_tive.calendar_module

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import com.example.patron_a_tive.R



// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PeriodFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PeriodFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        /*
        week_option.setOnClickListener {
            Toast.makeText(activity,"XYZ",Toast.LENGTH_SHORT).show()
        }

         */

        // Inflate the layout for this fragment

        return ComposeView(requireContext()).apply {
            setContent {
                PeriodFragmentLayout()
            }
        }

        //return inflater.inflate(R.layout.fragment_period, container, false)
    }


    @Composable
    fun PeriodFragmentLayout() {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(24.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    stringResource(R.string.choose_period),
                    style = TextStyle(
                        color = Color(0xff000000.toInt()),
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                val bColorWeekBtn = remember { mutableStateOf(0xff52bcff) }
                val bColorMonthBtn = remember { mutableStateOf(0xffffffff) }
                val txtColorWeekBtn = remember { mutableStateOf(0xffffffff) }
                val txtColorMonthBtn = remember { mutableStateOf(0xff000000) }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(bColorWeekBtn.value.toInt()))
                        .clickable(onClick = {
                            bColorWeekBtn.value = 0xff52bcff
                            bColorMonthBtn.value = 0xffffffff
                            txtColorWeekBtn.value = 0xffffffff
                            txtColorMonthBtn.value = 0xff000000
                        })
                ) {
                    Text(
                        stringResource(R.string.week),
                        modifier = Modifier.padding(8.dp),
                        style = TextStyle(
                            color = Color(txtColorWeekBtn.value.toInt()),
                            fontSize = 20.sp,
                        )
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(bColorMonthBtn.value.toInt()))
                        .clickable(onClick = {
                            bColorMonthBtn.value = 0xff52bcff
                            bColorWeekBtn.value = 0xffffffff
                            txtColorMonthBtn.value = 0xffffffff
                            txtColorWeekBtn.value = 0xff000000
                        })
                ) {
                    Text(
                        stringResource(R.string.month),
                        modifier = Modifier.padding(8.dp),
                        style = TextStyle(
                            color = Color(txtColorMonthBtn.value.toInt()),
                            fontSize = 20.sp,
                        )
                    )
                }

            }
            Column {
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xffcc4c80.toInt())),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(stringResource(R.string.accept),
                        style = TextStyle(fontSize = 20.sp, color = Color.White)
                    )
                }
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xff52bcff.toInt())),
                    modifier = Modifier.fillMaxWidth().padding(top = 24.dp)
                ) {
                    Text(
                        stringResource(R.string.go_back),
                        style = TextStyle(fontSize = 20.sp, color = Color.White)
                    )
                }
            }

        }
    }




    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PeriodFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PeriodFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}