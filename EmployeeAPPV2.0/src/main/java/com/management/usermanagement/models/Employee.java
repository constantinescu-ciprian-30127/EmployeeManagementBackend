//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.management.usermanagement.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Employee {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    @ManyToMany(
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE},
            fetch = FetchType.EAGER
    )
    @JoinTable(
            name = "employee_role",
            joinColumns = {@JoinColumn(
                    name = "employee_id"
            )},
            inverseJoinColumns = {@JoinColumn(
                    name = "role_id"
            )}
    )
    Set<Role> roles = new HashSet<>();

    public Employee() {
    }

    public int getId() {
        return this.id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }


    public Set<Role> getRoles() {
        return this.roles;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public void setPassword(final String password) {
        this.password = password;
    }


    public void setRoles(final Set<Role> roles) {
        this.roles = roles;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof Employee)) {
            return false;
        } else {
            Employee other = (Employee)o;
            if (!other.canEqual(this)) {
                return false;
            } else if (this.getId() != other.getId()) {
                return false;
            } else {
                Object this$firstName = this.getFirstName();
                Object other$firstName = other.getFirstName();
                if (this$firstName == null) {
                    if (other$firstName != null) {
                        return false;
                    }
                } else if (!this$firstName.equals(other$firstName)) {
                    return false;
                }

                Object this$lastName = this.getLastName();
                Object other$lastName = other.getLastName();
                if (this$lastName == null) {
                    if (other$lastName != null) {
                        return false;
                    }
                } else if (!this$lastName.equals(other$lastName)) {
                    return false;
                }

                label71: {
                    Object this$email = this.getEmail();
                    Object other$email = other.getEmail();
                    if (this$email == null) {
                        if (other$email == null) {
                            break label71;
                        }
                    } else if (this$email.equals(other$email)) {
                        break label71;
                    }

                    return false;
                }

                label64: {
                    Object this$password = this.getPassword();
                    Object other$password = other.getPassword();
                    if (this$password == null) {
                        if (other$password == null) {
                            break label64;
                        }
                    } else if (this$password.equals(other$password)) {
                        break label64;
                    }

                    return false;
                }


                Object this$roles = this.getRoles();
                Object other$roles = other.getRoles();
                if (this$roles == null) {
                    if (other$roles != null) {
                        return false;
                    }
                } else if (!this$roles.equals(other$roles)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Employee;
    }

    public int hashCode() {
        int result = 1;
        result = result * 59 + this.getId();
        Object $firstName = this.getFirstName();
        result = result * 59 + ($firstName == null ? 43 : $firstName.hashCode());
        Object $lastName = this.getLastName();
        result = result * 59 + ($lastName == null ? 43 : $lastName.hashCode());
        Object $email = this.getEmail();
        result = result * 59 + ($email == null ? 43 : $email.hashCode());
        Object $password = this.getPassword();
        result = result * 59 + ($password == null ? 43 : $password.hashCode());
        Object $roles = this.getRoles();
        result = result * 59 + ($roles == null ? 43 : $roles.hashCode());
        return result;
    }

    public String toString() {
        int var10000 = this.getId();
        return "Employee(id=" + var10000 + ", firstName=" + this.getFirstName() + ", lastName=" + this.getLastName() + ", email=" + this.getEmail() + ", password=" + this.getPassword() + ", roles=" + String.valueOf(this.getRoles()) + ")";
    }
}
