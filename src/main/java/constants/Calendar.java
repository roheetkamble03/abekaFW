package constants;

public @interface Calendar {
    String ONLY_ONE_TASK = "ONLY ONE TASK";
    String ONLY_TWO_TASK = "ONLY TWO TASK";
    String MULTIPLE_TASK = "MULTIPLE TASK";
    String MORE_EVENTS_TEXT = "+%s more events";
    String DATA_DATE_ATTRIBUTE = "data-date";
    String weekDayMonthDayOfMonth = "weekDayMonthDayOfMonth";
    String yyyy_MM_dd = "yyyy-MM-dd";
    String yyyyMMdd = "yyyyMMdd";
    String dayMMMMdd = "dayMMMMdd";
    String MMMddyyyy = "MMM, dd yyyy";
    String PIPE_SPLITTER = " | ";
    String TBOX_ITEMS = "Tboxitems";
    String QUIZ = "Quiz";
    int TOTAL_STUDY_DAYS = 5;

    String studentSelectionCheckBox = "xpath=//span[normalize-space(text())='%s']/parent::div/preceding-sibling::div[position()=1]/descendant::input[@type='checkbox']";
    String calendarSection = "id=calendar";
    String CALENDAR_MONTH_DAYS = "xpath=//div[@id='calendar']/descendant::td[@data-date]";
    String taskGridDay = "xpath=//div[@class='fc-content-skeleton']/table/descendant::td[@data-date='%s']/span";
    String taskGridText = "xpath=((//div[@class='fc-content-skeleton']/table/tbody)[%s]/descendant::tr[%s]/descendant::span[@class='fc-title'])[%s]";
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
}
