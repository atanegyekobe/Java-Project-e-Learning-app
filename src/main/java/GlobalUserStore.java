public class GlobalUserStore {
    private static int userId; // Static variable to store the user_id

    public static void setUserId(int id) {
        userId = id;
    }

    public static int getUserId() {
        return userId;
    }
}
