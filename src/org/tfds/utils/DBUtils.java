package org.tfds.utils;

import java.sql.*;
import java.util.*;
import java.util.Date;

import static org.tfds.AppConstants.*;

import org.tfds.beans.*;

public class DBUtils {

    public static Packet<Profile> logIn(Connection conn, String profileId, String password) {
        String query = "SELECT F.* FROM Profile F, Person S " +
                "WHERE F.OwnerSSN = S.SSN AND F.ProfileID = ? AND S.password = ?;";
        String updateQuery = "UPDATE User " +
                "SET DateOfLastAct = ? " +
                "WHERE SSN = ?;";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, profileId);
            ps.setString(2, password);
            ResultSet res = ps.executeQuery();

            Packet<Profile> packet = profileGenerator(res);

            ps.close();
            res.close();

            if (packet.isSuccessful()) {
                PreparedStatement updateStm = conn.prepareStatement(updateQuery);
                updateStm.setTimestamp(1, generateTimestamp());
                updateStm.setString(2, packet.getEntity().getSsn());
                updateStm.executeUpdate();
                conn.commit();
                updateStm.close();
            }
            return packet;
        } catch (Exception e) {		// Maybe change this back to sql exception only
//            e.printStackTrace();
            return new Packet<>(FAILED, e.getMessage(), null);
        }
    }

    public static Packet<Employee> employeeLogIn(Connection conn, String SSN, String password) {
        String query = "SELECT E.* FROM Employee E, Person P " +
                "WHERE E.SSN = P.SSN AND P.SSN = ? AND P.password = ?;";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, SSN);
            ps.setString(2, password);
            ResultSet res = ps.executeQuery();

            Packet<Employee> packet = employeeGenerator(res);

            ps.close();
            res.close();
            return packet;
        } catch (SQLException e) {
            e.printStackTrace();
            return new Packet<>(FAILED, e.getMessage(), null);
        }
    }

    public static Packet<Entity> like(Connection conn, String likerProfile, String likeeProfile) {
        String query = "INSERT INTO Likes " +
                "VALUES(?, ?, ?);";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, likerProfile);
            ps.setString(2, likeeProfile);
            ps.setTimestamp(3, generateTimestamp());
            String lines = ps.executeUpdate() + AFFECTED_ROLES;
            conn.commit();
            ps.close();
            return new Packet<>(SUCCEEDED, lines, null);
        } catch (SQLException e) {
            e.printStackTrace();
            return new Packet<>(FAILED, e.getMessage(), null);
        }
    }

    private static Timestamp generateTimestamp() {
        Date date = new Date();
        return new Timestamp(date.getTime());
    }

    public static Packet<ArrayList<String>> getFavourites(Connection conn, String profileId) {
        String query = "SELECT L.Likee AS likee, COUNT(*) AS counts " +
                "FROM Likes L WHERE L.Liker = ? " +
                "GROUP BY L.Likee ORDER BY counts DESC;";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, profileId);
            ResultSet res = ps.executeQuery();

            ArrayList<String> likees = new ArrayList<>();
            while (res.next()) {
                likees.add(res.getString("likee"));
            }
            return new Packet<>(true, "", likees);
        } catch (SQLException e) {
            e.printStackTrace();
            return new Packet<>(false, e.getMessage(), null);
        }
    }

    public static Packet<ArrayList<Like>> getUserLikes(Connection conn, String profileId) {
        String query = "SELECT * " +
                "FROM Likes L WHERE L.Liker = ? " +
                "ORDER BY L.Date_Time DESC;";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, profileId);
            ResultSet res = ps.executeQuery();

            ArrayList<Like> likees = new ArrayList<>();
            while (res.next()) {
                String liker = res.getString(Like.LIKER);
                String likee = res.getString(Like.LIKEE);
                Date date = res.getTimestamp(Like.DATE_TIME);
                likees.add(new Like(liker, likee, date));
            }
            return new Packet<>(true, "", likees);
        } catch (SQLException e) {
            e.printStackTrace();
            return new Packet<>(false, e.getMessage(), null);
        }
    }

    public static Packet<ArrayList<Like>> getUserBeLiked(Connection conn, String profileId) {
        String query = "SELECT * " +
                "FROM Likes L WHERE L.Likee = ? " +
                "ORDER BY L.Date_Time DESC;";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, profileId);
            ResultSet res = ps.executeQuery();

            ArrayList<Like> likers = new ArrayList<>();
            while (res.next()) {
                String liker = res.getString(Like.LIKER);
                String likee = res.getString(Like.LIKEE);
                Date date = res.getTimestamp(Like.DATE_TIME);
                likers.add(new Like(liker, likee, date));
            }
            return new Packet<>(true, "", likers);
        } catch (SQLException e) {
            e.printStackTrace();
            return new Packet<>(false, e.getMessage(), null);
        }
    }

    public static Packet<Entity> createCustomerDate(Connection conn, String profileId1, String profileId2, Date planningDateTime) {
        String query = "INSERT INTO CustomerDate(Profile1, Profile2, Date_Time, BookingFee) " +
                "VALUES (?, ?, ?, ?);";
        Random r = new Random();
        double bookingFee = 50 + 500 * Math.abs(r.nextGaussian());
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, profileId1);
            ps.setString(2, profileId2);
            ps.setTimestamp(3, new Timestamp(planningDateTime.getTime()));
            ps.setDouble(4, bookingFee);

            String lines = ps.executeUpdate() + AFFECTED_ROLES;
            conn.commit();
            ps.close();
            return new Packet<>(true, lines, null);
        } catch (SQLException e) {
            e.printStackTrace();
            return new Packet<>(false, e.getMessage(), null);
        }
    }

    public static Packet<Entity> createCustomerDate(Connection conn, String profileId1, String profileId2, String custRep, Date planningDateTime) {
        String query = "INSERT INTO CustomerDate(Profile1, Profile2, CustRep, Date_Time, BookingFee) " +
                "VALUES (?, ?, ?, ?, ?);";
        Random r = new Random();
        double bookingFee = 50 + 500 * Math.abs(r.nextGaussian());
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, profileId1);
            ps.setString(2, profileId2);
            ps.setString(3, custRep);
            ps.setTimestamp(4, new Timestamp(planningDateTime.getTime()));
            ps.setDouble(5, bookingFee);

            String lines = ps.executeUpdate() + AFFECTED_ROLES;
            conn.commit();
            ps.close();
            return new Packet<>(true, lines, null);
        } catch (SQLException e) {
            e.printStackTrace();
            return new Packet<>(false, e.getMessage(), null);
        }
    }

    public static Packet<Entity> createReferredDate(Connection conn, String profileId1, String profileId2, String referrerId) {
        String query = "INSERT INTO BlindDate(ProfileA, ProfileB, ProfileC, Date_Time) " +
                "VALUES (?, ?, ?, ?);";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, referrerId);
            ps.setString(2, profileId1);
            ps.setString(3, profileId2);
            Date now = new Date();
            ps.setTimestamp(4, new Timestamp(now.getTime()));

            String lines = ps.executeUpdate() + AFFECTED_ROLES;

            ps.close();
            conn.commit();
            return new Packet<>(true, lines, null);
        } catch (SQLException e) {
            e.printStackTrace();
            return new Packet<>(false, e.getMessage(), null);
        }
    }

    // message is the line number
    public static Packet<ArrayList<BlindDate>> getReferrals(Connection conn, String profileId) {
        String query = "SELECT * FROM BlindDate WHERE ProfileC = ? OR ProfileB = ?;";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, profileId);
            ps.setString(2, profileId);
            ResultSet res = ps.executeQuery();

            ArrayList<BlindDate> dates = new ArrayList<>();

            int count = 0;
            while (res.next()) {
                count++;
                String a = res.getString(BlindDate.PROFILE_A);
                String b = res.getString(BlindDate.PROFILE_B);
                String c = res.getString(BlindDate.PROFILE_C);
                Date dateTime = res.getTimestamp(BlindDate.DATE_TIME);
                dates.add(new BlindDate(a, b, c, dateTime));
            }
            String msg = count + COLLECTED_ROLES;
            return new Packet<>(SUCCEEDED, msg, dates);
        } catch (SQLException e) {
            e.printStackTrace();
            return new Packet<>(FAILED, e.getMessage(), null);
        }
    }

    // message is the line number
    public static Packet<ArrayList<SuggestedBy>> getSuggestedBy(Connection conn, String profileId) {
        String query = "SELECT * FROM SuggestedBy WHERE Profile1 = ? OR Profile2 = ?;";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, profileId);
            ps.setString(2, profileId);
            ResultSet res = ps.executeQuery();

            ArrayList<SuggestedBy> dates = new ArrayList<>();

            int count = 0;
            while (res.next()) {
                count++;
                String fst = res.getString(SuggestedBy.PROFILE1);
                String scd = res.getString(SuggestedBy.PROFILE2);
                String sugBy = res.getString(SuggestedBy.CUST_REP);
                Date dateTime = res.getTimestamp(SuggestedBy.DATE_TIME);
                dates.add(new SuggestedBy(sugBy, fst, scd, dateTime));
            }
            String msg = count + COLLECTED_ROLES;
            return new Packet<>(SUCCEEDED, msg, dates);
        } catch (SQLException e) {
            e.printStackTrace();
            return new Packet<>(FAILED, e.getMessage(), null);
        }
    }

    public static Packet<Entity> createSuggestion(Connection conn, String profileId1, String profileId2, String repID) {
        String query = "INSERT INTO SuggestedBy(CustRep, Profile1, Profile2, Date_Time) " +
                "VALUES (?, ?, ?, ?);";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, repID);
            ps.setString(2, profileId1);
            ps.setString(3, profileId2);
            Date now = new Date();
            ps.setTimestamp(4, new Timestamp(now.getTime()));

            String lines = ps.executeUpdate() + AFFECTED_ROLES;

            ps.close();
            conn.commit();
            return new Packet<>(true, lines, null);
        } catch (SQLException e) {
            e.printStackTrace();
            return new Packet<>(false, e.getMessage(), null);
        }
    }

    public static Packet<Entity> createGeoDate(Connection conn, String profileId1, 
    		String profileId2, String location, Date planningDateTime) {
        String query = "INSERT INTO CustomerDate(Profile1, Profile2, Date_Time, Location, BookingFee) " +
                "VALUES (?, ?, ?, ?, ?);";
        Random r = new Random();
        double bookingFee = 50 + 500 * Math.abs(r.nextGaussian());
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, profileId1);
            ps.setString(2, profileId2);
            ps.setTimestamp(3, new Timestamp(planningDateTime.getTime()));
            ps.setString(4, location);
            ps.setDouble(5, bookingFee);

            String lines = ps.executeUpdate() + AFFECTED_ROLES;
            conn.commit();
            ps.close();
            return new Packet<>(true, lines, null);
        } catch (SQLException e) {
            e.printStackTrace();
            return new Packet<>(false, e.getMessage(), null);
        }
    }

    public static Packet<ArrayList<CustomerDate>> getPendingDates(Connection conn, String profileId) {
        String query = "SELECT * " +
                "FROM CustomerDate D " +
                "WHERE (? = D.Profile1 XOR ? = D.Profile2) AND D.Date_Time > NOW() " +
                "ORDER BY D.Date_Time ASC;";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, profileId);
            ps.setString(2, profileId);

            ResultSet res = ps.executeQuery();

            ArrayList<CustomerDate> dates = new ArrayList<>();

            int count = 0;
            while (res.next()) {
                String id = res.getString(CustomerDate.DATE_ID);
                String pf1 = res.getString(CustomerDate.PROFILE_1);
                String pf2 = res.getString(CustomerDate.PROFILE_2);
                String rep = res.getString(CustomerDate.CUSTOMER_REP);
                Date dateTime = res.getTimestamp(CustomerDate.DATE_TIME);
                String location = res.getString(CustomerDate.LOCATION);
                double fee = res.getDouble(CustomerDate.BOOKING_FEE);
                String comments = res.getString(CustomerDate.COMMENTS);
                int rate1 = res.getInt(CustomerDate.USER1_RATING);
                int rate2 = res.getInt(CustomerDate.USER2_RATING);

                CustomerDate cd = new CustomerDate(id, pf1, pf2, rep, dateTime, location, fee, comments, rate1, rate2);
                count++;
                dates.add(cd);
            }
            String message = count + COLLECTED_ROLES;
            ps.close();
            res.close();
            return new Packet<>(SUCCEEDED, message, dates);
        } catch (SQLException e) {
            e.printStackTrace();
            return new Packet<>(FAILED, e.getMessage(), null);
        }
    }

    public static Packet<ArrayList<CustomerDate>> getPastDates(Connection conn, String profileId) {
        String query = "SELECT * " +
                "FROM CustomerDate D " +
                "WHERE (? = D.Profile1 XOR ? = D.Profile2) AND D.Date_Time <= NOW() " +
                "ORDER BY D.Date_Time DESC;";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, profileId);
            ps.setString(2, profileId);

            ResultSet res = ps.executeQuery();

            ArrayList<CustomerDate> dates = new ArrayList<>();

            int count = 0;
            while (res.next()) {
                String id = res.getString(CustomerDate.DATE_ID);
                String pf1 = res.getString(CustomerDate.PROFILE_1);
                String pf2 = res.getString(CustomerDate.PROFILE_2);
                String rep = res.getString(CustomerDate.CUSTOMER_REP);
                Date dateTime = res.getTimestamp(CustomerDate.DATE_TIME);
                String location = res.getString(CustomerDate.LOCATION);
                double fee = res.getDouble(CustomerDate.BOOKING_FEE);
                String comments = res.getString(CustomerDate.COMMENTS);
                int rate1 = res.getInt(CustomerDate.USER1_RATING);
                int rate2 = res.getInt(CustomerDate.USER2_RATING);

                CustomerDate cd = new CustomerDate(id, pf1, pf2, rep, dateTime, location, fee, comments, rate1, rate2);
                count++;
                dates.add(cd);
            }
            String message = count + COLLECTED_ROLES;
            ps.close();
            res.close();
            return new Packet<>(SUCCEEDED, message, dates);
        } catch (SQLException e) {
            e.printStackTrace();
            return new Packet<>(FAILED, e.getMessage(), null);
        }
    }

    public static Packet<Entity> cancelDates(Connection conn, String dateId) {
        String query = "DELETE FROM CustomerDate WHERE DateId = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            int dateIntId = Integer.parseInt(dateId);
            ps.setInt(1, dateIntId);

            String lines = ps.executeUpdate() + AFFECTED_ROLES;
            conn.commit();
            ps.close();

            return new Packet<>(SUCCEEDED, lines, null);
        } catch (SQLException e) {
            e.printStackTrace();
            return new Packet<>(FAILED, e.getMessage(), null);
        }
    }


    // Comments are line separated.
    public static Packet<Entity> writeComment(Connection conn, String profileId, String dateId, String comment) {
        String query1 = "SELECT Comments " +
                "FROM CustomerDate " +
                "WHERE DateId = ?";
        String query2 = "UPDATE CustomerDate " +
                "SET Comments = ? " +
                "WHERE DateId = ?;";
        try {
            PreparedStatement ps1 = conn.prepareStatement(query1);
            PreparedStatement ps2 = conn.prepareStatement(query2);
            ps1.setString(1, dateId);
            ResultSet res = ps1.executeQuery();
            res.next();
            String oldComment = res.getString(CustomerDate.COMMENTS);
            res.close();
            ps1.close();
            Date now = new Date();
            String newComment = "<b>" + profileId + "</b>: " + comment + "<br/><i>" + now.toString() + "</i><br/>";
            if (oldComment != null)
                newComment = oldComment + newComment;
            ps2.setString(1, newComment);
            ps2.setString(2, dateId);
            String lines = ps2.executeUpdate() + AFFECTED_ROLES;
            conn.commit();
            ps2.close();

            return new Packet<>(SUCCEEDED, lines, null);
        } catch (SQLException e) {
            e.printStackTrace();
            return new Packet<>(FAILED, e.getMessage(), null);
        }
    }


    // Fetchers
    // Return the type of the account only. Use fetchProfile, fetchPerson, fetchEmployee for further operation.
    public static Packet<Account> getAccount(Connection conn, String info) {
        String query1 = "SELECT Role FROM EMPLOYEE WHERE SSN = ?;";
        String query2 = "SELECT OwnerSSN FROM Profile WHERE ProfileID = ?;";
        String query3 = "SELECT * FROM Account WHERE OwnerSSN = ?;";
        try {
            Account.AccountType type;
            PreparedStatement ps1 = conn.prepareStatement(query1);
            ps1.setString(1, info);
            ResultSet res1 = ps1.executeQuery();
            if (res1.next()) {
                String role = res1.getString(Employee.ROLE);
                if (role.equals("Manager")) {
                    type = Account.AccountType.MANAGER;
                } else {
                    type = Account.AccountType.EMPLOYEE;
                }
            } else {
                PreparedStatement ps2 = conn.prepareStatement(query2);
                ps2.setString(1, info);
                ResultSet res2 = ps2.executeQuery();
                if (res2.next()) {
                    info = res2.getString(Profile.SSN);
                    type = Account.AccountType.CUSTOMER;
                } else {
                    return new Packet<>(FAILED, QUERY_ERROR_EMPTY_SET, null);
                }
                ps2.close();
                res2.close();
            }
            ps1.close();
            res1.close();
            // TODO: You can get rid of this kinda of Zhou
//            PreparedStatement ps3 = conn.prepareStatement(query3);
//            ps3.setString(1, info);
//            ResultSet res3 = ps3.executeQuery();
//            res3.next();
//            String ssn = res3.getString(Account.SSN);
//            String card = res3.getString(Account.CARD_NUMBER);
//            String acn = res3.getString(Account.ACCOUNT_NUMBER);
//            Date date = res3.getTimestamp(Account.CREATION_DATE);
//            Account acc = new Account(ssn, card, acn, date, type);
            Account acc = new Account(info, null, null, null, type);
            return new Packet<>(SUCCEEDED, null, acc);
        } catch (SQLException e) {
            e.printStackTrace();
            return new Packet<>(FAILED, e.getMessage(), null);
        }
    }

    public static Packet<ArrayList<Account>> getBankAccount(Connection conn, String info) {
        String query1 = "SELECT Role FROM EMPLOYEE WHERE SSN = ?;";
        String query2 = "SELECT OwnerSSN FROM Profile WHERE ProfileID = ?;";
        String query3 = "SELECT * FROM Account WHERE OwnerSSN = ?;";
        try {
            Account.AccountType type;
            PreparedStatement ps1 = conn.prepareStatement(query1);
            ps1.setString(1, info);
            ResultSet res1 = ps1.executeQuery();
            if (res1.next()) {
                String role = res1.getString(Employee.ROLE);
                if (role.equals("Manager")) {
                    type = Account.AccountType.MANAGER;
                } else {
                    type = Account.AccountType.EMPLOYEE;
                }
            } else {
                PreparedStatement ps2 = conn.prepareStatement(query2);
                ps2.setString(1, info);
                ResultSet res2 = ps2.executeQuery();
                if (res2.next()) {
                    info = res2.getString(Profile.SSN);
                    type = Account.AccountType.CUSTOMER;
                } else {
                    return new Packet<>(FAILED, QUERY_ERROR_EMPTY_SET, null);
                }
                ps2.close();
                res2.close();
            }
            ps1.close();
            res1.close();
            PreparedStatement ps3 = conn.prepareStatement(query3);
            ps3.setString(1, info);
            ResultSet res3 = ps3.executeQuery();
            ArrayList<Account> accounts = new ArrayList<>();
            while (res3.next()) {
                String ssn = res3.getString(Account.SSN);
                String card = res3.getString(Account.CARD_NUMBER);
                String acn = res3.getString(Account.ACCOUNT_NUMBER);
                Date date = res3.getTimestamp(Account.CREATION_DATE);
                Account acc = new Account(ssn, card, acn, date, type);
                accounts.add(acc);
            }
            String msg = accounts.size() + COLLECTED_ROLES;
            return new Packet<>(SUCCEEDED, msg, accounts);
        } catch (SQLException e) {
            e.printStackTrace();
            return new Packet<>(FAILED, e.getMessage(), null);
        }
    }

    public static Packet<Person> fetchPerson(Connection conn, String ssn) {
        String query = "SELECT * FROM Person WHERE SSN = ?;";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, ssn);
            ResultSet res = ps.executeQuery();

            Packet<Person> person = personGenerator(res);

            ps.close();
            res.close();
            return person;
        } catch (SQLException e) {
            e.printStackTrace();
            return new Packet<>(FAILED, e.getMessage(), null);
        }
    }

    // Only for user
    public static Packet<Person> fetchPersonFromPID(Connection conn, String profileId) {
        String query = "SELECT P.* FROM Person P, Profile F WHERE F.ProfileID = ? AND P.SSN = F.OwnerSSN;";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, profileId);
            ResultSet res = ps.executeQuery();

            Packet<Person> person = personGenerator(res);

            ps.close();
            res.close();
            return person;
        } catch (SQLException e) {
            e.printStackTrace();
            return new Packet<>(FAILED, e.getMessage(), null);
        }
    }

    private static Packet<Person> personGenerator(ResultSet res) {
        Person person;
        String message = null;
        boolean isSuccessful = true;

        try {
            if (res.next()) {
                String ssn = res.getString(Person.SSN);
                String password = res.getString(Person.PASSWORD);
                String fn = res.getString(Person.FIRST_NAME);
                String ln = res.getString(Person.LAST_NAME);
                String street = res.getString(Person.STREET);
                String city = res.getString(Person.CITY);
                String state = res.getString(Person.STATE);
                int zip = res.getInt(Person.ZIPCODE);
                String email = res.getString(Person.EMAIL);
                String phone = res.getString(Person.TELEPHONE);

                person = new Person(ssn, password, fn, ln, street, city, state, zip, email, phone);
            } else {
                person = null;
                isSuccessful = false;
                message = QUERY_ERROR_EMPTY_SET;
            }
            if (res.next()) {
                isSuccessful = false;
                message = QUERY_ERROR_INCONSISTENT_DATABASE;
            }
            return new Packet<>(isSuccessful, message, person);
        } catch (SQLException e) {
            e.printStackTrace();
            return new Packet<>(FAILED, e.getMessage(), null);
        }
    }

    public static Packet<Employee> fetchEmployee(Connection conn, String ssn) {
        String query = "SELECT * FROM EMPLOYEE WHERE SSN = ?;";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, ssn);
            ResultSet res = ps.executeQuery();

            Packet<Employee> employee = employeeGenerator(res);

            ps.close();
            res.close();
            return employee;
        } catch (SQLException e) {
            e.printStackTrace();
            return new Packet<>(FAILED, e.getMessage(), null);
        }
    }

    private static Packet<Employee> employeeGenerator(ResultSet res) {
        Employee employee;
        String message = null;
        boolean isSuccessful = true;

        try {
            if (res.next()) {
                String ssn = res.getString(Employee.SSN);
                String role = res.getString(Employee.ROLE);
                Date date = res.getTimestamp(Employee.START_DATE);
                int hourly = res.getInt(Employee.HOURLY_RATE);

                employee = new Employee(ssn, role, date, hourly);
            } else {
                employee = null;
                isSuccessful = false;
                message = QUERY_ERROR_EMPTY_SET;
            }
            if (res.next()) {
                isSuccessful = false;
                message = QUERY_ERROR_INCONSISTENT_DATABASE;
            }
            return new Packet<>(isSuccessful, message, employee);
        } catch (SQLException e) {
            e.printStackTrace();
            return new Packet<>(FAILED, e.getMessage(), null);
        }
    }

    public static Packet<User> fetchUser(Connection conn, String ssn) {
        String query = "SELECT * FROM User WHERE SSN = ?;";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, ssn);
            ResultSet res = ps.executeQuery();

            Packet<User> user = userGenerator(res);

            ps.close();
            res.close();
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return new Packet<>(FAILED, e.getMessage(), null);
        }
    }

    // Only for User
    public static Packet<User> fetchUserFromPID(Connection conn, String profileId) {
        String query = "SELECT U.* FROM User U, Profile P WHERE U.SSN = P.OwnerSSN AND P.ProfileID = ?;";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, profileId);
            ResultSet res = ps.executeQuery();

            Packet<User> user = userGenerator(res);

            ps.close();
            res.close();
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return new Packet<>(FAILED, e.getMessage(), null);
        }
    }

    private static Packet<User> userGenerator(ResultSet res) {
        User user;
        String message = null;
        boolean isSuccessful = true;

        try {
            if (res.next()) {
                String ssn = res.getString(User.SSN);
                String ppp = res.getString(User.PPP);
                Date active = res.getTimestamp(User.DATE_LAST_ACTIVE);
                int rating = res.getInt(User.RATING);

                user = new User(ssn, ppp, rating, active);
            } else {
                user = null;
                isSuccessful = false;
                message = QUERY_ERROR_EMPTY_SET;
            }
            if (res.next()) {
                isSuccessful = false;
                message = QUERY_ERROR_INCONSISTENT_DATABASE;
            }
            return new Packet<>(isSuccessful, message, user);
        } catch (SQLException e) {
            e.printStackTrace();
            return new Packet<>(false, e.getMessage(), null);
        }
    }

    public static Packet<Profile> fetchProfile(Connection conn, String profileId) {
        String query = "SELECT P.* FROM Profile P WHERE P.ProfileID = ?;";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, profileId);
            ResultSet res = ps.executeQuery();

            Packet<Profile> packet = profileGenerator(res);

            ps.close();
            res.close();
            return packet;
        } catch (SQLException e) {
            e.printStackTrace();
            return new Packet<>(false, e.getMessage(), null);
        }

    }

    private static Packet<Profile> profileGenerator(ResultSet res) {
        Profile profile;
        String message = null;
        boolean isSuccessful = true;

        try {
            if (res.next()) {
                String profileID = res.getString(Profile.PROFILE_ID);
                String ssn = res.getString(Profile.SSN);
                int age = res.getInt(Profile.AGE);
                int datingAgeRangeStart = res.getInt(Profile.DATING_AGE_RANGE_START);
                int datingAgeRangeEnd = res.getInt(Profile.DATING_AGE_RANGE_END);
                int datingGeoRange = res.getInt(Profile.DATING_GEO_RANGE);
                String gender = res.getString(Profile.GENDER);
                String hobbies = res.getString(Profile.HOBBIES);
                double height = res.getDouble(Profile.HEIGHT);
                double weight = res.getDouble(Profile.WEIGHT);
                String hairColor = res.getString(Profile.HAIR_COLOR);
                Date creationDate = res.getTimestamp(Profile.CREATION_DATE);
                Date lastModDate = res.getTimestamp(Profile.LAST_MOD_DATE);

                profile = new Profile(profileID, ssn, age, datingAgeRangeStart,
                        datingAgeRangeEnd, datingGeoRange, gender, hobbies, height,
                        weight, hairColor, creationDate, lastModDate);
            } else {
                profile = null;
                isSuccessful = false;
                message = QUERY_ERROR_EMPTY_SET;
            }
            if (res.next()) {
                isSuccessful = false;
                message = QUERY_ERROR_INCONSISTENT_DATABASE;
            }
            return new Packet<>(isSuccessful, message, profile);
        } catch (SQLException e) {
            e.printStackTrace();
            return new Packet<>(false, e.getMessage(), null);
        }
    }

    // Fetchers Ends

    // Return profileId, use fetchProfile to get a profile
    public static Packet<ArrayList<String>> getMostActiveProfiles(Connection conn) {
        String query = "SELECT P.ProfileID AS pid " +
                "FROM Profile P, CustomerDate D " +
                "WHERE (P.ProfileID = D.Profile1 XOR P.ProfileID = D.Profile2) AND D.Date_Time <= NOW() AND D.Date_Time >= DATE_SUB(NOW(),INTERVAL 1 YEAR) " +
                "GROUP BY P.ProfileID ORDER BY COUNT(P.ProfileID) DESC;";

        try {
            Statement st = conn.createStatement();
            ResultSet res = st.executeQuery(query);

            ArrayList<String> profileIds = new ArrayList<>();
            int count = 0;
            while (res.next()) {
                String id = res.getString("pid");
                profileIds.add(id);
                count++;
            }
            String msg = count + COLLECTED_ROLES;
            return new Packet<>(SUCCEEDED, msg, profileIds);
        } catch (SQLException e) {
            e.printStackTrace();
            return new Packet<>(FAILED, e.getMessage(), null);
        }
    }

    public static Packet<ArrayList<String[]>> getHighlyRatedProfiles(Connection conn) {
        String query = "SELECT P.ProfileID AS pid, U.Rating AS rating " +
                "FROM Profile P, User U " +
                "WHERE P.OwnerSSN = U.SSN " +
                "ORDER BY U.Rating DESC;";

        try {
            Statement st = conn.createStatement();
            ResultSet res = st.executeQuery(query);

            ArrayList<String[]> profileIds = new ArrayList<>();
            int count = 0;
            while (res.next()) {
                String[] info = new String[2];
                String id = res.getString("pid");
                String rate  = res.getString("rating");
                info[0] = id;
                info[1] = rate;
                profileIds.add(info);
                count++;
            }
            st.close();
            res.close();
            String msg = count + COLLECTED_ROLES;
            return new Packet<>(SUCCEEDED, msg, profileIds);
        } catch (SQLException e) {
            e.printStackTrace();
            return new Packet<>(FAILED, e.getMessage(), null);
        }
    }

    public static Packet<ArrayList<String>> getPopularLocations(Connection conn) {
        String query = "SELECT Location FROM CustomerDate " +
                "GROUP BY Location ORDER BY COUNT(Location) DESC;";

        try {
            Statement st = conn.createStatement();
            ResultSet res = st.executeQuery(query);

            ArrayList<String> locations = new ArrayList<>();
            int count = 0;
            while (res.next()) {
                String location = res.getString(CustomerDate.LOCATION);
                locations.add(location);
                count++;
            }
            st.close();
            res.close();
            String msg = count + COLLECTED_ROLES;
            return new Packet<>(SUCCEEDED, msg, locations);
        } catch (SQLException e) {
            e.printStackTrace();
            return new Packet<>(FAILED, e.getMessage(), null);
        }
    }

    public static Packet<ArrayList<String[]>> getHighestGrossingCustomers(Connection conn) {
    	String query = "SELECT P.ProfileID, SUM(D.BookingFee) AS Total " +
                "FROM Profile P, CustomerDate D " +
                "WHERE D.Profile1 = P.ProfileID OR D.Profile2 = P.ProfileID " +
    			"GROUP BY P.ProfileID " +
                "ORDER BY Total DESC LIMIT 5;";
    	
    	ArrayList<String[]> customers = new ArrayList<>();
    	
    	try  {
    		Statement statement = conn.createStatement();
    		ResultSet res = statement.executeQuery(query);
    		
    		while (res.next()) {
    			String profileId, total;

    			profileId = res.getString(Profile.PROFILE_ID);
    			total = res.getString("Total");
    			String[] arr = new String[2];
    			arr[0] = profileId;
    			arr[1] = total;

    			customers.add(arr);
    		}
    		statement.close();
    		res.close();
            return new Packet<>(SUCCEEDED, null, customers);
    	} catch (Exception e) {
            e.printStackTrace();
            return new Packet<>(FAILED, e.getMessage(), null);
        }
    }

    public static Packet<ArrayList<String[]>> getHighestGrossingEmployees(Connection conn) {
    	String query = "SELECT E.SSN, SUM(D.BookingFee) AS Total " +
                "FROM Employee E, CustomerDate D " +
                "WHERE E.SSN = D.CustRep " +
                "GROUP BY E.SSN " +
                "ORDER BY Total DESC LIMIT 5;";

    	ArrayList<String[]> customers = new ArrayList<>();

    	try  {
    		Statement statement = conn.createStatement();
    		ResultSet res = statement.executeQuery(query);

    		while (res.next()) {
    			String employee, total;

    			employee = res.getString(Employee.SSN);
    			total = res.getString("Total");
    			String[] arr = new String[2];
    			arr[0] = employee;
    			arr[1] = total;

    			customers.add(arr);
    		}
    		statement.close();
    		res.close();
            return new Packet<>(SUCCEEDED,null, customers);
    	} catch (Exception e) {
            e.printStackTrace();
            return new Packet<>(FAILED, e.getMessage(), null);
        }
    }

    public static Packet<ArrayList<String[]>> getMailingList(Connection conn) {
        String query = "SELECT F.ProfileID, P.Email " +
                "FROM Person P, Profile F " +
                "WHERE P.SSN = F.OwnerSSN;";

        try  {
            Statement statement = conn.createStatement();
            ResultSet res = statement.executeQuery(query);

            ArrayList<String[]> emails = new ArrayList<>();
            int count = 0;

            while (res.next()) {
                count++;
                String [] info = new String[2];
                info[0] = res.getString(Profile.PROFILE_ID);
                info[1] = res.getString(Person.EMAIL);
                emails.add(info);
            }
            statement.close();
            res.close();
            String lines = count + COLLECTED_ROLES;
            return new Packet<>(SUCCEEDED, lines, emails);
        } catch (Exception e) {
            e.printStackTrace();
            return new Packet<>(FAILED, e.getMessage(), null);
        }
    }

    public static Packet<Entity> registerCustomer(Connection conn, String SSN, String password, String email, String profileId) {
        String checkSSN = "SELECT 1 FROM Person P WHERE P.SSN = ?;";
        String checkEmail = "SELECT 1 FROM Person P WHERE P.Email = ?;";
        String checkProfileId = "SELECT 1 FROM Profile P WHERE P.ProfileID = ?;";

        String insert1 = "INSERT INTO Person (SSN, Password, Email) VALUES (?, ?, ?);";
        String insert2 = "INSERT INTO User VALUES (?, ?, ?, ?);";
        String insert3 = "INSERT INTO Profile(ProfileID, OwnerSSN, CreationDate, LastModDate) VALUES (?, ?, ?, ?);";
        try {
            PreparedStatement ssnPS = conn.prepareStatement(checkSSN);
            ssnPS.setString(1, SSN);
            if (ssnPS.executeQuery().next()) return new Packet<>(FAILED, OCCUPIED_SSN, null);
            PreparedStatement emailPS = conn.prepareStatement(checkEmail);
            emailPS.setString(1, email);
            if (emailPS.executeQuery().next()) return new Packet<>(FAILED, OCCUPIED_EMAIL, null);
            PreparedStatement idPS = conn.prepareStatement(checkProfileId);
            idPS.setString(1, profileId);
            if (idPS.executeQuery().next()) return new Packet<>(FAILED, OCCUPIED_ID, null);

            int line = 0;
            PreparedStatement ps1 = conn.prepareStatement(insert1);
            ps1.setString(1, SSN);
            ps1.setString(2, password);
            ps1.setString(3, email);
            line += ps1.executeUpdate();

            PreparedStatement ps2 = conn.prepareStatement(insert2);
            ps2.setString(1, SSN);
            ps2.setString(2, "User-User");
            ps2.setInt(3, 3);
            ps2.setTimestamp(4, generateTimestamp());
            line += ps2.executeUpdate();

            PreparedStatement ps3 = conn.prepareStatement(insert3);
            ps3.setString(1, profileId);
            ps3.setString(2, SSN);
            ps3.setTimestamp(3, generateTimestamp());
            ps3.setTimestamp(4, generateTimestamp());
            line += ps3.executeUpdate();

            conn.commit();

            ssnPS.close();
            emailPS.close();
            idPS.close();
            ps1.close();
            ps2.close();
            ps3.close();

            String lines = line + AFFECTED_ROLES;
            return new Packet<>(SUCCEEDED, lines, null);
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            return new Packet<>(FAILED, e.getMessage(), null);
        }
    }

    public static Packet<ArrayList<Date>> getHighestCalendarDate(Connection conn) {
        String query = "SELECT DATE(D.Date_Time) Date, (AVG(D.User1Rating)+AVG(D.User2Rating))/2 AS Rating " +
                "FROM CustomerDate D " +
                "GROUP BY DATE(D.Date_Time) " +
                "ORDER BY Rating DESC;";

        try {
            Statement statement = conn.createStatement();
            ResultSet res = statement.executeQuery(query);

            ArrayList<Date> dates = new ArrayList<>();
            int count = 0;

            while (res.next()) {
                count++;
                dates.add(res.getDate("Date"));
            }
            statement.close();
            res.close();
            String lines = count + COLLECTED_ROLES;
            return new Packet<>(SUCCEEDED, lines, dates);
        } catch (Exception e) {
            e.printStackTrace();
            return new Packet<>(FAILED, e.getMessage(), null);
        }
    }

    // The order of info is in AppConstants Profile Editing Protocol
    public static Packet<Entity> editProfile(Connection conn, HashMap<String, String> info) {
        String query1 = "UPDATE Person " +
                "SET Password = ?, " +
                "FirstName = ?, " +
                "LastName = ?, " +
                "Street = ?, " +
                "City = ?, " +
                "State = ?, " +
                "Zipcode = ?, " +
                "Email = ?, " +
                "Telephone = ? " +
                "WHERE SSN = ?;";

        String query2 = "UPDATE Profile " +
                "SET Age = ?, " +
                "DatingAgeRangeStart = ?, " +
                "DatingAgeRangeEnd = ?, " +
                "DatinGeoRange = ?, " +
                "M_F = ?, " +
                "Hobbies  = ?, " +
                "Height = ?, " +
                "Weight = ?, " +
                "HairColor = ?, " +
                "LastModDate = ? " +
                "WHERE ProfileID = ?;";

        try {
            int line = 0;
            PreparedStatement ps1 = conn.prepareStatement(query1);
            ps1.setString(1, info.get(Person.PASSWORD));
            ps1.setString(2, info.get(Person.FIRST_NAME));
            ps1.setString(3, info.get(Person.LAST_NAME));
            ps1.setString(4, info.get(Person.STREET));
            ps1.setString(5, info.get(Person.CITY));
            ps1.setString(6, info.get(Person.STATE));
            ps1.setInt(7, Integer.valueOf(info.get(Person.ZIPCODE)));
            ps1.setString(8, info.get(Person.EMAIL));
            ps1.setString(9, info.get(Person.TELEPHONE));
            ps1.setString(10, info.get(Person.SSN));
            line += ps1.executeUpdate();

            PreparedStatement ps2 = conn.prepareStatement(query2);
            ps2.setInt(1, Integer.valueOf(info.get(Profile.AGE)));
            ps2.setInt(2, Integer.valueOf(info.get(Profile.DATING_AGE_RANGE_START)));
            ps2.setInt(3, Integer.valueOf(info.get(Profile.DATING_AGE_RANGE_END)));
            ps2.setInt(4, Integer.valueOf(info.get(Profile.DATING_GEO_RANGE)));
            ps2.setString(5, info.get(Profile.GENDER));
            ps2.setString(6, info.get(Profile.HOBBIES));
            ps2.setDouble(7, Double.valueOf(info.get(Profile.HEIGHT)));
            ps2.setDouble(8, Double.valueOf(info.get(Profile.WEIGHT)));
            ps2.setString(9, info.get(Profile.HAIR_COLOR));
            ps2.setTimestamp(10, generateTimestamp());
            ps2.setString(11, info.get(Profile.PROFILE_ID));
            line += ps2.executeUpdate();

            ps1.close();
            ps2.close();
            conn.commit();

            String lines = line + AFFECTED_ROLES;
            return new Packet<>(SUCCEEDED, lines, null);
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            return new Packet<>(FAILED, e.getMessage(), null);
        }
    }

    public static Packet<Entity> deleteProfile(Connection conn, String profileId) {
        String checkExist = "SELECT OwnerSSN FROM Profile WHERE ProfileID = ?;";
        String deletion = "DELETE FROM Person WHERE SSN = ?;";

        try {
            PreparedStatement existPS = conn.prepareStatement(checkExist);
            existPS.setString(1, profileId);
            ResultSet profileSSN = existPS.executeQuery();
            String ssn;
            if (profileSSN.next()) {
                ssn = profileSSN.getString(Profile.SSN);
            } else {
                return new Packet<>(FAILED, QUERY_ERROR_EMPTY_SET, null);
            }
            PreparedStatement deletionPS = conn.prepareStatement(deletion);
            deletionPS.setString(1, ssn);

            int line = 0;
            line += deletionPS.executeUpdate();
            existPS.close();
            deletionPS.close();
            conn.commit();
            String lines = line + AFFECTED_ROLES;
            return new Packet<>(SUCCEEDED, lines, null);
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            return new Packet<>(FAILED, e.getMessage(), null);
        }
    }

    public static Packet<Entity> deleteEmployee(Connection conn, String ssn) {
        String checkExist = "SELECT SSN FROM Person WHERE SSN = ?;";
        String deletion = "DELETE FROM Person WHERE SSN = ?;";

        try {
            PreparedStatement existPS = conn.prepareStatement(checkExist);
            existPS.setString(1, ssn);
            ResultSet profileSSN = existPS.executeQuery();
            if (!profileSSN.next()) {
                return new Packet<>(FAILED, QUERY_ERROR_EMPTY_SET, null);
            }
            PreparedStatement deletionPS = conn.prepareStatement(deletion);
            deletionPS.setString(1, ssn);
            int line = deletionPS.executeUpdate();
            existPS.close();
            deletionPS.close();
            conn.commit();
            String lines = line + AFFECTED_ROLES;
            return new Packet<>(SUCCEEDED, lines, null);
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            return new Packet<>(FAILED, e.getMessage(), null);
        }
    }

    public static Packet<Entity> editEmployee(Connection conn, HashMap<String, String> info) {
        String query1 = "UPDATE Person " +
                "SET Password = ?, " +
                "FirstName = ?, " +
                "LastName = ?, " +
                "Street = ?, " +
                "City = ?, " +
                "State = ?, " +
                "Zipcode = ?, " +
                "Email = ?, " +
                "Telephone = ? " +
                "WHERE SSN = ?;";
        String query2 = "UPDATE Employee " +
                "SET Role = ?, " +
                "HourlyRate = ? " +
                "WHERE SSN = ?;";
        try {
            PreparedStatement ps = conn.prepareStatement(query1);
            ps.setString(1, info.get(Person.PASSWORD));
            ps.setString(2, info.get(Person.FIRST_NAME));
            ps.setString(3, info.get(Person.LAST_NAME));
            ps.setString(4, info.get(Person.STREET));
            ps.setString(5, info.get(Person.CITY));
            ps.setString(6, info.get(Person.STATE));
            ps.setInt(7, Integer.valueOf(info.get(Person.ZIPCODE)));
            ps.setString(8, info.get(Person.EMAIL));
            ps.setString(9, info.get(Person.TELEPHONE));
            ps.setString(10, info.get(Person.SSN));
            int line = ps.executeUpdate();

            PreparedStatement ps2 = conn.prepareStatement(query2);
            ps2.setString(1, info.get(Employee.ROLE));
            ps2.setInt(2, Integer.valueOf(info.get(Employee.HOURLY_RATE)));
            ps2.setString(3, info.get(Employee.SSN));
            line += ps2.executeUpdate();

            ps2.close();
            ps.close();
            conn.commit();

            String lines = line + AFFECTED_ROLES;
            return new Packet<>(SUCCEEDED, lines, null);
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            return new Packet<>(FAILED, e.getMessage(), null);
        }
    }

    // TODO: figure out what to do when a person wants to be the user and the employee at the same time
    public static Packet<Entity> createEmployee(Connection conn, HashMap<String, String> info) {
        String check = "SELECT SSN FROM Person WHERE SSN = ?;";
        String query = "INSERT INTO Person " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        String sal = "INSERT INTO Employee VALUES (?, ?, ?, ?);";
        try {
            PreparedStatement checkPS = conn.prepareStatement(check);
            checkPS.setString(1, info.get(Person.SSN));
            ResultSet res = checkPS.executeQuery();
            if (res.next()) return new Packet<>(FAILED, OCCUPIED_SSN, null);
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, info.get(Person.SSN));
            ps.setString(2, info.get(Person.PASSWORD));
            ps.setString(3, info.get(Person.FIRST_NAME));
            ps.setString(4, info.get(Person.LAST_NAME));
            ps.setString(5, info.get(Person.STREET));
            ps.setString(6, info.get(Person.CITY));
            ps.setString(7, info.get(Person.STATE));
            ps.setInt(8, Integer.valueOf(info.get(Person.ZIPCODE)));
            ps.setString(9, info.get(Person.EMAIL));
            ps.setString(10, info.get(Person.TELEPHONE));
            int line = ps.executeUpdate();

            PreparedStatement setSalary = conn.prepareStatement(sal);
            setSalary.setString(1, info.get(Employee.SSN));
            setSalary.setString(2, info.get(Employee.ROLE));
            setSalary.setDate(3, new java.sql.Date(generateTimestamp().getTime()));
            setSalary.setInt(4, Integer.parseInt(info.get(Employee.HOURLY_RATE)));
            line += setSalary.executeUpdate();

            checkPS.close();
            ps.close();
            setSalary.close();
            conn.commit();

            String lines = line + AFFECTED_ROLES;
            return new Packet<>(SUCCEEDED, lines, null);
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            return new Packet<>(FAILED, e.getMessage(), null);
        }
    }

    public static Packet<Entity> attachAccount(Connection conn, String ssn, String cardNum, String accNum) {
        String query = "INSERT INTO Account VALUES (?, ?, ?, ?);";

        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, ssn);
            ps.setString(2, cardNum);
            ps.setString(3, accNum);
            ps.setDate(4, new java.sql.Date(generateTimestamp().getTime()));
            String lines = ps.executeUpdate() + AFFECTED_ROLES;
            ps.close();
            conn.commit();
            return new Packet<>(SUCCEEDED, lines, null);
        } catch (SQLException e) {
            e.printStackTrace();
            return new Packet<>(FAILED, e.getMessage(), null);
        }
    }

    public static Packet<Entity> deleteAccount(Connection conn, String ssn, String cardNum, String accNum) {
        String query = "DELETE FROM Account " +
                "WHERE OwnerSSN = ? AND CardNumber = ? AND AcctNum = ?;";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, ssn);
            ps.setString(2, cardNum);
            ps.setString(3, accNum);
            String lines = ps.executeUpdate() + AFFECTED_ROLES;
            ps.close();
            conn.commit();
            return new Packet<>(SUCCEEDED, lines, null);
        } catch (SQLException e) {
            e.printStackTrace();
            return new Packet<>(FAILED, e.getMessage(), null);
        }
    }

    public static Packet<ArrayList<Report>> generateMonthlyReport(Connection conn, String year, String month) {
        String query = "SELECT P.FirstName AS FN, P.LastName AS LN, M.HourlyRate AS salary, N.Total AS total " +
                "FROM (SELECT E.SSN AS ssn, SUM(D.BookingFee) AS Total " +
                "FROM Employee E, CustomerDate D " +
                "WHERE Month(Date_Time) = ? AND YEAR(Date_Time) = ? AND E.SSN = D.CustRep " +
                "GROUP BY E.SSN) N, Employee M, Person P " +
                "WHERE N.ssn = P.SSN AND N.ssn = M.SSN " +
                "ORDER BY LN ASC;";

        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, Integer.valueOf(month));
            ps.setInt(2, Integer.valueOf(year));
            ResultSet res = ps.executeQuery();
            ArrayList<Report> reports = new ArrayList<>();
            int count = 0;
            while (res.next()) {
                count++;
                String fn = res.getString(Report.FIRST_NAME);
                String ln = res.getString(Report.LAST_NAME);
                int salary = res.getInt(Report.SALARY);
                double total = res.getDouble(Report.TOTAL);
                Report report = new Report(fn, ln, salary, total);
                reports.add(report);
            }
            ps.close();
            res.close();
            String lines = count + COLLECTED_ROLES;
            return new Packet<>(SUCCEEDED, lines, reports);

        } catch (SQLException e) {
            e.printStackTrace();
            return new Packet<>(FAILED, e.getMessage(), null);
        }
    }

    public static Packet<ArrayList<CustomerDate>> getDateFromCalendar(Connection conn, String year, String month, String day) {
        String query = "SELECT * " +
                "FROM CustomerDate D " +
                "WHERE Date(D.Date_Time) = ?;";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            Calendar c = Calendar.getInstance();
            c.clear();
            c.set(Calendar.YEAR, Integer.valueOf(year));
            int realMonth = Integer.valueOf(month) - 1;
            c.set(Calendar.MONTH, realMonth);
            c.set(Calendar.DATE, Integer.valueOf(day));
            ps.setDate(1, new java.sql.Date(c.getTime().getTime()));

            ResultSet res = ps.executeQuery();

            ArrayList<CustomerDate> dates = new ArrayList<>();

            int count = 0;
            while (res.next()) {
                String id = res.getString(CustomerDate.DATE_ID);
                String pf1 = res.getString(CustomerDate.PROFILE_1);
                String pf2 = res.getString(CustomerDate.PROFILE_2);
                String rep = res.getString(CustomerDate.CUSTOMER_REP);
                Date dateTime = res.getTimestamp(CustomerDate.DATE_TIME);
                String location = res.getString(CustomerDate.LOCATION);
                double fee = res.getDouble(CustomerDate.BOOKING_FEE);
                String comments = res.getString(CustomerDate.COMMENTS);
                int rate1 = res.getInt(CustomerDate.USER1_RATING);
                int rate2 = res.getInt(CustomerDate.USER2_RATING);

                CustomerDate cd = new CustomerDate(id, pf1, pf2, rep, dateTime, location, fee, comments, rate1, rate2);
                count++;
                dates.add(cd);
            }
            String message = count + COLLECTED_ROLES;
            ps.close();
            res.close();
            return new Packet<>(SUCCEEDED, message, dates);
        } catch (SQLException e) {
            e.printStackTrace();
            return new Packet<>(FAILED, e.getMessage(), null);
        }
    }

    public static Packet<ArrayList<String>> getDatingObjects(Connection conn, String profileId) {
        String query = "SELECT D.Profile1 AS ProfileId " +
                "FROM CustomerDate D " +
                "WHERE D.Profile2 = ? " +
                "UNION " +
                "SELECT D.Profile2 AS ProfileId " +
                "FROM CustomerDate D " +
                "WHERE D.Profile1 = ?;";

        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, profileId);
            ps.setString(2, profileId);
            ResultSet res = ps.executeQuery();

            ArrayList<String> ids = new ArrayList<>();
            int count = 0;

            while (res.next()) {
                ids.add(res.getString("ProfileId"));
                count++;
            }
            String msg = count + COLLECTED_ROLES;
            ps.close();
            res.close();
            return new Packet<>(SUCCEEDED, msg, ids);
        } catch (SQLException e) {
            e.printStackTrace();
            return new Packet<>(FAILED, e.getMessage(), null);
        }
    }

    private static Packet<CustomerDate> fetchDate(Connection conn, String dateId) {
        String query = "SELECT * FROM CustomerDate WHERE DateId = ?;";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, dateId);
            ResultSet res = ps.executeQuery();

            CustomerDate date;

            if (res.next()) {
                String id = res.getString(CustomerDate.DATE_ID);
                String pf1 = res.getString(CustomerDate.PROFILE_1);
                String pf2 = res.getString(CustomerDate.PROFILE_2);
                String rep = res.getString(CustomerDate.CUSTOMER_REP);
                Date dateTime = res.getTimestamp(CustomerDate.DATE_TIME);
                String location = res.getString(CustomerDate.LOCATION);
                double fee = res.getDouble(CustomerDate.BOOKING_FEE);
                String comments = res.getString(CustomerDate.COMMENTS);
                int rate1 = res.getInt(CustomerDate.USER1_RATING);
                int rate2 = res.getInt(CustomerDate.USER2_RATING);

                date = new CustomerDate(id, pf1, pf2, rep, dateTime, location, fee, comments, rate1, rate2);
            } else {
                return new Packet<>(FAILED, QUERY_ERROR_EMPTY_SET, null);
            }

            ps.close();
            res.close();
            return new Packet<>(SUCCEEDED, null, date);
        } catch (SQLException e) {
            e.printStackTrace();
            return new Packet<>(FAILED, e.getMessage(), null);
        }
    }

    public static Packet<Entity> rateDate(Connection conn, String dateId, String profileId, String rate) {
        String query1 = "UPDATE CustomerDate " +
                "SET User1Rating = ? " +
                "WHERE DateId = ?;";
        String query2 = "UPDATE CustomerDate " +
                "SET User2Rating = ? " +
                "WHERE DateId = ?;";
        try {
            PreparedStatement ps;
            Packet<CustomerDate> res = fetchDate(conn, dateId);
            CustomerDate date = res.getEntity();
            if (date == null) {
                return new Packet<>(FAILED, res.getMessage(), null);
            } else if (date.getProfile1().equals(profileId)) {
                ps = conn.prepareStatement(query1);
            } else if (date.getProfile2().equals(profileId)) {
                ps = conn.prepareStatement(query2);
            } else {
                return new Packet<>(FAILED, QUERY_ERROR_INCONSISTENT_DATABASE, null);
            }
            ps.setInt(1, Integer.valueOf(rate));
            ps.setString(2, dateId);
            String msg = ps.executeUpdate() + AFFECTED_ROLES;
            ps.close();
            conn.commit();
            return new Packet<>(SUCCEEDED, msg, null);
        } catch (SQLException e) {
            e.printStackTrace();
            return new Packet<>(FAILED, e.getMessage(), null);
        }
    }

    // profileId, zipcode, city, state, hobbies, gender, height, weight, hairColor, dateRangeStart, dateRangeEnd
    public static Packet<ArrayList<String>> search(Connection conn, HashMap<String, String> info) {
        String query = "SELECT F.ProfileID FROM Profile F, Person P " +
                "WHERE F.OwnerSSN = P.SSN " +
                "AND F.ProfileID LIKE ? " +
                "AND P.Zipcode > ? AND P.Zipcode < ? " +
                "AND P.FirstName LIKE ? " +
                "AND P.LastName LIKE ? " +
                "AND P.City LIKE ? " +
                "AND P.State LIKE ? " +
                "AND F.Hobbies LIKE ? " +
                "AND F.M_F LIKE ? " +
                "AND F.Height > ? AND F.Height < ? " +
                "AND F.Weight <= ? " +
                "AND F.HairColor LIKE ? " +
                "AND F.Age > ? AND F.Age < ?;";
        String pid = info.get(Profile.PROFILE_ID);
        String zip = info.get(Person.ZIPCODE);
        String fn = info.get(Person.FIRST_NAME);
        String ln = info.get(Person.LAST_NAME);
        String city = info.get(Person.CITY);
        String state = info.get(Person.STATE);
        String hobby = info.get(Profile.HOBBIES);
        String gender = info.get(Profile.GENDER);
        String ht = info.get(Profile.HEIGHT);
        String wt = info.get(Profile.WEIGHT);
        String hair = info.get(Profile.HAIR_COLOR);
        String st = info.get(Profile.DATING_AGE_RANGE_START);
        String end = info.get(Profile.DATING_AGE_RANGE_END);

        try {
            String skip = "%_%";
            PreparedStatement ps = conn.prepareStatement(query);
            if (pid == null) {
                ps.setString(1, skip);
            } else {
                ps.setString(1, "%" + pid + "%");
            }
            if (zip == null) {
                ps.setInt(2, 0);
                ps.setInt(3, 99999);
            } else {
                int intZip = Integer.valueOf(zip);
                ps.setInt(2, intZip - 20);
                ps.setInt(3, intZip + 20);
            }
            if (fn == null) {
                ps.setString(4, skip);
            } else {
                ps.setString(4, "%" + fn + "%");
            }
            if (ln == null) {
                ps.setString(5, skip);
            } else {
                ps.setString(5, "%" + ln + "%");
            }
            if (city == null) {
                ps.setString(6, skip);
            } else {
                ps.setString(6, "%" + city + "%");
            }
            if (state == null) {
                ps.setString(7, skip);
            } else {
                ps.setString(7, "%" + state + "%");
            }
            if (hobby == null) {
                ps.setString(8, skip);
            } else {
                ps.setString(8, "%" + hobby + "%");
            }
            if (gender == null) {
                ps.setString(9, skip);
            } else {
                ps.setString(9, gender);
            }
            if (ht == null) {
                ps.setDouble(10, 0.0);
                ps.setDouble(11, 99999.0);
            } else {
                double intHt = Double.valueOf(ht);
                ps.setDouble(10, intHt - 0.1);
                ps.setDouble(11, intHt + 0.1);
            }
            if (wt == null) {
                ps.setInt(12, 99999);
            } else {
                int intWt = Integer.valueOf(wt);
                ps.setInt(12, intWt);
            }
            if (hair == null) {
                ps.setString(13, skip);
            } else {
                ps.setString(13, "%" + hair + "%");
            }
            if (st == null) {
                ps.setInt(14, 0);
            } else {
                ps.setInt(14, Integer.valueOf(st));
            }
            if (end == null) {
                ps.setInt(15, 150);
            } else {
                ps.setInt(15, Integer.valueOf(end));
            }
            ResultSet res = ps.executeQuery();
            ArrayList<String> ids = new ArrayList<>();
            while (res.next()) {
                ids.add(res.getString(Profile.PROFILE_ID));
            }
            ps.close();
            res.close();
            String msg = ids.size() + COLLECTED_ROLES;
            return new Packet<>(SUCCEEDED, msg, ids);
        } catch (SQLException e) {
            e.printStackTrace();
            return new Packet<>(FAILED, e.getMessage(), null);
        }
    }
}