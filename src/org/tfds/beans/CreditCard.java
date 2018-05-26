package org.tfds.beans;

import java.util.Date;

public class CreditCard extends Entity {
	
	// Account attribute strings
	public static final String SSN = "OwnerSSN";
	public static final String CARD_NUMBER = "CardNumber";
	public static final String ACCOUNT_NUMBER = "AcctNum";
	public static final String CREATION_DATE = "AcctCreationDate";

    private String ssn;
    private String cardNumber;
    private String acctNum;
    private Date acctCreationDate;

    public CreditCard(String ssn, String cardNumber, String acctNum,
                   Date acctCreationDate) {
        this.ssn = ssn;
        this.cardNumber = cardNumber;
        this.acctNum = acctNum;
        this.acctCreationDate = acctCreationDate;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getAcctNum() {
        return acctNum;
    }

    public void setAcctNum(String acctNum) {
        this.acctNum = acctNum;
    }

    public Date getAcctCreationDate() {
        return acctCreationDate;
    }

    public void setAcctCreationDate(Date acctCreationDate) {
        this.acctCreationDate = acctCreationDate;
    }
}