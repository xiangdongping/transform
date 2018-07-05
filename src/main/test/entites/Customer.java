package entites;

import org.flybug.util.transform.annotaction.CopyIgnore;

/**
 * @author xdp
 * @date 2018/7/5
 */
public class Customer {

    @CopyIgnore(get=false)
    private  Long id;

    private String name;

    private Integer age;

    @CopyIgnore // 该属性始终不应该返回给客户端
    private String password;

    private String email;

    private Integer state;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return this.age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getState() {
        return this.state;
    }

    public void setState(Integer state) {
        this.state = state;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Customer{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", age=").append(age);
        sb.append(", password='").append(password).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", state=").append(state);
        sb.append('}');
        return sb.toString();
    }
}
