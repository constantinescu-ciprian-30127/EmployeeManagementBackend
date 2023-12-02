//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.management.usermanagement.repositories;

import com.management.usermanagement.models.Role;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    @Query(
            value = "SELECT * FROM role WHERE id NOT IN (SELECT role_id FROM employee_role WHERE employee_id = ?1)",
            nativeQuery = true
    )
    List<Role> getUserNotRoles(Integer userId);

    Role getRoleByDescription(String description);
}
