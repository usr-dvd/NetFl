public class Users {

    private  User[] users = new User[Const.USERS_ARR_LEN];
    private User user;

    public User getUser() {
        return user;
    }

    public boolean findUser(String userName, boolean needToDefine) {
        for (int i = 0; i < users.length; i++){
            if (users[i] != null && users[i].getUserName().equals(userName)) {
                if (needToDefine){
                    user = users[i];
                }
                return true;
            }
        }
        return false;
    }

    public void addUser(User user) {
        for (int i = 0; i < users.length; i++) {
            if(users[i]==null) {
                users[i]=user;
                break;
            }
        }
    }
    public boolean checkIfEmpty(){
        boolean isEmpty = true;
        for (int i = 0; i < users.length; i++) {
            if (users[i] != null) {
                isEmpty = false;
                break;
            }
        }
        return isEmpty;
    }
    public boolean checkIfFull() {
        boolean isFull = true;
        for (int i = 0; i < users.length; i++) {
            if (users[i]==null) {
                isFull = false;
                break;
            }
        }
        return isFull;
    }

    public User[] getUsers() {
        return users;
    }

    public void setUsers(User[] users) {
        this.users = users;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
