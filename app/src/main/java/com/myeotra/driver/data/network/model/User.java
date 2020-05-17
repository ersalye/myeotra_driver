package com.myeotra.driver.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    /*@SerializedName("id")
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
    private Integer fleet;
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
    private Double walletBalance;
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
    @SerializedName("access_token")
    @Expose
    private String accessToken;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("sos")
    @Expose
    private String sos;
    @SerializedName("measurement")
    @Expose
    private String measurement;
    @SerializedName("services")
    @Expose
    private Services services;
    @SerializedName("d_status")
    @Expose
    private Integer dStatus;
    @SerializedName("service")
    @Expose
    private Service service;
    @SerializedName("device")
    @Expose
    private Device device;

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

    public Integer getFleet() {
        return fleet;
    }

    public void setFleet(Integer fleet) {
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

    public Double getWalletBalance() {
        return walletBalance;
    }

    public void setWalletBalance(Double walletBalance) {
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

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
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

    public Services getServices() {
        return services;
    }

    public void setServices(Services services) {
        this.services = services;
    }

    public Integer getDStatus() {
        return dStatus;
    }

    public void setDStatus(Integer dStatus) {
        this.dStatus = dStatus;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }
*/

    @SerializedName("id")
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
    private Object avatar;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("fleet")
    @Expose
    private int fleet;
    @SerializedName("latitude")
    @Expose
    private Object latitude;
    @SerializedName("longitude")
    @Expose
    private Object longitude;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("country_code")
    @Expose
    private String countryCode;
    @SerializedName("city_latitude")
    @Expose
    private double cityLatitude;
    @SerializedName("city_longitude")
    @Expose
    private double cityLongitude;
    @SerializedName("referral_id")
    @Expose
    private Object referralId;
    @SerializedName("referral_code")
    @Expose
    private String referralCode;
    @SerializedName("usage_count")
    @Expose
    private Object usageCount;
    @SerializedName("referral_earning")
    @Expose
    private Object referralEarning;
    @SerializedName("usage_limit")
    @Expose
    private Object usageLimit;
    @SerializedName("expired_at")
    @Expose
    private String expiredAt;
    @SerializedName("otp")
    @Expose
    private int otp;
    @SerializedName("trip_type")
    @Expose
    private String tripType;
    @SerializedName("outstation_type")
    @Expose
    private String outstationType;
    @SerializedName("wallet_balance")
    @Expose
    private double walletBalance;
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
    @SerializedName("access_token")
    @Expose
    private String accessToken;
    @SerializedName("service")
    @Expose
    private Service service;
    @SerializedName("country")
    @Expose
    private Country country;
    @SerializedName("sos")
    @Expose
    private String sos;
    @SerializedName("device")
    @Expose
    private Device device;


    @SerializedName("message")
    @Expose
    private String message;


    @SerializedName("d_status")
    @Expose
    private Integer dStatus;
    @SerializedName("d_message")
    @Expose
    private String dMessage;


    public Integer getDStatus() {
        return dStatus;
    }

    public void setDStatus(Integer dStatus) {
        this.dStatus = dStatus;
    }

    public String getDMessage() {
        return dMessage;
    }

    public void setDMessage(String dMessage) {
        this.dMessage = dMessage;
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

    public Object getAvatar() {
        return avatar;
    }

    public void setAvatar(Object avatar) {
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

    public int getFleet() {
        return fleet;
    }

    public void setFleet(int fleet) {
        this.fleet = fleet;
    }

    public Object getLatitude() {
        return latitude;
    }

    public void setLatitude(Object latitude) {
        this.latitude = latitude;
    }

    public Object getLongitude() {
        return longitude;
    }

    public void setLongitude(Object longitude) {
        this.longitude = longitude;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public double getCityLatitude() {
        return cityLatitude;
    }

    public void setCityLatitude(double cityLatitude) {
        this.cityLatitude = cityLatitude;
    }

    public double getCityLongitude() {
        return cityLongitude;
    }

    public void setCityLongitude(double cityLongitude) {
        this.cityLongitude = cityLongitude;
    }

    public Object getReferralId() {
        return referralId;
    }

    public void setReferralId(Object referralId) {
        this.referralId = referralId;
    }

    public String getReferralCode() {
        return referralCode;
    }

    public void setReferralCode(String referralCode) {
        this.referralCode = referralCode;
    }

    public Object getUsageCount() {
        return usageCount;
    }

    public void setUsageCount(Object usageCount) {
        this.usageCount = usageCount;
    }

    public Object getReferralEarning() {
        return referralEarning;
    }

    public void setReferralEarning(Object referralEarning) {
        this.referralEarning = referralEarning;
    }

    public Object getUsageLimit() {
        return usageLimit;
    }

    public void setUsageLimit(Object usageLimit) {
        this.usageLimit = usageLimit;
    }

    public String getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(String expiredAt) {
        this.expiredAt = expiredAt;
    }

    public int getOtp() {
        return otp;
    }

    public void setOtp(int otp) {
        this.otp = otp;
    }

    public String getTripType() {
        return tripType;
    }

    public void setTripType(String tripType) {
        this.tripType = tripType;
    }

    public String getOutstationType() {
        return outstationType;
    }

    public void setOutstationType(String outstationType) {
        this.outstationType = outstationType;
    }

    public double getWalletBalance() {
        return walletBalance;
    }

    public void setWalletBalance(double walletBalance) {
        this.walletBalance = walletBalance;
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

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getSos() {
        return sos;
    }

    public void setSos(String sos) {
        this.sos = sos;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

}
