package constants;

public @interface Calendar {
    String ONLY_ONE_TASK = "ONLY ONE TASK";
    String ONLY_TWO_TASK = "ONLY TWO TASK";
    String MULTIPLE_TASK = "MULTIPLE TASK";
    String MORE_EVENTS_TEXT = "+%s more events";
    String DATA_DATE_ATTRIBUTE = "data-date";
    String weekDayMonthDayOfMonth = "weekDayMonthDayOfMonth";
    String dayMonthSingleDate = "dayMonthSingleDate";
    String yyyy_MM_dd = "yyyy-MM-dd";
    String yyyyMMdd = "yyyyMMdd";
    String dayMMMMdd = "dayMMMMdd";
    String MMM_dd_yyyy = "MMM dd, yyyy";
    String MMMddyyyy = "MMM, dd yyyy";
    String PIPE_SPLITTER = " | ";
    String TBOX_ITEMS = "Tboxitems";
    String QUIZ = "Quiz";
    int TOTAL_STUDY_DAYS = 5;
    String TEST = "Test";
    String BIBLE = "Bible";
    String LESSON_ONE = "Lesson 1";
    String sureDeleteEvent = "Are you sure you want to delete this event?";
    String EVENT_PROPERTY = "fc-event-container";

    String studentSelectionCheckBox = "xpath=//span[normalize-space(text())='%s']/parent::div/preceding-sibling::div[position()=1]/descendant::input[@type='checkbox']";
    String calendarSection = "id=calendar";
    String CALENDAR_MONTH_DAYS = "xpath=//div[@id='calendar']/descendant::td[@data-date]";
    String taskGridDay = "xpath=//div[@class='fc-content-skeleton']/table/descendant::td[@data-date='%s']/span";
    String eventGridText = "xpath=((//div[@class='fc-content-skeleton']/table/tbody)[%s]/descendant::tr[%s]/descendant::span[@class='fc-title'])[%s]";
    String allEventGrids = "xpath=((//div[@class='fc-content-skeleton']/table/tbody)[%s]/descendant::tr[%s]/descendant::a[contains(normalize-space(@class),'fc-day-grid-event')])";
    String moreEventsLink = "xpath=(((//div[@class='fc-content-skeleton']/table/tbody)[%s]/descendant::tr[3])/descendant::td[@class='fc-more-cell']/descendant::a[@class='fc-more'])[%s]";
    String allAvailableMoreEventsLinksInRow = "xpath=((//div[@class='fc-content-skeleton']/table/tbody)[%s]/descendant::tr[3])/descendant::td[@class='fc-more-cell']/descendant::a[@class='fc-more']";
    String dayPositionInRow = "xpath=(//div[@class='fc-content-skeleton']/table/thead)[%s]/descendant::td";
    String moreEventsPopUpHeader = "xpath=//div[@class='fc-popover fc-more-popover']/div[@class='fc-header fc-widget-header']/span[@class='fc-title']";
    String moreEventsPopupTasksText = "xpath=//div[@class='fc-popover fc-more-popover']/div[@class='fc-body fc-widget-content']/descendant::span[@class='fc-title']";
    String moreEventPopupCloseBtn = "xpath=//span[@class='fc-close fc-icon fc-icon-x']";
    String eventPreviewHeader = "xpath=//div[@aria-describedby='EventPreview']/descendant::h1";
    String eventPreviewDate = "xpath=//div[@aria-describedby='EventPreview']/descendant::span[@id='PreviewDate']";
    String eventPreviewDescription = "xpath=//div[@aria-describedby='EventPreview']/descendant::span[@id='PreviewDescription']";
    String eventPreviewCloseBtn = "xpath=//div[@aria-describedby='EventPreview']/descendant::button[@class='ui-dialog-titlebar-close']";
    String eventBoxes = "xpath=//a[contains(@class,'fc-day-grid-event')]";
    String holidayEventId = "event_0";
    String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd hh:mm:ss";
    String monthButton = "xpath=//button[normalize-space(text())='month']";
    String calendarNextButton = "xpath=//button[@aria-label='next']";
    String calendarPrevButton = "xpath=//button[@aria-label='prev']";
    String dateCellOnCalendar = "xpath=//h2[normalize-space(text())='%s']/ancestor::div[@id='calendar']/descendant::span[@class='fc-day-number' and normalize-space(text())='%s']";
    String tableRows = "xpath=//div[@class='fc-content-skeleton']";
    String tableRowDays = "xpath=(//div[@class='fc-content-skeleton'])[%s]/descendant::td[contains(@class,'fc-future')]";
    String cellEventRow = "xpath=(//div[@class='fc-content-skeleton'])[2]/descendant::tbody/tr[1]/descendant::td";
    String showMoreLink = "xpath=((//div[@class='fc-content-skeleton'])[%s]/descendant::tbody/tr[3]/descendant::a[@class='fc-more'])[%s]";
    String eventBox = "xpath=(//a[contains(@class,'%s')])[last()]";
    String eventBoxTitle = "xpath=//div[@class='fc-event-container']/descendant::span[@class='fc-title' and normalize-space(text())='%s']";
    String eventPreviewTitle = "xpath=//div[@id='EventPreview']/descendant::h1[normalize-space(text())='%s']/small[normalize-space(text())='%s']";
    String eventPreviewPopUpDate = "xpath=//span[@id='PreviewDate' and normalize-space(text())='%s']";
    String eventPreviewTime = "xpath=//span[@id='PreviewTime' and normalize-space(text())='%s']";
    String eventPreviewDescriptionText = "xpath=//span[@id='PreviewDescription' and normalize-space(text())='%s']";
    String dateCell = "xpath=(//td[@data-date='%s'])[2]";
    String createEventTitle = "xpath=//div[@id='addEvent']/descendant::input[@id='txtTitle']";
    String createEventCategoryDropdown = "id=slcCategory";
    String createEventSubjectDropdown = "id=txtSubject";
    String createEventLessonNumberDropdown = "id=txtLessonNumber";
    String createEventDescription = "id=txtDescription";
    String createEventCreateButton = "id=btnCreate";
    String calendarAccountCheckbox = "xpath=//span[@class='tab-name' and normalize-space(text())='%s']/parent::div/preceding-sibling::div[@class='tab-form']/descendant::input[@type='checkbox']";
    String deleteEventButton = "id=btnDelete";
    String deleteDialogButton = "id=DeleteDialogBtn";
    String calendarEventEditBtn = "id=EditEventPreviewBtn";
    String calendarEventSaveBtn = "id=btnSave";
    String calendarCategory = "id=matserTab_Categories";
    String categoryCheckBoxy = "xpath=//input[@type='checkbox' and @data-text='%s']";
}
