import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

@Entity
public class Role implements GrantedAuthority {
    @Id
    private String name;

    // Конструкторы
    public Role() {}

    public Role(String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return name;
    }

    // Геттеры и сеттеры
    public void setName(String name) {
        this.name = name;
    }
}
