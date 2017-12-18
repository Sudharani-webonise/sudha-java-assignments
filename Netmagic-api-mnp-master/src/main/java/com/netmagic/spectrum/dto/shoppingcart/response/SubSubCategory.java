package com.netmagic.spectrum.dto.shoppingcart.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class has sub sub category information from cms portal for shopping cart
 * 
 * @author webonise
 *
 */
public class SubSubCategory implements Serializable {

    private static final long serialVersionUID = -3364926228102359023L;

    @JsonProperty("subsubcatid")
    private String subsubcatid;
    @JsonProperty("subsubcatname")
    private String subsubcatname;
    @JsonProperty("alias")
    private String alias;

    public String getSubsubcatid() {
        return subsubcatid;
    }

    public void setSubsubcatid(String subsubcatid) {
        this.subsubcatid = subsubcatid;
    }

    public String getSubsubcatname() {
        return subsubcatname;
    }

    public void setSubsubcatname(String subsubcatname) {
        this.subsubcatname = subsubcatname;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
