package com.gseem.dao;

import com.gseem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author feiyue
 * @date 2019/11/9
 */
@Transactional
public interface UserDao extends PagingAndSortingRepository<User, Long>,
                                 JpaSpecificationExecutor<User>,
                                 JpaRepository<User,Long> {
    User findById(String id);

}
