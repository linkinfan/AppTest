package company.hulkbuster.flukeapp;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.commons.lang3.StringUtils;
import org.apmem.tools.layouts.FlowLayout;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    FlowLayout lin;
    EditText edText;
    ArrayList<String> array = new ArrayList<>();
    String bText;
    AlertDialog.Builder ad;
    EditText NameRandom;
    Button addButton;
    View AddHolder;
    int mCount = 0;

    public AddFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddFragment newInstance(String param1, String param2) {
        AddFragment fragment = new AddFragment();
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
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        AddHolder = inflater.inflate(R.layout.fragment_add, container, false);
        NameRandom = (EditText) AddHolder.findViewById(R.id.editText);
        edText = AddHolder.findViewById(R.id.textPut);
        addButton = AddHolder.findViewById(R.id.addButton);
        lin = AddHolder.findViewById(R.id.linEar);


        final AppDatabase db = Room.databaseBuilder(AddHolder.getContext(), AppDatabase.class, "prod")
                .allowMainThreadQueries()
                .build();
        edText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String stroka = edText.getText().toString();
                if ((StringUtils.countMatches(stroka, ",") == 1 | StringUtils.countMatches(stroka, ".") == 1) &
                        stroka.length() > 1 & stroka != "") {

                    final Button newB = new Button(AddHolder.getContext());
                    bText = edText.getText().toString().substring(0, edText.getText().length() - 1).toLowerCase();
                    array.add(bText);
                    mCount++;
                    newB.setId(mCount);
                    FlowLayout.LayoutParams param = new FlowLayout.LayoutParams(
                            FlowLayout.LayoutParams.WRAP_CONTENT,
                            FlowLayout.LayoutParams.WRAP_CONTENT);
                    newB.setLayoutParams(param);
                    newB.setText(bText);
                    lin.addView(newB);
                    edText.setText("");


                    newB.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override

                        public boolean onLongClick(View v) {

                            ad = new AlertDialog.Builder(AddHolder.getContext());
                            ad.setTitle(getString(R.string.AlertTitleDelete));
                            ad.setMessage(getString(R.string.MessageAlert));
                            ad.setPositiveButton(getString(R.string.Yes), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    lin.removeView(newB);
                                    array.remove(newB.getText());
                                    --mCount;
                                    Toast.makeText(AddHolder.getContext(), "Word was deleted", Toast.LENGTH_SHORT).show();
                                }

                            });
                            ad.setNegativeButton(getString(R.string.No), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });
                            AlertDialog alertDialog = ad.create();
                            alertDialog.show();
                            return true;
                        }
                    });

                  //  Already exist
                    backIS:
                    for (int i = 0; i < array.size(); i++) {
                        for (int j = 0; j < array.size(); j++) {
                            String a = array.get(i).toLowerCase();
                            String b = array.get(j).toLowerCase();
                            if (a.compareTo(b) == 0 & i != j) {
                                lin.removeView(newB);
                                array.remove(newB.getText());
                                --mCount;
                                Toast.makeText(AddHolder.getContext(), getString(R.string.toastFoundMatch), Toast.LENGTH_SHORT).show();
                                break backIS;
                            } else {
                            }
                        }
                    }

                } else if (StringUtils.countMatches(stroka, ",") > 1 | StringUtils.countMatches(stroka, ".") > 1 )

                {
                    edText.setText("");
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NameRandom.getText().toString() != null & mCount != 0) {
                    final AppDatabase db = Room.databaseBuilder(AddHolder.getContext(), AppDatabase.class, "production")
                            .allowMainThreadQueries()
                            .build();

                    String nameS = "", listS = "";
                    nameS = NameRandom.getText().toString();
                    for (int i = 0; i < array.size(); i++) {
                        listS += array.get(i) + " | ";
                    }
                    db.randomsDao().insertAll(new Randoms(nameS, listS));
                    View view = getActivity().findViewById(R.id.navigation_home);
                    view.performClick();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.container, new HomeFragment()).commit();
                    mCount = 0;
                }
                else { Toast.makeText(AddHolder.getContext(),R.string.fillFields,Toast.LENGTH_SHORT).show(); }
            }
        });
        return AddHolder;
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
