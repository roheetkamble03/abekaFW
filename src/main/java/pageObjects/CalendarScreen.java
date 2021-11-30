package pageObjects;

import base.GenericAction;
import com.codeborne.selenide.SelenideElement;
import com.google.common.base.CaseFormat;
import constants.Calendar;
import constants.DataBaseQueryConstant;
import constants.StudentDetails;
import constants.TableColumn;
import dataProvider.DataProviders;
import elementConstants.ProgressReportEventPreviewTestData;
import io.qameta.allure.Step;
import org.apache.commons.lang3.StringUtils;
import utility.HolidayList;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static constants.Calendar.*;
import static constants.CommonConstants.*;
import static constants.TableColumn.*;
import static constants.TableColumn.EVENT_ID;
import static java.time.temporal.ChronoUnit.DAYS;

public class CalendarScreen extends GenericAction {

    private static HashMap<String,String> allDayPositions = new HashMap<>();

    public CalendarScreen selectStudent(String studentName){
        waitForPageTobeLoaded();
        tempXpath = String.format(Calendar.studentSelectionCheckBox,studentName);
        bringElementIntoView(tempXpath);
        click(tempXpath);
        waitForPageTobeLoaded();
        return this;
    }

    @Step("Fetch progress report event date")
    public List<LocalDate> fetchProgressReportEventDate(List<Integer> dayCountList){
        List<Map<Integer,String>> dataMapList = new DataProviders().getExcelData(HOLIDAY_LIST, 0, 0);
        List<HolidayList> holidayListArrayList = new ArrayList<>();
        List<HolidayList> finalHolidayListArrayList = holidayListArrayList;
        dataMapList.stream().forEach(e-> finalHolidayListArrayList.add(HolidayList.builder().holiday(e.get(0)).beginDate(LocalDate.parse(e.get(1), DateTimeFormatter.ofPattern("yyyyMMdd"))).endDate(LocalDate.parse(e.get(2), DateTimeFormatter.ofPattern("yyyyMMdd"))).build()));
        courseBeginDate = "11/22/2021";
        LocalDate courseBeginDateLocal = LocalDate.parse(courseBeginDate, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        LocalDate courseDayCounter = LocalDate.parse(courseBeginDate, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        List<LocalDate> localDateList = new ArrayList<>();
        for(Integer dayCount: dayCountList) {
            LocalDate progressReportEventDate = courseBeginDateLocal.plusDays(dayCount);
            holidayListArrayList = finalHolidayListArrayList.stream().filter(e -> e.getBeginDate().isAfter(courseBeginDateLocal)).collect(Collectors.toList());

            while (!courseDayCounter.equals(progressReportEventDate)) {
                courseDayCounter = courseDayCounter.plusDays(1);
                if (courseDayCounter.getDayOfWeek() == DayOfWeek.SATURDAY ||
                        courseDayCounter.getDayOfWeek() == DayOfWeek.SUNDAY || isHolidayOnGivenDate(courseDayCounter, holidayListArrayList)) {
                    progressReportEventDate = progressReportEventDate.plusDays(1);
                }
            }
            localDateList.add(progressReportEventDate);
        }
        return localDateList;
    }

    private boolean isHolidayOnGivenDate(LocalDate courseDayCounter, List<HolidayList> holidayListArrayList){
        LocalDate tempDate;
        for(HolidayList holiday:holidayListArrayList){
            tempDate = holiday.getBeginDate().plusDays(1);
            for(int i = 0; i <= DAYS.between(holiday.getBeginDate(),holiday.getEndDate()); i++){
                if(tempDate.equals(courseDayCounter)){
                    return true;
                }
                tempDate = tempDate.plusDays(1);
            }
        }
        return false;
    }

    @Step("Validating calendar events")
    public CalendarScreen validateStudentCalendarEvents(boolean isOpenAndValidateEachEventPreview) {
        waitAndCloseWidgetTourPopup();
        if(isElementExists(Calendar.calendarSection, false)){
            bringElementIntoView(getElements(Calendar.CALENDAR_MONTH_DAYS).get(0));
            validateCalendarEvents(getCalendarDataFromDB(), isOpenAndValidateEachEventPreview);
        }else {
            softAssertions.fail("Calendar section not appeared for selected student");
        }
        return this;
    }

    @Step("Validating by default no student is selected")
    public CalendarScreen validateNoStudentIsSelected(){
        //checkbox property is not changing in dom showing checked though check box is not selected, hence validating with event box
            softAssertions.assertThat(getElements(Calendar.eventBoxes).size()>5)
                    .as("Though student is not selected, Calendar having event(s) on the page.").isFalse();
        return this;
    }

    private  ArrayList<HashMap<String,String>> getCalendarDataFromDB(){
        String studentId = getUserAccountDetails().getStudentId();
        String accountNumber = getUserAccountDetails().getAccountNumber();
        String startDate = getFormattedDate(bringElementIntoView(getElements(Calendar.CALENDAR_MONTH_DAYS).get(0)).getAttribute(DATA_DATE_ATTRIBUTE),Calendar.yyyy_MM_dd,Calendar.yyyyMMdd);
        String endDate = getFormattedDate(bringElementIntoView(getElements(Calendar.CALENDAR_MONTH_DAYS).get(getElements(Calendar.CALENDAR_MONTH_DAYS).size()-1)).getAttribute("data-date"),"yyyy-MM-dd","yyyyMMdd");

        return executeAndGetSelectQueryData(DataBaseQueryConstant.STUDENT_CALENDER_EVENTS_AD_DB
                .replaceAll(TableColumn.STUDENT_ID_DATA,studentId)
                .replaceAll(TableColumn.ACCOUNT_NUMBER_DATA,accountNumber)
                .replaceAll(TableColumn.START_DATE_DATA,startDate)
                .replaceAll(TableColumn.END_DATE_DATA,endDate), AD_DATA_BASE, false);
    }

    private void validateCalendarEvents(ArrayList<HashMap<String,String>> calendarEventsDBResult, boolean isOpenAndValidateEachEventPreview){

        String date;
        String monthDay;
        int taskSize;
        int dayPosition;
        int gridDayPosition;
        int cellCounter = 1;
        int rowCounter = 1;

        Map<String, ArrayList<String>> eventGridTextData = getGroupedByDataAccordingToColumn(START_DATE, calendarEventsDBResult, new String[]{SUBJECT, DESCRIPTION}, PIPE_SPLITTER);
        Map<String, ArrayList<String>> eventPreviewData = getGroupedByDataAccordingToColumn(START_DATE, calendarEventsDBResult, new String[]{SUBJECT, LONG_DESCRIPTION}, PIPE_SPLITTER);
        Map<String, ArrayList<String>> eventIDMapList = getCustomKeyAndColumnDataList(calendarEventsDBResult, new String[]{START_DATE,SUBJECT,LONG_DESCRIPTION}, PIPE_SPLITTER, new String[]{EVENT_ID},PIPE_SPLITTER);

        ArrayList<String> eventTextTaskList;
        ArrayList<String> eventPreviewTaskList;
        ArrayList<String> eventIDList;
        setDayPositionInRow(eventGridTextData.size()/ TOTAL_STUDY_DAYS);
        ArrayList<String> keySet = new ArrayList<>(eventGridTextData.keySet());

        for(int i=0;i<eventGridTextData.size();i++){
            date = keySet.get(i);
            log("Executing validation for date:"+date);
            monthDay = getTaskBoxDay(date);
            dayPosition = Integer.parseInt(allDayPositions.get(date))-1;
            eventTextTaskList = removeDelimiterForBlankData(eventGridTextData.get(date),PIPE_SPLITTER);
            eventPreviewTaskList = removeDelimiterForBlankData(eventPreviewData.get(date),PIPE_SPLITTER);
            eventIDList = getEventIDList(eventIDMapList, eventPreviewData, date);
            taskSize = eventTextTaskList.size();

            tempXpath = String.format(Calendar.taskGridDay,date);
            bringElementIntoView(tempXpath);
            softAssertions.assertThat(getElementText(tempXpath).equals(getTaskBoxDay(date)))
                    .as(date+":Expected task grid day box's day ["+getTaskBoxDay(date)+"] is not equal to actual ["+getElementText(tempXpath+"]")).isTrue();


            switch (getDayTaskGridType(taskSize)){
                case ONLY_ONE_TASK:
                    gridDayPosition = getGridDayPosition(rowCounter, 1, eventIDList, date);
                    if (isOpenAndValidateEachEventPreview) {
                        openAndValidateEventDetailsPopUp(rowCounter, 1, gridDayPosition, date,eventPreviewTaskList.get(0));
                    }else {
                        validateGridText(rowCounter, 1, gridDayPosition, date, eventTextTaskList.get(0));
                        validateIsMoreEventLinkIsNotPresent(rowCounter,dayPosition, eventTextTaskList.size(),date);
                    }
                    break;
                case ONLY_TWO_TASK:
                    validateEventGrid(rowCounter, date, eventTextTaskList, eventPreviewTaskList, eventIDList, isOpenAndValidateEachEventPreview);
                    validateIsMoreEventLinkIsNotPresent(rowCounter,dayPosition, eventTextTaskList.size(),date);
                    break;
                case MULTIPLE_TASK:
                    validateEventGrid(rowCounter, date, eventTextTaskList, eventPreviewTaskList, eventIDList, isOpenAndValidateEachEventPreview);
                    int moreEventLinkDayPosition = validateAndOpenMoreEventsPopupAndGetDayPosition(rowCounter,dayPosition, eventTextTaskList.size(),date, true);
                    if(moreEventLinkDayPosition != -1){
                        validateDayEventGridAndMoreEventTaskList(date,eventTextTaskList, eventPreviewTaskList, isOpenAndValidateEachEventPreview);
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

    private ArrayList<String> getEventIDList(Map<String,ArrayList<String>> eventIDMapList, Map<String, ArrayList<String>> eventPreviewData, String date){
        Stack<String> stack = new Stack<>();
        ArrayList<String> zeroEventID = new ArrayList<>();
        String eventId;
        for(String string:eventPreviewData.get(date)){
            eventId = eventIDMapList.get(date+PIPE_SPLITTER+string).get(0);
            if(eventId.equals("0")) {
                zeroEventID.add(eventId);
            } else {
                stack.push(eventId);
            }
        }
        if(zeroEventID.size()>0){
            softAssertions.fail(date+":is having other events on Holiday.");
            for(int i=0;i<zeroEventID.size();i++){
                stack.insertElementAt(zeroEventID.get(i),i);
            }
        }
        ArrayList<String> eventIdList = new ArrayList<>(stack);
        return eventIdList;
    }

    private int getGridDayPosition(int rowCounter, int gridPosition, ArrayList<String> assignmentId, String date){
        List<SelenideElement> elementList = getElements(String.format(Calendar.allEventGrids,rowCounter,gridPosition));
        for(int i = 0;i<elementList.size();i++){
            if(getClassAttributeValue(elementList.get(i)).indexOf("event_"+assignmentId.get(gridPosition-1))>0){
                return i+1;
            }
        }
        softAssertions.fail(date+":Grid "+gridPosition+" position event text is not matching with given assignment ID:"+assignmentId.get(gridPosition-1));
        return 0;
    }

    private int validateAndOpenMoreEventsPopupAndGetDayPosition(int rowCounter, int dayPosition, int eventTaskListSize, String date, boolean isLinkShouldPresent){
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
            if(isLinkShouldPresent) {
                softAssertions.fail(date + ":Either more event link is not present for given date or more events pop-up header's text is not matching with expected value [" + moreEventPopupHeader + "]");
            }
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
        tempXpath = getElementText(String.format(Calendar.eventGridText,rowCounter,gridPosition,dayPosition));
        if(isElementExists(tempXpath, false)){
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

    private void validateDayEventGridAndMoreEventTaskList(String date, ArrayList<String> dayTasksList, ArrayList<String> eventPreviewTaskList, boolean isOpenAndValidateEachEventPreview){
        tempXpath = Calendar.moreEventsPopupTasksText;
        List<SelenideElement> eventList = getElements(tempXpath);
        if(isOpenAndValidateEachEventPreview){
            for(int i=0;i<eventList.size();i++){
                click(getElements(tempXpath).get(i));
                validateEventPreviewPopUp(date, eventPreviewTaskList.get(i));
            }
        }else {
            softAssertions.assertThat(getEventListFromMoreEventsPopup().equals(dayTasksList))
                    .as(date+":More events expected event list ["+dayTasksList+"] is not equal to actual event list["+ getEventListFromMoreEventsPopup()+"]").isTrue();
        }
        click(Calendar.moreEventPopupCloseBtn);
        waitForPageTobeLoaded();
    }

    public void validateEventGrid(int rowCounter, String date, ArrayList<String> dayTasksList, ArrayList<String> eventPreviewTaskList, ArrayList<String> eventIDList, boolean isOpenAndValidateEachEventPreview){
        if(isOpenAndValidateEachEventPreview) {
            openAndValidateEventDetailsPopUp(rowCounter, 1, getGridDayPosition(rowCounter, 1, eventIDList, date), date, eventPreviewTaskList.get(0));
            openAndValidateEventDetailsPopUp(rowCounter, 2, getGridDayPosition(rowCounter, 2, eventIDList, date), date, eventPreviewTaskList.get(1));
        }else {
            validateGridText(rowCounter, 1, getGridDayPosition(rowCounter, 1, eventIDList, date), date, dayTasksList.get(0));
            validateGridText(rowCounter, 2, getGridDayPosition(rowCounter, 2, eventIDList, date), date, dayTasksList.get(1));
        }
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

    private void validateGridText(int rowCounter,int gridPosition, int dayPosition, String date, String compareText){
        if(dayPosition == 0){
            return;
        }
        tempXpath = String.format(Calendar.eventGridText,rowCounter,gridPosition,dayPosition);
        String gridText = getElementText(tempXpath);
            softAssertions.assertThat(gridText.equals(compareText))
                    .as(date+" day's expected task's grid ["+gridPosition+"] expect text ["+compareText+"] is not equal to actual grid text ["+gridText+"] xpath:"+tempXpath).isTrue();
    }

    private void validateIsMoreEventLinkIsNotPresent(int rowCounter, int dayPosition, int eventTaskListSize, String date){
        softAssertions.assertThat(validateAndOpenMoreEventsPopupAndGetDayPosition(rowCounter, dayPosition, eventTaskListSize, date, false)==-1)
                .as(date+" is having more events link though it is having only which it should not.").isTrue();
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

    public CalendarScreen selectStudent(StudentDetails studentDetails){
        clickByJavaScript(String.format(calendarAccountCheckbox,studentDetails.getStudentUserId()));
        waitForPageTobeLoaded();
        return this;
    }

    public CalendarScreen navigateToCalendarDate(LocalDate progressReportEventDate){
        waitForElementTobeExist(monthButton);
        click(monthButton);
        boolean isClickNext = progressReportEventDate.isAfter(LocalDate.now());
        int counter = 0;
        while (!isElementExists(String.format(dateCellOnCalendar,
                CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL,progressReportEventDate.getMonth()
                        .toString())+" "+progressReportEventDate.getYear(), progressReportEventDate.getDayOfMonth()), false, 5) && counter < 13){
            if (isClickNext) {
                click(calendarNextButton);
                waitForPageTobeLoaded();
            } else {
                click(calendarPrevButton);
            }
            counter++;
        }
        return this;
    }

    public void validateProgressReportEventPreview(ProgressReportEventPreviewTestData progressReportEventPreviewTestData){
        String dateShowMoreLinkXpath = getShowMoreLinkXpathOfGivenDate(progressReportEventPreviewTestData.getProgressReportEventDate().get(0));
        bringElementIntoView(dateShowMoreLinkXpath);
        click(dateShowMoreLinkXpath);
        click(String.format(eventBox,progressReportEventPreviewTestData.getEventID().get(0)));
        softAssertions.assertThat(isElementExists(String.format(eventPreviewTitle,progressReportEventPreviewTestData.getPopupTitle().get(0),progressReportEventPreviewTestData.getPopupType().get(0))
                , false)).as("Event preview is not expected as "+ progressReportEventPreviewTestData.getPreviewTitle()).isTrue();
        softAssertions.assertThat(isElementExists(String.format(eventPreviewPopUpDate,
                progressReportEventPreviewTestData.getProgressReportEventDate().get(0).format(DateTimeFormatter.ofPattern(MMM_dd_yyyy))),false)).as("Event preview pop up is not showing expected date "
                +progressReportEventPreviewTestData.getProgressReportEventDate().get(0).format(DateTimeFormatter.ofPattern(MMM_dd_yyyy))).isTrue();
        softAssertions.assertThat(isElementExists(String.format(eventPreviewDescriptionText,progressReportEventPreviewTestData.getPreviewDescription().get(0)), false))
                .as("Event preview description is not expected as "+ progressReportEventPreviewTestData.getPreviewDescription()).isTrue();
    }

    private String getShowMoreLinkXpathOfGivenDate(LocalDate localDate) {
        Map<String,Map<String,Integer>> dateRowMap = getDateRowCombinationMap();
        LocalDate date = localDate;
        Integer rowPosition = dateRowMap.get(date.toString()).get(ROW);
        Integer cellPosition = dateRowMap.get(date.toString()).get(CELL);
        return String.format(showMoreLink,rowPosition, cellPosition);
    }

    public CalendarScreen addNewEventToCalendar(LocalDate date, String category, String eventName){
        click(bringElementIntoView(String.format(dateCell, date)));
        type(createEventTitle, eventName);
        selectByIndex(createEventCategoryDropdown, (category.equals(VIDEO))?1:5);
        selectByIndex(createEventSubjectDropdown, 1);
        selectByIndex(createEventLessonNumberDropdown, 1);
        type(createEventDescription, AUTOMATION_TEST);
        click(createEventCreateButton);
        waitForPageTobeLoaded();
        return this;
    }

    public CalendarScreen openEventBox(LocalDate date, String eventName){
        String dateShowMoreLinkXpath = getShowMoreLinkXpathOfGivenDate(date);
        click(bringElementIntoView(dateShowMoreLinkXpath));
        if(!isElementExists(String.format(eventBoxTitle,eventName),false, 10)){
            click(bringElementIntoView(dateShowMoreLinkXpath));
        }
        clickByJavaScript(String.format(eventBoxTitle,eventName));
        return this;
    }

    public CalendarScreen validateNewlyAddedEvent(LocalDate date){
        openEventBox(date, AUTOMATION_TEST);
        softAssertions.assertThat(isElementExists(String.format(eventPreviewTitle,AUTOMATION_TEST,VIDEO)
                , false)).as("Event preview is not expected as "+ AUTOMATION_TEST+" and "+VIDEO).isTrue();
        softAssertions.assertThat(isElementExists(String.format(eventPreviewPopUpDate,
                date.format(DateTimeFormatter.ofPattern(MMM_dd_yyyy))),false)).as("Event preview pop up is not showing expected date "
                +date.format(DateTimeFormatter.ofPattern(MMM_dd_yyyy))).isTrue();
       // softAssertions.assertThat(isElementExists(String.format(eventPreviewTime, SIX_PM), false, 10)).as("Newly created event preview showing wrong time, other than "+ SIX_PM).isTrue();
        softAssertions.assertThat(isElementExists(String.format(eventPreviewDescriptionText,AUTOMATION_TEST), false))
                .as("Event preview description is not expected as "+ AUTOMATION_TEST).isTrue();
        return this;
    }

    public CalendarScreen editCreatedEvent(LocalDate date){
        //openEventBox(date);
        click(calendarEventEditBtn);
        type(createEventDescription, AUTOMATION_TEST_EDIT);
        click(calendarEventSaveBtn);
        openEventBox(date, AUTOMATION_TEST);
        softAssertions.assertThat(isElementExists(String.format(eventPreviewDescriptionText,AUTOMATION_TEST_EDIT), false))
                .as("After edit Event preview description is not expected as "+ AUTOMATION_TEST_EDIT).isTrue();
        return this;
    }

    public CalendarScreen deleteAndVerifyCreatedEvent(LocalDate date){
        click(deleteEventButton);
        waitForElementTobeExist(sureDeleteEvent);
        if(isElementDisplayed(sureDeleteEvent)){
            click(deleteDialogButton);
            waitForPageTobeLoaded();
        }
        validateIsEventBoxPresent(date, AUTOMATION_TEST);
        return this;
    }

    public CalendarScreen selectDeselectCalendarCategory(String category){
        click(calendarCategory);
        clickByJavaScript(String.format(categoryCheckBoxy,category));
        implicitWaitInSeconds(10);
        waitForPageTobeLoaded();
        return this;
    }

    public CalendarScreen validateIsEventBoxPresent(LocalDate date, String eventName){
        String dateShowMoreLinkXpath = getShowMoreLinkXpathOfGivenDate(date);
        click(bringElementIntoView(dateShowMoreLinkXpath));
        softAssertions.assertThat(isElementExists(String.format(eventBoxTitle,eventName), false, 10))
                .as(date+eventName+" Event is present, but it should not").isFalse();
        return this;
    }

    private Map<String, Map<String,Integer>> getDateRowCombinationMap() {
        waitForElementTobeExist(Calendar.tableRows);
        implicitWaitInSeconds(10);
        List<SelenideElement> tableRows = getElements(Calendar.tableRows);
        Map<String, Map<String,Integer>> tableRowCellCombination = new HashMap<>();
        Map<String, Integer> rowCellCombination = new HashMap<>();
        int rowCounter = 1;
        int cellCounter = 1;
        for(SelenideElement element:tableRows){
            rowCellCombination = new HashMap<>();
            rowCellCombination.put(ROW, rowCounter);
            rowCellCombination.put(CELL, cellCounter);
            cellCounter = 1;
            for(SelenideElement dateElement:getElements(String.format(tableRowDays, rowCounter))){
                tableRowCellCombination.put(getElementPropertyValue(dateElement, DATA_DATE), rowCellCombination);
                cellCounter++;
                cellCounter = (cellCounter == 8)?1:cellCounter;
            }
            rowCounter++;
        }
        return tableRowCellCombination;
    }
}
