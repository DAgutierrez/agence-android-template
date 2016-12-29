package com.agence.pharma_investi.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;


import com.agence.pharma_investi.R;
import com.agence.pharma_investi.utils.FormUtil;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * Created by diego on 31-10-16.
 */
public class AddTaskFragment  extends Fragment implements
        TimePickerDialog.OnTimeSetListener,
        DatePickerDialog.OnDateSetListener {

    Context context;
    AddTaskFragment _this;
    FragmentManager fm;

    AutoCompleteTextView txtCarPatent;
    TextView txtWorkToDo;
    TextView txtDescription;
    TextView txtHours;
    View inpStartDate;
    Button btnSave;


    String[] patentes = {"FRBS76 ","GHBS76","RHBS76","TYBS76","KFJF76"};
    ArrayAdapter<String> adapter;
    String startDate;
    SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
    SimpleDateFormat dateInputFormat = new SimpleDateFormat("dd ' de ' MMM ' del ' yyyy ' a las ' HH:mm");
    TextView inpStartDateText;
    TextView inpEndDateText;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = getActivity();
        _this =  this;
        fm = getActivity().getSupportFragmentManager();

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState)
    {

        return inflater.inflate(R.layout.fragment_add_task, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);




        // Car Patent

        adapter = new ArrayAdapter<>(context,
                android.R.layout.simple_list_item_1, patentes);

        txtCarPatent =  (AutoCompleteTextView) view.findViewById(R.id.txt_car_patent);
        txtCarPatent.setAdapter(adapter);
        txtCarPatent.setOnFocusChangeListener(FormUtil.validateRequiredField(context , view, txtCarPatent ));


        // Work to do
        txtWorkToDo =  (TextView) view.findViewById(R.id.txt_work_to_do);
//        txtWorkToDo.setOnFocusChangeListener(FormUtil.validateRequiredField(context, txtWorkToDo));

        inpStartDateText = (TextView) view.findViewById(R.id.inp_start_date_text);

        inpStartDate =   view.findViewById(R.id.inp_start_date);
        inpStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        _this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );

                dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");


            }
        });


        btnSave = (Button) view.findViewById(R.id.btn_save);
        btnSave.setEnabled(false);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("save!!!!");
            }
        });




    }


    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
        String startTime = hourOfDay+":"+minute;
        String startDateTimeString = startDate + " " + startTime;

        try {
            Date startDateTime = dateTimeFormat.parse(startDateTimeString);
            String startDateFormat = dateInputFormat.format(startDateTime);

            inpStartDateText.setText(startDateFormat);


        } catch (ParseException e) {
            e.printStackTrace();
        }

        System.out.println("");

//        timeTextView.setText(time);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
         startDate = dayOfMonth+"/"+(monthOfYear+1)+"/"+year;

        Calendar now = Calendar.getInstance();
        TimePickerDialog tpd = TimePickerDialog.newInstance(
                _this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                false
        );

        tpd.show(getActivity().getFragmentManager(), "Timepickerdialog");
        System.out.println("startDate " + startDate);
//        dateTextView.setText(date);
    }






//    private View.OnFocusChangeListener validateWorkTodo = new View.OnFocusChangeListener() {
//        @Override
//        public void onFocusChange(View view, boolean b) {
//
//            if(txtWorkToDo.getText().length() == 0) {
//                txtWorkToDo.setError(context.getString(R.string.required_field));
//            }
//
//        }
//    };


}
