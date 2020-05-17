package com.myeotra.driver.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserResponse {


    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("country_code")
    @Expose
    private String countryCode;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("avatar")
    @Expose
    private String avatar;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("fleet")
    @Expose
    private Object fleet;
    @SerializedName("latitude")
    @Expose
    private Double latitude;
    @SerializedName("longitude")
    @Expose
    private Double longitude;
    @SerializedName("stripe_acc_id")
    @Expose
    private Object stripeAccId;
    @SerializedName("stripe_cust_id")
    @Expose
    private Object stripeCustId;
    @SerializedName("paypal_email")
    @Expose
    private Object paypalEmail;
    @SerializedName("login_by")
    @Expose
    private String loginBy;
    @SerializedName("social_unique_id")
    @Expose
    private Object socialUniqueId;
    @SerializedName("otp")
    @Expose
    private Integer otp;
    @SerializedName("wallet_balance")
    @Expose
    private Integer walletBalance;
    @SerializedName("referral_unique_id")
    @Expose
    private Object referralUniqueId;
    @SerializedName("qrcode_url")
    @Expose
    private String qrcodeUrl;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("service")
    @Expose
    private Service service;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("sos")
    @Expose
    private String sos;
    @SerializedName("measurement")
    @Expose
    private String measurement;
    @SerializedName("profile")
    @Expose
    private Profile profile;
    @SerializedName("cash")
    @Expose
    private Integer cash;
    @SerializedName("card")
    @Expose
    private Integer card;
    @SerializedName("stripe_secret_key")
    @Expose
    private String stripeSecretKey;
    @SerializedName("stripe_publishable_key")
    @Expose
    private String stripePublishableKey;
    @SerializedName("stripe_currency")
    @Expose
    private String stripeCurrency;
    @SerializedName("payumoney")
    @Expose
    private Integer payumoney;
    @SerializedName("paypal")
    @Expose
    private Integer paypal;
    @SerializedName("paypal_adaptive")
    @Expose
    private Integer paypalAdaptive;
    @SerializedName("braintree")
    @Expose
    private Integer braintree;
    @SerializedName("paytm")
    @Expose
    private Integer paytm;
    @SerializedName("payumoney_environment")
    @Expose
    private String payumoneyEnvironment;
    @SerializedName("payumoney_key")
    @Expose
    private String payumoneyKey;
    @SerializedName("payumoney_salt")
    @Expose
    private String payumoneySalt;
    @SerializedName("payumoney_auth")
    @Expose
    private String payumoneyAuth;
    @SerializedName("paypal_environment")
    @Expose
    private String paypalEnvironment;
    @SerializedName("paypal_currency")
    @Expose
    private String paypalCurrency;
    @SerializedName("paypal_client_id")
    @Expose
    private String paypalClientId;
    @SerializedName("paypal_client_secret")
    @Expose
    private String paypalClientSecret;
    @SerializedName("braintree_environment")
    @Expose
    private String braintreeEnvironment;
    @SerializedName("braintree_merchant_id")
    @Expose
    private String braintreeMerchantId;
    @SerializedName("braintree_public_key")
    @Expose
    private String braintreePublicKey;
    @SerializedName("braintree_private_key")
    @Expose
    private String braintreePrivateKey;
    @SerializedName("referral_count")
    @Expose
    private String referralCount;
    @SerializedName("referral_amount")
    @Expose
    private String referralAmount;
    @SerializedName("referral_text")
    @Expose
    private String referralText;
    @SerializedName("referral_total_count")
    @Expose
    private String referralTotalCount;
    @SerializedName("referral_total_amount")
    @Expose
    private Integer referralTotalAmount;
    @SerializedName("referral_total_text")
    @Expose
    private String referralTotalText;
    @SerializedName("ride_otp")
    @Expose
    private Integer rideOtp;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getFleet() {
        return fleet;
    }

    public void setFleet(Object fleet) {
        this.fleet = fleet;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Object getStripeAccId() {
        return stripeAccId;
    }

    public void setStripeAccId(Object stripeAccId) {
        this.stripeAccId = stripeAccId;
    }

    public Object getStripeCustId() {
        return stripeCustId;
    }

    public void setStripeCustId(Object stripeCustId) {
        this.stripeCustId = stripeCustId;
    }

    public Object getPaypalEmail() {
        return paypalEmail;
    }

    public void setPaypalEmail(Object paypalEmail) {
        this.paypalEmail = paypalEmail;
    }

    public String getLoginBy() {
        return loginBy;
    }

    public void setLoginBy(String loginBy) {
        this.loginBy = loginBy;
    }

    public Object getSocialUniqueId() {
        return socialUniqueId;
    }

    public void setSocialUniqueId(Object socialUniqueId) {
        this.socialUniqueId = socialUniqueId;
    }

    public Integer getOtp() {
        return otp;
    }

    public void setOtp(Integer otp) {
        this.otp = otp;
    }

    public Integer getWalletBalance() {
        return walletBalance;
    }

    public void setWalletBalance(Integer walletBalance) {
        this.walletBalance = walletBalance;
    }

    public Object getReferralUniqueId() {
        return referralUniqueId;
    }

    public void setReferralUniqueId(Object referralUniqueId) {
        this.referralUniqueId = referralUniqueId;
    }

    public String getQrcodeUrl() {
        return qrcodeUrl;
    }

    public void setQrcodeUrl(String qrcodeUrl) {
        this.qrcodeUrl = qrcodeUrl;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getSos() {
        return sos;
    }

    public void setSos(String sos) {
        this.sos = sos;
    }

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Integer getCash() {
        return cash;
    }

    public void setCash(Integer cash) {
        this.cash = cash;
    }

    public Integer getCard() {
        return card;
    }

    public void setCard(Integer card) {
        this.card = card;
    }

    public String getStripeSecretKey() {
        return stripeSecretKey;
    }

    public void setStripeSecretKey(String stripeSecretKey) {
        this.stripeSecretKey = stripeSecretKey;
    }

    public String getStripePublishableKey() {
        return stripePublishableKey;
    }

    public void setStripePublishableKey(String stripePublishableKey) {
        this.stripePublishableKey = stripePublishableKey;
    }

    public String getStripeCurrency() {
        return stripeCurrency;
    }

    public void setStripeCurrency(String stripeCurrency) {
        this.stripeCurrency = stripeCurrency;
    }

    public Integer getPayumoney() {
        return payumoney;
    }

    public void setPayumoney(Integer payumoney) {
        this.payumoney = payumoney;
    }

    public Integer getPaypal() {
        return paypal;
    }

    public void setPaypal(Integer paypal) {
        this.paypal = paypal;
    }

    public Integer getPaypalAdaptive() {
        return paypalAdaptive;
    }

    public void setPaypalAdaptive(Integer paypalAdaptive) {
        this.paypalAdaptive = paypalAdaptive;
    }

    public Integer getBraintree() {
        return braintree;
    }

    public void setBraintree(Integer braintree) {
        this.braintree = braintree;
    }

    public Integer getPaytm() {
        return paytm;
    }

    public void setPaytm(Integer paytm) {
        this.paytm = paytm;
    }

    public String getPayumoneyEnvironment() {
        return payumoneyEnvironment;
    }

    public void setPayumoneyEnvironment(String payumoneyEnvironment) {
        this.payumoneyEnvironment = payumoneyEnvironment;
    }

    public String getPayumoneyKey() {
        return payumoneyKey;
    }

    public void setPayumoneyKey(String payumoneyKey) {
        this.payumoneyKey = payumoneyKey;
    }

    public String getPayumoneySalt() {
        return payumoneySalt;
    }

    public void setPayumoneySalt(String payumoneySalt) {
        this.payumoneySalt = payumoneySalt;
    }

    public String getPayumoneyAuth() {
        return payumoneyAuth;
    }

    public void setPayumoneyAuth(String payumoneyAuth) {
        this.payumoneyAuth = payumoneyAuth;
    }

    public String getPaypalEnvironment() {
        return paypalEnvironment;
    }

    public void setPaypalEnvironment(String paypalEnvironment) {
        this.paypalEnvironment = paypalEnvironment;
    }

    public String getPaypalCurrency() {
        return paypalCurrency;
    }

    public void setPaypalCurrency(String paypalCurrency) {
        this.paypalCurrency = paypalCurrency;
    }

    public String getPaypalClientId() {
        return paypalClientId;
    }

    public void setPaypalClientId(String paypalClientId) {
        this.paypalClientId = paypalClientId;
    }

    public String getPaypalClientSecret() {
        return paypalClientSecret;
    }

    public void setPaypalClientSecret(String paypalClientSecret) {
        this.paypalClientSecret = paypalClientSecret;
    }

    public String getBraintreeEnvironment() {
        return braintreeEnvironment;
    }

    public void setBraintreeEnvironment(String braintreeEnvironment) {
        this.braintreeEnvironment = braintreeEnvironment;
    }

    public String getBraintreeMerchantId() {
        return braintreeMerchantId;
    }

    public void setBraintreeMerchantId(String braintreeMerchantId) {
        this.braintreeMerchantId = braintreeMerchantId;
    }

    public String getBraintreePublicKey() {
        return braintreePublicKey;
    }

    public void setBraintreePublicKey(String braintreePublicKey) {
        this.braintreePublicKey = braintreePublicKey;
    }

    public String getBraintreePrivateKey() {
        return braintreePrivateKey;
    }

    public void setBraintreePrivateKey(String braintreePrivateKey) {
        this.braintreePrivateKey = braintreePrivateKey;
    }

    public String getReferralCount() {
        return referralCount;
    }

    public void setReferralCount(String referralCount) {
        this.referralCount = referralCount;
    }

    public String getReferralAmount() {
        return referralAmount;
    }

    public void setReferralAmount(String referralAmount) {
        this.referralAmount = referralAmount;
    }

    public String getReferralText() {
        return referralText;
    }

    public void setReferralText(String referralText) {
        this.referralText = referralText;
    }

    public String getReferralTotalCount() {
        return referralTotalCount;
    }

    public void setReferralTotalCount(String referralTotalCount) {
        this.referralTotalCount = referralTotalCount;
    }

    public Integer getReferralTotalAmount() {
        return referralTotalAmount;
    }

    public void setReferralTotalAmount(Integer referralTotalAmount) {
        this.referralTotalAmount = referralTotalAmount;
    }

    public String getReferralTotalText() {
        return referralTotalText;
    }

    public void setReferralTotalText(String referralTotalText) {
        this.referralTotalText = referralTotalText;
    }

    public Integer getRideOtp() {
        return rideOtp;
    }

    public void setRideOtp(Integer rideOtp) {
        this.rideOtp = rideOtp;
    }

    /*@SerializedName("id")
    @Expose
    private int id;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("avatar")
    @Expose
    private String avatar;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("fleet")
    @Expose
    private Object fleet;
    @SerializedName("latitude")
    @Expose
    private double latitude;
    @SerializedName("longitude")
    @Expose
    private double longitude;
    @SerializedName("otp")
    @Expose
    private int otp;
    @SerializedName("stripe_acc_id")
    @Expose
    private String stripeAccId;
    @SerializedName("stripe_cust_id")
    @Expose
    private String stripeCustId;
    @SerializedName("wallet_balance")
    @Expose
    private Float walletBalance;
    @SerializedName("negative_balance")
    @Expose
    private int negativeBalance;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("login_by")
    @Expose
    private String loginBy;
    @SerializedName("social_unique_id")
    @Expose
    private Object socialUniqueId;
    @SerializedName("service")
    @Expose
    private Service service;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("sos")
    @Expose
    private String sos;
    @SerializedName("measurement")
    @Expose
    private String measurement;
    @SerializedName("profile")
    @Expose
    private Profile profile;
    @SerializedName("cash")
    @Expose
    private int cash;
    @SerializedName("card")
    @Expose
    private int card;
    @SerializedName("stripe_secret_key")
    @Expose
    private String stripeSecretKey;
    @SerializedName("stripe_publishable_key")
    @Expose
    private String stripePublishableKey;

    @SerializedName("referral_amount")
    @Expose
    private String referral_amount;
    @SerializedName("referral_count")
    @Expose
    private String referral_count;
    @SerializedName("referral_text")
    @Expose
    private String referral_text;
    @SerializedName("referral_total_amount")
    @Expose
    private String referral_total_amount;

    @SerializedName("referral_total_count")
    @Expose
    private String referral_total_count;
    @SerializedName("referral_total_text")
    @Expose
    private String referral_total_text;

    @SerializedName("referral_unique_id")
    @Expose
    private String referral_unique_id;

    @SerializedName("ride_otp")
    @Expose
    private String ride_otp;

    @SerializedName("qrcode_url")
    @Expose
    private String qrcode_url;

    @SerializedName("payumoney")
    @Expose
    private int payumoney;

    @SerializedName("paypal")
    @Expose
    private int paypal;

    @SerializedName("braintree")
    @Expose
    private int braintree;

    @SerializedName("paytm")
    @Expose
    private int paytm;

    @SerializedName("paypal_adaptive")
    @Expose
    private int paypal_adaptive;

    public int getPayumoney() {
        return payumoney;
    }

    public void setPayumoney(int payumoney) {
        this.payumoney = payumoney;
    }

    public int getPaypal() {
        return paypal;
    }

    public void setPaypal(int paypal) {
        this.paypal = paypal;
    }

    public int getBraintree() {
        return braintree;
    }

    public void setBraintree(int braintree) {
        this.braintree = braintree;
    }

    public int getPaytm() {
        return paytm;
    }

    public void setPaytm(int paytm) {
        this.paytm = paytm;
    }

    public int getPaypal_adaptive() {
        return paypal_adaptive;
    }

    public void setPaypal_adaptive(int paypal_adaptive) {
        this.paypal_adaptive = paypal_adaptive;
    }

    public String getQrcode_url() {
        return qrcode_url;
    }

    public void setQrcode_url(String qrcode_url) {
        this.qrcode_url = qrcode_url;
    }


    public String getReferral_amount() {
        return referral_amount;
    }

    public void setReferral_amount(String referral_amount) {
        this.referral_amount = referral_amount;
    }

    public String getReferral_count() {
        return referral_count;
    }

    public void setReferral_count(String referral_count) {
        this.referral_count = referral_count;
    }

    public String getReferral_text() {
        return referral_text;
    }

    public void setReferral_text(String referral_text) {
        this.referral_text = referral_text;
    }

    public String getReferral_total_amount() {
        return referral_total_amount;
    }

    public void setReferral_total_amount(String referral_total_amount) {
        this.referral_total_amount = referral_total_amount;
    }

    public String getReferral_total_count() {
        return referral_total_count;
    }

    public void setReferral_total_count(String referral_total_count) {
        this.referral_total_count = referral_total_count;
    }

    public String getReferral_total_text() {
        return referral_total_text;
    }

    public void setReferral_total_text(String referral_total_text) {
        this.referral_total_text = referral_total_text;
    }

    public String getReferral_unique_id() {
        return referral_unique_id;
    }

    public void setReferral_unique_id(String referral_unique_id) {
        this.referral_unique_id = referral_unique_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getFleet() {
        return fleet;
    }

    public void setFleet(Object fleet) {
        this.fleet = fleet;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getOtp() {
        return otp;
    }

    public void setOtp(int otp) {
        this.otp = otp;
    }

    public String getStripeAccId() {
        return stripeAccId;
    }

    public void setStripeAccId(String stripeAccId) {
        this.stripeAccId = stripeAccId;
    }

    public String getStripeCustId() {
        return stripeCustId;
    }

    public void setStripeCustId(String stripeCustId) {
        this.stripeCustId = stripeCustId;
    }

    public Float getWalletBalance() {
        return walletBalance;
    }

    public void setWalletBalance(Float walletBalance) {
        this.walletBalance = walletBalance;
    }

    public int getNegativeBalance() {
        return negativeBalance;
    }

    public void setNegativeBalance(int negativeBalance) {
        this.negativeBalance = negativeBalance;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getLoginBy() {
        return loginBy;
    }

    public void setLoginBy(String loginBy) {
        this.loginBy = loginBy;
    }

    public Object getSocialUniqueId() {
        return socialUniqueId;
    }

    public void setSocialUniqueId(Object socialUniqueId) {
        this.socialUniqueId = socialUniqueId;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getSos() {
        return sos;
    }

    public void setSos(String sos) {
        this.sos = sos;
    }

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public int getCash() {
        return cash;
    }

    public void setCash(int cash) {
        this.cash = cash;
    }

    public int getCard() {
        return card;
    }

    public void setCard(int card) {
        this.card = card;
    }

    public String getStripeSecretKey() {
        return stripeSecretKey;
    }

    public void setStripeSecretKey(String stripeSecretKey) {
        this.stripeSecretKey = stripeSecretKey;
    }

    public String getStripePublishableKey() {
        return stripePublishableKey;
    }

    public void setStripePublishableKey(String stripePublishableKey) {
        this.stripePublishableKey = stripePublishableKey;
    }

    public String getRide_otp() {
        return ride_otp;
    }

    public void setRide_otp(String ride_otp) {
        this.ride_otp = ride_otp;
    }*/

}
