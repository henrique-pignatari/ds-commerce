package com.henrique.dscommerce.repositories;

import com.henrique.dscommerce.entities.User;
import com.henrique.dscommerce.projections.UserDetailsProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(nativeQuery = true, value = """
            SELECT u.email as userName, u.password, r.id as roleId, r.authority
            FROM tb_user u
            INNER JOIN tb_user_role ur ON u.id = ur.user_id
            INNER JOIN tb_role r ON r.id = ur.role_id
            WHERE u.email = :email
        """)
    List<UserDetailsProjection> searchUserAndRolesByEmail(String email);

    Optional<User> findByEmail(String email);
}
