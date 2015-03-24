package com.peelworks.hul.me.utils;

/**
 * Created by exort on 17/3/15.
 */
public class WSConstants {
    public static final Integer volleyRequestTimeout = 5000;
    //    public static final String baseURL = "https://9yards.peel-works.com/peelworkscustomer";

  public static final String baseURL = "http://test1.peel-works.com/peelworkscustomer";
  public static final String forgotPassword = "/appLogin/forgetPassword";
  public static final String changePassword = "/appLogin/savePassword";
  public static final String login = "/appLogin/login";
  public static final String securityQuestion = "/appLogin/securityQuestion";
    public static final String payslip = "/appSalary/paySlipDetails";
    public static final String attendanceType = "/appAttendance/getAttendanceType";
    public static final String attendanceSubmit = "/appAttendance/saveAttOfEmps";
    public static final String updateLocation = "/track/updateLocationTracker";
    public static final String getIncentivePercentage = "/appIncentive/achievementDetails";
    public static final String viewAllParametersOfEmployee = "/appIncentive/viewAllParametersOfEmployee";
    public static final String feedbackWS = "/appIncentive/feedBackDetails";


}
