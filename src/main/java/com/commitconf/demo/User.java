package com.commitconf.demo;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "users")
public record User(@Id Long id, String name) {

}
