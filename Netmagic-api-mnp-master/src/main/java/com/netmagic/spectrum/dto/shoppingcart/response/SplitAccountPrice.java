package com.netmagic.spectrum.dto.shoppingcart.response;

public class SplitAccountPrice {

    private double totalNmit;
    private double totalNmspl;

    public SplitAccountPrice() {
        super();
        // TODO Auto-generated constructor stub
    }

    public SplitAccountPrice(double totalNmit, double totalNmspl) {
        super();
        this.totalNmit = totalNmit;
        this.totalNmspl = totalNmspl;
    }

    public double getTotalNmit() {
        return totalNmit;
    }

    public void setTotalNmit(double totalNmit) {
        this.totalNmit = totalNmit;
    }

    public double getTotalNmspl() {
        return totalNmspl;
    }

    public void setTotalNmspl(double totalNmspl) {
        this.totalNmspl = totalNmspl;
    }
}
