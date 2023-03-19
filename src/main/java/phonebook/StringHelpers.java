package phonebook;

public class StringHelpers {
    public static boolean isNullOrEmpty(String string){
        if(string == null || string.trim().isEmpty()){
            return true;
        }
        return false;
    }
}
