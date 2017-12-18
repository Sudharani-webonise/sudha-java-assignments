package com.netmagic.spectrum.commons.url;

/**
 * Enum mapping the possible URL values for Service Ledger
 * 
 * @author Webonise
 *
 */
public enum ServiceLedger {

    WIDGET("fnWidgetDetails"),

    SOF_LIST("fnServiceLedgerSOFListAPI"),

    LINE_ITEM_LIST("fnServiceLedgerServiceListAPI");

    private String URL;

    private ServiceLedger(String URL) {
        this.URL = URL;
    }

    public String getURL() {
        return URL;
    }
}
