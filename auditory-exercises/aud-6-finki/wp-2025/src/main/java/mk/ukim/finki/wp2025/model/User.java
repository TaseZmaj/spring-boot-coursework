package mk.ukim.finki.wp2025.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
//moras vaka da go napravis ako go pravis bez @Table(name=) ke ti javi
//greshka palikacijata bidejki User e rezerviran zbor vo Postre
@Table(name = "shop_user")
public class User {

    @Id
    private String username;
    private String password;
    private String name;
    private String surname;

    //so ova mu kazuvas deka vekje ke e kreirana many-to-many tabela od ShoppingCart,
    //za da ne ti se kreira ushte edna tablea, go pravish vaka mappedBy= mu turas
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<ShoppingCart> carts;

    public User(String username, String password, String name, String surname) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
    }

}
