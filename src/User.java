import java.util.ArrayList;
import java.util.List;

public class User {

    private String username;
    private String password;
    private List<Hero> boughtHeroes;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        boughtHeroes = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
    
    public void addBoughtHero(Hero hero) {
        boughtHeroes.add(hero);
    }

    public List<Hero> getBoughtHeroes() {
        return boughtHeroes;
    }
}
