package com.example.myfirstapp.ui;
/**
 * ExpenditureFragment.jave
 * @author
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myfirstapp.R;

import java.util.Calendar;
import java.util.GregorianCalendar;
import com.example.myfirstapp.dbHelpers.DatabaseAccess;
import static com.example.myfirstapp.ui.MainActivity.thisUsername;


public class ExpenditureFragment extends Fragment {
    private DatabaseAccess databaseAccess;
    private TextView txt;
    private TextView tvMonthlyExpenditure;
    private Button btInputBudget;
    private float totalMonthlyExpenditure;
    private EditText etMonthlyBudget;
    private EditText etRemainingBudget;
    private TextView tvMonthlyBudget;
    private TextView tvRemainingBudget;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.databaseAccess= DatabaseAccess.getInstance(getContext());
        String months [] = {
                "January","February","March","April",
                "May","June","July","August",
                "September","October","November","December"};
        Calendar gCalendar = GregorianCalendar.getInstance();


        View view = inflater.inflate(R.layout.fragment_expenditure, container, false);
        etRemainingBudget = (EditText) view.findViewById(R.id.remain);
        etMonthlyBudget = (EditText) view.findViewById(R.id.budget);
        tvRemainingBudget = (EditText) view.findViewById(R.id.remainNum);
        tvMonthlyBudget = (EditText) view.findViewById(R.id.budgetNum);
        databaseAccess.open();
        float Budget = databaseAccess.getBudget(thisUsername);
        tvMonthlyBudget.setText(String.valueOf(Budget));
        btInputBudget = (Button) view.findViewById(R.id.inputBudget);

        btInputBudget.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                ((NavigationActivity)getActivity()).replaceThis(BudgetFragment.newInstance(),"Expenditure");
            }
        });

        txt = (TextView)view.findViewById(R.id.month);
        String currentMonth = String.valueOf(months[gCalendar.get(Calendar.MONTH)]);
        txt.setText(currentMonth);


        txt = (TextView)view.findViewById(R.id.year);
        String currentYear = String.valueOf((gCalendar.get(Calendar.YEAR)));
        txt.setText(currentYear);


        String month = "0";
        if (gCalendar.get(Calendar.MONTH)<10){
            month = (currentYear + "-" + "0" + Integer.toString(gCalendar.get(Calendar.MONTH)+1));
        }
        else{
            month = (currentYear + "-" + Integer.toString(gCalendar.get(Calendar.MONTH)+1));
        }
        totalMonthlyExpenditure = databaseAccess.getMonthlyExpenditure(month,thisUsername);
        tvMonthlyExpenditure = (TextView) view.findViewById(R.id.expenditure);
        tvMonthlyExpenditure.setText("$" + String.valueOf(totalMonthlyExpenditure));
        float remainingBudget = Budget - totalMonthlyExpenditure;
        tvRemainingBudget.setText(String.valueOf(remainingBudget));
        databaseAccess.close();
        return view;
    }

    /**
     *
     * @return
     */
    public static ExpenditureFragment newInstance() {
        return new ExpenditureFragment();
    }


}