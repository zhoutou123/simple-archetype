package com.miya.model;

import javax.persistence.Id;

/**
 * @description //TODO
 * @date 2018年6月29日 下午6:35:52
 *
 * @author zhoutuo
 */
// @Table(name = "person")
// @Entity
public class Person {

	@Id
	private int id;

	private String name;

	private String name1;

}
