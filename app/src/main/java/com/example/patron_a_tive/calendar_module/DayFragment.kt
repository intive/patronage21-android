package com.example.patron_a_tive.calendar_module

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.patron_a_tive.R
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DayFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DayFragment : Fragment() {
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

        return ComposeView(requireContext()).apply {
            setContent {
                DayFragmentLayout(findNavController())
            }
        }
    }

    @Composable
    fun DayFragmentLayout(navController: NavController? = null) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(24.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    "Piątek, 05.03.2021",
                    style = TextStyle(
                        color = Color(0xff52bcff.toInt()),
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                Text(
                    "Retrospective",
                    style = TextStyle(
                        color = Color(0xff000000.toInt()),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    "Godzina: 15:00-16:00",
                    style = TextStyle(
                        color = Color(0xff000000.toInt()),
                        fontSize = 20.sp
                    ),
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                Row(
                    horizontalArrangement =Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xffeef8fe.toInt()))
                        .padding(12.dp)
                ) {
                    Text(
                        "Uczestnicy",
                        style = TextStyle(
                            color = Color(0xff52bcff.toInt()),
                            fontSize = 18.sp
                        )
                    )

                    Text(
                        "5",
                        style = TextStyle(
                            color = Color(0xff52bcff.toInt()),
                            fontSize = 18.sp
                        )
                    )
                }

                UsersList()

            }

            Column {
                Button(
                    onClick = {
                        navController?.navigate(R.id.action_dayFragment_to_calendarFragment)
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xffcc4c80.toInt())),
                    modifier = Modifier.fillMaxWidth().padding(top = 12.dp)
                ) {
                    Text(
                        stringResource(R.string.accept_event),
                        style = TextStyle(fontSize = 20.sp, color = Color.White)
                    )
                }
                Button(
                    onClick = {
                        navController?.navigate(R.id.action_dayFragment_to_calendarFragment)
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xff52bcff.toInt())),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp)
                ) {
                    Text(
                        stringResource(R.string.reject_event),
                        style = TextStyle(fontSize = 20.sp, color = Color.White)
                    )
                }
            }

        }
    }

    @Composable
    fun UsersList(){
        val scrollState = rememberLazyListState()

        LazyColumn(state = scrollState) {
            items(10) {
                UsersListItem(it)
            }
        }
    }

    @Composable
    fun UsersListItem(index: Int) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().padding(8.dp)) {
            Spacer(Modifier.width(10.dp))
            Text("Uczestnik $index", style = TextStyle(fontSize = 18.sp))
            //Text("Organizator", style = MaterialTheme.typography.subtitle1)
        }
        Divider(color = Color.LightGray)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DayFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DayFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun findNavController(): NavController {
        val navHostFragment =
            (activity as FragmentActivity).supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        return navHostFragment.navController
    }
}