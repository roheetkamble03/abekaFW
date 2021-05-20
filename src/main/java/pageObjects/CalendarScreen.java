package pageObjects;

import base.GenericAction;
import com.codeborne.selenide.SelenideElement;
import constants.Calendar;
import constants.TableColumn;
import constants.DataBaseQueryConstant;
import edu.emory.mathcs.backport.java.util.Arrays;
import io.qameta.allure.Step;

import java.util.*;

import static constants.Calendar.*;
import static constants.CommonConstants.AD_DATA_BASE;

public class CalendarScreen extends GenericAction {

    private static HashMap<String,String> allDayPositions = new HashMap<>();

    public CalendarScreen selectStudent(String studentName){
        tempXpath = String.format(Calendar.studentSelectionCheckBox,studentName);
        bringElementIntoView(tempXpath);
        click(tempXpath);
        waitForPageTobLoaded();
        return this;
    }

    public CalendarScreen validateStudentCalendarEvents(boolean isOpenAndValidateEachEventPreview) {
        if(isElementExists(Calendar.calendarSection)){
            bringElementIntoView(getElements(Calendar.CALENDAR_MONTH_DAYS).get(0));
            Map<String, TreeSet<String>> groupedSubjectAsPerDate = getGroupedByCalendarData();
            validateCalendarEvents(groupedSubjectAsPerDate, isOpenAndValidateEachEventPreview);
        }else {
            softAssertions.fail("Calendar section not appeared for selected student");
        }
        return this;
    }

    private Map<String, TreeSet<String>> getGroupedByCalendarData(){
        String studentId = "427725";//userAccountDetails.get(TableColumn.STUDENT_ID);
        String accountNumber = "27579418";//userAccountDetails.get(TableColumn.ACCOUNT_NUMBER);
        String startDate = getFormattedDate(bringElementIntoView(getElements(Calendar.CALENDAR_MONTH_DAYS).get(0)).getAttribute(DATA_DATE_ATTRIBUTE),Calendar.yyyy_MM_dd,Calendar.yyyyMMdd);
        String endDate = getFormattedDate(bringElementIntoView(getElements(Calendar.CALENDAR_MONTH_DAYS).get(getElements(Calendar.CALENDAR_MONTH_DAYS).size()-1)).getAttribute("data-date"),"yyyy-MM-dd","yyyyMMdd");

        ArrayList<HashMap<String,String>> calendarEventsDBResult = executeAndGetSelectQueryData(DataBaseQueryConstant.MY_TO_DO_LIST_AD_DB
                .replaceAll(TableColumn.STUDENT_ID,studentId)
                .replaceAll(TableColumn.ACCOUNT_NUMBER,accountNumber)
                .replaceAll(TableColumn.START_DATE,startDate)
                .replaceAll(TableColumn.END_DATE,endDate), AD_DATA_BASE);
        ArrayList<String> columnTobeMerged = new ArrayList<String>(Arrays.asList(new String[]{TableColumn.SHORT_DESCRIPTION, TableColumn.LONG_DESCRIPTION}));
        return getGroupedDataAccordingToColumn(TableColumn.START_DATE, calendarEventsDBResult, columnTobeMerged, "|");
    }

    private void validateCalendarEvents(Map<String, TreeSet<String>> groupedSubjectAsPerDate, boolean isOpenAndValidateEachEventPreview){
        int newRowCounter = groupedSubjectAsPerDate.size()/5;
        ArrayList<String> dayTasksList;
        int rowCounter = 1;
        int taskSize;
        String dayPosition;
        String monthDay;
        setDayPositionInRow(groupedSubjectAsPerDate.size()/5);

        for(String date:groupedSubjectAsPerDate.keySet()){
            monthDay = getTaskBoxDay(date);
            dayPosition = allDayPositions.get(date);
            newRowCounter = (rowCounter==newRowCounter)?1:rowCounter;
            dayTasksList = new ArrayList<>(groupedSubjectAsPerDate.get(date));
            taskSize = dayTasksList.size();

            softAssertions.assertThat(getElementText(String.format(Calendar.taskGridDay,date)).equals(getTaskBoxDay(date)))
                    .as("Expected task grid day ["+getTaskBoxDay(date)+" is not equal to actual ["+getElementText(String.format(Calendar.taskGridDay,date)+"]")).isTrue();


            switch (getDayTaskGridType(taskSize)){
                case ONLY_ONE_TASK:
                    if (isOpenAndValidateEachEventPreview) {
                        openAndValidateEventDetailsPopUp(rowCounter, 1, dayPosition, date,dayTasksList.get(0));
                    }else {
                        validateGridText(rowCounter, 1, dayPosition, date, dayTasksList.get(0));
                        validateIsMoreEventLinkIsNotPresent(rowCounter, dayPosition, monthDay);
                    }
                    break;
                case ONLY_TWO_TASK:
                    if(isOpenAndValidateEachEventPreview) {
                        openAndValidateEventDetailsPopUp(rowCounter, 1, dayPosition, date,dayTasksList.get(0));
                        openAndValidateEventDetailsPopUp(rowCounter, 2, dayPosition, date,dayTasksList.get(0));
                    }else {
                        validateGridText(rowCounter, 1, dayPosition, date, dayTasksList.get(0));
                        validateGridText(rowCounter, 2, dayPosition, date, dayTasksList.get(1));
                        validateIsMoreEventLinkIsNotPresent(rowCounter, dayPosition, monthDay);
                    }
                    break;
                case MULTIPLE_TASK:
                    openAndValidateEventDetailsPopUp(rowCounter, 1, dayPosition, date,dayTasksList.get(0));
                    openAndValidateEventDetailsPopUp(rowCounter, 2, dayPosition, date,dayTasksList.get(0));
                    tempXpath = String.format(Calendar.moreEventsLink,rowCounter,dayPosition);
                    if(isElementExists(tempXpath)){
                        tempText = String.format(Calendar.MORE_EVENTS_TEXT,dayTasksList.size()-2);
                        softAssertions.assertThat(getElementText(tempXpath).equals(tempText))
                            .as("More event link text is not matching \nExpected:"+tempText+"\nActual:"+getElementText(tempXpath)).isTrue();
                        click(tempXpath);
                        validateMoreEventTaskList(date,dayTasksList,isOpenAndValidateEachEventPreview);
                    }else {
                        softAssertions.fail(monthDay+" is not having more events link though it is having multiple events.");
                    }
                    break;
                default:
                    softAssertions.fail("Database doesn't return any data.");
                rowCounter++;
            }
        }
    }

