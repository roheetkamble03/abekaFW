package pageObjects;

import base.GenericAction;
import com.codeborne.selenide.SelenideElement;
import constants.Calendar;
import constants.TableColumn;
import constants.DataBaseQueryConstant;
import edu.emory.mathcs.backport.java.util.Arrays;
import io.qameta.allure.Step;
import org.apache.commons.lang.StringUtils;

import java.util.*;

import static constants.Calendar.*;
import static constants.CommonConstants.AD_DATA_BASE;

public class CalendarScreen extends GenericAction {

    private static HashMap<String,String> allDayPositions = new HashMap<>();

    public CalendarScreen selectStudent(String studentName){
        waitForPageTobLoaded();
        tempXpath = String.format(Calendar.studentSelectionCheckBox,studentName);
        bringElementIntoView(tempXpath);
        click(tempXpath);
        waitForPageTobLoaded();
        return this;
    }

    @Step("Validating calendar events")
    public CalendarScreen validateStudentCalendarEvents(boolean isOpenAndValidateEachEventPreview) {
        if(isElementExists(Calendar.calendarSection)){
            bringElementIntoView(getElements(Calendar.CALENDAR_MONTH_DAYS).get(0));
            validateCalendarEvents(getCalendarDataFromDB(), isOpenAndValidateEachEventPreview);
        }else {
            softAssertions.fail("Calendar section not appeared for selected student");
        }
        return this;
    }

    @Step("Validating by default no student is selected")
    public CalendarScreen validateNoStudentIsSelected(){
        //checkbox property is not changing in dom showing checked though check box is not selected
        return this;
    }

    private  ArrayList<HashMap<String,String>> getCalendarDataFromDB(){
        String studentId = "427725";//userAccountDetails.get(TableColumn.STUDENT_ID);
        String accountNumber = "27579418";//userAccountDetails.get(TableColumn.ACCOUNT_NUMBER);
        String startDate = getFormattedDate(bringElementIntoView(getElements(Calendar.CALENDAR_MONTH_DAYS).get(0)).getAttribute(DATA_DATE_ATTRIBUTE),Calendar.yyyy_MM_dd,Calendar.yyyyMMdd);
        String endDate = getFormattedDate(bringElementIntoView(getElements(Calendar.CALENDAR_MONTH_DAYS).get(getElements(Calendar.CALENDAR_MONTH_DAYS).size()-1)).getAttribute("data-date"),"yyyy-MM-dd","yyyyMMdd");

        return executeAndGetSelectQueryData(DataBaseQueryConstant.MY_TO_DO_LIST_AD_DB
                .replaceAll(TableColumn.STUDENT_ID_DATA,studentId)
                .replaceAll(TableColumn.ACCOUNT_NUMBER_DATA,accountNumber)
                .replaceAll(TableColumn.START_DATE_DATA,startDate)
                .replaceAll(TableColumn.END_DATE_DATA,endDate), AD_DATA_BASE);
    }

