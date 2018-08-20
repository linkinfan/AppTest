package company.hulkbuster.flukeapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SettingsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RadioGroup radioG;
    RadioButton radioBlue, radioRed, radioDark;
    Button color;
    View SettingsHolder;
    private SharedPreferences mSettings;

    public static final String APP_PREFERENCES = "Settings";

    public static final String APP_PREFERENCES_STYLE = "Style";

    private OnFragmentInteractionListener mListener;

    public SettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        SettingsHolder = inflater.inflate(R.layout.fragment_settings, container, false);
        radioG = SettingsHolder.findViewById(R.id.radioGroup);
        radioBlue = SettingsHolder.findViewById(R.id.radioBlue);
        radioRed = SettingsHolder.findViewById(R.id.radioRed);
        radioDark = SettingsHolder.findViewById(R.id.radioDark);
        color = SettingsHolder.findViewById(R.id.buttonColor);

        color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (radioG.getCheckedRadioButtonId()) {
                    case R.id.radioBlue:
                        mSettings = getActivity().getSharedPreferences(APP_PREFERENCES, getContext().MODE_PRIVATE);
                        SharedPreferences.Editor editor = mSettings.edit();
                        editor.putInt(APP_PREFERENCES_STYLE, R.style.AppTheme);
                        editor.apply();
                  //      Toast.makeText(getContext(), R.string.changeStyle + "to Blue Style" + R.string.pleaseRestart, Toast.LENGTH_LONG).show();
                        break;
                    case R.id.radioRed:
                        mSettings = getActivity().getSharedPreferences(APP_PREFERENCES, getContext().MODE_PRIVATE);
                        SharedPreferences.Editor editor2 = mSettings.edit();
                        editor2.putInt(APP_PREFERENCES_STYLE, R.style.RedTheme);
                    //    Toast.makeText(getContext(), R.string.changeStyle + "to Red Style." + R.string.pleaseRestart, Toast.LENGTH_LONG).show();
                        editor2.apply();
                        break;
                    case R.id.radioDark:
                        mSettings = getActivity().getSharedPreferences(APP_PREFERENCES, getContext().MODE_PRIVATE);
                        SharedPreferences.Editor editor3 = mSettings.edit();
                        editor3.putInt(APP_PREFERENCES_STYLE, R.style.DarkTheme);
                        editor3.apply();
                   //     Toast.makeText(getContext(), R.string.changeStyle + "to Dark Style" + R.string.pleaseRestart, Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });

        return SettingsHolder;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {


            mSettings = getActivity().getPreferences(Context.MODE_PRIVATE);
            int getStyle = mSettings.getInt(APP_PREFERENCES_STYLE,0);

            Log.d("STYLE!!!!!",String.valueOf(getStyle));
            if(getStyle==R.style.AppTheme)
            {
                radioBlue.setChecked(true);
            }
            else
            if(getStyle==R.style.RedTheme)
            {
                radioRed.setChecked(true);

            }
            else if(getStyle==R.style.DarkTheme)
            {
                radioDark.setChecked(true);
            }


        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
