package com.netmagic.spectrum.commons.url;

/**
 * Enum mapping the possible URL values for Tickets
 * 
 * @author Webonise
 *
 */
public enum Tickets {

    WORKLOG_UPDATE("ticketAPI/ticket/worklog/add"),

    CONFIG("ticketAPI/tickets/widget/config?accesskey="),

    LIST("ticketAPI/tickets/widget/list/"),

    UPDATE("ticketAPI/ticket/status/change"),

    WIDGET("ticketAPI/tickets/widget/count?accesskey="),

    DETAIL("ticketAPI/ticket/worklog/view/"),

    CASE_TYPE("ticketAPI/ticket/types"),

    SUB_CASE_TYPE("ticketAPI/ticket/subtypes?accesskey="),

    DOWNLOAD_ATTACHMENT("ticketAPI/ticket/file/download?requestFor=worklog&ticketAttachmentId="),

    CREATE("ticketAPI/ticket/create");

    private String URL;

    private Tickets(String URL) {
        this.URL = URL;
    }

    public String getURL() {
        return URL;
    }

}
