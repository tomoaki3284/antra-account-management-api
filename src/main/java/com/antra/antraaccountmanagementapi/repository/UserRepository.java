package com.antra.antraaccountmanagementapi.repository;

import com.antra.antraaccountmanagementapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>  {
	
	@Query("SELECT u FROM User u "
		+ "join fetch u.userRoleSet "
		+ "WHERE u.username = :username")
	User findUserByUserName(@Param("username") String username);
}
