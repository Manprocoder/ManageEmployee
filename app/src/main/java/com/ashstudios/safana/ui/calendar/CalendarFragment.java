package com.ashstudios.safana.ui.calendar;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.ashstudios.safana.R;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class CalendarFragment extends Fragment{

    private TextView monthYearText;
    private Button prev_button, next_button;
    RecyclerView calendarRecyclerView;
    TaskCalendarAdapter taskCalendarAdapter;
    private LocalDate selectedDate;
    DayModel dayModel;
    ArrayList<DayModel> daysInMonth;
    private static  final String TAG = "CALENDAR_FRAGMENT";

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_calendar, container, false);
        daysInMonth = new ArrayList<>();

        calendarRecyclerView = root.findViewById(R.id.calendarRecyclerView);
        monthYearText = root.findViewById(R.id.monthYearTV);
        selectedDate = LocalDate.now();
        setMonthView();

        prev_button = root.findViewById(R.id.previous);
        prev_button.setOnClickListener(v -> {
            previousMonthAction();
        });

        next_button = root.findViewById(R.id.next);
        next_button.setOnClickListener(v -> {
            nextMonthAction();
        });

        /*RecyclerView recyclerView = root.findViewById(R.id.rv_calendar_task);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //set the adapter
        TaskAdapter taskAdapter = new TaskAdapter(getActivity(), calendarViewModel.getArrayListMutableLiveData());
        recyclerView.setAdapter(taskAdapter);*/
        return root;
    }



    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_settings).setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    private void setMonthView() {
        monthYearText.setText(monthYearFromDate(selectedDate));
        daysInMonth = daysInMonthArray(selectedDate);
        //Log.d(TAG, String.valueOf(daysInMonth.size()));

        for(DayModel value : daysInMonth){  //debug
            Log.d(TAG, value.getDay());
        }

        calendarRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 7, GridLayoutManager.VERTICAL , false));
        taskCalendarAdapter = new TaskCalendarAdapter(getActivity(), daysInMonth);
        calendarRecyclerView.setAdapter(taskCalendarAdapter);
    }

    private ArrayList<DayModel> daysInMonthArray(LocalDate date) {
        ArrayList<DayModel> daysInMonthArray = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);
        int daysInMonth = yearMonth.lengthOfMonth();

        LocalDate firstOfMonth = selectedDate.withDayOfMonth(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();
        String year = String.valueOf(date.getYear());
        String month = String.valueOf(date.getMonthValue());

        for(int i = 1; i <= 42; i++) {
            if(i <= dayOfWeek || i > daysInMonth + dayOfWeek) {
                dayModel = new DayModel(" ", month, year);
                daysInMonthArray.add(dayModel);

            }
            else {
                String day = String.valueOf(i- dayOfWeek);
                dayModel = new DayModel(day, month, year);
                daysInMonthArray.add(dayModel);
            }
        }
        return  daysInMonthArray;
    }

    private String monthYearFromDate(LocalDate date)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);
    }

    public void previousMonthAction()
    {
        selectedDate = selectedDate.minusMonths(1);
        setMonthView();
    }

    public void nextMonthAction()
    {
        selectedDate = selectedDate.plusMonths(1);
        setMonthView();
    }
}