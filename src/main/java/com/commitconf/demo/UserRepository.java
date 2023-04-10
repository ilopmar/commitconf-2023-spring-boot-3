package com.commitconf.demo;

import org.springframework.data.repository.ListCrudRepository;

interface UserRepository extends ListCrudRepository<User, Long> {

}
