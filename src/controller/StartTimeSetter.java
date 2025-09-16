package controller;

import raven.datetime.TimePicker;
import raven.datetime.event.TimeSelectionEvent;
import raven.datetime.event.TimeSelectionListener;

import static view.publicPanels.Constants.NUMBER_OF_TIMERS;

public class StartTimeSetter implements TimeSelectionListener {
    TimePicker[] timePickers;
    public StartTimeSetter(TimePicker[] timePickers) {
        if (timePickers == null) throw new NullPointerException("timePickers is null");
        if (timePickers.length != NUMBER_OF_TIMERS) throw new IllegalArgumentException("Number of timePickers must be " + NUMBER_OF_TIMERS);
        this.timePickers = timePickers;
    }
    
    @Override
    public void timeSelected(TimeSelectionEvent timeSelectionEvent) {
        TimePicker timePicker = (TimePicker) timeSelectionEvent.getSource();
        if (timePicker == timePickers[0]) model.Starttimes.setStarttime_1(timePicker.getSelectedTime());
        else if (timePicker == timePickers[1]) model.Starttimes.setStarttime_2(timePicker.getSelectedTime());
        else if (timePicker == timePickers[2]) model.Starttimes.setStarttime_3(timePicker.getSelectedTime());
    }
}