    private void validateCalendarEvents(ArrayList<HashMap<String,String>> calendarEventsDBResult, boolean isOpenAndValidateEachEventPreview){

        Map<String, ArrayList<String>> eventGridTextData = getGroupedDataAccordingToColumn(TableColumn.START_DATE, calendarEventsDBResult, new ArrayList<String>(Arrays.asList
                (new String[]{TableColumn.SUBJECT, TableColumn.DESCRIPTION})), Calendar.PIPE_SPLITTER);
        Map<String, ArrayList<String>> eventPreviewData = getGroupedDataAccordingToColumn(TableColumn.START_DATE, calendarEventsDBResult, new ArrayList<String>(Arrays.asList
                (new String[]{TableColumn.SUBJECT, TableColumn.LONG_DESCRIPTION})), PIPE_SPLITTER);

        int rowCounter = 1;
        int taskSize;
        int dayPosition;
        int cellCounter = 1;
        String date;
        String monthDay;
        ArrayList<String> eventTextTaskList;
        ArrayList<String> eventPreviewTaskList;
        setDayPositionInRow(eventGridTextData.size()/ TOTAL_STUDY_DAYS);
        ArrayList<String> keySet = new ArrayList<>(eventGridTextData.keySet());

        for(int i=0;i<eventGridTextData.size();i++){
            date = keySet.get(i);
            log("Executing validation for date:"+date);
            monthDay = getTaskBoxDay(date);
            dayPosition = Integer.parseInt(allDayPositions.get(date))-1;
            eventTextTaskList = eventGridTextData.get(date);
            eventPreviewTaskList = eventPreviewData.get(date);
//            Collections.sort(eventTextTaskList);
//            Collections.sort(eventPreviewTaskList);
            taskSize = eventTextTaskList.size();

            tempXpath = String.format(Calendar.taskGridDay,date);
            bringElementIntoView(tempXpath);
            softAssertions.assertThat(getElementText(tempXpath).equals(getTaskBoxDay(date)))
                    .as(date+":Expected task grid day box's day ["+getTaskBoxDay(date)+"] is not equal to actual ["+getElementText(tempXpath+"]")).isTrue();


            switch (getDayTaskGridType(taskSize)){
                case ONLY_ONE_TASK:
                    if (isOpenAndValidateEachEventPreview) {
                        openAndValidateEventDetailsPopUp(rowCounter, 1, dayPosition, date,eventPreviewTaskList.get(0));
                    }else {
                        validateGridText(rowCounter, 1, dayPosition, date, eventTextTaskList.get(0));
                        validateIsMoreEventLinkIsNotPresent(rowCounter, dayPosition, monthDay);
                    }
                    break;
                case ONLY_TWO_TASK:
                    if(isOpenAndValidateEachEventPreview) {
                        openAndValidateEventDetailsPopUp(rowCounter, 1, dayPosition, date,eventPreviewTaskList.get(0));
                        openAndValidateEventDetailsPopUp(rowCounter, 2, dayPosition, date,eventPreviewTaskList.get(0));
                    }else {
                        validateGridText(rowCounter, 1, dayPosition, date, eventTextTaskList.get(0));
                        validateGridText(rowCounter, 2, dayPosition, date, eventTextTaskList.get(1));
                        validateIsMoreEventLinkIsNotPresent(rowCounter, dayPosition, monthDay);
                    }
                    break;
                case MULTIPLE_TASK:
                    dayPosition = openMoreEventsPopupAndGetDayPosition(rowCounter,dayPosition, eventTextTaskList.size(),date);
                    if(dayPosition == -1){
                        validateDayEventGridAndMoreEventTaskList(rowCounter,dayPosition,date,removeDelimiterForBlankData(eventTextTaskList,PIPE_SPLITTER), eventPreviewTaskList, isOpenAndValidateEachEventPreview);
                    }else {
                        softAssertions.fail(monthDay+" is not having more events link though it is having multiple events.");
                    }
                    break;
                default:
                    softAssertions.fail("Database doesn't return any data.");
                rowCounter++;
            }
            cellCounter++;
            if(cellCounter % TOTAL_STUDY_DAYS == 1){
                rowCounter++;
            }
        }
    }

    private int openMoreEventsPopupAndGetDayPosition(int rowCounter, int dayPosition, int eventTaskListSize, String date){
        boolean isMoreEventPopupLinkExists = false;
        String moreEventPopupHeader = getFormattedDate(date, yyyy_MM_dd, weekDayMonthDayOfMonth);
        List<SelenideElement> moreEventLinkElementList = getElements(String.format(allAvailableMoreEventsLinksInRow,rowCounter));

        if(moreEventLinkElementList.size() < Calendar.TOTAL_STUDY_DAYS){
            for(int i=0;i<moreEventLinkElementList.size();i++){
                click(moreEventLinkElementList.get(i));
                isMoreEventPopupLinkExists = validateMoreEventPopUpHeader(moreEventPopupHeader);
                if(isMoreEventPopupLinkExists){
                    dayPosition = i+1;
                    tempXpath = String.format(Calendar.moreEventsLink,rowCounter,dayPosition);
                    break;
                }
            }
        }else {
            tempXpath = String.format(Calendar.moreEventsLink,rowCounter,dayPosition);
            try {
                click(tempXpath);
                isMoreEventPopupLinkExists = validateMoreEventPopUpHeader(moreEventPopupHeader);
            }catch (Throwable t){
                isMoreEventPopupLinkExists = false;
            }
        }
        if(isMoreEventPopupLinkExists) {
            tempText = String.format(Calendar.MORE_EVENTS_TEXT, eventTaskListSize - 2);
            softAssertions.assertThat(getElementText(tempXpath).equals(tempText))
                    .as(date + ": More event link text is not matching \nExpected:" + tempText + "\nActual:" + getElementText(tempXpath)).isTrue();
            return dayPosition;
        }else {
            softAssertions.fail(date + ":Either more event link is not present for given date or more events pop-up header's text is not matching with expected value [" + moreEventPopupHeader + "]");
            return -1;
        }
    }

    private boolean validateMoreEventPopUpHeader(String moreEventPopupHeader){
        if(getElementText(Calendar.moreEventsPopUpHeader).equals(moreEventPopupHeader)){
            return true;
        }
        click(Calendar.moreEventPopupCloseBtn);
        return false;
    }

