package com.netmagic.spectrum.commons;

public enum Param {

    TWO("%s%s"), THREE("%s%s%s"), FOUR("%s%s%s%s"), FIVE("%s%s%s%s%s"), SIX("%s%s%s%s%s%s"), SEVEN(
            "%s%s%s%s%s%s%s"), ELEVEN("%s%s%s%s%s%s%s%s%s%s%s"), NINE("%s%s%s%s%s%s%s%s%s"), EIGHT("%s%s%s%s%s%s%s%s");

    private String param;

    private Param(String param) {
        this.param = param;
    }

    public String getParam() {
        return param;
    }

}
