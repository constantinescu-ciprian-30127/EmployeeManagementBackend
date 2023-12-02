//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.management.usermanagement.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Role {
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    @Id
    private Integer id;
    private String description;
    private String details;

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof Role)) {
            return false;
        } else {
            Role other = (Role)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label47: {
                    Object this$id = this.getId();
                    Object other$id = other.getId();
                    if (this$id == null) {
                        if (other$id == null) {
                            break label47;
                        }
                    } else if (this$id.equals(other$id)) {
                        break label47;
                    }

                    return false;
                }

                Object this$description = this.getDescription();
                Object other$description = other.getDescription();
                if (this$description == null) {
                    if (other$description != null) {
                        return false;
                    }
                } else if (!this$description.equals(other$description)) {
                    return false;
                }

                Object this$details = this.getDetails();
                Object other$details = other.getDetails();
                if (this$details == null) {
                    if (other$details != null) {
                        return false;
                    }
                } else if (!this$details.equals(other$details)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Role;
    }

    public int hashCode() {
        int result = 1;
        Object $id = this.getId();
        result = result * 59 + ($id == null ? 43 : $id.hashCode());
        Object $description = this.getDescription();
        result = result * 59 + ($description == null ? 43 : $description.hashCode());
        Object $details = this.getDetails();
        result = result * 59 + ($details == null ? 43 : $details.hashCode());
        return result;
    }

    public String toString() {
        Integer var10000 = this.getId();
        return "Role(id=" + var10000 + ", description=" + this.getDescription() + ", details=" + this.getDetails() + ")";
    }

    public Role() {
    }

    public Role(final Integer id, final String description, final String details) {
        this.id = id;
        this.description = description;
        this.details = details;
    }
}