    private void openAndValidateEventDetailsPopUp(int rowCounter, int gridPosition, int dayPosition, String date, String textToCompare){
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
        String[] compareTextArray = textToCompare.split(" | ");
        softAssertions.assertThat(getElementText(Calendar.eventPreviewHeader).equals(compareTextArray[0]))
                .as(date+" day event's preview header is not matching \nActual:"+getElementText(eventPreviewHeader)+"\nExpected:"+compareTextArray[0]).isTrue();
        softAssertions.assertThat(getElementText(Calendar.eventPreviewDate).equals(date))
                .as(date+" day event's preview date is not matching \nActual:"+getElementText(eventPreviewDate)+"\nExpected:"+date).isTrue();
        softAssertions.assertThat(getElementText(Calendar.eventPreviewDescription).equals(compareTextArray[1]))
                .as(date+" day event's preview description is not matching \nActual:"+getElementText(eventPreviewDescription)+"\nExpected:"+compareTextArray[2]).isTrue();
        click(Calendar.eventPreviewCloseBtn);
    }

    private ArrayList<String> getEventListFromMoreEventsPopup(){
        ArrayList<String> taskList = new ArrayList<>();
        for(SelenideElement element:getElements(Calendar.moreEventsPopupTasksText)){
            taskList.add(element.getText());
        }
        return taskList;
    }

    private void validateDayEventGridAndMoreEventTaskList(int rowCounter, int dayPosition, String date, ArrayList<String> dayTasksList, ArrayList<String> eventPreviewTaskList, boolean isOpenAndValidateEachEventPreview){
        tempXpath = Calendar.moreEventsPopupTasksText;
        List<SelenideElement> eventList = getElements(tempXpath);
        if(isOpenAndValidateEachEventPreview){
            openAndValidateEventDetailsPopUp(rowCounter, 1, dayPosition, date,eventPreviewTaskList.get(0));
            openAndValidateEventDetailsPopUp(rowCounter, 2, dayPosition, date,eventPreviewTaskList.get(0));
            for(int i=0;i<eventList.size();i++){
                click(getElements(tempXpath).get(i));
                validateEventPreviewPopUp(date, dayTasksList.get(i));
            }
        }else {
            validateGridText(rowCounter, 1, dayPosition, date, dayTasksList.get(0));
            validateGridText(rowCounter, 2, dayPosition, date, dayTasksList.get(1));
            softAssertions.assertThat(getEventListFromMoreEventsPopup().equals(dayTasksList))
                    .as(date+":More events expected event list ["+dayTasksList+"] is not equal to actual event list["+ getEventListFromMoreEventsPopup()+"]").isTrue();
        }
        click(Calendar.moreEventPopupCloseBtn);
    }

    @Step("Removing delimiter for blank data")
    private ArrayList<String> removeDelimiterForBlankData(ArrayList<String> list, String splitter){
        LinkedList<String> tempList;
        ArrayList<String> returnList = new ArrayList<>();
        for(String string:list){
            tempList = new LinkedList<>(Arrays.asList(string.split("\\|")));
            tempList.removeIf(n -> n.trim().equals(""));
            returnList.add(StringUtils.join(tempList,splitter.trim()).trim());
        }
        return returnList;
    }

    @Step(" Setting date position in map")
    private void setDayPositionInRow(int totalRows){
        for(int rowNumber=1;rowNumber<=totalRows;rowNumber++){
            for(int position=1;position <= getElements(String.format(Calendar.dayPositionInRow,rowNumber)).size();position++){
                //allDayPositions.put(getFormattedDate(getElements(String.format(Calendar.dayPositionInRow,rowNumber)).get(position).getAttribute(Calendar.DATA_DATE_ATTRIBUTE),Calendar.yyyy_MM_dd,Calendar.yyyyMMdd),Integer.toString(position));
                allDayPositions.put(getElements(String.format(Calendar.dayPositionInRow,rowNumber)).get(position-1).getAttribute(Calendar.DATA_DATE_ATTRIBUTE),Integer.toString(position));
            }
        }
    }

    private void validateGridText(int rowCounter,int gridPosition, int dayPosition, String monthDay, String compareText){
            String gridText = getElementText(String.format(Calendar.taskGridText,rowCounter,gridPosition,dayPosition));
            softAssertions.assertThat(gridText.equals(compareText.replaceAll(Calendar.TBOX_ITEMS,Calendar.QUIZ)))
                    .as(monthDay+" day's expected task's grid ["+gridPosition+"] expect text ["+compareText+"] is not equal to actual grid text ["+gridText+"]").isTrue();
    }

    private void validateIsMoreEventLinkIsNotPresent(int rowCounter, int dayPosition, String monthDay){
        softAssertions.assertThat(isElementExists(String.format(Calendar.moreEventsLink,rowCounter,dayPosition)))
                .as(monthDay+" is having more events link though it is having only which it should not.").isFalse();
    }

    private String getTaskBoxDay(String date){
        int dateLength = date.length();
        return (Character.toString(date.charAt(dateLength-2)).equals("0"))?date.substring(dateLength-1,dateLength):date.substring(dateLength-2,dateLength);
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
