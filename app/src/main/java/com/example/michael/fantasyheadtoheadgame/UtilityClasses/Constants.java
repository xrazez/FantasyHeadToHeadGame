package com.example.michael.fantasyheadtoheadgame.UtilityClasses;

/**
 * Created by michaelgeehan on 30/03/2017.
 */

public class Constants {
    
    //server constants
    //public static String IP_ADDRESS = "http://10.0.2.2:8888/FantasyShowDown/";
    public static String IP_ADDRESS = "https://fantasyshowdown.000webhostapp.com/FantasyShowDown/";
    public static String LOGIN_ADDRESS = IP_ADDRESS+"login.php?";
    public static String REGISTER_ADDRESS = IP_ADDRESS+"register.php?";
    public static String BUDGET_ADDRESS = IP_ADDRESS+"getbudget.php?";
    public static String DEADLINE_ADDRESS = IP_ADDRESS+"getdeadline.php";
    public static String USERTEAM_ADDRESS = IP_ADDRESS+"getuserteam.php?";
    public static String CHECKINVITES_ADDRESS = IP_ADDRESS+"checknewinvites.php?";
    public static String FINDCONTEST_ADDRESS = IP_ADDRESS+"findheadtoheadmatch.php?";
    public static String SENDINVITE_ADDRESS = IP_ADDRESS+"findspecifiedheadtoheadmatch.php?";
    public static String REPLY_TO_INVITE_ADDRESS = IP_ADDRESS+"replytoinvite.php?";
    public static String UPDATE_USERTEAM_ADDRESS = IP_ADDRESS+"updateuserteam.php?";
    public static String SEARCH_ADDRESS = IP_ADDRESS+"searchforplayer.php?";
    public static String GET_USER_CONTESTS = IP_ADDRESS+"getusermatches.php?";


    //Input error messages
    public static final String USERNAME_EMPTY = "Please enter a username!";
    public static final String PASSWORD_EMPTY = "Please enter a password!";
    public static final String INVALID_USERNAME = "That is an invalid username!";
    public static final String EMAIL_EMPTY = "Please enter an email!";
    public static final String FULLNAME_EMPTY = "Please enter a name!";
    
    //MainHub strings
    
}