    private void openAndValidateEventDetailsPopUp(int rowCounter, int gridPosition, String dayPosition, String date, String textToCompare){
        tempXpath = getElementText(String.format(Calendar.taskGridText,rowCounter,gridPosition,dayPosition));
        if(isElementExists(tempXpath)){
            click(tempXpath);
            validateEventPreviewPopUp(date,textToCompare);
        }else{
            softAssertions.fail(date+" day's ["+ gridPosition+"] grid is not present.");
        }
    }

    private void validateEventPreviewPopUp(String date, String textToCompare){
        date = getFormattedDate(date,yyyy_MM_dd,MMMddyyyy);
        String[] compareTextArray = textToCompare.split("|");
        softAssertions.assertThat(getElementText(Calendar.eventPreviewHeader).equals(compareTextArray[0]))
                .as(date+" day event's preview header is not matching \nActual:"+getElementText(eventPreviewHeader)+"\nExpected:"+compareTextArray[0]).isTrue();
        softAssertions.assertThat(getElementText(Calendar.eventPreviewDate).equals(date))
                .as(date+" day event's preview date is not matching \nActual:"+getElementText(eventPreviewDate)+"\nExpected:"+date).isTrue();
        softAssertions.assertThat(getElementText(Calendar.eventPreviewDescription).equals(compareTextArray[1]))
                .as(date+" day event's preview description is not matching \nActual:"+getElementText(eventPreviewDescription)+"\nExpected:"+compareTextArray[1]).isTrue();
        click(Calendar.eventPreviewCloseBtn);
    }

    private ArrayList<String> getEventListFromMoreEventsPopup(){
        ArrayList<String> taskList = new ArrayList<>();
        for(SelenideElement element:getElements(Calendar.moreEventsPopupTasksText)){
            taskList.add(element.getText());
        }
        return taskList;
    }

    private void validateMoreEventTaskList(String date,ArrayList<String> dayTasksList,boolean isOpenAndValidateEachEventPreview){
        tempXpath = Calendar.moreEventsPopupTasksText;
        List<SelenideElement> eventList = getElements(tempXpath);
        if(isOpenAndValidateEachEventPreview){
            for(int i=0;i<eventList.size();i++){
                click(getElements(tempXpath).get(i));
                validateEventPreviewPopUp(date, dayTasksList.get(i));
            }
        }else {
            tempText = getFormattedDate(date, yyyy_MM_dd, weekDayMonthDayOfMonth);
            softAssertions.assertThat(getElementText(Calendar.moreEventsPopUpHeader).equals(tempText))
                    .as("More events pop-up header's expected value ["+tempText+"] is not matching with actual header text ["+getElementText(Calendar.moreEventsPopUpHeader)+"]").isTrue();
            softAssertions.assertThat(getEventListFromMoreEventsPopup().equals(dayTasksList))
                    .as("More events expected event list ["+dayTasksList.toString()+"] is not equal to actual event list["+ getEventListFromMoreEventsPopup()+"]").isTrue();
        }
        click(Calendar.moreEventPopupCloseBtn);
    }

    @Step(" Setting date position in map")
    private void setDayPositionInRow(int totalRows){
        for(int rowNumber=1;rowNumber<=totalRows;rowNumber++){
            for(int position=1;position <= getElements(String.format(Calendar.dayPositionInRow,rowNumber)).size();position++){
                allDayPositions.put(getFormattedDate(getElements(String.format(Calendar.dayPositionInRow,rowNumber)).get(position).getAttribute(Calendar.DATA_DATE_ATTRIBUTE),Calendar.yyyy_MM_dd,Calendar.yyyyMMdd),Integer.toString(position));
            }
        }
    }

    private void validateGridText(int rowCounter,int gridPosition, String dayPosition, String monthDay, String compareText){
            String gridText = getElementText(String.format(Calendar.taskGridText,rowCounter,gridPosition,dayPosition));
            softAssertions.assertThat(gridText.equals(compareText))
                    .as(monthDay+" day's expected task's grid ["+gridPosition+"] expect text ["+compareText+"] is not equal to actual grid text ["+gridText+"]").isTrue();
    }

    private void validateIsMoreEventLinkIsNotPresent(int rowCounter, String dayPosition, String monthDay){
        softAssertions.assertThat(isElementExists(String.format(Calendar.moreEventsLink,rowCounter,dayPosition)))
                .as(monthDay+" is having more events link though it is having only which it should not.").isFalse();
    }

    private String getTaskBoxDay(String date){
        int dateLength = date.length();
        return (date.substring(dateLength-1)=="0")?date.substring(dateLength):date.substring(dateLength-2,dateLength);
    }

    private String getDayTaskGridType(int taskSize){
        switch (taskSize){
            case 1:
                return ONLY_ONE_TASK;
            case 2:
                return ONLY_TWO_TASK;
            default:
                return MULTIPLE_TASK;
        }
    }
}
