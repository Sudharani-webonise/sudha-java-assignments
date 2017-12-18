package com.netmagic.spectrum.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.Version;

/**
 * The following Entity class helps to determine Master USer Transaction
 * Information data.
 * 
 * @author Webonise
 *
 */
@Entity
@Table(name = "mas_user_tran_info")
public class MasterUserTransactionEntity implements Serializable {

    private static final long serialVersionUID = -482579746519948977L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_tran_id_pk")
    @Version
    private long userTransactionId;

    @Column(name = "user_tran_email")
    private String userEmail;

    @Column(name = "user_tran_number")
    private String transactionNumber;

    @Column(name = "user_tran_mrch_number")
    private String merchantRefNumber;

    @Column(name = "user_tran_create_date_time")
    private Timestamp transactionCreatedTime;

    @Column(name = "user_tran_bill_to_cust_id")
    private String billToCustomerId;

    @Column(name = "user_tran_suppo_to_cust_id")
    private String supportCustomerId;

    @Column(name = "user_tran_total_amount")
    private double totalAmount;

    @Column(name = "user_tran_nspl_amount")
    private double nsplTotal;

    @Column(name = "user_tran_nspl_update_date_time")
    private Timestamp userTransactionNsplUpdatedTime;

    @Column(name = "user_tran_nmit_amount")
    private double nmitTotal;

    @Column(name = "user_tran_nmit_update_date_time")
    private Timestamp userTransactionNmitUpdatedTime;

    @Column(name = "user_tran_pytm_status")
    private boolean transactionStatus;

    @Column(name = "user_tran_is_mnp_user")
    private boolean isMnpUser;

    @Column(name = "user_tran_helios_admin")
    private boolean isHeliosAdmin;

    @Column(name = "user_tran_helios_admin_create_date_time")
    private Timestamp userTransactionHeliosAdminCreatedTime;

    @Column(name = "user_tran_helios_admin_req_body")
    private String superAdminRquestBody;

    @Column(name = "tran_pytm_conf_pytm_id_fk")
    private long paymentTypeId;

    @Column(name = "pytm_for_conf_pytm_for_id_fk")
    private long paymentForId;

    @OneToMany(mappedBy = "sofMasterUserTransaction", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<SofTransactionPaymentConfigurationEntity> sofTransactionPaymentConfigurations;

    @OneToOne(mappedBy = "masterUserTransaction", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private UserTransactionDetailsEntity userTransactionDetailsEntity;

    @OneToOne(mappedBy = "onAccountMasterUserTransaction", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private OnAccountTransactionDetailsEntity onAccountTransactionDetailsEntity;

    @OneToMany(mappedBy = "invoiceMasterUserTransaction", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<InvoiceTransactionDetailsEntity> invoiceTransactionDetailsEntity;

    public long getUserTransactionId() {
        return userTransactionId;
    }

    public void setUserTransactionId(long userTransactionId) {
        this.userTransactionId = userTransactionId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public String getMerchantRefNumber() {
        return merchantRefNumber;
    }

    public void setMerchantRefNumber(String merchantRefNumber) {
        this.merchantRefNumber = merchantRefNumber;
    }

    public Timestamp getTransactionCreatedTime() {
        return transactionCreatedTime;
    }

    public void setTransactionCreatedTime(Timestamp transactionCreatedTime) {
        this.transactionCreatedTime = transactionCreatedTime;
    }

    public String getBillToCustomerId() {
        return billToCustomerId;
    }

    public void setBillToCustomerId(String billToCustomerId) {
        this.billToCustomerId = billToCustomerId;
    }

    public String getSupportCustomerId() {
        return supportCustomerId;
    }

    public void setSupportCustomerId(String supportCustomerId) {
        this.supportCustomerId = supportCustomerId;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getNsplTotal() {
        return nsplTotal;
    }

    public void setNsplTotal(double nsplTotal) {
        this.nsplTotal = nsplTotal;
    }

    public Timestamp getUserTransactionNsplUpdatedTime() {
        return userTransactionNsplUpdatedTime;
    }

    public void setUserTransactionNsplUpdatedTime(Timestamp userTransactionNsplUpdatedTime) {
        this.userTransactionNsplUpdatedTime = userTransactionNsplUpdatedTime;
    }

    public double getNmitTotal() {
        return nmitTotal;
    }

    public void setNmitTotal(double nmitTotal) {
        this.nmitTotal = nmitTotal;
    }

    public Timestamp getUserTransactionNmitUpdatedTime() {
        return userTransactionNmitUpdatedTime;
    }

    public void setUserTransactionNmitUpdatedTime(Timestamp userTransactionNmitUpdatedTime) {
        this.userTransactionNmitUpdatedTime = userTransactionNmitUpdatedTime;
    }

    public boolean isTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(boolean transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public boolean isMnpUser() {
        return isMnpUser;
    }

    public void setMnpUser(boolean isMnpUser) {
        this.isMnpUser = isMnpUser;
    }

    public boolean isHeliosAdmin() {
        return isHeliosAdmin;
    }

    public void setHeliosAdmin(boolean isHeliosAdmin) {
        this.isHeliosAdmin = isHeliosAdmin;
    }

    public Timestamp getUserTransactionHeliosAdminCreatedTime() {
        return userTransactionHeliosAdminCreatedTime;
    }

    public void setUserTransactionHeliosAdminCreatedTime(Timestamp userTransactionHeliosAdminCreatedTime) {
        this.userTransactionHeliosAdminCreatedTime = userTransactionHeliosAdminCreatedTime;
    }

    public String getSuperAdminRquestBody() {
        return superAdminRquestBody;
    }

    public void setSuperAdminRquestBody(String superAdminRquestBody) {
        this.superAdminRquestBody = superAdminRquestBody;
    }

    public long getPaymentTypeId() {
        return paymentTypeId;
    }

    public void setPaymentTypeId(long paymentTypeId) {
        this.paymentTypeId = paymentTypeId;
    }

    public long getPaymentForId() {
        return paymentForId;
    }

    public void setPaymentForId(long paymentForId) {
        this.paymentForId = paymentForId;
    }

    public List<SofTransactionPaymentConfigurationEntity> getSofTransactionPaymentConfigurations() {
        return sofTransactionPaymentConfigurations;
    }

    public void setSofTransactionPaymentConfigurations(
            List<SofTransactionPaymentConfigurationEntity> sofTransactionPaymentConfigurations) {
        this.sofTransactionPaymentConfigurations = sofTransactionPaymentConfigurations;
    }

    public UserTransactionDetailsEntity getUserTransactionDetailsEntity() {
        return userTransactionDetailsEntity;
    }

    public void setUserTransactionDetailsEntity(UserTransactionDetailsEntity userTransactionDetailsEntity) {
        this.userTransactionDetailsEntity = userTransactionDetailsEntity;
    }

    public OnAccountTransactionDetailsEntity getOnAccountTransactionDetailsEntity() {
        return onAccountTransactionDetailsEntity;
    }

    public void setOnAccountTransactionDetailsEntity(
            OnAccountTransactionDetailsEntity onAccountTransactionDetailsEntity) {
        this.onAccountTransactionDetailsEntity = onAccountTransactionDetailsEntity;
    }

    public List<InvoiceTransactionDetailsEntity> getInvoiceTransactionDetailsEntity() {
        return invoiceTransactionDetailsEntity;
    }

    public void setInvoiceTransactionDetailsEntity(
            List<InvoiceTransactionDetailsEntity> invoiceTransactionDetailsEntity) {
        this.invoiceTransactionDetailsEntity = invoiceTransactionDetailsEntity;
    }

}
